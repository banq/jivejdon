/*
 * Copyright 2003-2009 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jdon.jivejdon.util;

import java.util.Calendar;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.jdon.annotation.Component;
import com.jdon.container.pico.Startable;

@Component("scheduledExecutorUtil")
public class ScheduledExecutorUtil implements Startable {

   private static final int SCHEDULER_THREADS = 2; // 调度线程数
    private static final int DB_THREAD_COUNT = 4; // 数据库线程池大小
    private static final Semaphore DB_SEMAPHORE = new Semaphore(4); // 并发限制

    private final ScheduledExecutorService executor;
    private static final ExecutorService DB_EXECUTOR = 
        Executors.newFixedThreadPool(DB_THREAD_COUNT);

    public ScheduledExecutorUtil() {
        this.executor = new ScheduledThreadPoolExecutor(SCHEDULER_THREADS, 
            new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Override
    public void start() {
        System.out.println("ScheduledExecutorUtil started");
    }

    @Override
    public void stop() {
        shutdownExecutor(executor, "executor");
        shutdownExecutor(DB_EXECUTOR, "db executor");
    }

    // 通用调度方法，支持时间限制
    public void scheduleTimeRestrictedDbTask(Runnable dbTask, long delay, long period, TimeUnit unit) {
        executor.scheduleAtFixedRate(() -> {
            long start = System.currentTimeMillis();
            try {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                if (hour >= 9 && hour < 18) {
                    if (DB_SEMAPHORE.tryAcquire(1, TimeUnit.SECONDS)) {
                        CompletableFuture.runAsync(() -> {
                            try {
                                dbTask.run();
                            } finally {
                                DB_SEMAPHORE.release();
                            }
                        }, DB_EXECUTOR).exceptionally(e -> {
                            System.err.println("DB task failed: " + e.getMessage());
                            return null;
                        });
                    } else {
                        System.out.println("Skipped DB task due to semaphore limit");
                    }
                }
                System.out.println("Scheduled check in " + (System.currentTimeMillis() - start) + "ms");
            } catch (InterruptedException e) {
                System.err.println("Task scheduling interrupted");
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.err.println("Task scheduling failed: " + e.getMessage());
            }
        }, delay, period, unit);
    }

    private void shutdownExecutor(ExecutorService executorService, String name) {
        try {
            System.out.println("Shutting down " + name);
            executorService.shutdown();
            if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                System.out.println(name + " did not terminate in time, forcing shutdown");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted while shutting down " + name);
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

	public ScheduledExecutorService getScheduExec() {
		return executor;
	}

}
