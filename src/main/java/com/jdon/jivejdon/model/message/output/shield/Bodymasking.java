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
package com.jdon.jivejdon.model.message.output.shield;

import com.jdon.jivejdon.model.message.MessageRenderSpecification;
import com.jdon.jivejdon.model.message.MessageVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * when first running, must config this class in managers.xml
 * 
 * @author banq
 * 
 */
public class Bodymasking implements MessageRenderSpecification {
	private final static Logger logger = LogManager.getLogger(Bodymasking.class);

	public static String maskLocalization = "THIS MESSAGE HAS BEEN MASKED";

	public MessageVO render(MessageVO messageVO) {
		if (messageVO.getForumMessage().isMasked())
			return MessageVO.builder().subject(messageVO.getSubject()).body
					(maskLocalization).message(messageVO.getForumMessage())
					.build();
		else
			return messageVO;
	}


	public String getMaskLocalization() {
		return maskLocalization;
	}

	public void setMaskLocalization(String maskLocalization) {
		this.maskLocalization = maskLocalization;
	}

}
