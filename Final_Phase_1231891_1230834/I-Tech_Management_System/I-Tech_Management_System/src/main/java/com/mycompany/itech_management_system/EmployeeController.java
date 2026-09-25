package com.mycompany.itech_management_system;

//imports for the initialize method parameters (fpr javafx)
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
import javafx.scene.control.*;
//imports for switching between pages
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class EmployeeController implements Initializable {

    //input fields for employee data
  @FXML TextField idField;
  @FXML TextField firstNameField;
  @FXML TextField lastNameField;
 @FXML TextField positionField;
 @FXML TextField salaryField;
 @FXML TextField supervisorIdField; //optional, can be empty if no supervisor
 @FXML TextField mainPhoneField;
  @FXML TextField searchField;
    //phone section fields
  @FXML TextField phoneEmployeeIdField;
  @FXML TextField phoneField;
  @FXML TextField phoneSearchField;
    //work summary search field
  @FXML TextField workSearchField;
    //search combobox
  @FXML ComboBox<String> searchTypeCombo;
    //labels for messages
   @FXML Label messageLabel;
   @FXML Label searchMessageLabel;
   @FXML Label phoneMessageLabel;
   @FXML Label employeeCountLabel;
   @FXML Label workMessageLabel;
   @FXML Label employeeSalesMessageLabel;
   @FXML Label employeePurchaseMessageLabel;
    //main employee table has supervisor name from recursive join
   @FXML TableView<Employee> employeeTable;
   @FXML TableColumn<Employee, Integer> idColumn;
   @FXML TableColumn<Employee, String> firstNameColumn;
   @FXML TableColumn<Employee, String> lastNameColumn;
   @FXML TableColumn<Employee, String> positionColumn;
   @FXML TableColumn<Employee, Double> salaryColumn;
   @FXML TableColumn<Employee, Integer> supervisorIdColumn;
  @FXML TableColumn<Employee, String> supervisorNameColumn;
    //phone table
   @FXML TableView<EmployeePhone> phoneTable;
   @FXML TableColumn<EmployeePhone, Integer> phoneEmployeeIdColumn;
   @FXML TableColumn<EmployeePhone, String> phoneEmployeeNameColumn;
   @FXML TableColumn<EmployeePhone, String> phoneColumn;
    //work summary table
   @FXML TableView<EmployeeWork> workTable;
   @FXML TableColumn<EmployeeWork, Integer> workEmployeeIdColumn;
   @FXML TableColumn<EmployeeWork, String> workEmployeeNameColumn;
   @FXML TableColumn<EmployeeWork, String> positionWorkColumn;
   @FXML TableColumn<EmployeeWork, String> managerWorkColumn;
    @FXML TableColumn<EmployeeWork, Integer> salesCountColumn;
   @FXML TableColumn<EmployeeWork, Integer> purchasesCountColumn;
    @FXML TableColumn<EmployeeWork, Integer> totalRecordsColumn;
    //employee sales table and search
    @FXML TextField employeeSalesSearchField;
    @FXML TableView<EmployeeSales> employeeSalesTable;
    @FXML TableColumn<EmployeeSales, Integer> esEmployeeIdColumn;
    @FXML TableColumn<EmployeeSales, String> esEmployeeNameColumn;
    @FXML TableColumn<EmployeeSales, Integer> esSaleIdColumn;
    @FXML TableColumn<EmployeeSales, String> esCustomerNameColumn;
    @FXML TableColumn<EmployeeSales, Integer> esProductsSoldColumn;
   @FXML TableColumn<EmployeeSales, Double> esSaleTotalColumn;
    //employee purchases table and search
    @FXML TextField employeePurchaseSearchField;
    @FXML TableView<EmployeePurchase> employeePurchaseTable;
    @FXML TableColumn<EmployeePurchase, Integer> epEmployeeIdColumn;
    @FXML TableColumn<EmployeePurchase, String> epEmployeeNameColumn;
    @FXML TableColumn<EmployeePurchase, Integer> epPurchaseIdColumn;
    @FXML TableColumn<EmployeePurchase, String> epSupplierNameColumn;
    @FXML TableColumn<EmployeePurchase, Integer> epProductsPurchasedColumn;
    @FXML TableColumn<EmployeePurchase, Double> epPurchaseTotalColumn;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {

       //link employee table columns to Employee class fields
     idColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("id"));
     firstNameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("firstName"));
     lastNameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("lastName"));
     positionColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("position"));
     salaryColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("salary"));
     supervisorIdColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("supervisorId"));
     supervisorNameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("supervisorName"));
     //link phone table columns
     phoneEmployeeIdColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("employeeId"));
     phoneEmployeeNameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("employeeName"));
     phoneColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("phone"));
       //link work summary table columns
     workEmployeeIdColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("employeeId"));
     workEmployeeNameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("employeeName"));
     positionWorkColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("position"));
     managerWorkColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("managerName"));
     salesCountColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("salesCount"));
       purchasesCountColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("purchasesCount"));
      totalRecordsColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("totalRecords"));
        //link employee sales table columns
      esEmployeeIdColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("employeeId"));
      esEmployeeNameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("employeeName"));
      esSaleIdColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("saleId"));
      esCustomerNameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("customerName"));
      esProductsSoldColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("productsSold"));
      esSaleTotalColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("saleTotal"));
        //link employee purchases table columns
      epEmployeeIdColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("employeeId"));
      epEmployeeNameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("employeeName"));
      epPurchaseIdColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("purchaseId"));
      epSupplierNameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("supplierName"));
      epProductsPurchasedColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("productsPurchased"));
      epPurchaseTotalColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory("purchaseTotal"));
        //search options for combobox
       searchTypeCombo.getItems().add("Employee ID");
      searchTypeCombo.getItems().add("First Name");
       searchTypeCombo.getItems().add("Last Name");
       searchTypeCombo.getItems().add("Position");
      searchTypeCombo.getItems().add("Manager Name");
      searchTypeCombo.setValue("First Name");

     doShow();
      showPhones();
        showWork();
    showEmployeeSales();
       showEmployeePurchases();
    }

