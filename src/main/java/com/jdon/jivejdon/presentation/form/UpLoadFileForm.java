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
package com.jdon.jivejdon.presentation.form;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

import com.jdon.jivejdon.domain.model.message.upload.UploadHelper;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class UpLoadFileForm extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UploadHelper uploadHelper;

	private String id;

	private String description;

	private FormFile theFile;

	private String parentName;

	private String parentId;

	private String tempId;

	private String path;

	private boolean authenticated = true;

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public UpLoadFileForm() {
		this.uploadHelper = new UploadHelper();
	}

	public String getId() {
		return id;
	}

	public FormFile getTheFile() {
		return theFile;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Check to make sure the client hasn't exceeded the maximum allowed upload
	 * size inside of this validate method.
	 */
	public void doValidate(ActionMapping mapping, HttpServletRequest request, List errors) {

		// has the maximum length been exceeded?
		Boolean maxLengthExceeded = (Boolean) request.getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);
		if ((maxLengthExceeded != null) && (maxLengthExceeded.booleanValue())) {

			errors.add("exceed the upload max length");

		} else if (theFile != null) {
			// retrieve the file name
			String fileName = theFile.getFileName();
			if (!uploadHelper.canBeUpload(fileName))
				errors.add(new ActionMessage("illegal file type! "));
		}

	}

	public String getName() {
		if (theFile != null)
			return theFile.getFileName();
		return "";
	}

	public int getSize() {
		try {
			if (theFile != null) {
				int size = theFile.getFileSize();
				return size / 1000;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public byte[] getData() {
		try {
			if (theFile != null)
				return theFile.getFileData();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return Returns the contentType.
	 */
	public String getContentType() {
		try {
			if (theFile != null) {
				return theFile.getContentType();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "html/text";
	}

	/**
	 * @return Returns the fileTypes.
	 */
	public List getFileTypes() {
		return uploadHelper.getFileTypes();
	}

	/**
	 * @return Returns the imagesTypes.
	 */
	public List getImagesTypes() {
		return uploadHelper.getImagesTypes();
	}

}
