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
package com.jdon.jivejdon.repository.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.ThreadTag;
import com.jdon.jivejdon.model.query.specification.TaggedThreadListSpec;
import com.jdon.jivejdon.repository.TagRepository;
import com.jdon.jivejdon.repository.dao.SequenceDao;
import com.jdon.jivejdon.repository.dao.TagDao;

public class TagRepositoryDao implements TagRepository {

	private TagDao tagDao;

	private SequenceDao sequenceDao;

	public TagRepositoryDao(TagDao tagDao, SequenceDao sequenceDao) {
		this.tagDao = tagDao;
		this.sequenceDao = sequenceDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.TagRepository#searchTitle(java.lang.String)
	 */
	public Collection<Long> searchTitle(String s) {
		return tagDao.searchTitle(s);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.TagRepository#getTaggedThread(com.jdon.jivejdon
	 * .model.query.specification.TaggedThreadListSpec, int, int)
	 */
	public PageIterator getTaggedThread(TaggedThreadListSpec taggedThreadListSpec, int start, int count) {
		return tagDao.getTaggedThread(taggedThreadListSpec, start, count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.repository.TagRepository#getThreadTags(int, int)
	 */
	public PageIterator getThreadTags(int start, int count) {
		return tagDao.getThreadTags(start, count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.TagRepository#getThreadTag(java.lang.Long)
	 */
	public ThreadTag getThreadTag(Long tagID) {
		ThreadTag tag = tagDao.getThreadTag(tagID);
		return tag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.TagRepository#addTagTitle(java.lang.Long,
	 * java.lang.String[])
	 */
	public void insertTagTitle(Long threadId, String[] tagTitle) throws Exception {
		if ((tagTitle == null) || (tagTitle.length == 0)) {
			return;
		}
		for (int i = 0; i < tagTitle.length; i++) {
			if ((tagTitle[i] != null) && (tagTitle[i].length() != 0))
				insert(threadId, tagTitle[i]);
		}

	}

	private void insert(Long fourmThreadId, String tagTitle) throws Exception {
		// tagTitle = tagTitle.replace(" ", "").toLowerCase();
		ThreadTag threadTag = tagDao.getThreadTagByTitle(tagTitle);
		if (threadTag != null) {
			// if threadTag is not belong to the thread, add one to it.
			if (!tagDao.checkThreadTagRelation(threadTag.getTagID(), fourmThreadId)) {
				threadTag.setAssonum(threadTag.getAssonum() + 1);
				updateThreadTag(threadTag);
				tagDao.addThreadTag(threadTag.getTagID(), fourmThreadId);
			}
		} else {
			threadTag = new ThreadTag();
			Long tagID = sequenceDao.getNextId(Constants.OTHERS);
			threadTag.setTagID(tagID);
			threadTag.setTitle(tagTitle);
			threadTag.setAssonum(1);
			tagDao.createThreadTag(threadTag);
			tagDao.addThreadTag(threadTag.getTagID(), fourmThreadId);
		}

	}

	// 进行比较 使用hibernate merge就一句OK
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.TagRepository#mergeTagTitle(java.lang.Long,
	 * java.lang.String[])
	 */
	public void saveTagTitle(Long threadId, String[] tagTitles) throws Exception {
		deleteTagTitle(threadId);
		insertTagTitle(threadId, tagTitles);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.TagRepository#deleteTagTitle(java.lang.Long)
	 */
	public void deleteTagTitle(Long threadId) throws Exception {
		Collection tags = getThreadTags(threadId);
		for (Object o : tags) {
			ThreadTag tag = (ThreadTag) o;
			if (tag.getAssonum() <= 1) {
				deleteThreadTag(tag);
			} else {
				tag.setAssonum(tag.getAssonum() - 1);
				updateThreadTag(tag);
				tagDao.delThreadTag(threadId);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.TagRepository#getThreadTags(com.jdon.jivejdon
	 * .model.ForumThread)
	 */
	public Collection getThreadTags(ForumThread forumThread) {
		Collection tags = new ArrayList();
		Collection ids = tagDao.getThreadTagIDs(forumThread.getThreadId());
		Iterator iter = ids.iterator();
		while (iter.hasNext()) {
			Long tagID = (Long) iter.next();
			tags.add(tagDao.getThreadTag(tagID));
		}
		return tags;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.TagRepository#getThreadTags(java.lang.Long)
	 */
	public Collection getThreadTags(Long forumThreadId) {
		Collection tags = new ArrayList();
		Collection ids = tagDao.getThreadTagIDs(forumThreadId);
		Iterator iter = ids.iterator();
		while (iter.hasNext()) {
			Long tagID = (Long) iter.next();
			tags.add(tagDao.getThreadTag(tagID));
		}
		return tags;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.TagRepository#updateThreadTag(com.jdon.jivejdon
	 * .model.ThreadTag)
	 */
	public void updateThreadTag(ThreadTag threadTag) throws Exception {
		tagDao.updateThreadTag(threadTag);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.TagRepository#deleteThreadTag(com.jdon.jivejdon
	 * .model.ThreadTag)
	 */
	public void deleteThreadTag(ThreadTag threadTag) throws Exception {
		threadTag = this.getThreadTag(threadTag.getTagID());
		tagDao.deleteThreadTag(threadTag.getTagID());
	}

}
