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
package com.jdon.jivejdon.infrastructure.component.shortmessage;

import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

import com.jdon.annotation.Component;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.shortmessage.ShortMessage;
import com.jdon.jivejdon.domain.model.shortmessage.ShortMessageState;
import com.jdon.jivejdon.domain.model.event.ATUserNotifiedEvent;
import com.jdon.jivejdon.infrastructure.repository.acccount.AccountFactory;
import com.jdon.jivejdon.infrastructure.repository.shortmessage.ShortMessageRepository;
import com.jdon.jivejdon.infrastructure.repository.dao.SequenceDao;
import com.jdon.jivejdon.infrastructure.repository.dao.ShortMessageDao;

/**
 * ShortMessageFactory.java
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
 * @author GeXinying banq
 * @version 1.0
 */
@Component("shortMessageFactory")
public class ShortMessageFactory implements Observer {

	private ShortMessageDao shortMessageDao;

	private SequenceDao sequenceDao;

	protected ShortMessageRepository repository;

	protected final AccountFactory accountFactory;

	protected final WeiBoShortMessageParams weiBoShortMessageParams;

	public ShortMessageFactory(ShortMessageDao shortMessageDao, SequenceDao sequenceDao, ShortMessageRepository repository,
			AccountFactory accountFactory, WeiBoShortMessageParams weiBoShortMessageParams) {
		this.shortMessageDao = shortMessageDao;
		this.sequenceDao = sequenceDao;
		this.repository = repository;
		this.accountFactory = accountFactory;
		this.weiBoShortMessageParams = weiBoShortMessageParams;
	}

	public boolean sendShortMessage(ShortMessage msg) throws Exception {
		if (!msg.isSatify(getMessageCount(msg)))
			return false;
		try {
			msg = composite(msg);
			msg.setHasSent(true);
			notifyTargetAccount(msg);
			this.shortMessageDao.sendShortMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void notifyTargetAccount(ShortMessage msg) {
		Account targetAccount = new Account();
		targetAccount.setUsername(msg.getMessageTo());
		targetAccount = accountFactory.getFullAccount(targetAccount);
		targetAccount.addOneNewMessage(1);
	}

	public int getNewShortMessageCount(ShortMessage msg) {
		Account targetAccount = new Account();
		targetAccount.setUsername(msg.getMessageTo());
		targetAccount = accountFactory.getFullAccount(targetAccount);
		return targetAccount.getNewShortMessageCount();
	}

	public boolean saveShortMessage(ShortMessage msg) throws Exception {
		if (!msg.isSatify(getMessageCount(msg)))
			return false;
		msg = composite(msg);
		this.shortMessageDao.sendShortMessage(msg);
		return true;
	}

	private ShortMessage composite(ShortMessage msg) throws Exception {
		long dateTime = System.currentTimeMillis();
		try {
			Long key = this.sequenceDao.getNextId(Constants.SHORTMESSAGE);
			ShortMessageState state = new ShortMessageState();
			state.setSendTime(Long.toString(dateTime));
			msg.setMsgId(key);
			msg.setShortMessageState(state);
		} catch (SQLException e) {
			throw new Exception(e);
		}
		return msg;
	}

	private int getMessageCount(ShortMessage msg) {
		PageIterator pageIterator = this.repository.getShortMessages(0, 10, msg.getAccount().getUserIdLong());
		return pageIterator.getAllCount();
	}

	public ShortMessage findShortMessage(Long msgId) {
		ShortMessage sm = this.shortMessageDao.findShortMessage(msgId);
		Account account = accountFactory.getFullAccount(sm.getAccount());
		sm.setAccount(account);

		account = findTheUser(sm.getMessageTo());
		account.addMessageObservable(sm.getShortMessageState());
		sm.getShortMessageState().addObserver(this);

		return sm;
	}

	public Account findTheUser(String userName) {
		Account account = new Account();
		account.setUsername(userName);
		return accountFactory.getFullAccount(account);
	}

	// for observer not open.
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.shortmessage.ShortMessageRepository#update(java.util.
	 * Observable, java.lang.Object)
	 */
	public void update(Observable obj, Object arg) {
		ShortMessageState shortMessageState = (ShortMessageState) obj;
		try {
			this.shortMessageDao.updateShortMessate(shortMessageState.getShortMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createWeiBoShortMessage(ATUserNotifiedEvent event) {
		try {
			for (String tousername : event.getToUsernames()) {
				Account account = findTheUser(tousername);
				if (account != null) {
					ShortMessage msg = new ShortMessage();
					msg.setAccount(event.getFromAccount());
					msg.setMessageFrom(event.getFromAccount().getUsername());
					msg.setMessageTo(account.getUsername());
					msg.setMessageTitle(weiBoShortMessageParams.getSubject());
					msg.setMessageBody(weiBoShortMessageParams.getBody(event));
					this.sendShortMessage(msg);
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
