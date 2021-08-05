package com.deverle.coding.threadmonitor.services;

import com.deverle.coding.threadmonitor.components.DelayedCounter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class ThreadPoolExecutorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolExecutorService.class);

    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private ApplicationContext applicationContext;

    private int counter = 0;

    public void instantiateDelayedCounter() {
        Resilience4JCircuitBreaker circuitBreaker = circuitBreakerFactory.create("delayedCounter");
        Supplier<Boolean> booleanSupplier = () -> getActiveCount() < 3;

        // TODO: validate by CircuitBreaker service
        boolean canInitNewCounter = circuitBreaker.run(booleanSupplier, throwable -> false);

        if(canInitNewCounter) {
            DelayedCounter myCounter = applicationContext.getBean(DelayedCounter.class);
            myCounter.setThreadName("Thread " + counter++);
            taskExecutor.execute(myCounter);
        } else {
            LOGGER.info("DelayedCounter prevented to initialize!");
        }
    }

    public int getPoolSize() {
        ThreadPoolTaskExecutor t = (ThreadPoolTaskExecutor) taskExecutor;
        return t.getPoolSize();
    }

    public int getActiveCount() {
        ThreadPoolTaskExecutor t = (ThreadPoolTaskExecutor) taskExecutor;
        return t.getActiveCount();
    }

    public long getCompletedTaskCount() {
        ThreadPoolTaskExecutor t = (ThreadPoolTaskExecutor) taskExecutor;
        return t.getThreadPoolExecutor().getCompletedTaskCount();
    }

    public long getTotalTaskCount() {
        ThreadPoolTaskExecutor t = (ThreadPoolTaskExecutor) taskExecutor;
        return t.getThreadPoolExecutor().getTaskCount();
    }

    public void shutdownService() {
        ThreadPoolTaskExecutor t = (ThreadPoolTaskExecutor) taskExecutor;
        t.shutdown();
    }
}
