/*
 * Copyright 2003-2009 the original author or authors.
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
package com.jdon.jivejdon.model.attachment;

import java.util.Collection;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.event.domain.producer.read.LazyLoaderRole;
import com.jdon.jivejdon.model.util.LazyLoader;

/**
 * upload files is a part of aggregation root.
 * 
 * it should be load with messageVO.
 * 
 * @author banq
 * 
 */
public class AttachmentsVO extends LazyLoader {

	private final long messageId;

	private LazyLoaderRole lazyLoaderRole;

	// for upload files lazyload
	private volatile Collection uploadFiles;

	// for read or load
	public AttachmentsVO(long messageId, LazyLoaderRole lazyLoaderRole) {
		super();
		this.messageId = messageId;
		this.lazyLoaderRole = lazyLoaderRole;
	}

	// for be written
	public AttachmentsVO(long messageId, Collection uploadFiles) {
		super();
		this.messageId = messageId;
		this.uploadFiles = uploadFiles;
	}

	public long getMessageId() {
		return messageId;
	}

	public void setUploadFiles(Collection uploadFiles) {
		// this.uploadFiles = uploadFiles;
		this.uploadFiles = uploadFiles;
	}

	public Collection getUploadFiles() {
		if (this.uploadFiles == null && lazyLoaderRole != null) {
			// return result cannot be null, can be a ArrayList that isEmpty()
			// blocked until return result, display attachement lifecycle is
			// same as messageVO;
			this.uploadFiles = (Collection) super.loadBlockedResult();
		}
		return uploadFiles;
	}

	public void preloadUploadFileDatas() {
		if (getUploadFiles() != null) {
			// preload will cause memory expand
			// for (Object o : uploadFiles) {
			// UploadFile uploadFile = (UploadFile) o;
			// uploadFile.preloadData();
			// }
		}
	}

	@Override
	public DomainMessage getDomainMessage() {
		return lazyLoaderRole.loadUploadFiles(Long.toString(messageId));
	}

}
