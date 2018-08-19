package com.jdon.jivejdon.service.imp.upload;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.logging.log4j.*;

import com.jdon.annotation.intercept.Poolable;
import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.manager.query.SlideImageQueryManager;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.attachment.UploadFile;
import com.jdon.jivejdon.model.attachment.UploadInfoVO;
import com.jdon.jivejdon.model.message.upload.UploadHelper;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.repository.UploadRepository;
import com.jdon.jivejdon.service.UploadInfoVOService;

/**
 * 
 * @author <a href="mailto:xinying_ge@yahoo.com.cn">GeXinying</a>
 * 
 */
@Poolable
public class UploadInfoVOServiceImp implements UploadInfoVOService {

	private final static Logger logger = LogManager.getLogger(UploadInfoVOServiceImp.class);

	private ForumFactory forumAbstractFactory;

	private UploadRepository uploadRepository;

	private UploadHelper uploadHelper = new UploadHelper();

	private SlideImageQueryManager slideImageQueryManager;

	public UploadInfoVOServiceImp(ForumFactory forumAbstractFactory, UploadRepository uploadRepository,
			SlideImageQueryManager slideImageQueryManager) {
		this.forumAbstractFactory = forumAbstractFactory;
		this.uploadRepository = uploadRepository;
		this.slideImageQueryManager = slideImageQueryManager;
	}

	public void deleteUpload(EventModel em) {
		logger.info("delete.....");
		UploadInfoVO imageInfo = (UploadInfoVO) em.getModelIF();
		uploadRepository.deleteUploadFile(imageInfo.getImageId());
	}

	public UploadInfoVO getUploadInfoVO(String imageId) {
		UploadFile uploadFile = uploadRepository.getUploadFile(imageId);
		if (uploadFile == null)
			return null;

		UploadInfoVO uvo = new UploadInfoVO();
		uvo.setImageId(imageId);
		uvo.setOid(Integer.toString(uploadFile.getOid()));
		uvo.setName(uploadFile.getName());
		uvo.setDescription(uploadFile.getDescription());
		ForumMessage message = forumAbstractFactory.getMessage(Long.parseLong(uploadFile.getParentId()));
		if (message != null) {
			uvo.setForumMessage(message);
			uvo.setBelongToMessage(true);
		}
		return uvo;
	}

	public void updateImage(EventModel em) {

	}

	public PageIterator getUploads(int start, int count) {
		PageIterator pageIterator = new PageIterator();
		try {
			pageIterator = uploadRepository.getUploadFiles(start, count);
		} catch (Exception ex) {
			logger.error(ex);
		}
		return pageIterator;
	}

	public Collection getUploadInfoVOs(int start, int count) {
		Collection list = new ArrayList();
		String imageId = null;
		try {
			PageIterator pageIterator = uploadRepository.getUploadFiles(start, count);
			if (pageIterator.getAllCount() == 0)
				return list;
			while (pageIterator.hasNext()) {
				imageId = (String) pageIterator.next();
				UploadInfoVO uvo = getUploadInfoVO(imageId);
				if (uvo != null && uploadHelper.isImage(uvo.getName()))
					list.add(uvo);
			}
		} catch (Exception ex) {
			logger.error(" imageId=" + imageId + " error: " + ex);
		}
		return list;
	}

	public Collection getUploadInfoVOsOfMessage(int count) {
		return slideImageQueryManager.getUploadInfoVOsOfMessage(count);
	}
}
