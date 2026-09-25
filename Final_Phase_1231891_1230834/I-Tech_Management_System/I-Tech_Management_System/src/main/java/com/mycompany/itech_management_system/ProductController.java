package com.mycompany.itech_management_system;

//imports for the initialize method parameters ( for javafx )
 import java.net.URL;
import java.util.ResourceBundle;
//imports for database connection and queries
 import java.sql.Connection;
 import java.sql.ResultSet;
import java.sql.Statement;
//imports for the table data list
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//imports for connecting controller to fxml and go between pages
  import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
//elements needed 
import javafx.scene.control.ComboBox;
 import javafx.scene.control.*;
//imports for switching between pages
 import javafx.scene.Parent;
  import javafx.scene.Scene;
 import javafx.stage.Stage;
import javafx.event.ActionEvent;



public class ProductController implements Initializable {
    //input fields for product data (linked to fxml by name)
@FXML TextField idField;//product id  for update and delete
@FXML TextField nameField;  //product name
@FXML TextField modelField; //product model
@FXML TextField priceField; //product price
@FXML TextField categoryIdField; //category foreign key
@FXML TextField brandIdField;//brand foreign key
@FXML TextField searchField; //search input for product table
    
    //fields for the category and brand section
@FXML TextField categoryNameField; //category name
@FXML TextField brandNameField;   //brand name
@FXML TextField brandWebsiteField;  //brand website 
@FXML TextField supplierSearchField; //search input for supplier table
  @FXML TextField categoryUnitSizeField; //space size for one unit in this category
    //labels to show messages to the user
@FXML Label messageLabel; // messages for add/update/delete product
 @FXML Label catBrandMessageLabel;// messages for category and brand 
@FXML Label supplierMessageLabel;//messages for supplier 
@FXML Label searchMessageLabel; //search result status
@FXML Label productCountLabel; //how many products are displayed
    //text areas to display categories and  brands as a list
 @FXML TextArea categoryInfoArea;
 @FXML TextArea brandInfoArea;
    ///main product table,each row is a Product 
 @FXML TableView<Product> productTable;
@FXML TableColumn<Product, Integer> idColumn;    //for id
@FXML TableColumn<Product, String> nameColumn;   //for name
@FXML TableColumn<Product, String> modelColumn;
@FXML TableColumn<Product, Double> priceColumn;  //for price
@FXML TableColumn<Product, String> categoryColumn; //category name from join
@FXML TableColumn<Product, String> brandColumn;    //brand name from join

    ///supplier table,each row is a ProductSupplier 
 @FXML TableView<ProductSupplier> supplierProductTable;
@FXML TableColumn<ProductSupplier, String> spProductNameColumn;
@FXML TableColumn<ProductSupplier, String> spSupplierNameColumn;
@FXML TableColumn<ProductSupplier, Double> spPriceColumn; //this is supply price not selling price

   //ComboBox to choose search type(Product ID/Name/Model/Price)
@FXML ComboBox<String> searchTypeCombo;
 // product info section
@FXML Label p_infoMessageLabel;
@FXML TextArea p_infoArea;

@Override
public void initialize(URL url, ResourceBundle rb) {

    //link each product table column to its getter in the Product class
  idColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("id"));
  nameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("name"));
   modelColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("model"));
 priceColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("price"));
   categoryColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("categoryName"));
    brandColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("brandName"));
    //same thing for the supplier table columns
   spProductNameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("productName"));
  spSupplierNameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("supplierName"));
    spPriceColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("price"));
    //the search options for the combobox one by one
    searchTypeCombo.getItems().add("Product ID");
  searchTypeCombo.getItems().add("Product Name");
   searchTypeCombo.getItems().add("Model");
   searchTypeCombo.getItems().add("Price");
   searchTypeCombo.setValue("Product Name"); //default selection when  open the page for the first time

    //load data into both tables so they are not empty when page opens
    doShow();
    showProductSuppliers();
}

    //for the Add button
