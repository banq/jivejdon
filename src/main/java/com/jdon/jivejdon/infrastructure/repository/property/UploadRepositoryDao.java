package com.jdon.jivejdon.infrastructure.repository.property;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.attachment.UploadFile;
import com.jdon.jivejdon.domain.model.message.upload.UploadHelper;
import com.jdon.jivejdon.infrastructure.repository.dao.UploadFileDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class UploadRepositoryDao implements UploadRepository {
	private final static Logger logger = LogManager.getLogger(UploadRepositoryDao.class);

	private UploadFileDao uploadFileDao;

	public UploadRepositoryDao(UploadFileDao uploadFileDao) {

		this.uploadFileDao = uploadFileDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.property.UploadRepository#getUploadFiles(java.lang
	 * .String)
	 */
	public List<UploadFile> getUploadFiles(String parentId) {
		List<UploadFile> uploads = new ArrayList<>();
		Collection ids = uploadFileDao.getAdjunctIDs(parentId);
		Iterator iter = ids.iterator();
		while (iter.hasNext()) {
			String id = (String) iter.next();
			UploadFile uf = getUploadFile(id);
			uploads.add(uf);
		}
		return uploads;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.property.UploadRepository#getSingleUploadFile(java
	 * .lang.String)
	 */
	public UploadFile getSingleUploadFile(String parentId) {
		Collection ids = uploadFileDao.getAdjunctIDs(parentId);
		Iterator iter = ids.iterator();
		UploadFile uf = null;
		if (iter.hasNext()) {
			String id = (String) iter.next();
			uf = getUploadFile(id);
		}
		return uf;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.infrastructure.repository.property.UploadRepository#getUploadFiles(int,
	 * int)
	 */
	public PageIterator getUploadFiles(int start, int count) {
		return uploadFileDao.getUploadFiles(start, count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.property.UploadRepository#getUploadFile(java.lang
	 * .String)
	 */
	public UploadFile getUploadFile(String objectId) {
		return uploadFileDao.getUploadFile(objectId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.property.UploadRepository#saveAllUploadFiles(java
	 * .lang.String, java.util.Collection)
	 */
	private void insertAllUploadFiles(String parentId, Collection uploads) throws Exception {
		logger.debug(" prepareForSaveAllUploadFiles parentId=" + parentId);
		if (uploads == null)
			return;
		logger.debug(" uploads size=" + uploads.size());
		Iterator iter = uploads.iterator();
		while (iter.hasNext()) {
			UploadFile uploadFile = (UploadFile) iter.next();
			uploadFile.setParentId(parentId);
			saveUpload(uploadFile);
		}
	}

	public void saveUpload(UploadFile uploadFile) throws Exception {
		UploadHelper uploadHelper = new UploadHelper();
		uploadFile.setName(uploadHelper.subImageName(uploadFile.getName()));

		uploadFileDao.createUploadFile(uploadFile);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.property.UploadRepository#updateAllUploadFiles(java
	 * .lang.String, java.util.Collection)
	 */
	public void saveAllUploadFiles(String parentId, Collection uploads) throws Exception {
		if (uploads == null)
			return;
		logger.debug(" mergeAllUploadFiles parentId=" + parentId);
		deleteAllUploadFiles(parentId);
		insertAllUploadFiles(parentId, uploads);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.property.UploadRepository#deleteAllUploadFiles(java
	 * .lang.String)
	 */
	public void deleteAllUploadFiles(String parentId) {
		Collection ids = uploadFileDao.getAdjunctIDs(parentId);
		Iterator iter = ids.iterator();
		while (iter.hasNext()) {
			String id = (String) iter.next();
			deleteUploadFile(id);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.property.UploadRepository#deleteUploadFile(java.lang
	 * .String)
	 */
	public void deleteUploadFile(String objectId) {
		uploadFileDao.deleteUploadFile(objectId);
	}
}
