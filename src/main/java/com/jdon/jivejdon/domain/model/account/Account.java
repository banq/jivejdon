package com.jdon.jivejdon.domain.model.account;

import java.util.Date;
import java.util.Observable;
import java.util.Optional;

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

	// === 内部标志：区分Entity和DTO ===
	private boolean isDTO = false;

	// === 保留这两个 DI 注入字段 ===
	@Inject
	public LazyLoaderRole lazyLoaderRole;

	@Inject
	public UploadLazyLoader uploadLazyLoader;

	// === 新引入的用于封装 Loader 操作的状态值对象 ===
	private AccountState accountState;

    private AccountSMState accountSMState;
	private Reward reward;

	private Account() {
		this.anonymous = false;
	}

    public static Account createAsEntity() {
		return new Account();
	}	


	public static Account createAsDTO() {
		Account dto = new Account();
		dto.isDTO = true;  // 标记为DTO模式
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
	 * 只有当：
	 * 1. 不是DTO模式
	 * 2. lazyLoaderRole 和 uploadLazyLoader 都已注入
	 * 时，才创建 AccountState
	 */
	private Optional<AccountState> initOrGetState() {
		if (isDTO || this.lazyLoaderRole == null || this.uploadLazyLoader == null) {
			return Optional.empty();
		}
		if (accountState == null) {
			accountState = new AccountState(this, this.lazyLoaderRole, this.uploadLazyLoader);
		}
		return Optional.of(accountState);
	}


	// ==========================================
	// 彻底重构：所有与 Loader 相关的逻辑全部委派给 AccountState
	// ==========================================

	public Attachment getAttachment() {
		return initOrGetState()
			.map(state -> this.accountState = state.loadAttachment())
			.map(AccountState::getAttachment)
			.orElse(null);
	}

	public AccountMessageVO getAccountMessageVO() {
		return initOrGetState()
			.map(state -> this.accountState = state.loadAccountMessageVO())
			.map(AccountState::getAccountMessageVO)
			.orElse(null);
	}

	public RoleLoader getRoleLoader() {
		return initOrGetState()
			.map(state -> this.accountState = state.loadRoleLoader())
			.map(AccountState::getRoleLoader)
			.orElse(null);
	}

	public SubscribedState getSubscribedState() {
		return initOrGetState()
			.map(state -> this.accountState = state.loadSubscribedState())
			.map(AccountState::getSubscribedState)
			.orElse(null);
	}

	public int getMessageCount() {
		return initOrGetState()
			.map(AccountState::getMessageCount)
			.orElse(0);
	}

	public String getRoleName() {
		initOrGetState()
			.ifPresent(state -> this.roleName = state.getRoleName(this.roleName));
		return this.roleName;
	}

	public int getSubscriptionCount() {
		return initOrGetState()
			.map(AccountState::getSubscriptionCount)
			.orElse(-1);
	}

	public int getSubscribedCount() {
		return initOrGetState()
			.map(AccountState::getSubscribedCount)
			.orElse(-1);
	}

	public UploadFile getUploadFile() {
		return getAttachment() != null ? getAttachment().getUploadFile() : null;
	}

	public void setUploadFile(boolean update) {
		if (update  && getAttachment() != null) {
			getAttachment().updateUploadFile();
		}
	}

	public void updateSubscriptionCount(int count) {
		if (getSubscribedState() != null) {
			getSubscribedState().update(count);
		}
	}

	public void updateMessageCount(int count) {
		if (getAccountMessageVO() != null) {
			this.getAccountMessageVO().update(count);
		}
	}

	// ==========================================
	// 其他基础属性与状态机逻辑（保持不变）
	// ==========================================

	public AccountSMState getAccountSMState() {
		return initOrGetSMState()
			.orElse(null);
	}
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
	
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public String getUserId() { return userId; }
	public void setUserId(String userId) { this.userId = userId; }
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	public boolean isAnonymous() { return anonymous; }
	public boolean isAdmin() { return getRoleName() != null && getRoleName().equals(Role.ADMIN); }
	public boolean isMasked() { return masked; }
	public void setMasked(boolean masked) { this.masked = masked; }

	public void setRoleName(String roleName) { 
		// 只有在非DTO且 accountState 还没有初始化时允许直接设值
		if (!isDTO && this.accountState != null) {
			throw new IllegalStateException("当前账户已进入业务激活状态，不允许直接通过 setRoleName 修改角色！");
		}
		this.roleName = roleName; 
	}

	private Optional<AccountSMState> initOrGetSMState() {
		if (isDTO || this.lazyLoaderRole == null) {
			return Optional.empty();
		}
		if (accountSMState == null) {
			accountSMState = new AccountSMState(this, this.lazyLoaderRole);
		}
		return Optional.of(accountSMState);
	}

	// ==========================================
	// 状态机行为的委派 (全面引入延迟装载拦截)
	// ==========================================

	public void reloadAccountSMState() {
		initOrGetSMState().ifPresent(AccountSMState::reload);
	}

	public void addOneNewMessage(int count) {
		initOrGetSMState().ifPresent(sm -> sm.addOneNewMessage(count));
	}

	public void addMessageObservable(Observable observable) {
		// 这一步通常需要注册监听器，触发初始化
		initOrGetSMState().ifPresent(sm -> observable.addObserver(sm));
	}

	public int getNewShortMessageCount() {
		return initOrGetSMState()
			.map(AccountSMState::getNewShortMessageCount)
			.orElse(-1);
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
			if (messageQueryDao.getMessageCountOfUser(getUserIdLong()) > throttler.getVipUserThrottleConf().getVipmessagecount())
				isAllowed = throttler.checkVIPValidate(this);
			else if (messageQueryDao.getMessageCountOfUser(getUserIdLong()) <= throttler.getVipUserThrottleConf().getVipmessagecount()
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