package main;

import database.DatabaseConnection;
import database.DatabaseSingleton;
import javafx.application.Application;
import javafx.stage.Stage;
import view.HomePage;

public class Main extends Application {
	DatabaseConnection db = DatabaseSingleton.getInstance();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
		launch(args);
	}
	
	public Main() {
		db.migrateTables();
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		HomePage homePage = new HomePage(arg0);
		arg0.setScene(homePage.getScene());
		arg0.show();
	}

}
