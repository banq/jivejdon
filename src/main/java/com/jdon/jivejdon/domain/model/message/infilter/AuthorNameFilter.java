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
package com.jdon.jivejdon.domain.model.message.infilter;

import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.util.UtilValidate;

import java.util.ArrayList;
import java.util.Collection;

public class AuthorNameFilter {
	public final static String PRE_AUTHOR = "@";
	public final static String SPECIAL_PRE_AUTHOR = "author=@";

	public final static int maxCount = 10;

	public Collection<String> getUsernameFromBody(MessageVO messageVO) {
		Collection<String> toUsernames = new ArrayList();
		try {
			if (messageVO.getBody().indexOf(PRE_AUTHOR) == -1 && messageVO.getSubject().indexOf(PRE_AUTHOR) == -1)
				return null;

			if (messageVO.getBody().indexOf("[author]") != -1) {
				return null;
			}
            //displaycode igonre code
            if (messageVO.getBody().indexOf("[code]") != -1  || messageVO.getBody().indexOf("displaycode") != -1) {
                return null;
            }

//            //20个字符以内@生效
//			if (messageVO.getSubject().indexOf(PRE_AUTHOR) < 20) {
//				String sbuff = messageVO.getSubject().substring(messageVO.getSubject().indexOf
// (PRE_AUTHOR));
//				String[] sas = sbuff.split("(?i)[^a-zA-Z0-9\u4E00-\u9FA5]");
//				if (sas.length >= 2 && !UtilValidate.isEmpty(sas[1]) && UtilValidate
// .isAlphanumeric(sas[1])) {
//					toUsernames.add(sas[1]);
//				}
//			}

			String messagebody = messageVO.getBody();
			int SPECIAL_PRE_AUTHOR_index = messagebody.indexOf(SPECIAL_PRE_AUTHOR);
			if (SPECIAL_PRE_AUTHOR_index != -1) {
				String specialbuff = messagebody.substring(SPECIAL_PRE_AUTHOR_index);
				String[] specialas = specialbuff.split("(?i)[^a-zA-Z0-9\u4E00-\u9FA5]");
				if (specialas.length >= 2 && !UtilValidate.isEmpty(specialas[2]) && UtilValidate.isAlphanumeric(specialas[2]))
					toUsernames.add(specialas[2]);
			}

			int PRE_AUTHOR_index = messagebody.indexOf(PRE_AUTHOR);
			if (PRE_AUTHOR_index == -1 || messagebody.matches("(.*)\\[(.*)@(.*)\\](.*)")) {
				return toUsernames;
			}
			// escape special ascii
			String body = messageVO.getShortBody(messagebody.length());
			int index = body.indexOf(PRE_AUTHOR);
			int fromIndex = 0;
			//只更新一个
			if (index != -1) {
				int CODE_index = body.indexOf("[code]", fromIndex);
				int CODE_index2 = body.indexOf("[/code]", fromIndex);
				if (index > CODE_index && index < CODE_index2) {
					fromIndex = CODE_index2 + 1;
				} else {
					String buff = body.substring(index);
					String[] as = buff.split("(?i)[^a-zA-Z0-9\u4E00-\u9FA5]");
					if (as.length >= 2 && !UtilValidate.isEmpty(as[1]) && UtilValidate.isAlphanumeric(as[1])) {
//						messageVO.setBody(StringUtil.replace(messageVO.getBody(), as[1],
// getAuthorURL(as[1])));
						if (!toUsernames.contains(as[1]) && toUsernames.size() < maxCount)
							toUsernames.add(as[1]);
					}
				}
				index = body.indexOf(PRE_AUTHOR, index + 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return toUsernames;
		}
		return toUsernames;
	}

	protected String getAuthorURL(String toUsername) {
		return "[author]" + toUsername + "[/author]";

	}
}
