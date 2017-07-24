package com.example.springintegrationdemo.server;


import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.time.LocalTime;
import java.util.UUID;

import static java.text.MessageFormat.format;

public class LogContentBroadcaster implements MessageSource<String> {

//    char delim = 0x0a;
    char delim = '|';

    private int count = 0;

    @Override
    public Message<String> receive() {

        return new GenericMessage<>(newLogEntry());
    }

    private String newLogEntry() {
        return format("time: {0}; payload: {1}{2}{3}{4}{5}{6}", LocalTime.now(), count++, delim, count++, delim, count++, delim);
    }

    private String randomString() {
        return UUID.randomUUID().toString();
    }
}
