package database;

public class DatabaseSingleton {
	private static DatabaseConnection db = null;
	
	public static DatabaseConnection getInstance() {
		if(db == null) {
			db = new DatabaseConnection();
		}
		return db;
	}
}