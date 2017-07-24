package com.example.springintegrationdemo.server;

import org.springframework.integration.ip.tcp.connection.TcpConnection;
import org.springframework.integration.ip.tcp.connection.TcpNioServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpSender;

import javax.management.remote.JMXConnectionNotification;

public class TcpConnectionsTracker extends TcpNioServerConnectionFactory implements TcpSender {

    private TcpSender sender;
    private TcpConnection clientConnection;

    public TcpConnectionsTracker(final int port) {
        super(port);
    }

    @Override public void registerSender(final TcpSender sender) {
        super.registerSender(this);
        this.sender = sender;
    }

    @Override public void addNewConnection(final TcpConnection connection) {
        sender.addNewConnection(connection);
        clientConnection = connection;
    }

    @Override public void removeDeadConnection(final TcpConnection connection) {
        sender.removeDeadConnection(connection);
        clientConnection = null;
    }

    public boolean clientConnected() {
        return clientConnection != null;
    }

    public TcpConnection getClientConnection() {
        return clientConnection;
    }
}