@FXML
void doSave(){

    //get the text from each input field
    String name = nameField.getText();
    String model = modelField.getText();
    String price = priceField.getText();
    String catId = categoryIdField.getText();
    String brandId = brandIdField.getText();
    //check each field separately to know which one is empty
    //return if its empty
    if (name.isEmpty()){
        messageLabel.setText("Please enter product name");
        return;
    }
    if (model.isEmpty()){
        messageLabel.setText("Please enter model");
        return;
    }
    if (price.isEmpty()){
        messageLabel.setText("Please enter price");
        return;
    }
    if (catId.isEmpty()){
        messageLabel.setText("Please enter category ID");
        return;
    }
    if (brandId.isEmpty()){
        messageLabel.setText("Please enter brand ID");
        return;
    }
    
    double priceValue;
    int catValue;
    int brandValue;
    //convert each number separately to show which one is wrong
    try {
        priceValue = Double.parseDouble(price); //string to double
    } catch (NumberFormatException e){
        messageLabel.setText("Price must be a number");
        return;
    }
    try {
        catValue = Integer.parseInt(catId); //string to int
    } catch (NumberFormatException e){
        messageLabel.setText("Category ID must be a number");
        return;
    }
    try {
        brandValue = Integer.parseInt(brandId);
    } catch (NumberFormatException e){
        messageLabel.setText("Brand ID must be a number");
        return;
    }
    //price cant be negative, doesnt make sense for a product to cost less than 0
    if (priceValue < 0) {
        messageLabel.setText("Price cannot be negative");
        return;
    }
    //ids start from 1 in the database so 0 or negative ids are not accpeted
    if (catValue <= 0) {
        messageLabel.setText("Category ID must be a positive number");
        return;
    }
    if (brandValue <= 0) {
        messageLabel.setText("Brand ID must be a positive number");
        return;
    }
    //insert the new product into the database
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();

        //build the sql query as a string
        String sql = "insert into Product(Product_Name, Model, Price, Category_ID, Brand_ID) values('"+ name + "','" + model + "'," + priceValue + "," + catValue + "," + brandValue + ")";
        stmt.executeUpdate(sql); //executeUpdate for insert/update/delete 
                                  //executeQuery for select
        messageLabel.setText("Product add is done");
        //close connection after finish
        stmt.close();
        mycon.close();

        clearFields();
        doShow(); //refresh the table to show the new product  added 

    } catch (Exception e){
        //check null first to avoid another error if message is empty
        if (e.getMessage() != null && e.getMessage().contains("Category_ID")){
            messageLabel.setText("Category ID does not exist in database");
        } else if (e.getMessage() != null && e.getMessage().contains("Brand_ID")){
            messageLabel.setText("Brand ID does not exist in database");
        } else{
            messageLabel.setText("Error: could not add product");
        }
    }
}
//to load all products from database and show them in the table
@FXML
void doShow(){

    try {
        Connection mycon = DBconnection.getConnection(); 
        Statement stmt = mycon.createStatement();
        String sql = "select p.Product_ID, p.Product_Name, p.Model, p.Price, c.Category_Name, b.Brand_Name "+ "from Product p, Category c, Brand b "+ "where p.Category_ID = c.Category_ID and p.Brand_ID = b.Brand_ID";
        //executeQuery for select because it returns data(ResultSet)
        ResultSet rs = stmt.executeQuery(sql);
        //ObservableList is required for TableView,it updates the table automatically when changed
        ObservableList<Product> list = FXCollections.observableArrayList();
        //rs.next() moves to the next row and returns false when no more rows are there
        while (rs.next()){
            //read each column by its name and correct type
            int pid = rs.getInt("Product_ID");
            String pname = rs.getString("Product_Name");
            String pmodel = rs.getString("Model");
            double pprice = rs.getDouble("Price");
            String pcat = rs.getString("Category_Name");   //from category table 
            String pbrand = rs.getString("Brand_Name");    //from brand table 
            //create a Product for this row and add it to the list
            Product p = new Product(pid, pname, pmodel, pprice, pcat, pbrand);
            list.add(p);
        }
        //put the full list in the table
        productTable.setItems(list);
        productCountLabel.setText("Total Products: " + list.size()); //show count
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e){
        messageLabel.setText("Could not load products");
    }
}

   //Update button
