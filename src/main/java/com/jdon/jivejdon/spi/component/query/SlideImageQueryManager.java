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
package com.jdon.jivejdon.spi.component.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.*;

import com.jdon.annotation.Component;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.attachment.UploadFile;
import com.jdon.jivejdon.domain.model.attachment.UploadInfoVO;
import com.jdon.jivejdon.domain.model.message.upload.UploadHelper;
import com.jdon.jivejdon.infrastructure.repository.ForumFactory;
import com.jdon.jivejdon.infrastructure.repository.property.UploadRepository;
import com.jdon.jivejdon.util.ContainerUtil;

@Component("slideImageQueryManager")
public class SlideImageQueryManager {
	private final static Logger logger = LogManager.getLogger(SlideImageQueryManager.class);

	protected ContainerUtil containerUtil;
	private UploadRepository uploadRepository;
	private ForumFactory forumAbstractFactory;
	private UploadHelper uploadHelper = new UploadHelper();

	public SlideImageQueryManager(ContainerUtil containerUtil, UploadRepository uploadRepository, ForumFactory forumAbstractFactory) {
		super();
		this.containerUtil = containerUtil;
		this.uploadRepository = uploadRepository;
		this.forumAbstractFactory = forumAbstractFactory;
	}

	public Collection<UploadInfoVO> getUploadInfoVOsOfMessage(int count) {
		String cacheKey = SlideImageQueryManager.class.getName();
		Collection<UploadInfoVO> resultSorteds = (List) containerUtil.getCacheManager().getCache().get(cacheKey);
		if (resultSorteds != null)
			return resultSorteds;

		resultSorteds = getUploadInfoVOsOfMessageFromDB(count);
		containerUtil.getCacheManager().getCache().put(cacheKey, resultSorteds);

		return resultSorteds;

	}

	protected Collection getUploadInfoVOsOfMessageFromDB(int count) {
		Collection list = new ArrayList();
		try {
			PageIterator pageIterator = uploadRepository.getUploadFiles(0, 200);
			if (pageIterator.getAllCount() == 0)
				return list;
			while (pageIterator.hasNext()) {
				String imageId = (String) pageIterator.next();
				UploadInfoVO uvo = getUploadInfoVO(imageId);
				if (uvo != null && uploadHelper.isImage(uvo.getName()) && uvo.isBelongToMessage())
					list.add(uvo);
				if (list.size() > count)
					break;
			}
		} catch (Exception ex) {
			logger.error(" error: " + ex);
		}
		return list;
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

}
