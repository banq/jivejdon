package com.jdon.jivejdon.domain.command;

import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.attachment.AttachmentsVO;
import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.jivejdon.domain.model.property.MessagePropertysVO;

public class PostTopicMessageCommand {

    private final Long messageId;
    private final Forum forum;
    private final Account account;
    private final MessageVO messageVO;
    private final AttachmentsVO attachment;;
    private final MessagePropertysVO messagePropertysVO;
    private final String[] tagTitle;
    private final String token;

    public PostTopicMessageCommand(Long messageId, Forum forum, Account account, MessageVO messageVO, AttachmentsVO attachment, MessagePropertysVO
            messagePropertysVO, String[] tagTitle, String token) {
        this.messageId = messageId;
        this.forum = forum;
        this.account = account;
        this.messageVO = messageVO;
        this.attachment = attachment;
        this.messagePropertysVO = messagePropertysVO;
        this.tagTitle = tagTitle;
        this.token = token;
    }

    public Long getMessageId() {
        return messageId;
    }

    public Forum getForum() {
        return forum;
    }

    public Account getAccount() {
        return account;
    }

    public MessageVO getMessageVO() {
        return messageVO;
    }

    public AttachmentsVO getAttachment() {
        return attachment;
    }

    public MessagePropertysVO getMessagePropertysVO() {
        return messagePropertysVO;
    }

    public String[] getTagTitle() {
        return tagTitle;
    }

    public String getToken() {
        return token;
    }

    
}
