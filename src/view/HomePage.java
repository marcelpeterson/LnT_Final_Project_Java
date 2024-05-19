package view;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Menu;
import controller.MenuController;

public class HomePage {
	protected Scene scene;
	protected Stage stage;
	protected BorderPane borderPane;
	protected Label titleLabel;
	protected TableView<Menu> tableView;
	protected TableColumn<Menu, String> kodeColumn;
	protected TableColumn<Menu, String> namaColumn;
	protected TableColumn<Menu, String> hargaColumn;
	protected TableColumn<Menu, String> stokColumn;
	protected ObservableList<Menu> menuList;
	protected VBox vBox;
	
	protected TextField kodeField;
	protected TextField namaField;
	protected TextField hargaField;
	protected TextField stokField;
	protected Button addButton;
	protected Button updateButton;
	protected Button deleteButton;
	protected HBox hBox;
	
	public HomePage(Stage stage) {
		this.stage = stage;
		stage.setTitle("View Menu");
		stage.setWidth(500);
		stage.setHeight(550);
		
		initialize();
		setLayout();
		
		this.scene = new Scene(new Group());
		((Group) scene.getRoot()).getChildren().addAll(vBox);
	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	@SuppressWarnings("unchecked")
	public void initialize() {
		List<Menu> menus = MenuController.getAllMenus();
		menuList = FXCollections.observableArrayList(menus);
		
		titleLabel = new Label("All Menus");
		titleLabel.setFont(new Font("Inter", 20));
		
		kodeColumn = new TableColumn<>("Kode");
		kodeColumn.setMinWidth(75);
		kodeColumn.setCellValueFactory(cell -> cell.getValue().kodeProperty());
		
		namaColumn = new TableColumn<>("Nama");
		namaColumn.setMinWidth(185);
		namaColumn.setCellValueFactory(cell -> cell.getValue().namaProperty());
		
		hargaColumn = new TableColumn<>("Harga");
		hargaColumn.setMinWidth(100);
		hargaColumn.setCellValueFactory(cell -> cell.getValue().hargaProperty());
		
		stokColumn = new TableColumn<>("Stok");
		stokColumn.setMinWidth(50);
		stokColumn.setCellValueFactory(cell -> cell.getValue().stokProperty());
		
		tableView = new TableView<>();
		tableView.setItems(menuList);
		tableView.getColumns().addAll(kodeColumn, namaColumn, hargaColumn, stokColumn);
		
		namaField = new TextField();
		namaField.setPromptText("Nama");
		namaField.setMaxWidth(namaColumn.getPrefWidth());
		
		hargaField = new TextField();
		hargaField.setPromptText("Harga");
		hargaField.setMaxWidth(hargaColumn.getPrefWidth());
		
		stokField = new TextField();
		stokField.setPromptText("Stok");
		stokField.setMaxWidth(stokColumn.getPrefWidth());
		
		addButton = new Button("Add");
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent e) {
				String menuCode;
				do {
					menuCode = MenuController.randomizeKode();
				} while(!MenuController.isKodeExists(menuCode));
				
				String menuName = namaField.getText();
				Integer menuPrice = null;
				Integer menuStock = null;
				
				if(menuName.isEmpty()) {
					System.out.println("Nama can't be empty");
					return;
				}
				
				try {
					menuPrice = Integer.parseInt(hargaField.getText());
				} catch (NumberFormatException n) {
					System.out.println("Invalid price format");
					return;
				}
				
				try {
					menuStock = Integer.parseInt(stokField.getText());
				} catch (NumberFormatException s) {
					System.out.println("Invalid stok format");
					return;
				}
				
				Menu menu = new Menu(menuCode, menuName, menuPrice, menuStock);
				Boolean insertBoolean = MenuController.insertMenu(menu);
				if(insertBoolean) {
					System.out.println("Menu added");
					menuList.add(menu);
				}
				
				namaField.clear();
				hargaField.clear();
				stokField.clear();
			}
		});
		
		updateButton = new Button("Update");
		updateButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				UpdatePopup updatePopup = new UpdatePopup(menuList);
				updatePopup.show();
			}
		});
		
		deleteButton = new Button("Delete");
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				DeletePopup deletePopup = new DeletePopup(menuList);
				deletePopup.show();
			}
		});
		
		hBox = new HBox();
	}
	
	public void setLayout() {
		Region region = new Region();
		HBox.setHgrow(region, Priority.ALWAYS);
		
		hBox.getChildren().addAll(namaField, hargaField, stokField, addButton, region, updateButton, deleteButton);
		hBox.setSpacing(3);
		hBox.setAlignment(Pos.CENTER_LEFT);
		
		vBox = new VBox();
		vBox.setSpacing(5);
		vBox.setPadding(new Insets(10, 20, 0, 20));
		vBox.getChildren().addAll(titleLabel, tableView, hBox);
	}
	
}
