package com.example.springintegrationdemo.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.ip.tcp.TcpReceivingChannelAdapter;

import javax.net.SocketFactory;
import java.net.Socket;

@SpringBootApplication
public class ClientApplication implements CommandLineRunner {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ClientApplication.class, args);

//		context.getBean(TcpReceivingChannelAdapter.class);
	}


	@Override public void run(final String... args) throws Exception {

//		Socket socket = SocketFactory.getDefault().createSocket("localhost", 3333);
//
//		StringBuilder sb = new StringBuilder();
//
//		do {
//			if (socket.getInputStream().available() == 0) {
//				System.out.println("RECEIVED: " + sb.toString());
//				sb = new StringBuilder();
//			}
//
//
//			int read = socket.getInputStream().read();
//
//
//			sb.append(read);
//
//		} while (true);

	}
}
