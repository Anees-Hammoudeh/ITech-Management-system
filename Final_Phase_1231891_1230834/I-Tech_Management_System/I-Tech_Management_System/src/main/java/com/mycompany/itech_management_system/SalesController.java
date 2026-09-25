package com.mycompany.itech_management_system;

import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.time.LocalDate;

public class SalesController implements Initializable {

    // for sale table
    @FXML TextField saleIdField;
    @FXML ComboBox<String> saleCustomerCombo;
    @FXML ComboBox<String> saleEmployeeCombo;
    @FXML DatePicker saleDatePicker;

    @FXML TextField saleSearchField;
    @FXML ComboBox<String> saleSearchTypeCombo;

    @FXML Label saleMessageLabel;
    @FXML Label saleSearchMessageLabel;
    @FXML Label saleCountLabel;

    @FXML TableView<SalePage> saleTable;
    @FXML TableColumn<SalePage, Integer> saleIdColumn;
    @FXML TableColumn<SalePage, String> saleCustomerColumn;
    @FXML TableColumn<SalePage, String> saleEmployeeColumn;
    @FXML TableColumn<SalePage, String> saleDateColumn;
    @FXML TableColumn<SalePage, Double> saleTotalColumn;

    // for sale detail table
    @FXML TextField detailSaleIdField;
    @FXML ComboBox<String> detailProductCombo;
    @FXML TextField detailQuantityField;
    @FXML TextField detailDiscountField;
    @FXML TextField detailSearchField;
    @FXML ComboBox<String> detailSearchTypeCombo;

    @FXML Label detailMessageLabel;
    @FXML Label detailSearchMessageLabel;

    @FXML TableView<SaleDetail> detailTable;
    @FXML TableColumn<SaleDetail, Integer> detailSaleIdColumn;
    @FXML TableColumn<SaleDetail, String> detailProductColumn;
    @FXML TableColumn<SaleDetail, Integer> detailQuantityColumn;
    @FXML TableColumn<SaleDetail, Double> detailUnitPriceColumn;
    @FXML TableColumn<SaleDetail, Double> detailDiscountColumn;
    @FXML TableColumn<SaleDetail, Double> detailLineTotalColumn;

    // for payment table
    @FXML TextField paymentSaleIdField;
    @FXML TextField paymentAmountField;
    @FXML ComboBox<String> paymentMethodCombo;
    @FXML DatePicker paymentDatePicker;
    @FXML ComboBox<String> paymentStatusCombo;
    @FXML TextField paymentSearchField;
    @FXML ComboBox<String> paymentSearchTypeCombo;

    @FXML Label paymentMessageLabel;
    @FXML Label paymentSearchMessageLabel;
    @FXML Label paymentSaleTotalLabel;
    @FXML Label paymentPaidAmountLabel;
    @FXML Label paymentRemainingLabel;
    @FXML Label paymentStatusLabel;

    @FXML TableView<PaymentRow> paymentTable;
    @FXML TableColumn<PaymentRow, Integer> paymentIdColumn;
    @FXML TableColumn<PaymentRow, Integer> paymentSaleColumn;
    @FXML TableColumn<PaymentRow, Double> paymentAmountColumn;
    @FXML TableColumn<PaymentRow, String> paymentMethodColumn;
    @FXML TableColumn<PaymentRow, String> paymentStatusColumn;
    @FXML TableColumn<PaymentRow, String> paymentDateColumn;

    // for warranty table
    @FXML TextField warrantySaleIdField;
    @FXML ComboBox<String> warrantyProductCombo;
    @FXML TextField warrantyNumberField;
    @FXML ComboBox<String> warrantyTypeCombo;
    @FXML Spinner<Integer> periodValue;
    @FXML ComboBox<String> periodUnit;
    @FXML DatePicker warrantyStartDatePicker;
    @FXML TextField warrantySearchField;
    @FXML ComboBox<String> warrantySearchTypeCombo;
    @FXML Label warrantyMessageLabel;
    @FXML Label warrantySearchMessageLabel;
    @FXML TableView<WarrantyRow> warrantyTable;
    @FXML TableColumn<WarrantyRow, Integer> warrantySaleIdColumn;
    @FXML TableColumn<WarrantyRow, String> warrantyProductColumn;
    @FXML TableColumn<WarrantyRow, String> warrantyNumberColumn;
    @FXML TableColumn<WarrantyRow, String> warrantyTypeColumn;
    @FXML TableColumn<WarrantyRow, String> warrantyPeriodColumn;
    @FXML TableColumn<WarrantyRow, String> warrantyStartDateColumn;

    // for return table
    @FXML TextField returnIdField;
    @FXML TextField returnSaleIdField;
    @FXML ComboBox<String> returnProductCombo;
    @FXML DatePicker returnDatePicker;
    @FXML TextField returnQuantityField;
    @FXML TextField returnReasonField;
    @FXML ComboBox<String> returnStatusCombo;
    @FXML ComboBox<String> returnSectionCombo;
    @FXML TextField returnSearchField;
    @FXML ComboBox<String> returnSearchTypeCombo;
    @FXML Label returnMessageLabel;
    @FXML Label returnSearchMessageLabel;
    @FXML Label returnSectionLabel;
    @FXML TableView<ReturnRow> returnTable;
    @FXML TableColumn<ReturnRow, Integer> returnIdColumn;
    @FXML TableColumn<ReturnRow, Integer> returnSaleIdColumn;
    @FXML TableColumn<ReturnRow, String> returnProductColumn;
    @FXML TableColumn<ReturnRow, Integer> returnQuantityColumn;
    @FXML TableColumn<ReturnRow, String> returnReasonColumn;
    @FXML TableColumn<ReturnRow, String> returnStatusColumn;
    @FXML TableColumn<ReturnRow, String> returnDateColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // link sale table columns to SalePage getters
        saleIdColumn.setCellValueFactory(new PropertyValueFactory("saleId"));
        saleCustomerColumn.setCellValueFactory(new PropertyValueFactory("customer"));
        saleEmployeeColumn.setCellValueFactory(new PropertyValueFactory("employee"));
        saleDateColumn.setCellValueFactory(new PropertyValueFactory("date"));
        saleTotalColumn.setCellValueFactory(new PropertyValueFactory("total"));

        // link detail table columns to SaleDetail getters
        detailSaleIdColumn.setCellValueFactory(new PropertyValueFactory("saleId"));
        detailProductColumn.setCellValueFactory(new PropertyValueFactory("product"));
        detailQuantityColumn.setCellValueFactory(new PropertyValueFactory("quantity"));
        detailUnitPriceColumn.setCellValueFactory(new PropertyValueFactory("unitPrice"));
        detailDiscountColumn.setCellValueFactory(new PropertyValueFactory("discount"));
        detailLineTotalColumn.setCellValueFactory(new PropertyValueFactory("lineTotal"));

        // link payment table columns to PaymentRow getters
        paymentIdColumn.setCellValueFactory(new PropertyValueFactory("paymentId"));
        paymentSaleColumn.setCellValueFactory(new PropertyValueFactory("saleId"));
        paymentAmountColumn.setCellValueFactory(new PropertyValueFactory("amount"));
        paymentMethodColumn.setCellValueFactory(new PropertyValueFactory("method"));
        paymentStatusColumn.setCellValueFactory(new PropertyValueFactory("status"));
        paymentDateColumn.setCellValueFactory(new PropertyValueFactory("date"));

        // link warranty table columns to WarrantyRow getters
        warrantySaleIdColumn.setCellValueFactory(new PropertyValueFactory("saleId"));
        warrantyProductColumn.setCellValueFactory(new PropertyValueFactory("product"));
        warrantyNumberColumn.setCellValueFactory(new PropertyValueFactory("warrantyNumber"));
        warrantyTypeColumn.setCellValueFactory(new PropertyValueFactory("warrantyType"));
        warrantyPeriodColumn.setCellValueFactory(new PropertyValueFactory("warrantyPeriod"));
        warrantyStartDateColumn.setCellValueFactory(new PropertyValueFactory("startDate"));

