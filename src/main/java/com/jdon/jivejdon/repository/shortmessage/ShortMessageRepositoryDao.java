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
package com.jdon.jivejdon.repository.shortmessage;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.account.Account;
import com.jdon.jivejdon.model.shortmessage.ShortMessage;
import com.jdon.jivejdon.repository.dao.ShortMessageDao;

/**
 * ShortMessageRepository.java
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * CreateData: Jun 6, 2008
 * </p>
 * 
 * @author GeXinying
 * @version 1.0
 */
public class ShortMessageRepositoryDao implements ShortMessageRepository {

	private ShortMessageDao shortMessageDao;

	public ShortMessageRepositoryDao(ShortMessageDao shortMessageDao) {
		this.shortMessageDao = shortMessageDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.shortmessage.ShortMessageRepository#updateShortMessage
	 * (com.jdon.jivejdon.model.shortmessage.ShortMessage)
	 */
	public void updateShortMessage(ShortMessage msg) throws Exception {
		this.shortMessageDao.updateShortMessate(msg);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.shortmessage.ShortMessageRepository#findTheUser(java.
	 * lang.String)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.shortmessage.ShortMessageRepository#deleteShortMessage
	 * (com.jdon.jivejdon.model.shortmessage.ShortMessage)
	 */
	public void deleteShortMessage(ShortMessage msg) throws Exception {
		this.shortMessageDao.deleteShortMessage(msg.getMsgId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.shortmessage.ShortMessageRepository#deleteUserAllShortMessage
	 * (java.lang.String)
	 */
	public void deleteUserAllShortMessage(String userId) throws Exception {
		this.shortMessageDao.deleteUserAllShortMessage(userId);
	}

	public void deleteUserRecAllShortMessage(String username) throws Exception {
		this.shortMessageDao.deleteUserRecAllShortMessage(username);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.shortmessage.ShortMessageRepository#findShortMessage(
	 * java.lang.Long)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.shortmessage.ShortMessageRepository#getShortMessages(int,
	 * int, java.lang.Long)
	 */
	public PageIterator getShortMessages(int start, int count, Long userId) {
		// TODO Auto-generated method stub
		return this.shortMessageDao.getShortMessages(start, count, userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.shortmessage.ShortMessageRepository#getReceiveShortMessages
	 * (int, int, com.jdon.jivejdon.model.account.Account)
	 */
	public PageIterator getReceiveShortMessages(int start, int count, Account account) {
		// TODO Auto-generated method stub
		return this.shortMessageDao.getReceiveShortMessages(start, count, account);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.shortmessage.ShortMessageRepository#getSendShortMessages
	 * (int, int, com.jdon.jivejdon.model.account.Account)
	 */
	public PageIterator getSendShortMessages(int start, int count, Account account) {
		// TODO Auto-generated method stub
		return this.shortMessageDao.getSendShortMessages(start, count, account);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.shortmessage.ShortMessageRepository#getSaveShortMessages
	 * (int, int, com.jdon.jivejdon.model.account.Account)
	 */
	public PageIterator getSaveShortMessages(int start, int count, Account account) {
		// TODO Auto-generated method stub
		return this.shortMessageDao.getSaveShortMessages(start, count, account);
	}

}
