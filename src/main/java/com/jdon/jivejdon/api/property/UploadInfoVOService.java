package com.jdon.jivejdon.api.property;

import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.attachment.UploadInfoVO;

import java.util.Collection;

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
