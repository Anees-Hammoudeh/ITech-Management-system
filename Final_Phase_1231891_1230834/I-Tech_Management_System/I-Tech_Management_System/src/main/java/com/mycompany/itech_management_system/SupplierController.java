package com.mycompany.itech_management_system;
//imports for the initialize method parameters ( for javafx)
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
// elements nneeded (TextField, Label, TableView, ComboBox etc)
import javafx.scene.control.*;
//imports for switching between pages
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class SupplierController implements Initializable {

    //input fields for supplier data
    @FXML TextField idField;        //for update and delete
    @FXML TextField contactNameField;
    @FXML TextField companyNameField;
    @FXML TextField addressField;
    @FXML TextField mainPhoneField; //phone added when creating new supplier
    //search fields
    @FXML TextField searchField;
    @FXML ComboBox<String> searchTypeCombo;
    //phone section fields
   @FXML TextField phoneSupplierIdField;
   @FXML TextField phoneField;
    @FXML TextField phoneSearchField;
    //labels for messages
   @FXML Label messageLabel;
  @FXML Label searchMessageLabel;
   @FXML Label phoneMessageLabel;
   @FXML Label supplierCountLabel;
    //main supplier table
   @FXML TableView<Supplier> supplierTable;
   @FXML TableColumn<Supplier, Integer> idColumn;
   @FXML TableColumn<Supplier, String> contactNameColumn;
   @FXML TableColumn<Supplier, String> companyNameColumn;
    @FXML TableColumn<Supplier, String> addressColumn;
    //phone table from Supplier and Supplier_Phone
    @FXML TableView<SupplierPhone> phoneTable;
    @FXML TableColumn<SupplierPhone, Integer> phoneSupplierIdColumn;
    @FXML TableColumn<SupplierPhone, String> phoneSupplierNameColumn;
     @FXML TableColumn<SupplierPhone, String> phoneColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //link supplier table columns to Supplier class fields
    idColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("id"));
    contactNameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("contactName"));
    companyNameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("companyName"));
     addressColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("address"));
        //link phone table columns
     phoneSupplierIdColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("supplierId"));
     phoneSupplierNameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("supplierName"));
     phoneColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("phone"));
        //search options for combobox
     searchTypeCombo.getItems().add("Supplier ID");
    searchTypeCombo.getItems().add("Contact Name");
     searchTypeCombo.getItems().add("Company Name");
     searchTypeCombo.getItems().add("Address");
     searchTypeCombo.setValue("Contact Name");
        doShow();
       showPhones();
    }

//adds supplier and phone in two separate inserts because phone is in a different table
@FXML
void doSave() {

    String contact = contactNameField.getText();
    String company = companyNameField.getText();
    String address = addressField.getText();
    String phone = mainPhoneField.getText();

    //all fields are required
    if (contact.isEmpty()) { messageLabel.setText("Contact name is required"); return; }
    if (company.isEmpty()) { messageLabel.setText("Company name is required"); return; }
    if (address.isEmpty()) { messageLabel.setText("Address is required"); return; }
    if (phone.isEmpty()) { messageLabel.setText("Phone is required"); return; }

    //contact name must contain letters, cant be just numbers or symbols
    if (!contact.matches(".*[a-zA-Z].*")) {
        messageLabel.setText("Contact name must contain letters");
        return;
    }
    //company name must contain letters
    if (!company.matches(".*[a-zA-Z].*")) {
        messageLabel.setText("Company name must contain letters");
        return;
    }
    //address must contain at least some letters
    if (!address.matches(".*[a-zA-Z].*")) {
        messageLabel.setText("Address must contain letters");
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
        //insert supplier first without phone
        stmt.executeUpdate("insert into Supplier(Contact_Name, Company_Name, Address) values('"+ contact + "','" + company + "','" + address + "')");
        //get the id that was just generated by the database
        ResultSet rs = stmt.executeQuery("select max(Supplier_ID) as id from Supplier");
        int newId = 0;
        if (rs.next()) { newId = rs.getInt("id"); }
        //insert phone linked to the new supplier
        stmt.executeUpdate("insert into Supplier_Phone(Supplier_ID, Phone) values("+ newId + ",'" + phone + "')");
        rs.close();
        stmt.close();
        mycon.close();
        messageLabel.setText("Supplier add is done");
        clearFields();
        doShow();
        showPhones();
    } catch (Exception e) {
        messageLabel.setText("Could not add supplier");
    }
}

