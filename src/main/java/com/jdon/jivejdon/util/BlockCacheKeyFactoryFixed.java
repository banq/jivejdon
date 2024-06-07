package com.jdon.jivejdon.util;

import com.jdon.controller.cache.CacheKey;
import com.jdon.controller.cache.StringCacheKeyFactory;

public class BlockCacheKeyFactoryFixed extends StringCacheKeyFactory {
   public static final String CACHE_TYPE_BLOCK = "BLOCK";

   public BlockCacheKeyFactoryFixed() {
   }

   public CacheKey createCacheKeyImp(String dataKey, String modelClassName) {
      return new CacheKeyFixed("BLOCK", dataKey, modelClassName);
   }
   

}
