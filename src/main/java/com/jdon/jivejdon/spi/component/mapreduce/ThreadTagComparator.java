package com.jdon.jivejdon.spi.component.mapreduce;

import java.util.Comparator;
import java.util.Map;

/**
 * Hot Tag Comparator
 */
public class ThreadTagComparator implements Comparator<Long> {

	private final Map<Long, Integer> tags_countWindows;

	public ThreadTagComparator(Map<Long, Integer> tags_countWindows) {
		this.tags_countWindows = tags_countWindows;
	}

	@Override
	public int compare(Long tagID1, Long tagID2) {
		if (tagID1.longValue() == tagID2.longValue())
			return 0;

		int num1 = tags_countWindows.get(tagID1);
		int num2 = tags_countWindows.get(tagID2);

		if (num1 > num2)
			return -1; // returning the first object
		else if (num1 < num2)
			return 1;
		else {
			if (tagID1.longValue() > tagID2.longValue())
				return -1;
			else
				return 1;
		}
	}
}
