package com.jdon.jivejdon.infrastructure.repository.acccount;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;
import java.util.regex.Pattern;

import org.apache.commons.validator.EmailValidator;

import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.infrastructure.repository.dao.AccountDao;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.util.Debug;
import com.jdon.util.UtilValidate;

public class AccountFactoryDao implements AccountFactory, Startable {
	private final static String module = AccountFactoryDao.class.getName();
	private final static Pattern illEscape = Pattern.compile("[^\\/|^\\<|^\\>|^\\=|^\\?|^\\\\|^\\s|^\\(|^\\)|^\\{|^\\}|^\\[|^\\]|^\\+|^\\*]*");

	private AccountDao accountDao;

	private ContainerUtil containerUtil;
	
		
	public AccountFactoryDao(AccountDao accountDao, ContainerUtil containerUtil) {
		this.accountDao = accountDao;
		this.containerUtil = containerUtil;
	}

	private final ConcurrentHashMap<Long, FutureTask<Account>> inflightAccounts =
        new ConcurrentHashMap<>();

	private Account loadFullAccount(Long userId) {

		FutureTask<Account> task = new FutureTask<>(() -> {
			return accountDao.getAccount(Long.toString(userId));
		});

		FutureTask<Account> existing = inflightAccounts.putIfAbsent(userId, task);

		if (existing == null) {
			existing = task;
			task.run();
		}

		try {
			return existing.get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			inflightAccounts.remove(userId, existing);
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.acccount.AccountFactory#getFullAccount(com.jdon.jivejdon
	 * .model.Account)
	 */
	public Account getFullAccount(Account accountIn) {
		Debug.logVerbose("enter AccountFactory create", module);
		if (accountIn == null)
			return Account.createAnonymous();
		return !UtilValidate.isEmpty(accountIn.getUserId()) ? getFullAccount(accountIn.getUserId()) :
           !UtilValidate.isEmpty(accountIn.getUsername()) ? getFullAccountForUsername(accountIn.getUsername()) :
           !UtilValidate.isEmpty(accountIn.getEmail()) ? getFullAccountForEmail(accountIn.getEmail()) :
           Account.createAnonymous();
	}

	public Account getFullAccountForEmail(String email) {
		if (!illEscape.matcher(email).matches())
			return null;
		if (!EmailValidator.getInstance().isValid(email))
			return null;
		if (email.length() > 50)
			return null;

		Long userId = accountDao.fetchAccountByEmail(email);

		if (userId == null || userId <= 0) {
			return null;
		}

		return loadFullAccount(userId);

	}

	public Account getFullAccountForUsername(String username) {
		Account account = null;

		if (!illEscape.matcher(username).matches())
			return account;
		if (username.length() > 30)
			return account;
		Long userId = accountDao.fetchAccountByName(username);

		if (userId == null || userId <= 0) {
			return null;
		}

		return loadFullAccount(userId);
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.acccount.AccountFactory#getFullAccount(java.lang.
	 * String)
	 */
	public Account getFullAccount(String userId) {
		Debug.logVerbose("enter AccountFactory create", module);
		if (userId == null)
			return Account.createAnonymous();		
		return loadFullAccount(Long.valueOf(userId));
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
