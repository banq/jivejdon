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
package com.jdon.jivejdon.model.message.upload;

import java.util.Collection;
import java.util.Iterator;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.attachment.UploadFile;
import com.jdon.jivejdon.model.message.MessageRenderSpecification;
import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.util.Debug;

/**
 * @author banq(http://www.jdon.com)
 * 
 */
public class UploadFileFilter implements MessageRenderSpecification {
	private final static String module = UploadFileFilter.class.getName();

	// want ot modify it in admin
	private String fileShowFileName = "../../uploadDL.jsp";

	public final static String DOWNLOAD_NAME = "dlname";

	public ForumMessage render(ForumMessage message) {
		try {
			MessageVO messageVO = message.getMessageVO();
			String newBody = appendTags(message);
			messageVO.setBody(newBody);
		} catch (Exception e) {
			Debug.logError("" + e, module);
		}
		return message;
	}

	/**
	 * return the String:
	 * 
	 * [img]/imageShow.jsp?id=3464[/img]
	 * 
	 * @param str
	 * @return
	 */
	public String appendTags(ForumMessage message) {
		MessageVO messageVO = message.getMessageVO();
		String str = messageVO.getBody();
		Collection uploadFiles = message.getAttachment().getUploadFiles();
		if ((uploadFiles == null) || (uploadFiles.size() == 0)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		sb.append("\n");

		StringBuilder attachString = new StringBuilder();
		attachString.append("<br>");
		attachString.append("attachment:<br>");
		attachString.append("<hr>");

		boolean attached = false;

		Iterator iter = uploadFiles.iterator();
		while (iter.hasNext()) {
			UploadFile uploadFile = (UploadFile) iter.next();
			UploadHelper uploadHelper = new UploadHelper();
			if (uploadHelper.isOthersTypes(uploadFile.getName())) {
				attached = true;
				append(attachString, uploadFile);
			}

		}

		if (attached)
			sb.append(attachString);

		return sb.toString().intern();
	}

	// see com.jdon.jivejdon.presentation.action.UploadShowAction
	private void append(StringBuilder sb, UploadFile uploadFile) {
		// sb.append("[img]images/rar.gif[/img]");

		sb.append("<a href=\"");
		sb.append(fileShowFileName);
		sb.append("?type=").append(uploadFile.getContentType());
		sb.append("&").append(DOWNLOAD_NAME).append("=").append(uploadFile.getName());// file
		// that
		// need
		// download
		sb.append("&id=").append(uploadFile.getId());
		sb.append("&oid=").append(uploadFile.getOid());
		sb.append("\" target=\"_blank\">");
		sb.append(uploadFile.getName());
		sb.append("</a>");
		sb.append("<br>");
	}

	public String getFileShowFileName() {
		return fileShowFileName;
	}

	public void setFileShowFileName(String fileShowFileName) {
		this.fileShowFileName = fileShowFileName;
	}

}
