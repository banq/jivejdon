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
package com.jdon.jivejdon.spi.pubsub.publish;

import com.jdon.annotation.Component;
import com.jdon.annotation.Introduce;
import com.jdon.annotation.model.Send;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.domain.model.realtime.LobbyPublisherRoleIF;
import com.jdon.jivejdon.domain.model.realtime.Notification;

@Component
@Introduce("message")
public class LobbyPublisherRole implements LobbyPublisherRoleIF {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.domain.model.realtime.LobbyPublisherRoleIF#notifyLobby(com
	 * .jdon.jivejdon.model.realtime.Notification)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.domain.model.realtime.LobbyPublisherRoleIF#notifyLobby(com
	 * .jdon.jivejdon.model.realtime.Notification)
	 */
	@Override
	@Send("newMessageBaiduSearchNotifier")
	public DomainMessage notifyLobby(Notification notification) {
		return new DomainMessage(notification);
	}
}