        // link return table columns to ReturnRow getters
        returnIdColumn.setCellValueFactory(new PropertyValueFactory("returnId"));
        returnSaleIdColumn.setCellValueFactory(new PropertyValueFactory("saleId"));
        returnProductColumn.setCellValueFactory(new PropertyValueFactory("product"));
        returnQuantityColumn.setCellValueFactory(new PropertyValueFactory("quantity"));
        returnReasonColumn.setCellValueFactory(new PropertyValueFactory("reason"));
        returnStatusColumn.setCellValueFactory(new PropertyValueFactory("status"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory("date"));

        // search type options for each table
        saleSearchTypeCombo.getItems().add("Sale ID");
        saleSearchTypeCombo.getItems().add("Customer Name");
        saleSearchTypeCombo.getItems().add("Employee Name");
        saleSearchTypeCombo.getItems().add("Date");
        saleSearchTypeCombo.setValue("Sale ID");

        detailSearchTypeCombo.getItems().add("Sale ID");
        detailSearchTypeCombo.getItems().add("Product Name");
        detailSearchTypeCombo.getItems().add("Quantity");
        detailSearchTypeCombo.getItems().add("Unit Price");
        detailSearchTypeCombo.getItems().add("Discount");
        detailSearchTypeCombo.getItems().add("Line Total");
        detailSearchTypeCombo.setValue("Sale ID");

        paymentSearchTypeCombo.getItems().add("Payment ID");
        paymentSearchTypeCombo.getItems().add("Sale ID");
        paymentSearchTypeCombo.getItems().add("Amount");
        paymentSearchTypeCombo.getItems().add("Method");
        paymentSearchTypeCombo.getItems().add("Status");
        paymentSearchTypeCombo.getItems().add("Date");
        paymentSearchTypeCombo.setValue("Sale ID");

        warrantySearchTypeCombo.getItems().add("Sale ID");
        warrantySearchTypeCombo.getItems().add("Product Name");
        warrantySearchTypeCombo.getItems().add("Warranty Number");
        warrantySearchTypeCombo.getItems().add("Type");
        warrantySearchTypeCombo.getItems().add("Period");
        warrantySearchTypeCombo.getItems().add("Start Date");
        warrantySearchTypeCombo.setValue("Sale ID");

        // payment method options
        paymentMethodCombo.getItems().add("Cash");
        paymentMethodCombo.getItems().add("Card");
        paymentMethodCombo.getItems().add("Bank Transfer");
        paymentMethodCombo.setValue("Cash");

        // warranty type options
        warrantyTypeCombo.getItems().add("Manufacturer Warranty");
        warrantyTypeCombo.getItems().add("Store Warranty");
        warrantyTypeCombo.getItems().add("Extended Warranty");
        warrantyTypeCombo.setValue("Manufacturer Warranty");

        // warranty period spinner from 1 to 60, default 12
        periodValue.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60, 12));
        periodUnit.getItems().add("Months");
        periodUnit.getItems().add("Years");
        periodUnit.setValue("Months");

        // return status options
        returnStatusCombo.getItems().addAll("Approved","Rejected");
        returnStatusCombo.setValue("Approved");
        returnSearchTypeCombo.getItems().addAll("Return ID","Sale ID","Product Name","Status","Date");
        returnSearchTypeCombo.setValue("Return ID");

        // reload available sections whenever product, quantity, or status changes
        returnProductCombo.valueProperty().addListener((obs, oldValue, newValue) -> loadReturnSections());
        returnQuantityField.textProperty().addListener((obs, oldValue, newValue) -> loadReturnSections());
        returnStatusCombo.valueProperty().addListener((obs, oldValue, newValue) -> loadReturnSections());

        // reset payment summary labels to zero on startup
        setPaymentSummary(0, 0);

        // fill combos from database
        loadCustomersCombo();
        loadEmployeesCombo();
        loadProductsCombo();

        // when user clicks a sale row, fill all related tabs with its data
        saleTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSale, selectedSale) -> {
            if (selectedSale != null) {
                loadSaleForm(selectedSale.getSaleId());
                detailSaleIdField.setText(String.valueOf(selectedSale.getSaleId()));
                paymentSaleIdField.setText(String.valueOf(selectedSale.getSaleId()));
                warrantySaleIdField.setText(String.valueOf(selectedSale.getSaleId()));
                returnSaleIdField.setText(String.valueOf(selectedSale.getSaleId()));
                loadDetailsForSale(selectedSale.getSaleId());
                loadPaymentsForSale(selectedSale.getSaleId());
                loadWarrantyProductsForSale(selectedSale.getSaleId());
                loadWarrantiesForSale(selectedSale.getSaleId());
                loadReturnProductsForSale(selectedSale.getSaleId());
                loadReturnsForSale(selectedSale.getSaleId());
                updatePaymentSummary(selectedSale.getSaleId());
                detailSearchMessageLabel.setText("Showing details for Sale ID: " + selectedSale.getSaleId());
                paymentSearchMessageLabel.setText("Showing payments for Sale ID: " + selectedSale.getSaleId());
                warrantySearchMessageLabel.setText("Showing warranties for Sale ID: " + selectedSale.getSaleId());
                returnSearchMessageLabel.setText("Showing returns for Sale ID: " + selectedSale.getSaleId());
            }
        });

        // disable future dates in all date pickers
        saleDatePicker.setDayCellFactory(dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    return;
                }
                if (item.isAfter(LocalDate.now())) {
                    setDisable(true);
                }
            }
        });

        paymentDatePicker.setDayCellFactory(dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    return;
                }
                if (item.isAfter(LocalDate.now())) {
                    setDisable(true);
                }
            }
        });

        warrantyStartDatePicker.setDayCellFactory(dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    return;
                }
                if (item.isAfter(LocalDate.now())) {
                    setDisable(true);
                }
            }
        });

        returnDatePicker.setDayCellFactory(dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    return;
                }
                if (item.isAfter(LocalDate.now())) {
                    setDisable(true);
                }
            }
        });

        // load all tables on startup
        doShowSales();
        doShowDetails();
        doShowPayments();
        doShowWarranties();
        doShowReturns();
    }

    // fill the sale form fields when a row is selected from the table
    private void loadSaleForm(int saleId) {
        try {
            Connection con = DBconnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select Customer_ID,Employee_ID,Sale_Date from Sale where Sale_ID=" + saleId);
            if (rs.next()) {
                saleIdField.setText(String.valueOf(saleId));
                String customerStart = rs.getInt("Customer_ID") + " - ";
                String employeeStart = rs.getInt("Employee_ID") + " - ";
                // find the matching combo item that starts with the correct ID
                for (String value : saleCustomerCombo.getItems()) {
                    if (value.startsWith(customerStart)) saleCustomerCombo.setValue(value);
                }
                for (String value : saleEmployeeCombo.getItems()) {
                    if (value.startsWith(employeeStart)) saleEmployeeCombo.setValue(value);
                }
                saleDatePicker.setValue(rs.getDate("Sale_Date").toLocalDate());
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            saleMessageLabel.setText("Could not load sale data");
        }
    }

    // add a new sale record
    @FXML
    void doAddSale() {

        String selectedCustomer = saleCustomerCombo.getValue();
        String selectedEmployee = saleEmployeeCombo.getValue();

        if (selectedCustomer == null || selectedEmployee == null || saleDatePicker.getValue() == null) {
            saleMessageLabel.setText("Please make sure that all sale fields are filled");
            return;
        }

        int custId = getIdFromCombo(selectedCustomer);
        int empId = getIdFromCombo(selectedEmployee);
        String date = saleDatePicker.getValue().toString();

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();
            stmt.executeUpdate("insert into Sale(Customer_ID, Employee_ID, Sale_Date) values("
                    + custId + "," + empId + ",'" + date + "')");
            stmt.close();
            mycon.close();
            saleMessageLabel.setText("Sale added successfully");
            clearSaleFields();
            doShowSales();
        } catch (Exception e) {
            saleMessageLabel.setText("Could not add sale");
        }
    }

    // update an existing sale record
    @FXML
    void doUpdateSale() {
        String id = saleIdField.getText();
        String customer = saleCustomerCombo.getValue();
        String employee = saleEmployeeCombo.getValue();

        if (!isPositiveInt(id) || customer == null || employee == null || saleDatePicker.getValue() == null) {
            saleMessageLabel.setText("Enter Sale ID and fill all sale fields");
            return;
        }

        String sql = "update Sale set Customer_ID=?, Employee_ID=?, Sale_Date=? where Sale_ID=?";
        try {
            Connection con = DBconnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, getIdFromCombo(customer));
            ps.setInt(2, getIdFromCombo(employee));
            ps.setDate(3, java.sql.Date.valueOf(saleDatePicker.getValue()));
            ps.setInt(4, Integer.parseInt(id));

            int rows = ps.executeUpdate();
            ps.close();
            con.close();

            if (rows > 0) {
                saleMessageLabel.setText("Sale updated successfully");
                doShowSales();
            } else {
                saleMessageLabel.setText("Sale ID not found");
            }
        } catch (Exception e) {
            saleMessageLabel.setText("Could not update sale");
        }
    }

    // delete a sale and restore stock for all its items
    @FXML
    void doDeleteSale() {

        String id = saleIdField.getText();
        if (id.isEmpty()) {
            saleMessageLabel.setText("Enter Sale ID to delete");
            return;
        }
        if (!isPositiveInt(id)) {
            saleMessageLabel.setText("Sale ID must be a positive number");
            return;
        }

        int saleIdValue = Integer.parseInt(id);
        Connection mycon = null;
        Statement stmt = null;

        try {
            mycon = DBconnection.getConnection();
            mycon.setAutoCommit(false);
            stmt = mycon.createStatement();

            // get each product sold in this sale
            ResultSet rs = stmt.executeQuery(
                "select Product_ID, Quantity from Sale_Details where Sale_ID = " + saleIdValue);
            while (rs.next()) {
                int productId = rs.getInt("Product_ID");
                int soldQty   = rs.getInt("Quantity");

                // get how many were already returned so we don't restore those
                Statement retStmt = mycon.createStatement();
                ResultSet retRs = retStmt.executeQuery("select sum(Quantity) as ReturnedQty from Return_Record " +
                    "where Sale_ID = " + saleIdValue +" and Product_ID = " + productId +" and Return_Status = 'Approved'");
                int returnedQty = 0;
                if (retRs.next()) {
                    returnedQty = retRs.getInt("ReturnedQty");
                    if (retRs.wasNull()) { returnedQty = 0; }
                }
                retRs.close();
                retStmt.close();

                int quantityToRestore = soldQty - returnedQty;
                Statement restoreStmt = mycon.createStatement();
                if (quantityToRestore > 0) restoreStockAfterDelete(restoreStmt, productId, quantityToRestore);
                restoreStmt.close();
            }
            rs.close();

            int rows = stmt.executeUpdate("delete from Sale where Sale_ID = " + saleIdValue);
            if (rows > 0) {
                mycon.commit();
                saleMessageLabel.setText("Sale deleted and stock restored successfully");
            } else {
                mycon.rollback();
                saleMessageLabel.setText("Sale ID not found");
            }

            clearSaleFields();
            doShowSales();
            doShowDetails();
            doShowPayments();
            doShowWarranties();
            doShowReturns();

        } catch (Exception e) {
            try {
                if (mycon != null) mycon.rollback();
            } catch (Exception ex) {}
            saleMessageLabel.setText("Could not delete sale");
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (mycon != null) {
                    mycon.setAutoCommit(true);
                    mycon.close();
                }
            } catch (Exception e) {}
        }
    }

    // load all sales into the sale table
    @FXML
    void doShowSales() {

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();
            Statement totalStmt = mycon.createStatement(); // separate stmt for getSaleTotal inside the loop

            // join with customer and employee to get names
            String sql = "select s.Sale_ID, concat(c.First_Name, ' ', c.Last_Name) as CustomerName, concat(e.First_Name, ' ', e.Last_Name) as EmployeeName, s.Sale_Date "
                      + "from Sale s "+ "left join Customer c on s.Customer_ID = c.Customer_ID "
                      + "left join Employee e on s.Employee_ID = e.Employee_ID "
                      + "order by s.Sale_Date desc, s.Sale_ID desc";

            ResultSet rs = stmt.executeQuery(sql);
            ObservableList<SalePage> list = FXCollections.observableArrayList();

            while (rs.next()) {
                int saleId = rs.getInt("Sale_ID");
                list.add(new SalePage(
                        saleId,
                        rs.getString("CustomerName"),
                        rs.getString("EmployeeName"),
                        rs.getString("Sale_Date"),
                        getSaleTotal(totalStmt, saleId)
                ));
            }

            saleTable.setItems(list);
            saleCountLabel.setText("Total Sales: " + list.size());

            rs.close();
            totalStmt.close();
            stmt.close();
            mycon.close();

        } catch (Exception e) {
            saleMessageLabel.setText("Could not load sales");
        }
    }

    // search sales by the selected type
    @FXML
    void doSearchSale() {

        String type = saleSearchTypeCombo.getValue();
        String value = saleSearchField.getText();

        if (value.isEmpty()) {
            saleSearchMessageLabel.setText("Enter a search value");
            return;
        }

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();
            Statement totalStmt = mycon.createStatement();

            // base query joining sale with customer and employee
            String sql = "select s.Sale_ID, concat(c.First_Name, ' ', c.Last_Name) as CustomerName, concat(e.First_Name, ' ', e.Last_Name) as EmployeeName, s.Sale_Date "
                    + "from Sale s "+ "left join Customer c on s.Customer_ID = c.Customer_ID "
                    + "left join Employee e on s.Employee_ID = e.Employee_ID ";

            // add filter based on selected search type
            if (type.equals("Sale ID")) {
                sql += "where s.Sale_ID = " + value + " ";
            } else if (type.equals("Customer Name")) {
                sql += "where concat(c.First_Name, ' ', c.Last_Name) like '%" + value + "%' ";
            } else if (type.equals("Employee Name")) {
                sql += "where concat(e.First_Name, ' ', e.Last_Name) like '%" + value + "%' ";
            } else if (type.equals("Date")) {
                sql += "where s.Sale_Date = '" + value + "' ";
            }

            sql += "order by s.Sale_ID desc";

            ResultSet rs = stmt.executeQuery(sql);
            ObservableList<SalePage> list = FXCollections.observableArrayList();

            while (rs.next()) {
                int saleId = rs.getInt("Sale_ID");
                list.add(new SalePage(saleId, rs.getString("CustomerName"),
                        rs.getString("EmployeeName"), rs.getString("Sale_Date"), getSaleTotal(totalStmt, saleId)));
            }

            saleTable.setItems(list);

            if (list.isEmpty()) {
                saleSearchMessageLabel.setText("No sales found");
            } else {
                saleSearchMessageLabel.setText("Found " + list.size() + " records");
            }

            rs.close();
            totalStmt.close();
            stmt.close();
            mycon.close();

        } catch (Exception e) {
            saleSearchMessageLabel.setText("Search failed");
        }
    }

    // add a product to a sale and reduce its stock
    @FXML
    void doAddDetail() {

        String saleId = detailSaleIdField.getText();
        String selectedProduct = detailProductCombo.getValue();
        String qty = detailQuantityField.getText();
        String discount = detailDiscountField.getText();

        if (saleId.isEmpty() || selectedProduct == null || qty.isEmpty()) {
            detailMessageLabel.setText("Fill Sale ID, Product and Quantity");
            return;
        }

        if (!isPositiveInt(saleId) || !isPositiveInt(qty)) {
            detailMessageLabel.setText("Sale ID and Quantity must be positive numbers");
            return;
        }

        if (discount.isEmpty()) discount = "0";

        if (!isNonNegativeDouble(discount)) {
            detailMessageLabel.setText("Discount must be a valid number");
            return;
        }

        int saleIdValue = Integer.parseInt(saleId);
        int productId = getProductIdFromCombo(selectedProduct);
        int requestedQty = Integer.parseInt(qty);
        double discountValue = Double.parseDouble(discount);

        Connection mycon = null;
        Statement stmt = null;

        try {
            mycon = DBconnection.getConnection();
            mycon.setAutoCommit(false);
            stmt = mycon.createStatement();

            // check sale exists
            ResultSet saleCheck = stmt.executeQuery("select Sale_ID from Sale where Sale_ID = " + saleIdValue);
            if (!saleCheck.next()) {
                saleCheck.close();
                mycon.rollback();
                detailMessageLabel.setText("Sale ID does not exist");
                return;
            }
            saleCheck.close();

            // check product is not already in this sale
            ResultSet duplicateCheck = stmt.executeQuery("select Quantity from Sale_Details where Sale_ID = " + saleIdValue + " and Product_ID = " + productId);
            if (duplicateCheck.next()) {
                duplicateCheck.close();
                mycon.rollback();
                detailMessageLabel.setText("This product already exists in this sale");
                return;
            }
            duplicateCheck.close();

            // check available stock
            int availableQty = getAvailableQuantity(stmt, productId);
            if (requestedQty > availableQty) {
                mycon.rollback();
                detailMessageLabel.setText("Not enough stock. Available: " + availableQty);
                return;
            }

            // insert detail using the product price from the Product table
            stmt.executeUpdate("insert into Sale_Details(Sale_ID, Product_ID, Quantity, Unit_Price, Discount) "
                    + "select " + saleIdValue + ", Product_ID, " + requestedQty + ", Price, " + discountValue
                    + " from Product where Product_ID = " + productId);

            // reduce stock in storage
            reduceStockForSale(stmt, productId, requestedQty);

            mycon.commit();
            detailMessageLabel.setText("Detail added and stock updated successfully");
            clearDetailItemFields();
            detailSaleIdField.setText(String.valueOf(saleIdValue));
            loadDetailsForSale(saleIdValue);
            loadWarrantyProductsForSale(saleIdValue);
            loadWarrantiesForSale(saleIdValue);
            loadReturnProductsForSale(saleIdValue);
            loadReturnsForSale(saleIdValue);
            updatePaymentSummary(saleIdValue);
            doShowSales();

        } catch (Exception e) {
            try {
                if (mycon != null) mycon.rollback();
            } catch (Exception ex) {}
            detailMessageLabel.setText("Could not add detail");
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (mycon != null) {
                    mycon.setAutoCommit(true);
                    mycon.close();
                }
            } catch (Exception e) {}
        }
    }

    // delete a product from a sale and restore its stock
    @FXML
    void doDeleteDetail() {

        String saleId = detailSaleIdField.getText();
        String selectedProduct = detailProductCombo.getValue();

        if (saleId.isEmpty() || selectedProduct == null) {
            detailMessageLabel.setText("Enter Sale ID and select Product to delete");
            return;
        }

        if (!isPositiveInt(saleId)) {
            detailMessageLabel.setText("Sale ID must be a positive number");
            return;
        }

        int saleIdValue = Integer.parseInt(saleId);
        int productId = getProductIdFromCombo(selectedProduct);
        Connection mycon = null;
        Statement stmt = null;

        try {
            mycon = DBconnection.getConnection();
            mycon.setAutoCommit(false);
            stmt = mycon.createStatement();

            // get the sold quantity and price for this detail
            ResultSet rs = stmt.executeQuery("select Quantity, Unit_Price, Discount from Sale_Details where Sale_ID = " + saleIdValue
                    + " and Product_ID = " + productId);
            if (!rs.next()) {
                rs.close();
                mycon.rollback();
                detailMessageLabel.setText("Detail not found");
                return;
            }
            int oldQty = rs.getInt("Quantity");
            double unitPrice = rs.getDouble("Unit_Price");
            double discount = rs.getDouble("Discount");
            if (rs.wasNull()) { discount = 0; }
            rs.close();

            // get how many were already returned so we don't restore those
            ResultSet returnRs = stmt.executeQuery("select sum(Quantity) as ReturnedQty from Return_Record "
                    + "where Sale_ID=" + saleIdValue + " and Product_ID=" + productId + " and Return_Status='Approved'");
            int returnedQty = 0;
            if (returnRs.next()) {
                returnedQty = returnRs.getInt("ReturnedQty");
                if (returnRs.wasNull()) { returnedQty = 0; }
            }
            returnRs.close();

            // check that deleting this detail won't make paid amount exceed the new sale total
            int netQuantity = oldQty - returnedQty;
            double oldLineTotal = (unitPrice - discount / oldQty) * netQuantity;
            double saleTotalAfterDelete = getSaleTotal(stmt, saleIdValue) - oldLineTotal;
            double paid = getPaidAmount(stmt, saleIdValue);
            if (paid > saleTotalAfterDelete + 0.0001) {
                mycon.rollback();
                detailMessageLabel.setText("Cannot delete detail because payments would be greater than the sale total");
                return;
            }

            int rows = stmt.executeUpdate("delete from Sale_Details where Sale_ID = " + saleIdValue
                    + " and Product_ID = " + productId);

            if (rows > 0) {
                // restore only the quantity that was not already returned
                int quantityToRestore = oldQty - returnedQty;
                if (quantityToRestore > 0) restoreStockAfterDelete(stmt, productId, quantityToRestore);
                mycon.commit();
                detailMessageLabel.setText("Detail deleted and stock restored successfully");
            } else {
                mycon.rollback();
                detailMessageLabel.setText("Detail not found");
            }

            loadDetailsForSale(saleIdValue);
            loadWarrantyProductsForSale(saleIdValue);
            loadWarrantiesForSale(saleIdValue);
            loadReturnProductsForSale(saleIdValue);
            loadReturnsForSale(saleIdValue);
            updatePaymentSummary(saleIdValue);
            doShowSales();

        } catch (Exception e) {
            try {
                if (mycon != null) mycon.rollback();
            } catch (Exception ex) {}
            detailMessageLabel.setText("Could not delete detail");
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (mycon != null) {
                    mycon.setAutoCommit(true);
                    mycon.close();
                }
            } catch (Exception e) {}
        }
    }

    // load all sale details into the detail table
    @FXML
    void doShowDetails() {

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();

            // join with Product to get the product name
            String sql = "select sd.Sale_ID, p.Product_Name, sd.Quantity, sd.Unit_Price, sd.Discount "
                    + "from Sale_Details sd, Product p "
                    + "where sd.Product_ID = p.Product_ID "
                    + "order by sd.Sale_ID desc";

            ResultSet rs = stmt.executeQuery(sql);
            ObservableList<SaleDetail> list = FXCollections.observableArrayList();

            while (rs.next()) {
                int quantity = rs.getInt("Quantity");
                double unitPrice = rs.getDouble("Unit_Price");
                double discount = rs.getDouble("Discount");
                if (rs.wasNull()) { discount = 0; }
                double lineTotal = (unitPrice * quantity) - discount;
                list.add(new SaleDetail(rs.getInt("Sale_ID"),rs.getString("Product_Name"),
                        quantity,unitPrice,discount,lineTotal));
            }

            detailTable.setItems(list);

            rs.close();
            stmt.close();
            mycon.close();

        } catch (Exception e) {
            detailMessageLabel.setText("Could not load details");
        }
    }

    // load details for a specific sale only
    private void loadDetailsForSale(int saleIdValue) {

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();

            String sql = "select sd.Sale_ID, p.Product_Name, sd.Quantity, sd.Unit_Price, sd.Discount "
                    + "from Sale_Details sd, Product p "
                    + "where sd.Product_ID = p.Product_ID "
                    + "and sd.Sale_ID = " + saleIdValue + " "
                    + "order by p.Product_Name";

            ResultSet rs = stmt.executeQuery(sql);
            ObservableList<SaleDetail> list = FXCollections.observableArrayList();

            while (rs.next()) {
                int quantity = rs.getInt("Quantity");
                double unitPrice = rs.getDouble("Unit_Price");
                double discount = rs.getDouble("Discount");
                if (rs.wasNull()) { discount = 0; }
                double lineTotal = (unitPrice * quantity) - discount;
                list.add(new SaleDetail(
                        rs.getInt("Sale_ID"),
                        rs.getString("Product_Name"),
                        quantity,
                        unitPrice,
                        discount,
                        lineTotal
                ));
            }

            detailTable.setItems(list);

            rs.close();
            stmt.close();
            mycon.close();

        } catch (Exception e) {
            detailMessageLabel.setText("Could not load sale details");
        }
    }

    // search sale details by the selected type
    @FXML
    void doSearchDetail() {

        String type = detailSearchTypeCombo.getValue();
        String value = detailSearchField.getText();

        if (value.isEmpty()) {
            detailSearchMessageLabel.setText("Enter a search value");
            return;
        }

        if ((type.equals("Sale ID") || type.equals("Quantity")) && !isPositiveInt(value)) {
            detailSearchMessageLabel.setText(type + " must be a positive number");
            return;
        }

        if ((type.equals("Unit Price") || type.equals("Discount") || type.equals("Line Total")) && !isNonNegativeDouble(value)) {
            detailSearchMessageLabel.setText(type + " must be a valid number");
            return;
        }

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();

            String sql = "select sd.Sale_ID, p.Product_Name, sd.Quantity, sd.Unit_Price, sd.Discount "
                    + "from Sale_Details sd, Product p "
                    + "where sd.Product_ID = p.Product_ID ";

            // add filter based on selected search type
            if (type.equals("Sale ID")) {
                sql += "and sd.Sale_ID = " + value + " ";
            } else if (type.equals("Product Name")) {
                sql += "and p.Product_Name like '%" + value + "%' ";
            } else if (type.equals("Quantity")) {
                sql += "and sd.Quantity = " + value + " ";
            } else if (type.equals("Unit Price")) {
                sql += "and sd.Unit_Price = " + value + " ";
            } else if (type.equals("Discount")) {
                sql += "and (sd.Discount = " + value + " or sd.Discount is null) ";
            } else if (type.equals("Line Total")) {
                sql += "and (sd.Unit_Price * sd.Quantity) - sd.Discount = " + value + " ";
            }

            sql += "order by sd.Sale_ID desc";

            ResultSet rs = stmt.executeQuery(sql);
            ObservableList<SaleDetail> list = FXCollections.observableArrayList();

            while (rs.next()) {
                int quantity = rs.getInt("Quantity");
                double unitPrice = rs.getDouble("Unit_Price");
                double discount = rs.getDouble("Discount");
                if (rs.wasNull()) { discount = 0; }
                double lineTotal = (unitPrice * quantity) - discount;
                list.add(new SaleDetail(rs.getInt("Sale_ID"), rs.getString("Product_Name"),
                        quantity, unitPrice,
                        discount, lineTotal));
            }

            detailTable.setItems(list);

            if (list.isEmpty()) {
                detailSearchMessageLabel.setText("No details found");
            } else {
                detailSearchMessageLabel.setText("Found " + list.size() + " records");
            }

            rs.close();
            stmt.close();
            mycon.close();

        } catch (Exception e) {
            detailSearchMessageLabel.setText("Search failed");
        }
    }

    // add a payment for a sale
    @FXML
    void doAddPayment() {

        String saleId = paymentSaleIdField.getText();
        String amount = paymentAmountField.getText();
        String method = paymentMethodCombo.getValue();

        if (saleId.isEmpty() || amount.isEmpty() || method == null || paymentDatePicker.getValue() == null) {
            paymentMessageLabel.setText("Fill Sale ID, Amount, Method and Date");
            return;
        }

        if (!isPositiveInt(saleId)) {
            paymentMessageLabel.setText("Sale ID must be a positive number");
            return;
        }

        if (!isPositiveDouble(amount)) {
            paymentMessageLabel.setText("Amount must be greater than 0");
            return;
        }

        int saleIdValue = Integer.parseInt(saleId);
        double amountValue = Double.parseDouble(amount);
        String date = paymentDatePicker.getValue().toString();

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();

            double total = getSaleTotal(stmt, saleIdValue);
            if (total <= 0) {
                paymentMessageLabel.setText("Cannot add payment before adding sale details");
                stmt.close();
                mycon.close();
                return;
            }

            double paid = getPaidAmount(stmt, saleIdValue);
            double remaining = total - paid;

            if (remaining <= 0.0001) {
                paymentMessageLabel.setText("This sale is already fully paid");
                stmt.close();
                mycon.close();
                return;
            }

            // payment cannot exceed what is still owed
            if (amountValue > remaining + 0.0001) {
                paymentMessageLabel.setText("Amount is greater than remaining. Remaining: " + String.format("%.2f", remaining));
                stmt.close();
                mycon.close();
                return;
            }

            // calculate status based on new total paid after this payment
            double newPaid = paid + amountValue;
            String newStatus = calculatePaymentStatus(total, newPaid);

            stmt.executeUpdate("insert into Payment(Sale_ID,Amount,Payment_Method,Payment_Date,Payment_Status) values("
                    + saleIdValue + "," + amountValue + ",'" + method + "','" + date + "','" + newStatus + "')");

            stmt.close();
            mycon.close();

            paymentMessageLabel.setText("Payment added successfully. Status: " + newStatus);
            clearPaymentItemFields();
            paymentSaleIdField.setText(String.valueOf(saleIdValue));
            loadPaymentsForSale(saleIdValue);
            updatePaymentSummary(saleIdValue);

        } catch (Exception e) {
            paymentMessageLabel.setText("Could not add payment");
        }
    }

    // payment status is always calculated automatically, not editable
    @FXML
    void doUpdatePaymentStatus() {
        paymentMessageLabel.setText("Payment status is calculated automatically from paid and remaining amounts");
    }

    // load all payments into the payment table
    @FXML
    void doShowPayments() {

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();
            Statement calcStmt = mycon.createStatement(); // separate stmt for status calculation inside loop

            String sql = getPaymentQuery(0) + " order by p.Payment_Date desc";

            ResultSet rs = stmt.executeQuery(sql);
            ObservableList<PaymentRow> list = FXCollections.observableArrayList();

            while (rs.next()) {
                int saleId = rs.getInt("Sale_ID");
                // calculate status from total vs paid, not from the stored value
                String status = calculatePaymentStatus(getSaleTotal(calcStmt, saleId), getPaidAmount(calcStmt, saleId));
                list.add(new PaymentRow(
                        rs.getInt("Payment_ID"),
                        saleId,
                        rs.getDouble("Amount"),
                        rs.getString("Payment_Method"),
                        status,
                        rs.getString("Payment_Date")
                ));
            }

            paymentTable.setItems(list);
            setPaymentSummary(0, 0);

            rs.close();
            calcStmt.close();
            stmt.close();
            mycon.close();

        } catch (Exception e) {
            paymentMessageLabel.setText("Could not load payments");
        }
    }

    // load payments for a specific sale only
    private void loadPaymentsForSale(int saleIdValue) {

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();
            Statement calcStmt = mycon.createStatement();

            String sql = getPaymentQuery(saleIdValue) + " order by p.Payment_Date desc";

            ResultSet rs = stmt.executeQuery(sql);
            ObservableList<PaymentRow> list = FXCollections.observableArrayList();

            while (rs.next()) {
                int saleId = rs.getInt("Sale_ID");
                String status = calculatePaymentStatus(getSaleTotal(calcStmt, saleId), getPaidAmount(calcStmt, saleId));
                list.add(new PaymentRow(
                        rs.getInt("Payment_ID"),
                        saleId,
                        rs.getDouble("Amount"),
                        rs.getString("Payment_Method"),
                        status,
                        rs.getString("Payment_Date")
                ));
            }

            paymentTable.setItems(list);
            updatePaymentSummary(saleIdValue);

            rs.close();
            calcStmt.close();
            stmt.close();
            mycon.close();

        } catch (Exception e) {
            paymentMessageLabel.setText("Could not load sale payments");
        }
    }

    // search payments by the selected type
    @FXML
    void doSearchPayment() {

        String type = paymentSearchTypeCombo.getValue();
        String value = paymentSearchField.getText();

        if (value.isEmpty()) {
            paymentSearchMessageLabel.setText("Enter a search value");
            return;
        }

        if ((type.equals("Payment ID") || type.equals("Sale ID")) && !isPositiveInt(value)) {
            paymentSearchMessageLabel.setText(type + " must be a positive number");
            return;
        }

        if (type.equals("Amount") && !isNonNegativeDouble(value)) {
            paymentSearchMessageLabel.setText("Amount must be a valid number");
            return;
        }

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();
            Statement calcStmt = mycon.createStatement();

            String sql = getPaymentQuery(0);

            // add filter based on selected search type
            if (type.equals("Payment ID")) {
                sql += "where p.Payment_ID = " + value + " ";
            } else if (type.equals("Sale ID")) {
                sql += "where p.Sale_ID = " + value + " ";
            } else if (type.equals("Amount")) {
                sql += "where p.Amount = " + value + " ";
            } else if (type.equals("Method")) {
                sql += "where p.Payment_Method like '%" + value + "%' ";
            } else if (type.equals("Date")) {
                sql += "where p.Payment_Date = '" + value + "' ";
            }

            sql += "order by p.Payment_Date desc";

            ResultSet rs = stmt.executeQuery(sql);
            ObservableList<PaymentRow> list = FXCollections.observableArrayList();

            while (rs.next()) {
                int saleId = rs.getInt("Sale_ID");
                String status = calculatePaymentStatus(getSaleTotal(calcStmt, saleId), getPaidAmount(calcStmt, saleId));
                // status filter is done in Java since it is calculated not stored
                if (type.equals("Status") && !status.toLowerCase().contains(value.toLowerCase())) continue;
                list.add(new PaymentRow(rs.getInt("Payment_ID"), rs.getInt("Sale_ID"),
                        rs.getDouble("Amount"), rs.getString("Payment_Method"),
                        status, rs.getString("Payment_Date")));
            }

            paymentTable.setItems(list);

            if (type.equals("Sale ID") && isPositiveInt(value)) {
                updatePaymentSummary(Integer.parseInt(value));
            } else {
                setPaymentSummary(0, 0);
            }

            if (list.isEmpty()) {
                paymentSearchMessageLabel.setText("No payments found");
            } else {
                paymentSearchMessageLabel.setText("Found " + list.size() + " records");
            }

            rs.close();
            calcStmt.close();
            stmt.close();
            mycon.close();

        } catch (Exception e) {
            paymentSearchMessageLabel.setText("Search failed");
        }
    }

    // add a warranty for a product in a sale
    @FXML
    void doAddWarranty() {

        String saleId = warrantySaleIdField.getText();
        String selectedProduct = warrantyProductCombo.getValue();
        String number = warrantyNumberField.getText();
        String type = warrantyTypeCombo.getValue();
        Integer periodNumber = periodValue.getValue();
        String unit = periodUnit.getValue();

        if (saleId.isEmpty() || selectedProduct == null || number.isEmpty() || type == null || periodNumber == null || unit == null || warrantyStartDatePicker.getValue() == null) {
            warrantyMessageLabel.setText("Fill all warranty fields");
            return;
        }

        if (!isPositiveInt(saleId)) {
            warrantyMessageLabel.setText("Sale ID must be a positive number");
            return;
        }

        int saleIdValue = Integer.parseInt(saleId);
        int productId = getProductIdFromCombo(selectedProduct);
        String period = periodNumber + " " + unit;
        String date = warrantyStartDatePicker.getValue().toString();

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();

            // walk-in customer cannot have a warranty
            ResultSet customerCheck = stmt.executeQuery("select s.Customer_ID from Sale s where s.Sale_ID=" + saleIdValue);
            if (customerCheck.next() && customerCheck.getInt("Customer_ID") == 1) {
                customerCheck.close();
                stmt.close();
                mycon.close();
                warrantyMessageLabel.setText("Walk-in customer cannot have a warranty");
                return;
            }
            customerCheck.close();

            // check product is in this sale and get sale date
            ResultSet check = stmt.executeQuery("select sd.Quantity, s.Sale_Date from Sale_Details sd, Sale s "
                    + "where sd.Sale_ID=s.Sale_ID and sd.Sale_ID=" + saleIdValue + " and sd.Product_ID=" + productId);
            if (!check.next()) {
                check.close();
                stmt.close();
                mycon.close();
                warrantyMessageLabel.setText("Product is not in this sale detail");
                return;
            }
            int soldQuantity = check.getInt("Quantity");
            java.sql.Date saleDate = check.getDate("Sale_Date");
            check.close();

            // warranty cannot start before the sale date
            if (warrantyStartDatePicker.getValue().isBefore(saleDate.toLocalDate())) {
                stmt.close();
                mycon.close();
                warrantyMessageLabel.setText("Warranty start date cannot be before sale date");
                return;
            }

            // warranty count cannot exceed the sold quantity
            ResultSet countRs = stmt.executeQuery("select count(*) as WarrantyCount from Warranty where Sale_ID="
                    + saleIdValue + " and Product_ID=" + productId);
            countRs.next();
            int warrantyCount = countRs.getInt("WarrantyCount");
            countRs.close();

            if (warrantyCount >= soldQuantity) {
                stmt.close();
                mycon.close();
                warrantyMessageLabel.setText("Warranty count cannot be more than sold quantity");
                return;
            }

            PreparedStatement insert = mycon.prepareStatement(
                    "insert into Warranty(Sale_ID, Product_ID, Warranty_Number, Warranty_Type, Warranty_Period, Start_Date) values(?,?,?,?,?,?)");
            insert.setInt(1, saleIdValue);
            insert.setInt(2, productId);
            insert.setString(3, number);
            insert.setString(4, type);
            insert.setString(5, period);
            insert.setDate(6, java.sql.Date.valueOf(date));
            insert.executeUpdate();
            insert.close();

            stmt.close();
            mycon.close();

            warrantyMessageLabel.setText("Warranty added successfully");
            clearWarrantyItemFields();
            warrantySaleIdField.setText(String.valueOf(saleIdValue));
            loadWarrantyProductsForSale(saleIdValue);
            loadWarrantiesForSale(saleIdValue);

        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("Duplicate")) {
                warrantyMessageLabel.setText("This warranty already exists");
            } else {
                warrantyMessageLabel.setText("Could not add warranty");
            }
        }
    }

    // delete a warranty record
    @FXML
    void doDeleteWarranty() {

        String saleId = warrantySaleIdField.getText();
        String selectedProduct = warrantyProductCombo.getValue();
        String number = warrantyNumberField.getText();

        if (saleId.isEmpty() || selectedProduct == null || number.isEmpty()) {
            warrantyMessageLabel.setText("Enter Sale ID, Product and Warranty Number to delete");
            return;
        }

        if (!isPositiveInt(saleId)) {
            warrantyMessageLabel.setText("Sale ID must be a positive number");
            return;
        }

        int saleIdValue = Integer.parseInt(saleId);
        int productId = getProductIdFromCombo(selectedProduct);

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();

            int rows = stmt.executeUpdate("delete from Warranty where Sale_ID = " + saleIdValue
                    + " and Product_ID = " + productId + " and Warranty_Number = '" + number + "'");

            stmt.close();
            mycon.close();

            if (rows > 0) {
                warrantyMessageLabel.setText("Warranty deleted successfully");
            } else {
                warrantyMessageLabel.setText("Warranty not found");
            }

            loadWarrantiesForSale(saleIdValue);

        } catch (Exception e) {
            warrantyMessageLabel.setText("Could not delete warranty");
        }
    }

    // load all warranties into the warranty table
    @FXML
    void doShowWarranties() {

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();
            ResultSet rs = stmt.executeQuery(getWarrantyQuery(0));
            ObservableList<WarrantyRow> list = FXCollections.observableArrayList();

            while (rs.next()) {
                list.add(new WarrantyRow(rs.getInt("Sale_ID"), rs.getString("Product_Name"),
                        rs.getString("Warranty_Number"), rs.getString("Warranty_Type"),
                        rs.getString("Warranty_Period"), rs.getString("Start_Date")));
            }

            warrantyTable.setItems(list);
            rs.close();
            stmt.close();
            mycon.close();

        } catch (Exception e) {
            warrantyMessageLabel.setText("Could not load warranties");
        }
    }

    // search warranties by the selected type
    @FXML
    void doSearchWarranty() {

        String type = warrantySearchTypeCombo.getValue();
        String value = warrantySearchField.getText();

        if (value.isEmpty()) {
            warrantySearchMessageLabel.setText("Enter a search value");
            return;
        }

        if (type.equals("Sale ID") && !isPositiveInt(value)) {
            warrantySearchMessageLabel.setText("Sale ID must be a positive number");
            return;
        }

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();
            String sql = "select w.Sale_ID, p.Product_Name, w.Warranty_Number, w.Warranty_Type, w.Warranty_Period, w.Start_Date "
                    + "from Warranty w join Product p on w.Product_ID = p.Product_ID ";

            // add filter based on selected search type
            if (type.equals("Sale ID")) {
                sql += "where w.Sale_ID = " + value + " ";
            } else if (type.equals("Product Name")) {
                sql += "where p.Product_Name like '%" + value + "%' ";
            } else if (type.equals("Warranty Number")) {
                sql += "where w.Warranty_Number like '%" + value + "%' ";
            } else if (type.equals("Type")) {
                sql += "where w.Warranty_Type like '%" + value + "%' ";
            } else if (type.equals("Period")) {
                sql += "where w.Warranty_Period like '%" + value + "%' ";
            } else if (type.equals("Start Date")) {
                sql += "where w.Start_Date = '" + value + "' ";
            }

            sql += "order by w.Sale_ID desc";

            ResultSet rs = stmt.executeQuery(sql);
            ObservableList<WarrantyRow> list = FXCollections.observableArrayList();

            while (rs.next()) {
                list.add(new WarrantyRow(rs.getInt("Sale_ID"), rs.getString("Product_Name"),
                        rs.getString("Warranty_Number"), rs.getString("Warranty_Type"),
                        rs.getString("Warranty_Period"), rs.getString("Start_Date")));
            }

            warrantyTable.setItems(list);
            if (list.isEmpty()) {
                warrantySearchMessageLabel.setText("No warranties found");
            } else {
                warrantySearchMessageLabel.setText("Found " + list.size() + " records");
            }

            rs.close();
            stmt.close();
            mycon.close();

        } catch (Exception e) {
            warrantySearchMessageLabel.setText("Search failed");
        }
    }

    // load warranties for a specific sale only
    private void loadWarrantiesForSale(int saleIdValue) {

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();
            ResultSet rs = stmt.executeQuery(getWarrantyQuery(saleIdValue));
            ObservableList<WarrantyRow> list = FXCollections.observableArrayList();

            while (rs.next()) {
                list.add(new WarrantyRow(rs.getInt("Sale_ID"), rs.getString("Product_Name"),
                        rs.getString("Warranty_Number"), rs.getString("Warranty_Type"),
                        rs.getString("Warranty_Period"), rs.getString("Start_Date")));
            }

            warrantyTable.setItems(list);
            rs.close();
            stmt.close();
            mycon.close();

        } catch (Exception e) {
            warrantyMessageLabel.setText("Could not load sale warranties");
        }
    }

    // fill the warranty product combo with products from a specific sale
    private void loadWarrantyProductsForSale(int saleIdValue) {

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();
            ResultSet rs = stmt.executeQuery("select p.Product_ID, p.Product_Name, p.Price from Sale_Details sd "
                    + "join Product p on sd.Product_ID = p.Product_ID "
                    + "where sd.Sale_ID = " + saleIdValue + " order by p.Product_Name");
            ObservableList<String> products = FXCollections.observableArrayList();

            while (rs.next()) {
                products.add(rs.getInt("Product_ID") + " - " + rs.getString("Product_Name") + " ($" + rs.getDouble("Price") + ")");
            }

            warrantyProductCombo.setItems(products);
            rs.close();
            stmt.close();
            mycon.close();

        } catch (Exception e) {
            warrantyMessageLabel.setText("Could not load sale products");
        }
    }

    // build the warranty SELECT query, filtered by sale if saleIdValue > 0
    private String getWarrantyQuery(int saleIdValue) {
        String sql = "select w.Sale_ID, p.Product_Name, w.Warranty_Number, w.Warranty_Type, w.Warranty_Period, w.Start_Date "
                + "from Warranty w join Product p on w.Product_ID = p.Product_ID ";
        if (saleIdValue > 0) {
            sql += "where w.Sale_ID = " + saleIdValue + " ";
        }
        sql += "order by w.Sale_ID desc";
        return sql;
    }

    // build the payment SELECT query, filtered by sale if saleIdValue > 0
    private String getPaymentQuery(int saleIdValue) {
        String sql = "select p.Payment_ID,p.Sale_ID,p.Amount,p.Payment_Method,p.Payment_Date from Payment p ";
        if (saleIdValue > 0) {
            sql += "where p.Sale_ID = " + saleIdValue + " ";
        }
        return sql;
    }

    // reload available storage sections for the return section combo
    private void loadReturnSections() {
        returnSectionCombo.getItems().clear();
        returnSectionCombo.setValue(null);

        // rejected returns don't go back to storage
        if (!"Approved".equals(returnStatusCombo.getValue())) {
            returnSectionCombo.setDisable(true);
            returnSectionLabel.setText("Rejected returns do not change stock");
            return;
        }

        returnSectionCombo.setDisable(false);
        if (returnProductCombo.getValue() == null || !isPositiveInt(returnQuantityField.getText())) {
            returnSectionLabel.setText("Choose product and enter quantity");
            return;
        }

        int productId = getProductIdFromCombo(returnProductCombo.getValue());
        int quantity = Integer.parseInt(returnQuantityField.getText());

        try {
            Connection con = DBconnection.getConnection();
            Statement stmt = con.createStatement();

            // get unit size for this product from its category
            ResultSet sizeRs = stmt.executeQuery("select c.Unit_Size from Product p, Category c "
                    + "where p.Category_ID=c.Category_ID and p.Product_ID=" + productId);
            if (!sizeRs.next()) {
                sizeRs.close();
                stmt.close();
                con.close();
                returnSectionLabel.setText("Product unit size was not found");
                return;
            }
            int unitSize = sizeRs.getInt("Unit_Size");
            sizeRs.close();

            // get each section with how much space is already used
            String sql = "select ss.Section_ID, ss.Section_Name, ss.Capacity, "
                    + "sum(sd.Quantity * c.Unit_Size) as UsedCapacity "
                    + "from Storage_Section ss "
                    + "left join Storage_Details sd on ss.Section_ID=sd.Section_ID "
                    + "left join Product p on sd.Product_ID=p.Product_ID "
                    + "left join Category c on p.Category_ID=c.Category_ID "
                    + "group by ss.Section_ID, ss.Section_Name, ss.Capacity order by ss.Section_ID";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                double usedCapacity = rs.getDouble("UsedCapacity");
                if (rs.wasNull()) { usedCapacity = 0; }
                double free = rs.getDouble("Capacity") - usedCapacity;
                // only show sections that have enough room for the return quantity
                if (free >= quantity * unitSize) {
                    int availableUnits = (int) (free / unitSize);
                    returnSectionCombo.getItems().add(rs.getInt("Section_ID") + " - "
                            + rs.getString("Section_Name") + " (available: " + availableUnits + " units)");
                }
            }

            rs.close();
            stmt.close();
            con.close();

            if (returnSectionCombo.getItems().isEmpty()) {
                returnSectionLabel.setText("No section has enough capacity");
            } else {
                returnSectionLabel.setText("Choose one of the available sections");
            }
        } catch (Exception e) {
            returnSectionLabel.setText("Could not load storage sections");
        }
    }

    // add a return record and restore stock if approved
    @FXML
    void doAddReturn() {
        String saleId = returnSaleIdField.getText();
        String selectedProduct = returnProductCombo.getValue();
        String qty = returnQuantityField.getText();
        String reason = returnReasonField.getText();
        String status = returnStatusCombo.getValue();

        if (saleId.isEmpty() || selectedProduct == null || qty.isEmpty() || status == null || returnDatePicker.getValue() == null) {
            returnMessageLabel.setText("Fill Sale ID, Product, Quantity, Status and Date");
            return;
        }
        if (!isPositiveInt(saleId) || !isPositiveInt(qty)) {
            returnMessageLabel.setText("Sale ID and Quantity must be positive numbers");
            return;
        }
        if (reason.isEmpty()) {
            reason = "No reason";
        }

        int saleIdValue = Integer.parseInt(saleId);
        int productId = getProductIdFromCombo(selectedProduct);
        int returnQty = Integer.parseInt(qty);
        String date = returnDatePicker.getValue().toString();
        int sectionId = 0;

        if (status.equals("Approved")) {
            if (returnSectionCombo.getValue() == null) {
                returnMessageLabel.setText("Choose a storage section for the returned product");
                return;
            }
            sectionId = getIdFromCombo(returnSectionCombo.getValue());
        }

        Connection mycon = null;
        Statement stmt = null;

        try {
            mycon = DBconnection.getConnection();
            mycon.setAutoCommit(false);
            stmt = mycon.createStatement();

            // walk-in customer cannot have a return
            ResultSet customerCheck = stmt.executeQuery("select s.Customer_ID from Sale s where s.Sale_ID=" + saleIdValue);
            if (customerCheck.next() && customerCheck.getInt("Customer_ID") == 1) {
                customerCheck.close();
                mycon.rollback();
                returnMessageLabel.setText("Walk-in customer cannot have a return");
                return;
            }
            customerCheck.close();

            // check product is in this sale and get sale date
            ResultSet soldRs = stmt.executeQuery("select sd.Quantity, s.Sale_Date from Sale_Details sd, Sale s "
                    + "where sd.Sale_ID=s.Sale_ID and sd.Sale_ID=" + saleIdValue + " and sd.Product_ID=" + productId);
            if (!soldRs.next()) {
                soldRs.close();
                mycon.rollback();
                returnMessageLabel.setText("Product is not in this sale");
                return;
            }
            int soldQty = soldRs.getInt("Quantity");
            java.sql.Date saleDate = soldRs.getDate("Sale_Date");
            soldRs.close();

            // return date cannot be before the sale date
            if (returnDatePicker.getValue().isBefore(saleDate.toLocalDate())) {
                mycon.rollback();
                returnMessageLabel.setText("Return date cannot be before sale date");
                return;
            }

            // returned quantity cannot exceed what was sold minus what was already returned
            int returnedBefore = getReturnedQuantity(stmt, saleIdValue, productId);
            if (returnQty + returnedBefore > soldQty) {
                mycon.rollback();
                returnMessageLabel.setText("Return quantity is more than sold quantity. Available to return: " + (soldQty - returnedBefore));
                return;
            }

            PreparedStatement insert = mycon.prepareStatement(
                    "insert into Return_Record(Sale_ID, Product_ID, Return_Date, Quantity, Return_Reason, Return_Status) values(?,?,?,?,?,?)");
            insert.setInt(1, saleIdValue);
            insert.setInt(2, productId);
            insert.setDate(3, java.sql.Date.valueOf(date));
            insert.setInt(4, returnQty);
            insert.setString(5, reason);
            insert.setString(6, status);
            insert.executeUpdate();
            insert.close();

            // restore stock only for approved returns
            if (status.equals("Approved")) {
                restoreStockForReturn(stmt, productId, returnQty, sectionId);
            }

            mycon.commit();
            returnMessageLabel.setText("Return added successfully");
            clearReturnItemFields();
            returnSaleIdField.setText(String.valueOf(saleIdValue));
            loadReturnProductsForSale(saleIdValue);
            loadReturnsForSale(saleIdValue);
            loadDetailsForSale(saleIdValue);
            loadPaymentsForSale(saleIdValue);
            updatePaymentSummary(saleIdValue);
            doShowSales();
        } catch (Exception e) {
            try { if (mycon != null) mycon.rollback(); } catch (Exception ex) {}
            returnMessageLabel.setText("Could not add return");
        } finally {
            closeTransaction(mycon, stmt);
        }
    }

    // delete a return and reduce stock if it was approved
    @FXML
    void doDeleteReturn() {
        String returnId = returnIdField.getText();
        if (returnId.isEmpty()) {
            returnMessageLabel.setText("Enter Return ID to delete");
            return;
        }
        if (!isPositiveInt(returnId)) {
            returnMessageLabel.setText("Return ID must be a positive number");
            return;
        }

        Connection mycon = null;
        Statement stmt = null;

        try {
            mycon = DBconnection.getConnection();
            mycon.setAutoCommit(false);
            stmt = mycon.createStatement();

            ResultSet rs = stmt.executeQuery("select Sale_ID, Product_ID, Quantity, Return_Status from Return_Record where Return_ID=" + returnId);
            if (!rs.next()) {
                rs.close();
                mycon.rollback();
                returnMessageLabel.setText("Return ID not found");
                return;
            }
            int saleIdValue = rs.getInt("Sale_ID");
            int productId = rs.getInt("Product_ID");
            int qty = rs.getInt("Quantity");
            String status = rs.getString("Return_Status");
            rs.close();

            // if the return was approved, stock was added so we remove it back
            if (status.equals("Approved")) {
                reduceStockAfterReturnDelete(stmt, productId, qty);
            }

            stmt.executeUpdate("delete from Return_Record where Return_ID=" + returnId);
            mycon.commit();
            returnMessageLabel.setText("Return deleted successfully");
            clearReturnFields();
            returnSaleIdField.setText(String.valueOf(saleIdValue));
            loadReturnsForSale(saleIdValue);
            loadDetailsForSale(saleIdValue);
            loadPaymentsForSale(saleIdValue);
            updatePaymentSummary(saleIdValue);
            doShowSales();
        } catch (Exception e) {
            try { if (mycon != null) mycon.rollback(); } catch (Exception ex) {}
            returnMessageLabel.setText("Could not delete return");
        } finally {
            closeTransaction(mycon, stmt);
        }
    }

    // load all returns into the return table
    @FXML
    void doShowReturns() {
        loadReturns("select r.Return_ID, r.Sale_ID, p.Product_Name, r.Quantity, r.Return_Reason, r.Return_Status, r.Return_Date "
                + "from Return_Record r, Product p where r.Product_ID=p.Product_ID order by r.Return_Date desc", false);
    }

    // search returns by the selected type
    @FXML
    void doSearchReturn() {
        String type = returnSearchTypeCombo.getValue();
        String value = returnSearchField.getText();

        if (value.isEmpty()) {
            returnSearchMessageLabel.setText("Enter a search value");
            return;
        }
        if ((type.equals("Return ID") || type.equals("Sale ID")) && !isPositiveInt(value)) {
            returnSearchMessageLabel.setText(type + " must be a positive number");
            return;
        }
        if (type.equals("Date") && !isValidDate(value)) {
            returnSearchMessageLabel.setText("Date format must be yyyy-mm-dd");
            return;
        }

        String sql = "select r.Return_ID, r.Sale_ID, p.Product_Name, r.Quantity, r.Return_Reason, r.Return_Status, r.Return_Date "
                + "from Return_Record r, Product p where r.Product_ID=p.Product_ID ";

        // add filter based on selected search type
        if (type.equals("Return ID")) sql += "and r.Return_ID=" + value + " ";
        else if (type.equals("Sale ID")) sql += "and r.Sale_ID=" + value + " ";
        else if (type.equals("Product Name")) sql += "and p.Product_Name like '%" + value + "%' ";
        else if (type.equals("Status")) sql += "and r.Return_Status like '%" + value + "%' ";
        else if (type.equals("Date")) sql += "and r.Return_Date='" + value + "' ";

        sql += "order by r.Return_Date desc";
        loadReturns(sql, true);
    }

    // load returns for a specific sale only
    private void loadReturnsForSale(int saleIdValue) {
        loadReturns("select r.Return_ID, r.Sale_ID, p.Product_Name, r.Quantity, r.Return_Reason, r.Return_Status, r.Return_Date "
                + "from Return_Record r, Product p where r.Product_ID=p.Product_ID and r.Sale_ID=" + saleIdValue + " order by r.Return_Date desc", false);
    }

    // shared method used by doShowReturns, doSearchReturn, and loadReturnsForSale
    private void loadReturns(String sql, boolean search) {
        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ObservableList<ReturnRow> list = FXCollections.observableArrayList();

            while (rs.next()) {
                list.add(new ReturnRow(rs.getInt("Return_ID"), rs.getInt("Sale_ID"), rs.getString("Product_Name"), rs.getInt("Quantity"), rs.getString("Return_Reason"), rs.getString("Return_Status"), rs.getString("Return_Date")));
            }

            returnTable.setItems(list);
            if (search) {
                if (list.isEmpty()) returnSearchMessageLabel.setText("No returns found");
                else returnSearchMessageLabel.setText("Found " + list.size() + " records");
            }

            rs.close();
            stmt.close();
            mycon.close();
        } catch (Exception e) {
            returnMessageLabel.setText("Could not load returns");
        }
    }

    // fill the return product combo with products from a specific sale
    private void loadReturnProductsForSale(int saleIdValue) {
        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();
            ResultSet rs = stmt.executeQuery("select p.Product_ID, p.Product_Name, p.Price from Sale_Details sd, Product p "
                    + "where sd.Product_ID=p.Product_ID and sd.Sale_ID=" + saleIdValue + " order by p.Product_Name");
            ObservableList<String> products = FXCollections.observableArrayList();

            while (rs.next()) {
                products.add(rs.getInt("Product_ID") + " - " + rs.getString("Product_Name") + " ($" + rs.getDouble("Price") + ")");
            }

            returnProductCombo.setItems(products);
            rs.close();
            stmt.close();
            mycon.close();
        } catch (Exception e) {
            returnMessageLabel.setText("Could not load sale products");
        }
    }

    // get total returned quantity for a product in a sale (excluding rejected returns)
    private int getReturnedQuantity(Statement stmt, int saleIdValue, int productId) throws Exception {
        ResultSet rs = stmt.executeQuery("select Quantity, Return_Status from Return_Record where Sale_ID=" + saleIdValue + " and Product_ID=" + productId);
        int total = 0;
        while (rs.next()) {
            if (!rs.getString("Return_Status").equals("Rejected")) {
                total += rs.getInt("Quantity");
            }
        }
        rs.close();
        return total;
    }

    // get total available quantity for a product across all storage sections
    private int getAvailableQuantity(Statement stmt, int productId) throws Exception {
        ResultSet rs = stmt.executeQuery("select sum(Quantity) as AvailableQty from Storage_Details where Product_ID = " + productId);
        int available = 0;
        if (rs.next()) {
            available = rs.getInt("AvailableQty");
            if (rs.wasNull()) { available = 0; }
        }
        rs.close();
        return available;
    }

    // take stock from sections starting from the first available, record each movement
    private void reduceStockForSale(Statement stmt, int productId, int quantity) throws Exception {
        int remaining = quantity;
        ResultSet rs = stmt.executeQuery("select Product_ID, Section_ID, Quantity from Storage_Details "
                + "where Product_ID = " + productId + " and Quantity > 0 order by Section_ID");

        while (rs.next() && remaining > 0) {
            int sectionId = rs.getInt("Section_ID");
            int sectionQty = rs.getInt("Quantity");
            int takeQty = Math.min(remaining, sectionQty);

            Statement updateStmt = stmt.getConnection().createStatement();
            updateStmt.executeUpdate("update Storage_Details set Quantity = Quantity - " + takeQty
                    + " where Product_ID = " + productId + " and Section_ID = " + sectionId);
            updateStmt.executeUpdate("insert into Stock_Movement_History(Product_ID, Source_Section_ID, Destination_Section_ID, Movement_Type, Quantity, Movement_Date) values("
                    + productId + "," + sectionId + ", null, 'Sale', " + takeQty + ", curdate())");
            updateStmt.close();

            remaining -= takeQty;
        }
        rs.close();
        if (remaining > 0) {
            throw new Exception("Not enough stock");
        }
    }

    // find a section with enough space and restore stock when a sale detail is deleted
    private void restoreStockAfterDelete(Statement stmt, int productId, int quantity) throws Exception {
        // get unit size for this product from its category
        ResultSet sizeRs = stmt.executeQuery("select c.Unit_Size from Product p, Category c "
                + "where p.Category_ID=c.Category_ID and p.Product_ID=" + productId);
        if (!sizeRs.next()) throw new Exception("Product unit size not found");
        int unitSize = sizeRs.getInt("Unit_Size");
        sizeRs.close();

        // find first section with enough free space
        int sectionId = 0;
        ResultSet sectionRs = stmt.executeQuery("select ss.Section_ID,ss.Capacity,"
                + "sum(sd.Quantity*c.Unit_Size) as UsedCapacity from Storage_Section ss "
                + "left join Storage_Details sd on ss.Section_ID=sd.Section_ID "
                + "left join Product p on sd.Product_ID=p.Product_ID "
                + "left join Category c on p.Category_ID=c.Category_ID "
                + "group by ss.Section_ID,ss.Capacity order by ss.Section_ID");
        while (sectionRs.next()) {
            double usedCapacity = sectionRs.getDouble("UsedCapacity");
            if (sectionRs.wasNull()) { usedCapacity = 0; }
            double free = sectionRs.getDouble("Capacity") - usedCapacity;
            if (free >= quantity * unitSize) {
                sectionId = sectionRs.getInt("Section_ID");
                break;
            }
        }
        sectionRs.close();

        if (sectionId == 0) throw new Exception("No storage section has enough capacity");

        // update if row exists or insert new
        ResultSet exists = stmt.executeQuery("select Quantity from Storage_Details where Product_ID = " + productId + " and Section_ID = " + sectionId);
        boolean hasRow = exists.next();
        exists.close();

        if (hasRow) {
            stmt.executeUpdate("update Storage_Details set Quantity = Quantity + " + quantity
                    + " where Product_ID = " + productId + " and Section_ID = " + sectionId);
        } else {
            stmt.executeUpdate("insert into Storage_Details(Product_ID, Section_ID, Quantity) values("
                    + productId + "," + sectionId + "," + quantity + ")");
        }

        stmt.executeUpdate("insert into Stock_Movement_History(Product_ID, Source_Section_ID, Destination_Section_ID, Movement_Type, Quantity, Movement_Date) values("
                + productId + ", null, " + sectionId + ", 'Sale Detail Deleted', " + quantity + ", curdate())");
    }

    // restore stock to a chosen section when a return is approved
    private void restoreStockForReturn(Statement stmt, int productId, int quantity, int sectionId) throws Exception {
        // check section capacity
        ResultSet capRs = stmt.executeQuery("select ss.Capacity, sum(sd.Quantity*c.Unit_Size) as UsedCapacity "
                + "from Storage_Section ss left join Storage_Details sd on ss.Section_ID=sd.Section_ID "
                + "left join Product p on sd.Product_ID=p.Product_ID left join Category c on p.Category_ID=c.Category_ID "
                + "where ss.Section_ID=" + sectionId + " group by ss.Section_ID,ss.Capacity");
        if (!capRs.next()) throw new Exception("Storage section not found");
        double capacity = capRs.getDouble("Capacity");
        double used = capRs.getDouble("UsedCapacity");
        if (capRs.wasNull()) { used = 0; }
        capRs.close();

        // get unit size
        ResultSet sizeRs = stmt.executeQuery("select c.Unit_Size from Product p, Category c "
                + "where p.Category_ID=c.Category_ID and p.Product_ID=" + productId);
        if (!sizeRs.next()) throw new Exception("Product unit size not found");
        int unitSize = sizeRs.getInt("Unit_Size");
        sizeRs.close();

        if (used + quantity * unitSize > capacity) {
            throw new Exception("Selected section does not have enough capacity");
        }

        // update if row exists or insert new
        ResultSet exists = stmt.executeQuery("select Quantity from Storage_Details where Product_ID=" + productId + " and Section_ID=" + sectionId);
        boolean hasRow = exists.next();
        exists.close();

        if (hasRow) {
            stmt.executeUpdate("update Storage_Details set Quantity=Quantity+" + quantity + " where Product_ID=" + productId + " and Section_ID=" + sectionId);
        } else {
            stmt.executeUpdate("insert into Storage_Details(Product_ID, Section_ID, Quantity) values(" + productId + "," + sectionId + "," + quantity + ")");
        }

        stmt.executeUpdate("insert into Stock_Movement_History(Product_ID, Source_Section_ID, Destination_Section_ID, Movement_Type, Quantity, Movement_Date) values("
                + productId + ", null, " + sectionId + ", 'Return', " + quantity + ", curdate())");
    }

    // reduce stock when an approved return record is deleted
    private void reduceStockAfterReturnDelete(Statement stmt, int productId, int quantity) throws Exception {
        int remaining = quantity;
        ResultSet rs = stmt.executeQuery("select Product_ID, Section_ID, Quantity from Storage_Details where Product_ID=" + productId + " and Quantity>0 order by Section_ID");

        while (rs.next() && remaining > 0) {
            int sectionId = rs.getInt("Section_ID");
            int sectionQty = rs.getInt("Quantity");
            int takeQty = Math.min(remaining, sectionQty);
            Statement updateStmt = stmt.getConnection().createStatement();
            updateStmt.executeUpdate("update Storage_Details set Quantity=Quantity-" + takeQty + " where Product_ID=" + productId + " and Section_ID=" + sectionId);
            updateStmt.executeUpdate("insert into Stock_Movement_History(Product_ID, Source_Section_ID, Destination_Section_ID, Movement_Type, Quantity, Movement_Date) values("
                    + productId + "," + sectionId + ", null, 'Return Deleted', " + takeQty + ", curdate())");
            updateStmt.close();
            remaining -= takeQty;
        }
        rs.close();

        if (remaining > 0) {
            throw new Exception("Not enough stock to delete return");
        }
    }

    // get sale total after subtracting approved returned items value
    private double getSaleTotal(Statement stmt, int saleIdValue) throws Exception {
        // read each detail row and calculate total in Java to safely handle NULL discounts
        ResultSet saleRs = stmt.executeQuery("select Unit_Price, Quantity, Discount "
                + "from Sale_Details where Sale_ID=" + saleIdValue);
        double saleTotal = 0;
        while (saleRs.next()) {
            double unitPrice = saleRs.getDouble("Unit_Price");
            int quantity     = saleRs.getInt("Quantity");
            double discount  = saleRs.getDouble("Discount");
            if (saleRs.wasNull()) { discount = 0; }
            saleTotal += (unitPrice * quantity) - discount;
        }
        saleRs.close();

        // read each approved return row and subtract its value
        ResultSet returnRs = stmt.executeQuery(
                "select r.Quantity as RetQty, sd.Unit_Price, sd.Discount, sd.Quantity as SoldQty "
                + "from Return_Record r, Sale_Details sd "
                + "where r.Sale_ID=sd.Sale_ID and r.Product_ID=sd.Product_ID "
                + "and r.Return_Status='Approved' and r.Sale_ID=" + saleIdValue);
        double returnTotal = 0;
        while (returnRs.next()) {
            int retQty       = returnRs.getInt("RetQty");
            double unitPrice = returnRs.getDouble("Unit_Price");
            double discount  = returnRs.getDouble("Discount");
            if (returnRs.wasNull()) { discount = 0; }
            int soldQty      = returnRs.getInt("SoldQty");
            double netUnitPrice = unitPrice - discount / soldQty;
            returnTotal += netUnitPrice * retQty;
        }
        returnRs.close();

        return saleTotal - returnTotal;
    }

    // get total amount paid so far for a sale
    private double getPaidAmount(Statement stmt, int saleIdValue) throws Exception {
        ResultSet rs = stmt.executeQuery("select sum(Amount) as Paid from Payment where Sale_ID = " + saleIdValue);
        double paid = 0;
        if (rs.next()) {
            paid = rs.getDouble("Paid");
            if (rs.wasNull()) { paid = 0; }
        }
        rs.close();
        return paid;
    }

    // recalculate and update the payment summary labels for a sale
    private void updatePaymentSummary(int saleIdValue) {
        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();
            double total = getSaleTotal(stmt, saleIdValue);
            double paid = getPaidAmount(stmt, saleIdValue);
            setPaymentSummary(total, paid);
            stmt.close();
            mycon.close();
        } catch (Exception e) {
            setPaymentSummary(0, 0);
        }
    }

    // set the 4 payment summary labels from total and paid values
    private void setPaymentSummary(double total, double paid) {
        double remaining = total - paid;
        if (remaining < 0) remaining = 0;
        paymentSaleTotalLabel.setText("Sale Total: " + String.format("%.2f", total));
        paymentPaidAmountLabel.setText("Paid Amount: " + String.format("%.2f", paid));
        paymentRemainingLabel.setText("Remaining: " + String.format("%.2f", remaining));
        paymentStatusLabel.setText("Status: " + calculatePaymentStatus(total, paid));
    }

    // calculate payment status from total and paid values
    private String calculatePaymentStatus(double total, double paid) {
        if (total <= 0 || paid <= 0) {
            return "Pending";
        }
        if (paid + 0.0001 < total) {
            return "Partial";
        }
        if (paid > total + 0.0001) {
            return "Refund Due";
        }
        return "Paid";
    }

    // fill customer combo from database
    private void loadCustomersCombo() {

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();

            ResultSet rs = stmt.executeQuery("select Customer_ID, First_Name, Last_Name from Customer order by Customer_ID");

            ObservableList<String> customers = FXCollections.observableArrayList();

            while (rs.next()) {
                customers.add(rs.getInt("Customer_ID") + " - " + rs.getString("First_Name") + " " + rs.getString("Last_Name"));
            }

            saleCustomerCombo.setItems(customers);

            rs.close();
            stmt.close();
            mycon.close();

        } catch (Exception e) {
            saleMessageLabel.setText("Could not load customers");
        }
    }

    // fill employee combo from database
    private void loadEmployeesCombo() {

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();

            ResultSet rs = stmt.executeQuery("select Employee_ID, First_Name, Last_Name from Employee order by Employee_ID");

            ObservableList<String> employees = FXCollections.observableArrayList();

            while (rs.next()) {
                employees.add(rs.getInt("Employee_ID") + " - " + rs.getString("First_Name") + " " + rs.getString("Last_Name"));
            }

            saleEmployeeCombo.setItems(employees);

            rs.close();
            stmt.close();
            mycon.close();

        } catch (Exception e) {
            saleMessageLabel.setText("Could not load employees");
        }
    }

    // get the numeric ID from a combo item like "5 - Name"
    private int getIdFromCombo(String text) {
        return Integer.parseInt(text.substring(0, text.indexOf(" - ")));
    }

    // fill product combo from database
    private void loadProductsCombo() {

        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();

            ResultSet rs = stmt.executeQuery("select Product_ID, Product_Name, Price from Product order by Product_Name");

            ObservableList<String> products = FXCollections.observableArrayList();

            while (rs.next()) {
                products.add(rs.getInt("Product_ID") + " - " + rs.getString("Product_Name") + " ($" + rs.getDouble("Price") + ")");
            }

            detailProductCombo.setItems(products);

            rs.close();
            stmt.close();
            mycon.close();

        } catch (Exception e) {
            detailMessageLabel.setText("Could not load products");
        }
    }

    // get the product ID from a combo item like "5 - Name ($xx)"
    private int getProductIdFromCombo(String text) {
        return Integer.parseInt(text.substring(0, text.indexOf(" - ")));
    }

    @FXML
    void clearSaleFields() {
        saleIdField.clear();
        saleCustomerCombo.setValue(null);
        saleEmployeeCombo.setValue(null);
        saleDatePicker.setValue(null);
        saleSearchField.clear();
        saleMessageLabel.setText("");
    }

    @FXML
    void clearDetailFields() {
        detailSaleIdField.clear();
        detailProductCombo.setValue(null);
        detailQuantityField.clear();
        detailDiscountField.clear();
        detailSearchField.clear();
        detailMessageLabel.setText("");
    }

    // clear detail item fields without clearing the sale ID
    private void clearDetailItemFields() {
        detailProductCombo.setValue(null);
        detailQuantityField.clear();
        detailDiscountField.clear();
        detailSearchField.clear();
    }

    @FXML
    void clearPaymentFields() {
        paymentSaleIdField.clear();
        paymentAmountField.clear();
        paymentDatePicker.setValue(null);
        paymentSearchField.clear();
        paymentMessageLabel.setText("");
        setPaymentSummary(0, 0);
    }

    // clear payment item fields without clearing the sale ID
    private void clearPaymentItemFields() {
        paymentAmountField.clear();
        paymentDatePicker.setValue(null);
        paymentSearchField.clear();
    }

    @FXML
    void clearWarrantyFields() {
        warrantySaleIdField.clear();
        warrantyProductCombo.setValue(null);
        warrantyNumberField.clear();
        warrantyTypeCombo.setValue("Manufacturer Warranty");
        periodValue.getValueFactory().setValue(12);
        periodUnit.setValue("Months");
        warrantyStartDatePicker.setValue(null);
        warrantySearchField.clear();
        warrantyMessageLabel.setText("");
    }

    // clear warranty item fields without clearing the sale ID
    private void clearWarrantyItemFields() {
        warrantyProductCombo.setValue(null);
        warrantyNumberField.clear();
        warrantyTypeCombo.setValue("Manufacturer Warranty");
        periodValue.getValueFactory().setValue(12);
        periodUnit.setValue("Months");
        warrantyStartDatePicker.setValue(null);
    }

    @FXML
    void clearReturnFields() {
        returnIdField.clear();
        returnSaleIdField.clear();
        returnProductCombo.setValue(null);
        returnDatePicker.setValue(null);
        returnQuantityField.clear();
        returnReasonField.clear();
        returnStatusCombo.setValue("Approved");
        returnSectionCombo.setValue(null);
        returnSectionLabel.setText("Choose product and quantity");
        returnSearchField.clear();
        returnMessageLabel.setText("");
    }

    // clear return item fields without clearing the sale ID
    private void clearReturnItemFields() {
        returnIdField.clear();
        returnProductCombo.setValue(null);
        returnDatePicker.setValue(null);
        returnQuantityField.clear();
        returnReasonField.clear();
        returnStatusCombo.setValue("Approved");
        returnSectionCombo.setValue(null);
    }

    @FXML
    void notReady() {
        saleMessageLabel.setText("This section is not ready yet");
    }

    // navigation buttons
    @FXML
    void openDashboard(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1400, 840));
            stage.setMaximized(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openProducts(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/product.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1400, 840));
            stage.setMaximized(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openCustomers(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/customer.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1400, 840));
            stage.setMaximized(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openSuppliers(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/supplier.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1400, 840));
            stage.setMaximized(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openEmployees(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/employee.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1400, 840));
            stage.setMaximized(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openPurchases(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/purchases.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1400, 840));
            stage.setMaximized(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openInventory(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/inventory.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1400, 840));
            stage.setMaximized(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // shared helper to open any page
    private void openPage(ActionEvent event, String fxmlPath, String errorMessage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1400, 840));
            
            stage.setMaximized(true);
        } catch (Exception e) {
            System.out.println(errorMessage);
            e.printStackTrace();
        }
    }
    @FXML
    void openReports(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/report.fxml"));
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root,1400,840));
        stage.setMaximized(true);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    @FXML
    void openSales(ActionEvent event) {
        openPage(event, "/fxml/sales.fxml", "Cannot open sales page");
    }

    // check if a string is a valid positive integer
    private boolean isPositiveInt(String text) {
        try {
            return Integer.parseInt(text) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // check if a string is a valid number >= 0
    private boolean isNonNegativeDouble(String text) {
        try {
            return Double.parseDouble(text) >= 0;
        } catch (Exception e) {
            return false;
        }
    }

    // check if a string is a valid number > 0
    private boolean isPositiveDouble(String text) {
        try {
            return Double.parseDouble(text) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // check if a string is a valid date in yyyy-mm-dd format
    private boolean isValidDate(String text) {
        try {
            java.time.LocalDate.parse(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // close connection and reset auto commit after a transaction
    private void closeTransaction(Connection mycon, Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }

            if (mycon != null) {
                mycon.setAutoCommit(true);
                mycon.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}