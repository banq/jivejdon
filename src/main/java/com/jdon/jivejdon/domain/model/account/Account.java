package com.jdon.jivejdon.domain.model.account;

import com.jdon.annotation.Model;
import com.jdon.annotation.model.Inject;
import com.jdon.jivejdon.spi.component.throttle.post.Throttler;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.spi.pubsub.reconstruction.LazyLoaderRole;
import com.jdon.jivejdon.domain.model.attachment.UploadFile;
import com.jdon.jivejdon.domain.model.auth.Role;
import com.jdon.jivejdon.domain.model.message.upload.UploadLazyLoader;
import com.jdon.jivejdon.domain.model.property.Reward;
import com.jdon.jivejdon.domain.model.shortmessage.AccountSMState;
import com.jdon.jivejdon.domain.model.subscription.SubscribedState;
import com.jdon.jivejdon.domain.model.subscription.subscribed.AccountSubscribed;

import java.util.Date;
import java.util.Observable;

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

	public Account() {

		accountSMState = new AccountSMState(this);
	}

	public Attachment getAttachment() {
		if (attachment == null)
			attachment = new Attachment(this.getUserIdLong(), this.uploadLazyLoader);
		return attachment;
	}

	public AccountMessageVO getAccountMessageVO() {
		if (accountMessageVO == null)
			accountMessageVO = new AccountMessageVO(this.getUserIdLong(), this.lazyLoaderRole);
		return accountMessageVO;
	}

	public RoleLoader getRoleLoader() {
		if (roleLoader == null)
			roleLoader = new RoleLoader(this.getUserIdLong(), this.lazyLoaderRole);
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
	 * @param creationDate
	 *            The creationDate to set.
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
	 * @param emailVisible
	 *            The emailVisible to set.
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
	 * @param modifiedDate
	 *            The modifiedDate to set.
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
	 * @param reward
	 *            The reward to set.
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
	 * @param postIP
	 *            The postIP to set.
	 */
	public void setPostIP(String postIP) {
		this.postIP = postIP;
	}

	/**
	 * @return Returns the userId.
	 */
	public Long getUserIdLong() {
		if (this.getUserId() != null)
			return new Long(this.getUserId());
		else
			return null;
	}

	/**
	 * @param userId
	 *            The userId to set.
	 */
	public void setUserIdLong(Long userId) {
		if (userId != null)
			this.setUserId(userId.toString().trim());

	}

	/**
	 * @return Returns the messageCount.
	 */
	public int getMessageCount() {
		if (isAnonymous())
			return 0;
		if (lazyLoaderRole != null)
			return getAccountMessageVO().getMessageCount();
		else
			return 0;
	}

	public int getMessageCountNow() {
		if (isAnonymous())
			return 0;
		if (lazyLoaderRole != null)
			return getAccountMessageVO().getMessageCountNow();
		else
			return 0;
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
		if (lazyLoaderRole != null && this.roleName == null)
			return getRoleLoader().getRoleName();
		else
			return "User";
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

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}

	public boolean isEmailValidate() {
		return emailValidate;
	}

	public void setEmailValidate(boolean emailValidate) {
		this.emailValidate = emailValidate;
	}

	public SubscribedState getSubscribedState() {
		if (subscribedState == null)
			subscribedState = new SubscribedState(new AccountSubscribed(this.getUserIdLong()));
		return subscribedState;
	}

	public int getSubscriptionCount() {
		try {
			if (this.lazyLoaderRole != null)
				return getSubscribedState().getSubscriptionCount(this.lazyLoaderRole);
			else
				return -1;
		} catch (Exception e) {
			return -1;
		}
	}

	public int getSubscribedCount() {
		try {
			if (this.lazyLoaderRole != null)
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
		this.getAccountMessageVO().update(count);
	}

	/**
	 * <logic:notEmpty name="forumMessage" property="account.uploadFile"> <img
	 * src=
	 * "<%=request.getContextPath() %>/img/uploadShowAction.shtml?oid=<bean:write name="
	 * forumMessage
	 * " property="account.userId"/>&id=<bean:write name="forumMessage
	 * " property="account.uploadFile.id"/>" border='0' /> </logic:notEmpty>
	 * 
	 * @return
	 */
	public UploadFile getUploadFile() {
		return getAttachment().getUploadFile();
	}

	public void setUploadFile(boolean update) {
		if (update)
			getAttachment().updateUploadFile();
	}

	public void reloadAccountSMState() {
		accountSMState.reload();
	}

	public void addOneNewMessage(int count) {
		accountSMState.addOneNewMessage(count);
	}

	public void addMessageObservable(Observable observable) {
		observable.addObserver(accountSMState);
	}

	public int getNewShortMessageCount() {
		return accountSMState.getNewShortMessageCount();
	}

	public boolean isAdmin() {
		if (this.getRoleName().equals(Role.ADMIN))
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
     * @param methodNameNow
     * @param throttler
     * @return
     */
	public boolean postIsAllowed(String methodNameNow,Throttler throttler) {
		boolean isAllowed = false;
		if (isMasked()) {
			throttler.blockIP(getPostIP());
			return isAllowed;
		}
		//	if (methodNameNow.contains("create")) {
		if (methodNameNow.contains("createTopic") || methodNameNow.contains("createReply")) {
			if (getMessageCount() > throttler.getVipUserThrottleConf().getVipmessagecount())
				isAllowed = throttler.checkVIPValidate(this);
			else if(getMessageCount() <= throttler.getVipUserThrottleConf().getVipmessagecount() && methodNameNow.contains("createTopic") )
				isAllowed = false;
			else
				isAllowed = throttler.checkNewUserPostValidate(this);
		} else
			isAllowed = !throttler.isAbusive(getPostIP());
		return isAllowed;
	}

}