package com.jdon.jivejdon.domain.model.message;

import java.util.Collection;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        if (!isReady()) {
            logger.error("when build but outFilters is null");
            return messageVO;
        }
        
            try {
                messageVO = outFilters.parallelStream()
                        .reduce(Function.identity(), Function::andThen)
                        .apply(messageVO);
            } catch (Exception e) {
                logger.error(" applyFilters error1:" + e + messageVO.getForumMessage().getMessageId());
                logger.error(" applyFilters error2:" + e + messageVO.getShortBody(5));
               
            }
		return messageVO;
	}
}
