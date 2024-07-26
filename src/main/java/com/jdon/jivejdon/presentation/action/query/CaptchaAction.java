package com.jdon.jivejdon.presentation.action.query;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.jivejdon.presentation.form.SkinUtils;
import com.jdon.util.UtilValidate;

public class CaptchaAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	HttpSession session = request.getSession();

		// 尝试从会话中获取 randstr 和 registerCode
		String randstr = (String) session.getAttribute("randstr");
		String registerCode = (String) session.getAttribute("registerCode");

		// 如果会话中没有 randstr，则从请求中获取并存储到会话中
		if (randstr == null || registerCode == null) {
			randstr = request.getParameter("randstr");
			registerCode = request.getParameter("registerCode");
			if (UtilValidate.isEmpty(registerCode) || UtilValidate.isEmpty(randstr))
				return mapping.findForward("failure");
			if (SkinUtils.verifyQQRegisterCode(registerCode, randstr, request.getRemoteAddr())) {
				session.setAttribute("registerCode", registerCode);
				session.setAttribute("randstr", randstr);
			} else
				return mapping.findForward("failure");

		}

		return mapping.findForward("success");

	}

}
