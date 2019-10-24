package com.jdon.jivejdon.domain.model.message.output.html;

import com.jdon.jivejdon.domain.model.message.output.beanutil.FilterBeanInfo;

public class FontStyleBeanInfo extends FilterBeanInfo {

	public static final String[] PROPERTY_NAMES = new String[0];

	public FontStyleBeanInfo() {
		super();
	}

	public Class getBeanClass() {
		return FontStyle.class;
	}

	public String[] getPropertyNames() {
		return PROPERTY_NAMES;
	}

	public String getName() {
		return "FontStyle";
	}
}
