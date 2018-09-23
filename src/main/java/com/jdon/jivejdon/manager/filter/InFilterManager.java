package com.jdon.jivejdon.manager.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.logging.log4j.*;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.message.MessageRenderSpecification;

public class InFilterManager {
	private final static Logger logger = LogManager.getLogger(InFilterManager.class);

	private Collection inFilters;

	public InFilterManager(String[] inFilterClassNames) {
		initFilters(inFilterClassNames);
	}

	public void initFilters(String[] filters) {
		inFilters = new ArrayList();
		try {
			for (int i = 0; i < filters.length; i++) {
				String className = filters[i];
				inFilters.add((MessageRenderSpecification) Class.forName(className).newInstance());
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * before creting a message, we must append the in filter to it such upload
	 * images, we will add image display html code to the message. the message.
	 * 
	 * @param forumMessage
	 * @throws Exception
	 */
	public void applyFilters(ForumMessage forumMessage) {
		logger.debug("enter inFilter: ");
		try {
			Iterator iter = inFilters.iterator();
			while (iter.hasNext()) {
				((MessageRenderSpecification) iter.next()).render(forumMessage);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

}
