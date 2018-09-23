/*
 * Copyright 2003-2005 the original author or authors.
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
package com.jdon.jivejdon.model.message;

import com.jdon.jivejdon.model.ForumMessage;

/**
 * Specification pattern Message rendering Specification, Value Object.
 * 
 * The decorator of the ForumMessage. this decorator is used by message filter
 * display.
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public interface MessageRenderSpecification {

	/**
	 * render a new Value Object
	 * 
	 */
	public abstract ForumMessage render(ForumMessage forumMessage);

}
