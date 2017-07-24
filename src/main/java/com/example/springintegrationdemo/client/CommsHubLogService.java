package com.example.springintegrationdemo.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;

import static java.text.MessageFormat.format;

public class CommsHubLogService {

    Logger log = LoggerFactory.getLogger(getClass());

    public void saveLogEntry(String payload) {

        // save to repo here
        log.info(format("At {0} received message: {1}", LocalTime.now(), payload));
    }
}
