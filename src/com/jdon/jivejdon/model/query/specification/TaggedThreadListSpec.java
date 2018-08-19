package com.jdon.jivejdon.model.query.specification;

public class TaggedThreadListSpec extends ThreadListSpec {
	
	protected Long tagID;

	public TaggedThreadListSpec() {
		sorttableName = "threadID";
	}

	public Long getTagID() {
		return tagID;
	}

	public void setTagID(Long tagID) {
		this.tagID = tagID;
	}
	
	

}
