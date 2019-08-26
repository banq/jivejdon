package com.jdon.jivejdon.model.message;

import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.attachment.AttachmentsVO;

/**
 * used for presentation MessageForm
 * and for persistence MessageCore of jiveMessage database table
 */
public class AnemicMessage {
    private Long messageId;
    private String creationDate;
    private long modifiedDate;
    private ForumThread forumThread;
    private Forum forum;
    private Account account;
    private MessageVO messageVO;
    private AnemicMessage parentMessage;
    // for upload files
    private AttachmentsVO attachment;;
    private boolean authenticated = true;// default true

    // not let messageVo be Filtered , it should be save to DB.
    private boolean messageVOFiltered = true;
    private boolean masked;
    private boolean replyNotify;
    private String[] tagTitles;

    public AnemicMessage() {
        messageVO = new MessageVO();

        forum = new Forum(); // for parameter forum.forumId=xxx
        forumThread = new ForumThread();
        account = new Account();
        parentMessage = new AnemicMessage();

        forumThread = new ForumThread();
    }

    public AnemicMessage(Long messageId) {
       super();
       this.messageId = messageId;
    }


    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public ForumThread getForumThread() {
        return forumThread;
    }

    public void setForumThread(ForumThread forumThread) {
        this.forumThread = forumThread;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public AnemicMessage getParentMessage() {
        return parentMessage;
    }

    public void setParentMessage(AnemicMessage parentMessage) {
        this.parentMessage = parentMessage;
    }

    public AttachmentsVO getAttachment() {
        return attachment;
    }

    public void setAttachment(AttachmentsVO attachment) {
        this.attachment = attachment;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public MessageVO getMessageVO() {
        return messageVO;
    }

    public void setMessageVO(MessageVO messageVO) {
        this.messageVO = messageVO;
    }

    public boolean isMessageVOFiltered() {
        return messageVOFiltered;
    }

    public void setMessageVOFiltered(boolean messageVOFiltered) {
        this.messageVOFiltered = messageVOFiltered;
    }

    public boolean isMasked() {
        return masked;
    }

    public void setMasked(boolean masked) {
        this.masked = masked;
    }

    public boolean isReplyNotify() {
        return replyNotify;
    }

    public void setReplyNotify(boolean replyNotify) {
        this.replyNotify = replyNotify;
    }

    public String[] getTagTitles() {
        return tagTitles;
    }

    public void setTagTitles(String[] tagTitles) {
        this.tagTitles = tagTitles;
    }


}
