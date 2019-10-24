package com.jdon.jivejdon.domain.model.message.output.thumbnailUrl;

import com.jdon.jivejdon.domain.model.message.output.beanutil.FilterBeanInfo;

public class ThumbnailExtractorBeanInfo extends FilterBeanInfo {
	public static final String[] PROPERTY_NAMES = {"thumbpics"};

	public ThumbnailExtractorBeanInfo() {
		super();
	}

	@Override
	public String[] getPropertyNames() {
		return PROPERTY_NAMES;
	}

	@Override
	public Class getBeanClass() {
		return ThumbnailExtractor.class;
	}

	@Override
	public String getName() {
		return "ThumbnailExtractor";
	}
}
