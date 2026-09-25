package com.mycompany.itech_management_system;

import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class PurchaseController {

    // purchase table
    @FXML private TableView<PurchasePage> purchaseTable;
    @FXML private TableColumn<PurchasePage, Integer> purchaseIdCol;
    @FXML private TableColumn<PurchasePage, String> supplierCol;
    @FXML private TableColumn<PurchasePage, String> employeeCol;
    @FXML private TableColumn<PurchasePage, String> purchaseDateCol;
    @FXML private TableColumn<PurchasePage, String> deliveryDateCol;
    @FXML private TableColumn<PurchasePage, Double> totalCol;
    @FXML private TableColumn<PurchasePage, String> statusCol;

    // purchase detail table
    @FXML private TableView<PurchaseDetail> detailTable;
    @FXML private TableColumn<PurchaseDetail, Integer> detailPurchaseIdCol;
    @FXML private TableColumn<PurchaseDetail, Integer> detailProductIdCol;
    @FXML private TableColumn<PurchaseDetail, String> detailProductCol;
    @FXML private TableColumn<PurchaseDetail, String> detailSectionCol;
    @FXML private TableColumn<PurchaseDetail, Integer> detailQuantityCol;
    @FXML private TableColumn<PurchaseDetail, Double> detailUnitPriceCol;
    @FXML private TableColumn<PurchaseDetail, Double> detailLineTotalCol;

    // input fields
    @FXML private ComboBox<String> supplierBox;
    @FXML private ComboBox<String> employeeBox;
    @FXML private ComboBox<String> productBox;
    @FXML private ComboBox<String> sectionBox;
    @FXML private DatePicker purchaseDatePicker;
    @FXML private DatePicker deliveryDatePicker;
    @FXML private TextField quantityField;
    @FXML private TextField unitPriceField;
    @FXML private TextField searchField;

    // labels
    @FXML private Label totalPurchasesLabel;
    @FXML private Label costLabel;
    @FXML private Label itemsLabel;
    @FXML private Label messageLabel;
    @FXML private Label currentPurchaseLabel;
    @FXML private Label detailsTitleLabel;
    @FXML private Label sectionCapacityLabel;

    // keeps current purchase ID while adding items to it
    private int currentPurchaseId = 0;

    // stores selected section for each product in a purchase (key = "purchaseId-productId")
    private Map<String, String> purchaseSections = new HashMap<>();
    private ObservableList<PurchasePage> list = FXCollections.observableArrayList();
    private ObservableList<PurchaseDetail> detailList = FXCollections.observableArrayList();

    public void initialize() {

        //  purchase table
        purchaseIdCol.setCellValueFactory(new PropertyValueFactory("purchaseId"));
        supplierCol.setCellValueFactory(new PropertyValueFactory("supplier"));
        employeeCol.setCellValueFactory(new PropertyValueFactory("employee"));
        purchaseDateCol.setCellValueFactory(new PropertyValueFactory("purchaseDate"));
        deliveryDateCol.setCellValueFactory(new PropertyValueFactory("deliveryDate"));
        statusCol.setCellValueFactory(new PropertyValueFactory("status"));
        totalCol.setCellValueFactory(new PropertyValueFactory("total"));
        purchaseTable.setItems(list);

        // detail table
        detailPurchaseIdCol.setCellValueFactory(new PropertyValueFactory("purchaseId"));
        detailProductIdCol.setCellValueFactory(new PropertyValueFactory("productId"));
        detailProductCol.setCellValueFactory(new PropertyValueFactory("productName"));
        detailSectionCol.setCellValueFactory(new PropertyValueFactory("sectionName"));
        detailQuantityCol.setCellValueFactory(new PropertyValueFactory("quantity"));
        detailUnitPriceCol.setCellValueFactory(new PropertyValueFactory("unitPrice"));
        detailLineTotalCol.setCellValueFactory(new PropertyValueFactory("lineTotal"));
        detailTable.setItems(detailList);

        // cannot select future dates for purchase date
        purchaseDatePicker.setDayCellFactory(dp -> new DateCell() {
            @Override public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isAfter(LocalDate.now())) { setDisable(true); }
            }
        });

        deliveryDatePicker.setDayCellFactory(dp -> new DateCell() {
            @Override public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (purchaseDatePicker.getValue() != null && item.isBefore(purchaseDatePicker.getValue())) {
                    setDisable(true);
                }
            }
        });

        // when user clicks a purchase row show its items
        purchaseTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                currentPurchaseId = newValue.getPurchaseId();
                currentPurchaseLabel.setText("Current Purchase ID: " + currentPurchaseId);
                detailsTitleLabel.setText("Items for Purchase ID: " + currentPurchaseId);
                loadPurchaseDetails(currentPurchaseId);
            }
        });

        // when user clicks a detail row, fill the product and quantity fields
        detailTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, item) -> {
            if (item != null) {
                // find the matching product in the combo
                for (String product : productBox.getItems()) {
                    if (product.startsWith(item.getProductId() + " - ")) {
                        productBox.setValue(product);
                        break;
                    }
                }
                quantityField.setText(String.valueOf(item.getQuantity()));
                unitPriceField.setText(String.valueOf(item.getUnitPrice()));
                // reload sections then select the stored section for this item
                loadAvailableSections();
                selectStoredSection(item.getPurchaseId(), item.getProductId());
            }
        });

        // reload sections when product or quantity changes
        productBox.valueProperty().addListener((obs, oldValue, newValue) -> loadAvailableSections());
        quantityField.textProperty().addListener((obs, oldValue, newValue) -> loadAvailableSections());

        // load combos and data
        loadSuppliers();
        loadEmployees();
        loadProducts();
        loadPurchases();
    }

    // get the numeric ID from a combo item like "3 - Name"
    private int getId(String text) {
        return Integer.parseInt(text.substring(0, text.indexOf(" - ")));
    }

    // fill supplier combo from database
    private void loadSuppliers() {
        supplierBox.getItems().clear();
        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();
            ResultSet rs = stmt.executeQuery("select Supplier_ID, Company_Name from Supplier order by Supplier_ID");
            while (rs.next()) {
                supplierBox.getItems().add(rs.getInt("Supplier_ID") + " - " + rs.getString("Company_Name"));
            }
            rs.close();
            stmt.close();
            mycon.close();
        } catch (Exception e) {
            messageLabel.setText("Could not load suppliers");
        }
    }

    // fill employee combo from database
    private void loadEmployees() {
        employeeBox.getItems().clear();
        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();
            ResultSet rs = stmt.executeQuery("select Employee_ID, First_Name, Last_Name from Employee order by Employee_ID");
            while (rs.next()) {
                employeeBox.getItems().add(rs.getInt("Employee_ID") + " - " + rs.getString("First_Name") + " " + rs.getString("Last_Name"));
            }
            rs.close();
            stmt.close();
            mycon.close();
        } catch (Exception e) {
            messageLabel.setText("Could not load employees");
        }
    }

    // fill product combo from database
    private void loadProducts() {
        productBox.getItems().clear();
        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();
            ResultSet rs = stmt.executeQuery("select Product_ID, Product_Name from Product order by Product_ID");
            while (rs.next()) {
                productBox.getItems().add(rs.getInt("Product_ID") + " - " + rs.getString("Product_Name"));
            }
            rs.close();
            stmt.close();
            mycon.close();
        } catch (Exception e) {
            messageLabel.setText("Could not load products");
        }
    }

    // reload section combo showing only sections with enough free space
    private void loadAvailableSections() {
        sectionBox.getItems().clear();
        sectionBox.setValue(null);

        if (productBox.getValue() == null || quantityField.getText().isEmpty()) {
            sectionCapacityLabel.setText("Choose product and enter quantity");
            return;
        }

        try {
            int productId = getId(productBox.getValue());
            int quantity = Integer.parseInt(quantityField.getText());
            if (quantity <= 0) return;

            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();

            // get unit size for this product from its category
            ResultSet sizeRs = stmt.executeQuery("select c.Unit_Size from Product p, Category c " +"where p.Category_ID = c.Category_ID and p.Product_ID = " + productId);
            sizeRs.next();
            int unitSize = sizeRs.getInt("Unit_Size");
            sizeRs.close();

            // get each section with how much space is already used
            String sql = "select ss.Section_ID, ss.Section_Name, ss.Capacity, " +
                         "sum(sd.Quantity * c.Unit_Size) as UsedCapacity " +"from Storage_Section ss " +
                         "left join Storage_Details sd on ss.Section_ID = sd.Section_ID " +
                        "left join Product p on sd.Product_ID = p.Product_ID " +"left join Category c on p.Category_ID = c.Category_ID " +
                         "group by ss.Section_ID, ss.Section_Name, ss.Capacity " +"order by ss.Section_ID";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                double usedCapacity = rs.getDouble("UsedCapacity");
                if (rs.wasNull()) { usedCapacity = 0; }
                double free = rs.getDouble("Capacity") - usedCapacity;
                // only add section if it fits the quantity
                if (free >= quantity * unitSize) {
                    int availableUnits = (int) (free / unitSize);
                    sectionBox.getItems().add(
                        rs.getInt("Section_ID") + " - " + rs.getString("Section_Name") + " (available: " + availableUnits + " units)"
                    );
                }
            }
            rs.close();
            stmt.close();
            mycon.close();

            if (sectionBox.getItems().isEmpty()) { sectionCapacityLabel.setText("No section has enough capacity"); }
            else { sectionCapacityLabel.setText("Choose one of the available sections"); }

        } catch (Exception e) {
            sectionCapacityLabel.setText("Enter a valid quantity");
        }
    }

    // select the previously stored section for an item in the combo
    private void selectStoredSection(int purchaseId, int productId) {
        String stored = purchaseSections.get(purchaseId + "-" + productId);
        if (stored != null && sectionBox.getItems().contains(stored)) {
            sectionBox.setValue(stored);
        }
    }

    // load all purchases into the purchase table
    @FXML
    private void loadPurchases() {
        list.clear();
        int count = 0;
        int items = 0;
        double cost = 0;

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt     = mycon.createStatement(); // outer loop
            Statement stmtCalc = mycon.createStatement(); // inner total query

            // join purchase with supplier and employee for names
            String sql = "select p.Purchase_ID, s.Company_Name, e.First_Name, e.Last_Name, p.Purchase_Date, p.Delivery_Date, p.Purchase_Status " +
                        "from Purchase p, Supplier s, Employee e " +
                      "where p.Supplier_ID = s.Supplier_ID and p.Employee_ID = e.Employee_ID " +
                       "order by p.Purchase_ID desc";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int purchaseId = rs.getInt("Purchase_ID");
                String supplier = rs.getString("Company_Name");
                String employee = rs.getString("First_Name") + " " + rs.getString("Last_Name");
                String purchaseDate = rs.getString("Purchase_Date");
                String deliveryDate = rs.getString("Delivery_Date");
                String status = rs.getString("Purchase_Status");

                // use stmtCalc so it does not close the outer rs on stmt
                ResultSet totRs = stmtCalc.executeQuery("select sum(Quantity * Unit_Price) as Total, sum(Quantity) as Items from Purchase_Details where Purchase_ID = " + purchaseId);
                double total = 0;
                int itemCount = 0;
                if (totRs.next()) {
                    total = totRs.getDouble("Total");
                    if (totRs.wasNull()) { total = 0; }
                    itemCount = totRs.getInt("Items");
                    if (totRs.wasNull()) { itemCount = 0; }
                }
                totRs.close();

                list.add(new PurchasePage(purchaseId, supplier, employee, purchaseDate, deliveryDate, status, total));
                count++;
                cost += total;
                items += itemCount;
            }

            totalPurchasesLabel.setText(String.valueOf(count));
            costLabel.setText(String.format("%.2f", cost));
            itemsLabel.setText(String.valueOf(items));

            rs.close();
            stmtCalc.close();
            stmt.close();
            mycon.close();

        } catch (Exception e) {
            messageLabel.setText("Could not load purchases");
        }
    }

    // load items of a specific purchase into the detail table
    private void loadPurchaseDetails(int purchaseId) {
        detailList.clear();
        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();

            // join with Product to show name
            String sql = "select pd.Purchase_ID, pd.Product_ID, p.Product_Name, pd.Quantity, pd.Unit_Price, (pd.Quantity * pd.Unit_Price) as Line_Total " +
                          "from Purchase_Details pd, Product p " +
                         "where pd.Product_ID = p.Product_ID and pd.Purchase_ID = " + purchaseId +
                         " order by p.Product_Name";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // get stored section for this item or show "Not assigned"
                String selectedSection = purchaseSections.get(purchaseId + "-" + rs.getInt("Product_ID"));
                if (selectedSection == null) { selectedSection = "Not assigned"; }
                detailList.add(new PurchaseDetail(rs.getInt("Purchase_ID"),rs.getInt("Product_ID"),
                    rs.getString("Product_Name"),selectedSection,
                    rs.getInt("Quantity"),rs.getDouble("Unit_Price"),
                    rs.getDouble("Line_Total")));
            }
            rs.close();
            stmt.close();
            mycon.close();
        } catch (Exception e) {
            messageLabel.setText("Could not load purchase details");
        }
    }

    // create a new purchase record
    @FXML
    private void createPurchase() {
        if (supplierBox.getValue() == null || employeeBox.getValue() == null || purchaseDatePicker.getValue() == null) {
            messageLabel.setText("Choose supplier, employee and purchase date");
            return;
        }
        if (deliveryDatePicker.getValue() != null && deliveryDatePicker.getValue().isBefore(purchaseDatePicker.getValue())) {
            messageLabel.setText("Delivery date cannot be before purchase date");
            return;
        }

        int supplierId = getId(supplierBox.getValue());
        int employeeId = getId(employeeBox.getValue());
        String purchaseDate = purchaseDatePicker.getValue().toString();
        String deliveryDate = deliveryDatePicker.getValue() != null ? "'" + deliveryDatePicker.getValue().toString() + "'" : "null";

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();

            stmt.executeUpdate(
                "insert into Purchase(Employee_ID, Supplier_ID, Purchase_Date, Delivery_Date) values(" +
                employeeId + "," + supplierId + ",'" + purchaseDate + "'," + deliveryDate + ")"
            );

            // get the auto generated ID of the new purchase
            ResultSet keys = stmt.executeQuery("select last_insert_id() as NewId");
            keys.next();
            currentPurchaseId = keys.getInt("NewId");
            keys.close();

            stmt.close();
            mycon.close();

            currentPurchaseLabel.setText("Current Purchase ID: " + currentPurchaseId);
            detailsTitleLabel.setText("Items for Purchase ID: " + currentPurchaseId);
            detailList.clear();
            loadPurchases();
            messageLabel.setText("Purchase created. Now add items.");

        } catch (Exception e) {
            messageLabel.setText("Could not create purchase");
        }
    }

    // add or update an item in the current purchase
    @FXML
    private void addItem() {
        if (currentPurchaseId == 0 || productBox.getValue() == null || sectionBox.getValue() == null
                || quantityField.getText().isEmpty() || unitPriceField.getText().isEmpty()) {
            messageLabel.setText("Fill all item fields and select a purchase first");
            return;
        }

        int productId = getId(productBox.getValue());
        int sectionId = getId(sectionBox.getValue());
        int quantity;
        double price;

        try {
            quantity = Integer.parseInt(quantityField.getText());
            price = Double.parseDouble(unitPriceField.getText());
        } catch (NumberFormatException e) {
            messageLabel.setText("Quantity and price must be numbers");
            return;
        }

        if (quantity <= 0 || price < 0) {
            messageLabel.setText("Quantity must be positive and price cannot be negative");
            return;
        }

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();

            // only pending purchases can be edited
            ResultSet statusRs = stmt.executeQuery("select Purchase_Status from Purchase where Purchase_ID = " + currentPurchaseId);
            if (!statusRs.next() || !statusRs.getString("Purchase_Status").equals("Pending")) {
                statusRs.close(); stmt.close(); mycon.close();
                messageLabel.setText("Items can only be saved in a pending purchase");
                return;
            }
            statusRs.close();

            // check the section has enough capacity
            if (!sectionHasSpace(stmt, sectionId, productId, quantity)) {
                stmt.close(); mycon.close();
                messageLabel.setText("Selected section does not have enough capacity");
                return;
            }

            // check if this product is already in this purchase
            ResultSet itemRs = stmt.executeQuery(
                "select Product_ID from Purchase_Details where Purchase_ID = " + currentPurchaseId + " and Product_ID = " + productId
            );
            boolean exists = itemRs.next();
            itemRs.close();

            if (exists) {
                // update if already added
                stmt.executeUpdate(
                    "update Purchase_Details set Unit_Price = " + price + ", Quantity = " + quantity +
                    " where Purchase_ID = " + currentPurchaseId + " and Product_ID = " + productId
                );
            } else {
                // insert new item
                stmt.executeUpdate(
                    "insert into Purchase_Details(Purchase_ID, Product_ID, Unit_Price, Quantity) values(" +
                    currentPurchaseId + "," + productId + "," + price + "," + quantity + ")"
                );
            }

            stmt.close();
            mycon.close();

            // save the chosen section for this product
            purchaseSections.put(currentPurchaseId + "-" + productId, sectionBox.getValue());

            clearItemFields();
            loadPurchases();
            loadPurchaseDetails(currentPurchaseId);
            messageLabel.setText("Item saved. Stock will update when purchase is received.");

        } catch (Exception e) {
            messageLabel.setText("Could not save item");
        }
    }

    // check if a section has enough free capacity for a product
    private boolean sectionHasSpace(Statement stmt, int sectionId, int productId, int quantity) throws Exception {
        // get unit size for this product
        ResultSet sizeRs = stmt.executeQuery(
            "select c.Unit_Size from Product p, Category c " +
            "where p.Category_ID = c.Category_ID and p.Product_ID = " + productId
        );
        if (!sizeRs.next()) return false;
        int unitSize = sizeRs.getInt("Unit_Size");
        sizeRs.close();

        // get section capacity and used space
        String sql = "select ss.Capacity, sum(sd.Quantity * c.Unit_Size) as UsedCapacity " +
                    "from Storage_Section ss " +
                    "left join Storage_Details sd on ss.Section_ID = sd.Section_ID " +
                    "left join Product p on sd.Product_ID = p.Product_ID " +
                    "left join Category c on p.Category_ID = c.Category_ID " +
                     "where ss.Section_ID = " + sectionId +" group by ss.Section_ID, ss.Capacity";

        ResultSet rs = stmt.executeQuery(sql);
        if (!rs.next()) return false;
        double used = rs.getDouble("UsedCapacity");
        if (rs.wasNull()) { used = 0; }
        boolean hasSpace = used + quantity * unitSize <= rs.getDouble("Capacity");
        rs.close();
        return hasSpace;
    }

    // delete an item from the current purchase
    @FXML
    private void deleteItem() {
        if (currentPurchaseId == 0 || productBox.getValue() == null) {
            messageLabel.setText("Select a purchase and product to delete");
            return;
        }

        int productId = getId(productBox.getValue());

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();

            // only pending purchases can be edited
            ResultSet statusRs = stmt.executeQuery("select Purchase_Status from Purchase where Purchase_ID = " + currentPurchaseId);
            if (!statusRs.next() || !statusRs.getString("Purchase_Status").equals("Pending")) {
                statusRs.close(); stmt.close(); mycon.close();
                messageLabel.setText("Items cannot be deleted after a purchase is received");
                return;
            }
            statusRs.close();

            int rows = stmt.executeUpdate(
                "delete from Purchase_Details where Purchase_ID = " + currentPurchaseId + " and Product_ID = " + productId
            );
            stmt.close();
            mycon.close();

            if (rows > 0) {
                purchaseSections.remove(currentPurchaseId + "-" + productId);
                clearItemFields();
                loadPurchases();
                loadPurchaseDetails(currentPurchaseId);
                messageLabel.setText("Item deleted");
            } else {
                messageLabel.setText("Item not found in this purchase");
            }

        } catch (Exception e) {
            messageLabel.setText("Could not delete item");
        }
    }

    // mark the current purchase as received and add stock
    @FXML
    private void markAsReceived() {
        if (currentPurchaseId == 0) {
            messageLabel.setText("Select a purchase first");
            return;
        }

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();

            // check purchase exists and is still pending
            ResultSet purchaseRs = stmt.executeQuery("select Purchase_Status from Purchase where Purchase_ID = " + currentPurchaseId);
            if (!purchaseRs.next() || !purchaseRs.getString("Purchase_Status").equals("Pending")) {
                purchaseRs.close(); stmt.close(); mycon.close();
                messageLabel.setText("Purchase is already received or not found");
                return;
            }
            purchaseRs.close();

            // collect all product IDs, quantities, and sections before updating
            ArrayList<Integer> productIds = new ArrayList<>();
            ArrayList<Integer> quantities = new ArrayList<>();
            ArrayList<Integer> sectionIds = new ArrayList<>();

            ResultSet itemRs = stmt.executeQuery("select Product_ID, Quantity from Purchase_Details where Purchase_ID = " + currentPurchaseId);
            while (itemRs.next()) {
                int productId = itemRs.getInt("Product_ID");
                String section = purchaseSections.get(currentPurchaseId + "-" + productId);
                if (section == null) {
                    itemRs.close(); stmt.close(); mycon.close();
                    messageLabel.setText("Choose a section for every purchase item first");
                    return;
                }
                productIds.add(productId);
                quantities.add(itemRs.getInt("Quantity"));
                sectionIds.add(getId(section));
            }
            itemRs.close();

            if (productIds.isEmpty()) {
                stmt.close(); mycon.close();
                messageLabel.setText("Add at least one item before receiving the purchase");
                return;
            }

            // add stock for each item to its chosen section
            for (int i = 0; i < productIds.size(); i++) {
                int productId = productIds.get(i);
                int quantity = quantities.get(i);
                int sectionId = sectionIds.get(i);

                if (!sectionHasSpace(stmt, sectionId, productId, quantity)) {
                    stmt.close(); mycon.close();
                    messageLabel.setText("Section " + sectionId + " does not have enough capacity");
                    return;
                }

                // check if storage detail already exists for this product and section
                ResultSet stockRs = stmt.executeQuery("select Quantity from Storage_Details where Product_ID = " + productId + " and Section_ID = " + sectionId);
                boolean stockExists = stockRs.next();
                stockRs.close();

                if (stockExists) {
                    stmt.executeUpdate("update Storage_Details set Quantity = Quantity + " + quantity +
                        " where Product_ID = " + productId + " and Section_ID = " + sectionId);
                } else {
                    stmt.executeUpdate("insert into Storage_Details(Product_ID, Section_ID, Quantity) values(" +productId + "," + sectionId + "," + quantity + ")");
                }

                // record the stock movement
                stmt.executeUpdate("insert into Stock_Movement_History(Product_ID, Source_Section_ID, Destination_Section_ID, Movement_Type, Quantity, Movement_Date) values(" +
                    productId + ", null, " + sectionId + ", 'Purchase', " + quantity + ", curdate())");
            }

            // mark purchase as received and set delivery date to today
            stmt.executeUpdate("update Purchase set Purchase_Status = 'Received', Delivery_Date = curdate() where Purchase_ID = " + currentPurchaseId);

            stmt.close();
            mycon.close();
            messageLabel.setText("Purchase received. Stock updated.");
            loadPurchases();
            loadPurchaseDetails(currentPurchaseId);

        } catch (Exception e) {
            messageLabel.setText("Could not receive purchase: " + e.getMessage());
        }
    }

    // finish working on current purchase and reset the form
    @FXML
    private void finishPurchase() {
        currentPurchaseId = 0;
        currentPurchaseLabel.setText("Current Purchase ID: none");
        detailsTitleLabel.setText("Select a purchase to show its items");
        detailList.clear();
        clearFields();
        messageLabel.setText("Purchase finished");
    }

    // search purchases by ID, supplier, or employee name
    @FXML
    private void searchPurchases() {
        String search = searchField.getText().toLowerCase();
        loadPurchases(); // reload all first

        // remove rows that don't match the search text
        for (int i = list.size() - 1; i >= 0; i--) {
            PurchasePage purchase = list.get(i);
            String id = String.valueOf(purchase.getPurchaseId());
            String supplier = purchase.getSupplier().toLowerCase();
            String employee = purchase.getEmployee().toLowerCase();
            if (!id.contains(search) && !supplier.contains(search) && !employee.contains(search)) {
                list.remove(i);
            }
        }
    }

    // clear all form inputs
    @FXML
    private void clearFields() {
        supplierBox.setValue(null);
        employeeBox.setValue(null);
        purchaseDatePicker.setValue(null);
        deliveryDatePicker.setValue(null);
        clearItemFields();
        searchField.clear();
        messageLabel.setText("");
    }

    // clear item inputs only
    private void clearItemFields() {
        productBox.setValue(null);
        sectionBox.setValue(null);
        quantityField.clear();
        unitPriceField.clear();
        sectionCapacityLabel.setText("Choose a product and enter quantity");
    }

    @FXML
    private void notReady() {
        messageLabel.setText("This section is not ready yet");
    }

    // go to dashboard page
    @FXML
    void openDashboard(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1400, 840));
            stage.setMaximized(true);
        } catch (Exception e) { messageLabel.setText("Cannot open dashboard page"); }
    }

    // go to products page
    @FXML
    void openProducts(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/product.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1400, 840));
            stage.setMaximized(true);
        } catch (Exception e) { messageLabel.setText("Cannot open products page"); }
    }

    // go to customers page
    @FXML
    void openCustomers(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/customer.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1400, 840));
            stage.setMaximized(true);
        } catch (Exception e) { messageLabel.setText("Cannot open customers page"); }
    }

    // go to suppliers page
    @FXML
    void openSuppliers(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/supplier.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1400, 840));
            stage.setMaximized(true);
        } catch (Exception e) { messageLabel.setText("Cannot open suppliers page"); }
    }

    // go to employees page
    @FXML
    void openEmployees(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/employee.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1400, 840));
            stage.setMaximized(true);
        } catch (Exception e) { messageLabel.setText("Cannot open employees page"); }
    }

    // go to sales page
    @FXML
    void openSales(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/sales.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1400, 840));
            stage.setMaximized(true);
        } catch (Exception e) { messageLabel.setText("Cannot open sales page"); }
    }

    // go to purchases page
    @FXML
    void openPurchases(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/purchases.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1400, 840));
            stage.setMaximized(true);
        } catch (Exception e) { messageLabel.setText("Cannot open purchases page"); }
    }

    // go to inventory page
    @FXML
    void openInventory(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/inventory.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1400, 840));
            stage.setMaximized(true);
        } catch (Exception e) { messageLabel.setText("Cannot open inventory page"); }
    }
    @FXML
    void openReports(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/report.fxml"));
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root,1400,840));
        stage.setMaximized(true);
    } catch (Exception e) {
        messageLabel.setText("Cannot open reports page");
    }
}
}