//adds employee and phone in two inserts becuase phone is in a different table
@FXML
void doSave() {

    String fname = firstNameField.getText();
    String lname = lastNameField.getText();
    String pos = positionField.getText();
    String salary = salaryField.getText();
    String sup = supervisorIdField.getText(); //optional
    String phone = mainPhoneField.getText();

    //all fields except supervisor are required
    if (fname.isEmpty()) { 
        messageLabel.setText("First name is required"); 
        return;
    }
    if (lname.isEmpty()) { 
        messageLabel.setText("Last name is required"); 
        return; 
    }
    if (pos.isEmpty()) {
        messageLabel.setText("Position is required");
        return; 
    }
    if (salary.isEmpty()) {
        messageLabel.setText("Salary is required"); 
        return; 
    }
    if (phone.isEmpty()) {
        messageLabel.setText("Phone is required"); 
        return;
    }

    //names and position must contain letters, cant be just numbers
    if (!fname.matches(".*[a-zA-Z].*")) {
        messageLabel.setText("First name must contain letters");
        return; 
    }
    if (!lname.matches(".*[a-zA-Z].*")) {
        messageLabel.setText("Last name must contain letters");
        return;
    }
    if (!pos.matches(".*[a-zA-Z].*")) {
        messageLabel.setText("Position must contain letters");
        return; 
    }

    //phone must be digits only and between 9 and 12 digits
    if (!phone.matches("\\d+") || phone.length() < 9 || phone.length() > 12) {
        messageLabel.setText("Phone must be between 9 and 12 digits");
        return; 
    }

    //salary must be a positive number
    double sal;
    try {
        sal = Double.parseDouble(salary);
    } catch (NumberFormatException e) {
        messageLabel.setText("Salary must be a number");
        return;
    }
    if (sal <= 0) {
        messageLabel.setText("Salary must be more than 0"); 
        return; 
    }

    //supervisor is optional but if filled must be a positive number
    if (!sup.isEmpty()) {
        int sid;
        try {
            sid = Integer.parseInt(sup);
        } catch (NumberFormatException e) {
            messageLabel.setText("Supervisor ID must be a number");
            return;
        }
        if (sid <= 0) { 
            messageLabel.setText("Supervisor ID must be a positive number");
            return; 
        }
    }

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql;
        //build different query depending on if supervisor field is filled or not
        if (sup.isEmpty()) {
            //if no supervisor: put NULL in the database
            sql = "insert into Employee(First_Name, Last_Name, Position, Salary, Supervisor_ID) values('"+ fname + "','" + lname + "','" + pos + "'," + sal + ", NULL)";
        } else {
            int sid = Integer.parseInt(sup);
            //employee cant supervise himself
            sql = "insert into Employee(First_Name, Last_Name, Position, Salary, Supervisor_ID) values('"+ fname + "','" + lname + "','" + pos + "'," + sal + "," + sid + ")";
        }
        stmt.executeUpdate(sql);
        //get the id just generated, max gives the last inserted id since auto increment goes up
        ResultSet rs = stmt.executeQuery("select max(Employee_ID) as id from Employee");
        int newId = 0;
        if (rs.next()) { newId = rs.getInt("id"); }
        //insert phone linked to new employee id
        stmt.executeUpdate("insert into Employee_Phone(Employee_ID, Phone) values("+ newId + ",'" + phone + "')");
        rs.close();
        stmt.close();
        mycon.close();
        messageLabel.setText("Employee add is done");
        clearFields();
        doShow();
        showPhones();
        showWork();
    } catch (Exception e) {
        if (e.getMessage() != null && e.getMessage().contains("Supervisor_ID")) {
            messageLabel.setText("Supervisor ID does not exist");
        } else {
            messageLabel.setText("Could not add employee");
        }
    }
}

 //all employees and show in the table
