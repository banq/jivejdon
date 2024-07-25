package com.jdon.jivejdon.presentation.action.query;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.ForumMessageService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.presentation.form.SkinUtils;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.util.UtilValidate;

public class SearchAction extends ModelListAction {


	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {

		HttpSession session = request.getSession();

		// 尝试从会话中获取 randstr 和 registerCode
		String randstr = (String) session.getAttribute("randstr");
		String registerCode = (String) session.getAttribute("registerCode");

		// 如果会话中没有 randstr，则从请求中获取并存储到会话中
		if (randstr == null || registerCode == null) {
			randstr = request.getParameter("randstr");
			registerCode = request.getParameter("registerCode");
			if (UtilValidate.isEmpty(registerCode) || UtilValidate.isEmpty(randstr))
				return new PageIterator();
			if (SkinUtils.verifyQQRegisterCode(registerCode, randstr, request.getRemoteAddr())) {
				session.setAttribute("registerCode", registerCode);
				session.setAttribute("randstr", randstr);
			} else
				return new PageIterator();

		}
		
		
		String query = request.getParameter("query");
		if ((query == null) || (UtilValidate.isEmpty(query))) {
			return new PageIterator();
		}
		String useGBK = request.getParameter("useGBK");
		if (useGBK != null) {
			try {
				query = ToolsUtil.getParameterFromQueryString(request.getQueryString(), "query");
				query = new String((query).getBytes("ISO-8859-1"), "GBK");
				query = ToolsUtil.gbToUtf8(query);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		query = query.replaceAll("[^(a-zA-Z0-9\\s\\u4e00-\\u9fa5)]", "");
		ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil
				.getService("forumMessageQueryService", this.servlet.getServletContext());
		return forumMessageQueryService.searchMessages(query, start, count);
	}

	// null method not be run.
	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService",
				this.servlet.getServletContext());
		return forumMessageService.getMessage((Long) key);
	}

	public static void main(String[] args) {
		String testS = "33333eee阿斯顿撒%大苏打";
		System.out.println(testS.matches("^[\\_a-zA-Z0-9\\u4e00-\\u9fa5]+$")?"ok":"no");
	}

}
