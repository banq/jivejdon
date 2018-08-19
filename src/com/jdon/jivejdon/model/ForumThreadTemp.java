package com.jdon.jivejdon.model;

/**
 * temporary ForumThread that not need saved into cache.
 * 
 * construte a empty forumthread only include messageCount; so we donot need a
 * full forumThread, that will cost memory.
 * 
 * @author banq
 * 
 */

// not cache the no full object
public class ForumThreadTemp extends ForumThread {

	/**
	 * 
	 */
	private static final long serialVersionUID = -508914034119208042L;

	public ForumThreadTemp() {
		super();
	}

}