//all suppliers from database
@FXML
void doShow() {

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        ResultSet rs = stmt.executeQuery("select * from Supplier");
        ObservableList<Supplier> list = FXCollections.observableArrayList();
        while (rs.next()) {
            int sid = rs.getInt("Supplier_ID");
            String cname = rs.getString("Contact_Name");
            String coname = rs.getString("Company_Name");
            String addr = rs.getString("Address");
            Supplier s = new Supplier(sid, cname, coname, addr);
            list.add(s);
        }
        supplierTable.setItems(list);
        supplierCountLabel.setText("Total Suppliers: " + list.size());
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        messageLabel.setText("Could not load suppliers");
    }
}

//update supplier info
@FXML
void doUpdate() {

    String id = idField.getText();
    String contact = contactNameField.getText();
    String company = companyNameField.getText();
    String address = addressField.getText();

    //all fields required
    if (id.isEmpty()) { messageLabel.setText("Supplier ID is required"); return; }
    if (contact.isEmpty()) { messageLabel.setText("Contact name is required"); return; }
    if (company.isEmpty()) { messageLabel.setText("Company name is required"); return; }
    if (address.isEmpty()) { messageLabel.setText("Address is required"); return; }

    //contact name must contain letters
    if (!contact.matches(".*[a-zA-Z].*")) {
        messageLabel.setText("Contact name must contain letters");
        return;
    }
    //company name must contain letters
    if (!company.matches(".*[a-zA-Z].*")) {
        messageLabel.setText("Company name must contain letters");
        return;
    }
    //address must contain letters
    if (!address.matches(".*[a-zA-Z].*")) {
        messageLabel.setText("Address must contain letters");
        return;
    }
    //validate id is a number and positive before connecting
    int sid;
    try {
        sid = Integer.parseInt(id);
    } catch (NumberFormatException e) {
        messageLabel.setText("Supplier ID must be a number");
        return;
    }
    //ids start from 1, 0 or negative dont exist
    if (sid <= 0) {
        messageLabel.setText("Supplier ID must be a positive number");
        return;
    }
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        int rows = stmt.executeUpdate("update Supplier set Contact_Name='" + contact+ "', Company_Name='" + company+ "', Address='" + address+ "' where Supplier_ID=" + sid);
        stmt.close();
        mycon.close();
        if (rows > 0) {
            messageLabel.setText("Supplier update is done");
            clearFields();
            doShow();
        } else {
            messageLabel.setText("Supplier ID not found");
        }
    } catch (Exception e) {
        messageLabel.setText("Update failed");
    }
}

//delete supplier by id
@FXML
void doDelete(){

    String id = idField.getText();
    if (id.isEmpty()) {
        messageLabel.setText("Supplier ID is required");
        return;
    }
    //id must be a number
    int sid;
    try {
        sid = Integer.parseInt(id);
    } catch (NumberFormatException e) {
        messageLabel.setText("Supplier ID must be a number");
        return;
    }
    //0 or negative ids dont exist
    if (sid <= 0) {
        messageLabel.setText("Supplier ID must be a positive number");
        return;
    }
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        int rows = stmt.executeUpdate("delete from Supplier where Supplier_ID=" + sid);
        stmt.close();
        mycon.close();
        if (rows > 0) {
            messageLabel.setText("Supplier delete is done");
            clearFields();
            doShow();
            showPhones();
        } else {
            messageLabel.setText("Supplier ID not found");
        }
    } catch (Exception e) {
        //database blocks delete if supplier is linked to a purchase
        if (e.getMessage() != null && (e.getMessage().contains("foreign key") || e.getMessage().contains("constraint"))) {
            messageLabel.setText("Cannot delete, supplier is used in another table");
        } else {
            messageLabel.setText("Delete failed");
        }
    }
}

