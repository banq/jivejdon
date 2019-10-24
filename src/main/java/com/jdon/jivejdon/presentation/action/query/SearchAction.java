package com.jdon.jivejdon.presentation.action.query;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.api.ForumMessageService;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.util.UtilValidate;

public class SearchAction extends ModelListAction {

	private final static Logger logger = LogManager.getLogger(ThreadQueryAction.class);

	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {

		String query = request.getParameter("query");
		if ((query == null) || (UtilValidate.isEmpty(query))) {
			logger.error(" getPageIterator error : query is null " + request.getRemoteAddr());
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
		Map paramsMap = new HashMap();
		request.setAttribute("paramsMap", paramsMap);
		String userId = request.getParameter("userId");
		paramsMap.put("query", query);
		if (!UtilValidate.isEmpty(userId)) {
			paramsMap.put("userId", userId);
		}
		// request.setAttribute("query", query);
		ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService",
				this.servlet.getServletContext());
		return forumMessageQueryService.searchMessages(query, start, count);
	}

	// null method not be run.
	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil
				.getService("forumMessageService", this.servlet.getServletContext());
		logger.debug(" key calss type = " + key.getClass().getName());
		return forumMessageService.getMessage((Long) key);
	}

}
