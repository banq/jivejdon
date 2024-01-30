/*
 * Copyright (c) 2008 Ge Xinying
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jdon.jivejdon.presentation.action.shortmessage;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.shortmessage.ShortMessageService;
import com.jdon.strutsutil.ModelListAction;

/**
 * ReceiveShortMessageListAction.java
 * <p>
 * Title:收件箱
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * CreateData: May 30, 2008
 * </p>
 * 
 * @author GeXinying
 * @version 1.0
 */
public class ReceiveBoxListAction extends ModelListAction {
	private static Logger logger = LogManager.getLogger(ReceiveBoxListAction.class);

	@Override
	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		logger.debug("getPageIterator()");
		ShortMessageService service = (ShortMessageService) WebAppUtil.getService("shortMessageService", 
				this.servlet.getServletContext());

		return service.getToShortMessage((Long) key);
	}

	@Override
	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		logger.debug("findModelIFByKey()");
		ShortMessageService service = (ShortMessageService) WebAppUtil.getService("shortMessageService", 
				this.servlet.getServletContext());

		return service.getReceiveShortMessages(start, count);
	}

}
