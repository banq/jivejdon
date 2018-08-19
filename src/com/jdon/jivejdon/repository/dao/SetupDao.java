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
package com.jdon.jivejdon.repository.dao;

/**
 * read the xml setup from the database, and modify the xml ant then save to database.
 * @author <a href="mailto:banqJdon<AT>jdon.com">banq</a>
 *
 */
public interface SetupDao {
    
    String getSetupValue(String name);
    
    void saveSetupValue(String name, String value);
        
}
