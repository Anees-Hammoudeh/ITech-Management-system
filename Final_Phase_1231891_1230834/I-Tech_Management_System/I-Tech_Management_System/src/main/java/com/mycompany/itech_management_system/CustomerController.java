package com.mycompany.itech_management_system;

//imports for the initialize method parameters (for javafx)
import java.net.URL;
import java.util.ResourceBundle;
//imports for database connection and queries
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
//imports for the table data list
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
// imports for connecting controller to fxml and go between pages
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
// elements nneeded (TextField, Label, TableView, ComboBox etc)
import javafx.scene.control.*;
//imports for switching between pages
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class CustomerController implements Initializable {

    //input fields for customer data
    @FXML TextField idField;       //for update and delete
    @FXML TextField firstNameField;
    @FXML TextField lastNameField;
    @FXML TextField mainPhoneField; /// the phone added when creating new customer
    //search fields
    @FXML TextField searchField;
      @FXML ComboBox<String> searchTypeCombo;
    //phone section fields
    @FXML TextField phoneCustomerIdField;
    @FXML TextField phoneField;
     @FXML TextField phoneSearchField;
    //labels for messages
    @FXML Label messageLabel;
    @FXML Label searchMessageLabel;
    @FXML Label phoneMessageLabel;
    @FXML Label customerCountLabel;
    @FXML Label salesMessageLabel;
    //main customer table
    @FXML TableView<Customer> customerTable;
    @FXML TableColumn<Customer, Integer> idColumn;
    @FXML TableColumn<Customer, String> firstNameColumn;
    @FXML TableColumn<Customer, String> lastNameColumn;
    //phone table from Customer and Customer_Phone
    @FXML TableView<CustomerPhone> phoneTable;
   @FXML TableColumn<CustomerPhone, Integer> phoneCustomerIdColumn;
    @FXML TableColumn<CustomerPhone, String> phoneCustomerNameColumn;
    @FXML TableColumn<CustomerPhone, String> phoneColumn;
    //sales table from multiple tables
    @FXML TableView<CustomerSales> customerSalesTable;
    @FXML TableColumn<CustomerSales, Integer> csCustomerIdColumn;
    @FXML TableColumn<CustomerSales, String> csCustomerNameColumn;
    @FXML TableColumn<CustomerSales, Integer> csSaleIdColumn;
    @FXML TableColumn<CustomerSales, String> csProductNameColumn;
   @FXML TableColumn<CustomerSales, Integer> csQuantityColumn;
    @FXML TableColumn<CustomerSales, Double> csLineTotalColumn;
    @FXML TableColumn<CustomerSales, Double> csPaidTotalColumn;
    //search field for sales table
    @FXML TextField salesSearchField;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {

       //link customer table columns to Customer class fields
      idColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("id"));
      firstNameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("firstName"));
      lastNameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("lastName"));
        //link phone table columns
       phoneCustomerIdColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("customerId"));
       phoneCustomerNameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("customerName"));
       phoneColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("phone"));
        //link sales table columns
       csCustomerIdColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("customerId"));
       csCustomerNameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("customerName"));
       csSaleIdColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("saleId"));
       csProductNameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("productName"));
       csQuantityColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("quantity"));
        csLineTotalColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("lineTotal"));
       csPaidTotalColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("paidTotal"));
        //earch options for combobox
       searchTypeCombo.getItems().add("Customer ID");
         searchTypeCombo.getItems().add("First Name");
       searchTypeCombo.getItems().add("Last Name");
        searchTypeCombo.setValue("First Name");
       
      doShow();
       showPhones();
        showCustomerSales();
    }