@FXML
void doUpdate() {

    //get all field values including id
    String id = idField.getText();
    String name = nameField.getText();
    String model = modelField.getText();
    String price = priceField.getText();
    String catId = categoryIdField.getText();
    String brandId = brandIdField.getText();

    //check all fields assumed that all of them are required for update
    if (id.isEmpty() || name.isEmpty() || model.isEmpty() || price.isEmpty() || catId.isEmpty() || brandId.isEmpty()){
        messageLabel.setText("Fill all fields to update");
        return;
    }

    try {
        //convert all number fields
        int pid = Integer.parseInt(id);
        double priceVal = Double.parseDouble(price);
        int catVal = Integer.parseInt(catId);
        int brandVal = Integer.parseInt(brandId);
        // negative/positive checks
        if (pid <= 0) {
            messageLabel.setText("Product ID must be a positive number");
            return;
        }
        if (priceVal < 0) {
            messageLabel.setText("Price cannot be negative");
            return;
        }
        if (catVal <= 0) {
            messageLabel.setText("Category ID must be a positive number");
            return;
        }
        if (brandVal <= 0) {
            messageLabel.setText("Brand ID must be a positive number");
            return;
        }
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
       
        String sql = "update Product set "+ "Product_Name='" + name + "', "+ "Model='" + model + "', " + "Price=" + priceVal + ", "+ "Category_ID=" + catVal + ", "+ "Brand_ID=" + brandVal+ " where Product_ID=" + pid;
        //executeUpdate returns number of rows affected
        int rows = stmt.executeUpdate(sql);
        stmt.close();
        mycon.close();
        //if rows is 0 means no product have this id
        if (rows > 0) {
            messageLabel.setText("Product update is done");
        } else {
            messageLabel.setText("No product found with this ID");
        }

        clearFields();
        doShow(); 
    } catch (NumberFormatException e){
        // error massege
        messageLabel.setText("ID, Price, Category and Brand must all be numbers");
    } catch (Exception e){
        //check foreign key errors 
        if (e.getMessage() != null && e.getMessage().contains("Category_ID")) {
            messageLabel.setText("Category ID not found");
        } else if (e.getMessage() != null && e.getMessage().contains("Brand_ID")) {
            messageLabel.setText("Brand ID not found");
        } else {
            messageLabel.setText("Update failed");
        }
    }
}
@FXML
void doDelete() {

    String id = idField.getText();
    if (id.isEmpty()){
        messageLabel.setText("Enter the product ID to delete");
        return;
    }
    int pid;
    try {
        pid = Integer.parseInt(id);
    } catch (NumberFormatException e){
        messageLabel.setText("Product ID must be a number");
        return;
    }
    //negative or 0 ids dont exist in the database so no point 
    if (pid <= 0) {
        messageLabel.setText("Product ID must be a positive number");
        return;
    }
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "delete from Product where Product_ID=" + pid;
        int rows = stmt.executeUpdate(sql); //returns number of rows deleted
        stmt.close();
        mycon.close();
        if (rows > 0) {
            messageLabel.setText("Product delete is done");
        } else {
            messageLabel.setText("Product ID not found");
        }
        clearFields();
        doShow();
    } catch (Exception e){
       //cant delete item if its related into another table
        if (e.getMessage() != null && (e.getMessage().contains("foreign key") || e.getMessage().contains("constraint"))){
            messageLabel.setText("Cannot delete, product is used in sales or purchases");
        } else{
            messageLabel.setText("Delete failed");
        }
    }
}