//search suppliers based on what user selects in the combobox
@FXML
void doSearch() {

    searchMessageLabel.setText("");
    String searchText = searchField.getText();
    if (searchText.isEmpty()) {
        searchMessageLabel.setText("Enter a search value");
        return;
    }
    String selectedType = searchTypeCombo.getValue();

    if (selectedType.equals("Supplier ID")) {
        //id must be a positive number
        int searchId;
        try {
            searchId = Integer.parseInt(searchText);
        } catch (NumberFormatException e) {
            searchMessageLabel.setText("Supplier ID must be a number");
            return;
        }
        if (searchId <= 0) {
            searchMessageLabel.setText("Supplier ID must be a positive number");
            return;
        }
    } else {
        //text search fields must contain at least some letters, not just numbers or symbols
        if (!searchText.matches(".*[a-zA-Z].*")) {
            searchMessageLabel.setText("Search text must contain letters");
            return;
        }
    }
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "";
        if (selectedType.equals("Supplier ID")) {
            sql = "select * from Supplier where Supplier_ID=" + searchText;
        } else if (selectedType.equals("Contact Name")) {
            sql = "select * from Supplier where Contact_Name like '%" + searchText + "%'";
        } else if (selectedType.equals("Company Name")) {
            sql = "select * from Supplier where Company_Name like '%" + searchText + "%'";
        } else if (selectedType.equals("Address")) {
            sql = "select * from Supplier where Address like '%" + searchText + "%'";
        }
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<Supplier> list = FXCollections.observableArrayList();
        while (rs.next()) {
            int sid = rs.getInt("Supplier_ID");
            String cname = rs.getString("Contact_Name");
            String coname = rs.getString("Company_Name");
            String addr = rs.getString("Address");
            Supplier s = new Supplier(sid, cname, coname, addr);
            list.add(s);
        }
        supplierTable.setItems(list);
        supplierCountLabel.setText("Found: " + list.size() + " suppliers");
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

//add extra phone number to an existing supplier
@FXML
void addPhone() {

    String id = phoneSupplierIdField.getText();
    String phone = phoneField.getText();

    if (id.isEmpty()) { phoneMessageLabel.setText("Supplier ID is required"); return; }
    if (phone.isEmpty()) { phoneMessageLabel.setText("Phone is required"); return; }

    //phone must be digits only and between 9 and 12 digits
    if (!phone.matches("\\d+") || phone.length() < 9 || phone.length() > 12) {
        phoneMessageLabel.setText("Phone must be between 9 and 12 digits");
        return;
    }
    int sid;
    try {
        sid = Integer.parseInt(id);
    } catch (NumberFormatException e) {
        phoneMessageLabel.setText("Supplier ID must be a number");
        return;
    }
    if (sid <= 0) {
        phoneMessageLabel.setText("Supplier ID must be a positive number");
        return;
    }
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        stmt.executeUpdate("insert into Supplier_Phone(Supplier_ID, Phone) values(" + sid + ",'" + phone + "')");
        stmt.close();
        mycon.close();
        phoneMessageLabel.setText("Phone add is done");
        phoneSupplierIdField.clear();
        phoneField.clear();
        showPhones();
    } catch (Exception e) {
        if (e.getMessage() != null && e.getMessage().contains("Supplier_ID")) {
            phoneMessageLabel.setText("Supplier ID does not exist");
        } else if (e.getMessage() != null && e.getMessage().contains("Duplicate")) {
            phoneMessageLabel.setText("This phone already exists for this supplier");
        } else {
            phoneMessageLabel.setText("Could not add phone");
        }
    }
}

