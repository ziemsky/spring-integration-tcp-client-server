package com.example.springintegrationdemo.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ip.tcp.TcpSendingMessageHandler;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.springframework.integration.dsl.core.Pollers.fixedDelay;

@Configuration
@EnableIntegration
public class ServerConfig {

    @Bean
    public IntegrationFlow integrationFlow(final TcpSendingMessageHandler messageHandler,
                                           final MessageSource<String> messageSource,
                                           final MessageConnectionIdHeaderPopulator messageConnectionIdHeaderPopulator,
                                           final TcpConnectionsTracker tcpConnectionsTracker) {

        return IntegrationFlows
            .from(messageSource, endpointSpec -> endpointSpec.poller(fixedDelay(2, SECONDS)))
            .filter(message -> tcpConnectionsTracker.clientConnected())
            .transform(messageConnectionIdHeaderPopulator)
            .transform(message -> {System.out.println("SENDING: " + message); return message;})
            .handle(messageHandler)
            .get();
    }

    @Bean
    public TcpSendingMessageHandler tcpSendingMessageHandler(final TcpConnectionsTracker tcpConnectionsTracker) {

        TcpSendingMessageHandler tcpSendingMessageHandler = new TcpSendingMessageHandler();
        tcpSendingMessageHandler.setConnectionFactory(tcpConnectionsTracker);
        tcpSendingMessageHandler.setClientMode(false);
        tcpSendingMessageHandler.setRetryInterval(500);
        tcpSendingMessageHandler.setLoggingEnabled(true);

        return tcpSendingMessageHandler;
    }

    @Bean
    TcpConnectionsTracker tcpConnectionsTracker(@Value("${atps.chLink.chLogs.port}") int port) {

        TcpConnectionsTracker tcpConnectionsTracker = new TcpConnectionsTracker(port);
        tcpConnectionsTracker.registerListener(message -> {System.out.println("MESSAGE: " + message); return true;});

        return tcpConnectionsTracker;
    }

    @Bean
    MessageConnectionIdHeaderPopulator messageConnectionIdHeaderPopulator(final TcpConnectionsTracker tcpConnectionsTracker) {
        return new MessageConnectionIdHeaderPopulator(tcpConnectionsTracker);
    }

    @Bean
    public MessageSource<String> logMessageSource() {

        return new LogContentBroadcaster();
    }

}
