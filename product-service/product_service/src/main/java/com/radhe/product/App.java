package com.radhe.product;

import com.radhe.product.logger.AppLogger;

import java.io.IOException;

import com.radhe.product.controller.ControllerRepository;
import com.radhe.product.controller.bizz.ProductController;
import com.radhe.product.http.server.HttpServerApp;

/**
 * Hello world!
 *
 */
public class App {

	private static AppLogger log = AppLogger.getLogger(App.class);

	public static void main(String[] args) throws IOException, InterruptedException {
		log.debug("### START APP ###");

		registerController();

		startHTTPServer();

		log.debug("### END APP ###");

	}

	private static void startHTTPServer() throws IOException, InterruptedException {

		log.info("### START HTTP SERVER START ###");

		HttpServerApp httpServerApp = new HttpServerApp();
		httpServerApp.startServer();
		log.info("### START HTTP SERVER END ###");

	}

	private static void registerController() {
		log.debug("### REGISTER CONTROLLERS START ###");
		ControllerRepository controllerRepository = new ControllerRepository();
		controllerRepository.registerController("product", new ProductController());
		ControllerRepository.logControlleres();
		log.debug("### REGISTER CONTROLLERS END ###");

	}
}
