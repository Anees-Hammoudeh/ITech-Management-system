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
//imports for connecting controller to fxml and go between pages
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
// elements nneeded 
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
//imports for switching between pages
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class InventoryController implements Initializable {

    //top stat labels
  @FXML Label totalProductsLabel;
  @FXML Label totalUnitsLabel;
  @FXML Label totalSectionsLabel;
  @FXML Label lowStockLabel;
    //alert labels
    @FXML Label alertLowStockLabel;
    @FXML Label alertEmptyLabel;
    //stock overview search
   @FXML TextField searchField;
   @FXML ComboBox<String> searchTypeCombo;
   @FXML Label searchMessageLabel;
    //storage capacity search
   @FXML TextField capacitySearchField;
   @FXML Label capacityMessageLabel;
    //stock movements search
   @FXML TextField movementSearchField;
   @FXML ComboBox<String> movementSearchTypeCombo;
   @FXML Label movementMessageLabel;
    //transfer section
    @FXML ComboBox<String> transferProductCombo;
    @FXML ComboBox<String> transferFromCombo;
    @FXML ComboBox<String> transferToCombo;
    @FXML TextField transferQuantityField;
    @FXML Label transferMessageLabel;
    //main stock table
    @FXML TableView<InventoryStock> stockTable;
    @FXML TableColumn<InventoryStock, Integer> productIdColumn;
    @FXML TableColumn<InventoryStock, String> productNameColumn;
     @FXML TableColumn<InventoryStock, String> categoryColumn;
    @FXML TableColumn<InventoryStock, String> brandColumn;
    @FXML TableColumn<InventoryStock, Integer> stockColumn;
    //storage capacity table
    @FXML TableView<StorageCapacity> capacityTable;
    @FXML TableColumn<StorageCapacity, Integer> sectionIdColumn;
    @FXML TableColumn<StorageCapacity, String> sectionNameColumn;
    @FXML TableColumn<StorageCapacity, String> sectionTypeColumn;
    @FXML TableColumn<StorageCapacity, Integer> capacityColumn;
    @FXML TableColumn<StorageCapacity, Double> usedQuantityColumn;
    @FXML TableColumn<StorageCapacity, Double> capacityPercentColumn;
    //stock movements table
    @FXML TableView<StockMovement> movementTable;
    @FXML TableColumn<StockMovement, Integer> movementIdColumn;
    @FXML TableColumn<StockMovement, String> movementProductColumn;
    @FXML TableColumn<StockMovement, String> movementTypeColumn;
    @FXML TableColumn<StockMovement, Integer> movementQuantityColumn;
    @FXML TableColumn<StockMovement, String> sourceColumn;
   @FXML TableColumn<StockMovement, String> destinationColumn;
     @FXML TableColumn<StockMovement, String> movementDateColumn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         //link stock table columns to InventoryStock 
      productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
      productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
      categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
      brandColumn.setCellValueFactory(new PropertyValueFactory<>("brandName"));
       stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        //link storage capacity table columns
       sectionIdColumn.setCellValueFactory(new PropertyValueFactory<>("sectionId"));
       sectionNameColumn.setCellValueFactory(new PropertyValueFactory<>("sectionName"));
       sectionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("sectionType"));
       capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
       usedQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("usedQuantity"));
       capacityPercentColumn.setCellValueFactory(new PropertyValueFactory<>("capacityPercent"));
       //link movements table columns
        movementIdColumn.setCellValueFactory(new PropertyValueFactory<>("movementId"));
        movementProductColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        movementTypeColumn.setCellValueFactory(new PropertyValueFactory<>("movementType"));
        movementQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("sourceSection"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destinationSection"));
        movementDateColumn.setCellValueFactory(new PropertyValueFactory<>("movementDate"));
        //search options for stock overview
        searchTypeCombo.getItems().add("Product Name");
       searchTypeCombo.getItems().add("Category");
       searchTypeCombo.getItems().add("Brand");
       searchTypeCombo.setValue("Product Name");
        //search options for movements
        movementSearchTypeCombo.getItems().add("Product Name");
       movementSearchTypeCombo.getItems().add("Type");
       movementSearchTypeCombo.setValue("Product Name");

       showStatistics();
        showAlerts();
       showStock();
       showCapacity();
        showMovements();
       
        loadTransferCombos();
    }

