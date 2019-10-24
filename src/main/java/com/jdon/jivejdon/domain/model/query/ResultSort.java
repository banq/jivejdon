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
package com.jdon.jivejdon.model.query;

/**
 * @author banq(http://www.jdon.com)
 *
 */
public class ResultSort {
   
    public static final int DESCENDING = 0;   
    public static final int ASCENDING = 1;
    
    private int sortOrder = DESCENDING;
            
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	/**
     * @return Returns the sortOrder.
     */
    public boolean isASCENDING() {
         return sortOrder==ASCENDING?true:false;
    }
    /**
     * @param sortOrder The sortOrder to set.
     */
    public void setOrder_ASCENDING() {
        this.sortOrder = ASCENDING;
    }
    
    public void setOrder_DESCENDING() {
        this.sortOrder = DESCENDING;
    }
    
    public String toString() {
		if (isASCENDING())
			return "  ASC ";
		else
			return "  DESC ";
	}
   
   
}
