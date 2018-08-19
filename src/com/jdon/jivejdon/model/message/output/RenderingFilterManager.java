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
package com.jdon.jivejdon.model.message.output;

import com.jdon.jivejdon.manager.filter.OutFilterManager;
import com.jdon.jivejdon.model.message.MessageRenderSpecification;


/**
 * Filter for display the message body.
 * the filter character is replacing the message content, not appending.
 *  
 */
public interface RenderingFilterManager {
    
    String PERSISTENCE_NAME = "FILTERS";
    
    /**
     * returns available ForumMessageFilter collection, except the used ones!
     * @return
     */
    MessageRenderSpecification[] getAvailableFilters();
    
    
    /**
     * returns the used ForumMessageFilter collection, except the available ones!
     * @return
     */
    MessageRenderSpecification[] getFilters();
    
    /**
     * Returns the ForumMessageFilter at the specified index.
     *
     * @return the filter at the specified index.
     * @throws UnauthorizedException if not an admin.
     */
    public MessageRenderSpecification getFilter(int index) throws Exception;

    /**
     * Returns the count of currently active filters for the forum.
     *
     * @return a count of the currently active filters.
     * @throws UnauthorizedException if not an admin.
     */
    public int getFilterCount() throws Exception;

    /**
     * Adds a new ForumMessageFilter to the end of the filter list.
     *
     * @param filter ForumMessageFilter to add to the filter list.
     * @throws UnauthorizedException if not an admin.
     */
    public void addFilter(MessageRenderSpecification filter) throws Exception;
    
    
    void addFilterClass(String className) throws ClassNotFoundException, IllegalArgumentException ;

    /**
     * Inserts a new ForumMessageFilter at specified index in the filter list.
     *
     * @param filter ForumMessageFilter to add to the filter list.
     * @param index position in filter list to insert new filter.
     * @throws UnauthorizedException if not an admin.
     */
    public void addFilter(MessageRenderSpecification filter, int index)
            throws Exception;

    /**
     * Removes a ForumMessageFilter at the specified index in the filter list.
     *
     * @param index position in filter list to remove filter from.
     * @throws UnauthorizedException if not an admin.
     */
    public void removeFilter(int index) throws Exception;

    /**
     * Saves all ForumMessageFilters to the persistent store. This method
     * should be called after setting any properties on invidual filters
     * that are being managed by this filter manager. Warning: calling this
     * method will expire the entire message cache so that cache consistency
     * is maintained.
     *
     * @throws UnauthorizedException if not an admin.
     */
    public void saveFilters() throws Exception;


    public void setOutFilterManager(OutFilterManager outFilterManager);
    
   
   
}
