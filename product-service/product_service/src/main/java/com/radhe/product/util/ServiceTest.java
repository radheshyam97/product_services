package com.radhe.product.util;

import java.util.List;

import com.radhe.product.model.Product;
import com.radhe.product.services.ProductService;

public class ServiceTest {

	public static void main(String[] args) throws Exception {

		System.out.println("START");
		// insetProductTest();
		printProductTest();
		System.out.println("END");

	}

	private static void insetProductTest() throws Exception {
		ProductService productService = new ProductService();
		Product product = new Product();
		product.setProdName("Bullet STD 350");
		product.setProdPrice(150000);

		productService.inserProduct(product);
	}

	private static void printProductTest() throws Exception {
		ProductService productService = new ProductService();

		List<Product> allProduct = productService.getAllProduct();
		System.out.println("productList: " + allProduct);

	}

	private static void updateProductTest() throws Exception {
		ProductService productService = new ProductService();
		Product product = new Product();
		product.setProdId(1);
		product.setProdName("Bullet STD 350 New");
		product.setProdPrice(150000);

		productService.inserProduct(product);
	}
}
