package com.jdon.jivejdon.domain.model.account;

import com.jdon.jivejdon.domain.model.message.upload.UploadLazyLoader;
import com.jdon.jivejdon.domain.model.subscription.SubscribedState;
import com.jdon.jivejdon.domain.model.subscription.subscribed.AccountSubscribed;
import com.jdon.jivejdon.spi.pubsub.reconstruction.LazyLoaderRole;

public class AccountState {
    private final Account account;
    private final LazyLoaderRole lazyLoaderRole;
    private final UploadLazyLoader uploadLazyLoader;
    
    // 将原本 Account 内部的懒加载实例和锁转移到这里
    private final Attachment attachment;
    private final AccountMessageVO accountMessageVO;
    private final RoleLoader roleLoader;
    private final SubscribedState subscribedState;

    // 内部专用的并发锁
    private final Object lock = new Object();

    private LazyLoaderRole effectiveLazyLoaderRole() {
        return this.lazyLoaderRole != null ? this.lazyLoaderRole : account.lazyLoaderRole;
    }

    private UploadLazyLoader effectiveUploadLazyLoader() {
        return this.uploadLazyLoader != null ? this.uploadLazyLoader : account.uploadLazyLoader;
    }

    public AccountState(Account account, LazyLoaderRole lazyLoaderRole, UploadLazyLoader uploadLazyLoader) {
        this.account = account;
        this.lazyLoaderRole = lazyLoaderRole;
        this.uploadLazyLoader = uploadLazyLoader;
        this.attachment = null;
        this.accountMessageVO = null;
        this.roleLoader = null;
        this.subscribedState = null;
    }

    // 用于更新懒加载状态的私有构造函数（保持不可变性）
    private AccountState(Account account, LazyLoaderRole lazyLoaderRole, UploadLazyLoader uploadLazyLoader,
                         Attachment attachment, AccountMessageVO accountMessageVO, RoleLoader roleLoader, SubscribedState subscribedState) {
        this.account = account;
        this.lazyLoaderRole = lazyLoaderRole;
        this.uploadLazyLoader = uploadLazyLoader;
        this.attachment = attachment;
        this.accountMessageVO = accountMessageVO;
        this.roleLoader = roleLoader;
        this.subscribedState = subscribedState;
    }

    // ==========================================
    // 核心重构：内聚所有与这两个 Loader 相关的懒加载双重检查锁
    // ==========================================

    public AccountState loadAttachment() {
        if (this.attachment != null) return this;
        synchronized (lock) {
            if (this.attachment == null) {
                Attachment newAttachment = new Attachment(account.getUserIdLong(), effectiveUploadLazyLoader());
                return new AccountState(account, lazyLoaderRole, uploadLazyLoader, newAttachment, accountMessageVO, roleLoader, subscribedState);
            }
        }
        return this;
    }

    public AccountState loadAccountMessageVO() {
        if (this.accountMessageVO != null) return this;
        synchronized (lock) {
            if (this.accountMessageVO == null) {
                AccountMessageVO newMessageVO = new AccountMessageVO(account.getUserIdLong(), effectiveLazyLoaderRole());
                return new AccountState(account, lazyLoaderRole, uploadLazyLoader, attachment, newMessageVO, roleLoader, subscribedState);
            }
        }
        return this;
    }

    public AccountState loadRoleLoader() {
        if (this.roleLoader != null) return this;
        synchronized (lock) {
            if (this.roleLoader == null) {
                RoleLoader newRoleLoader = new RoleLoader(account.getUserIdLong(), effectiveLazyLoaderRole());
                return new AccountState(account, lazyLoaderRole, uploadLazyLoader, attachment, accountMessageVO, newRoleLoader, subscribedState);
            }
        }
        return this;
    }

    public AccountState loadSubscribedState() {
        if (this.subscribedState != null) return this;
        synchronized (lock) {
            if (this.subscribedState == null) {
                SubscribedState newState = new SubscribedState(new AccountSubscribed(account.getUserIdLong()));
                return new AccountState(account, lazyLoaderRole, uploadLazyLoader, attachment, accountMessageVO, roleLoader, newState);
            }
        }
        return this;
    }

    // ==========================================
    // 涉及 Loader 的业务计算逻辑
    // ==========================================

    public int getMessageCount() {
        if (effectiveLazyLoaderRole() != null) {
            // 这里会确保自身已经加载了 accountMessageVO
            return this.loadAccountMessageVO().getAccountMessageVO().getMessageCount();
        }
        return 0;
    }

    public String getRoleName(String currentRoleName) {
        if (currentRoleName == null) {
            return this.loadRoleLoader().getRoleLoader().getRoleName();
        }
        return currentRoleName;
    }

    public int getSubscriptionCount() {
        try {
                return this.loadSubscribedState().getSubscribedState().getSubscriptionCount(effectiveLazyLoaderRole());
        } catch (Exception e) {
            return -1;
        }
    }

    public int getSubscribedCount() {
        try {
                return this.loadSubscribedState().getSubscribedState().getSubscribedCount(effectiveLazyLoaderRole());
        } catch (Exception e) {
            return -1;
        }
    }

    // ==========================================
    // Getters & 基础委派
    // ==========================================

    public Attachment getAttachment() { return attachment; }
    public AccountMessageVO getAccountMessageVO() { return accountMessageVO; }
    public RoleLoader getRoleLoader() { return roleLoader; }
    public SubscribedState getSubscribedState() { return subscribedState; }
}
