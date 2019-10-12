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
package com.jdon.jivejdon.service.property;

import com.jdon.container.visitor.data.SessionContext;
import com.jdon.controller.events.EventModel;
import com.jdon.jivejdon.model.attachment.UploadFile;

import java.util.Collection;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public interface UploadService {

	// get all uploadFiles include DB and Session
	Collection getAllUploadFiles(Long messageId);

	// get all uploadFiles only in Session that is work station
	Collection getAllUploadFiles(SessionContext sessionContext);

	Collection loadAllUploadFilesOfMessage(Long messageId, SessionContext sessionContext);

	void clearSession(SessionContext sessionContext);

	// get a uploadFile , lazy is true, the uploadFile's data not be load from
	// db
	UploadFile getUploadFile(String objectId);

	// save a uploadFile in Session
	void saveUploadFile(EventModel em);

	// save and update a uploadFile in persistence
	void saveAccountFaceFile(EventModel em);

	// delete a uploadFile from session
	void removeUploadFile(EventModel em);

}
