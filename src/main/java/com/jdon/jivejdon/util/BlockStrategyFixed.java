package com.jdon.jivejdon.util;

import java.util.Collection;
import java.util.List;

import com.jdon.model.query.block.Block;
import com.jdon.model.query.block.BlockQueryJDBC;
import com.jdon.model.query.block.BlockStrategy;
import com.jdon.model.query.cache.QueryConditonDatakey;
import com.jdon.util.Debug;

public class BlockStrategyFixed {
private static final String module = BlockStrategy.class.getName();
   private final BlockQueryJDBC blockQueryJDBC;
   private final BlockCacheManagerFixed blockCacheManager;
   private int blockLength = 200;

   public BlockStrategyFixed(BlockQueryJDBC blockQueryJDBC, BlockCacheManagerFixed blockCacheManager) {
      this.blockQueryJDBC = blockQueryJDBC;
      this.blockCacheManager = blockCacheManager;
   }

   public Block locate(String sqlquery, Collection queryParams, Object locateId) {
      int blockSize = this.getBlockLength();
      Block block = null;
      int index = -1;
      int prevBlockStart = Integer.MIN_VALUE;
      int nextBlockStart = Integer.MIN_VALUE;
      int start = 0;
      Debug.logVerbose("[JdonFramework]try to locate a block locateId= " + locateId + " blockSize=" + blockSize, module);

      try {
         while(index == -1) {
            block = this.getBlock(sqlquery, queryParams, start, blockSize);
            if (block == null) {
               break;
            }

            List list = block.getList();
            index = list.indexOf(locateId);
            if (index >= 0 && index < list.size()) {
               Debug.logVerbose("[JdonFramework]found the locateId, index= " + index, module);
               if (index == 0 && block.getStart() >= blockSize) {
                  prevBlockStart = start - blockSize;
               } else if (index == blockSize - 1) {
                  nextBlockStart = start + blockSize;
               }
               break;
            }

            if (block.getCount() < blockSize) {
               break;
            }

            start += blockSize;
         }

         if (index == -1) {
            Debug.logVerbose("[JdonFramework] not locate the block that have the locateId= " + locateId, module);
            return null;
         } else {
            Block nextBlock;
            if (prevBlockStart != Integer.MIN_VALUE) {
               nextBlock = this.getBlock(sqlquery, queryParams, prevBlockStart, blockSize);
               nextBlock.getList().addAll(block.getList());
               nextBlock.setStart(nextBlock.getStart() + nextBlock.getCount());
               nextBlock.setCount(nextBlock.getCount() + block.getCount());
               return nextBlock;
            } else if (nextBlockStart != Integer.MIN_VALUE) {
               nextBlock = this.getBlock(sqlquery, queryParams, nextBlockStart, blockSize);
               if (nextBlock != null) {
                  block.getList().addAll(nextBlock.getList());
                  block.setCount(block.getCount() + nextBlock.getCount());
               }

               return block;
            } else {
               return block;
            }
         }
      } catch (Exception var11) {
         Debug.logError(" locate Block error" + var11, module);
         return block;
      }
   }

   public Block getBlock(String sqlquery, Collection queryParams, int startIndex, int count) {
      Debug.logVerbose("[JdonFramework]enter getBlock .. ", module);
      if (count > this.blockLength || count <= 0) {
         count = this.blockLength;
      }

      QueryConditonDatakey qcdk = new QueryConditonDatakey(sqlquery, queryParams, startIndex, count, this.blockLength);
      Block block = this.getBlock(qcdk);
      if (block.getCount() > 0) {
         Debug.logVerbose("[JdonFramework]got a Block" + block.getCount(), module);
         return block;
      } else {
         Debug.logVerbose("[JdonFramework]not found the block!", module);
         return null;
      }
   }

   private Block getBlock(QueryConditonDatakey qcdk) {
      Block clientBlock = new Block(qcdk.getStart(), qcdk.getCount());
      if (clientBlock.getCount() > this.blockLength) {
         clientBlock.setCount(this.blockLength);
      }

      List list = this.getBlockKeys(qcdk);
      Block dataBlock = new Block(qcdk.getBlockStart(), list.size());
      int currentStart = clientBlock.getStart() - dataBlock.getStart();
      Block currentBlock = new Block(currentStart, clientBlock.getCount());
      currentBlock.setList(list);

      try {
         int lastCount = dataBlock.getCount() + dataBlock.getStart() - clientBlock.getStart();
         Debug.logVerbose("[JdonFramework] lastCount=" + lastCount, module);
         if (lastCount < clientBlock.getCount()) {
            if (dataBlock.getCount() == this.blockLength) {
               int newStartIndex = dataBlock.getStart() + dataBlock.getCount();
               int newCount = clientBlock.getCount() - lastCount;
               qcdk.setStart(newStartIndex);
               qcdk.setCount(newCount);
               Debug.logVerbose("[JdonFramework]  newStartIndex=" + newStartIndex + " newCount=" + newCount, module);
               Block nextBlock = this.getBlock(qcdk);
               Debug.logVerbose("[JdonFramework]  nextBlock.getCount()=" + nextBlock.getCount(), module);
               currentBlock.setCount(currentBlock.getCount() + nextBlock.getCount());
            } else {
               currentBlock.setCount(lastCount);
            }
         }
      } catch (Exception var11) {
         Debug.logError(" getBlock error" + var11, module);
      }

      return currentBlock;
   }

   private List getBlockKeys(QueryConditonDatakey qcdk) {
      List keys = this.blockCacheManager.getBlockKeysFromCache(qcdk);
      if (keys == null) {
         keys = this.blockQueryJDBC.fetchDatas(qcdk);
         if (keys != null && keys.size() != 0) {
            this.blockCacheManager.saveBlockKeys(qcdk, keys);
         }
      }

      Debug.logVerbose("[JdonFramework] getBlockKeys, size=" + keys.size(), module);
      return keys;
   }

   public int getBlockLength() {
      return this.blockLength;
   }

   public void setBlockLength(int blockLength) {
      this.blockLength = blockLength;
   }
}
