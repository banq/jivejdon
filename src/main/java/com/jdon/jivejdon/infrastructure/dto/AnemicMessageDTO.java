package com.jdon.jivejdon.infrastructure.dto;

import com.jdon.jivejdon.domain.command.PostTopicMessageCommand;
import com.jdon.jivejdon.domain.command.ReviseForumMessageCommand;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.attachment.AttachmentsVO;
import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.jivejdon.domain.model.property.MessagePropertysVO;

/**
 * ForumMessage DTO, it is a anemic model used for presentation MessageForm and
 * for persistence MessageCore of jiveMessage database table
 */
public class AnemicMessageDTO {
    private Long messageId;
    private String creationDate;
    private long modifiedDate;
    private ForumThread forumThread;
    private Forum forum;
    private Account account;
    private MessageVO messageVO;
    private AnemicMessageDTO parentMessage;
    // for upload files
    private AttachmentsVO attachment;;
    private MessagePropertysVO messagePropertysVO;
    private Account operator; // operator this message,maybe Admin or others;
    private boolean authenticated;// default false

    private boolean masked;
    private boolean replyNotify;
    // for messageForm inject
    private String[] tagTitle;

    public AnemicMessageDTO() {
        messageVO = new MessageVO();
        //
        // forum = new Forum(); // for parameter forum.forumId=xxx
        // account = new Account();
        // forumThread = new ForumThread();
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

    public static AnemicMessageDTO commandToDTO(PostTopicMessageCommand postTopicMessageCommand) {
        AnemicMessageDTO forumMessageDTO = new AnemicMessageDTO(postTopicMessageCommand.getMessageId());
        forumMessageDTO.setAccount(postTopicMessageCommand.getAccount());
        forumMessageDTO.setMessageVO(postTopicMessageCommand.getMessageVO());
        forumMessageDTO.setAttachment(postTopicMessageCommand.getAttachment());
        forumMessageDTO.setForum(postTopicMessageCommand.getForum());
        forumMessageDTO.setMessagePropertysVO(postTopicMessageCommand.getMessagePropertysVO());
        forumMessageDTO.setTagTitle(postTopicMessageCommand.getTagTitle());
        return forumMessageDTO;
    }

    public static AnemicMessageDTO commandToDTO(ReviseForumMessageCommand reviseForumMessageCommand) {
        AnemicMessageDTO forumMessageDTO = new AnemicMessageDTO(
                reviseForumMessageCommand.getOldforumMessage().getMessageId());
        forumMessageDTO.setAccount(reviseForumMessageCommand.getOldforumMessage().getAccount());
        forumMessageDTO.setMessageVO(reviseForumMessageCommand.getMessageVO());
        forumMessageDTO.setAttachment(reviseForumMessageCommand.getAttachment());
        forumMessageDTO.setForum(reviseForumMessageCommand.getOldforumMessage().getForum());
        forumMessageDTO.setForumThread(reviseForumMessageCommand.getOldforumMessage().getForumThread());
        forumMessageDTO.setMessagePropertysVO(reviseForumMessageCommand.getMessagePropertysVO());
        forumMessageDTO.setTagTitle(reviseForumMessageCommand.getOldforumMessage().getTagTitle());
        return forumMessageDTO;
    }

}
