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
package com.jdon.jivejdon.domain.model.attachment;

import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.message.upload.UploadHelper;

/**
 * @author banq(http://www.jdon.com)
 * 
 */

public class UploadFile extends com.jdon.strutsutil.file.UploadFile implements Comparable {
	private static final long serialVersionUID = 1L;

	private UploadHelper uploadHelper;

	// private UploadCacheMonitored ucm;

	public UploadFile() {
		this.uploadHelper = new UploadHelper();
		// this.ucm = new UploadCacheMonitored();
	}

	public void addMonitor(ForumMessage message) {
		// this.ucm.addObserver(message);
	}

	public boolean isImage() {
		return uploadHelper.isImage(this.getName());

	}

	public byte[] getData(String oid) {
		if (!isValid(Integer.parseInt(oid)))
			return null;
		return getData();
	}

	public boolean isValid(int oid) {
		if (oid == Integer.parseInt(this.getParentId())) {
			return true;
		}
		return false;
	}

	// // parent id
	public int getOid() {
		try {
			return Integer.parseInt(this.getParentId());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public int compareTo(Object comparesup) {
		long compareid = Long.parseLong(((UploadFile) comparesup).getId());
		long thisid = Long.parseLong(this.getId());
		return Long.compare(thisid, compareid);

	}
}
