package com.jdon.jivejdon.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jdon.controller.cache.CacheKey;
import com.jdon.controller.cache.CacheManager;
import com.jdon.model.query.cache.QueryConditonDatakey;

public class BlockCacheManagerFixed {
    private static final Logger logger = LogManager.getLogger(BlockCacheManagerFixed.class);
    public static final String CACHE_TYPE_BLOCK = "BLOCK";

    private final CacheManager cacheManager;
    private final List<CacheKey> cacheKeys = new CopyOnWriteArrayList();
    private final BlockCacheKeyFactoryFixed blockCacheKeyFactory;

    public BlockCacheManagerFixed(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        this.blockCacheKeyFactory = new BlockCacheKeyFactoryFixed();
    }

    public List getBlockKeysFromCache(QueryConditonDatakey qckey) {
        CacheKey cacheKey = this.blockCacheKeyFactory.createCacheKey(qckey.getBlockDataKey(), qckey.getSQlKey());
        return (List) this.cacheManager.fetchObject(cacheKey);
    }

    public void saveBlockKeys(QueryConditonDatakey qckey, List keys) {
        CacheKey cacheKey = this.blockCacheKeyFactory.createCacheKey(qckey.getBlockDataKey(), qckey.getSQlKey());
        this.cacheManager.putObect(cacheKey, keys);
        this.cacheKeys.add(cacheKey);
    }

    public Integer getAllCountsFromCache(QueryConditonDatakey qckey) {
        CacheKey cacheKey = this.blockCacheKeyFactory.createCacheKey(qckey.getBlockDataKey(), qckey.getSQlKey());
        return (Integer) this.cacheManager.fetchObject(cacheKey);
    }

    public void saveAllCounts(QueryConditonDatakey qckey, Integer allCount) {
        CacheKey cacheKey = this.blockCacheKeyFactory.createCacheKey(qckey.getBlockDataKey(), qckey.getSQlKey());
        this.cacheManager.putObect(cacheKey, allCount);
        this.cacheKeys.add(cacheKey);
    }

    public void clearCache() {
        if (this.cacheKeys != null && this.cacheManager != null) {
            Object[] keys = this.cacheKeys.toArray();
            this.cacheKeys.clear();

            for (int i = 0; i < keys.length; ++i) {
                if (keys[i] != null) {
                    try {
                        CacheKey cacheKey = (CacheKey) keys[i];
                        this.cacheManager.removeObect(cacheKey);
                    } catch (Exception var4) {
                        logger.error(var4);
                    }
                }
            }

        }
    }

    private String getSQlKey(String sqlquery, Collection queryParams) {
        StringBuilder sb = new StringBuilder(sqlquery);
        Iterator iter = queryParams.iterator();

        while (iter.hasNext()) {
            Object queryParamO = iter.next();
            if (queryParamO != null) {
                sb.append("+");
                sb.append(queryParamO.toString());
            }
        }

        return sb.toString();
    }

    public void cleaeCache(String sqlquery, Collection queryParams) {
        String SQLKey = this.getSQlKey(sqlquery, queryParams);
        Object[] keys = this.cacheKeys.toArray();

        for (int i = 0; i < keys.length; ++i) {
            if (keys[i] != null) {
                try {
                    CacheKey cacheKey = (CacheKey) keys[i];
                    if (cacheKey.getDataTypeName().equals(SQLKey)) {
                        this.cacheManager.removeObect(cacheKey);
                    }
                } catch (Exception var7) {
                    logger.error(var7);
                }
            }
        }

    }
}
