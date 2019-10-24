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
package com.jdon.jivejdon.api.property;

import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.property.HotKeys;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.domain.model.query.specification.TaggedThreadListSpec;

import java.util.Collection;

public interface TagService {

	public void saveHotKeys(HotKeys hotKeys);

	public HotKeys getHotKeys();

	public Collection tags(String s);

	public PageIterator getTaggedThread(TaggedThreadListSpec taggedThreadListSpec, int start, int count);

	public PageIterator getTaggedRandomThreads(TaggedThreadListSpec taggedThreadListSpec, int start, int count);

	public PageIterator getThreadTags(int start, int count);

	public ThreadTag getThreadTag(Long tagID);

	public void updateThreadTag(EventModel em);

	public void deleteThreadTag(EventModel em);

	public void saveTag(Long threadId, String[] tagTitle);

}
