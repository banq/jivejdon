/*
 * Copyright 2003-2005 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.model;

import com.jdon.annotation.Model;
import com.jdon.annotation.model.Inject;
import com.jdon.annotation.model.OnCommand;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.event.domain.producer.read.LazyLoaderRole;
import com.jdon.jivejdon.event.domain.producer.write.MessageEventSourcingRole;
import com.jdon.jivejdon.event.domain.producer.write.ShortMPublisherRole;
import com.jdon.jivejdon.model.attachment.AttachmentsVO;
import com.jdon.jivejdon.model.attachment.UploadFile;
import com.jdon.jivejdon.model.event.MessagePropertiesUpdatedEvent;
import com.jdon.jivejdon.model.event.MessageUpdatedEvent;
import com.jdon.jivejdon.model.event.ReplyMessageCreatedEvent;
import com.jdon.jivejdon.model.event.UploadFilesSavedEvent;
import com.jdon.jivejdon.model.message.AnemicMessageDTO;
import com.jdon.jivejdon.model.message.FilterPipleSpec;
import com.jdon.jivejdon.model.message.MessageUrlVO;
import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.jivejdon.model.proptery.MessagePropertysVO;
import com.jdon.jivejdon.model.reblog.ReBlogVO;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Aggregate Root + DTO
 * <p>
 * 1. This model  is Aggregate Root Entity when being RootMessage in ForumThread.
 * ForumThread is another Aggregate Root; in this system, there are two aggregate roots.
 * like Car and Enginee!
 * <p>
 * 2. this model act as data transfer object(DTO) that is from UI to domain.
 * <p>
 * so this model acts two kinds of Actor in two diiferent flows:
 * 1. In from repository to domain flow, It is aggregateRoot Entity;
 * 2. In from domain to UI flow ,It is DTO;
 * <p>
 * there are two construct methods in two flows.
 * 1. using builder() and ForumModel's solid state when in first flow
 * 2. using business special method such as addChild() and ForumModel's parameter state when in
 * second flow!
 *
 * @author <a href="mailto:banq@163.com">banq</a>
 */
@Model
public class ForumMessage extends ForumModel implements Cloneable {
    private static final long serialVersionUID = 1L;
    @Inject
    public LazyLoaderRole lazyLoaderRole;
    @Inject
    public MessageEventSourcingRole eventSourcing;
    @Inject
    public ShortMPublisherRole shortMPublisherRole;

    private Long messageId;
    private MessageVO messageVO;
    private MessageUrlVO messageUrlVO;
    private FilterPipleSpec filterPipleSpec;
    private String creationDate;
    private long modifiedDate;
    private Account account; // owner
    private volatile ForumThread forumThread;
    private Forum forum;

    private String[] tagTitle;
    private AttachmentsVO attachmentsVO;
    private MessagePropertysVO messagePropertysVO;
    private ReBlogVO reBlogVO;

    // created from repository that will be in memory, it is Entity
    public ForumMessage(Long messageId) {
        this.messageId = messageId;
        this.messageVO = this.messageVOBuilder().subject("").body("").build();
        this.messageUrlVO = new MessageUrlVO("", "");
    }

    // the forumThread using this for AnemicMessageDTO
    // if create a ForumMessage, please using builder pattern!
    public ForumMessage() {
        this.messageId = null;
        this.setParameter(true);//this is a DTO parameter
        this.messageVO = this.messageVOBuilder().subject("").body("").build();
        this.messageUrlVO = new MessageUrlVO("", "");
        this.account = new Account();
        this.attachmentsVO = new AttachmentsVO(new Long(0), new ArrayList());
        this.messagePropertysVO = new MessagePropertysVO(new Long(0), new ArrayList());
    }

    public  RequireMessageId messageBuilder() {
        return messageId -> messageVO -> forum -> forumThread -> account -> creationDate ->modifiedDate-> filterPipleSpec
                -> uploads -> properties -> new FinalStageVO(messageId, messageVO, forum, forumThread, account,  creationDate,  modifiedDate, filterPipleSpec, uploads, properties);
    }

    public Account getAccount() {
        return account;
    }

    private void setAccount(com.jdon.jivejdon.model.Account account) {
        this.account = account;
    }

    public boolean isLeaf() {
        return this.forumThread.isLeaf(this);
    }

    public boolean isRoot() {
        return this.forumThread.isRoot(this);
    }

