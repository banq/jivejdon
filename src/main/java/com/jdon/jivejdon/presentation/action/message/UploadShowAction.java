package com.jdon.jivejdon.presentation.action.message;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.domain.model.attachment.UploadFile;
import com.jdon.jivejdon.domain.model.message.upload.UploadFileFilter;
import com.jdon.jivejdon.api.property.UploadService;
import com.jdon.util.Debug;
import com.jdon.util.UtilValidate;

public class UploadShowAction extends Action {
	public final static String module = UploadShowAction.class.getName();

	private static final byte[] BLANK = { 71, 73, 70, 56, 57, 97, 1, 0, 1, 0, -111, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, 0, 0, 0, 33, -7, 4, 1, 0,
			0, 2, 0, 44, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 2, 76, 1, 0, 59 };

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		if (UtilValidate.isEmpty(id))
			throw new Exception("parameter id is null");

		// see
		// com.jdon.jivejdon.domain.model.message.upload.MessageRenderingFile/MessageRenderingImage
		String type = request.getParameter("type");
		if (UtilValidate.isEmpty(type))
			type = "image/JPEG";

		String dlname = request.getParameter(UploadFileFilter.DOWNLOAD_NAME);
		UploadFile uploadFile = null;
		try {
			UploadService uploadService = (UploadService) WebAppUtil.getService("uploadService", request);
			uploadFile = uploadService.getUploadFile(id);
			if (uploadFile == null)
				throw new Exception("uploadFile == null");

			if (!UtilValidate.isEmpty(uploadFile.getContentType()))
				type = uploadFile.getContentType();

			outUploadFile(response, uploadFile.getData(), type, dlname);
		} catch (Exception ex) {
			Debug.logError("get the image error:" + ex + " id=" + id, module);
			outUploadFile(response, BLANK, "images/jpeg", null);
		}
		return null;
	}

	protected void outUploadFile(HttpServletResponse response, byte[] data, String type, String dlname) throws Exception {
		response.setContentType(type);
		if (!UtilValidate.isEmpty(dlname)) {
			response.setHeader("Content-Disposition", "attachment; filename=\"" + dlname + "\"");
		}
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		OutputStream toClient = response.getOutputStream();
		try {
			toClient.write(data);
		} catch (Exception ex) {
			Debug.logError("get the image error:" + ex, module);
		} finally {
			toClient.close();
		}
	}
}
