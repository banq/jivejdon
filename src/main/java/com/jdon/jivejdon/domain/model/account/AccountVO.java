package com.jdon.jivejdon.domain.model.account;

import java.util.Date;

import com.jdon.jivejdon.domain.model.property.Reward;
import com.jdon.jivejdon.util.Constants;

public class AccountVO {

    private String userId;

    private String password;

    private String username;

    private String email;

    private boolean emailVisible;

    private boolean emailValidate;

    private String creationDate;

    private String modifiedDate;

    private String postIP;


    private Reward reward;


    private boolean masked;


    private final Object lock = new Object();

    /**
	 * Default constructor reserved for dynamic proxy / framework initialization.
	 */
	public AccountVO() {
		
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


    public boolean isEmailValidate() {
        return emailValidate;
    }

    public void setEmailValidate(boolean emailValidate) {
        this.emailValidate = emailValidate;
    }


    public boolean isMasked() {
        return masked;
    }

    public void setMasked(boolean masked) {
        this.masked = masked;
    }

}
