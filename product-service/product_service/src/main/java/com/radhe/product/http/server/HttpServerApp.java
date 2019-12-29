package com.radhe.product.http.server;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import org.apache.http.ConnectionClosedException;
import org.apache.http.ExceptionLogger;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.protocol.ImmutableHttpProcessor;

 
public class HttpServerApp {

	public static void main(String[] args) throws Exception {

		System.out.println("START");
		
		HttpServerApp app=new HttpServerApp();
		app.startServer();
 
		System.out.println("END");

	}

	public   void startServer() throws IOException, InterruptedException {
		HttpRequestHandler requestHandler = new AppControllerHttpRequestHandler();
		HttpRequestInterceptor httpInterCeptor = new DefaultHttpRequestInterceptor();
		ImmutableHttpProcessor httpProcessor = new ImmutableHttpProcessor(httpInterCeptor);
		SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(15000).setTcpNoDelay(true).build();
		final HttpServer server = ServerBootstrap.bootstrap().setListenerPort(1122).setHttpProcessor(httpProcessor)
				.setSocketConfig(socketConfig).setExceptionLogger(new StdErrorExceptionLogger())
				.registerHandler("*", requestHandler).create();
		server.start();
		configureShutDownHook(server);

		server.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
	}

	private   void configureShutDownHook(final HttpServer server) {
		System.out.println("before hook");
		Runtime.getRuntime().addShutdownHook(new Thread() {

			{
				System.out.println("addShutdownHook");
			}

			@Override
			public void run() {
				System.out.println("run: shutdonw()");
				server.shutdown(5, TimeUnit.SECONDS);
			}
		});
	}

	  class StdErrorExceptionLogger implements ExceptionLogger {

		// @Override
		public void log(final Exception ex) {
			if (ex instanceof SocketTimeoutException) {
				System.err.println("Connection timed out");
			} else if (ex instanceof ConnectionClosedException) {
				System.err.println(ex.getMessage());
			} else {
				ex.printStackTrace();
			}
		}

	}

}
