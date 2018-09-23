package com.jdon.jivejdon.model.message;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableProperty;

import com.jdon.util.StringUtil;

/**
 * ForumMessage's value object
 * 
 * @author gateway
 * 
 */
@Searchable(root = false)
public class MessageVO implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static Pattern quoteEscape = Pattern.compile("\\[.*?\\](.*)\\[\\/.*?\\]");
	private final static Pattern htmlEscape = Pattern.compile("\\<.*?\\>|<[^>]+");
	private final static Pattern newlineEscape = Pattern.compile("\\<br\\>|\\<p\\>", Pattern.CASE_INSENSITIVE);
	private final static Pattern httpURLEscape = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

	@SearchableProperty
	protected String subject;

	@SearchableProperty
	protected String body;

	@SearchableProperty
	private String[] tagTitle;

	private int rewardPoints;

	private volatile boolean isFiltered;

	private String linkUrl;

	private String thumbnailUrl;

	public MessageVO() {
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

	/**
	 * @param body
	 *            The body to set.
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @param subject
	 *            The subject to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return Returns the rewardPoints.
	 */
	public int getRewardPoints() {
		return rewardPoints;
	}

	/**
	 * @param rewardPoints
	 *            The rewardPoints to set.
	 */
	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	public String[] getTagTitle() {
		return tagTitle;
	}

	public void setTagTitle(String[] tagTitle) {
		this.tagTitle = tagTitle;
	}

	public String getShortBody(int length) {
		if (getBody() != null) {
			dealLinkUrl();
			return shortenNoHTML(getBody(), length);
		} else
			return "";
	}

	public String getBodyText(int length) {
		if (getBody() != null) {
			dealLinkUrl();
			return shortenNoHTMLText(getBody(), length);
		} else
			return "";
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

	private void dealLinkUrl() {
		if (linkUrl != null)
			return;
		Matcher matcher = httpURLEscape.matcher(this.body);
		if (matcher.find()) {
			linkUrl = matcher.group();
			body = matcher.replaceAll("");
		} else
			linkUrl = "";
	}

	public String getLinkUrl() {
		if (linkUrl != null)
			return linkUrl;

		dealLinkUrl();
		return linkUrl;
	}

	public boolean isFiltered() {
		return isFiltered;
	}

	public void setFiltered(boolean isFiltered) {
		this.isFiltered = isFiltered;
	}

	/**
	 * add property to propertys;
	 * 
	 * @param propName
	 * @param propValue
	 */

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

}
