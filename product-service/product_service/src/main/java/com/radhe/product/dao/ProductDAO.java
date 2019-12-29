package com.radhe.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.radhe.product.model.Product;

public class ProductDAO {
	private Connection conn;

	public ProductDAO(Connection conn) {
		this.conn = conn;
	}

	public int insertProduct(Product product) {

		int insertCount = 0;
		PreparedStatement ps = null;

		try {
			String sql = "INSERT INTO product_tbl(prod_name,price) VALUES (?,?)";
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, product.getProdName());
			ps.setDouble(2, product.getProdPrice());
			insertCount = ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				long prodId = rs.getInt(1);
				product.setProdId(prodId);
				System.out.println("generated prod id: " + prodId);
			}
			System.out.println("insertCount: " + insertCount);

		} catch (Exception ex) {
			System.out
					.println("Exception occured while inserting the product details: " + ex.getMessage() + "ps: " + ps);
			ex.printStackTrace();

		} finally {
			close(ps);
		}

		return insertCount;
	}

	public int updateProduct(Product product) {

		int updateCount = 0;
		PreparedStatement ps = null;

		try {
			String sql = "UPDATE product_tbl SET prod_name=?, price=? WHERE prod_id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, product.getProdName());
			ps.setDouble(2, product.getProdPrice());
			ps.setLong(3, product.getProdId());

			updateCount = ps.executeUpdate();
			System.out.println("updateCount: " + updateCount);

		} catch (Exception ex) {
			System.out
					.println("Exception occured while updating the product details: " + ex.getMessage() + "ps: " + ps);
			ex.printStackTrace();

		} finally {

			close(ps);
		}

		return updateCount;
	}

	public int deleteProduct(Product product) {

		int deleteCount = 0;
		PreparedStatement ps = null;

		try {
			String sql = "DELETE FROM product_tbl WHERE prod_id=?";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, product.getProdId());

			deleteCount = ps.executeUpdate();
			System.out.println("deleteCount: " + deleteCount);

		} catch (Exception ex) {
			System.out
					.println("Exception occured while updating the product details: " + ex.getMessage() + "ps: " + ps);
			ex.printStackTrace();

		} finally {

			close(ps);
		}

		return deleteCount;
	}

	public List<Product> getAllProduct() {

		List<Product> prodList = new ArrayList<Product>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT prod_id, prod_name, price FROM product_tbl";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			Product product = null;
			while (rs.next()) {

				product = new Product();
				prodList.add(product);

				product.setProdId(rs.getLong(1));
				product.setProdName(rs.getString(2));
				product.setProdPrice(rs.getDouble(3));
			}

		} catch (Exception ex) {
			System.out
					.println("Exception occured while fetching the product details: " + ex.getMessage() + "ps: " + ps);
			ex.printStackTrace();

		} finally {

			close(rs);
			close(ps);
		}
		return prodList;

	}

	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close(Statement st) {
		try {
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void commit() {

		System.out.println("Commit START");
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Commit END");
	}

	public void rollback() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Product getProduct(long prodId) {

		PreparedStatement ps = null;
		Product product = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT prod_id, prod_name, price FROM product_tbl WHERE prod_id=?";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, prodId);
			rs = ps.executeQuery();
			if (rs.next()) {

				product = new Product();

				product.setProdId(rs.getLong(1));
				product.setProdName(rs.getString(2));
				product.setProdPrice(rs.getDouble(3));
			}

		} catch (Exception ex) {
			System.out
					.println("Exception occured while fetching the product details: " + ex.getMessage() + "ps: " + ps);
			ex.printStackTrace();

		} finally {

			close(rs);
			close(ps);
		}
		return product;

	}
}