@FXML
void doSave() {

    //get values from input fields
    String fname = firstNameField.getText();
    String lname = lastNameField.getText();
    String phone = mainPhoneField.getText();
    //check all fields are filled 
    if (fname.isEmpty() || lname.isEmpty() || phone.isEmpty()) {
        messageLabel.setText("Fill all customer fields");
        return;
    }
    //names cant be numbers
    if (fname.matches(".*\\d.*") || lname.matches(".*\\d.*")) {
        messageLabel.setText("Name cannot contain numbers");
        return;
    }
    //phone must be digits only and between 9 and 12 digits
    if (!phone.matches("\\d+") || phone.length() < 9 || phone.length() > 12) {
        messageLabel.setText("Phone must be between 9 and 12 digits");
        return;
    }
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        //insert the customer first without the phone
        stmt.executeUpdate("insert into Customer(First_Name, Last_Name) values('"+ fname + "','" + lname + "')");
        
        //get the id that was just generated by the database and max gives the last inserted id since auto increment goes up
        ResultSet rs = stmt.executeQuery("select max(Customer_ID) as id from Customer");
        int newId = 0;
        if (rs.next()) {
            newId = rs.getInt("id");
        }
        //insert the phone linked to the new customer, id phone is in a separate table because customer can have multiple phones
        stmt.executeUpdate("insert into Customer_Phone(Customer_ID, Phone) values("+ newId + ",'" + phone + "')");
        rs.close();
        stmt.close();
        mycon.close();
        messageLabel.setText("Customer add is done");
        clearFields();
        doShow();      //refresh customer table
        showPhones();  //refresh phone table
    } catch (Exception e) {
        messageLabel.setText("Could not add customer");
    }
}

@FXML
void doShow() {

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        ResultSet rs = stmt.executeQuery("select * from Customer");
        ObservableList<Customer> list = FXCollections.observableArrayList();
        //rs.next() moves to next row and returns false when no more rows
        while (rs.next()){
            //read each column by name and correct type
            int cid = rs.getInt("Customer_ID");
            String fname = rs.getString("First_Name");
            String lname = rs.getString("Last_Name");
            Customer c = new Customer(cid, fname, lname);
            list.add(c);
        }
        customerTable.setItems(list);
        customerCountLabel.setText("Total Customers: " + list.size());
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e){
        messageLabel.setText("Could not load customers");
    }
}
//only updates first and last name not the phone (phone has its own section)
@FXML
void doUpdate() {

    //get all field values 
    String id = idField.getText();
    String fname = firstNameField.getText();
    String lname = lastNameField.getText();
    if (id.isEmpty() || fname.isEmpty() || lname.isEmpty()) {
        messageLabel.setText("Fill all fields to update");
        return;
    }
    //customer with id=1 is the default walk-in customer used for unregistered buyers and dont want anyone to accidentally change it
    if (id.equals("1")) {
        messageLabel.setText("Cannot update walk-in customer");
        return;
    }
    //names cant be numbers
    if (fname.matches(".*\\d.*") || lname.matches(".*\\d.*")) {
        messageLabel.setText("Name cannot contain numbers");
        return;
    }
    int cid;
    try {
        cid = Integer.parseInt(id);
    } catch (NumberFormatException e) {
        messageLabel.setText("Customer ID must be a number");
        return;
    }
    //ids start from 1, 0 or negative dont exist
    if (cid <= 0) {
        messageLabel.setText("Customer ID must be a positive number");
        return;
    }
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        int rows = stmt.executeUpdate("update Customer set First_Name='" + fname+ "', Last_Name='" + lname+ "' where Customer_ID=" + cid);
        stmt.close();
        mycon.close();
        //rows is 0 if no customer had this id
        if (rows > 0) {
            messageLabel.setText("Customer update is done");
            clearFields();
            doShow();
        } else {
            messageLabel.setText("Customer ID not found");
        }
    } catch (Exception e) {
        messageLabel.setText("Update failed");
    }
}
//deleting a customer also deletes their phones because of cascade delete in database
@FXML
void doDelete() {
    String id = idField.getText();
    if (id.isEmpty()) {
        messageLabel.setText("Enter customer ID to delete");
        return;
    }
    // customer id=1 is the default walk-in customer used for unregistered buyers its protected it from being deleted
    if (id.equals("1")) {
        messageLabel.setText("Cannot delete walk-in customer");
        return;
    }
    //validate id is a number before 
    int cid;
    try {
        cid = Integer.parseInt(id);
    } catch (NumberFormatException e) {
        messageLabel.setText("Customer ID must be a number");
        return;
    }
    //0 or negative ids dont exist
    if (cid <= 0) {
        messageLabel.setText("Customer ID must be a positive number");
        return;
    }
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        int rows = stmt.executeUpdate("delete from Customer where Customer_ID=" + cid);
        stmt.close();
        mycon.close();
        if (rows > 0) {
            messageLabel.setText("Customer delete is done");
            clearFields();
            doShow();
            showPhones(); 
        } else {
            messageLabel.setText("Customer ID not found");
        }
    } catch (Exception e) {
        //error happens if customer is linked to a sale, database blocks the delete
        if (e.getMessage() != null && (e.getMessage().contains("foreign key") || e.getMessage().contains("constraint"))) {
            messageLabel.setText("Cannot delete, customer is used in another table");
        } else {
            messageLabel.setText("Delete failed");
        }
    }
}

