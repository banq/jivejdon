package com.jdon.jivejdon.domain.model.message.output.linkurl;

import java.util.function.Function;

import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.message.MessageUrlVO;
import com.jdon.jivejdon.domain.model.message.MessageVO;

/**
 * Quote link
 */
public class LinkUrlExtractor implements Function<MessageVO, MessageVO> {

 public MessageVO apply(MessageVO vo) {
        String body = vo.getBody();

        int end = 0;
        int len = body.length();
        while (end < len) {
            char c = body.charAt(end);
            if (c <= ' ' || c >= 128) break;   // 空白 / 控制字符 / 中文等非 ASCII 视为 URL 结束
            end++;
        }

        String linkUrl = body.substring(0, end);
        String newBody = body.substring(end);

        ForumMessage fm = vo.getForumMessage();
        MessageUrlVO old = fm.getMessageUrlVO();
        fm.setMessageUrlVO(new MessageUrlVO(linkUrl, old.getThumbnailUrl(), old.getImageUrl()));

        return vo.builder().subject(vo.getSubject()).body(newBody).build();
    }
}
