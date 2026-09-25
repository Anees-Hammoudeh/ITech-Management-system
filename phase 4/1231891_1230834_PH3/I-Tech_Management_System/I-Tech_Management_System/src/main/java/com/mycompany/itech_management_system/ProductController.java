package com.mycompany.itech_management_system;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductController implements Initializable{
    @FXML
    private TextField nameField;
    @FXML
     private TextField priceField;
    @FXML
    private Label messageLabel;
    @FXML
     private TextField idField;
    @FXML
    private TextField modelField;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, String> modelColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @Override
    public void initialize(URL url, ResourceBundle rb){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
private void doSave(){

    try {

        Connection mycon = DBconnection.getConnection();
        java.sql.Statement stmt = mycon.createStatement();
        String name = nameField.getText();
        String price = priceField.getText();
        String model = modelField.getText(); 
        //check that fields are not empty
        if (name.isEmpty() || price.isEmpty() || model.isEmpty()){
            messageLabel.setText("Please fill all fields");
            messageLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            return;
        }
        //make sure that the price is a number (can be decimal )
        if (!price.matches("\\d+(\\.\\d+)?")){ //\d+ means one or more numbers and (\\.\\d+)? means optional decimal part

            messageLabel.setText("Price must be a number");
            messageLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            return;
        }
        String sql = "insert into Product " + "(Product_Name, Model, Price, Category_ID, Brand_ID) " + "values ('" + name + "', '" + model + "', " + price + ", 1, 1)";
        stmt.executeUpdate(sql);
        messageLabel.setText("Product added successfully");
        messageLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        stmt.close();
        mycon.close();
    } catch (Exception e){
        messageLabel.setText("Error adding product");
        messageLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
    }
}
     
    @FXML
private void doShow(){

    try {
        Connection mycon = DBconnection.getConnection();
        String sql = "select Product_ID, Product_Name, Model, Price from Product";
        java.sql.Statement stmt = mycon.createStatement();
        ResultSet rs =stmt.executeQuery(sql);
        //list for table
        ObservableList<Product> list = FXCollections.observableArrayList();
        while (rs.next()) {
            list.add(new Product(rs.getInt("Product_ID"),rs.getString("Product_Name"), rs.getString("Model"),rs.getDouble("Price")));
        }
        productTable.setItems(list);     
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e){
        messageLabel.setText("Error showing products");
    }
}

@FXML
private void doUpdate(){

    try {
        Connection mycon = DBconnection.getConnection();
        java.sql.Statement stmt = mycon.createStatement();
        String id = idField.getText();
        String name = nameField.getText();
        String price = priceField.getText();
        String model = modelField.getText();       
        if (id.isEmpty() || !id.matches("\\d+")) {
            messageLabel.setText("ID must be a number");
            messageLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            return; 
        }       
        if (name.isEmpty() || price.isEmpty() || model.isEmpty()) {
            messageLabel.setText("Please fill all fields");
            messageLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            return;
        }
        //check id exists
        String checkSql = "select * from Product where Product_ID=" + id;
        ResultSet rs = stmt.executeQuery(checkSql);
        if (rs.next()) {
            String sql = "update Product set Product_Name='" + name + "', Model='" + model + "', Price=" + price + " where Product_ID=" + id;
            stmt.executeUpdate(sql);
            messageLabel.setText("Product with ID " + id + " updated successfully");
            messageLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            doShow();
        } else {
            messageLabel.setText("ID " + id + " does not exist");
            messageLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e){
        messageLabel.setText("Error");
        messageLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
    }
}

    @FXML
private void doSearch(){

    try {
        Connection mycon = DBconnection.getConnection();
        java.sql.Statement stmt = mycon.createStatement();
        String keyword = searchField.getText();
        String sql = "select Product_ID, Product_Name, Model, Price " +"from Product " + "where Product_ID like '%" + keyword + "%' " +"or Product_Name like '%" + keyword + "%'";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<Product> list = FXCollections.observableArrayList();
        while (rs.next()){
            list.add(new Product(rs.getInt("Product_ID"),rs.getString("Product_Name"),rs.getString("Model"),rs.getDouble("Price") ));
        }
        productTable.setItems(list);
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e){
        messageLabel.setText("Search Error");
    }
}

    @FXML
private void doDelete(){

    try {
        Connection mycon = DBconnection.getConnection();
        java.sql.Statement stmt = mycon.createStatement();
        String id = idField.getText();      
        if (id.isEmpty() || !id.matches("\\d+")){
            messageLabel.setText("ID must be a number");
            messageLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            return; 
        }      
        String checkSql = "select * from Product where Product_ID=" + id;
        ResultSet rs = stmt.executeQuery(checkSql);
        if(rs.next()){
        String sql = "delete from Product where Product_ID=" + id;
        stmt.executeUpdate(sql);
        messageLabel.setText("Product with ID " + id + " Deleted");
        messageLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        doShow();
        }
        else{
            messageLabel.setText("ID "+ id+" entered does not exist");
            messageLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }      
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e){
        messageLabel.setText("Error");
        messageLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
    }
}

    @FXML
private void clearFields(){
    nameField.clear();
    priceField.clear();
    idField.clear();
    modelField.clear();
    messageLabel.setText("");
}
}

