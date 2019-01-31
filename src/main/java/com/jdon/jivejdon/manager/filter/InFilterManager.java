package com.jdon.jivejdon.manager.filter;

import com.jdon.jivejdon.model.message.MessageVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

public class InFilterManager {
	private final static Logger logger = LogManager.getLogger(InFilterManager.class);

	private Collection<Function<MessageVO, MessageVO>> inFilters;

	public InFilterManager(String[] inFilterClassNames) {
		initFilters(inFilterClassNames);
	}

	public void initFilters(String[] filters) {
		inFilters = new ArrayList();
		try {
			for (int i = 0; i < filters.length; i++) {
				String className = filters[i];
				inFilters.add((Function<MessageVO, MessageVO>) Class.forName(className)
						.newInstance());
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
	 * @throws Exception
	 */
	public MessageVO applyFilters(MessageVO messageVO) {
		logger.debug("enter inFilter: ");
		try {
			Iterator iter = inFilters.iterator();
			while (iter.hasNext()) {
				messageVO = ((Function<MessageVO, MessageVO>) iter.next()).apply(messageVO);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return messageVO;
	}

}
