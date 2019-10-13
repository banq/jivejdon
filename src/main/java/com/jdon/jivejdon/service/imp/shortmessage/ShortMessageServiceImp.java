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
package com.jdon.jivejdon.service.imp.shortmessage;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.Component;
import com.jdon.annotation.intercept.Poolable;
import com.jdon.annotation.intercept.SessionContextAcceptable;
import com.jdon.annotation.model.OnEvent;
import com.jdon.container.visitor.data.SessionContext;
import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.component.shortmessage.ShortMessageFactory;
import com.jdon.jivejdon.model.account.Account;
import com.jdon.jivejdon.model.shortmessage.FromShortMessage;
import com.jdon.jivejdon.model.shortmessage.ShortMessage;
import com.jdon.jivejdon.model.shortmessage.ToShortMessage;
import com.jdon.jivejdon.repository.acccount.AccountFactory;
import com.jdon.jivejdon.repository.shortmessage.ShortMessageRepository;
import com.jdon.jivejdon.repository.builder.ForumAbstractFactory;
import com.jdon.jivejdon.service.shortmessage.ShortMessageService;
import com.jdon.jivejdon.service.util.SessionContextUtil;

/**
 * ShortMessageServiceImp.java
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
 * @author GeXinying banq
 * @version 1.0
 */
@Poolable
@Component("shortMessageService")
public class ShortMessageServiceImp implements ShortMessageService {
	private final static Logger logger = LogManager.getLogger(ShortMessageServiceImp.class);

	private SessionContext sessionContext;

	private final SessionContextUtil sessionContextUtil;

	protected final ShortMessageRepository repository;

	protected final ShortMessageFactory factory;

	protected final AccountFactory accountFactory;

	protected final ForumAbstractFactory forumAbstractFactory;

	public ShortMessageServiceImp(SessionContextUtil sessionContextUtil, ShortMessageRepository repository, ShortMessageFactory factory,
			AccountFactory accountFactory, ForumAbstractFactory forumAbstractFactory) {
		this.sessionContextUtil = sessionContextUtil;
		this.repository = repository;
		this.factory = factory;
		this.accountFactory = accountFactory;
		this.forumAbstractFactory = forumAbstractFactory;
	}

	/**
	 * 发送草稿箱中的消息
	 * 
	 * @throws Exception
	 * 
	 */
	public void sendInDraftMessage(EventModel em) {
		logger.debug("ShortMessageServiceImp");
		try {
			FromShortMessage msg = (FromShortMessage) em.getModelIF();
			if (userIsExists(msg.getMessageTo()) == null)
				throw new Exception("No such user!");
			// 这里发送的是已经写的消息，这里只是更新表中的数据
			msg.getShortMessageState().setHasSent(true);
			this.repository.updateShortMessage(msg);
		} catch (Exception e) {
			em.setErrors("发送的对象不存在，请检查拼写！");
		}
	}

	/**
	 * 发送草稿箱中的消息
	 * 
	 * @throws Exception
	 * 
	 */
	public void saveInDraftMessage(EventModel em) {
		logger.debug("ShortMessageServiceImp");
		try {
			FromShortMessage msg = (FromShortMessage) em.getModelIF();
			if (userIsExists(msg.getMessageTo()) == null)
				throw new Exception("No such user!");
			// 这里发送的是已经写的消息，这里只是更新表中的数据
			this.repository.updateShortMessage(msg);
		} catch (Exception e) {
			em.setErrors("发送的对象不存在，请检查拼写！");
		}
	}

	/**
	 * 删除草稿箱中的消息
	 * 
	 * @throws Exception
	 * 
	 */
	public void deleInDraftMessage(EventModel em) {
		logger.debug("ShortMessageServiceImp");
		try {
			deleteShortMessage(em);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			em.setErrors("删除操作出错！");
		}
	}