    public MessageVO getMessageVO() {
        return messageVO;
    }

    private void setMessageVO(MessageVO messageVO) {
        if (messageVO.getForumMessage() == null || messageVO.getForumMessage() != this) {
            this.messageVO = this.messageVOBuilder().subject(messageVO.getSubject()).body(messageVO
                    .getBody()).build();
        } if(messageVO.getSubject().length() == 0 || messageVO.getBody().length() == 0)
            System.err.println("messageVO is null" + this.messageId);
        else
            this.messageVO = messageVO;
    }

    public void updateSubject(String subject){
        this.messageVO = this.messageVOBuilder().subject(subject).body(this.messageVO
                .getBody()).build();
    }

    public MessageVO getMessageVOClone() throws Exception {
        return (MessageVO) this.messageVO.clone();
    }

    /**
     * there are two kinds MessageVO;
     * 1. applied business rule filter
     * 2. original that saved in repository
     */
    public void reloadMessageVOOrignal() {
        DomainMessage em = lazyLoaderRole.reloadMessageVO(this.messageId);
        messageVO = (MessageVO) em.getBlockEventResult();
        setMessageVO(messageVO);
        em.clear();
    }

    public boolean isSubjectRepeated(String subject){
        String lastSubject  = getMessageVO().getSubject();
        return lastSubject.equals(subject)?true:false;
    }

    /**
     * post a reply forumMessage
     */
    @OnCommand("postReplyMessageCommand")
    public void addChild(AnemicMessageDTO anemicMessageDTO) {
        try {
//			Thread.sleep(5000); test blocking async
            // basic construct
            ForumMessageReply forumMessageReply = new ForumMessageReply(anemicMessageDTO.getMessageId(), this.getMessageId());
            long modifiedDate = System.currentTimeMillis();
            String creationDate = Constants.getDefaultDateTimeDisp(modifiedDate);
            forumMessageReply = this.messageBuilder()
                    .messageId(anemicMessageDTO.getMessageId())
                    .messageVO(anemicMessageDTO.getMessageVO())
                    .forum(this.forum).forumThread(this.forumThread)
                    .acount(anemicMessageDTO.getAccount())
                    .creationDate(creationDate)
                    .modifiedDate(modifiedDate)
                    .filterPipleSpec(this.filterPipleSpec)
                    .uploads(anemicMessageDTO.getAttachment().getUploadFiles())
                    .props(anemicMessageDTO.getMessagePropertysVO().getPropertys())
                    .build(forumMessageReply, this);

            forumThread.addNewMessage(this, forumMessageReply);
            forumMessageReply.getAccount().updateMessageCount(1);
            anemicMessageDTO.setForumThread(this.forumThread);
            anemicMessageDTO.setForum(this.forum);
            eventSourcing.addReplyMessage(new ReplyMessageCreatedEvent(anemicMessageDTO));
        } catch (Exception e) {
            System.err.print(" addReplyMessage error:" + e + this.messageId);
        }
    }

    /**
     * doing after put a exist forumMessage
     *
     * @param newForumMessageInputparamter
     */
    @OnCommand("updateForumMessage")
    public void update(AnemicMessageDTO newForumMessageInputparamter) {
        try {
            merge(newForumMessageInputparamter);

            ForumThread forumThread = this.getForumThread();
            forumThread.updateMessage(this);

            if (newForumMessageInputparamter.getForum() != null) {
                Long newforumId = newForumMessageInputparamter.getForum().getForumId();
                if (newforumId != null && getForum().getForumId().longValue() != newforumId.longValue()
                         && ((this.getForumThread().isRoot(this)) && (this.isLeaf()))) {
                    Forum newForum = forumThread.moveForum(this, newForumMessageInputparamter.getForum());
                    this.setForum(newForum);
                }
            }
            // save this updated message to db
            eventSourcing.saveMessage(new MessageUpdatedEvent(newForumMessageInputparamter));
//			this.applyFilters();
        } catch (Exception e) {
            System.err.print(" updateMessage error:" + e + this.messageId);
        }
    }

