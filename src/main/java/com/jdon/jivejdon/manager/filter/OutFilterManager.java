package com.jdon.jivejdon.manager.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.logging.log4j.*;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.message.MessageRenderSpecification;
import com.jdon.jivejdon.model.message.output.RenderingFilterManager;

public class OutFilterManager {
	private final static Logger logger = LogManager.getLogger(OutFilterManager.class);

	private Collection outFilters;

	private RenderingFilterManager renderingFilterManager;

	public OutFilterManager(RenderingFilterManager renderingFilterManager) {
		initFilters(renderingFilterManager.getFilters());
		this.renderingFilterManager = renderingFilterManager;
		this.renderingFilterManager.setOutFilterManager(this);
	}

	public void initFilters(MessageRenderSpecification[] filters) {
		outFilters = new ArrayList();
		for (int i = 0; i < filters.length; i++) {
			outFilters.add(filters[i]);
		}
	}

	/**
	 * // Loop through replacing filters and apply them //these filters will not
	 * add new content to message, just replace something. //they are different
	 * from appending filters.
	 */
	public void applyFilters(ForumMessage forumMessage) {
		logger.debug("enter outFilter: ");
		try {
			Iterator iter = outFilters.iterator();
			while (iter.hasNext()) {
				((MessageRenderSpecification) iter.next()).render(forumMessage);
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public RenderingFilterManager getRenderingFilterManager() {
		return renderingFilterManager;
	}

	public Collection getOutFilters() {
		return outFilters;
	}

	public void setOutFilters(Collection outFilters) {
		this.outFilters = outFilters;
	}

}
