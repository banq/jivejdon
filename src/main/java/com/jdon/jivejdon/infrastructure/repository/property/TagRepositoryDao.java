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
package com.jdon.jivejdon.infrastructure.repository.property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.infrastructure.repository.dao.SequenceDao;
import com.jdon.jivejdon.infrastructure.repository.dao.TagDao;
import com.jdon.jivejdon.util.Constants;

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
	 * com.jdon.jivejdon.infrastructure.repository.property.TagRepository#searchTitle(java.lang.String)
	 */
	public Collection<Long> searchTitle(String s) {
		return tagDao.searchTitle(s);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.property.TagRepository#getTaggedThread(com.jdon.jivejdon
	 * .model.query.specification.TaggedThreadListSpec, int, int)
	 */
	public PageIterator getTaggedThread(Long tagID, int start, int count) {
		return tagDao.getTaggedThread(tagID, start, count);
	}

	public List<Long> getTaggedThread(Long tagID){
		return tagDao.getTaggedThread(tagID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.infrastructure.repository.property.TagRepository#getThreadTags(int, int)
	 */
	public PageIterator getThreadTags(int start, int count) {
		return tagDao.getThreadTags(start, count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.property.TagRepository#getThreadTag(java.lang.Long)
	 */
	public ThreadTag getThreadTag(Long tagID) {
		ThreadTag tag = tagDao.getThreadTag(tagID);
		return tag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.property.TagRepository#addTagTitle(java.lang.Long,
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
				updateTag(threadTag);
				tagDao.addThreadTag(threadTag.getTagID(), fourmThreadId);
			}
		} else {
			threadTag = new ThreadTag();
			Long tagID = sequenceDao.getNextId(Constants.OTHERS);
			threadTag.setTagID(tagID);
			threadTag.setTitle(tagTitle);
			threadTag.setAssonum(1);
			tagDao.createTag(threadTag);
			tagDao.addThreadTag(threadTag.getTagID(), fourmThreadId);
		}

	}

	// 进行比较 使用hibernate merge就一句OK
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.property.TagRepository#mergeTagTitle(java.lang.Long,
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
	 * com.jdon.jivejdon.infrastructure.repository.property.TagRepository#deleteTagTitle(java.lang.Long)
	 */
	public void deleteTagTitle(Long threadId) throws Exception {
		Collection tags = getThreadTags(threadId);
		for (Object o : tags) {
			ThreadTag tag = (ThreadTag) o;
			if (tag.getAssonum() <= 1) {
				deleteTag(tag);
			} else {
				tag.setAssonum(tag.getAssonum() - 1);
				updateTag(tag);
				tagDao.delThreadTag(threadId);
			}
		}
	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.property.TagRepository#getThreadTags(java.lang.Long)
	 */
	public Collection<ThreadTag> getThreadTags(Long forumThreadId) {
		Collection<ThreadTag> tags = new ArrayList<>();
		Collection<Long> ids = tagDao.getThreadTagIDs(forumThreadId);
		for (Long tagID: ids) {
			tags.add(tagDao.getThreadTag(tagID));
		}
		return tags;
	}

	public void saveThreadToken(Long threadId, String token) {
		try {
			if (getThreadToken(threadId) == null) {
				tagDao.createThreadToken(threadId, token);
			} else {
				tagDao.delThreadToken(threadId);
				tagDao.createThreadToken(threadId, token);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public String getThreadToken(Long threadId) {
		return tagDao.getThreadToken(threadId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.property.TagRepository#updateTag(
	 * com.jdon.jivejdon
	 * .model.ThreadTag)
	 */
	public void updateTag(ThreadTag threadTag) throws Exception {
		tagDao.updateTag(threadTag);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.property.TagRepository#deleteTag(
	 * com.jdon.jivejdon
	 * .model.ThreadTag)
	 */
	public void deleteTag(ThreadTag threadTag) throws Exception {
		threadTag = this.getThreadTag(threadTag.getTagID());
		tagDao.deleteTag(threadTag.getTagID());
	}

}
