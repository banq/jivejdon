package com.jdon.jivejdon.model.message;

import java.util.Collection;

public final class FilterPipleVO {

	private final Collection<MessageRenderSpecification> outFilters;
	private final boolean isFiltered;

	public FilterPipleVO(Collection<MessageRenderSpecification> outFilters, boolean isFiltered) {
		this.outFilters = outFilters;
		this.isFiltered = isFiltered;
	}

	public Collection<MessageRenderSpecification> getOutFilters() {
		return outFilters;
	}

	public boolean isFiltered() {
		return isFiltered;
	}
}
