package controller;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import database.DatabaseConnection;
import database.DatabaseSingleton;
import model.Menu;

public class MenuController {
	protected static DatabaseConnection db = DatabaseSingleton.getInstance();
	
	public static Boolean insertMenu(Menu menu) {
		String queryString = "INSERT INTO Menus(Kode, Nama, Harga, Stok) "
				+ "VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement preparedStatement = db.connection.prepareStatement(queryString);
			preparedStatement.setString(1, menu.getKodeString());
			preparedStatement.setString(2, menu.getNamaString());
			preparedStatement.setInt(3, menu.getHargaInteger());
			preparedStatement.setInt(4, menu.getStokInteger());
			
			Integer rowAffected = preparedStatement.executeUpdate();
			System.out.println("Rows affected from insert menu: " + rowAffected);
			return rowAffected > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static List<Menu> getAllMenus() {
		String queryString = "SELECT * FROM Menus";
		List<Menu> menus = new ArrayList<Menu>();
		
		try {
			PreparedStatement preparedStatement = db.connection.prepareStatement(queryString);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				String kodeString = resultSet.getString("Kode");
				String namaString = resultSet.getString("Nama");
				String hargaString = resultSet.getString("Harga");
				String stokString = resultSet.getString("Stok");
				Menu menu = new Menu(kodeString, namaString, Integer.parseInt(hargaString), Integer.parseInt(stokString));
				menus.add(menu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return menus;
	}
	
	public static List<String> getAllName(){
		String queryString = "SELECT Nama FROM Menus";
		List<String> menuList = new ArrayList<String>();
		try {
			PreparedStatement preparedStatement = db.connection.prepareStatement(queryString);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				String namaString = resultSet.getString("Nama");
				menuList.add(namaString);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return menuList;
	}
	
	public static Boolean deleteMenu(String namaString) {
		if(isMenuExists(namaString)) {
			String queryString = "DELETE FROM Menus WHERE Nama = ?";
			try {
				PreparedStatement preparedStatement = db.connection.prepareStatement(queryString);
				preparedStatement.setString(1, namaString);
				
				int rowAffected = preparedStatement.executeUpdate();
				return rowAffected > 0;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static Boolean updateMenuDetail(String namaString, Integer hargaInteger, Integer stokInteger) {
		if(isMenuExists(namaString)) {
			String queryString = "UPDATE Menus SET Harga = ?, Stok = ? WHERE Nama = ?";
			try {
				PreparedStatement preparedStatement = db.connection.prepareStatement(queryString);
				preparedStatement.setInt(1, hargaInteger);
				preparedStatement.setInt(2, stokInteger);
				preparedStatement.setString(3, namaString);
				
				int rowAffected = preparedStatement.executeUpdate();
				return rowAffected > 0;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static Boolean isMenuExists(String namaString) {
		String queryString = "SELECT COUNT(*) FROM Menus WHERE Nama = ?";
		try {
			PreparedStatement preparedStatement = db.connection.prepareStatement(queryString);
			preparedStatement.setString(1, namaString);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				int count = resultSet.getInt(1);
				return count > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static Boolean isKodeExists(String kodeString) {
		String queryString = "SELECT COUNT(*) FROM Menus WHERE Kode = ?";
		try {
			PreparedStatement preparedStatement = db.connection.prepareStatement(queryString);
			preparedStatement.setString(1, kodeString);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				int count = resultSet.getInt(1);
				return count > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static String randomizeKode() {
		String numString = "1234567890";
		Random random = new Random();
		
		StringBuilder stringBuilder = new StringBuilder(3);
		
		for(int i = 0; i < 3; i++) {
			int index = random.nextInt(numString.length());
			stringBuilder.append(numString.charAt(index));
		}
		
		return ("PD-" + stringBuilder.toString());
	}
}