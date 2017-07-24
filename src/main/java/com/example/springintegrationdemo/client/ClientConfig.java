package com.example.springintegrationdemo.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.Channels;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.ip.tcp.TcpReceivingChannelAdapter;
import org.springframework.integration.ip.tcp.connection.TcpNioClientConnectionFactory;
import org.springframework.integration.ip.tcp.serializer.ByteArrayLfSerializer;

import java.util.concurrent.Executors;

import static org.springframework.integration.dsl.support.Transformers.objectToString;

@Configuration
@EnableIntegration
public class ClientConfig {

    Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    public IntegrationFlow incomingChLogsFlow(final TcpReceivingChannelAdapter tcpReceivingChannelAdapter,
                                              final CommsHubLogService commsHubLogService) {

        return IntegrationFlows
            .from(tcpReceivingChannelAdapter)
//            .channel(Channels::queue)
            .transform(source -> {
                log.info("Transforming: {}", source);
                return source;
            }) // converts message's payload from byte[] to String
            .transform(objectToString()) // converts message's payload from byte[] to String
            .handle(message -> commsHubLogService.saveLogEntry((String) message.getPayload()))
            .get();
    }

    @Bean
    public TcpReceivingChannelAdapter tcpReceivingChannelAdapter(@Value("${atps.chLink.host}") String host,
                                                                 @Value("${atps.chLink.chLogs.port}") int port) {

        // Each connection factory can have only one listener (channel adapter) registered, so it's okay to
        // create it here as an 'anonomous bean' rather than 'fully blown bean'.
        TcpNioClientConnectionFactory tcpNioClientConnectionFactory = new TcpNioClientConnectionFactory(host, port);

        tcpNioClientConnectionFactory.setDeserializer(new MyByteArrayLfSerializer());

        TcpReceivingChannelAdapter tcpReceivingChannelAdapter = new TcpReceivingChannelAdapter();

        tcpReceivingChannelAdapter.setConnectionFactory(tcpNioClientConnectionFactory);

        tcpReceivingChannelAdapter.setAutoStartup(true); // seems to be causing re-establishment of the connection if lost
        tcpReceivingChannelAdapter.setClientMode(true); // was required to establish the connection for the first time

        // Default interval is one minute - retries are attempted both to re-establish previously available connection
        // and to establish it for the very first time, e.g. when client was started before the server.
         tcpReceivingChannelAdapter.setRetryInterval(10000);

        return tcpReceivingChannelAdapter;
    }

    @Bean
    public CommsHubLogService commsHubLogService() {
        return new CommsHubLogService();
    }
}