//some employees have no supervisor
@FXML
void doShow(){

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
       
        String sql = "select e.Employee_ID, e.First_Name, e.Last_Name, e.Position, e.Salary, "+ "e.Supervisor_ID, s.First_Name as SFirst, s.Last_Name as SLast "+ "from Employee e left join Employee s on e.Supervisor_ID = s.Employee_ID";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<Employee> list = FXCollections.observableArrayList();
        while (rs.next()) {
            //check if supervisor name is null before building the string
            String supName = "";
            if (rs.getString("SFirst") != null) { supName = rs.getString("SFirst") + " " + rs.getString("SLast"); }
            int eid = rs.getInt("Employee_ID");
            String fname = rs.getString("First_Name");
            String lname = rs.getString("Last_Name");
            String pos = rs.getString("Position");
            double sal = rs.getDouble("Salary");
            int supId = rs.getInt("Supervisor_ID");
            Employee e = new Employee(eid, fname, lname, pos, sal, supId, supName);
            list.add(e);
        }
        employeeTable.setItems(list);
        employeeCountLabel.setText("Total Employees: " + list.size());
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        messageLabel.setText("Could not load employees");
    }
}

//update employee info
@FXML
void doUpdate() {

    String id = idField.getText();
    String fname = firstNameField.getText();
    String lname = lastNameField.getText();
    String pos = positionField.getText();
    String salary = salaryField.getText();
    String sup = supervisorIdField.getText(); //optional

    //all required fields
    if (id.isEmpty()) { 
        messageLabel.setText("Employee ID is required");
        return;
    }
    if (fname.isEmpty()) { 
        messageLabel.setText("First name is required"); 
        return;
    }
    if (lname.isEmpty()) {
        messageLabel.setText("Last name is required");
        return; 
    }
    if (pos.isEmpty()) {
        messageLabel.setText("Position is required");
        return; 
    }
    if (salary.isEmpty()) {
        messageLabel.setText("Salary is required");
        return;
    }

    //names and position must contain letters
    if (!fname.matches(".*[a-zA-Z].*")) {
        messageLabel.setText("First name must contain letters"); 
        return;
    }
    if (!lname.matches(".*[a-zA-Z].*")) {
        messageLabel.setText("Last name must contain letters");
        return;
    }
    if (!pos.matches(".*[a-zA-Z].*")) { 
        messageLabel.setText("Position must contain letters");
        return; 
    }

    //employee id must be a positive number
    int eid;
    try {
        eid = Integer.parseInt(id);
    } catch (NumberFormatException e) {
        messageLabel.setText("Employee ID must be a number");
        return;
    }
    if (eid <= 0) {
        messageLabel.setText("Employee ID must be a positive number");
        return; 
    }

    //salary must be a positive number
    double sal;
    try {
        sal = Double.parseDouble(salary);
    } catch (NumberFormatException e) {
        messageLabel.setText("Salary must be a number");
        return;
    }
    if (sal <= 0) { 
        messageLabel.setText("Salary must be more than 0");
        return;
    }

    //supervisor optional but if filled must be positive and not the employee himself
    if (!sup.isEmpty()) {
        int sid;
        try {
            sid = Integer.parseInt(sup);
        } catch (NumberFormatException e) {
            messageLabel.setText("Supervisor ID must be a number");
            return;
        }
        if (sid <= 0) {
            messageLabel.setText("Supervisor ID must be a positive number"); 
            return; 
        }
        if (eid == sid) { 
            messageLabel.setText("Employee cannot supervise himself"); 
            return; 
        }
    }

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql;
        //build different query depending on if supervisor is there or not
        if (sup.isEmpty()) {
            //clear the supervisor ( null)
            sql = "update Employee set First_Name='" + fname+ "', Last_Name='" + lname+ "', Position='" + pos+ "', Salary=" + sal+ ", Supervisor_ID=NULL where Employee_ID=" + eid;
        } else {
            int sid = Integer.parseInt(sup);
            sql = "update Employee set First_Name='" + fname+ "', Last_Name='" + lname+ "', Position='" + pos+ "', Salary=" + sal+ ", Supervisor_ID=" + sid+ " where Employee_ID=" + eid;
        }
        int rows = stmt.executeUpdate(sql);
        stmt.close();
        mycon.close();
        //rows is 0 if no employee had this id
        if (rows > 0) {
            messageLabel.setText("Employee update is done");
            clearFields();
            doShow();
            showWork();
        } else {
            messageLabel.setText("Employee ID not found");
        }
    } catch (Exception e) {
        if (e.getMessage() != null && e.getMessage().contains("Supervisor_ID")) {
            messageLabel.setText("Supervisor ID does not exist");
        } else {
            messageLabel.setText("Update failed");
        }
    }
}

