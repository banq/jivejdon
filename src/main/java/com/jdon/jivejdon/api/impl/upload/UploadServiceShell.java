package com.jdon.jivejdon.api.impl.upload;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.intercept.Poolable;
import com.jdon.annotation.intercept.SessionContextAcceptable;
import com.jdon.container.visitor.data.SessionContext;
import com.jdon.controller.events.EventModel;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.attachment.UploadFile;
import com.jdon.jivejdon.infrastructure.repository.acccount.AccountFactory;
import com.jdon.jivejdon.infrastructure.repository.property.UploadRepository;
import com.jdon.jivejdon.infrastructure.repository.dao.SequenceDao;
import com.jdon.jivejdon.api.property.UploadService;
import com.jdon.util.UtilValidate;

@Poolable
public class UploadServiceShell implements UploadService {
	private final static Logger logger = LogManager.getLogger(UploadServiceShell.class);

	private final static String UPLOAD_NAME = "UPLOAD";

	private final static int UPLOAD_QTY = 3;

	protected final UploadRepository uploadRepository;

	protected final AccountFactory accountFactory;

	private final SequenceDao sequenceDao;

	protected SessionContext sc;

	public UploadServiceShell(UploadRepository uploadRepository, AccountFactory accountFactory, SequenceDao sequenceDao) {
		this.uploadRepository = uploadRepository;
		this.accountFactory = accountFactory;
		this.sequenceDao = sequenceDao;

	}

	/*
	 * save the uploadfile to session becuase the messageId has not produce,
	 * when the message create operation is active, will call saveUploadFileToDB
	 * method
	 */
	public void saveUploadFile(EventModel em) {
		UploadFile uploadFile = (UploadFile) em.getModelIF();
		logger.debug("UploadFile size =" + uploadFile.getSize());

		Long parentId = null;
		if (!UtilValidate.isEmpty(uploadFile.getParentId()))
			parentId = Long.parseLong(uploadFile.getParentId());

		addUploadFileSession(parentId, uploadFile, UPLOAD_QTY);
	}

	protected void addUploadFileSession(Long parentId, UploadFile uploadFile, int maxCount) {
		try {
			Long mIDInt = sequenceDao.getNextId(Constants.OTHERS);
			uploadFile.setId(mIDInt.toString());
			Collection<UploadFile> uploads = getUploadFilesFromSession(this.sc);
			if (uploads == null) {
				initSession(parentId, this.sc);
				uploads = getUploadFilesFromSession(this.sc);
			}

			if (uploads.size() != 0 && uploads.size() >= maxCount) {
				uploads.remove(uploads.size() - 1);
			}
			uploads.add(uploadFile);
		} catch (Exception e) {
			logger.error("addUploadFileSession error:" + e);
		}
	}

	public void saveUploadFileNow(EventModel em) {
		UploadFile uploadFile = (UploadFile) em.getModelIF();
		String pid = uploadFile.getParentId();
		Long parentId = Long.parseLong(pid);
		addUploadFileSession(parentId, uploadFile, 1);
		Collection uploads = getAllUploadFiles(parentId);
		try {
			uploadRepository.saveAllUploadFiles(pid, uploads);
			clearSession();
		} catch (Exception e) {
			em.setErrors(Constants.ERRORS);
		}
		em.setModelIF(uploadFile);
	}

	public void saveAccountFaceFile(EventModel em) {
		UploadFile uploadFile = (UploadFile) em.getModelIF();
		String pid = uploadFile.getParentId();
		Long parentId = Long.parseLong(pid);
		Account account = accountFactory.getFullAccount(pid);
		if (account.isAnonymous())
			return;
		addUploadFileSession(parentId, uploadFile, 1);
		Collection uploads = getAllUploadFiles(parentId);
		try {
			uploadRepository.saveAllUploadFiles(pid, uploads);
			clearSession();
			account.getAttachment().setUploadFile(uploadFile);
		} catch (Exception e) {
			logger.error("saveAccountFaceFile error:" + e);

		}

	}

	public Collection getAllUploadFiles(Long messageId) {
		Collection<UploadFile> uploads = getUploadFilesFromSession(this.sc);
		if (uploads == null) {
			initSession(messageId, this.sc);
			uploads = getUploadFilesFromSession(this.sc);
		}
		return uploads;
	}

	/**
	 * get all UploadFiles include session but not exclude the old
	 */
	public Collection getAllUploadFiles(SessionContext sessionContext) {
		logger.debug(" loadUploadFiles ");
		Collection re = getUploadFilesFromSession(sessionContext);
		return re;
	}

	public Collection loadAllUploadFilesOfMessage(Long messageId, SessionContext sessionContext) {
		logger.debug(" loadUploadFiles ");
		Collection re = getUploadFilesFromSession(sessionContext);
		if (re != null)
			for (Object o : re) {
				UploadFile uploadFile = (UploadFile) o;
				uploadFile.setParentId(Long.toString(messageId));
			}
		return re;
	}

	public void removeUploadFile(EventModel em) {
		logger.debug(" uploadService.removeUploadFile ");
		UploadFile newuploadFile = (UploadFile) em.getModelIF();
		Collection<UploadFile> uploads = getUploadFilesFromSession(this.sc);
		if (uploads == null)
			return;

		Iterator iter = uploads.iterator();
		while (iter.hasNext()) {
			UploadFile uploadFile = (UploadFile) iter.next();
			if (uploadFile.getId().equals(newuploadFile.getId()))
				iter.remove();
		}

	}

	public UploadFile getUploadFile(String objectId) {
		logger.debug("getUploadFile for id=" + objectId);
		UploadFile uf = getUploadFileFromSession(objectId);
		if (uf == null)
			uf = uploadRepository.getUploadFile(objectId);
		return uf;
	}

	private UploadFile getUploadFileFromSession(String objectId) {
		Collection<UploadFile> uploads = getUploadFilesFromSession(this.sc);
		if (uploads == null)
			return null;

		for (UploadFile uploadFile : uploads) {
			if (uploadFile.getId().equals(objectId))
				return uploadFile;
		}

		return null;
	}

	private Collection<UploadFile> getUploadFilesFromSession(SessionContext sessionContext) {
		if (sessionContext == null)
			return null;
		return (Collection) sessionContext.getArrtibute(UPLOAD_NAME);
	}

	private void initSession(Long parentId, SessionContext sessionContext) {
		Collection<UploadFile> uploads = new TreeSet();
		sessionContext.setArrtibute(UPLOAD_NAME, uploads);
		logger.debug("first time init ");
		if ((parentId != null) && (parentId.longValue() != 0)) {
			Collection dbList = uploadRepository.getUploadFiles(parentId.toString());
			Iterator iter = dbList.iterator();
			while (iter.hasNext()) {
				UploadFile uploadFile = (UploadFile) iter.next();
				uploads.add(uploadFile);
			}
		}
	}

	public void clearSession(SessionContext sessionContext) {
		if (sessionContext != null)
			sessionContext.remove(UPLOAD_NAME);
	}

	public void clearSession() {
		if (this.sc != null)
			this.sc.remove(UPLOAD_NAME);
	}

	/**
	 * @return Returns the sessionContext.
	 */
	public SessionContext getSessionContext() {
		return sc;
	}

	/**
	 * @param sessionContext
	 *            The sessionContext to set.
	 */
	@SessionContextAcceptable
	public void setSessionContext(SessionContext sessionContext) {
		if (sessionContext != null)
			this.sc = sessionContext;
	}

}
