package com.jdon.jivejdon.event.domain.consumer.write.postThread;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.qiniu.cdn.CdnManager;
import com.qiniu.cdn.CdnResult;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.util.Arrays;


@Consumer("postThread")
public class CDNRefreshListener implements DomainEventHandler {
	private final static Logger logger = LogManager.getLogger(CDNRefreshListener.class);

	private final CDNRefreshUrls cdnRefreshUrls;


	public CDNRefreshListener(CDNRefreshUrls cdnRefreshUrls) {
		this.cdnRefreshUrls = cdnRefreshUrls;
	}

	@Override
	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		String clientIp = InetAddress.getLocalHost().getHostAddress();
		if (clientIp.indexOf("192.168") != -1 || clientIp.indexOf("127.0.0.1") != -1) {
			logger.error(clientIp + " is not server, so not send cdn refresh");
			return;
		}
		Arrays.stream(cdnRefreshUrls.getUrls()).forEach(this::refreshCDN);
	}

	private void refreshCDN(String fileurl) {
		//设置需要操作的账号的AK和SK
		String accessKey = "axCnOZ5hHeMMJLejjKhh7O56JdxAGpmEgY11G3EB";
		String secretKey = "sGSeH06-V2jiW3gKqzq7R0Rg0ZpJ576G_laLL2AK";
		Auth auth = Auth.create(accessKey, secretKey);

		Zone z = Zone.zone0();
		Configuration c = new Configuration(z);

		//实例化一个BucketManager对象
		BucketManager bucketManager = new BucketManager(auth, c);
		//要测试的空间和key，并且这个key在你空间中存在
		String bucket = "cdnjdon";
		String key = fileurl;
		try {
			//调用delete方法移动文件
			bucketManager.delete(bucket, key);
		} catch (QiniuException e) {
			//捕获异常信息
			Response r = e.response;
			System.out.println(r.toString());
			logger.error("cdn delete error=" + r.toString());
		}

		CdnManager cdn = new CdnManager(auth);
		//待刷新的链接列表
		String[] urls = new String[]{
				"https://cdn.jdon.com/" + fileurl,
				//....
		};
		try {
			//单次方法调用刷新的链接不可以超过100个
			CdnResult.RefreshResult result = cdn.refreshUrls(urls);
			logger.debug(" cdn refresh" + result.code);
			//获取其他的回复内容
		} catch (QiniuException e) {
			System.err.println(e.response.toString());
			logger.error("cdn refres error=" + e.response.toString());
		}
	}
}