//delete employee by id
@FXML
void doDelete() {

    String id = idField.getText();
    if (id.isEmpty()) { 
        messageLabel.setText("Employee ID is required");
        return; 
    }

    int eid;
    try {
        eid = Integer.parseInt(id);
    } catch (NumberFormatException e) {
        messageLabel.setText("Employee ID must be a number");
        return;
    }
    //0 or negative ids dont exist
    if (eid <= 0) { 
        messageLabel.setText("Employee ID must be a positive number");
        return; 
    }

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        int rows = stmt.executeUpdate("delete from Employee where Employee_ID=" + eid);
        stmt.close();
        mycon.close();
        if (rows > 0) {
            messageLabel.setText("Employee delete is done");
            clearFields();
            doShow();
            showPhones();
            showWork();
        } else {
            messageLabel.setText("Employee ID not found");
        }
    } catch (Exception e) {
        //database blocks delete if employee has sales or purchases linked to them
        if (e.getMessage() != null && (e.getMessage().contains("foreign key") || e.getMessage().contains("constraint"))) {
            messageLabel.setText("Cannot delete, employee is used in another table");
        } else {
            messageLabel.setText("Delete failed");
        }
    }
}

//search employees based on what user selects in the combobox
@FXML
void doSearch() {

    searchMessageLabel.setText("");
    String searchText = searchField.getText();
    if (searchText.isEmpty()) {
        searchMessageLabel.setText("Enter a search value");
        return;
    }

    String selectedType = searchTypeCombo.getValue();
    if (selectedType.equals("Employee ID")) {
        int searchId;
        try {
            searchId = Integer.parseInt(searchText);
        } catch (NumberFormatException e) {
            searchMessageLabel.setText("Employee ID must be a number");
            return;
        }
        //negative ids dont exist
        if (searchId <= 0) { 
            searchMessageLabel.setText("Employee ID must be a positive number");
            return;
        }
    } else {
        //text searches must contain at least some letters
        if (!searchText.matches(".*[a-zA-Z].*")) { 
            searchMessageLabel.setText("Search text must contain letters"); 
            return; 
        }
    }

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        
        String sql = "";
        if (selectedType.equals("Employee ID")) {
            sql = "select e.Employee_ID, e.First_Name, e.Last_Name, e.Position, e.Salary, "+ "e.Supervisor_ID, s.First_Name as SFirst, s.Last_Name as SLast "+ "from Employee e left join Employee s on e.Supervisor_ID = s.Employee_ID "+ "where e.Employee_ID=" + searchText;
        } else if (selectedType.equals("First Name")) {
            sql = "select e.Employee_ID, e.First_Name, e.Last_Name, e.Position, e.Salary, "+ "e.Supervisor_ID, s.First_Name as SFirst, s.Last_Name as SLast "+ "from Employee e left join Employee s on e.Supervisor_ID = s.Employee_ID "+ "where e.First_Name like '%" + searchText + "%'";
        } else if (selectedType.equals("Last Name")) {
            sql = "select e.Employee_ID, e.First_Name, e.Last_Name, e.Position, e.Salary, "+ "e.Supervisor_ID, s.First_Name as SFirst, s.Last_Name as SLast "+ "from Employee e left join Employee s on e.Supervisor_ID = s.Employee_ID "+ "where e.Last_Name like '%" + searchText + "%'";
        } else if (selectedType.equals("Position")) {
            sql = "select e.Employee_ID, e.First_Name, e.Last_Name, e.Position, e.Salary, "+ "e.Supervisor_ID, s.First_Name as SFirst, s.Last_Name as SLast "+ "from Employee e left join Employee s on e.Supervisor_ID = s.Employee_ID "+ "where e.Position like '%" + searchText + "%'";
        } else if (selectedType.equals("Manager Name")) {
            sql = "select e.Employee_ID, e.First_Name, e.Last_Name, e.Position, e.Salary, "+ "e.Supervisor_ID, s.First_Name as SFirst, s.Last_Name as SLast "+ "from Employee e left join Employee s on e.Supervisor_ID = s.Employee_ID "+ "where s.First_Name like '%" + searchText + "%' "+ "or s.Last_Name like '%" + searchText + "%'";
        }
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<Employee> list = FXCollections.observableArrayList();
        while (rs.next()) {
            String supName = "";
            if (rs.getString("SFirst") != null) { 
                supName = rs.getString("SFirst") + " " + rs.getString("SLast"); 
            }
            
            int eid = rs.getInt("Employee_ID");
            String fname = rs.getString("First_Name");
            String lname = rs.getString("Last_Name");
            String pos = rs.getString("Position");
            double sal = rs.getDouble("Salary");
            int supId = rs.getInt("Supervisor_ID");
            Employee e = new Employee(eid, fname, lname, pos, sal, supId, supName);
            list.add(e);
        }
        employeeTable.setItems(list);
        employeeCountLabel.setText("Found: " + list.size() + " employees");
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

//add extra phone number to an existing employee
@FXML
void addPhone() {

    String id = phoneEmployeeIdField.getText();
    String phone = phoneField.getText();

    if (id.isEmpty()) {
        phoneMessageLabel.setText("Employee ID is required"); 
        return;
    }
    if (phone.isEmpty()) {
        phoneMessageLabel.setText("Phone is required");
        return;
    }

    //phone must be digits only and between 9 and 12 digits
    if (!phone.matches("\\d+") || phone.length() < 9 || phone.length() > 12) {
        phoneMessageLabel.setText("Phone must be between 9 and 12 digits");
        return;
    }

    int eid;
    try {
        eid = Integer.parseInt(id);
    } catch (NumberFormatException e) {
        phoneMessageLabel.setText("Employee ID must be a number");
        return;
    }
    if (eid <= 0) { 
        phoneMessageLabel.setText("Employee ID must be a positive number");
        return;
    }

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        //both employee id and phone together are the primary key of Employee_Phone
        stmt.executeUpdate("insert into Employee_Phone(Employee_ID, Phone) values("+ eid + ",'" + phone + "')");
        stmt.close();
        mycon.close();
        phoneMessageLabel.setText("Phone add is done");
        phoneEmployeeIdField.clear();
        phoneField.clear();
        showPhones();
    } catch (Exception e) {
        if (e.getMessage() != null && e.getMessage().contains("Employee_ID")) {
            //foreign key error: employee id not found in Employee table
            phoneMessageLabel.setText("Employee ID does not exist");
        } else if (e.getMessage() != null && e.getMessage().contains("Duplicate")) {
            //same phone already exists for this employee
            phoneMessageLabel.setText("This phone already exists for this employee");
        } else {
            phoneMessageLabel.setText("Could not add phone");
        }
    }
}

