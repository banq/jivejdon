package com.jdon.jivejdon.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import com.jdon.cache.LRUCache;
import com.jdon.controller.cache.CacheManager;
import com.jdon.controller.model.PageIterator;
import com.jdon.model.query.JdbcTemp;
import com.jdon.model.query.PageIteratorSolver;
import com.jdon.model.query.block.Block;
import com.jdon.model.query.block.BlockQueryJDBC;
import com.jdon.model.query.block.BlockQueryJDBCTemp;
import com.jdon.model.query.cache.QueryConditonDatakey;
import com.jdon.util.Debug;
import com.jdon.util.UtilValidate;


public class PageIteratorSolverFixed {
 private static final String module = PageIteratorSolver.class.getName();
   private DataSource dataSource;
   private final BlockCacheManagerFixed blockCacheManager;
   private BlockStrategyFixed blockStrategy;
   private boolean cacheEnable;

   public PageIteratorSolverFixed(DataSource dataSource, CacheManager cacheManager) {
      this.cacheEnable = true;
      this.dataSource = dataSource;
      this.blockCacheManager = new BlockCacheManagerFixed(cacheManager);
      this.blockStrategy = new BlockStrategyFixed(new BlockQueryJDBCTemp(dataSource), this.blockCacheManager);
   }

   public PageIteratorSolverFixed(DataSource dataSource, CacheManager cacheManager, BlockStrategyFixed blockStrategy) {
      this(dataSource, cacheManager);
      this.blockStrategy = blockStrategy;
   }

   public PageIteratorSolverFixed(DataSource dataSource, CacheManager cacheManager, boolean cacheEnable) {
      this(dataSource, cacheManager);
      this.cacheEnable = cacheEnable;
  
   }

   public PageIteratorSolverFixed(DataSource dataSource) {
      this.cacheEnable = true;
      this.dataSource = dataSource;
      CacheManager cacheManager = new CacheManager(new LRUCache("cache.xml"));
      this.blockCacheManager = new BlockCacheManagerFixed(cacheManager);
      this.blockStrategy = new BlockStrategyFixed(new BlockQueryJDBCTemp(dataSource), this.blockCacheManager);
   }

   public Object querySingleObject(Collection queryParams, String sqlquery) throws Exception {
      JdbcTemp jdbcTemp = new JdbcTemp(this.dataSource);
      return jdbcTemp.querySingleObject(queryParams, sqlquery);
   }

   public List queryMultiObject(Collection queryParams, String sqlquery) throws Exception {
      JdbcTemp jdbcTemp = new JdbcTemp(this.dataSource);
      return jdbcTemp.queryMultiObject(queryParams, sqlquery);
   }

   public PageIterator getDatas(String queryParam, String sqlqueryAllCount, String sqlquery, int start, int count) {
      if (UtilValidate.isEmpty(sqlqueryAllCount)) {
         Debug.logError(" the parameter sqlqueryAllCount is null", module);
         return new PageIterator();
      } else if (UtilValidate.isEmpty(sqlquery)) {
         Debug.logError(" the parameter sqlquery is null", module);
         return new PageIterator();
      } else {
         Collection queryParams = new ArrayList();
         if (!UtilValidate.isEmpty(queryParam)) {
            queryParams.add(queryParam);
         }

         return this.getPageIterator(sqlqueryAllCount, sqlquery, (Collection)queryParams, start, count);
      }
   }

   public PageIterator getPageIterator(String sqlqueryAllCount, String sqlquery, String queryParam, int start, int count) {
      if (UtilValidate.isEmpty(sqlqueryAllCount)) {
         Debug.logError(" the parameter sqlqueryAllCount is null", module);
         return new PageIterator();
      } else if (UtilValidate.isEmpty(sqlquery)) {
         Debug.logError(" the parameter sqlquery is null", module);
         return new PageIterator();
      } else {
         return this.getDatas(queryParam, sqlqueryAllCount, sqlquery, start, count);
      }
   }

   public PageIterator getPageIterator(String sqlqueryAllCount, String sqlquery, Collection queryParams, int startIndex, int count) {
      Debug.logVerbose("[JdonFramework]enter getPageIterator .. start= " + startIndex + " count=" + count, module);
      if (queryParams == null) {
         Debug.logError(" the parameters collection is null", module);
         return new PageIterator();
      } else {
         if (count > this.blockStrategy.getBlockLength() || count <= 0) {
            count = this.blockStrategy.getBlockLength();
         }

         Block currentBlock = this.getBlock(sqlquery, queryParams, startIndex, count);
         if (currentBlock == null) {
            return new PageIterator();
         } else {
            startIndex = currentBlock.getStart();
            int endIndex = startIndex + currentBlock.getCount();
            Object[] keys = currentBlock.getList().toArray();
            int allCount = this.getDatasAllCount(queryParams, sqlqueryAllCount);
            Debug.logVerbose("[JdonFramework]currentBlock: startIndex=" + startIndex + " endIndex=" + endIndex + " keys length=" + keys.length, module);
            if (endIndex < startIndex) {
               Debug.logWarning("WARNNING : endIndex < startIndex", module);
               return new PageIterator();
            } else {
               return new PageIterator(allCount, keys, startIndex, endIndex, count);
            }
         }
      }
   }

   public Block locate(String sqlquery, Collection queryParams, Object locateId) {
      return this.blockStrategy.locate(sqlquery, queryParams, locateId);
   }

   public Block getBlock(String sqlquery, Collection queryParams, int startIndex, int count) {
      return this.blockStrategy.getBlock(sqlquery, queryParams, startIndex, count);
   }

   public int getDatasAllCount(String queryParam, String sqlquery) {
      Collection queryParams = new ArrayList();
      queryParams.add(queryParam);
      return this.getDatasAllCount((Collection)queryParams, sqlquery);
   }

   public int getDatasAllCount(Collection queryParams, String sqlquery) {
      QueryConditonDatakey qcdk = new QueryConditonDatakey(sqlquery, queryParams);
      return this.getDatasAllCount(qcdk);
   }

   public int getDatasAllCount(QueryConditonDatakey qcdk) {
      int allCountInt = 0;

      try {
         Integer allCount = this.blockCacheManager.getAllCountsFromCache(qcdk);
         if (allCount != null && this.cacheEnable) {
            allCountInt = allCount;
         } else {
            BlockQueryJDBC blockQueryJDBC = new BlockQueryJDBCTemp(this.dataSource);
            allCountInt = blockQueryJDBC.fetchDataAllCount(qcdk);
            if (this.cacheEnable && allCountInt != 0) {
               this.blockCacheManager.saveAllCounts(qcdk, new Integer(allCountInt));
            }
         }
      } catch (Exception var5) {
         Debug.logError(" getDatasAllCount error:" + var5, module);
      }

      return allCountInt;
   }

   public void clearCache() {
      Debug.logVerbose("[JdonFramework] clear the cache for the batch inquiry!", module);
      this.blockCacheManager.clearCache();
   }

   public void clearCache(String sqlquery, Collection queryParams) {
      Debug.logVerbose("[JdonFramework] clear the cache for the batch inquiry!", module);
      this.blockCacheManager.cleaeCache(sqlquery, queryParams);
   }

   public boolean isCacheEnable() {
      return this.cacheEnable;
   }

   public void setCacheEnable(boolean cacheEnable) {
      this.cacheEnable = cacheEnable;
   }
}