//show the 4 cards at the top of the page
@FXML
void showStatistics() {

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        // total number of products in the store
        ResultSet rs1 = stmt.executeQuery("select count(*) as c from Product");
        if (rs1.next()) {
            int total = rs1.getInt("c");
            totalProductsLabel.setText("Total Products: " + total);
        }
        rs1.close();
        //total units across all storage sections added together
        ResultSet rs2 = stmt.executeQuery("select sum(Quantity) as q from Storage_Details");
        if (rs2.next()) {
            int units = rs2.getInt("q");
            totalUnitsLabel.setText("Total Units: " + units);
        }
        rs2.close();
        //how many storage sections exist
        ResultSet rs3 = stmt.executeQuery("select count(*) as c from Storage_Section");
        if (rs3.next()) {
            int sections = rs3.getInt("c");
            totalSectionsLabel.setText("Storage Sections: " + sections);
        }
        rs3.close();
        //used storage is counted from quantity times category unit size
         String usedSql = "select sum(sd.Quantity * c.Unit_Size) as u from Storage_Details sd, Product p, Category c where sd.Product_ID = p.Product_ID and p.Category_ID = c.Category_ID";
         ResultSet rs4 = stmt.executeQuery(usedSql);
           double used = 0;

              if (rs4.next()) {
             used = rs4.getDouble("u");
             }
              rs4.close();

           //get total storage capacity from all sections
            ResultSet rs5 = stmt.executeQuery("select sum(Capacity) as c from Storage_Section");
         double totalCapacity = 0;

             if (rs5.next()) {
               totalCapacity = rs5.getDouble("c");
             }
            rs5.close();
           lowStockLabel.setText("Used: " + (int)used + " / " + (int)totalCapacity);
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        totalProductsLabel.setText("Error loading");
    }
}
// show capacity and empty section alerts at the top of the page
@FXML
void showAlerts() {

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        //count sections that are over capacity 
       String sql1 = "select ss.Section_ID, ss.Capacity, sum(sd.Quantity * c.Unit_Size) as used_q from Storage_Section ss left join Storage_Details sd on ss.Section_ID = sd.Section_ID left join Product p on sd.Product_ID = p.Product_ID left join Category c on p.Category_ID = c.Category_ID group by ss.Section_ID, ss.Capacity having sum(sd.Quantity * c.Unit_Size) > ss.Capacity";
      ResultSet rs1 = stmt.executeQuery(sql1);
        int overCount = 0;
        while (rs1.next()) { overCount++; }
        rs1.close();
        // count sections that have no products stored in them at all
        String sql2 = "select count(*) as c from Storage_Section ss "+ "where ss.Section_ID not in (select Section_ID from Storage_Details where Quantity > 0)";
        ResultSet rs2 = stmt.executeQuery(sql2);
        int emptyCount = 0;
        if (rs2.next()) {
            emptyCount = rs2.getInt("c");
        }
        rs2.close();
        stmt.close();
        mycon.close();
        //the alert message 
        if (overCount > 0) {
            alertLowStockLabel.setText("! " + overCount + " section(s) are over capacity");
        } else {
            alertLowStockLabel.setText("All sections are within capacity");
        }
        if (emptyCount > 0) {
            alertEmptyLabel.setText("! " + emptyCount + " storage section(s) are empty");
        } else {
            alertEmptyLabel.setText("No empty sections");
        }
    } catch (Exception e) {
        alertLowStockLabel.setText("Error loading alerts");
    }
}
 //all products with their total stock
