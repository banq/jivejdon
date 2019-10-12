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
package com.jdon.jivejdon.repository.property;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.attachment.UploadFile;

import java.util.Collection;

public interface UploadRepository {

	public abstract Collection getUploadFiles(String parentId);

	public abstract UploadFile getSingleUploadFile(String parentId);

	public abstract PageIterator getUploadFiles(int start, int count);

	public abstract UploadFile getUploadFile(String objectId);

	public abstract void saveAllUploadFiles(String parentId, Collection uploads) throws Exception;

	public abstract void deleteAllUploadFiles(String parentId);

	public abstract void deleteUploadFile(String objectId);

	public void saveUpload(UploadFile uploadFile) throws Exception;
}