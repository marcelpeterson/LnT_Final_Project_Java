package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConnection {
	public Connection connection;
	public Statement statement;
	// singleton
	public DatabaseConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pudding_db", "root", "");
			statement = connection.createStatement();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createMenusTable() {
		String query = "CREATE TABLE IF NOT EXISTS Menus("
				+ "Kode CHAR(6) NOT NULL,"
				+ "Nama VARCHAR(50) NOT NULL,"
				+ "Harga INT NOT NULL,"
				+ "Stok INT NOT NULL"
				+ ")";
		try {
			exec(query);			
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}
	public void migrateTables() {
		createMenusTable();
	}
	 
	public void exec(String query) {
		try {
			statement.execute(query);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}