@FXML
void showStock() {

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "select p.Product_ID, p.Product_Name, c.Category_Name, b.Brand_Name, sum(sd.Quantity) as stock "+ "from Product p, Category c, Brand b, Storage_Details sd "+ "where p.Category_ID = c.Category_ID "+ "and p.Brand_ID = b.Brand_ID "+ "and p.Product_ID = sd.Product_ID "+ "group by p.Product_ID, p.Product_Name, c.Category_Name, b.Brand_Name "+ "order by p.Product_ID";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<InventoryStock> list = FXCollections.observableArrayList();
        while (rs.next()) {
            int id = rs.getInt("Product_ID");
            String name = rs.getString("Product_Name");
            String cat = rs.getString("Category_Name");
            String brand = rs.getString("Brand_Name");
            int qty = rs.getInt("stock");
            InventoryStock s = new InventoryStock(id, name, cat, brand, qty);
            list.add(s);
        }
        stockTable.setItems(list);
        if (list.isEmpty()) {
            searchMessageLabel.setText("No stock records found");
        } else {
            searchMessageLabel.setText("Showing all stock");
        }
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        searchMessageLabel.setText("Could not load stock");
    }
}
 //search stock by product name category or brand depending on combobox
@FXML
void searchInventory() {

    String searchText = searchField.getText();
    if (searchText == null || searchText.trim().isEmpty()) {
        searchMessageLabel.setText("Please enter something to search");
        return;
    }
    searchText = searchText.trim().replace("'", "''");
    if (searchText.length() > 50) {
        searchMessageLabel.setText("Search value is too long");
        return;
    }
    String selectedType = searchTypeCombo.getValue();
    if (selectedType == null) {
        searchMessageLabel.setText("Please choose search type");
        return;
    }
    String sql = "";
    if (selectedType.equals("Product Name")) {
        sql = "select p.Product_ID, p.Product_Name, c.Category_Name, b.Brand_Name, sum(sd.Quantity) as stock "+ "from Product p, Category c, Brand b, Storage_Details sd "+ "where p.Category_ID = c.Category_ID and p.Brand_ID = b.Brand_ID and p.Product_ID = sd.Product_ID "+ "and p.Product_Name like '%" + searchText + "%' "+ "group by p.Product_ID, p.Product_Name, c.Category_Name, b.Brand_Name";
    } else if (selectedType.equals("Category")) {
        sql = "select p.Product_ID, p.Product_Name, c.Category_Name, b.Brand_Name, sum(sd.Quantity) as stock "+ "from Product p, Category c, Brand b, Storage_Details sd "+ "where p.Category_ID = c.Category_ID and p.Brand_ID = b.Brand_ID and p.Product_ID = sd.Product_ID "+ "and c.Category_Name like '%" + searchText + "%' "+ "group by p.Product_ID, p.Product_Name, c.Category_Name, b.Brand_Name";
    } else if (selectedType.equals("Brand")) {
        sql = "select p.Product_ID, p.Product_Name, c.Category_Name, b.Brand_Name, sum(sd.Quantity) as stock "+ "from Product p, Category c, Brand b, Storage_Details sd "+ "where p.Category_ID = c.Category_ID and p.Brand_ID = b.Brand_ID and p.Product_ID = sd.Product_ID "+ "and b.Brand_Name like '%" + searchText + "%' "+ "group by p.Product_ID, p.Product_Name, c.Category_Name, b.Brand_Name";
    } else {
        searchMessageLabel.setText("Wrong search type");
        return;
    }
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<InventoryStock> list = FXCollections.observableArrayList();
        while (rs.next()) {
            int id = rs.getInt("Product_ID");
            String name = rs.getString("Product_Name");
            String cat = rs.getString("Category_Name");
            String brand = rs.getString("Brand_Name");
            int qty = rs.getInt("stock");
            InventoryStock s = new InventoryStock(id, name, cat, brand, qty);
            list.add(s);
        }
        stockTable.setItems(list);
        rs.close();
        stmt.close();
        mycon.close();
        if (list.isEmpty()) {
            searchMessageLabel.setText("No results found");
        } else {
            searchMessageLabel.setText("Found " + list.size() + " result(s)");
        }
    } catch (Exception e) {
        searchMessageLabel.setText("Search failed");
    }
}
//all storage sections with how much of their capacity is used
@FXML
void showCapacity() {

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "select ss.Section_ID, ss.Section_Name, ss.Section_Type, ss.Capacity, sum(sd.Quantity * c.Unit_Size) as used_q "+ "from Storage_Section ss left join Storage_Details sd on ss.Section_ID = sd.Section_ID left join Product p on sd.Product_ID = p.Product_ID left join Category c on p.Category_ID = c.Category_ID "+ "group by ss.Section_ID, ss.Section_Name, ss.Section_Type, ss.Capacity "+ "order by ss.Section_ID";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<StorageCapacity> list = FXCollections.observableArrayList();
        while (rs.next()) {
            int secId = rs.getInt("Section_ID");
            String secName = rs.getString("Section_Name");
            String secType = rs.getString("Section_Type");
            int cap = rs.getInt("Capacity");
            double used = rs.getDouble("used_q");
            if (rs.wasNull()) {
                used = 0;
            }
            //calculate percentage
            double percent = 0;
            if (cap > 0) {
                percent = (used * 100.0) / cap;
            }
            StorageCapacity c = new StorageCapacity(secId, secName, secType, cap, used, percent);
            list.add(c);
        }
        capacityTable.setItems(list);
        if (list.isEmpty()) {
            capacityMessageLabel.setText("No storage sections found");
        } else {
            capacityMessageLabel.setText("Showing all sections");
        }
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        capacityMessageLabel.setText("Could not load capacity");
    }
}

