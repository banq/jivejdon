package com.jdon.jivejdon.domain.command;

import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.attachment.AttachmentsVO;
import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.jivejdon.domain.model.property.MessagePropertysVO;

public class PostRepliesMessageCommand extends PostTopicMessageCommand {

    private final ForumMessage parentMessage;

    public PostRepliesMessageCommand(ForumMessage parentMessage, Long messageId, Account account, MessageVO messageVO, AttachmentsVO attachment,
                                     MessagePropertysVO messagePropertysVO, String[] tagTitle) {
        super(messageId, parentMessage.getForum(), account, messageVO, attachment, messagePropertysVO, tagTitle, null);
        this.parentMessage = parentMessage;
    }

    public ForumMessage getParentMessage() {
        return parentMessage;
    }
}
