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
package com.jdon.jivejdon.event.domain.consumer.write;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.event.ThreadTagsSavedEvent;
import com.jdon.jivejdon.model.thread.ThreadTagsVO;
import com.jdon.jivejdon.repository.property.TagRepository;
import com.jdon.jivejdon.repository.builder.ForumAbstractFactory;

import java.util.Collection;
import java.util.Optional;

@Consumer("saveTags")
public class ThreadTagsListener implements DomainEventHandler {

	private final TagRepository tagRepository;

	private final ForumAbstractFactory forumAbstractFactory;

	public ThreadTagsListener(TagRepository tagRepository, ForumAbstractFactory forumAbstractFactory) {
		super();
		this.tagRepository = tagRepository;
		this.forumAbstractFactory = forumAbstractFactory;
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {

		ThreadTagsSavedEvent es = (ThreadTagsSavedEvent) event.getDomainMessage().getEventSource();
		Long threadId = es.getThreadId();
		Optional<ForumThread> forumThreadOptional = forumAbstractFactory.getThread(threadId);
		if (!forumThreadOptional.isPresent()) return;
		try {
			String[] titles = (String[]) es.getTitles().toArray(new String[0]);
			tagRepository.saveTagTitle(threadId, titles);

			Collection newtags = tagRepository.getThreadTags(forumThreadOptional.get());
			ThreadTagsVO threadTagsVO = new ThreadTagsVO(forumThreadOptional.get(), newtags);
			Collection lasttags = forumThreadOptional.get().getThreadTagsVO().getTags();
			ForumThread forumThread = forumThreadOptional.get();
			if (forumThread != null) {
				forumThread.build(forumThread.getForum(),forumThread.getRootMessage(),threadTagsVO);
				threadTagsVO.subscriptionNotify(lasttags);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