@FXML
void doSearch() {

    searchMessageLabel.setText("");  
    String searchText = searchField.getText();
    if (searchText.isEmpty()) {
        searchMessageLabel.setText("Enter a search value");
        return;
    }
    String selectedType = searchTypeCombo.getValue();
    //only validate as number if searching by id
    if (selectedType.equals("Customer ID")) {
        int searchId;
        try {
            searchId = Integer.parseInt(searchText);
        } catch (NumberFormatException e) {
            searchMessageLabel.setText("Customer ID must be a number");
            return;
        }
        //negative ids dont exist
        if (searchId <= 0) {
            searchMessageLabel.setText("Customer ID must be a positive number");
            return;
        }
    }
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        //start with empty sql then fill based on selected type
        String sql = "";
        if (selectedType.equals("Customer ID")) {
            //exact match for id
            sql = "select * from Customer where Customer_ID=" + searchText;
        } else if (selectedType.equals("First Name")) {
            //like with % to find matches
            sql = "select * from Customer where First_Name like '%" + searchText + "%'";
        } else if (selectedType.equals("Last Name")) {
            sql = "select * from Customer where Last_Name like '%" + searchText + "%'";
        }
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<Customer> list = FXCollections.observableArrayList();
        while (rs.next()) {
            int cid = rs.getInt("Customer_ID");
            String fname = rs.getString("First_Name");
            String lname = rs.getString("Last_Name");
            Customer c = new Customer(cid, fname, lname);
            list.add(c);
        }
        customerTable.setItems(list);
        customerCountLabel.setText("Found: " + list.size() + " customers");
        rs.close();
        stmt.close();
        mycon.close();
      
        if (list.isEmpty()) {
            searchMessageLabel.setText("No results found");
        } else {
            searchMessageLabel.setText("Search is done");
        }
    } catch (Exception e) {
        searchMessageLabel.setText("Search failed");
    }
}
 //add an extra phone number to an existing customer
@FXML
void addPhone() {
    String id = phoneCustomerIdField.getText();
    String phone = phoneField.getText();
    if (id.isEmpty() || phone.isEmpty()) {
        phoneMessageLabel.setText("Enter customer ID and phone");
        return;
    }
    //protect walk-in customer from being changed
    if (id.equals("1")) {
        phoneMessageLabel.setText("Cannot change walk-in customer phone");
        return;
    }
    //phone must be digits only and between 9 and 12 digits
    if (!phone.matches("\\d+") || phone.length() < 9 || phone.length() > 12) {
        phoneMessageLabel.setText("Phone must be between 9 and 12 digits");
        return;
    }
    int cid;
    try {
        cid = Integer.parseInt(id);
    } catch (NumberFormatException e) {
        phoneMessageLabel.setText("Customer ID must be a number");
        return;
    }
    //negative ids dont exist
    if (cid <= 0) {
        phoneMessageLabel.setText("Customer ID must be a positive number");
        return;
    }
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        // insert into Customer_Phone, both customer id and phone together are the primary key
        stmt.executeUpdate("insert into Customer_Phone(Customer_ID, Phone) values("+ cid + ",'" + phone + "')");
        stmt.close();
        mycon.close();
        phoneMessageLabel.setText("Phone add is done");
        phoneCustomerIdField.clear();
        phoneField.clear();
        showPhones(); 
    } catch (Exception e) {
       
        if (e.getMessage() != null && e.getMessage().contains("Customer_ID")) {
            //foreign key error:customer id not found in Customer table
            phoneMessageLabel.setText("Customer ID does not exist");
        } else if (e.getMessage() != null && e.getMessage().contains("Duplicate")) {
            //unique ;same phone already added for this customer
            phoneMessageLabel.setText("This phone already exists for this customer");
        } else {
            phoneMessageLabel.setText("Could not add phone");
        }
    }
}

