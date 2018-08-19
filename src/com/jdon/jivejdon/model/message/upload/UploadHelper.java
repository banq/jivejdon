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

import java.util.ArrayList;
import java.util.List;

/**
 * @author banq(http://www.jdon.com)
 * 
 */
public class UploadHelper {
	protected List imagesTypes;

	private List othersTypes;

	private List fileTypes;

	public UploadHelper() {
		imagesTypes = new ArrayList(5);
		imagesTypes.add("gif");
		imagesTypes.add("jpg");
		imagesTypes.add("png");

		othersTypes = new ArrayList(10);
		othersTypes.add("rar");
		othersTypes.add("zip");
		othersTypes.add("swf");
		othersTypes.add("wmv");
		othersTypes.add("mp3");
		othersTypes.add("txt");
		othersTypes.add("doc");
		othersTypes.add("xls");
		othersTypes.add("xml");
		othersTypes.add("ppt");

		fileTypes = new ArrayList(20);
		fileTypes.addAll(imagesTypes);
		fileTypes.addAll(othersTypes);
	}

	public boolean isImage(String fileName) {
		int extInt = fileName.lastIndexOf(".");
		String ext = fileName.substring(extInt + 1);
		if (imagesTypes.contains(ext))
			return true;
		else
			return false;
	}

	public String subImageName(String fileName) {
		int extInt = fileName.lastIndexOf(".");
		String extname = fileName.substring(extInt + 1);
		String prename = fileName.substring(0, extInt);
		if (prename.length() > 6) {
			prename = prename.substring(0, 6);
		}
		return prename + "." + extname;

	}

	public boolean isOthersTypes(String fileName) {
		int extInt = fileName.lastIndexOf(".");
		String ext = fileName.substring(extInt + 1);
		if (othersTypes.contains(ext))
			return true;
		else
			return false;
	}

	public boolean canBeUpload(String fileName) {
		int extInt = fileName.lastIndexOf(".");
		String ext = fileName.substring(extInt + 1);
		if (fileTypes.contains(ext))
			return true;
		else
			return false;
	}

	/**
	 * @return Returns the fileTypes.
	 */
	public List getFileTypes() {
		return fileTypes;
	}

	/**
	 * @param fileTypes
	 *            The fileTypes to set.
	 */
	public void setFileTypes(List fileTypes) {
		this.fileTypes = fileTypes;
	}

	/**
	 * @return Returns the imagesTypes.
	 */
	public List getImagesTypes() {
		return imagesTypes;
	}

	/**
	 * @param imagesTypes
	 *            The imagesTypes to set.
	 */
	public void setImagesTypes(List imagesTypes) {
		this.imagesTypes = imagesTypes;
	}

	/**
	 * @return Returns the othersTypes.
	 */
	public List getOthersTypes() {
		return othersTypes;
	}

	/**
	 * @param othersTypes
	 *            The othersTypes to set.
	 */
	public void setOthersTypes(List othersTypes) {
		this.othersTypes = othersTypes;
	}
}