@FXML
void doSearch(){

    searchMessageLabel.setText(""); //clear old message before search
    String searchText = searchField.getText();
    if (searchText.isEmpty()){
        searchMessageLabel.setText("Please enter a search value");
        return;
    }
    //get the selected search type from the combobox
    String selectedType = searchTypeCombo.getValue();
    //only validate as number if the search type needs a number
    if (selectedType.equals("Product ID") || selectedType.equals("Price")){
        double searchValue;
        try{
            searchValue = Double.parseDouble(searchText); //for both int and double
        } catch (NumberFormatException e){
            searchMessageLabel.setText("Please enter a valid number");
            return;
        }
        //product id and price cant be negative in the database
        if (searchValue < 0){
            searchMessageLabel.setText("Value cannot be negative");
            return;
        }
    }
    try{
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        //start with empty sql then fill it based on selected type
        String sql = "";
        if (selectedType.equals("Product ID")){
            //exact match for id
            sql = "select p.Product_ID, p.Product_Name, p.Model, p.Price, c.Category_Name, b.Brand_Name "+ "from Product p, Category c, Brand b "+ "where p.Category_ID = c.Category_ID and p.Brand_ID = b.Brand_ID "+ "and p.Product_ID = " + searchText;
        } else if (selectedType.equals("Product Name")){
            //like with % on both sides to find partial matches
            sql = "select p.Product_ID, p.Product_Name, p.Model, p.Price, c.Category_Name, b.Brand_Name "+ "from Product p, Category c, Brand b "+ "where p.Category_ID = c.Category_ID and p.Brand_ID = b.Brand_ID " + "and p.Product_Name like '%" + searchText + "%'";
        } else if (selectedType.equals("Model")){
            //same as name but on model 
            sql = "select p.Product_ID, p.Product_Name, p.Model, p.Price, c.Category_Name, b.Brand_Name "+ "from Product p, Category c, Brand b "+ "where p.Category_ID = c.Category_ID and p.Brand_ID = b.Brand_ID "+ "and p.Model like '%" + searchText + "%'";
        } else if (selectedType.equals("Price")){
            //exact match for price
            sql = "select p.Product_ID, p.Product_Name, p.Model, p.Price, c.Category_Name, b.Brand_Name "+ "from Product p, Category c, Brand b "+ "where p.Category_ID = c.Category_ID and p.Brand_ID = b.Brand_ID "+ "and p.Price = " + searchText;
        }
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<Product> list = FXCollections.observableArrayList();

       
        while (rs.next()) {
            int pid = rs.getInt("Product_ID");
            String pname = rs.getString("Product_Name");
            String pmodel = rs.getString("Model");
            double pprice = rs.getDouble("Price");
            String pcat = rs.getString("Category_Name");
            String pbrand = rs.getString("Brand_Name");
            Product p = new Product(pid, pname, pmodel, pprice, pcat, pbrand);
            list.add(p);
        }
        productTable.setItems(list);
        productCountLabel.setText("Found: " + list.size() + " products");
        rs.close();
        stmt.close();
        mycon.close();
        //check results after closing connection
        if (list.size() == 0){
            searchMessageLabel.setText("No results found");
        } else {
            searchMessageLabel.setText("Search is done");
        }
    } catch (Exception e){
        searchMessageLabel.setText("Search failed");
    }
}

@FXML
void addCategory() {

    String catName = categoryNameField.getText();
    String unitSizeText = categoryUnitSizeField.getText();
    if (catName.isEmpty()) {
        catBrandMessageLabel.setText("Enter category name first");
        return;
    }
    if (unitSizeText.isEmpty()) {
        catBrandMessageLabel.setText("Enter unit size first");
        return;
    }
    //unit size has to be a whole number, and it has to be more than 0
    int unitSize;
    try {
        unitSize = Integer.parseInt(unitSizeText);
    } catch (NumberFormatException e) {
        catBrandMessageLabel.setText("Unit size must be a number");
        return;
    }
    if (unitSize <= 0) {
        catBrandMessageLabel.setText("Unit size must be more than 0");
        return;
    }
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        stmt.executeUpdate("insert into Category(Category_Name, Unit_Size) values('" + catName + "'," + unitSize + ")");
        stmt.close();
        mycon.close();
        catBrandMessageLabel.setText("Category add is done");
        categoryNameField.clear(); 
        categoryUnitSizeField.clear();
        showCategories(); //refresh
    } catch (Exception e) {
        if (e.getMessage() != null && e.getMessage().contains("Duplicate")) {
            catBrandMessageLabel.setText("This category already exists");
        } else {
            catBrandMessageLabel.setText("Failed to add category");
        }
    }
}

@FXML
void showCategories(){
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        ResultSet rs = stmt.executeQuery("select * from Category order by Category_ID");
        categoryInfoArea.clear(); //clear first so old list doesnt stay on screen
        categoryInfoArea.appendText("Categories List:\n");
        while (rs.next()) {
            int cid = rs.getInt("Category_ID");
            String cname = rs.getString("Category_Name");
            int csize = rs.getInt("Unit_Size");
            categoryInfoArea.appendText(cid + " : " + cname + " (unit size " + csize + ")\n");
        }
        rs.close();
        stmt.close();
        mycon.close();
        catBrandMessageLabel.setText("Done");
    } catch (Exception e){
        catBrandMessageLabel.setText("Could not load categories");
    }
}
@FXML
void addBrand(){
    String bname = brandNameField.getText();
    String bweb = brandWebsiteField.getText();
    if (bname.isEmpty()) {
        catBrandMessageLabel.setText("Enter brand name");
        return;
    }
    //website is optional if its empty  just store empty string
    if (bweb.isEmpty()){
        bweb = "";
    }
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        stmt.executeUpdate("insert into Brand(Brand_Name, Website) values('" + bname + "','" + bweb + "')");
        stmt.close();
        mycon.close();
        catBrandMessageLabel.setText("Brand added");
        brandNameField.clear();
        brandWebsiteField.clear(); 
        showBrands(); //refresh 

    } catch (Exception e){
        if (e.getMessage() != null && e.getMessage().contains("Duplicate")) {
            catBrandMessageLabel.setText("Brand already exists");
        } else {
            catBrandMessageLabel.setText("Failed to add brand");
        }
    }
}