@FXML
void deletePhone() {

    String id = phoneCustomerIdField.getText();
    String phone = phoneField.getText();
    //need both fields to identify which record to delete
    if (id.isEmpty() || phone.isEmpty()) {
        phoneMessageLabel.setText("Enter customer ID and phone to delete");
        return;
    }
    //protect walk-in customer
    if (id.equals("1")) {
        phoneMessageLabel.setText("Cannot delete walk-in customer phone");
        return;
    }
    //same phone validation as add  must be digits and between 9 and 12 digits
    if (!phone.matches("\\d+") || phone.length() < 9 || phone.length() > 12) {
        phoneMessageLabel.setText("Phone must be between 9 and 12 digits");
        return;
    }
    int cid;
    try {
        cid = Integer.parseInt(id);
    } catch (NumberFormatException e) {
        phoneMessageLabel.setText("Customer ID must be a number");
        return;
    }
    if (cid <= 0) {
        phoneMessageLabel.setText("Customer ID must be a positive number");
        return;
    }
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        // need both customer id and phone because same phone number could belong to different customers and same customer can have multiple phones
        int rows = stmt.executeUpdate("delete from Customer_Phone where Customer_ID="+ cid + " and Phone='" + phone + "'");
        stmt.close();
        mycon.close();
        if (rows > 0) {
            phoneMessageLabel.setText("Phone delete is done");
            phoneCustomerIdField.clear();
            phoneField.clear();
            showPhones(); 
        } else {
            //rows = 0 means the combination of id + phone was not found
            phoneMessageLabel.setText("Phone not found for this customer");
        }
    } catch (Exception e) {
        phoneMessageLabel.setText("Delete failed");
    }
}
  
@FXML
void showPhones() {

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
     
        String sql = "select c.Customer_ID, c.First_Name, c.Last_Name, cp.Phone "+ "from Customer c, Customer_Phone cp "+ "where c.Customer_ID = cp.Customer_ID "+ "order by c.Customer_ID";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<CustomerPhone> list = FXCollections.observableArrayList();
        while (rs.next()) {
            int cid = rs.getInt("Customer_ID");
            String fullName = rs.getString("First_Name") + " " + rs.getString("Last_Name");
            String phone = rs.getString("Phone");
            CustomerPhone cp = new CustomerPhone(cid, fullName, phone);
            list.add(cp);
        }
        phoneTable.setItems(list);
        phoneMessageLabel.setText("Loaded " + list.size() + " phones");
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        phoneMessageLabel.setText("Could not load phones");
    }
}
   
@FXML
void searchPhones() {

    String searchText = phoneSearchField.getText();
    if (searchText.isEmpty()) {
        phoneMessageLabel.setText("Enter customer name to search");
        return;
    }
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
       
        String sql = "select c.Customer_ID, c.First_Name, c.Last_Name, cp.Phone "+ "from Customer c, Customer_Phone cp "+ "where c.Customer_ID = cp.Customer_ID "+ "and (c.First_Name like '%" + searchText + "%' "+ "or c.Last_Name like '%" + searchText + "%')";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<CustomerPhone> list = FXCollections.observableArrayList();
        while (rs.next()){
            int cid = rs.getInt("Customer_ID");
            String fullName = rs.getString("First_Name") + " " + rs.getString("Last_Name");
            String phone = rs.getString("Phone");
            CustomerPhone cp = new CustomerPhone(cid, fullName, phone);
            list.add(cp);
        }
        phoneTable.setItems(list);
        rs.close();
        stmt.close();
        mycon.close();
        if (list.isEmpty()){
            phoneMessageLabel.setText("No phones found for this customer");
        } else {
            phoneMessageLabel.setText("Found " + list.size() + " results");
        }
    } catch (Exception e){
        phoneMessageLabel.setText("Search failed");
    }
}

