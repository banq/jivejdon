package com.jdon.jivejdon.spi.component.mapreduce;

import java.util.Comparator;
import java.util.Map;

/**
 * Hot Tag Comparator
 */
public class ThreadTagComparator implements Comparator<Long> {

	private final ThreadTagList threadTagList;

	public ThreadTagComparator(ThreadTagList threadTagList) {
		this.threadTagList = threadTagList;
	}

	@Override
	public int compare(Long tagID1, Long tagID2) {
		if (tagID1.longValue() == tagID2.longValue())
			return 0;

		int num1 = threadTagList.getTagThreadIds(tagID1).size() * threadTagList.getTags_countWindows().get(tagID1);
		int num2 = threadTagList.getTagThreadIds(tagID2).size() * threadTagList.getTags_countWindows().get(tagID2);

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
