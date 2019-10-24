/*
 * Copyright 2003-2006 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jdon.jivejdon.presentation.action;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.spi.component.throttle.hitkey.CustomizedThrottle;
import com.jdon.jivejdon.spi.component.throttle.hitkey.HitKeyIF;
import com.jdon.jivejdon.spi.component.throttle.hitkey.HitKeySame;
import com.jdon.jivejdon.domain.model.attachment.UploadFile;
import com.jdon.jivejdon.domain.model.message.upload.UploadFileFilter;
import com.jdon.jivejdon.api.property.UploadService;
import com.jdon.util.Debug;
import com.jdon.util.UtilValidate;

/**
 * id and oid are need oid is parentId of the image id is the image id;
 * 
 * @author banq(http://www.jdon.com)
 * 
 */
public class UploadShowAction extends Action {
	public final static String module = UploadShowAction.class.getName();
	private static final byte[] BLANK = { 71, 73, 70, 56, 57, 97, 1, 0, 1, 0, -111, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, 0, 0, 0, 33, -7, 4, 1, 0,
			0, 2, 0, 44, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 2, 76, 1, 0, 59 };

	private CustomizedThrottle customizedThrottle;

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		long header = request.getDateHeader("If-Modified-Since");
		if (header > 0) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return null;
		}

		String dlname = request.getParameter(UploadFileFilter.DOWNLOAD_NAME);
		String id = request.getParameter("id");
		String oid = request.getParameter("oid");
		String type = request.getParameter("type");

		try {
			if (UtilValidate.isEmpty(id) && UtilValidate.isEmpty(oid))
				throw new Exception("parameter id or oid is null");

			if (!checkSpamHit(id, request)) {
				throw new Exception("this is spam =" + request.getRemoteAddr());
			}

			UploadFile uploadFile = getUploadFile(oid, id);

			if (uploadFile == null)
				throw new Exception("uploadFile == null");

			if (!UtilValidate.isEmpty(uploadFile.getContentType()))
				type = uploadFile.getContentType();
			else
				type = "image/JPEG";

			byte[] bytes = null;
			if (!UtilValidate.isEmpty(oid))
				bytes = uploadFile.getData(oid);
			else
				// for old version
				bytes = uploadFile.getData();

			if (bytes == null) {
				bytes = BLANK;
			}
			outUploadFile(response, bytes, type, dlname);
		} catch (Exception ex) {
			Debug.logError(
					"when get the image, error happened:" + ex.getMessage() + " id=" + id + " oid=" + oid + " remote=" + request.getRemoteAddr(),
					module);
			closeResponse(request, response);
		}
		return null;
	}

	protected UploadFile getUploadFile(String oid, String id) {
		if (UtilValidate.isEmpty(id))
			return null;
		UploadService uploadService = (UploadService) WebAppUtil.getService("uploadService", this.servlet.getServletContext());
		return uploadService.getUploadFile(id);
	}

	private void closeResponse(HttpServletRequest request, HttpServletResponse response) {
		try {
			disableSessionOnlines(request);
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
		} catch (IOException e) {
		}
	}

	private void disableSessionOnlines(HttpServletRequest httpRequest) {
		HttpSession session = httpRequest.getSession(false);
		if (session != null)
			session.invalidate();
	}

	private boolean checkSpamHit(String oid, HttpServletRequest request) {
		if (customizedThrottle == null) {
			customizedThrottle = (CustomizedThrottle) WebAppUtil.getComponentInstance("customizedThrottle", this.servlet.getServletContext());
		}
		HitKeyIF hitKey = new HitKeySame(request.getRemoteAddr(), oid);
		return customizedThrottle.processHitFilter(hitKey);
	}

	protected void outUploadFile(HttpServletResponse response, byte[] data, String type, String dlname) throws Exception {
		if (data == null)
			throw new Exception("data is null ");
		response.setContentType(type);
		if (!UtilValidate.isEmpty(dlname)) {
			response.setHeader("Content-Disposition", "attachment; filename=\"" + dlname + "\"");
		}

		long d = System.currentTimeMillis();
		response.addDateHeader("Last-Modified", d);
		response.addDateHeader("Expires", d + (5 * 24 * 60 * 60 * 1000));
		OutputStream toClient = response.getOutputStream();
		try {
			toClient.write(data);
		} catch (Exception ex) {
			Debug.logError("get the image error:" + ex, module);
			throw new Exception(ex);
		} finally {
			toClient.close();
		}
	}

}
