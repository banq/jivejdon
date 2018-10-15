package com.jdon.jivejdon.manager.mapreduce;

import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.ThreadTag;
import com.jdon.jivejdon.service.TagService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ThreadTagList {

	public final static int DigsListMAXSize = 30;

	private final static int TIME_WINDOWS = 100;

	private final static int TAGSLIST_SIZE = 10;
	private final ConcurrentHashMap<Long, Integer> tags_countWindows;
	private final List<ThreadTag> tags_cachedWindows;
	private final TagService tagService;

	public ThreadTagList(TagService tagService) {
		this.tagService = tagService;
		this.tags_countWindows = new ConcurrentHashMap();
		this.tags_cachedWindows = new ArrayList<>(TAGSLIST_SIZE);
		;
	}

	public void addForumThread(ForumThread forumThread) {
		Date mDate = new Date(forumThread.getState().getModifiedDate2());
		Date nowDate = new Date();
		long daysBetween = (nowDate.getTime() - mDate.getTime() + 1000000) / (60 * 60 * 24 * 1000);
		if (daysBetween < TIME_WINDOWS) {
			addTagsSorting(forumThread);
		}
	}

	private void addTagsSorting(ForumThread forumThread) {
		for (ThreadTag threadTag : forumThread.getTags()) {
			tags_countWindows.merge(threadTag.getTagID(), 1, (oldValue, one) -> oldValue +
					one);
		}
	}


	public Collection<ThreadTag> getThreadTags() {
		if (tags_cachedWindows.isEmpty()) {
			TreeSet<Long> tagIds = new TreeSet<Long>(new ThreadTagComparator(tags_countWindows));
			tagIds.addAll(tags_countWindows.keySet());
			tags_cachedWindows.addAll(tagIds.stream().limit(TAGSLIST_SIZE).map(tagId -> tagService
					.getThreadTag(tagId)).collect(Collectors.toList()));
		}
		return tags_cachedWindows;
	}

	public void clear() {
		tags_countWindows.clear();
		tags_cachedWindows.clear();
	}

}