//search storage sections by section name
@FXML
void searchCapacity() {
    String searchText = capacitySearchField.getText();
    if (searchText == null || searchText.trim().isEmpty()) {
        capacityMessageLabel.setText("Enter section name to search");
        return;
    }
    searchText = searchText.trim().replace("'", "''");
    if (searchText.length() > 50) {
        capacityMessageLabel.setText("Search value is too long");
        return;
    }
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "select ss.Section_ID, ss.Section_Name, ss.Section_Type, ss.Capacity, sum(sd.Quantity * c.Unit_Size) as used_q "+ "from Storage_Section ss left join Storage_Details sd on ss.Section_ID = sd.Section_ID left join Product p on sd.Product_ID = p.Product_ID left join Category c on p.Category_ID = c.Category_ID "+ "where ss.Section_Name like '%" + searchText + "%' "+ "group by ss.Section_ID, ss.Section_Name, ss.Section_Type, ss.Capacity " + "order by ss.Section_ID";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<StorageCapacity> list = FXCollections.observableArrayList();
        while (rs.next()) {
            int secId = rs.getInt("Section_ID");
            String secName = rs.getString("Section_Name");
            String secType = rs.getString("Section_Type");
            int cap = rs.getInt("Capacity");
            double used = rs.getDouble("used_q");
            if (rs.wasNull()) {
                used = 0;
            }
            double percent = 0;
            if (cap > 0) {
                percent = (used * 100.0) / cap;
            }
            StorageCapacity c = new StorageCapacity(secId, secName, secType, cap, used, percent);
            list.add(c);
        }
        capacityTable.setItems(list);
        rs.close();
        stmt.close();
        mycon.close();
        if (list.isEmpty()) {
            capacityMessageLabel.setText("No sections found with this name");
        } else {
            capacityMessageLabel.setText("Found " + list.size() + " result(s)");
        }
    } catch (Exception e) {
        capacityMessageLabel.setText("Search failed");
    }
}

//clear the capacity search
@FXML
void clearCapacitySearch() {
    capacitySearchField.clear();
    capacityMessageLabel.setText("");
    showCapacity();
}

