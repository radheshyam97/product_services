package com.radhe.product.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBUtil {
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (ClassNotFoundException ex) {
			System.out.println("Exception occurd while loading the driver msg: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		System.out.println("Main START");
		Connection conn = getConnection();
		String createProdTableSQL = "CREATE TABLE product_tbl ( prod_id INT NOT NULL GENERATED ALWAYS AS IDENTITY, prod_name VARCHAR(100), price FLOAT(10,2) );";
		createTable(createProdTableSQL, conn);
		conn.commit();
		conn.close();
		System.out.println("Main END");
	}

	public static Connection getConnection() throws Exception {

		// load the driver

		Connection connection = DriverManager.getConnection("jdbc:derby:product_db;create=true");
		System.out.println("isClosed: " + connection.isClosed());
		return connection;
	}

	public static boolean createTable(String sql, Connection conn) {

		Statement stmt = null;
		boolean createdTable = true;
		try {
			// Creating the Statement object
			stmt = conn.createStatement();

			// Executing the query
		//	String query = "drop TABLE product_tbl";
			
			String query = "CREATE TABLE product_tbl ( prod_id INT NOT NULL GENERATED ALWAYS AS IDENTITY, prod_name VARCHAR(100), price FLOAT(10) )";

			stmt.execute(query);
			System.out.println("Table created");
		} catch (Exception ex) {
			System.out.println("Exception occurd while creating table " + ex.getMessage());
			ex.printStackTrace();
			createdTable = false;
		} catch (Throwable t) {

			System.out.println("Throwable occurd while creating table: " + t.getMessage());
			t.printStackTrace();
			createdTable = false;

		} finally {

			if (stmt != null) {

				try {
					stmt.close();
				} catch (Exception ex) {
					System.out.println("Exception occurd in closing the Statement: " + ex.getMessage());
					ex.printStackTrace();

				}
			}
		}

		return createdTable;

	}
}