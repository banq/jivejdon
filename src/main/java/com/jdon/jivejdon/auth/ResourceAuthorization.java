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
package com.jdon.jivejdon.auth;

import com.jdon.jivejdon.model.account.Account;
import org.apache.logging.log4j.*;

import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.auth.Role;

/**
 * 1. Authorization check: amdin and the owner can modify this nessage.
 * 2. if the message has childern, only admin can update it.
 *    before business logic, we must get a true message from persistence layer,
 *    now the ForumMessage packed in EventModel object is not full, it is 
 *    a DTO from prensentation layer.
 * 
 * @author banq(http://www.jdon.com)
 */
public class ResourceAuthorization {
    private final static Logger logger = LogManager.getLogger(ResourceAuthorization.class);
    
   public ResourceAuthorization() {
    }

   /**
    * check the Model forumMessage is isAuthenticated to modified or deleted.
    * 
    * @param forumMessage
    * @param account  loged Account
    * @throws NoPermissionException
    */
   public void verifyAuthenticated(ForumMessage forumMessage, Account account)
			throws NoPermissionException {
		if (!isMessageOwner(forumMessage, account)) {
			throw new NoPermissionException(Constants.NOPERMISSIONS);
		}
		if (!isMessageLeaf(forumMessage, account)) {
			throw new NoPermissionException(Constants.NOPERMISSIONS2);
		}
	}
   
   public boolean isAuthenticated(ForumMessage forumMessage, Account account){
	   boolean isAuthenticated = false;
		try {
			if (isMessageOwner(forumMessage, account)
					&& isMessageLeaf(forumMessage, account)) {
				isAuthenticated = true;
			}
		} catch (Exception e) {
		}
		return isAuthenticated;
	}
  
    /**
	 * 1. auth check: amdin and the owner can modify this nessage.
	 * 
	 */
    public boolean isMessageOwner(ForumMessage forumMessage, Account account ){
        logger.debug(" enter operateMessageAuthCheck1 id =" + forumMessage.getMessageId());        
        return isOwner(account,  forumMessage.getAccount());
    }
    
    /**
     * 
     * @param account login account,
     * @param account2 old account
     * @return
     */
    public boolean isOwner(Account account, Account account2) {
		boolean ret = false;
		if (isAdmin(account)) {
			ret = true;
		} else if (account.getUserId().equals(account2.getUserId())) {
			ret = true;
		} else {
			logger.debug("not the owner , no permission update Message!");
		}
		return ret;
	}
    
    public boolean isAdmin(Account account) {
		boolean ret = false;
		if ((account.getRoleName().equals(Role.ADMIN))
				|| (account.getRoleName().equals(Role.MODERATOR))) {
			ret = true;
		}
		return ret;
	}
    
    
    /**
	 * 2. if the message has childern, only admin can update it.
	 */
    public boolean isMessageLeaf(ForumMessage forumMessage, Account account){
        boolean ret = false;
        logger.debug(" enter operateMessageAuthCheck2 id =" + forumMessage.getMessageId());        
        try {
            if ((account.getRoleName().equals(Role.ADMIN)) || (account.getRoleName().equals(Role.MODERATOR))){
                ret = true;
            }else {
                if (forumMessage.isLeaf()){//only it is leaf, owner can update                    
                    ret = true;
                }else{
                    logger.debug("the Message has childern, no permission to modify it !");
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return ret;
    }    
    
   
    
}
