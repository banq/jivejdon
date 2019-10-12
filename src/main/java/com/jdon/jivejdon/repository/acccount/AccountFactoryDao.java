package com.jdon.jivejdon.repository.acccount;

import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.model.account.Account;
import com.jdon.jivejdon.model.auth.Role;
import com.jdon.jivejdon.repository.dao.AccountDao;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.util.Debug;
import com.jdon.util.UtilValidate;
import org.apache.commons.validator.EmailValidator;

import java.util.regex.Pattern;

public class AccountFactoryDao implements AccountFactory, Startable {
	private final static String module = AccountFactoryDao.class.getName();
	private final static Pattern illEscape = Pattern.compile("[^\\/|^\\<|^\\>|^\\=|^\\?|^\\\\|^\\s|^\\(|^\\)|^\\{|^\\}|^\\[|^\\]|^\\+|^\\*]*");

	private AccountDao accountDao;

	private ContainerUtil containerUtil;
	
	private final Account anonymous;

	public AccountFactoryDao(AccountDao accountDao, ContainerUtil containerUtil) {
		this.accountDao = accountDao;
		this.containerUtil = containerUtil;
		this.anonymous = this.createAnonymous();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.acccount.AccountFactory#getFullAccount(com.jdon.jivejdon
	 * .model.Account)
	 */
	public Account getFullAccount(Account accountIn) {
		Debug.logVerbose("enter AccountFactory create", module);
		if (accountIn == null)
			return this.anonymous;
		Account account = null;
		if (!UtilValidate.isEmpty(accountIn.getUserId())) {
			account = getFullAccount(accountIn.getUserId());
		} else if (!UtilValidate.isEmpty(accountIn.getUsername())) {
			account = getFullAccountForUsername(accountIn.getUsername());
		} else if (!UtilValidate.isEmpty(accountIn.getEmail())) {
			account = getFullAccountForEmail(accountIn.getEmail());
		}
		if (account == null) {
			Debug.logError("the user has been delete, it is Anonymous id=" + accountIn.getUserId() + " username=" + accountIn.getUsername()
					+ " email=" + accountIn.getEmail(), module);
			account = this.anonymous;
		}
		return account;
	}

	public Account getFullAccountForEmail(String email) {
		if (!illEscape.matcher(email).matches())
			return null;
		if (!EmailValidator.getInstance().isValid(email))
			return null;
		if (email.length() > 50)
			return null;
		Long userId = accountDao.fetchAccountByEmail(email);
		if (userId != null) // ensure one instance in cache for one key;
			return accountDao.getAccount(Long.toString(userId));
		else
			return null;

	}

	public Account getFullAccountForUsername(String username) {
		Account account = null;

		if (!illEscape.matcher(username).matches())
			return account;
		if (username.length() > 30)
			return account;
		try {
			Long userId = accountDao.fetchAccountByName(username);
			if (userId != null) {// ensure one instance in cache for one key;
				account = accountDao.getAccount(Long.toString(userId));
				if (account != null && !account.getUsername().equalsIgnoreCase(username)) {
					Debug.logError("the user is wrong, username=" + username + " userId=" + userId + " account username=" + account.getUsername(),
							module);
					containerUtil.clearCache(userId);
					return null;
				}
			}
		} catch (Exception e) {
			Debug.logError("the user look error=" + e + " username=" + username, module);
		}
		if (account == null) {
			Debug.logError("the user not found username=" + username, module);
		}
		return account;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.acccount.AccountFactory#getFullAccount(java.lang.
	 * String)
	 */
	public Account getFullAccount(String userId) {
		Debug.logVerbose("enter AccountFactory create", module);
		if (userId == null)
			return this.anonymous;
		Account account = null;
		if (userId != null) {
			account = accountDao.getAccount(userId);
		}
		if (account == null) {
			Debug.logVerbose("the user has been delete, it is Anonymous, userId=" + userId, module);
			account = this.anonymous;
		}
		return account;
	}

	private Account createAnonymous() {
		Account account = new Account();
		account.setUsername("anonymous");
		account.setUserIdLong(new Long(0));
		account.setEmail("anonymous@anonymous.com");
		account.setRoleName(Role.ANONYMOUS);
		account.setModifiedDate("");
		account.setCreationDate("");
		account.setAnonymous(true);
		return account;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		if (this.accountDao != null) {
			this.accountDao = null;
		}

	}

}
