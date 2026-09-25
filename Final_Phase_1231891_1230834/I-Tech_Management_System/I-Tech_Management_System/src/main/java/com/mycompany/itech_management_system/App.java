package com.mycompany.itech_management_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage; 

public class App extends Application {
 
    @Override 
    public void start(Stage stage) throws Exception {

        FXMLLoader loader =new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        Scene scene = new Scene(loader.load(), 1400, 840);
        stage.setMaximized(true);
        stage.setTitle(" I - Tech_Managment_System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