	/**
	 * 发送 新写的 消息 这是往表里增加新的数据
	 * 
	 */
	public void sendShortMessage(EventModel em) {
		logger.debug("ShortMessageServiceImp");
		try {
			FromShortMessage msg = (FromShortMessage) em.getModelIF();
			if (userIsExists(msg.getMessageTo()) == null)
				throw new Exception("No such user!");
			Account account = this.sessionContextUtil.getLoginAccount(sessionContext);
			msg.setAccount(account);
			msg.setMessageFrom(account.getUsername());
			if (!this.factory.sendShortMessage(msg))
				em.setErrors("shormessage.exceed.max");
		} catch (Exception e) {
			em.setErrors("errors.nouser");
		}
	}

	/**
	 * 保存消息
	 */
	public void saveShortMessage(EventModel em) {
		logger.debug("service: saveShortMessage()");
		try {
			FromShortMessage msg = (FromShortMessage) em.getModelIF();
			if (userIsExists(msg.getMessageTo()) == null)
				throw new Exception("No such user!");
			Account account = this.sessionContextUtil.getLoginAccount(sessionContext);
			msg.setAccount(account);
			if (!this.factory.saveShortMessage(msg))
				em.setErrors("shormessage.exceed.max");
		} catch (Exception e) {
			em.setErrors("errors.nouser");
		}
	}

	/**
	 * 
	 * @param userId
	 * @throws Exception
	 */
	private Account userIsExists(String userName) throws Exception {
		return factory.findTheUser(userName);
	}

	/**
	 * 删除消息
	 * 
	 * @throws Exception
	 */
	public void deleteShortMessage(EventModel em) throws Exception {
		logger.debug("deleteShortMessage");
		ShortMessage smf = (FromShortMessage) em.getModelIF();
		logger.debug("" + smf.getMsgId());
		smf = this.factory.findShortMessage(smf.getMsgId());
		Account loginaccount = this.sessionContextUtil.getLoginAccount(sessionContext);
		// check auth
		if ((smf.getMessageFrom().equals(loginaccount.getUsername())) || (smf.getMessageTo().equals(loginaccount.getUsername()))) {
			this.repository.deleteShortMessage(smf);
			loginaccount.reloadAccountSMState();
		}
	}

	public void deleteUserAllShortMessage() throws Exception {
		logger.debug("deleteShortMessage");
		Account loginaccount = this.sessionContextUtil.getLoginAccount(sessionContext);
		this.repository.deleteUserAllShortMessage(loginaccount.getUserId());
		loginaccount.reloadAccountSMState();
	}

	public void deleteUserRecAllShortMessage() throws Exception {
		logger.debug("deleteUserRecAllShortMessage");
		Account loginaccount = this.sessionContextUtil.getLoginAccount(sessionContext);
		this.repository.deleteUserRecAllShortMessage(loginaccount.getUsername());
		loginaccount.reloadAccountSMState();
	}

	/**
	 * 编辑消息
	 */
	public void updateShortMessage(EventModel em) {
		logger.debug("ShortMessageServiceImp");

	}

