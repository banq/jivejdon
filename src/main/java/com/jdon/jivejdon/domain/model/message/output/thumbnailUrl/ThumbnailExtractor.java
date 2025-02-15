package com.jdon.jivejdon.domain.model.message.output.thumbnailUrl;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.jivejdon.domain.model.message.MessageUrlVO;
import com.jdon.jivejdon.domain.model.message.MessageVO;

/**
 * add a thumbnail to a thread for list;
 * add this filter to renderingAvailableFilters
 */
public class ThumbnailExtractor implements Function<MessageVO, MessageVO> {
	private final static Logger logger = LogManager.getLogger(ThumbnailExtractor.class);
	private final static Pattern imgPattern = Pattern.compile("(<img\\b|(?!^)\\G)[^>]*?\\b" +
			"(src|width|height)=([\"']?)([^\"]*)\\3");
	private String thumbpics = "/simgs/thumb/1.jpg,/simgs/thumb/2.jpg";

	@Override
	public MessageVO apply(MessageVO messageVO) {
		String thumbnailUrl = messageVO.getForumMessage().getMessageUrlVO().getThumbnailUrl();
		String imageUrl = messageVO.getForumMessage().getMessageUrlVO().getImageUrl();
		Matcher matcher = imgPattern.matcher(messageVO.getBody());
		if (matcher.find()) {
			imageUrl = matcher.group(4);
			thumbnailUrl = "";
		} else {
			if (thumbnailUrl == null || thumbnailUrl.length() == 0) {
				thumbnailUrl = this.getRandomDefaulThumb();
				imageUrl = "";
			}
		}
		messageVO.getForumMessage().setMessageUrlVO(new MessageUrlVO(messageVO
				.getForumMessage().getMessageUrlVO().getLinkUrl(), thumbnailUrl, imageUrl));
		return messageVO;
	}

	private String getRandomDefaulThumb() {
		String[] thumbs = thumbpics.split(",");
		if (thumbs == null || thumbs.length == 0) {
			logger.error("please add default thumbs UploadImageFilter");
			return "please add default thumbs UploadImageFilter";
		}

		int i = ThreadLocalRandom.current().nextInt(thumbs.length);
		return thumbs[i];

	}

	public String getThumbpics() {
		return thumbpics;
	}

	public void setThumbpics(String thumbpics) {
		this.thumbpics = thumbpics;
	}
}
