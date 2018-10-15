/*
 * Copyright 2003-2005 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.model.message.output.emotion;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.message.MessageRenderSpecification;
import com.jdon.jivejdon.model.message.MessageVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A ForumMessageFilter that converts ASCII emoticons into image equivalents.
 * This filter should only be run after any HTML stripping filters.
 * <p>
 * <p>
 * The filter must be configured with information about where the image files
 * are located. A table containing all the supported emoticons with their ASCII
 * representations and image file names is as follows:
 * <p>
 *
 * <table border=1>
 * <tr>
 * <td><b>Emotion</b></td>
 * <td><b>ASCII</b></td>
 * <td><b>Image</b></td>
 * </tr>
 *
 * <tr>
 * <td>Happy</td>
 * <td>:) or :-)</td>
 * <td>happy.gif</td>
 * </tr>
 * <tr>
 * <td>Sad</td>
 * <td>:( or :-(</td>
 * <td>sad.gif</td>
 * </tr>
 * <tr>
 * <td>Grin</td>
 * <td>:D</td>
 * <td>grin.gif</td>
 * </tr>
 * <tr>
 * <td>Love</td>
 * <td>:x</td>
 * <td>love.gif</td>
 * </tr>
 * <tr>
 * <td>Mischief</td>
 * <td>;\</td>
 * <td>mischief.gif</td>
 * </tr>
 * <tr>
 * <td>Cool</td>
 * <td>B-)</td>
 * <td>cool.gif</td>
 * </tr>
 * <tr>
 * <td>Devil</td>
 * <td>]:)</td>
 * <td>devil.gif</td>
 * </tr>
 * <tr>
 * <td>Silly</td>
 * <td>:p</td>
 * <td>silly.gif</td>
 * </tr>
 * <tr>
 * <td>Angry</td>
 * <td>X-(</td>
 * <td>angry.gif</td>
 * </tr>
 * <tr>
 * <td>Laugh</td>
 * <td>:^O</td>
 * <td>laugh.gif</td>
 * </tr>
 * <tr>
 * <td>Wink</td>
 * <td>;) or ;-)</td>
 * <td>wink.gif</td>
 * </tr>
 * <tr>
 * <td>Blush</td>
 * <td>:8}</td>
 * <td>blush.gif</td>
 * </tr>
 * <tr>
 * <td>Cry</td>
 * <td>:_|</td>
 * <td>cry.gif</td>
 * </tr>
 * <tr>
 * <td>Confused</td>
 * <td>?:|</td>
 * <td>consused.gif</td>
 * </tr>
 * <tr>
 * <td>Shocked</td>
 * <td>:O</td>
 * <td>shocked.gif</td>
 * </tr>
 * <tr>
 * <td>Plain</td>
 * <td>:|</td>
 * <td>plain.gif</td>
 * </tr>
 * </table>
 * <p>
 * Note: special thanks to August Harrison for implementing an earlier version
 * of this filter.
 */
public class Emoticon implements MessageRenderSpecification {
	private final static Logger logger = LogManager.getLogger(Emoticon.class);

	private static String imageHappy = "regular_smile.png";
	private static String imageSad = "sad_smile.png";
	private static String imageGrin = "teeth_smile.png";
	private static String imageLove = "kiss.png";
	private static String imageMischief = "mischief.gif";
	private static String imageCool = "shades_smile.png";
	private static String imageDevil = "devil.gif";
	private static String imageSilly = "tongue_smile.png";
	private static String imageAngry = "angry.gif";
	private static String imageLaugh = "embarrassed_smile.png";
	private static String imageWink = "wink_smile.png";
	private static String imageBlush = "blush.gif";
	private static String imageCry = "cry_smile.png";
	private static String imageConfused = "whatchutalkingabout_smile.png";
	private static String imageShocked = "omg_smile.png";
	private static String imagePlain = "whatchutalkingabout_smile.png";

	private String imageURL = "common\\ckeditor\\plugins\\smiley\\images";
	private boolean filteringSubject = false;
	private boolean filteringBody = true;

