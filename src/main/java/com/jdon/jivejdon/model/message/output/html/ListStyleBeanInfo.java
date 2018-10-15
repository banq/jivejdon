package com.jdon.jivejdon.model.message.output.html;

import com.jdon.jivejdon.model.message.output.beanutil.FilterBeanInfo;

public class ListStyleBeanInfo extends FilterBeanInfo {

	public static final String[] PROPERTY_NAMES = new String[0];

	public ListStyleBeanInfo() {
		super();
	}

	public Class getBeanClass() {
		return ListStyle.class;
	}

	public String[] getPropertyNames() {
		return PROPERTY_NAMES;
	}

	public String getName() {
		return "ListStyle";
	}
}
