package view;

import java.util.List;

import controller.MenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Menu;

public class UpdatePopup {
    private Stage stage;
    private Scene scene;
    private Button updateButton;
    private ComboBox<String> comboBox;
    private Label hargaLabel, stokLabel;
    private TextField hargaField, stokField;
    private VBox vBox;
    private VBox fieldBox;
    private ObservableList<Menu> menuList;

    public UpdatePopup(ObservableList<Menu> menuList) {
        this.menuList = menuList;
        stage = new Stage();
        stage.setWidth(300);
        stage.setHeight(250);
        stage.setTitle("Update Menu Item");

        init();
        setLayout();

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(vBox);
        scene = new Scene(borderPane);
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }

    private void init() {
        List<String> menuNames = MenuController.getAllName();
        comboBox = new ComboBox<>();
        comboBox.setItems(FXCollections.observableArrayList(menuNames));
        
        hargaLabel = new Label("Harga");
        hargaLabel.setFont(new Font("Inter", 12));
        hargaField = new TextField();
        hargaField.setMaxWidth(150);
        
        stokLabel = new Label("Stok");
        stokLabel.setFont(new Font("Inter", 12));
        stokField = new TextField();
        stokField.setMaxWidth(150);
        
        updateButton = new Button("Update");
        updateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String selectedString = comboBox.getValue();
                Integer menuPrice = null;
				Integer menuStock = null;
                
                if (selectedString != null) {
                    try {
                    	menuPrice = Integer.parseInt(hargaField.getText());
                    	menuStock = Integer.parseInt(stokField.getText());
                    	
                        Boolean isUpdated = MenuController.updateMenuDetail(selectedString, menuPrice, menuStock);
                        if (isUpdated) {
                            System.out.println("Update success");
                            for (Menu menu : menuList) {
                                if (menu.getNamaString().equals(selectedString)) {
                                    menu.setHargaInteger(menuPrice);
                                    menu.setStokInteger(menuStock);
                                    break;
                                }
                            }
                            menuList.setAll(MenuController.getAllMenus());
                            stage.close();
                        } else {
                            System.out.println("Update failed");
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println("Invalid number format");
                    }
                }
            }
        });
    }

    private void setLayout() {
        vBox = new VBox();
        fieldBox = new VBox();
        
        fieldBox.setSpacing(5);
        fieldBox.getChildren().addAll(hargaLabel, hargaField, stokLabel, stokField);
        
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().addAll(comboBox, fieldBox, updateButton);
    }
}