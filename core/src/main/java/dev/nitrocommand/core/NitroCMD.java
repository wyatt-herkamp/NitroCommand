package dev.nitrocommand.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NitroCMD {
    public static Logger LOGGER = LoggerFactory.getLogger(NitroCMD.class);
    /**
     * This is used for running minor tasks.
     */
    public static ExecutorService INTERNAL_THREAD_POOL = Executors.newFixedThreadPool(1);
}
