package com.example.springintegrationdemo.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.ip.tcp.serializer.ByteArrayLfSerializer;
import org.springframework.integration.ip.tcp.serializer.ByteArraySingleTerminatorSerializer;

import java.io.IOException;
import java.io.InputStream;

public class MyByteArrayLfSerializer extends ByteArraySingleTerminatorSerializer {

    Logger log = LoggerFactory.getLogger(getClass());

    public MyByteArrayLfSerializer() {
        super((byte) '|');
    }

    @Override protected byte[] doDeserialize(final InputStream inputStream, final byte[] buffer) throws IOException {

        log.info("deserialising");

        return super.doDeserialize(inputStream, buffer);
    }
}
