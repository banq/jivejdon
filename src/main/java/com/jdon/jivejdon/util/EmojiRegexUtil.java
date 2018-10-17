package com.jdon.jivejdon.util;

/**
 * Created by zhuleiyue on 2017/3/20.
 * <p>
 * emoji regex
 */
public class EmojiRegexUtil {

	private static final String MiscellaneousSymbolsAndPictographs =
			"[\\uD83C\\uDF00-\\uD83D\\uDDFF]";

	private static final String SupplementalSymbolsAndPictographs =
			"[\\uD83E\\uDD00-\\uD83E\\uDDFF]";

	private static final String Emoticons = "[\\uD83D\\uDE00-\\uD83D\\uDE4F]";

	private static final String TransportAndMapSymbols = "[\\uD83D\\uDE80-\\uD83D\\uDEFF]";

	private static final String MiscellaneousSymbols = "[\\u2600-\\u26FF]\\uFE0F?";

	private static final String Dingbats = "[\\u2700-\\u27BF]\\uFE0F?";

	private static final String EnclosedAlphanumerics = "\\u24C2\\uFE0F?";

	private static final String RegionalIndicatorSymbol = "[\\uD83C\\uDDE6-\\uD83C\\uDDFF]{1,2}";

	private static final String EnclosedAlphanumericSupplement =
			"[\\uD83C\\uDD70\\uD83C\\uDD71\\uD83C\\uDD7E\\uD83C\\uDD7F\\uD83C\\uDD8E\\uD83C" +
					"\\uDD91" +
					"-\\uD83C\\uDD9A]\\uFE0F?";

	private static final String BasicLatin = "[\\u0023\\u002A\\u0030-\\u0039]\\uFE0F?\\u20E3";

	private static final String Arrows = "[\\u2194-\\u2199\\u21A9-\\u21AA]\\uFE0F?";

	private static final String MiscellaneousSymbolsAndArrows =
			"[\\u2B05-\\u2B07\\u2B1B\\u2B1C\\u2B50\\u2B55]\\uFE0F?";

	private static final String SupplementalArrows = "[\\u2934\\u2935]\\uFE0F?";

	private static final String CJKSymbolsAndPunctuation = "[\\u3030\\u303D]\\uFE0F?";

	private static final String EnclosedCJKLettersAndMonths = "[\\u3297\\u3299]\\uFE0F?";

	private static final String EnclosedIdeographicSupplement =
			"[\\uD83C\\uDE01\\uD83C\\uDE02\\uD83C\\uDE1A\\uD83C\\uDE2F\\uD83C\\uDE32-\\uD83C" +
					"\\uDE3A\\uD83C\\uDE50\\uD83C\\uDE51]\\uFE0F?";

	private static final String GeneralPunctuation = "[\\u203C\\u2049]\\uFE0F?";

	private static final String GeometricShapes =
			"[\\u25AA\\u25AB\\u25B6\\u25C0\\u25FB-\\u25FE]\\uFE0F?";

	private static final String LatinSupplement = "[\\u00A9\\u00AE]\\uFE0F?";

	private static final String LetterlikeSymbols = "[\\u2122\\u2139]\\uFE0F?";

	private static final String MahjongTiles = "\\uD83C\\uDC04\\uFE0F?";

	private static final String PlayingCards = "\\uD83C\\uDCCF\\uFE0F?";

	private static final String MiscellaneousTechnical =
			"[\\u231A\\u231B\\u2328\\u23CF\\u23E9-\\u23F3\\u23F8-\\u23FA]\\uFE0F?";

	public static String getFullEmojiRegex() {
		return "(?:"
				+ MiscellaneousSymbolsAndPictographs + "|"
				+ SupplementalSymbolsAndPictographs + "|"
				+ Emoticons + "|"
				+ TransportAndMapSymbols + "|"
				+ MiscellaneousSymbols + "|"
				+ Dingbats + "|"
				+ EnclosedAlphanumerics + "|"
				+ RegionalIndicatorSymbol + "|"
				+ EnclosedAlphanumericSupplement + "|"
				+ BasicLatin + "|"
				+ Arrows + "|"
				+ MiscellaneousSymbolsAndArrows + "|"
				+ SupplementalArrows + "|"
				+ CJKSymbolsAndPunctuation + "|"
				+ EnclosedCJKLettersAndMonths + "|"
				+ EnclosedIdeographicSupplement + "|"
				+ GeneralPunctuation + "|"
				+ GeometricShapes + "|"
				+ LatinSupplement + "|"
				+ LetterlikeSymbols + "|"
				+ MahjongTiles + "|"
				+ PlayingCards + "|"
				+ MiscellaneousTechnical + ")";
	}
}
