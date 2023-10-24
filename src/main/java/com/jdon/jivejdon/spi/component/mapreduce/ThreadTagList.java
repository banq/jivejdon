package com.jdon.jivejdon.spi.component.mapreduce;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.jdon.jivejdon.api.property.TagService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.property.ThreadTag;

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
	private final Set<Long> tagIds;
	private final ConcurrentHashMap<Long, Long> threadId_tagIDs;
	private final ConcurrentHashMap<Long, String> tags_messageImageUrls;

	public ThreadTagList(TagService tagService, ForumMessageQueryService forumMessageQueryService) {
		this.tagService = tagService;
		this.forumMessageQueryService = forumMessageQueryService;
		this.tags_countWindows = new ConcurrentHashMap<>();
		this.tags_messageImageUrls = new ConcurrentHashMap<>();
		this.tagThreadIds = new ConcurrentHashMap<>();
		this.threadId_tagIDs = new ConcurrentHashMap<>();
		this.tagIds = Collections.synchronizedSet(new TreeSet<Long>(new ThreadTagComparator(this)));
	}

	public void addForumThread(ForumThread forumThread) {
		Date mDate = new Date(forumThread.getCreationDate2());
		Date nowDate = new Date();
		long daysBetween = (nowDate.getTime() - mDate.getTime() + 1000000) / (60 * 60 * 24 * 1000);
		if (daysBetween < TIME_WINDOWS) {
			for (ThreadTag threadTag : forumThread.getTags()) {
				addTagsSorting(forumThread,threadTag);
			}	
		}
	}

	private void addTagsSorting(ForumThread forumThread, ThreadTag threadTag) {
		tags_countWindows.merge(threadTag.getTagID(), 1, (oldValue, one) -> oldValue +
				one);
		if (forumThread.getRootMessage().hasImage())
			tags_messageImageUrls.putIfAbsent(threadTag.getTagID(),
					forumThread.getRootMessage().getMessageUrlVO().getImageUrl());

		Long threadId_tagID = threadId_tagIDs.computeIfAbsent(forumThread.getThreadId(), k -> threadTag.getTagID());
		if (threadId_tagID.longValue() == threadTag.getTagID().longValue()) {
			TreeSet<Long> threadIdsForTags = tagThreadIds.computeIfAbsent(threadTag.getTagID(),
					k -> new TreeSet<>(new ThreadDigComparator(forumMessageQueryService)));
			if (threadIdsForTags.size() < 5)
				threadIdsForTags.add(forumThread.getThreadId());
		}
	}

	public List<ThreadTag> getThreadTags() {
		if (tagIds.isEmpty())
			tagIds.addAll(tags_countWindows.keySet());
		return tagIds.stream().limit(TAGSLIST_SIZE).map(tagId -> tagService
				.getThreadTag(tagId)).collect(Collectors.toList());
	}

	
	public ConcurrentHashMap<Long, Integer> getTags_countWindows() {
		return tags_countWindows;
	}


	public ConcurrentHashMap<Long, Long> getThreadId_tagIDs() {
		return threadId_tagIDs;
	}

	public String[] getImageUrls() {
		if (tagIds.isEmpty())
			tagIds.addAll(tags_countWindows.keySet());
		return tagIds.stream().limit(TAGSLIST_SIZE).map(tagId -> tags_messageImageUrls.get(tagId))
				.toArray(String[]::new);
	}

	public TreeSet<Long> getTagThreadIds(Long tagID) {
		return tagThreadIds.get(tagID);
	}

	

}
