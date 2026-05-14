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
import com.jdon.jivejdon.domain.model.subscription.subscribed.AccountSubscribed;
import com.jdon.jivejdon.infrastructure.repository.dao.MessageQueryDao;
import com.jdon.jivejdon.spi.component.throttle.post.Throttler;
import com.jdon.jivejdon.spi.pubsub.reconstruction.LazyLoaderRole;
import com.jdon.jivejdon.util.Constants;

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

	private String password;

	private String username;

	private String email;

	private String roleName;

	private boolean emailVisible;

	private boolean emailValidate;

	private String creationDate;

	private String modifiedDate;

	private String postIP;

	private AccountMessageVO accountMessageVO;

	private SubscribedState subscribedState;

	private final AccountSMState accountSMState;

	private Reward reward;

	private boolean anonymous;

	private Attachment attachment;

	private boolean masked;

	private RoleLoader roleLoader;

	@Inject
	public LazyLoaderRole lazyLoaderRole;

	@Inject
	public UploadLazyLoader uploadLazyLoader;

	private final Object lock = new Object();

	/**
	 * Default constructor reserved for dynamic proxy / framework initialization.
	 */
	public Account() {
		accountSMState = new AccountSMState(this);
		this.anonymous = false;
	}

	/**
	 * Explicit constructor for code-level creation with initialized state.
	 */
	public Account(AccountSMState accountSMState) {
		this.accountSMState = accountSMState;
		this.anonymous = false;
	}

	public static Account createAnonymous() {
		Account account = new Account(null);
		account.anonymous = true;
		account.setUsername("anonymous");
		account.setUserIdLong(Long.valueOf(0));
		account.setEmail("anonymous@anonymous.com");
		account.setRoleName(Role.ANONYMOUS);
		account.setModifiedDate("");
		account.setCreationDate("");
		return account;
	}

	public Attachment getAttachment() {
		if (accountSMState == null) return null;
		if (attachment == null) {
			synchronized (lock) {
				if (attachment == null) {
					attachment = new Attachment(this.getUserIdLong(), this.uploadLazyLoader);
				}
			}
		}
		return attachment;
	}

	public AccountMessageVO getAccountMessageVO() {
		if (accountSMState == null) return null;	
		if (accountMessageVO == null) {
			synchronized (lock) {
				if (accountMessageVO == null) {
					accountMessageVO = new AccountMessageVO(this.getUserIdLong(), this.lazyLoaderRole);
				}
			}
		}
		return accountMessageVO;
	}

	public RoleLoader getRoleLoader() {	
		if (accountSMState == null) return null;
		if (roleLoader == null) {
			synchronized (lock) {
				if (roleLoader == null) {
					roleLoader = new RoleLoader(this.getUserIdLong(), this.lazyLoaderRole);
				}
			}
		}
		return roleLoader;
	}

	/**
	 * @return Returns the creationDate.
	 */
	public String getCreationDate() {
		try {
			if (creationDate != null && creationDate.length() != 0)
				return creationDate.substring(0, 11);
		} catch (Exception e) {
		}
		return "";
	}

	public Date getCreationDate2() {
		return Constants.parseDateTime(creationDate);
	}

	/**
	 * @param creationDate The creationDate to set.
	 */
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return Returns the emailVisible.
	 */
	public boolean isEmailVisible() {
		return emailVisible;
	}

	/**
	 * @param emailVisible The emailVisible to set.
	 */
	public void setEmailVisible(boolean emailVisible) {
		this.emailVisible = emailVisible;
	}

	/**
	 * @return Returns the modifiedDate.
	 */
	public String getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate The modifiedDate to set.
	 */
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return Returns the reward.
	 */
	public Reward getReward() {
		return reward;
	}

	/**
	 * @param reward The reward to set.
	 */
	public void setReward(Reward reward) {
		this.reward = reward;
	}

	/**
	 * @return Returns the postIP.
	 */
	public String getPostIP() {
		return postIP;
	}

	/**
	 * @param postIP The postIP to set.
	 */
	public void setPostIP(String postIP) {
		this.postIP = postIP;
	}

	/**
	 * @return Returns the userId.
	 */
	public Long getUserIdLong() {
		if (this.getUserId() != null)
			return Long.valueOf(this.getUserId());
		else
			return 0L;
	}

	/**
	 * @param userId The userId to set.
	 */
	public void setUserIdLong(Long userId) {
		if (userId != null)
			this.setUserId(userId.toString().trim());

	}

	/**
	 * @return Returns the messageCount.
	 */
	public int getMessageCount() {
		if (accountSMState == null) return 0;
		if (isAnonymous())
			return 0;
		if (lazyLoaderRole != null)
			return getAccountMessageVO().getMessageCount();
		else
			return 0;
	}

	public int getMessageCountNow(MessageQueryDao messageQueryDao) {
		if (accountSMState == null) return 0;
		if (isAnonymous())
			return 0;
		return messageQueryDao.getMessageCountOfUser(getUserIdLong());
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoleName() {
		if (accountSMState != null && this.roleName == null)
			this.roleName = getRoleLoader().getRoleName();
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAnonymous() {
		return anonymous;
	}



	public boolean isEmailValidate() {
		return emailValidate;
	}

	public void setEmailValidate(boolean emailValidate) {
		this.emailValidate = emailValidate;
	}

	public SubscribedState getSubscribedState() {
		if (accountSMState != null && subscribedState == null)
			subscribedState = new SubscribedState(new AccountSubscribed(this.getUserIdLong()));
		return subscribedState;
	}

	public int getSubscriptionCount() {
		try {
			if (accountSMState != null )
				return getSubscribedState().getSubscriptionCount(this.lazyLoaderRole);
			else
				return -1;
		} catch (Exception e) {
			return -1;
		}
	}

	public int getSubscribedCount() {
		try {
			if (accountSMState != null )
				return getSubscribedState().getSubscribedCount(this.lazyLoaderRole);
			else
				return -1;
		} catch (Exception e) {
			return -1;
		}
	}

	public void updateSubscriptionCount(int count) {
		getSubscribedState().update(count);
	}

	public void updateMessageCount(int count) {
		if (accountSMState != null)
			this.getAccountMessageVO().update(count);
	}

	/**
	 * <logic:notEmpty name="forumMessage" property="account.uploadFile"> <img src=
	 * "<%=request.getContextPath() %>/img/uploadShowAction.shtml?oid=<bean:write
	 * name=" forumMessage " property="account.userId"/>&id=<bean:write name=
	 * "forumMessage " property="account.uploadFile.id"/>" border='0' />
	 * </logic:notEmpty>
	 * 
	 * @return
	 */
	public UploadFile getUploadFile() {
		return accountSMState != null ? getAttachment().getUploadFile() : null;
	}

	public void setUploadFile(boolean update) {
		if (update && accountSMState != null)
			getAttachment().updateUploadFile();
	}

	public void reloadAccountSMState() {
		if (accountSMState != null)
		accountSMState.reload();
	}

	public void addOneNewMessage(int count) {
		if (accountSMState != null)
			accountSMState.addOneNewMessage(count);
	}

	public void addMessageObservable(Observable observable) {
		if (accountSMState != null)
			observable.addObserver(accountSMState);
	}

	public int getNewShortMessageCount() {
		return accountSMState != null ? accountSMState.getNewShortMessageCount() : 0;
	}

	public boolean isAdmin() {
		if (getRoleName() != null && getRoleName().equals(Role.ADMIN))
			return true;
		else
			return false;
	}

	public boolean isMasked() {
		return masked;
	}

	public void setMasked(boolean masked) {
		this.masked = masked;
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