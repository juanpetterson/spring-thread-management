package com.deverle.coding.threadmonitor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ThreadConfig {

    @Bean
    @Primary
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
//        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("default_task_executor_thread");
        executor.initialize();
//        executor.setThreadFactory();
        executor.setTaskDecorator(new TaskDecorator() {
            @Override
            public Runnable decorate(Runnable runnable) {
                return () -> {
                    long t = System.currentTimeMillis();
                    runnable.run();
                    System.out.printf("Thread %s has a processing time: %s%n", Thread.currentThread().getName(), (System.currentTimeMillis() - t));
                };
            }
        });
        return executor;
    }
}
