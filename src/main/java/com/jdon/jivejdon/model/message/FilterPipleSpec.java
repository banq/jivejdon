package com.jdon.jivejdon.model.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Iterator;

/**
 * piple pattern
 * <p>
 * apply business rule filter to MessageVO for display or other output format!
 */
public final class FilterPipleSpec {
	private final static Logger logger = LogManager.getLogger(FilterPipleSpec.class);

	private final Collection<MessageRenderSpecification> outFilters;

	public FilterPipleSpec(Collection<MessageRenderSpecification> outFilters) {
		this.outFilters = outFilters;
	}

	public Collection<MessageRenderSpecification> getOutFilters() {
		return outFilters;
	}


	public boolean isReady() {
		return (outFilters != null) ? true : false;
	}

	public MessageVO applyFilters(MessageVO messageVO) {
		try {
			if (!isReady()) {
				logger.error("when build but outFilters is null");
				return messageVO;
			}
			Iterator iter = outFilters.iterator();
			while (iter.hasNext()) {
				MessageRenderSpecification mrs = ((MessageRenderSpecification) iter
						.next());
				messageVO = mrs.render(messageVO);
			}
		} catch (Exception e) {
			logger.error(" applyFilters error:" + e + messageVO);
		}
		return messageVO;
	}
}
