package com.example.springintegrationdemo.server;

import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.support.MutableMessageHeaders;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;

public class MessageConnectionIdHeaderPopulator implements GenericTransformer<Message, Message> {

    private TcpConnectionsTracker tcpConnectionsTracker;

    public MessageConnectionIdHeaderPopulator(
        final TcpConnectionsTracker tcpConnectionsTracker) {
        this.tcpConnectionsTracker = tcpConnectionsTracker;
    }

    @Override
    public Message transform(final Message source) {

        MessageHeaders messageHeaders = new MutableMessageHeaders(source.getHeaders());
        messageHeaders.put(IpHeaders.CONNECTION_ID, tcpConnectionsTracker.getClientConnection().getConnectionId());

        return new GenericMessage(source.getPayload(), messageHeaders);
    }
}
