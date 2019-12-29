package com.radhe.product.services;

import java.sql.Connection;
import java.util.List;

import com.radhe.product.dao.ProductDAO;
import com.radhe.product.model.Product;

public class ProductService {

	public int inserProduct(Product product) throws Exception {

		ProductDAO dao = getDAO();
		int insertProduct = dao.insertProduct(product);
		if (insertProduct >= 1) {
			dao.commit();
			System.out.println("product inserted successfylly");

		} else {
			System.out.println("product insertion failed");
			dao.rollback();
		}
 		dao.close();
		return insertProduct;

	}
	
	
	public int updateProduct(Product product) throws Exception {

		ProductDAO dao = getDAO();
		int updateProduct = dao.updateProduct(product);
		if (updateProduct >= 1) {
			dao.commit();
			System.out.println("product updated successfylly");

		} else {
			System.out.println("product updation failed");
			dao.rollback();
		}
		dao.commit();
		dao.close();
		return updateProduct;

	}
	
	public int deleteProduct(Product product) throws Exception {
		
		ProductDAO dao = getDAO();
		int updateProduct = dao.deleteProduct(product);
		if (updateProduct >= 1) {
			dao.commit();
			System.out.println("product deleted successfylly");

		} else {
			System.out.println("product deletion failed");
			dao.rollback();
		}
		dao.commit();
		dao.close();
		return updateProduct;

	}

	public List<Product> getAllProduct() throws Exception {

		ProductDAO dao = getDAO();
		List<Product> productList = dao.getAllProduct();
		dao.close();
		return productList;
	}
	public Product getProduct(long prodId) throws Exception {

		ProductDAO dao = getDAO();
		Product product= dao.getProduct(prodId);
		dao.close();
		return product;
	}

	private ProductDAO getDAO() throws Exception {

		Connection conn = com.radhe.product.util.DBUtil.getConnection();
		return new ProductDAO(conn);
	}

}
