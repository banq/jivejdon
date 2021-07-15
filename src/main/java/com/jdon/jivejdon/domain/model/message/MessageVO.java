package com.jdon.jivejdon.domain.model.message;

import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.util.StringUtil;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * ForumMessage's value object created by MessageVOCreatedBuilder
 *
 * there are two kinds MessageVO; 1. applied business rule filter in
 * FilterPipleSpec 2. original that saved in repository
 *
 */
public final class MessageVO implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private final static Pattern quoteEscape = Pattern.compile("\\[.*?\\](.*)\\[\\/.*?\\]");
	private final static Pattern htmlEscape = Pattern.compile("\\<.*?\\>|<[^>]+");
	private final static Pattern newlineEscape = Pattern.compile("\\<br\\>|\\<p\\>", Pattern.CASE_INSENSITIVE);

	private final ForumMessage forumMessage;

	private final String subject;

	private final String body;

	/**
	 * only using builder pattern to create MessageVO!
	 * <p>
	 * MessageVO..builder().subject().body().message().build();
	 */
	private MessageVO(String subject, String body, ForumMessage forumMessage) {
		this.forumMessage = forumMessage;
		this.subject = subject;
		this.body = body;
	}

	// used for compass lucene search
	public MessageVO() {
		this("", "", null);
	}

	/**
	 * build a messageVO
	 */
	public RequireSubject builder() {
		return subject -> body -> new MessageVOFinalStage(subject, body, getForumMessage());
	}

	/**
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return subject;
	}

	public String getBody() {
		return body;
	}

	public String getShortBody(int length) {
		return shortenNoHTML(getBody(), length);
	}

	public String getBodyText(int length) {
		return shortenNoHTMLText(getBody(), length);
	}

	public String shortenNoHTML(String in, int length) {
		String s = StringUtil.shorten(in, length * 3);
		String nohtml = htmlEscape.matcher(s).replaceAll(" ");
		String noQuote = quoteEscape.matcher(nohtml).replaceAll(" ");
		return StringUtil.shorten(noQuote, length);
	}

	public String shortenNoHTMLText(String in, int length) {
		String s = StringUtil.shorten(in, length * 3);
		String puretext = newlineEscape.matcher(s).replaceAll("\r\n");
		String nohtml = htmlEscape.matcher(puretext).replaceAll(" ");
		String noQuote = quoteEscape.matcher(nohtml).replaceAll(" ");
		return StringUtil.shorten(noQuote, length);
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public ForumMessage getForumMessage() {
		return forumMessage;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		MessageVO that = (MessageVO) o;
		return subject.equals(that.getSubject()) && body.equals(that.getBody());
	}

	@Override
	public int hashCode() {
		return Objects.hash(subject, body);
	}

	@FunctionalInterface
	public interface RequireSubject {
		RequireBody subject(String subject);
	}

	@FunctionalInterface
	public interface RequireBody {
		MessageVOFinalStage body(String body);
	}

	public static class MessageVOFinalStage {
		private final String subject;
		private final String body;
		private final ForumMessage message;
		// constructor

		public MessageVOFinalStage(String subject, String body, ForumMessage message) {
			this.subject = subject;
			this.body = body;
			this.message = message;
		}

		public MessageVO build() {
			return new MessageVO(subject, body, message);
		}
	}
}
