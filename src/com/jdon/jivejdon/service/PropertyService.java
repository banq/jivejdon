package com.jdon.jivejdon.service;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.Property;

public interface PropertyService {

	PageIterator getThreadIdsByNameAndValue(String name, String value);
	
	PageIterator getThreadIdsByName(String name);

	void updateThreadProperty(Long threadId, Property property);

	void deleteThreadProperty(Long threadId, Property property);

}
