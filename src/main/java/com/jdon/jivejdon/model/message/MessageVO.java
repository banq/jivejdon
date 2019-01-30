package com.jdon.jivejdon.model.message;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.util.StringUtil;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableProperty;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ForumMessage's value object
 * created by MessageVOCreatedBuilder
 *
 *  there are two kinds MessageVO;
 *  1. applied business rule filter in FilterPipleSpec
 *  2. original that saved in repository
 *
 */
@Searchable(root = false)
public final class MessageVO implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private final static Pattern quoteEscape = Pattern.compile("\\[.*?\\](.*)\\[\\/.*?\\]");
	private final static Pattern htmlEscape = Pattern.compile("\\<.*?\\>|<[^>]+");
	private final static Pattern newlineEscape = Pattern.compile("\\<br\\>|\\<p\\>", Pattern
			.CASE_INSENSITIVE);
	private final static Pattern httpURLEscape = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

	private final static Pattern imgPattern = Pattern.compile("(<img\\b|(?!^)\\G)[^>]*?\\b" +
			"(src|width|height)=([\"']?)([^\"]*)\\3");

	private final ForumMessage forumMessage;

	@SearchableProperty
	private final String subject;

	@SearchableProperty
	private final String body;

	private final String linkUrl;

	private final String thumbnailUrl;


	/**
	 * only using builder pattern to create MessageVO!
	 * <p>
	 * MessageVO..builder().subject().body().message().build();
	 *
	 * @param subject
	 * @param body
	 * @param forumMessage
	 */
	private MessageVO(String subject, String body, ForumMessage forumMessage) {
		this.forumMessage = forumMessage;
		this.subject = subject;
		Matcher matcher = imgPattern.matcher(body);
		if (matcher.find()) {
			thumbnailUrl = matcher.group(4);
		} else
			thumbnailUrl = "";

		matcher = httpURLEscape.matcher(body);
		if (matcher.find()) {
			linkUrl = matcher.group();
			this.body = matcher.replaceAll("");
		} else {
			linkUrl = "";
			this.body = body;
		}

	}

	/**
	 * build pattern entry
	 *
	 * @return
	 */
	public static RequireSubject builder() {
		return subject -> body -> message -> new FinalStage(subject, body, message);
	}

	/**
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @return Returns the body.
	 */
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

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public ForumMessage getForumMessage() {
		return forumMessage;
	}

	@FunctionalInterface
	public interface RequireSubject {
		RequireBody subject(String subject);
	}

	@FunctionalInterface
	public interface RequireBody {
		RequireForumMessage body(String body);
	}

	@FunctionalInterface
	public interface RequireForumMessage {
		FinalStage message(ForumMessage message);
	}

	public static class FinalStage {
		private final String subject;
		private final String body;
		private final ForumMessage message;
		// constructor

		public FinalStage(String subject, String body, ForumMessage message) {
			this.subject = subject;
			this.body = body;
			this.message = message;
		}

		public MessageVO build() {
			return new MessageVO(subject, body, message);
		}
	}
}
