package com.jdon.jivejdon.presentation.action.admin;

import javax.servlet.http.HttpServletRequest;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.account.AccountService;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.util.UtilValidate;

public class UserListAction extends ModelListAction {

	@Override
	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		String username = request.getParameter("username");
		AccountService accountService = (AccountService) WebAppUtil.getService("accountService", 
				this.servlet.getServletContext());
		if (UtilValidate.isEmpty(username)) {
			return accountService.getAccounts(start, count);
		} else {
			return accountService.getAccountByNameLike(username, start, count);

		}
	}

	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		AccountService accountService = (AccountService) WebAppUtil.getService("accountService", 
				this.servlet.getServletContext());
		return accountService.getAccount((Long) key);

	}

}
