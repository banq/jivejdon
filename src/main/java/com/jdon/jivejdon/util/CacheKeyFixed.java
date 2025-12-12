package com.jdon.jivejdon.util;

import com.jdon.controller.cache.CacheKey;

public class CacheKeyFixed extends CacheKey {

    private final String cacheType;
	private final String dataKey;
	private final String dataTypeName;

	public CacheKeyFixed(String cacheType, String dataKey, String dataTypeName) {
		super(cacheType, dataKey, dataTypeName);
        this.cacheType = cacheType;
		this.dataTypeName = dataTypeName;
		this.dataKey = dataKey;
	}

	public String getCacheType() {
		return cacheType;
	}

	public String getDataKey() {
		return dataKey;
	}

	public String getDataTypeName() {
		return dataTypeName;
	}

	// cacheType + dataTypeName + dataKey
	public String getKey() {
		StringBuilder buffer = new StringBuilder(cacheType);
		buffer.append(dataTypeName);
		if (dataKey != null){
            buffer.append("+");//fixed 
			buffer.append(dataKey.toString());
        }
		return buffer.toString();
	}

	public String toString() {
		return getKey();
	}


}
