package com.jdon.jivejdon.domain.model;

import java.util.Collection;

import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.attachment.UploadFile;
import com.jdon.jivejdon.domain.model.message.FilterPipleSpec;
import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.jivejdon.domain.model.property.HotKeys;
import com.jdon.jivejdon.domain.model.property.Property;

/**
 * RootMessage is the root entity of aggregates! default ForumMessage is a
 * RootMessage. RootMessage make aggregates objects just like Factory for
 * aggregates
 */
public class RootMessage {
    protected volatile boolean isCreated;

    public boolean isCreated() {
        return isCreated;
    }

    public static RequireMessageId messageBuilder() {
        return messageId -> parentMessage -> messageVO -> forum -> forumThread -> account -> creationDate -> modifiedDate -> filterPipleSpec -> uploads -> properties -> hotKeys -> new FinalStageVO(
                messageId, parentMessage, messageVO, forum, forumThread, account, creationDate, modifiedDate,
                filterPipleSpec, uploads, properties, hotKeys);
    }

    @FunctionalInterface
    public interface RequireMessageId {
        RequireParentMessage messageId(long messageId);
    }

    @FunctionalInterface
    public interface RequireParentMessage {
        RequireMessageVO parentMessage(ForumMessage parentMessage);
    }

    @FunctionalInterface
    public interface RequireMessageVO {
        RequireForum messageVO(MessageVO messageVO);
    }

    @FunctionalInterface
    public interface RequireForum {
        RequireForumThread forum(Forum forum);
    }

    @FunctionalInterface
    public interface RequireForumThread {
        RequireAccount forumThread(ForumThread forumThread);
    }

    @FunctionalInterface
    public interface RequireAccount {
        RequireCreationDate acount(Account account);
    }

    @FunctionalInterface
    public interface RequireCreationDate {
        RequireModifiedDate creationDate(String creationDate);
    }

    @FunctionalInterface
    public interface RequireModifiedDate {
        RequireFilterPipleSpec modifiedDate(long modifiedDate);
    }

    @FunctionalInterface
    public interface RequireFilterPipleSpec {
        OptionsUploadFile filterPipleSpec(FilterPipleSpec filterPipleSpec);
    }

    @FunctionalInterface
    public interface OptionsUploadFile {
        OptionsProperties uploads(Collection<UploadFile> uploads);
    }

    @FunctionalInterface
    public interface OptionsProperties {
        OptionsHotKeys props(Collection<Property> props);
    }

    @FunctionalInterface
    public interface OptionsHotKeys {
        FinalStageVO hotKeys(HotKeys hotKeys);
    }

    @FunctionalInterface
    public interface RequireSubject {
        MessageVO.RequireBody subject(String subject);
    }

    @FunctionalInterface
    public interface RequireBody {
        MessageVO.MessageVOFinalStage body(String body);
    }

    public static class FinalStageVO {
        private final long messageId;
        private final ForumMessage parentMessage;
        private final MessageVO messageVO;
        private final Account account;
        private final String creationDate;
        private final long modifiedDate;
        private final Forum forum;
        private final ForumThread forumThread;
        private final FilterPipleSpec filterPipleSpec;
        private final Collection<UploadFile> uploads;
        private final Collection<Property> props;
        private final HotKeys hotKeys;

        public FinalStageVO(long messageId, ForumMessage parentMessage, MessageVO messageVO, Forum forum,
                ForumThread forumThread, Account account, String creationDate, long modifiedDate,
                FilterPipleSpec filterPipleSpec, Collection<UploadFile> uploads, Collection<Property> props,
                HotKeys hotKeys) {
            this.messageId = messageId;
            this.parentMessage = parentMessage;
            this.messageVO = messageVO;
            this.account = account;
            this.creationDate = creationDate;
            this.modifiedDate = modifiedDate;
            this.forum = forum;
            this.forumThread = forumThread;
            this.filterPipleSpec = filterPipleSpec;
            this.uploads = uploads;
            this.props = props;
            this.hotKeys = hotKeys;
        }

        public ForumMessage build() {
            try {
                if (parentMessage != null) {
                    ForumMessageReply forumMessageRely = new ForumMessageReply();
                    forumMessageRely.build(messageId, messageVO, forum, forumThread, account, creationDate,
                            modifiedDate, filterPipleSpec, uploads, props, hotKeys, parentMessage);
                    return forumMessageRely;
                } else {
                    ForumMessage forumMessage = new ForumMessage();
                    forumMessage.build(messageId, messageVO, forum, forumThread, account, creationDate, modifiedDate,
                            filterPipleSpec, uploads, props, hotKeys);
                    return forumMessage;
                }
            } catch (Exception e) {
                System.err.println("build Exception:" + e.getMessage() + " messageId=" + messageId);
                return null;
            }

        }
    }

}