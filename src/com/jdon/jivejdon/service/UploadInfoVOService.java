package com.jdon.jivejdon.service;

import java.util.Collection;

import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.attachment.UploadInfoVO;

/**
 * 
 * @author <a href="mailto:xinying_ge@yahoo.com.cn">GeXinying</a>
 * 
 */
public interface UploadInfoVOService {

	void deleteUpload(EventModel em);

	UploadInfoVO getUploadInfoVO(String id);

	PageIterator getUploads(int start, int count);

	Collection getUploadInfoVOs(int start, int count);

	Collection getUploadInfoVOsOfMessage(int count);

}
