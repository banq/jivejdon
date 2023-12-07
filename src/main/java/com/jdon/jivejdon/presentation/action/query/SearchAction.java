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


	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {

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
		String testS = "33333eee";
		System.out.println(testS.matches("\\d+")?"ok":"no");
	}

}