	/**
	 * Clones a new filter that will have the same properties and that will wrap
	 * around the specified message.
	 *
	 * @param message the ForumMessage to wrap the new filter around.
	 */
	public ForumMessage render(ForumMessage message) {
		try {
			MessageVO messageVO = message.getMessageVO();
			if (!messageVO.isFiltered()) {
				messageVO.setSubject(convertEmoticons(messageVO.getSubject()));
				messageVO.setBody(convertEmoticons(messageVO.getBody()));
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return message;
	}

	// FILTER PROPERTIES//

	/**
	 * Returns the base URL for emoticon images. This can be specified as an
	 * absolute or relative path.
	 *
	 * @return the base URL for smiley images.
	 */
	public String getImageURL() {
		return imageURL;
	}

	/**
	 * Sets the base URL for emoticon images. This can be specified as an
	 * absolute or relative path.
	 *
	 * @param imageURL the base URL for emoticon images.
	 */
	public void setImageURL(String imageURL) {
		if (imageURL != null && imageURL.length() > 0) {
			if (imageURL.charAt(imageURL.length() - 1) == '/') {
				imageURL = imageURL.substring(0, imageURL.length() - 1);
			}
		}
		this.imageURL = imageURL;
	}

	/**
	 * Returns true if filtering on the subject is enabled.
	 *
	 * @return true if filtering on the subject is enabled.
	 */
	public boolean isFilteringSubject() {
		return filteringSubject;
	}

	/**
	 * Enables or disables filtering on the subject.
	 *
	 * @param filteringSubject toggle value for filtering on subject.
	 */
	public void setFilteringSubject(boolean filteringSubject) {
		this.filteringSubject = filteringSubject;
	}

	/**
	 * Returns true if filtering on the body is enabled.
	 *
	 * @return true if filtering on the body is enabled.
	 */
	public boolean isFilteringBody() {
		return filteringBody;
	}

	/**
	 * Enables or disables filtering on the body.
	 *
	 * @param filteringBody toggle value for filtering on body.
	 */
	public void setFilteringBody(boolean filteringBody) {
		this.filteringBody = filteringBody;
	}

	/**
	 * Returns an HTML image tag using the base image URL and image name.
	 */
	private String buildURL(String imageName) {
		String tag = "<img border=\"0\" src=\"" + imageURL + "/" + imageName + "\">";

		return tag;
	}

	/**
	 * Converts any ASCII emoticons into image equivalents.
	 *
	 * @param input the text to be converted.
	 * @return the input string with the ASCII emoticons replaced with the image
	 * tags.
	 */
	private String convertEmoticons(String input) {
		// Check if the string is null or zero length or if filter is not
		// enabled
		// or imageURL has not been set if so, return what was sent in.
		if (input == null || input.length() == 0) {
			return input;
		}

		// Placeholders for the built img tags
		String imgHappy = null;
		String imgSad = null;
		String imgGrin = null;
		String imgLove = null;
		String imgMischief = null;
		String imgCool = null;
		String imgDevil = null;
		String imgSilly = null;
		String imgAngry = null;
		String imgLaugh = null;
		String imgWink = null;
		String imgBlush = null;
		String imgCry = null;
		String imgConfused = null;
		String imgShocked = null;
		String imgPlain = null;

		// Build the response in a buffer
		StringBuilder buf = new StringBuilder(input.length() + 100);
		char[] chars = input.toCharArray();
		int len = input.length();
		int len1 = len - 1;
		int len2 = len - 2;

		int index = -1, i = 0;
		int oldend = 0;
		String imgTag;

		// Replace each of the emoticons, expanded search for performance
		while (++index < len1) {

			// no tag found yet...
			imgTag = null;

			switch (chars[i = index]) {
				default:
					break;
				case ']':
					// "]:)"
					if (i < len2 && chars[++i] == ':' && chars[++i] == ')')
						imgTag = imgDevil == null ? imgDevil = buildURL(imageDevil) : imgDevil;
					break;
				case ':':
					switch (chars[++i]) {
						default:
							break;
						case ')':
							// ":)"
							imgTag = imgHappy == null ? imgHappy = buildURL(imageHappy) : imgHappy;
							break;
						case '-':
							// ":-)"
							if (i < len1 && chars[++i] == ')')
								imgTag = imgHappy == null ? imgHappy = buildURL(imageHappy) :
										imgHappy;
								// ":-("
							else if (chars[i] == '(')
								imgTag = imgSad == null ? imgSad = buildURL(imageSad) : imgSad;
							else if (chars[i] == 'o')
								imgTag = imgShocked == null ? imgShocked = buildURL(imageShocked)
										: imgShocked;
							break;
						case '(':
							// ":("
							imgTag = imgSad == null ? imgSad = buildURL(imageSad) : imgSad;
							break;
						case 'D':
							// ":D"
							imgTag = imgGrin == null ? imgGrin = buildURL(imageGrin) : imgGrin;
							break;
						case 'x':
							// ":x"
							imgTag = imgLove == null ? imgLove = buildURL(imageLove) : imgLove;
							break;
						case 'p':
							// ":p"
							imgTag = imgSilly == null ? imgSilly = buildURL(imageSilly) : imgSilly;
							break;
						case 'P':
							// ":P"
							imgTag = imgSilly == null ? imgSilly = buildURL(imageSilly) : imgSilly;
							break;
						case '*':
							// ":*)"
							imgTag = imgLaugh == null ? imgLaugh = buildURL(imageLaugh) : imgLaugh;
							break;
						case '^':
							// ":^O"
							if (i < len1 && chars[++i] == 'O')
								imgTag = imgLaugh == null ? imgLaugh = buildURL(imageLaugh) :
										imgLaugh;
							break;
						case '8':
							// ":8}"
							if (i < len1 && chars[++i] == '}')
								imgTag = imgBlush == null ? imgBlush = buildURL(imageBlush) :
										imgBlush;
							break;
						case '_':
							// ":_|"
							if (i < len1 && chars[++i] == '|')
								imgTag = imgCry == null ? imgCry = buildURL(imageCry) : imgCry;
							else if (i < len1 && chars[++i] == '*')
								imgTag = imgLove == null ? imgLove = buildURL(imageLove) : imgLove;
							break;

						case 'O':
							// ":O"
							imgTag = imgShocked == null ? imgShocked = buildURL(imageShocked) :
									imgShocked;
							break;
						case '|':
							// ":|"
							imgTag = imgPlain == null ? imgPlain = buildURL(imagePlain) : imgPlain;
							break;

					}
					break;
				case ';':
					switch (chars[++i]) {
						default:
							break;
						case ')':
							// ";)"
							imgTag = imgWink == null ? imgWink = buildURL(imageWink) : imgWink;
							break;
						case '(':
							// ";("
							imgTag = imgCry == null ? imgCry = buildURL(imageCry) : imgCry;
							break;
						case '-':
							// ";-)"
							if (i < len1 && chars[++i] == ')')
								imgTag = imgWink == null ? imgWink = buildURL(imageWink) : imgWink;
							break;
						case '\\':
							// ";\\"
							imgTag = imgMischief == null ? imgMischief = buildURL(imageMischief) :
									imgMischief;
							break;
					}
					break;
				case 'B':
					// "B-)"
					if (i < len2 && chars[++i] == '-' && chars[++i] == ')')
						imgTag = imgCool == null ? imgCool = buildURL(imageCool) : imgCool;
					break;
				case 'o':
					// "o:)
					if (i < len2 && chars[++i] == ':' && chars[++i] == ')')
						imgTag = imgBlush == null ? imgBlush = buildURL(imageBlush) : imgBlush;
					break;
				case '8':
					// "8-)
					if (i < len2 && chars[++i] == '-' && chars[++i] == ')')
						imgTag = imgCool == null ? imgCool = buildURL(imageCool) : imgCool;
					break;
				case 'X':
					// "X-("
					if (i < len2 && chars[++i] == '-' && chars[++i] == '(')
						imgTag = imgAngry == null ? imgAngry = buildURL(imageAngry) : imgAngry;
					break;
				case '?':
					// "?:|"
					if (i < len2 && chars[++i] == ':' && chars[++i] == '|')
						imgTag = imgConfused == null ? imgConfused = buildURL(imageConfused) :
								imgConfused;
					break;
			}

			// if we found one, replace
			if (imgTag != null) {
				buf.append(chars, oldend, index - oldend);
				buf.append(imgTag);

				oldend = i + 1;
				index = i;
			}
		}
		if (oldend < len) {
			buf.append(chars, oldend, len - oldend);
		}
		return buf.toString();
	}
}