@FXML
void showCustomerSales() {

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        
        // line_total = unit price * quantity - discount for each product in the sale
       // paid_total =all payments made for each sale
         String sql = "select c.Customer_ID, c.First_Name, c.Last_Name, "+ "s.Sale_ID, "+ "p.Product_Name, "+ "sd.Quantity, "+ "((sd.Unit_Price * sd.Quantity) - sd.Discount) as line_total, "+ "(select sum(pay.Amount) from Payment pay where pay.Sale_ID = s.Sale_ID) as paid_total "+ "from Customer c, Sale s, Sale_Details sd, Product p "+ "where c.Customer_ID = s.Customer_ID "+ "and s.Sale_ID = sd.Sale_ID "+ "and sd.Product_ID = p.Product_ID "+ "order by c.Customer_ID, s.Sale_ID";
       ResultSet rs = stmt.executeQuery(sql);
        ObservableList<CustomerSales> list = FXCollections.observableArrayList();
        while (rs.next()) {
            int cid = rs.getInt("Customer_ID");
            String fullName = rs.getString("First_Name") + " " + rs.getString("Last_Name");
            int saleId = rs.getInt("Sale_ID");
            String pname = rs.getString("Product_Name");
            int qty = rs.getInt("Quantity");
            double lineTotal = rs.getDouble("line_total");   
            double paidTotal = rs.getDouble("paid_total");   
            CustomerSales cs = new CustomerSales(cid, fullName, saleId, pname, qty, lineTotal, paidTotal);
            list.add(cs);
        }
        customerSalesTable.setItems(list);
        salesMessageLabel.setText("Loaded " + list.size() + " records");
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        salesMessageLabel.setText("Could not load sales");
    }
}
 
    @FXML
    void searchCustomerSales() {
        String searchText = salesSearchField.getText();
        if (searchText.isEmpty()) {
            salesMessageLabel.setText("Enter customer name to search");
            return;
        }
        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();
          
        String sql = "select c.Customer_ID, c.First_Name, c.Last_Name, "+ "s.Sale_ID, "+ "p.Product_Name, "+ "sd.Quantity, "+ "((sd.Unit_Price * sd.Quantity) - sd.Discount) as line_total, "+ "(select sum(pay.Amount) from Payment pay where pay.Sale_ID = s.Sale_ID) as paid_total "+ "from Customer c, Sale s, Sale_Details sd, Product p "+ "where c.Customer_ID = s.Customer_ID "+ "and s.Sale_ID = sd.Sale_ID "+ "and sd.Product_ID = p.Product_ID "+ "and (c.First_Name like '%" + searchText + "%' or c.Last_Name like '%" + searchText + "%') "+ "order by c.Customer_ID, s.Sale_ID";
            ResultSet rs = stmt.executeQuery(sql);
            ObservableList<CustomerSales> list = FXCollections.observableArrayList();
            while (rs.next()) {
                int cid = rs.getInt("Customer_ID");
                String fullName = rs.getString("First_Name") + " " + rs.getString("Last_Name");
                int saleId = rs.getInt("Sale_ID");
                String pname = rs.getString("Product_Name");
                int qty = rs.getInt("Quantity");
                double lineTotal = rs.getDouble("line_total");
                double paidTotal = rs.getDouble("paid_total");
                CustomerSales cs = new CustomerSales(cid, fullName, saleId, pname, qty, lineTotal, paidTotal);
                list.add(cs);
            }
            customerSalesTable.setItems(list);
            rs.close();
            stmt.close();
            mycon.close();
            if (list.isEmpty()){
                salesMessageLabel.setText("No sales found for this customer");
            } else {
                salesMessageLabel.setText("Found " + list.size() + " records");
            }

        } catch (Exception e){
            salesMessageLabel.setText("Search failed");
        }
    }
 //clear all customer input fields and message
@FXML
void clearFields() {
    idField.clear();
    firstNameField.clear();
    lastNameField.clear();
    mainPhoneField.clear();
    searchField.clear();
    messageLabel.setText("");
}

//clear the phone section fields 
@FXML
void clearPhoneFields() {
    phoneCustomerIdField.clear();
    phoneField.clear();
    phoneSearchField.clear();
    phoneMessageLabel.setText("");
}
// for buttons that are not ready yet
@FXML
void notReady() {
    messageLabel.setText("This section is not ready yet");
}
// go to products page 
@FXML
void openProducts(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/product.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1400, 840));
        stage.setMaximized(true);
    } catch (Exception e) {
        messageLabel.setText("Cannot open products page");
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