package com.jdon.jivejdon.event.domain.consumer.write.postThread;

import com.jdon.annotation.Component;
import com.qiniu.cdn.CdnManager;
import com.qiniu.cdn.CdnResult;
import com.qiniu.common.QiniuException;
import com.qiniu.util.Auth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Component("cdnRefreshSubsciber")
public class CDNQiNiuRefreshSubsciber {
	private final static Logger logger = LogManager.getLogger(CDNQiNiuRefreshSubsciber.class);

	public static void main(String[] args) {
		CDNQiNiuRefreshSubsciber cDNQiNiuRefreshSubsciber = new CDNQiNiuRefreshSubsciber();
		cDNQiNiuRefreshSubsciber.cdnRefresh("rss");
		System.out.println("qq");
	}

	public void cdnRefresh(String fileurl) {
		new Thread(() -> {
			try {
				//if (ToolsUtil.isDebug()) return;
				refreshCDN(fileurl);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}

	private void refreshCDN(String fileurl) {
		//设置需要操作的账号的AK和SK
		String accessKey = "axCnOZ5hHeMMJLejjKhh7O56JdxAGpmEgY11G3EB";
		String secretKey = "sGSeH06-V2jiW3gKqzq7R0Rg0ZpJ576G_laLL2AK";
		Auth auth = Auth.create(accessKey, secretKey);

		CdnManager cdn = new CdnManager(auth);
		//待刷新的链接列表
		String[] urls = new String[]{
				"https://cdn.jdon.com/" + fileurl,
				//....
		};
		try {
			//单次方法调用刷新的链接不可以超过100个
			CdnResult.RefreshResult result = cdn.refreshUrls(urls);
			logger.debug(urls + " cdn refresh" + result.code);
			//获取其他的回复内容
		} catch (QiniuException e) {
			System.err.println("cdn refres url=" + urls + " error:" + e.response.toString());
			logger.error("cdn refres url=" + urls + " error:" + e.response.toString());
		}
	}

}