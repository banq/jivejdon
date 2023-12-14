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
package com.jdon.jivejdon.infrastructure.repository.dao;

import java.util.Collection;
import java.util.List;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.property.ThreadTag;

public interface TagDao {

	ThreadTag getThreadTagByTitle(String title);

	Collection<Long> searchTitle(String s);

	ThreadTag getThreadTag(Long tagID);

	void createTag(ThreadTag threadTag) throws Exception;

	void updateTag(ThreadTag threadTag) throws Exception;

	Collection getThreadTagIDs(Long threadID);

	PageIterator getThreadTags(int start, int count);

	PageIterator getTaggedThread(Long tagID, int start, int count);

	List<Long> getTaggedThread(Long tagID);

	List getThreadsPrevNextInTag(Long tagId, Long currentThreadId);

	void addThreadTag(Long tagID, Long threadID) throws Exception;

	void delThreadTag(Long threadID) throws Exception;

	boolean checkThreadTagRelation(Long tagID, Long threadID);

	void delThreadTag(Long tagID, Long threadID) throws Exception;

	void deleteTag(Long tagID) throws Exception;


}
