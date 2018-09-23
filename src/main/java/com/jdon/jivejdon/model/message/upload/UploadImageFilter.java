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
public class UploadImageFilter implements MessageRenderSpecification {
	private final static String module = UploadImageFilter.class.getName();
	// this imageShowUrl must modified if jivejdon is not default web name
	private String imageShowUrl = "/jivejdon/img";
	private String imageShowInHtmlUrl = "/jivejdon/imageShowInHtml.jsp";
	private String thumbpics = "/simgs/thumb/1.jpg,/simgs/thumb/2.jpg";

	private UploadHelper uploadHelper = new UploadHelper();

	public String getImageShowFileName() {
		return imageShowUrl;
	}

	public void setImageShowFileName(String imageShowFileName) {
		imageShowUrl = imageShowFileName;
	}

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
	 * @param str
	 * @return
	 */
	public String appendTags(ForumMessage message) {
		MessageVO messageVO = message.getMessageVO();
		String str = messageVO.getBody();
		Collection uploadFiles = message.getAttachment().getUploadFiles();
		if ((uploadFiles == null) || (uploadFiles.size() == 0)) {
			if (messageVO.getThumbnailUrl() == null || messageVO.getThumbnailUrl().length() == 0)
				messageVO.setThumbnailUrl(this.getRandomDefaulThumb());
			return str;
		}

		Iterator iter2 = uploadFiles.iterator();
		if (iter2.hasNext()) {
			UploadFile uploadFile = (UploadFile) iter2.next();
			messageVO.setThumbnailUrl(imageShowUrl + "/" + uploadFile.getId() + "/" + uploadFile.getOid());
		}

		if (str.indexOf("[img index=") != -1) {
			str = processImgEmbed(uploadFiles, str);
			return str;
		} else {
			StringBuilder sb = new StringBuilder(str);
			Iterator iter = uploadFiles.iterator();
			while (iter.hasNext()) {
				UploadFile uploadFile = (UploadFile) iter.next();
				// uploadFile.preloadData();
				if (uploadHelper.isImage(uploadFile.getName())) {
					sb.append(appendImgShowURL(uploadFile));
				}
			}
			return sb.toString();
		}

	}

	// see com.jdon.jivejdon.presentation.action.UploadShowAction
	private String appendImgShowURL(UploadFile uploadFile) {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("\n<p>").append(uploadFile.getDescription());
			sb.append("<br><a href='").append(imageShowInHtmlUrl).append("?id=").append(uploadFile.getId());
			sb.append("&oid=").append(uploadFile.getOid());
			sb.append("' target=_blank>");
			sb.append("<img src=\"");
			sb.append(imageShowUrl);
			//sb.append("?id=").append(uploadFile.getId());
			//sb.append("&oid=").append(uploadFile.getOid());
			sb.append("/").append(uploadFile.getId());
			sb.append("/").append(uploadFile.getOid());			
			sb.append("\" border='0' >");
			sb.append("</a></p>\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	private String processImgEmbed(Collection uploadFiles, String message) {
		String finalStr = message;

		int[] index = { 1, 2, 3 };
		for (int i : index) {
			int p = message.indexOf("[img index=" + i + "]");
			try {
				if (p != -1) {
					UploadFile uploadFile = getUpload(uploadFiles, i);
					if (uploadFile != null && uploadHelper.isImage(uploadFile.getName()))
						finalStr = finalStr.replace("[img index=" + i + "]", appendImgShowURL(uploadFile));
				}
			} catch (IndexOutOfBoundsException e) {
				finalStr = finalStr.replace("[img index=" + i + "]", "");
			}
		}
		return finalStr;
	}

	private UploadFile getUpload(Collection uploadFiles, int index) {
		int i = 1;
		for (Object o : uploadFiles) {
			UploadFile uploadFile = (UploadFile) o;
			if (i == index)
				return uploadFile;
			i++;
		}
		return null;
	}

	private String getRandomDefaulThumb() {
		String[] thumbs = thumbpics.split(",");
		if (thumbs == null || thumbs.length == 0) {
			Debug.logError("please add default thumbs UploadImageFilter", module);
			return "please add default thumbs UploadImageFilter";
		}

		int i = (int) (Math.random() * thumbs.length);
		return thumbs[i];

	}

	public String getThumbpics() {
		return thumbpics;
	}

	public void setThumbpics(String thumbpics) {
		this.thumbpics = thumbpics;
	}

	public String getImageShowUrl() {
		return imageShowUrl;
	}

	public void setImageShowUrl(String imageShowUrl) {
		this.imageShowUrl = imageShowUrl;
	}

	public String getImageShowInHtmlUrl() {
		return imageShowInHtmlUrl;
	}

	public void setImageShowInHtmlUrl(String imageShowInHtmlUrl) {
		this.imageShowInHtmlUrl = imageShowInHtmlUrl;
	}

}
