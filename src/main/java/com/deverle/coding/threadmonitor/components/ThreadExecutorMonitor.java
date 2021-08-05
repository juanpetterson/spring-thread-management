package com.deverle.coding.threadmonitor.components;

import com.deverle.coding.threadmonitor.services.ThreadPoolExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ThreadExecutorMonitor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadExecutorMonitor.class);

    @Autowired
    ThreadPoolExecutorService threadPoolExecutorService;

    @Scheduled(fixedRate = 1000)
    public void verifyThreadExecutorStatus() {
        int activeCount = threadPoolExecutorService.getActiveCount();
        int poolSize = threadPoolExecutorService.getPoolSize();
        long totalTaskCount = threadPoolExecutorService.getTotalTaskCount();
        long completedTaskCount = threadPoolExecutorService.getCompletedTaskCount();
        LOGGER.info("Active Count: " + activeCount
                + " Pool Size: " + poolSize
                + " Total Task Count: " + totalTaskCount
                + " Completed Task Count: " + completedTaskCount);

//        threadPoolExecutorService.instantiateDelayedCounter();
    }
}
