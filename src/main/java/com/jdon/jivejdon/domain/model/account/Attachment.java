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
package com.jdon.jivejdon.domain.model.account;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.domain.model.attachment.UploadFile;
import com.jdon.jivejdon.domain.model.message.upload.UploadLazyLoader;
import com.jdon.jivejdon.domain.model.util.LazyLoader;

public class Attachment extends LazyLoader {

	private final long accountId;

	private final UploadLazyLoader uploadLazyLoader;

	private volatile UploadFile uploadFile;

	public Attachment(long accountId, UploadLazyLoader uploadLazyLoader) {
		super();
		this.accountId = accountId;
		this.uploadLazyLoader = uploadLazyLoader;
	}

	/**
	 * twice times called.
	 * 
	 * * <logic:notEmpty name="forumMessage" property="account.uploadFile"> <img
	 * src=
	 * "<%=request.getContextPath() %>/img/uploadShowAction.shtml?oid=<bean:write name="
	 * forumMessage
	 * " property="account.userId"/>&id=<bean:write name="forumMessage
	 * " property="account.uploadFile.id"/>" border='0' /> </logic:notEmpty>
	 * 
	 * 
	 * @return
	 */
	public UploadFile getUploadFile() {
		if (uploadFile == null) {
			if (this.domainMessage == null && uploadLazyLoader != null) {
				super.preload();
			} else if (this.domainMessage != null) {
				uploadFile = super.loadResult().map(value -> (UploadFile) value).orElse(null);
			}
		}
		return uploadFile;
	}

	public void updateUploadFile() {
		this.domainMessage = uploadLazyLoader.loadUploadFile(Long.toString(this.accountId));
	}

	@Override
	public DomainMessage getDomainMessage() {
		return uploadLazyLoader.loadUploadFile(Long.toString(this.accountId));
	}

	public void setUploadFile(UploadFile uploadFile) {
		this.uploadFile = uploadFile;
	}

}
