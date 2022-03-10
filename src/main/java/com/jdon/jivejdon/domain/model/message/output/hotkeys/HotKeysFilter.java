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
package com.jdon.jivejdon.domain.model.message.output.hotkeys;

import com.jdon.jivejdon.domain.model.property.HotKeys;
import com.jdon.jivejdon.domain.model.property.Property;
import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.util.Debug;

import java.util.Iterator;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * removing replace hot keywords with HotKeys added by administrator
 * 
 * @author banq
 * 
 */
public class HotKeysFilter implements Function<MessageVO, MessageVO> {
	private final static String module = HotKeysFilter.class.getName();

	private String prefix_regEx = "([\\u4e00-\\u9fa5]|[\\s])(?i)"; // chinese or
	// whitespace
	private String suffix_regEx = "";

	//
	public MessageVO apply(MessageVO messageVO) {
		return messageVO.builder().subject(messageVO.getSubject()).body(applyFilteredBody(messageVO)).build();
	}

	/**
	 * space + keyword + space will be replaced
	 */
	public String applyFilteredBody(MessageVO messageVO) {
		String body = messageVO.getBody();
		try {
			HotKeys hotKeys = messageVO.getForumMessage().getHotKeys();
			if (hotKeys == null)
				return body;

			Iterator iter = hotKeys.getProps().iterator();
			while (iter.hasNext()) {
				Property prop = (Property) iter.next();
				String regEx = prefix_regEx + prop.getName() + suffix_regEx;
				String replcaement = getKeyUrlStr(prop.getName(), prop.getValue());
				body = Pattern.compile(regEx).matcher(body).replaceFirst("$1" + replcaement);
			}
		} catch (Exception e) {
			Debug.logError("" + e, module);
		}
		return body;
	}

	private String getKeyUrlStr(String name, String url) {
		StringBuilder bf = new StringBuilder();
		bf.append("<a href='");
		bf.append(url);

		bf.append("' target=_blank><b>");
		// // class="hotkeys ajax_query=cache"
		// bf.append(" class='hotkeys ajax_query=").append(name).append("' ");
		// bf.append(" id='id_").append(url).append("' ><b>");
		bf.append(name);
		bf.append("</b></a>");
		return bf.toString();
	}

	public String getPrefix_regEx() {
		return prefix_regEx;
	}

	public void setPrefix_regEx(String prefix_regEx) {
		if (prefix_regEx != null)
			this.prefix_regEx = prefix_regEx;
	}

	public String getSuffix_regEx() {
		return suffix_regEx;
	}

	public void setSuffix_regEx(String suffix_regEx) {
		if (suffix_regEx != null)
			this.suffix_regEx = suffix_regEx;
	}

}