@FXML
void showBrands() {

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        ResultSet rs = stmt.executeQuery("select * from Brand order by Brand_ID");
        brandInfoArea.clear();
        brandInfoArea.appendText("Brands List:\n");
        while (rs.next()) {
            int bid = rs.getInt("Brand_ID");
            String bname = rs.getString("Brand_Name");
            brandInfoArea.appendText(bid + " : " + bname + "\n");
        }
        rs.close();
        stmt.close();
        mycon.close();
        catBrandMessageLabel.setText("Done");
    } catch (Exception e) {
        catBrandMessageLabel.setText("Could not load brands");
    }
}
@FXML
void showProductSuppliers() {

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "select p.Product_Name, s.Contact_Name, sp.Supply_Price "+ "from Supplier_Product sp, Product p, Supplier s "+ "where sp.Product_ID = p.Product_ID "+ "and sp.Supplier_ID = s.Supplier_ID "+ "order by p.Product_Name"; 
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<ProductSupplier> list = FXCollections.observableArrayList();
        while (rs.next()) {
            String pname = rs.getString("Product_Name");
            String sname = rs.getString("Contact_Name");
            double sprice = rs.getDouble("Supply_Price");
            ProductSupplier ps = new ProductSupplier(pname, sname, sprice);
            list.add(ps);
        }
        supplierProductTable.setItems(list);
        supplierMessageLabel.setText("Loaded " + list.size() + " records");
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e){
        supplierMessageLabel.setText("Could not load supplier data");
    }
}

@FXML
void searchProductSuppliers(){

    String searchText = supplierSearchField.getText();
    if (searchText.isEmpty()) {
        supplierMessageLabel.setText("Enter a product name to search");
        return;
    }
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "select p.Product_Name, s.Contact_Name, sp.Supply_Price "+ "from Supplier_Product sp, Product p, Supplier s "+ "where sp.Product_ID = p.Product_ID "+ "and sp.Supplier_ID = s.Supplier_ID "+ "and p.Product_Name like '%" + searchText + "%'";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<ProductSupplier> list = FXCollections.observableArrayList();
        while (rs.next()) {
            String pname = rs.getString("Product_Name");
            String sname = rs.getString("Contact_Name");
            double sprice = rs.getDouble("Supply_Price");
            ProductSupplier ps = new ProductSupplier(pname, sname, sprice);
            list.add(ps);
        }
        supplierProductTable.setItems(list);
        rs.close();
        stmt.close();
        mycon.close();
        if (list.isEmpty()){
            supplierMessageLabel.setText("No suppliers found for this product");
        } else {
            supplierMessageLabel.setText("Found " + list.size() + " results");
        }
    } catch (Exception e){
        supplierMessageLabel.setText("Search failed");
    }
}


//count how many products are in each category
@FXML
void showProductsPerCategory() {

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        //group by category and count products in each one , order by most products first
        String sql = "select c.Category_Name, COUNT(p.Product_ID) as Total "+ "from Category c, Product p "+ "where c.Category_ID = p.Category_ID "+ "group by c.Category_Name "+ "order by Total desc";
        ResultSet rs = stmt.executeQuery(sql);
        p_infoArea.clear();
        p_infoArea.appendText("Products per Category:\n");
        p_infoArea.appendText("----------------------\n");
        int totalRows = 0; //counter to show how many categories were found
        while (rs.next()){
            String cat = rs.getString("Category_Name");
            int count = rs.getInt("Total"); 

            p_infoArea.appendText("Category : " + cat + "\n");
            p_infoArea.appendText("Count    : " + count + " products\n");
            p_infoArea.appendText("\n"); 
            totalRows++;
        }
        p_infoMessageLabel.setText("Done - " + totalRows + " categories found");
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        p_infoMessageLabel.setText("Could not load information");
    }
}
//average selling price for products in each category
@FXML
void showAvgPricePerCategory() {

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();

        // avg price grouped by category, sorted highest first
         String sql = "select c.Category_Name, AVG(p.Price) as AvgPrice "+ "from Category c, Product p "+ "where c.Category_ID = p.Category_ID "+ "group by c.Category_Name "+ "order by AvgPrice desc";
        ResultSet rs = stmt.executeQuery(sql);
        p_infoArea.clear(); 
        p_infoArea.appendText("Average Price per Category:\n");
        p_infoArea.appendText("---------------------------\n");
        while (rs.next()) {
            String cat = rs.getString("Category_Name");
            double avg = rs.getDouble("AvgPrice"); 
            p_infoArea.appendText("Category  : " + cat + "\n");
            p_infoArea.appendText("Avg Price : " + avg + "\n");
            p_infoArea.appendText("\n"); 
        }
        p_infoMessageLabel.setText("Done"); 
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        p_infoMessageLabel.setText("Could not load information");
    }
}

