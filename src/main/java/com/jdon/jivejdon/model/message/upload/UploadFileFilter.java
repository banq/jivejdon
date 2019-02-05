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

import com.jdon.jivejdon.model.attachment.UploadFile;
import com.jdon.jivejdon.model.message.MessageVO;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

/**
 * @author banq
 */
public class UploadFileFilter implements Function<MessageVO, MessageVO> {
	public final static String DOWNLOAD_NAME = "dlname";
	private final static String module = UploadFileFilter.class.getName();
	// want ot modify it in admin
	private String fileShowFileName = "../../uploadDL.jsp";

	public MessageVO apply(MessageVO messageVO) {
		String newBody = appendTags(messageVO, messageVO.getForumMessage().getAttachment()
				.getUploadFiles());
		return messageVO.builder().subject(messageVO.getSubject()).body(newBody)
				.build();
	}


	/**
	 * return the String:
	 * <p>
	 * [img]/imageShow.jsp?id=3464[/img]
	 *
	 */
	public String appendTags(MessageVO messageVO, Collection uploadFiles) {
		String str = messageVO.getBody();
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
