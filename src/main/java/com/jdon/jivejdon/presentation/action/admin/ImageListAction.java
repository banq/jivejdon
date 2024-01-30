package com.jdon.jivejdon.presentation.action.admin;

import javax.servlet.http.HttpServletRequest;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.property.UploadInfoVOService;
import com.jdon.strutsutil.ModelListAction;
/**
 * 
 * @author <a href="mailto:xinying_ge@yahoo.com.cn">GeXinying</a>
 *
 */
public class ImageListAction extends ModelListAction {

	/*
	 * (non-Javadoc)
	 * @see com.jdon.strutsutil.ModelListAction#getPageIterator(javax.servlet.http.HttpServletRequest, int, int)
	 */
	public PageIterator getPageIterator(HttpServletRequest request, int start,
			int count) {
		
		UploadInfoVOService uploadInfoVOService = (UploadInfoVOService) WebAppUtil.getService("uploadInfoVOService", 
				this.servlet.getServletContext());
		return uploadInfoVOService.getUploads(start, count);
	}

	/*
	 * (non-Javadoc)
	 * @see com.jdon.strutsutil.ModelListAction#findModelIFByKey(javax.servlet.http.HttpServletRequest, java.lang.Object)
	 */
	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		
		UploadInfoVOService uploadInfoVOService = (UploadInfoVOService) WebAppUtil.getService("uploadInfoVOService", 
				this.servlet.getServletContext());
		return uploadInfoVOService.getUploadInfoVO((String)key);
	}

}
