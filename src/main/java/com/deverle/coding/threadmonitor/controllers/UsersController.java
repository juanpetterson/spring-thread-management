package com.deverle.coding.threadmonitor.controllers;

import com.deverle.coding.threadmonitor.MainExecutorService;
import com.deverle.coding.threadmonitor.ThreadConfig;
import com.deverle.coding.threadmonitor.components.ThreadExecutorMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    ThreadConfig threadConfig;

    @GetMapping
    public Mono<String> getUser() {
        return Mono.just("1").map(value -> {
            try {
                LOGGER.info("Start user sleep call");
                Thread.sleep(5000);
                LOGGER.info("End user sleep call");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return value;
        }).subscribeOn(Schedulers.fromExecutor(threadConfig.threadPoolTaskExecutor()));

//    subscribeOn(Schedulers.fromExecutorService(MainExecutorService.executor));
    }
}