// delete a specific phone number from a supplier
@FXML
void deletePhone() {

    String id = phoneSupplierIdField.getText();
    String phone = phoneField.getText();

    if (id.isEmpty()) { phoneMessageLabel.setText("Supplier ID is required"); return; }
    if (phone.isEmpty()) { phoneMessageLabel.setText("Phone is required"); return; }

    // phone validation 
    if (!phone.matches("\\d+") || phone.length() < 9 || phone.length() > 12) {
        phoneMessageLabel.setText("Phone must be between 9 and 12 digits");
        return;
    }
    int sid;
    try {
        sid = Integer.parseInt(id);
    } catch (NumberFormatException e) {
        phoneMessageLabel.setText("Supplier ID must be a number");
        return;
    }
    if (sid <= 0) {
        phoneMessageLabel.setText("Supplier ID must be a positive number");
        return;
    }
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        int rows = stmt.executeUpdate("delete from Supplier_Phone where Supplier_ID="+ sid + " and Phone='" + phone + "'");
        stmt.close();
        mycon.close();
        if (rows > 0) {
            phoneMessageLabel.setText("Phone delete is done");
            phoneSupplierIdField.clear();
            phoneField.clear();
            showPhones();
        } else {
            phoneMessageLabel.setText("Phone not found for this supplier");
        }
    } catch (Exception e) {
        phoneMessageLabel.setText("Delete failed");
    }
}

//all supplier phones
@FXML
void showPhones() {

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "select s.Supplier_ID, s.Contact_Name, sp.Phone "+ "from Supplier s, Supplier_Phone sp "+ "where s.Supplier_ID = sp.Supplier_ID "+ "order by s.Supplier_ID";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<SupplierPhone> list = FXCollections.observableArrayList();
        while (rs.next()) {
            int sid = rs.getInt("Supplier_ID");
            String sname = rs.getString("Contact_Name");
            String phone = rs.getString("Phone");
            SupplierPhone sp = new SupplierPhone(sid, sname, phone);
            list.add(sp);
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

//search phone table by supplier contact name
@FXML
void searchPhones() {

    String searchText = phoneSearchField.getText();
    if (searchText.isEmpty()) {
        phoneMessageLabel.setText("Enter supplier name to search");
        return;
    }
    //name search must contain letters
    if (!searchText.matches(".*[a-zA-Z].*")) {
        phoneMessageLabel.setText("Search text must contain letters");
        return;
    }
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "select s.Supplier_ID, s.Contact_Name, sp.Phone "+ "from Supplier s, Supplier_Phone sp "+ "where s.Supplier_ID = sp.Supplier_ID "+ "and s.Contact_Name like '%" + searchText + "%'";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<SupplierPhone> list = FXCollections.observableArrayList();
        while (rs.next()) {
            int sid = rs.getInt("Supplier_ID");
            String sname = rs.getString("Contact_Name");
            String phone = rs.getString("Phone");
            SupplierPhone sp = new SupplierPhone(sid, sname, phone);
            list.add(sp);
        }
        phoneTable.setItems(list);
        rs.close();
        stmt.close();
        mycon.close();
        if (list.isEmpty()) {
            phoneMessageLabel.setText("No phones found for this supplier");
        } else {
            phoneMessageLabel.setText("Found " + list.size() + " results");
        }
    } catch (Exception e) {
        phoneMessageLabel.setText("Search failed");
    }
}

//clear all supplier input fields
@FXML
void clearFields() {
    idField.clear();
    contactNameField.clear();
    companyNameField.clear();
    addressField.clear();
    mainPhoneField.clear();
    searchField.clear();
    messageLabel.setText("");
}

//clear the phone section fields
@FXML
void clearPhoneFields() {
    phoneSupplierIdField.clear();
    phoneField.clear();
    phoneSearchField.clear();
    phoneMessageLabel.setText("");
}

//for pages not done yet
@FXML
void notReady() {
    messageLabel.setText("This section is not ready yet");
}

//go to products page
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

//go to customers page
@FXML
void openCustomers(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/customer.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1400, 840));
        stage.setMaximized(true);
    } catch (Exception e) {
        messageLabel.setText("Cannot open customers page");
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