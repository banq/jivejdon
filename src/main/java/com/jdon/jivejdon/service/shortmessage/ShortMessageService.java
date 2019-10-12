/*
 * Copyright (c) 2008 Ge Xinying
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jdon.jivejdon.service.shortmessage;

import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.shortmessage.FromShortMessage;
import com.jdon.jivejdon.model.shortmessage.ToShortMessage;

/**
 * ShortMessageService.java
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * CreateData: 2008-5-20
 * </p>
 * 
 * @author GeXinying
 * @version 1.0
 */
public interface ShortMessageService {
	/**
	 * 草稿箱中发送信息
	 * 
	 * @param em
	 * @throws Exception
	 */
	void sendInDraftMessage(EventModel em) throws Exception;

	/**
	 * 发送草稿箱中的消息
	 * 
	 * @throws Exception
	 * 
	 */
	public void saveInDraftMessage(EventModel em) throws Exception;

	/**
	 * 删除草稿箱中的消息
	 * 
	 * @throws Exception
	 * 
	 */
	public void deleInDraftMessage(EventModel em) throws Exception;

	/**
	 * 
	 * @param em
	 * @return
	 */
	ToShortMessage initShortMessage(EventModel em);

	/**
	 * 
	 * @param msgId
	 * @return
	 */
	FromShortMessage getFromShortMessage(Long msgId);

	ToShortMessage getToShortMessage(Long msgId);

	/**
	 * 
	 * @param em
	 * @throws Exception
	 */
	void sendShortMessage(EventModel em) throws Exception;

	/**
	 * 
	 * @param em
	 * @throws Exception
	 */
	void saveShortMessage(EventModel em) throws Exception;

	/**
	 * 
	 * @param em
	 */
	void updateShortMessage(EventModel em);

	/**
	 * 
	 * @param em
	 * @throws Exception
	 */
	void deleteShortMessage(EventModel em) throws Exception;

	void deleteUserAllShortMessage() throws Exception;

	void deleteUserRecAllShortMessage() throws Exception;

	/**
	 * 
	 * @param start
	 * @param count
	 * @return
	 */
	PageIterator getShortMessages(int start, int count);

	/**
	 * 
	 * @param start
	 * @param count
	 * @return
	 */
	PageIterator getReceiveShortMessages(int start, int count);

	int checkReceiveShortMessages();

	/**
	 * 
	 * @param start
	 * @param count
	 * @return
	 */
	PageIterator getSendShortMessages(int start, int count);

	/**
	 * 
	 * @param start
	 * @param count
	 * @return
	 */
	PageIterator getSaveShortMessages(int start, int count);
}