//all stock movement history records
@FXML
void showMovements() {

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "select sm.Movement_ID, p.Product_Name, sm.Movement_Type, sm.Quantity, "+ "s1.Section_Name as source_name, s2.Section_Name as dest_name, sm.Movement_Date "+ "from Stock_Movement_History sm "+ "join Product p on sm.Product_ID = p.Product_ID "+ "left join Storage_Section s1 on sm.Source_Section_ID = s1.Section_ID "+ "left join Storage_Section s2 on sm.Destination_Section_ID = s2.Section_ID "+ "order by sm.Movement_ID";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<StockMovement> list = FXCollections.observableArrayList();
        while (rs.next()) {
            int mId = rs.getInt("Movement_ID");
            String pName = rs.getString("Product_Name");
            String mType = rs.getString("Movement_Type");
            int qty = rs.getInt("Quantity");

            //replace null with a dash so the table looks good
            String src = rs.getString("source_name");
            String dst = rs.getString("dest_name");
            if (src == null) { src = "-"; }
            if (dst == null) { dst = "-"; }
            String mDate = rs.getString("Movement_Date");
            StockMovement m = new StockMovement(mId, pName, mType, qty, src, dst, mDate);
            list.add(m);
        }
        movementTable.setItems(list);
        if (list.isEmpty()) {
            movementMessageLabel.setText("No movements found");
        } else {
            movementMessageLabel.setText("Showing all movements");
        }
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        movementMessageLabel.setText("Could not load movements");
    }
}

//search movements by product name or movement type
@FXML
void searchMovements() {

    String searchText = movementSearchField.getText();
    if (searchText == null || searchText.trim().isEmpty()) {
        movementMessageLabel.setText("Enter value to search");
        return;
    }
    searchText = searchText.trim().replace("'", "''");
    if (searchText.length() > 50) {
        movementMessageLabel.setText("Search value is too long");
        return;
    }
    String selectedType = movementSearchTypeCombo.getValue();
    if (selectedType == null) {
        movementMessageLabel.setText("Please choose search type");
        return;
    }
    String sql = "";
    if (selectedType.equals("Product Name")) {
        sql = "select sm.Movement_ID, p.Product_Name, sm.Movement_Type, sm.Quantity, "+ "s1.Section_Name as source_name, s2.Section_Name as dest_name, sm.Movement_Date "+ "from Stock_Movement_History sm "+ "join Product p on sm.Product_ID = p.Product_ID "+ "left join Storage_Section s1 on sm.Source_Section_ID = s1.Section_ID "+ "left join Storage_Section s2 on sm.Destination_Section_ID = s2.Section_ID "+ "where p.Product_Name like '%" + searchText + "%' "+ "order by sm.Movement_ID";
    } else if (selectedType.equals("Type")) {
        sql = "select sm.Movement_ID, p.Product_Name, sm.Movement_Type, sm.Quantity, "+ "s1.Section_Name as source_name, s2.Section_Name as dest_name, sm.Movement_Date "+ "from Stock_Movement_History sm "+ "join Product p on sm.Product_ID = p.Product_ID "+ "left join Storage_Section s1 on sm.Source_Section_ID = s1.Section_ID "+ "left join Storage_Section s2 on sm.Destination_Section_ID = s2.Section_ID "+ "where sm.Movement_Type like '%" + searchText + "%' "+ "order by sm.Movement_ID";
    } else {
        
        return;
    }
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<StockMovement> list = FXCollections.observableArrayList();
        while (rs.next()) {
            int mId = rs.getInt("Movement_ID");
            String pName = rs.getString("Product_Name");
            String mType = rs.getString("Movement_Type");
            int qty = rs.getInt("Quantity");
            String src = rs.getString("source_name");
            String dst = rs.getString("dest_name");
            if (src == null) { src = "-"; }
            if (dst == null) { dst = "-"; }
            String mDate = rs.getString("Movement_Date");
            StockMovement m = new StockMovement(mId, pName, mType, qty, src, dst, mDate);
            list.add(m);
        }
        movementTable.setItems(list);
        rs.close();
        stmt.close();
        mycon.close();
        if (list.isEmpty()) {
            movementMessageLabel.setText("No movements found");
        } else {
            movementMessageLabel.setText("Found " + list.size() + " result(s)");
        }
    } catch (Exception e) {
        movementMessageLabel.setText("Search failed");
    }
}

