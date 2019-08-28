package com.jdon.jivejdon.model.message;

import com.jdon.annotation.Model;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.attachment.AttachmentsVO;
import com.jdon.jivejdon.model.proptery.MessagePropertysVO;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableComponent;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableReference;

/**
 * ForumMessage DTO, it is a anemic model
 * used for presentation MessageForm
 * and for persistence MessageCore of jiveMessage database table
 */
@Model  //for edit , is a jdon-struts bug
@Searchable
public class AnemicMessageDTO {
    @SearchableId
    private Long messageId;
    private String creationDate;
    private long modifiedDate;
    private ForumThread forumThread;
    @SearchableReference
    private Forum forum;
    private Account account;
    @SearchableComponent
    private MessageVO messageVO;
    private AnemicMessageDTO parentMessage;
    // for upload files
    private AttachmentsVO attachment;;
    private MessagePropertysVO messagePropertysVO;
    private Account operator; // operator this message,maybe Admin or others;
    private boolean authenticated = true;// default true

    // not let messageVo be Filtered , it should be save to DB.
    private boolean messageVOFiltered = true;
    private boolean masked;
    private boolean replyNotify;
    //for messageForm inject
    private String[] tagTitle;

    public AnemicMessageDTO() {
        messageVO = new MessageVO();

        forum = new Forum(); // for parameter forum.forumId=xxx
        forumThread = new ForumThread();
        account = new Account();
        forumThread = new ForumThread();
    }

    public AnemicMessageDTO(Long messageId) {
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

    public AnemicMessageDTO getParentMessage() {
        return parentMessage;
    }

    public void setParentMessage(AnemicMessageDTO parentMessage) {
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

    public String[] getTagTitle() {
        return tagTitle;
    }

    public void setTagTitle(String[] tagTitle) {
        this.tagTitle = tagTitle;
    }



    public MessagePropertysVO getMessagePropertysVO() {
        return messagePropertysVO;
    }

    public void setMessagePropertysVO(MessagePropertysVO messagePropertysVO) {
        this.messagePropertysVO = messagePropertysVO;
    }

    public Account getOperator() {
        return operator;
    }

    public void setOperator(Account operator) {
        this.operator = operator;
    }
}
