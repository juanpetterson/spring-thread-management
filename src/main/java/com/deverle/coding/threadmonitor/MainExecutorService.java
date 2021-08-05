package com.deverle.coding.threadmonitor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainExecutorService {

    public static final ExecutorService executor = Executors.newFixedThreadPool(2);
}
