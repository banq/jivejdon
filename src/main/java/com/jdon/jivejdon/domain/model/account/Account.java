package com.jdon.jivejdon.domain.model.account;

import java.util.Date;
import java.util.Observable;

import com.jdon.annotation.Model;
import com.jdon.annotation.model.Inject;
import com.jdon.jivejdon.domain.model.attachment.UploadFile;
import com.jdon.jivejdon.domain.model.auth.Role;
import com.jdon.jivejdon.domain.model.message.upload.UploadLazyLoader;
import com.jdon.jivejdon.domain.model.property.Reward;
import com.jdon.jivejdon.domain.model.shortmessage.AccountSMState;
import com.jdon.jivejdon.domain.model.subscription.SubscribedState;
import com.jdon.jivejdon.infrastructure.repository.dao.MessageQueryDao;
import com.jdon.jivejdon.spi.component.throttle.post.Throttler;
import com.jdon.jivejdon.spi.pubsub.reconstruction.LazyLoaderRole;

/**
 * we have a SSO server, all auth information will be save to the sso server,
 * and in this system, we keep some additional fields.
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
@Model
public class Account {
	private String userId;
	private String username;
	private String password;
	private String roleName;
	private String postIP;
	private boolean anonymous;
	private boolean masked;

	// 上一次重构引入的值对象
	private EmailInfo emailInfo = new EmailInfo("", false, false);
	private AuditTimeline auditTimeline = new AuditTimeline("", "");

	// === 保留这两个 DI 注入字段 ===
	@Inject
	public LazyLoaderRole lazyLoaderRole;

	@Inject
	public UploadLazyLoader uploadLazyLoader;

	// === 新引入的用于封装 Loader 操作的状态值对象 ===
	private AccountState accountState;

    private AccountSMState accountSMState;
	private Reward reward;

	public Account() {
		this.anonymous = false;
	}


	public static Account createAsDTO() {
		Account dto = new Account();
		dto.anonymous = false;
		return dto;
	}

	public static Account createAnonymous() {
		Account account = Account.createAsDTO();
		account.anonymous = true;
		account.setUsername("anonymous");
		account.setUserIdLong(0L);
		account.setRoleName(Role.ANONYMOUS);
		account.emailInfo = new EmailInfo("anonymous@anonymous.com", false, false);
		account.auditTimeline = new AuditTimeline("", "");
		return account;
	}

	/**
	 * 初始化或获取当前的 AccountState
	 * 因为容器注入（@Inject）是在构造函数之后发生的，所以采用延迟初始化的方式获取最新的注入实例
	 */
	private AccountState initOrGetState() {
		if (accountState == null) {
			accountState = new AccountState(this, this.lazyLoaderRole, this.uploadLazyLoader);
		}
		return accountState;
	}


	// ==========================================
	// 彻底重构：所有与 Loader 相关的逻辑全部委派给 AccountState
	// ==========================================

	public Attachment getAttachment() {
		this.accountState = initOrGetState().loadAttachment();
		return this.accountState.getAttachment();
	}

	public AccountMessageVO getAccountMessageVO() {
		this.accountState = initOrGetState().loadAccountMessageVO();
		return this.accountState.getAccountMessageVO();
	}

	public RoleLoader getRoleLoader() {
		this.accountState = initOrGetState().loadRoleLoader();
		return this.accountState.getRoleLoader();
	}

	public SubscribedState getSubscribedState() {
		this.accountState = initOrGetState().loadSubscribedState();
		return this.accountState.getSubscribedState();
	}

	public int getMessageCount() {
		return initOrGetState().getMessageCount();
	}

	public String getRoleName() {
		this.roleName = initOrGetState().getRoleName(this.roleName);
		return this.roleName;
	}

	public int getSubscriptionCount() {
		return initOrGetState().getSubscriptionCount();
	}

	public int getSubscribedCount() {
		return initOrGetState().getSubscribedCount();
	}

	public UploadFile getUploadFile() {
		return accountSMState != null && getAttachment() != null ? getAttachment().getUploadFile() : null;
	}

	public void setUploadFile(boolean update) {
		if (update && accountSMState != null && getAttachment() != null) {
			getAttachment().updateUploadFile();
		}
	}

	public void updateSubscriptionCount(int count) {
		if (getSubscribedState() != null) {
			getSubscribedState().update(count);
		}
	}

	public void updateMessageCount(int count) {
		if (accountSMState != null && getAccountMessageVO() != null) {
			this.getAccountMessageVO().update(count);
		}
	}

	// ==========================================
	// 其他基础属性与状态机逻辑（保持不变）
	// ==========================================

	public AccountSMState getAccountSMState() { return accountSMState; }
	public String getEmail() { return emailInfo.getAddress(); }
	public void setEmail(String email) { this.emailInfo = emailInfo.withAddress(email); }
	public boolean isEmailVisible() { return emailInfo.isVisible(); }
	public void setEmailVisible(boolean emailVisible) { this.emailInfo = emailInfo.withVisible(emailVisible); }
	public boolean isEmailValidate() { return emailInfo.isValidated(); }
	public void setEmailValidate(boolean emailValidate) { this.emailInfo = emailInfo.withValidated(emailValidate); }
	public String getCreationDate() { return auditTimeline.getShortCreationDate(); }
	public Date getCreationDate2() { return auditTimeline.getCreationDateTime(); }
	public void setCreationDate(String creationDate) { this.auditTimeline = new AuditTimeline(creationDate, this.auditTimeline.getModifiedDate()); }
	public String getModifiedDate() { return auditTimeline.getModifiedDate(); }
	public void setModifiedDate(String modifiedDate) { this.auditTimeline = new AuditTimeline(this.auditTimeline.getCreationDate(), modifiedDate); }
	public Reward getReward() { return reward; }
	public void setReward(Reward reward) { this.reward = reward; }
	public String getPostIP() { return postIP; }
	public void setPostIP(String postIP) { this.postIP = postIP; }
	public Long getUserIdLong() { return this.getUserId() != null ? Long.valueOf(this.getUserId()) : 0L; }
	public void setUserIdLong(Long userId) { if (userId != null) this.setUserId(userId.toString().trim()); }
	public int getMessageCountNow(MessageQueryDao messageQueryDao) { if (accountSMState == null || isAnonymous()) return 0; return messageQueryDao.getMessageCountOfUser(getUserIdLong()); }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public void setRoleName(String roleName) { this.roleName = roleName; }
	public String getUserId() { return userId; }
	public void setUserId(String userId) { this.userId = userId; }
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	public boolean isAnonymous() { return anonymous; }
	public boolean isAdmin() { return getRoleName() != null && getRoleName().equals(Role.ADMIN); }
	public boolean isMasked() { return masked; }
	public void setMasked(boolean masked) { this.masked = masked; }

	private AccountSMState initOrGetSMState() {
		if (accountSMState == null) {
			accountSMState = new AccountSMState(this, this.lazyLoaderRole);
		}
		return accountSMState;
	}

	// ==========================================
	// 状态机行为的委派 (全面引入延迟装载拦截)
	// ==========================================

	public void reloadAccountSMState() {
		initOrGetSMState().reload();
	}

	public void addOneNewMessage(int count) {
		initOrGetSMState().addOneNewMessage(count);
	}

	public void addMessageObservable(Observable observable) {
		// 这一步通常需要注册监听器，触发初始化
		observable.addObserver(initOrGetSMState());
	}

	public int getNewShortMessageCount() {
		return initOrGetSMState().getNewShortMessageCount();
	}

	/**
	 * post rule is business rule, refactoring from CUDInputInterceptor to here.
	 * called by CUDInputInterceptor.
	 * 
	 * @param methodNameNow
	 * @param throttler
	 * @return
	 */
	public boolean postIsAllowed(String methodNameNow, Throttler throttler, MessageQueryDao messageQueryDao) {
		boolean isAllowed = false;
		if (isMasked()) {
			throttler.blockIP(getPostIP());
			return isAllowed;
		}
		// if (methodNameNow.contains("create")) {
		if (methodNameNow.contains("createTopic") || methodNameNow.contains("createReply")) {
			if (getMessageCountNow(messageQueryDao) > throttler.getVipUserThrottleConf().getVipmessagecount())
				isAllowed = throttler.checkVIPValidate(this);
			else if (getMessageCountNow(messageQueryDao) <= throttler.getVipUserThrottleConf().getVipmessagecount()
					&& methodNameNow.contains("createTopic"))
				isAllowed = false;
			else {
				if (throttler.checkAllValidate())
					isAllowed = throttler.checkNewUserPostValidate(this);
			}
		} else
			isAllowed = !throttler.isAbusive(getPostIP());
		return isAllowed;
	}

}