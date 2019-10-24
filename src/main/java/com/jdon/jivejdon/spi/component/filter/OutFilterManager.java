package com.jdon.jivejdon.infrastructure.component.filter;

import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.jivejdon.domain.model.message.output.RenderingFilterManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

/**
 * customzied filter
 * configure in component.xml
 */
public class OutFilterManager {
	private final static Logger logger = LogManager.getLogger(OutFilterManager.class);

	private Collection outFilters;

	private RenderingFilterManager renderingFilterManager;

	public OutFilterManager(RenderingFilterManager renderingFilterManager) {
		initFilters(renderingFilterManager.getFilters());
		this.renderingFilterManager = renderingFilterManager;
		this.renderingFilterManager.setOutFilterManager(this);
	}

	public void initFilters(Function<MessageVO, MessageVO>[] filters) {
		outFilters = new ArrayList();
		for (int i = 0; i < filters.length; i++) {
			outFilters.add(filters[i]);
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