	/**
	 * 查找消息 根据PageIterator结果进行查找
	 */
	public FromShortMessage getFromShortMessage(Long msgId) {
		logger.debug("ShortMessageServiceImp getFromShortMessage");
		ShortMessage smsg = this.factory.findShortMessage(msgId);
		Account loginaccount = this.sessionContextUtil.getLoginAccount(sessionContext);
		// check auth
		if (!smsg.getMessageFrom().equals(loginaccount.getUsername())) {
			logger.error("msgId=" + msgId + " is not this user's =" + loginaccount.getUserId());
			return null;
		}
		FromShortMessage fromsmsg = new FromShortMessage();
		try {
			PropertyUtils.copyProperties(fromsmsg, smsg);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fromsmsg;
	}

	public ToShortMessage getToShortMessage(Long msgId) {
		logger.debug("ShortMessageServiceImp getToShortMessage");
		ShortMessage smsg = this.factory.findShortMessage(msgId);
		Account loginaccount = this.sessionContextUtil.getLoginAccount(sessionContext);
		// check auth
		if (!smsg.getMessageTo().equals(loginaccount.getUsername())) {
			logger.error("msgId=" + msgId + " is not this user's =" + loginaccount.getUserId());
			return null;
		}
		ToShortMessage tosmsg = new ToShortMessage();
		try {
			PropertyUtils.copyProperties(tosmsg, smsg);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tosmsg;
	}

	public ToShortMessage initShortMessage(EventModel em) {
		logger.debug("enter initShortMessage");
		ToShortMessage smf = (ToShortMessage) em.getModelIF();
		if (smf.getMsgId() == null) {
			return smf;
		}
		String sendTo = smf.getMessageFrom();
		smf.setMessageTo(sendTo);
		smf.setMessageBody("");
		return smf;
	}

	/**
	 * 
	 */
	public PageIterator getShortMessages(int start, int count) {
		PageIterator pageIterator = new PageIterator();
		Account loginaccount = this.sessionContextUtil.getLoginAccount(sessionContext);
		try {
			pageIterator = this.repository.getShortMessages(start, count, loginaccount.getUserIdLong());
		} catch (Exception ex) {
			logger.error(ex);
		}
		return pageIterator;
	}

	/**
	 * 获取接收的消息
	 */
	public PageIterator getReceiveShortMessages(int start, int count) {
		Account account = sessionContextUtil.getLoginAccount(sessionContext);
		PageIterator pageIterator = new PageIterator();
		try {
			pageIterator = this.repository.getReceiveShortMessages(start, count, account);
		} catch (Exception ex) {
			logger.error(ex);
		}
		return pageIterator;
	}

	// accept pubsub from LazyLoaderRole's @Send("loadNewShortMessageCount")
	@OnEvent("loadNewShortMessageCount")
	public int loadNewShortMessageCount(String userId) {
		Account account = this.accountFactory.getFullAccount(userId);
		int count = checkReceiveShortMessages(account);
		return count;
	}

	public int checkReceiveShortMessages() {
		int count = 0;
		Account account = sessionContextUtil.getLoginAccount(sessionContext);
		if (account == null)
			return count;

		count = checkReceiveShortMessages(account);
		return count;
	}

	public int checkReceiveShortMessages(Account account) {
		int count = 0;
		try {
			PageIterator pi = this.repository.getReceiveShortMessages(0, 50, account);
			if ((pi == null) || (pi.getAllCount() == 0))
				return count;
			while (pi.hasNext()) {
				Long msgId = (Long) pi.next();
				ShortMessage shortMessage = this.factory.findShortMessage(msgId);
				if (shortMessage == null)
					return count;
				if (!shortMessage.getShortMessageState().isHasRead()) {
					count++;
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
		return count;
	}

	/**
	 * 获取没有发送的消息
	 */
	public PageIterator getSaveShortMessages(int start, int count) {
		Account account = sessionContextUtil.getLoginAccount(sessionContext);
		PageIterator pageIterator = new PageIterator();
		try {
			pageIterator = this.repository.getSaveShortMessages(start, count, account);
		} catch (Exception ex) {
			logger.error(ex);
		}
		return pageIterator;
	}

	/**
	 * 获取已发送的消息
	 */
	public PageIterator getSendShortMessages(int start, int count) {
		Account account = sessionContextUtil.getLoginAccount(sessionContext);
		PageIterator pageIterator = new PageIterator();
		try {
			pageIterator = this.repository.getSendShortMessages(start, count, account);
		} catch (Exception ex) {
			logger.error(ex);
		}
		return pageIterator;
	}

	/**
	 * @return Returns the sessionContext.
	 */
	public SessionContext getSessionContext() {
		return sessionContext;
	}

	/**
	 * @param sessionContext
	 *            The sessionContext to set.
	 */
	@SessionContextAcceptable
	public void setSessionContext(SessionContext sessionContext) {
		logger.debug(":void setSessionContext(SessionContext sessionContext)");
		this.sessionContext = sessionContext;
	}

}
