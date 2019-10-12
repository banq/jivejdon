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
package com.jdon.jivejdon.repository.property;

import com.jdon.jivejdon.model.property.HotKeys;

import java.util.Collection;

public class HotKeysRepository {
	
	private PropertyFactory forumPropertyFactory;
	
	public HotKeysRepository(PropertyFactory forumPropertyFactory) {
		super();
		this.forumPropertyFactory = forumPropertyFactory;
	}

	public HotKeys getHotKeys(){
		HotKeys hotKeys = new HotKeys();
		Collection props = forumPropertyFactory.getForumPropertys(new Long(hotKeys.getId()));
		hotKeys.setProps(props);
		return hotKeys;
	}
	
	public void saveHotKeys(HotKeys hotKeys) {
		forumPropertyFactory.saveForumPropertys(hotKeys.getId(), hotKeys.getProps());
	}

}