//delete a specific phone from an employee
@FXML
void deletePhone() {

    String id = phoneEmployeeIdField.getText();
    String phone = phoneField.getText();

    if (id.isEmpty()) { 
        phoneMessageLabel.setText("Employee ID is required"); 
        return; 
    }
    if (phone.isEmpty()) {
        phoneMessageLabel.setText("Phone is required"); 
        return;
    }

    //same phone validation as add
    if (!phone.matches("\\d+") || phone.length() < 9 || phone.length() > 12) {
        phoneMessageLabel.setText("Phone must be between 9 and 12 digits"); 
        return;
    }

    int eid;
    try {
        eid = Integer.parseInt(id);
    } catch (NumberFormatException e) {
        phoneMessageLabel.setText("Employee ID must be a number");
        return;
    }
    if (eid <= 0) {
        phoneMessageLabel.setText("Employee ID must be a positive number");
        return; 
    }

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        int rows = stmt.executeUpdate("delete from Employee_Phone where Employee_ID="+ eid + " and Phone='" + phone + "'");
        stmt.close();
        mycon.close();
        if (rows > 0) {
            phoneMessageLabel.setText("Phone delete is done");
            phoneEmployeeIdField.clear();
            phoneField.clear();
            showPhones();
        } else {
            //rows = 0 means the combination of id + phone was not found
            phoneMessageLabel.setText("Phone not found for this employee");
        }
    } catch (Exception e) {
        phoneMessageLabel.setText("Delete failed");
    }
}