    /**
     * merge the new into myself, include aggregation or association parts
     *
     * @param newForumMessageInputparamter
     * @throws Exception
     */
    private void merge(AnemicMessageDTO newForumMessageInputparamter) throws Exception {
        long now = System.currentTimeMillis();
        setModifiedDate(now);

        Collection<UploadFile> uploads = newForumMessageInputparamter.getAttachment()
                .getUploadFiles();
        if (uploads != null) {
            eventSourcing.saveUploadFiles(new UploadFilesSavedEvent(this.messageId, uploads));
        }
        // 3. association message property
        Collection<Property> props = newForumMessageInputparamter.getMessagePropertysVO()
                .getPropertys();
        this.setSolid(false);
        this.messageBuilder().messageId(this.getMessageId()).messageVO
                (newForumMessageInputparamter.getMessageVO()).forum
                (this.forum).forumThread(this.forumThread)
                .acount(this.getAccount()).creationDate(this.creationDate).modifiedDate(now).filterPipleSpec(this.filterPipleSpec)
                .uploads(uploads).props(props).build(this);

        // merge with old properties;
        eventSourcing.saveMessageProperties(new MessagePropertiesUpdatedEvent(this.messageId,
                getMessagePropertysVO().getPropertys()));

    }

    public void updateMasked(boolean masked) {
        this.getMessagePropertysVO().updateMasked(masked);
        eventSourcing.saveMessageProperties(new MessagePropertiesUpdatedEvent(this.messageId,
                getMessagePropertysVO().getPropertys()));
        this.getForumThread().updateMessage(this);
        this.reloadMessageVOOrignal();
    }

    public AttachmentsVO getAttachment() {
        if (attachmentsVO == null)
            attachmentsVO = new AttachmentsVO(this.messageId, this.lazyLoaderRole);
        return attachmentsVO;
    }

    private void setAttachment(AttachmentsVO attachmentsVO) {
        this.attachmentsVO = attachmentsVO;
    }

    public boolean isMasked() {
        return this.getMessagePropertysVO().isMasked();
    }

    private void setMasked(boolean masked) {
        getMessagePropertysVO().setMasked(masked);
    }

    public String getCreationDate() {
        return creationDate;
    }

    private void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreationDateForDay() {
        return creationDate.substring(2, 16);
    }

    public Long getMessageId() {
        return messageId;
    }

    private void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getModifiedDate() {
        if (this.modifiedDate == 0)
            return "";
        return Constants.getDefaultDateTimeDisp(modifiedDate);
    }

