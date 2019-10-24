package com.jdon.jivejdon.domain.model.message.output.linkurl;

import com.jdon.jivejdon.domain.model.message.output.beanutil.FilterBeanInfo;

public class LinkUrlExtractorBeanInfo extends FilterBeanInfo {

	public LinkUrlExtractorBeanInfo() {
		super();
	}

	@Override
	public String[] getPropertyNames() {
		return new String[0];
	}

	@Override
	public Class getBeanClass() {
		return LinkUrlExtractor.class;
	}

	@Override
	public String getName() {
		return "LinkUrlExtractor";
	}
}
