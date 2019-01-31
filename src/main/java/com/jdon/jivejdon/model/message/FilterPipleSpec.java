package com.jdon.jivejdon.model.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

/**
 * piple pattern
 * <p>
 * apply business rule filter to MessageVO for display or other output format!
 */
public final class FilterPipleSpec implements Function<MessageVO, MessageVO> {
	private final static Logger logger = LogManager.getLogger(FilterPipleSpec.class);

	private final Collection<Function<MessageVO, MessageVO>> outFilters;

	public FilterPipleSpec(Collection<Function<MessageVO, MessageVO>> outFilters) {
		this.outFilters = outFilters;
	}

	public Collection<Function<MessageVO, MessageVO>> getOutFilters() {
		return outFilters;
	}


	public boolean isReady() {
		return (outFilters != null) ? true : false;
	}

	public MessageVO apply(MessageVO messageVO) {
		try {
			if (!isReady()) {
				logger.error("when build but outFilters is null");
				return messageVO;
			}
			Iterator iter = outFilters.iterator();
			while (iter.hasNext()) {
				Function<MessageVO, MessageVO> mrs = ((Function<MessageVO, MessageVO>) iter
						.next());
				messageVO = mrs.apply(messageVO);
			}
		} catch (Exception e) {
			logger.error(" applyFilters error:" + e + messageVO);
		}
		return messageVO;
	}
}