//all employee phones
@FXML
void showPhones() {

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        // Employee with Employee_Phone to get name with each phone
        String sql = "select e.Employee_ID, e.First_Name, e.Last_Name, ep.Phone "+ "from Employee e, Employee_Phone ep "+ "where e.Employee_ID = ep.Employee_ID "+ "order by e.Employee_ID";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<EmployeePhone> list = FXCollections.observableArrayList();
        while (rs.next()) {
            int eid = rs.getInt("Employee_ID");
            String fullName = rs.getString("First_Name") + " " + rs.getString("Last_Name");
            String phone = rs.getString("Phone");
            EmployeePhone ep = new EmployeePhone(eid, fullName, phone);
            list.add(ep);
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

//search phone table by employee name
@FXML
void searchPhones() {

    String searchText = phoneSearchField.getText();
    if (searchText.isEmpty()) {
        phoneMessageLabel.setText("Enter employee name to search"); 
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
        
        
        String sql = "select e.Employee_ID, e.First_Name, e.Last_Name, ep.Phone "+ "from Employee e, Employee_Phone ep "+ "where e.Employee_ID = ep.Employee_ID "+ "and (e.First_Name like '%" + searchText + "%' "+ "or e.Last_Name like '%" + searchText + "%')";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<EmployeePhone> list = FXCollections.observableArrayList();
        while (rs.next()) {
            int eid = rs.getInt("Employee_ID");
            String fullName = rs.getString("First_Name") + " " + rs.getString("Last_Name");
            String phone = rs.getString("Phone");
            EmployeePhone ep = new EmployeePhone(eid, fullName, phone);
            list.add(ep);
        }
        phoneTable.setItems(list);
        rs.close();
        stmt.close();
        mycon.close();
        if (list.isEmpty()) {
            phoneMessageLabel.setText("No phones found for this employee");
        } else {
            phoneMessageLabel.setText("Found " + list.size() + " results");
        }
    } catch (Exception e) {
        phoneMessageLabel.setText("Search failed");
    }
}

//show work summary for all employees, how many sales and purchases each one recorded
@FXML
void showWork() {

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "select e.Employee_ID, e.First_Name, e.Last_Name, e.Position, "+ "s.First_Name as MFirst, s.Last_Name as MLast, "+ "(select count(*) from Sale sa where sa.Employee_ID = e.Employee_ID) as sales_count, "+ "(select count(*) from Purchase p where p.Employee_ID = e.Employee_ID) as purchase_count "+ "from Employee e left join Employee s on e.Supervisor_ID = s.Employee_ID "+ "order by e.Employee_ID";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<EmployeeWork> list = FXCollections.observableArrayList();
        while (rs.next()) {
            int eid = rs.getInt("Employee_ID");
            String fullName = rs.getString("First_Name") + " " + rs.getString("Last_Name");
            String pos = rs.getString("Position");
            //check null for manager name
            String manager = "";
            if (rs.getString("MFirst") != null) { 
                manager = rs.getString("MFirst") + " " + rs.getString("MLast"); 
            }
            int sales = rs.getInt("sales_count");
            int purchases = rs.getInt("purchase_count");
            EmployeeWork w = new EmployeeWork(eid, fullName, pos, manager, sales, purchases, sales + purchases);
            list.add(w);
        }
        workTable.setItems(list);
        workMessageLabel.setText("Work summary loaded");
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        workMessageLabel.setText("Could not load work summary");
    }
}

//search work summary by employee name
@FXML
void searchWork() {

    String searchText = workSearchField.getText();
    if (searchText.isEmpty()) {
        workMessageLabel.setText("Enter employee name to search"); 
        return;
    }
    //name must contain letters
    if (!searchText.matches(".*[a-zA-Z].*")) { 
        workMessageLabel.setText("Search text must contain letters");
        return; 
    }

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "select e.Employee_ID, e.First_Name, e.Last_Name, e.Position, "+ "s.First_Name as MFirst, s.Last_Name as MLast, "+ "(select count(*) from Sale sa where sa.Employee_ID = e.Employee_ID) as sales_count, "+ "(select count(*) from Purchase p where p.Employee_ID = e.Employee_ID) as purchase_count "+ "from Employee e left join Employee s on e.Supervisor_ID = s.Employee_ID "+ "where e.First_Name like '%" + searchText + "%' "+ "or e.Last_Name like '%" + searchText + "%'";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<EmployeeWork> list = FXCollections.observableArrayList();
        while (rs.next()) {
            int eid = rs.getInt("Employee_ID");
            String fullName = rs.getString("First_Name") + " " + rs.getString("Last_Name");
            String pos = rs.getString("Position");
            String manager = "";
            if (rs.getString("MFirst") != null) {
                manager = rs.getString("MFirst") + " " + rs.getString("MLast"); 
            }
            int sales = rs.getInt("sales_count");
            int purchases = rs.getInt("purchase_count");
            EmployeeWork w = new EmployeeWork(eid, fullName, pos, manager, sales, purchases, sales + purchases);
            list.add(w);
        }
        workTable.setItems(list);
        rs.close();
        stmt.close();
        mycon.close();
        if (list.isEmpty()) {
            workMessageLabel.setText("No results found");
        } else {
            workMessageLabel.setText("Found " + list.size() + " employees");
        }
    } catch (Exception e) {
        workMessageLabel.setText("Search failed");
    }
}

