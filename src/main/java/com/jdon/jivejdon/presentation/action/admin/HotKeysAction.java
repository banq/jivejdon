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
package com.jdon.jivejdon.presentation.action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.model.property.HotKeys;
import com.jdon.jivejdon.presentation.form.PropertysForm;
import com.jdon.jivejdon.service.property.TagService;

public class HotKeysAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PropertysForm df = (PropertysForm) form;
		String action = df.getAction();
		TagService othersService = (TagService) WebAppUtil.getService("othersService", request);
		if ((action != null) && (action.equals("save"))) {
			HotKeys hotKeys = new HotKeys();
			hotKeys.setProps(df.getPropertys());
			othersService.saveHotKeys(hotKeys);
		} else {
			HotKeys hotKeys = othersService.getHotKeys();
			df.getPropertys().addAll(hotKeys.getProps());
		}
		df.setAction("save");
		return mapping.findForward("forward");
	}

}
