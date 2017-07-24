package com.example.springintegrationdemo.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.ip.tcp.serializer.ByteArrayLfSerializer;

import java.io.IOException;
import java.io.InputStream;

public class MyByteArrayLfSerializer extends ByteArrayLfSerializer {

    Logger log = LoggerFactory.getLogger(getClass());

    @Override protected byte[] doDeserialize(final InputStream inputStream, final byte[] buffer) throws IOException {

        log.info("deserialising with delimeter {}:{}", (int)'\f', 0x0a);

        return super.doDeserialize(inputStream, buffer);
    }
}
