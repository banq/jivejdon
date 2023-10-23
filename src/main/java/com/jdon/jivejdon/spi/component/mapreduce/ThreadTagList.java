package com.jdon.jivejdon.spi.component.mapreduce;

import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.message.MessageUrlVO;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.domain.model.query.specification.ApprovedListSpec;
import com.jdon.jivejdon.api.property.TagService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
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
	
	private final TagService tagService;
	private final ForumMessageQueryService forumMessageQueryService;

	private final ConcurrentHashMap<Long, Integer> tags_countWindows;
	private final ConcurrentHashMap<Long, TreeSet<Long>> tagThreadIds;
	private final TreeSet<Long> tagIds;
	private final TreeSet<Long> threadIds;
	private final ConcurrentHashMap<Long, String> tags_messageImageUrls;

	public ThreadTagList(TagService tagService, ForumMessageQueryService forumMessageQueryService) {
		this.tagService = tagService;
		this.forumMessageQueryService = forumMessageQueryService;
		this.tags_countWindows = new ConcurrentHashMap<>();
		this.tags_messageImageUrls = new ConcurrentHashMap<>();
		this.tagThreadIds = new ConcurrentHashMap<>();
		this.threadIds = new TreeSet<>();
		this.tagIds = new TreeSet<Long>(new ThreadTagComparator(this));
	}

	public void addForumThread(ForumThread forumThread) {
		Date mDate = new Date(forumThread.getCreationDate2());
		Date nowDate = new Date();
		long daysBetween = (nowDate.getTime() - mDate.getTime() + 1000000) / (60 * 60 * 24 * 1000);
		if (daysBetween < TIME_WINDOWS) {
			if (!threadIds.contains(forumThread.getThreadId())) {
				addTagsSorting(forumThread);
				threadIds.add(forumThread.getThreadId());
			}

		}
	}

	private void addTagsSorting(ForumThread forumThread) {
		for (ThreadTag threadTag : forumThread.getTags()) {
			tags_countWindows.merge(threadTag.getTagID(), 1, (oldValue, one) -> oldValue +
					one);
			if (forumThread.getRootMessage().hasImage())
				tags_messageImageUrls.putIfAbsent(threadTag.getTagID(),
						forumThread.getRootMessage().getMessageUrlVO().getImageUrl());

			TreeSet<Long> threadIdsForTags = tagThreadIds.computeIfAbsent(threadTag.getTagID(),
					k -> new TreeSet<>(new ThreadDigComparator(forumMessageQueryService)));
			if (threadIdsForTags.size()<4)
			   threadIdsForTags.add(forumThread.getThreadId());
		}
	}

	public List<ThreadTag> getThreadTags() {
		if (isEmpty()) 
		    sort();
		return tagIds.stream().limit(TAGSLIST_SIZE).map(tagId -> tagService
					.getThreadTag(tagId)).collect(Collectors.toList());
	}

	private synchronized void sort() {
		if (!threadIds.isEmpty())
		  tagIds.addAll(tags_countWindows.keySet());
		threadIds.clear();
	}

	
	public ConcurrentHashMap<Long, Integer> getTags_countWindows() {
		return tags_countWindows;
	}

	public boolean isEmpty(){
		return (tagIds.isEmpty())?true:false;
	}


	public void clear() {
		tags_countWindows.clear();
		tags_messageImageUrls.clear();
		this.tagIds.clear();
		this.tagThreadIds.clear();
		this.threadIds.clear();
	}

	public String[] getImageUrls() {
		if (isEmpty())
			sort();
		return tagIds.stream().limit(TAGSLIST_SIZE).map(tagId -> tags_messageImageUrls.get(tagId))
				.toArray(String[]::new);
	}

	public TreeSet<Long> getTagThreadIds(Long tagID) {
		return tagThreadIds.get(tagID);
	}

	

}
