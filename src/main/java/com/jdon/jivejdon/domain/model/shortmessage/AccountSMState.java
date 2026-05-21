/*
 * Copyright 2003-2009 the original author or authors.
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
package com.jdon.jivejdon.domain.model.shortmessage;

import java.util.Observable;
import java.util.Observer;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.spi.pubsub.reconstruction.LazyLoaderRole;

public class AccountSMState implements Observer {

	private final Account account;
    // 明确声明依赖，不再通过 account.lazyLoaderRole 产生隐式耦合
    private final LazyLoaderRole lazyLoaderRole; 

    private volatile DomainMessage countAsyncResult;
    private int newShortMessageCount = -1;

    public AccountSMState(Account account, LazyLoaderRole lazyLoaderRole) {
        this.account = account;
        this.lazyLoaderRole = lazyLoaderRole;
    }

    public int getNewShortMessageCount() {
        // 只有当真正是“实体”且注入了 loader 时才执行异步加载
        if (newShortMessageCount == -1 && lazyLoaderRole != null) {
            if (countAsyncResult == null) {
                countAsyncResult = lazyLoaderRole.loadNewShortMessageCount(account.getUserId());
                return 0; // 第一次不返回真实值
            } else {
                Object asynResult = countAsyncResult.getEventResult();
                if (asynResult != null) {
                    newShortMessageCount = (Integer) asynResult;
                    countAsyncResult.clear();
                }
            }
        }
        return newShortMessageCount;
    }

    public void addOneNewMessage(int count) {
        newShortMessageCount = getNewShortMessageCount() + count;
    }

    @Override
    public void update(Observable obj, Object arg) {
        if (arg == null) return;
        newShortMessageCount = getNewShortMessageCount() - 1;
    }

    public void reload() {
        if (lazyLoaderRole != null) {
            countAsyncResult = lazyLoaderRole.loadNewShortMessageCount(account.getUserId());
            newShortMessageCount = -1;
        }
    }

}
