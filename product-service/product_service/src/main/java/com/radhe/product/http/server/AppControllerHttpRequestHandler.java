package com.radhe.product.http.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.RequestLine;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import com.radhe.product.controller.Controller;
import com.radhe.product.controller.ControllerRepository;
import com.radhe.product.logger.AppLogger;
import com.radhe.product.model.ResponseVO;

public class AppControllerHttpRequestHandler implements HttpRequestHandler {

	AppLogger log = AppLogger.getLogger(AppControllerHttpRequestHandler.class);
	PrintStream out = System.out;

	@Override
	public void handle(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext context) throws IOException {
		System.out.println("In EmployeeHttpRequestHandler:handle()");
		RequestLine requestLine = httpRequest.getRequestLine();
		out.print("requestLine: " + requestLine);
		out.print("ProtocolVersion: " + httpRequest.getProtocolVersion());
		Header[] allHeaders = httpRequest.getAllHeaders();
		for (Header header : allHeaders) {
			System.out.println(header.getName() + " : " + header.getValue());
		}
//		out.print("httpRequest: " + httpRequest.getClass().getCanonicalName());

		//BasicHttpEntityEnclosingRequest newReq = (BasicHttpEntityEnclosingRequest) httpRequest;
	//	HttpEntity entity = newReq.getEntity();
	//	InputStream inputStream = entity.getContent();
		//String content = printContent(inputStream);
		//log.debug("$$$"+content);

	log.debug("");
		//
		ControllerRepository cr = new ControllerRepository();

		log.info("URI: " + requestLine.getUri());

		String uriKey = requestLine.getUri().split("/")[1];
		log.info("controller search uriKey: " + uriKey);

		if (uriKey == null || uriKey.length() == 0) {
			httpResponse.setStatusCode(HttpStatus.SC_NOT_FOUND);
			httpResponse.setEntity(new StringEntity("Invalid Request URI", ContentType.TEXT_PLAIN));

		} else {
			Controller controller = cr.getController(uriKey);

			log.info("controller class name: " + controller.getClass().getCanonicalName());

			ResponseVO controllerResp = controller.processRequest(httpRequest, httpResponse, context);
			httpResponse.setStatusCode(HttpStatus.SC_OK);
			httpResponse.setEntity(new StringEntity(controllerResp.getResponseMsg(), ContentType.TEXT_PLAIN));
		}
	}

	private String printContent(InputStream inputStream) throws IOException {

		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		String data = null;

		while ((data = br.readLine()) != null) {
			System.out.print(data);
			sb.append(data);
		}

		return sb.toString();
	}

	public void print(Object obj) {
		System.out.println(obj);
		System.out.println("\n");
	}
}
