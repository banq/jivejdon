/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.domain.model.message.output.hotkeys;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.jivejdon.domain.model.property.HotKeys;
import com.jdon.util.Debug;

/**
 * removing replace hot keywords with HotKeys added by administrator
 * all char:
 * ([!\"#$%&'()*+,-./:;<=>?@[\\\\]^_`{|}~]|[a-zA-Z0-9\\s\\u4e00-\\uFFFF]|[\\s])(?i)
 * @author banq
 * 
 */
public class HotKeysFilter implements Function<MessageVO, MessageVO> {
	private final static String module = HotKeysFilter.class.getName();

	private String prefix_regEx = "([\\u4e00-\\uFFFF]|[\\s])(?i)"; // chinese or
	// whitespace
	private String suffix_regEx = "";

	private final static ConcurrentHashMap<String, Pattern> Patterns = new ConcurrentHashMap<>();
	//
	public MessageVO apply(MessageVO messageVO) {
		return messageVO.builder().subject(messageVO.getSubject()).body(applyFilteredBody(messageVO)).build();
	}

	/**
	 * space + keyword + space will be replaced
	 */
	public String applyFilteredBody(MessageVO messageVO) {
		String body = messageVO.getBody();
		try {
			HotKeys hotKeys = messageVO.getForumMessage().getHotKeys();
			if (hotKeys == null)
				return body;

			Map<String, String> searchMap = hotKeys.getProps().stream().collect(Collectors.toMap(prop->prop.getName(), prop->prop.getValue()));
			body =  parallelReplace(body,searchMap);	
		} catch (Exception e) {
			Debug.logError("" + e, module);
		}
		return body;
	}

	//from chatGPT
	public  String parallelReplace(String input,Map<String, String> searchMap) {
        int parallelism = ForkJoinPool.commonPool().getParallelism();

        // Split the input string into chunks based on the number of available processors
        int chunkSize = input.length() / parallelism;
        String[] chunks = new String[parallelism];
        for (int i = 0; i < parallelism - 1; i++) {
            chunks[i] = input.substring(i * chunkSize, (i + 1) * chunkSize);
        }
        chunks[parallelism - 1] = input.substring((parallelism - 1) * chunkSize);

        // Use parallel stream for concurrent processing
        String result = String.join("", java.util.Arrays.stream(chunks)
                .parallel()
                .map(chunk -> convertSearch(searchMap,chunk))
                .toArray(String[]::new));

        return result;
    }

	public static void main(String[] args) {
		HotKeysFilter hotKeysFilter = new HotKeysFilter();
		// Example usage
		String largeText = "This is a large text with multiple occurrences of the word Java. Replace Java with another word.";
		Map<String, String> searchMap = new HashMap<>();
		searchMap.put("Java", "GPT-3");
		searchMap.put("word", "GPT-4");

		String result = hotKeysFilter.parallelReplace(largeText,searchMap);

		System.out.println("Original: " + largeText);
		System.out.println("Modified: " + result);
	}

	private String convertSearch(Map<String, String> searchMap, String chunk) {
		for (String key : searchMap.keySet()) {
			String regEx = prefix_regEx + key + suffix_regEx;
			String replacement = getKeyUrlStr(key, searchMap.get(key));
			Pattern pattern = Patterns.computeIfAbsent(replacement, k -> Pattern.compile(regEx));
			chunk = pattern.matcher(chunk).replaceFirst(replacement);
		}
		return chunk;
	}

	

	private String getKeyUrlStr(String name, String url) {
		StringBuilder bf = new StringBuilder();
		bf.append("<a href='");
		bf.append(url);

		bf.append("' target=_blank><b>");
		// // class="hotkeys ajax_query=cache"
		// bf.append(" class='hotkeys ajax_query=").append(name).append("' ");
		// bf.append(" id='id_").append(url).append("' ><b>");
		bf.append(name);
		bf.append("</b></a>");
		return bf.toString();
	}

	public String getPrefix_regEx() {
		return prefix_regEx;
	}

	public void setPrefix_regEx(String prefix_regEx) {
		if (prefix_regEx != null)
			this.prefix_regEx = prefix_regEx;
	}

	public String getSuffix_regEx() {
		return suffix_regEx;
	}

	public void setSuffix_regEx(String suffix_regEx) {
		if (suffix_regEx != null)
			this.suffix_regEx = suffix_regEx;
	}

	// public static void main(String[] args) {
	// 	System.out.println(Pattern.compile("(.*)" + "上下文" + "").matcher("sss(上下文").replaceFirst("$1" + "hello"));
	// }

}
