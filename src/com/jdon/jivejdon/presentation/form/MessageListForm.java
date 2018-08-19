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
package com.jdon.jivejdon.presentation.form;

import com.jdon.strutsutil.ModelListForm;

/**
 * @author <a href="mailto:banqJdon<AT>jdon.com">banq</a>
 * 
 */
public class MessageListForm extends ModelListForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean[] authenticateds;

	public MessageListForm() {
		this.setCount(5); // default there are #count# messages in one page
	}

	public boolean getAuthenticated(int index) {
		if (authenticateds == null) {
			authenticateds = new boolean[this.getList().size()];
		}
		return authenticateds[index];
	}

	public boolean[] getAuthenticateds() {
		if (authenticateds == null) {
			authenticateds = new boolean[this.getList().size()];
		}
		return authenticateds;
	}

	public void setAuthenticateds(boolean[] authenticateds) {
		this.authenticateds = authenticateds;
	}

	/**
	 * @return Returns the numPages.
	 */
	public int getNumReplies() {
		if (getAllCount() >= 1)
			return getAllCount() - 1;
		return 0;
	}

	/**
	 * @return Returns the numReplies.
	 */
	public int getNumPages() {
		if (getCount() == 0)
			return 0;
		return (int) Math.ceil((double) getAllCount() / (double) getCount());
	}

}
