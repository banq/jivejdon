package com.jdon.jivejdon.api.imp;

import com.jdon.annotation.Service;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.property.Property;
import com.jdon.jivejdon.infrastructure.repository.dao.PropertyDao;
import com.jdon.jivejdon.api.property.PropertyService;

@Service("propertyService")
public class PropertyServiceImp implements PropertyService {

	private PropertyDao propertyDao;

	public PropertyServiceImp(PropertyDao propertyDao) {
		this.propertyDao = propertyDao;
	}

	public PageIterator getThreadIdsByNameAndValue(String name, String value) {
		return propertyDao.getIdsByNameAndValue(Constants.THREAD, name, value);
	}
	
	public PageIterator getThreadIdsByName(String name) {
		return propertyDao.getIdsByName(Constants.THREAD, name);
	}

	public void updateThreadProperty(Long threadId, Property property) {
		propertyDao.updateProperty(Constants.THREAD, threadId, property);
	}

	public void deleteThreadProperty(Long threadId, Property property) {
		propertyDao.deleteProperty(Constants.THREAD, threadId, property);
	}

}