//most expensive product in each brand
@FXML
void showMostExpensivePerBrand() {

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        // for each product check if its price equals the max price of its brand
        String sql = "select b.Brand_Name, p.Product_Name, p.Price "+ "from Brand b, Product p "+ "where b.Brand_ID = p.Brand_ID "+ "and p.Price = (select MAX(p2.Price) from Product p2 where p2.Brand_ID = b.Brand_ID) "+ "order by p.Price desc";
        ResultSet rs = stmt.executeQuery(sql);
        p_infoArea.clear();
        p_infoArea.appendText("Most Expensive Product per Brand:\n");
        p_infoArea.appendText("---------------------------------\n");
        while (rs.next()) {
            String brand = rs.getString("Brand_Name");
            String pname = rs.getString("Product_Name");
            double price = rs.getDouble("Price");

            p_infoArea.appendText("Brand   : " + brand + "\n");
            p_infoArea.appendText("Product : " + pname + "\n");
            p_infoArea.appendText("Price   : " + price + "\n");
            p_infoArea.appendText("\n"); 
        }
        p_infoMessageLabel.setText("Done");
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        p_infoMessageLabel.setText("Could not load information");
    }
}

//clears all input fields
@FXML
void clearFields() {
    idField.clear();
    nameField.clear();
    modelField.clear();
    priceField.clear();
    categoryIdField.clear();
    brandIdField.clear();
    searchField.clear();
    messageLabel.setText(""); //clear the message too
}

//for buttons that are not ready yet
@FXML
void notReady() {
    messageLabel.setText("This section is not ready yet");
}

//go to customers page
@FXML
void openCustomers(ActionEvent event) {
    try {
        //load the fxml file and build the ui from it
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/customer.fxml"));
        //get the current window from the button that was clicked
        //event.getSource()=the button, getScene().getWindow()=the window
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        //replace the current scene with the new one,same window, new content
        stage.setScene(new Scene(root, 1400, 840));
        stage.setMaximized(true);
    } catch (Exception e) {
        messageLabel.setText("Cannot open customers page");
    }
}

//go to suppliers page
@FXML
void openSuppliers(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/supplier.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1400, 840));
        stage.setMaximized(true);
    } catch (Exception e) {
        messageLabel.setText("Cannot open suppliers page");
    }
}

//go to employees page
@FXML
void openEmployees(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/employee.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1400, 840));
        stage.setMaximized(true);
    } catch (Exception e) {
        messageLabel.setText("Cannot open employees page");
    }
}

//go to inventory page
@FXML
void openInventory(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/inventory.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1400, 840));
        stage.setMaximized(true);
    } catch (Exception e) {
        messageLabel.setText("Cannot open inventory page");
    }
}

//go to reports page
@FXML
void openReports(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/report.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1400, 840));
        stage.setMaximized(true);
    } catch (Exception e) {
        messageLabel.setText("Cannot open reports page");
    }
}
//go to dashboard page
@FXML
void openDashboard(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1400, 840));
        stage.setMaximized(true);
    } catch (Exception e) {
        messageLabel.setText("Cannot open dashboard page");
    }
}
//go to sales page
@FXML
void openSales(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sales.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1400, 840));
        stage.setMaximized(true);
    } catch (Exception e) {
        messageLabel.setText("Cannot open sales page");
    }
}
//go to purchases page
@FXML
void openPurchases(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/purchases.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1400, 840));
        stage.setMaximized(true);
    } catch (Exception e) {
        messageLabel.setText("Cannot open purchases page");
    }
}
}