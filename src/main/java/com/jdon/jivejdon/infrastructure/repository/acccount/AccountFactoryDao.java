package com.jdon.jivejdon.infrastructure.repository.acccount;

import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.auth.Role;
import com.jdon.jivejdon.infrastructure.repository.dao.AccountDao;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.util.Debug;
import com.jdon.util.UtilValidate;
import org.apache.commons.validator.EmailValidator;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

public class AccountFactoryDao implements AccountFactory, Startable {
	private final static String module = AccountFactoryDao.class.getName();
	private final static Pattern illEscape = Pattern.compile("[^\\/|^\\<|^\\>|^\\=|^\\?|^\\\\|^\\s|^\\(|^\\)|^\\{|^\\}|^\\[|^\\]|^\\+|^\\*]*");

	private AccountDao accountDao;

	private ContainerUtil containerUtil;
	
	private final Account anonymous;
	
	// 缓存 username -> userId 的映射，防止并发重复查询数据库
	private final ConcurrentMap<String, Long> usernameCacheMap = new ConcurrentHashMap<>();
	
	// 缓存 email -> userId 的映射，防止并发重复查询数据库
	private final ConcurrentMap<String, Long> emailCacheMap = new ConcurrentHashMap<>();
	
	// 缓存 userId -> Account 的映射，防止并发重复创建 Account 实例
	private final ConcurrentMap<String, Account> accountCacheMap = new ConcurrentHashMap<>();

	public AccountFactoryDao(AccountDao accountDao, ContainerUtil containerUtil) {
		this.accountDao = accountDao;
		this.containerUtil = containerUtil;
		this.anonymous = this.createAnonymous();
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
		// 使用 computeIfAbsent 确保同一 email 只查询一次数据库，避免并发重复创建
		Long userId = emailCacheMap.computeIfAbsent(email, this::fetchUserIdByEmail);
		if (userId != null && userId > 0) // ensure one instance in cache for one key;
			return accountDao.getAccount(Long.toString(userId));
		else
			return null;

	}
	
	private Long fetchUserIdByEmail(String email) {
		Long userId = accountDao.fetchAccountByEmail(email);
		return userId != null ? userId : -1L; // 用 -1 表示查询过但不存在，避免重复查询
	}

	public Account getFullAccountForUsername(String username) {
		Account account = null;

		if (!illEscape.matcher(username).matches())
			return account;
		if (username.length() > 30)
			return account;
		try {
			// 使用 computeIfAbsent 确保同一 username 只查询一次数据库，避免并发重复创建
			Long userId = usernameCacheMap.computeIfAbsent(username, this::fetchUserIdByUsername);
			if (userId != null && userId > 0) {// ensure one instance in cache for one key;
				account = accountDao.getAccount(Long.toString(userId));
				if (account != null && !account.getUsername().equalsIgnoreCase(username)) {
					Debug.logError("the user is wrong, username=" + username + " userId=" + userId + " account username=" + account.getUsername(),
							module);
					containerUtil.clearCache(userId);
					usernameCacheMap.remove(username); // 清除错误的缓存
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
	
	private Long fetchUserIdByUsername(String username) {
		Long userId = accountDao.fetchAccountByName(username);
		return userId != null ? userId : -1L; // 用 -1 表示查询过但不存在，避免重复查询
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
			return this.anonymous;
		// 使用 computeIfAbsent 确保同一 userId 只加载一次，避免并发重复创建 Account 实例
		return accountCacheMap.computeIfAbsent(userId, this::fetchAccountById);
	}
	
	private Account fetchAccountById(String userId) {
		Account account = accountDao.getAccount(userId);
		if (account == null) {
			Debug.logVerbose("the user has been delete, it is Anonymous, userId=" + userId, module);
			account = this.anonymous;
		}
		return account;
	}

	private Account createAnonymous() {
		Account account = new Account(null);
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
		usernameCacheMap.clear();
		emailCacheMap.clear();
		accountCacheMap.clear();

	}

}