//show all employee sales details
@FXML
void showEmployeeSales() {

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        
        String sql = "select e.Employee_ID, e.First_Name, e.Last_Name, s.Sale_ID, "+ "c.First_Name as CFirst, c.Last_Name as CLast, "+ "(select count(*) from Sale_Details sd where sd.Sale_ID=s.Sale_ID) as products_sold, "+ "(select sum(pay.Amount) from Payment pay where pay.Sale_ID=s.Sale_ID) as sale_total "+ "from Employee e, Sale s, Customer c "+ "where e.Employee_ID=s.Employee_ID "+ "and c.Customer_ID=s.Customer_ID "+ "order by s.Sale_ID";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<EmployeeSales> list = FXCollections.observableArrayList();
        while (rs.next()) {
            int eid = rs.getInt("Employee_ID");
            String ename = rs.getString("First_Name") + " " + rs.getString("Last_Name");
            int saleId = rs.getInt("Sale_ID");
            String cname = rs.getString("CFirst") + " " + rs.getString("CLast");
            int productsSold = rs.getInt("products_sold");
            double saleTotal = rs.getDouble("sale_total");
            EmployeeSales es = new EmployeeSales(eid, ename, saleId, cname, productsSold, saleTotal);
            list.add(es);
        }
        employeeSalesTable.setItems(list);
        employeeSalesMessageLabel.setText("Loaded " + list.size() + " records");
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        employeeSalesMessageLabel.setText("Could not load employee sales");
    }
}

//search employee sales by employee name
@FXML
void searchEmployeeSales() {

    String searchText = employeeSalesSearchField.getText();
    if (searchText.isEmpty()) {
        employeeSalesMessageLabel.setText("Enter employee name to search"); 
        return; 
    }
    if (!searchText.matches(".*[a-zA-Z].*")) { 
        employeeSalesMessageLabel.setText("Search text must contain letters");
        return; 
    }

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "select e.Employee_ID, e.First_Name, e.Last_Name, s.Sale_ID, "+ "c.First_Name as CFirst, c.Last_Name as CLast, "+ "(select count(*) from Sale_Details sd where sd.Sale_ID=s.Sale_ID) as products_sold, "+ "(select sum(pay.Amount) from Payment pay where pay.Sale_ID=s.Sale_ID) as sale_total "+ "from Employee e, Sale s, Customer c "+ "where e.Employee_ID=s.Employee_ID "+ "and c.Customer_ID=s.Customer_ID "+ "and (e.First_Name like '%" + searchText + "%' or e.Last_Name like '%" + searchText + "%')";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<EmployeeSales> list = FXCollections.observableArrayList();
        while (rs.next()) {
            int eid = rs.getInt("Employee_ID");
            String ename = rs.getString("First_Name") + " " + rs.getString("Last_Name");
            int saleId = rs.getInt("Sale_ID");
            String cname = rs.getString("CFirst") + " " + rs.getString("CLast");
            int productsSold = rs.getInt("products_sold");
            double saleTotal = rs.getDouble("sale_total");
            EmployeeSales es = new EmployeeSales(eid, ename, saleId, cname, productsSold, saleTotal);
            list.add(es);
        }
        employeeSalesTable.setItems(list);
        rs.close();
        stmt.close();
        mycon.close();
        if (list.isEmpty()) {
            employeeSalesMessageLabel.setText("No sales found for this employee");
        } else {
            employeeSalesMessageLabel.setText("Found " + list.size() + " records");
        }
    } catch (Exception e) {
        employeeSalesMessageLabel.setText("Search failed");
    }
}

