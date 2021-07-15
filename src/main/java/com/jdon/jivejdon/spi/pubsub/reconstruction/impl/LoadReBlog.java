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
package com.jdon.jivejdon.spi.pubsub.reconstruction.impl;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jdon.annotation.Component;
import com.jdon.annotation.model.OnEvent;
import com.jdon.jivejdon.domain.model.util.Many2ManyDTO;
import com.jdon.jivejdon.infrastructure.repository.ForumFactory;
import com.jdon.jivejdon.infrastructure.repository.MessageRepository;

@Component
public class LoadReBlog {
	private ForumFactory forumFactory;
	private MessageRepository messageRepository;

	public LoadReBlog(ForumFactory forumFactory, MessageRepository messageRepository) {
		this.forumFactory = forumFactory;
		this.messageRepository = messageRepository;
	}

	@OnEvent("loadReBlog")
	public Many2ManyDTO loadReBlogTos(Long Id) {
		try {
			Collection<Long> froms = messageRepository.getReBlogByTo(Id);
			Collection threadfroms = froms.stream().map(forumFactory::getThread)
					.flatMap(o -> o.isPresent() ? Stream.of(o.get()) : Stream.empty()).collect(Collectors.toList());
			Collection<Long> tos = messageRepository.getReBlogByFrom(Id);
			Collection threadtos = tos.stream().map(forumFactory::getThread)
					.flatMap(o -> o.isPresent() ? Stream.of(o.get()) : Stream.empty()).collect(Collectors.toList());
			return new Many2ManyDTO(threadfroms, threadtos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
