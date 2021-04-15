/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.api.impl.tags;

import com.jdon.annotation.Service;
import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.controller.pool.Poolable;
import com.jdon.jivejdon.domain.model.thread.ThreadTagsVO;
import com.jdon.jivejdon.domain.model.util.OneManyDTO;
import com.jdon.jivejdon.domain.model.util.OneOneDTO;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.property.HotKeys;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.domain.model.query.specification.TaggedThreadListSpec;
import com.jdon.jivejdon.infrastructure.repository.property.HotKeysRepository;
import com.jdon.jivejdon.infrastructure.repository.MessageRepository;
import com.jdon.jivejdon.infrastructure.repository.property.TagRepository;
import com.jdon.jivejdon.api.property.TagService;
import com.jdon.jivejdon.api.util.JtaTransactionUtil;
import com.jdon.util.UtilValidate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.regex.Pattern;

@Service("othersService")
public class TagServiceImp implements TagService, Poolable {
	private final static Logger logger = LogManager.getLogger(TagServiceImp.class);
	private final static Pattern illEscape = Pattern
			.compile("[^\\/|^\\<|^\\>|^\\=|^\\?|^\\\\|^\\s|^\\(|^\\)|^\\{|^\\}|^\\[|^\\]|^\\+|^\\*]*");
	protected final JtaTransactionUtil jtaTransactionUtil;
	private final HotKeysRepository hotKeysRepository;
	private final TagRepository tagRepository;
	private final MessageRepository messageRepository;

	public TagServiceImp(HotKeysRepository hotKeysFactory, TagRepository tagRepository,
			MessageRepository messageRepository, JtaTransactionUtil jtaTransactionUtil) {
		super();
		this.hotKeysRepository = hotKeysFactory;
		this.tagRepository = tagRepository;
		this.messageRepository = messageRepository;
		this.jtaTransactionUtil = jtaTransactionUtil;
	}

	public Collection tags(String s) {
		Collection tags = new ArrayList();
		if (UtilValidate.isEmpty(s))
			return tags;
		if (s.length() > 10)
			return tags;
		if (!illEscape.matcher(s).matches())
			return tags;

		for (Long tagID : tagRepository.searchTitle(s)) {
			ThreadTag tag = tagRepository.getThreadTag(tagID);
			tags.add(tag);
		}
		return tags;
	}

	public PageIterator getTaggedThread(TaggedThreadListSpec taggedThreadListSpec, int start, int count) {
		return tagRepository.getTaggedThread(taggedThreadListSpec, start, count);
	}

	public PageIterator getTaggedRandomThreads(TaggedThreadListSpec taggedThreadListSpec, int start, int count) {
		PageIterator pi = tagRepository.getTaggedThread(taggedThreadListSpec, start, count);
		if ((pi.getAllCount() == 0) || (count == 0))
			return new PageIterator();
		int pageCount = pi.getAllCount() / count;
		int nowPage = (int) (Math.random() * pageCount);
		start = nowPage * count;
		return tagRepository.getTaggedThread(taggedThreadListSpec, start, count);
	}

	public void updateThreadTag(EventModel em) {
		ThreadTag threadTag = (ThreadTag) em.getModelIF();
		if (threadTag == null)
			return;
		ThreadTag threadTagOld = tagRepository.getThreadTag(threadTag.getTagID());
		try {
			jtaTransactionUtil.beginTransaction();
			threadTagOld.setTitle(threadTag.getTitle());
			tagRepository.updateThreadTag(threadTagOld);
			jtaTransactionUtil.commitTransaction();
		} catch (Exception e) {
			logger.error(e);
			jtaTransactionUtil.rollback();
			em.setErrors(Constants.ERRORS);

		}
	}

	public void deleteThreadTag(EventModel em) {
		ThreadTag threadTag = (ThreadTag) em.getModelIF();
		threadTag = tagRepository.getThreadTag(threadTag.getTagID());
		if (threadTag == null)
			return;
		try {
			jtaTransactionUtil.beginTransaction();
			tagRepository.deleteThreadTag(threadTag);
			jtaTransactionUtil.commitTransaction();
		} catch (Exception e) {
			logger.error(e);
			jtaTransactionUtil.rollback();
			em.setErrors(Constants.ERRORS);

		}
	}

	public PageIterator getThreadTags(int start, int count) {

		return tagRepository.getThreadTags(start, count);
	}

	public ThreadTag getThreadTag(Long tagID) {
		return tagRepository.getThreadTag(tagID);
	}

	public void saveTag(Long threadId, String[] tagTitles) {
		if ((tagTitles == null) || (tagTitles.length == 0)) {
			return;
		}
		try {
			tagRepository.saveTagTitle(threadId, tagTitles);
			Optional<ForumThread> forumThreadOptional = messageRepository.getForumBuilder().getThread(threadId);
			Collection<ThreadTag> newtags = tagRepository.getThreadTags(forumThreadOptional.get());
			ThreadTagsVO threadTagsVO = new ThreadTagsVO(forumThreadOptional.get(), newtags);
			// this is update thread in memory cache
			forumThreadOptional.get().changeTags(threadTagsVO);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public void saveHotKeys(HotKeys hotKeys) {
		hotKeysRepository.saveHotKeys(hotKeys);
	}

	public HotKeys getHotKeys() {
		return hotKeysRepository.getHotKeys();
	}

	public void saveReBlogLink(OneOneDTO oneOneDTO) {
		try {
			messageRepository.saveReBlog(oneOneDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Collection<Long> getReBlogLink(Long messageId) {
		try {
			return messageRepository.getReBlogByFrom(messageId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
