package com.jdon.jivejdon.model.query;

import com.jdon.jivejdon.manager.query.ThreadCompareVO;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HotThreadSpecification {

	public void sortByMessageCount(List<ThreadCompareVO> threads) {
		Comparator comp = new ThreadReplyComparator();
		Collections.sort(threads, Collections.reverseOrder(comp));
	}

	static class ThreadReplyComparator implements Comparator {
		public int compare(Object x, Object y) {
			ThreadCompareVO a = (ThreadCompareVO) x, b = (ThreadCompareVO) y;
			int replya = a.getMessageCount();
			int replyb = b.getMessageCount();
			return replya - replyb;
		}
	}
}
