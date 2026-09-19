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

public class TaggedThreadListCaptchaAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String ticket = request.getParameter("ticket");
        String randstr = request.getParameter("randstr");
        String tagId = request.getParameter("tagID");
        String start = request.getParameter("start");
        String count = request.getParameter("count");

        if (UtilValidate.isEmpty(ticket) || UtilValidate.isEmpty(randstr)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return mapping.findForward("failure");
        }

        if (!SkinUtils.verifyQQRegisterCode(ticket, randstr, request.getRemoteAddr())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return mapping.findForward("failure");
        }

        HttpSession session = request.getSession();
        session.setAttribute("taggedThreadListVerified", Boolean.TRUE);

        StringBuilder redirectUrl = new StringBuilder(request.getContextPath()).append("/tag/").append(tagId == null ? "" : tagId);
        if (start != null && start.matches("\\d+")) {
            redirectUrl.append("/").append(start);
        }
        redirectUrl.append("?count=").append(count == null || !count.matches("\\d+") ? "18" : count);
        response.sendRedirect(redirectUrl.toString());

        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        return null;
    }
}
