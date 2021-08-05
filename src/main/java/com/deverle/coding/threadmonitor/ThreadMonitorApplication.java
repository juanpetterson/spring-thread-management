package com.deverle.coding.threadmonitor;

import com.deverle.coding.threadmonitor.components.DelayedCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class ThreadMonitorApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThreadMonitorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ThreadMonitorApplication.class, args);
	}

	@PostConstruct
	public void init() {
		LOGGER.info("CPU: {}", Runtime.getRuntime().availableProcessors());
	}
}