//clea
@FXML
void clearMovementSearch() {
    movementSearchField.clear();
    movementMessageLabel.setText("");
    showMovements();
}
    //clear 
    @FXML
    void clearSearch() {
        searchField.clear();
        searchMessageLabel.setText("");
        showStock();
    }

    //for pages not done yet
    @FXML
    void notReady() {
        searchMessageLabel.setText("This section is not ready yet");
    }

    //load products and sections into transfer combos
    void loadTransferCombos() {
        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();
            //load products that exist in storage
            ResultSet rs1 = stmt.executeQuery("select distinct p.Product_ID, p.Product_Name from Product p, Storage_Details sd where p.Product_ID = sd.Product_ID and sd.Quantity > 0 order by p.Product_Name");
            transferProductCombo.getItems().clear();
            while (rs1.next()) { transferProductCombo.getItems().add(rs1.getInt("Product_ID") + " - " + rs1.getString("Product_Name")); }
            rs1.close();
            //load all sections for from and to combos
            ResultSet rs2 = stmt.executeQuery("select Section_ID, Section_Name from Storage_Section order by Section_Name");
            ObservableList<String> sections = FXCollections.observableArrayList();
            while (rs2.next()) { sections.add(rs2.getInt("Section_ID") + " - " + rs2.getString("Section_Name")); }
            rs2.close();
            transferFromCombo.setItems(FXCollections.observableArrayList(sections));
            transferToCombo.setItems(FXCollections.observableArrayList(sections));
            stmt.close();
            mycon.close();
        } catch (Exception e) {
            transferMessageLabel.setText("Could not load transfer data");
        }
    }

    //do the transfer between two sections
    @FXML
    void doTransfer() {
        //validate inputs
        if (transferProductCombo.getValue() == null) {
            transferMessageLabel.setText("Please select a product");
            return; 
        }
        if (transferFromCombo.getValue() == null) {
            transferMessageLabel.setText("Please select source section");
            return; 
        }
        if (transferToCombo.getValue() == null) {
            transferMessageLabel.setText("Please select destination section"); 
            return; 
        }
        if (transferQuantityField.getText() == null || transferQuantityField.getText().trim().isEmpty()) {
            transferMessageLabel.setText("Please enter quantity"); 
            return;
        }
        //parse IDs from combo values ("ID - Name")
        int productId = Integer.parseInt(transferProductCombo.getValue().split(" - ")[0].trim());
        int fromId = Integer.parseInt(transferFromCombo.getValue().split(" - ")[0].trim());
        int toId = Integer.parseInt(transferToCombo.getValue().split(" - ")[0].trim());
        //check source and destination are not the same
        if (fromId == toId) {
            transferMessageLabel.setText("Source and destination cannot be the same"); 
            return;
        }
        int qty;
        try {
            qty = Integer.parseInt(transferQuantityField.getText().trim());
            if (qty <= 0) {
                transferMessageLabel.setText("Quantity must be greater than zero");
                return; 
            }
        } catch (NumberFormatException e) {
            transferMessageLabel.setText("Quantity must be a number");
            return;
        }
        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();
            //check if product exists in source section with enough quantity
            ResultSet rs1 = stmt.executeQuery("select Quantity from Storage_Details where Product_ID = " + productId + " and Section_ID = " + fromId);
            if (!rs1.next()) {
                transferMessageLabel.setText("Product not found in selected source section"); rs1.close(); stmt.close(); mycon.close(); 
                return; 
            }
            int availableQty = rs1.getInt("Quantity");
            rs1.close();
            if (availableQty < qty) {
                transferMessageLabel.setText("Not enough quantity in source section. Available: " + availableQty); stmt.close(); mycon.close();
                return; 
            }
            //check destination capacity before transferring
            ResultSet rs2 = stmt.executeQuery("select ss.Capacity, sum(sd.Quantity * c.Unit_Size) as used_q from Storage_Section ss left join Storage_Details sd on ss.Section_ID = sd.Section_ID left join Product p on sd.Product_ID = p.Product_ID left join Category c on p.Category_ID = c.Category_ID where ss.Section_ID = " + toId + " group by ss.Section_ID, ss.Capacity");
            double usedDest = 0;
            int capDest = 0;
            if (rs2.next()) { 
                capDest = rs2.getInt("Capacity");
                usedDest = rs2.getDouble("used_q");
            }
            rs2.close();
            //get unit size of the product being transferred
            ResultSet rs3 = stmt.executeQuery("select c.Unit_Size from Product p, Category c where p.Category_ID = c.Category_ID and p.Product_ID = " + productId);
            int unitSize = 1;
            if (rs3.next()) {
                unitSize = rs3.getInt("Unit_Size");
            }
            rs3.close();
            //check if destination has enough space
            double spaceNeeded = qty * unitSize;
            if (usedDest + spaceNeeded > capDest) {
                transferMessageLabel.setText("Not enough capacity in destination section. Available space: " + (int)(capDest - usedDest));
                stmt.close();
                mycon.close(); 
                return; 
            }
            //decrease quantity in source section
            stmt.executeUpdate("update Storage_Details set Quantity = Quantity - " + qty + " where Product_ID = " + productId + " and Section_ID = " + fromId);
            //check if product already exists in destination section
            ResultSet rs4 = stmt.executeQuery("select Quantity from Storage_Details where Product_ID = " + productId + " and Section_ID = " + toId);
            if (rs4.next()) {
                //if product exists in destination then just increase quantity
                stmt.executeUpdate("update Storage_Details set Quantity = Quantity + " + qty + " where Product_ID = " + productId + " and Section_ID = " + toId);
            } else {
                //if product not in destination yet then insert new row
                stmt.executeUpdate("insert into Storage_Details (Product_ID, Section_ID, Quantity) values (" + productId + ", " + toId + ", " + qty + ")");
            }
            rs4.close();
            //record the transfer in stock movement history
            stmt.executeUpdate("insert into Stock_Movement_History (Product_ID, Source_Section_ID, Destination_Section_ID, Movement_Type, Quantity, Movement_Date) values (" + productId + ", " + fromId + ", " + toId + ", 'Transfer', " + qty + ", CURDATE())");
            stmt.close();
            mycon.close();
            transferMessageLabel.setText("Transfer completed successfully");
            //refresh all tables and combos
            transferQuantityField.clear();
            transferProductCombo.setValue(null);
            transferFromCombo.setValue(null);
            transferToCombo.setValue(null);
            loadTransferCombos();
            showStock();
            showCapacity();
            showMovements();
            showStatistics();
            showAlerts();
        } catch (Exception e) {
            transferMessageLabel.setText("Transfer failed");
        }
    }

    //clear transfer form
    @FXML
    void clearTransfer() {
        transferProductCombo.setValue(null);
        transferFromCombo.setValue(null);
        transferToCombo.setValue(null);
        transferQuantityField.clear();
        transferMessageLabel.setText("");
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
            searchMessageLabel.setText("Cannot open products page");
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
            searchMessageLabel.setText("Cannot open customers page");
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
            searchMessageLabel.setText("Cannot open suppliers page");
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
            searchMessageLabel.setText("Cannot open employees page");
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
        searchMessageLabel.setText("Cannot open reports page");
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
        searchMessageLabel.setText("Cannot open dashboard page");
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
        searchMessageLabel.setText("Cannot open sales page");
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
        searchMessageLabel.setText("Cannot open purchases page");
    }
}

}