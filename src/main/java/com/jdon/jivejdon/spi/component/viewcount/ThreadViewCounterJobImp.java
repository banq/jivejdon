package com.jdon.jivejdon.spi.component.viewcount;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.Component;
import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.domain.model.property.Property;
import com.jdon.jivejdon.domain.model.property.ThreadPropertys;
import com.jdon.jivejdon.domain.model.thread.ViewCounter;
import com.jdon.jivejdon.infrastructure.repository.dao.PropertyDao;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;

/**
 * a cache used for holding view count of ForumThread the data that in the cache
 * will be flushed to database per one hour
 * 
 * @author oojdon banq
 * 
 */

@Component("threadViewCounterJob")
public class ThreadViewCounterJobImp implements Startable, ThreadViewCounterJob {
	private final static Logger logger = LogManager.getLogger(ThreadViewCounterJobImp.class);

	private final Map<Long,ViewCounter> viewcounters;
	private final Map<String, AtomicInteger> ipCountsMirror;
	
	private final PropertyDao propertyDao;
	private final ThreadViewCountParameter threadViewCountParameter;
	private final ScheduledExecutorUtil scheduledExecutorUtil;

	public ThreadViewCounterJobImp(final PropertyDao propertyDao,
			final ThreadViewCountParameter threadViewCountParameter, ScheduledExecutorUtil scheduledExecutorUtil) {
		this.viewcounters = new ConcurrentHashMap<>();
		this.ipCountsMirror = new ConcurrentHashMap<>();
		this.propertyDao = propertyDao;
		this.threadViewCountParameter = threadViewCountParameter;
		this.scheduledExecutorUtil = scheduledExecutorUtil;
	}

	public void start() {
		Runnable task = new Runnable() {
			public void run() {
				writeDB();
			}
		};
		// flush to db per one hour
		scheduledExecutorUtil.getScheduExec().scheduleAtFixedRate(task, threadViewCountParameter.getInitdelay(),
				threadViewCountParameter.getDelay(), TimeUnit.SECONDS);
	   System.out.println("ThreadViewCounterJobImp started");                
	}

	// when container down or undeploy, active this method.
	public void stop() {
		writeDB();
		viewcounters.clear();
		ipCountsMirror.clear();
		this.scheduledExecutorUtil.stop();
	}

	public void writeDB() {
		// construct a immutable set
		List<ViewCounter> viewCounters2 = new ArrayList<>(this.viewcounters.values());
		viewcounters.clear();
		ipCountsMirror.clear();
		// for (ViewCounter viewCounter : viewCounters2) {
		// 	long count = viewCounter.getViewCount();
		// 	if (count != -1 && count != 0) {
		// 		saveItem(viewCounter);
		// 	}
		// }
	}

	private void saveItem(ViewCounter viewCounter) {
		try {
			Property property = new Property();
			property.setName(ThreadPropertys.VIEW_COUNT);
			property.setValue(Long.toString(viewCounter.getViewCount()));
			propertyDao.updateProperty(Constants.THREAD, viewCounter.getThreadId(), property);
		} catch (Exception e) {
			logger.error(e);
		}
	}

/**
     * 门面方法：一键完成「原子限流校验」与「受控自增」
     * 通过threadId直接获取viewCount，无需传入完整的ViewCounter对象
     */
    @Override
    public void saveAndIncrement(Long threadId, String ip) {
        if (threadId == null || ip == null) {
            return;
        }

        // 1. 从数据库获取当前的view_count值
        Integer number = null;
        Property p = propertyDao.getThreadProperty(threadId, ThreadPropertys.VIEW_COUNT);
        if (p != null)
            number = Integer.valueOf(p.getValue());
        else
            number = new Integer(0);

        // 2. 创建临时ViewCounter用于限流检查和计数
        ViewCounter viewCounter = new ViewCounter(threadId, number);

        // 3. 直接把 ip 传给核心限流器进行双账本校验
        this.saveViewCounter(viewCounter, ip);

    }

    /**
     * 核心原子限流器（$O(1)$ 复杂度，纯原子无锁）
     */
    public void saveViewCounter(ViewCounter viewCounter, String ipAddress) {
        ipAddress = getIpSegmentKey(ipAddress);
        boolean shouldSave = false;

        // 1. 【原子步骤一】尝试将帖子抢占放入大账本（检查这小时内是不是第一次被点开）
        if (viewcounters.putIfAbsent(viewCounter.getThreadId(), viewCounter) == null) {

            // 2. 【原子步骤二】新帖子抢占成功，原子的增加影子账本中该 IP 的计数
            int currentCount = ipCountsMirror.compute(ipAddress, (key, count) -> {
                if (count == null) {
                    return new AtomicInteger(1); // 该 IP 第一次开新帖，初始化为 1
                } else {
                    count.incrementAndGet(); // 该 IP 又开了别的帖子，内部原子加 1
                    return count;
                }
            }).get();

            // 3. 熔断机制：如果发现该 IP 已经在疯狂开新帖了(>5)，立刻撤销并拒绝
            if (currentCount > 5) {
                viewcounters.remove(viewCounter.getThreadId()); // 从主账本抹除回滚
                shouldSave = false;
            } else {
                shouldSave = true;
            }
        } else {
            // 4. 【老帖子分支】如果大账本里早就有了这个帖子，直接累加影子账本的 IP 计数
            int currentCount = ipCountsMirror.compute(ipAddress, (key, count) -> {
                if (count == null) {
                    return new AtomicInteger(1);
                } else {
                    count.incrementAndGet();
                }
                return count;
            }).get();

            // 如果一段时间内该 IP 访问的不同新帖子总数没超过 5，则允许老帖子继续累加数量
            shouldSave = currentCount <= 5;
        }

        // 执行保存逻辑
        if (shouldSave) {
            if (viewCounter.addViewCount(ipAddress)) {
                saveItem(viewCounter);
            }
        }
    }

	/**
     * 🌟 高性能纯原生 IP 裁剪工具
     * 支持 IPv4 的 C段提取；如果是 IPv6 则保留原样或提取前缀。
     */
    private String getIpSegmentKey(String ip) {
        // 处理常见的 IPv4 
        int lastDotIndex = ip.lastIndexOf('.');
        if (lastDotIndex > 0) {
            // 截取到最后一个点（包含点），例如 "123.22.22."
            return ip.substring(0, lastDotIndex + 1);
        }
        
        // 如果是局域网 IPv6 或者异常格式（如 "0:0:0:0:0:0:0:1" 或 "::1"）
        // IPv6 爬虫通常变动的是后 64 位，可以考虑截取前 4 个冒号，这里简单做前缀提取
        int lastColonIndex = ip.lastIndexOf(':');
        if (lastColonIndex > 0) {
            return ip.substring(0, lastColonIndex + 1);
        }
        
        return ip; // 保底返回
    }

	public ViewCounter getViewCounter(Long threadId){
		return viewcounters.get(threadId);
	}

	public List<Long> getThreadIdsList() {
		return viewcounters.values().stream().map(e -> e.getThreadId()).collect(Collectors.toList());
	}

}
