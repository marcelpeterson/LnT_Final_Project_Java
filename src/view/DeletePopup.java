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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Menu;

public class DeletePopup {
    private Stage stage;
    private Scene scene;
    private Button deleteButton;
    private ComboBox<String> comboBox;
    private VBox vBox;
    private ObservableList<Menu> menuList;

    public DeletePopup(ObservableList<Menu> menuList) {
        this.menuList = menuList;
        stage = new Stage();
        stage.setWidth(300);
        stage.setHeight(150);
        stage.setTitle("Delete Menu Item");

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

        deleteButton = new Button("Delete");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String selectedString = comboBox.getValue();
                
                if(selectedString != null) {
                    Boolean isDeleted = MenuController.deleteMenu(selectedString);
                    if (isDeleted) {
                        System.out.println("Delete success");
                        menuList.setAll(MenuController.getAllMenus());
                        stage.close();
                    } else {
                        System.out.println("Delete failed");
                    }
                }
            }
        });
    }

    private void setLayout() {
        vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().addAll(comboBox, deleteButton);
    }
}