package com.jdon.jivejdon.domain.command;

import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.attachment.AttachmentsVO;
import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.jivejdon.domain.model.property.MessagePropertysVO;

public class ReviseForumMessageCommand {

    private final ForumMessage oldforumMessage;
    private final MessageVO messageVO;
    private final AttachmentsVO attachment;
    private final MessagePropertysVO messagePropertysVO;


    public ReviseForumMessageCommand(ForumMessage oldforumMessage, MessageVO messageVO, AttachmentsVO attachment, MessagePropertysVO
            messagePropertysVO) {
        this.oldforumMessage = oldforumMessage;
        this.messageVO = messageVO;
        this.attachment = attachment;
        this.messagePropertysVO = messagePropertysVO;
    }

    public ForumMessage getOldforumMessage() {
        return oldforumMessage;
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
}
