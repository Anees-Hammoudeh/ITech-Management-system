package com.mycompany.itech_management_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage; 

public class App extends Application {
 
    @Override 
    public void start(Stage stage) throws Exception {

        FXMLLoader loader =new FXMLLoader(getClass().getResource("/fxml/product.fxml"));
        Scene scene = new Scene(loader.load(), 1100, 700);
        stage.setTitle("Product Form");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
