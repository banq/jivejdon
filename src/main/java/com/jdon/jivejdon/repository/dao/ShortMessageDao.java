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
package com.jdon.jivejdon.repository.dao;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.account.Account;
import com.jdon.jivejdon.model.shortmessage.ShortMessage;

/**
 * ShortMessageDao.java
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * CreateData: 2008-5-21
 * </p>
 * 
 * @author GeXinying
 * @version 1.0
 */
public interface ShortMessageDao {

	/**
	 * 
	 * @param key
	 * @return
	 */
	public ShortMessage findShortMessage(Long key);

	/**
	 * 
	 * @param shortMessage
	 * @throws Exception
	 */
	public void sendShortMessage(ShortMessage shortMessage) throws Exception;

	/**
	 * 
	 * @param start
	 * @param count
	 * @return
	 */
	public PageIterator getShortMessages(int start, int count, Long userId);

	/**
	 * 
	 * @param start
	 * @param count
	 * @param account
	 * @return
	 */
	public PageIterator getReceiveShortMessages(int start, int count, Account account);

	/**
	 * 
	 * @param start
	 * @param count
	 * @param account
	 * @return
	 */
	public PageIterator getSendShortMessages(int start, int count, Account account);

	/**
	 * 
	 * @param start
	 * @param count
	 * @param account
	 * @return
	 */
	public PageIterator getSaveShortMessages(int start, int count, Account account);

	/**
	 * 删除
	 * 
	 * @param msgId
	 * @return
	 * @throws Exception
	 */
	public void deleteShortMessage(Long msgId) throws Exception;

	void deleteUserAllShortMessage(String userId) throws Exception;

	void deleteUserRecAllShortMessage(String username) throws Exception;

	/**
	 * 更新消息 草稿箱的
	 * 
	 * @param msg
	 * @throws Exception
	 */
	public void updateShortMessate(ShortMessage msg) throws Exception;

}
