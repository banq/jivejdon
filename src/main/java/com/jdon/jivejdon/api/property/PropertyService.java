package com.jdon.jivejdon.service.property;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.property.Property;

public interface PropertyService {

	PageIterator getThreadIdsByNameAndValue(String name, String value);
	
	PageIterator getThreadIdsByName(String name);

	void updateThreadProperty(Long threadId, Property property);

	void deleteThreadProperty(Long threadId, Property property);

}