//show all employee purchase details
@FXML
void showEmployeePurchases() {

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        
        String sql = "select e.Employee_ID, e.First_Name, e.Last_Name, p.Purchase_ID, "+ "s.Contact_Name, "+ "(select count(*) from Purchase_Details pd where pd.Purchase_ID=p.Purchase_ID) as products_purchased, "+ "(select sum(pd.Unit_Price * pd.Quantity) from Purchase_Details pd where pd.Purchase_ID=p.Purchase_ID) as purchase_total "+ "from Employee e, Purchase p, Supplier s "+ "where e.Employee_ID=p.Employee_ID "+ "and s.Supplier_ID=p.Supplier_ID "+ "order by p.Purchase_ID";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<EmployeePurchase> list = FXCollections.observableArrayList();
        while (rs.next()) {
            int eid = rs.getInt("Employee_ID");
            String ename = rs.getString("First_Name") + " " + rs.getString("Last_Name");
            int purchaseId = rs.getInt("Purchase_ID");
            String sname = rs.getString("Contact_Name");
            int productsPurchased = rs.getInt("products_purchased");
            double purchaseTotal = rs.getDouble("purchase_total");
            EmployeePurchase ep = new EmployeePurchase(eid, ename, purchaseId, sname, productsPurchased, purchaseTotal);
            list.add(ep);
        }
        employeePurchaseTable.setItems(list);
        employeePurchaseMessageLabel.setText("Loaded " + list.size() + " records");
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        employeePurchaseMessageLabel.setText("Could not load employee purchases");
    }
}

//search employee purchases by employee name
@FXML
void searchEmployeePurchases() {

    String searchText = employeePurchaseSearchField.getText();
    if (searchText.isEmpty()) { 
        employeePurchaseMessageLabel.setText("Enter employee name to search"); 
        return;
    }
    if (!searchText.matches(".*[a-zA-Z].*")) { 
        employeePurchaseMessageLabel.setText("Search text must contain letters");
        return; 
    }

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "select e.Employee_ID, e.First_Name, e.Last_Name, p.Purchase_ID, "+ "s.Contact_Name, "+ "(select count(*) from Purchase_Details pd where pd.Purchase_ID=p.Purchase_ID) as products_purchased, "+ "(select sum(pd.Unit_Price * pd.Quantity) from Purchase_Details pd where pd.Purchase_ID=p.Purchase_ID) as purchase_total "+ "from Employee e, Purchase p, Supplier s "+ "where e.Employee_ID=p.Employee_ID "+ "and s.Supplier_ID=p.Supplier_ID "+ "and (e.First_Name like '%" + searchText + "%' or e.Last_Name like '%" + searchText + "%')";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<EmployeePurchase> list = FXCollections.observableArrayList();
        while (rs.next()) {
            int eid = rs.getInt("Employee_ID");
            String ename = rs.getString("First_Name") + " " + rs.getString("Last_Name");
            int purchaseId = rs.getInt("Purchase_ID");
            String sname = rs.getString("Contact_Name");
            int productsPurchased = rs.getInt("products_purchased");
            double purchaseTotal = rs.getDouble("purchase_total");
            EmployeePurchase ep = new EmployeePurchase(eid, ename, purchaseId, sname, productsPurchased, purchaseTotal);
            list.add(ep);
        }
        employeePurchaseTable.setItems(list);
        rs.close();
        stmt.close();
        mycon.close();
        if (list.isEmpty()) {
            employeePurchaseMessageLabel.setText("No purchases found for this employee");
        } else {
            employeePurchaseMessageLabel.setText("Found " + list.size() + " records");
        }
    } catch (Exception e) {
        employeePurchaseMessageLabel.setText("Search failed");
    }
}

//clear main employee fields
    @FXML
    void clearFields() {
        idField.clear();
        firstNameField.clear();
        lastNameField.clear();
        positionField.clear();
        salaryField.clear();
        supervisorIdField.clear();
        mainPhoneField.clear();
        searchField.clear();
        messageLabel.setText("");
    }

  //clear phone section fields
  @FXML
    void clearPhoneFields() {
        phoneEmployeeIdField.clear();
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