/*
 * Copyright 2003-2006 the original author or authors.
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
package com.jdon.jivejdon.repository.dao;



/**
 * @author banq(http://www.jdon.com)
 *
 */
public class MessageDaoFacade {
    
    protected MessageDao messageDao;
    protected MessageQueryDao messageQueryDao;
    protected SequenceDao sequenceDao;
    

    public MessageDaoFacade(MessageDao messageDao, 
            MessageQueryDao messageQueryDao,
            SequenceDao sequenceDao){
      
        this.messageDao = messageDao;
        this.messageQueryDao = messageQueryDao;
        this.sequenceDao = sequenceDao;
    }
    
    
    /**
     * @return Returns the messageDao.
     */
    public MessageDao getMessageDao() {
        return messageDao;
    }
    /**
     * @param messageDao The messageDao to set.
     */
    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }
    /**
     * @return Returns the messageQueryDao.
     */
    public MessageQueryDao getMessageQueryDao() {
        return messageQueryDao;
    }
    /**
     * @param messageQueryDao The messageQueryDao to set.
     */
    public void setMessageQueryDao(MessageQueryDao messageQueryDao) {
        this.messageQueryDao = messageQueryDao;
    }
    /**
     * @return Returns the sequenceDao.
     */
    public SequenceDao getSequenceDao() {
        return sequenceDao;
    }
    /**
     * @param sequenceDao The sequenceDao to set.
     */
    public void setSequenceDao(SequenceDao sequenceDao) {
        this.sequenceDao = sequenceDao;
    }
}
