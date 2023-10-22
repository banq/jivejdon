package com.jdon.jivejdon.spi.component.mapreduce;

import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.message.MessageUrlVO;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.api.property.TagService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Hot Tag such as #springboot #API ...
 */
public class ThreadTagList {

	public final static int DigsListMAXSize = 30;

	private final static int TIME_WINDOWS = 50;

	private final static int TAGSLIST_SIZE = 30;
	private final ConcurrentHashMap<Long, Integer> tags_countWindows;
	private TreeSet<Long> tagIds;
	private final TagService tagService;
	private final ConcurrentHashMap<Long, String> tags_messageImageUrls;

	public ThreadTagList(TagService tagService) {
		this.tagService = tagService;
		this.tags_countWindows = new ConcurrentHashMap<>();
		this.tags_messageImageUrls = new ConcurrentHashMap<>();
	}

	public void addForumThread(ForumThread forumThread) {
		Date mDate = new Date(forumThread.getCreationDate2());
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
			if(forumThread.getRootMessage().hasImage())
				tags_messageImageUrls.putIfAbsent(threadTag.getTagID(), forumThread.getRootMessage().getMessageUrlVO().getImageUrl());
			
		}
	}


	public List<ThreadTag> getThreadTags() {
		if ( tagIds == null) {
			tagIds = new TreeSet<Long>(new ThreadTagComparator(tags_countWindows));
			tagIds.addAll(tags_countWindows.keySet());		
		}
		return tagIds.stream().limit(TAGSLIST_SIZE).map(tagId -> tagService
					.getThreadTag(tagId)).collect(Collectors.toList());
	}

	public void clear() {
		tags_countWindows.clear();
		tags_messageImageUrls.clear();
	}

	public String[] getImageUrls() {
		if (tagIds == null) {
			tagIds = new TreeSet<Long>(new ThreadTagComparator(tags_countWindows));
			tagIds.addAll(tags_countWindows.keySet());
		}
		return tagIds.stream().limit(TAGSLIST_SIZE).map(tagId -> tags_messageImageUrls.get(tagId))
				.toArray(String[]::new);
	}

	

}