    private void setModifiedDate(long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedDate3() {
        if (modifiedDate == 0)
            return "";
        return Constants.convertDataToPretty(modifiedDate);
    }

    public long getModifiedDate2() {
        return modifiedDate;
    }

    public ForumThread getForumThread() {
        return forumThread;
    }

    private void setForumThread(ForumThread forumThread) {
        if(forumThread.lazyLoaderRole == null || (forumThread.getRootMessage().lazyLoaderRole == null && this.messageId.longValue() != forumThread
                .getRootMessage().getMessageId().longValue())){
            System.err.println("forumThread not solid messageId=" + messageId + " threadId="+forumThread.getThreadId());
        }
        this.forumThread = forumThread;
    }

    public Forum getForum() {
        return forum;
    }

    private void setForum(Forum forum) {
        if (forum.lazyLoaderRole == null || forum.getName() == null){
            System.err.println("forum not solid for messageId=" + messageId + " forumId="+ forum.getForumId());
        }
        this.forum = forum;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getDigCount() {
        return this.getMessagePropertysVO().getDigCount();
    }

    public MessagePropertysVO getMessagePropertysVO() {
        if (messagePropertysVO == null && lazyLoaderRole != null)
            messagePropertysVO = new MessagePropertysVO(messageId, this.lazyLoaderRole);
        else if (messagePropertysVO == null && lazyLoaderRole == null) {
            System.err.print("my god, how MessagePropertysVO was bornd?");
        }
        return messagePropertysVO;
    }

    public void preloadWhenView() {
        getMessagePropertysVO().preload();
        getAttachment().preloadUploadFileDatas();// preload img
    }

    public void messaegDigAction() {
        this.getMessagePropertysVO().addMessageDigCount();
        this.forumThread.addDig(this);
        eventSourcing.saveMessageProperties(new MessagePropertiesUpdatedEvent(this.messageId,
                getMessagePropertysVO().getPropertys()));
    }

    public String getPostip() {
        return this.getMessagePropertysVO().getPostip();
    }

    public ReBlogVO getReBlogVO() {
        if (reBlogVO == null)
            reBlogVO = new ReBlogVO(this.messageId, this.lazyLoaderRole);
        return reBlogVO;
    }

    public void setReBlogVO(ReBlogVO reBlogVO) {
        this.reBlogVO = reBlogVO;
    }

    public String[] getTagTitle() {
        return tagTitle;
    }

    public void setTagTitle(String[] tagTitle) {
        this.tagTitle = tagTitle;
    }

    private void setFilterPipleSpec(FilterPipleSpec filterPipleSpec) {
        this.filterPipleSpec = filterPipleSpec;
    }

    public MessageUrlVO getMessageUrlVO() {
        return messageUrlVO;
    }

    public void setMessageUrlVO(MessageUrlVO messageUrlVO) {
        this.messageUrlVO = messageUrlVO;
    }

    /**
     * build a messageVO
     */
    public RequireSubject messageVOBuilder() {
        return subject -> body -> new MessageVO.MessageVOFinalStage(subject, body, this);
    }


    @FunctionalInterface
    public interface RequireMessageId {
        RequireMessageVO messageId(long messageId);
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
        FinalStageVO props(Collection<Property> props);
    }

    @FunctionalInterface
    public interface RequireSubject {
        MessageVO.RequireBody subject(String subject);
    }

    @FunctionalInterface
    public interface RequireBody {
        MessageVO.MessageVOFinalStage body(String body);
    }



    public  class FinalStageVO {
        private final long messageId;
        private final MessageVO messageVO;
        private final Account account;
        private final String creationDate;
        private final long modifiedDate;
        private final Forum forum;
        private final ForumThread forumThread;
        private final FilterPipleSpec filterPipleSpec;
        private final Collection<UploadFile> uploads;
        private final Collection<Property> props;

        public FinalStageVO(long messageId, MessageVO messageVO, Forum
                forum, ForumThread forumThread, Account account, String creationDate, long modifiedDate,FilterPipleSpec filterPipleSpec,
                            Collection<UploadFile> uploads, Collection<Property> props) {
            this.messageId = messageId;
            this.messageVO = messageVO;
            this.account = account;
            this.creationDate = creationDate;
            this.modifiedDate = modifiedDate;
            this.forum = forum;
            this.forumThread = forumThread;
            this.filterPipleSpec = filterPipleSpec;
            this.uploads = uploads;
            this.props = props;
        }

        public ForumMessage build(ForumMessage forumMessage) {
            if (forumMessage instanceof ForumMessageReply){
                ForumMessageReply forumMessageReply = (ForumMessageReply)forumMessage;
                build(forumMessageReply, forumMessageReply.getParentMessage());
            }else
                forumMessage.build(messageId, messageVO, forum, forumThread, account,
                    creationDate,  modifiedDate, filterPipleSpec, uploads, props);
            return forumMessage;
        }

        public ForumMessageReply build(ForumMessageReply forumMessageRely,
                                       ForumMessage parentForumMessage) {
            forumMessageRely.build(messageId, messageVO, forum, forumThread, account,
                    creationDate,  modifiedDate, filterPipleSpec, uploads,
                    props, parentForumMessage);
            return forumMessageRely;
        }
    }


    public void build(long messageId, MessageVO messageVO, Forum
            forum, ForumThread forumThread, Account account,String creationDate, long modifiedDate ,FilterPipleSpec filterPipleSpec,
                      Collection<UploadFile> uploads, Collection<Property> props) {
        try {
            if (!this.isSolid())
                synchronized (this) {
                    if (!this.isSolid()) {
                        setMessageId(messageId);
                        setAccount(account);
                        setCreationDate(creationDate);
                        setModifiedDate(modifiedDate);
                        setForum(forum);
                        setForumThread(forumThread);
                        setFilterPipleSpec(filterPipleSpec);
                        if (uploads != null)
                            this.getAttachment().setUploadFiles(uploads);
                        this.messagePropertysVO = new MessagePropertysVO(messageId, this.lazyLoaderRole);
                        if (props != null)
                            this.getMessagePropertysVO().replacePropertys(props);
                        else
                            //preload messageProperty
                            getMessagePropertysVO().preLoadPropertys();
                        //apply all filter specification , business rule!
                        messageVO = this.messageVOBuilder().subject(messageVO.getSubject()).body(messageVO
                                .getBody()).build();
                        setMessageVO(filterPipleSpec.apply(messageVO));
                        this.setSolid(true);//construt end
                    }
                }
        } catch (Exception e) {
            System.err.print("Message build error:"+ messageId);
        }

    }
}
