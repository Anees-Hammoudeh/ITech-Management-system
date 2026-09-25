//I-Tech...

package com.mycompany.itech_management_system;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.time.LocalDate;

public class HomeController implements Initializable {

    // top summary card labels
    @FXML Label productsCountLabel;
    @FXML Label customersCountLabel;
    @FXML Label suppliersCountLabel;
    @FXML Label salesCountLabel;
    @FXML Label purchasesCountLabel;
    @FXML Label revenueLabel;
    @FXML Label totalStockLabel;
    @FXML Label pendingPaymentsLabel;
    @FXML Label returnsLabel;
    @FXML Label lowStockLabel;
    @FXML Label employeesLabel;

    // alert labels
    @FXML Label alertOneLabel;
    @FXML Label alertTwoLabel;
    @FXML Label alertThreeLabel;

    // monthly summary labels
    @FXML Label monthSalesLabel;
    @FXML Label monthRevenueLabel;
    @FXML Label monthProfitLabel;

    // recent sales table
    @FXML TableView<DashboardSale> recentSalesTable;
    @FXML TableColumn<DashboardSale, Integer> saleIdColumn;
    @FXML TableColumn<DashboardSale, String>  saleCustomerColumn;
    @FXML TableColumn<DashboardSale, String>  saleEmployeeColumn;
    @FXML TableColumn<DashboardSale, String>  saleDateColumn;
    @FXML TableColumn<DashboardSale, String>  saleTotalColumn; 

    // top products table
    @FXML TableView<TopProduct> topProductsTable;
    @FXML TableColumn<TopProduct, String>  topProductColumn;
    @FXML TableColumn<TopProduct, Integer> topSoldColumn;
    @FXML TableColumn<TopProduct, String>  topRevenueColumn; 

    // pending payments table
    @FXML TableView<PendingPayment> paymentsTable;
    @FXML TableColumn<PendingPayment, Integer> paymentSaleColumn;
    @FXML TableColumn<PendingPayment, String>  paymentAmountColumn; 
    @FXML TableColumn<PendingPayment, String>  paymentMethodColumn;
    @FXML TableColumn<PendingPayment, String>  paymentDateColumn;

    // line charts (same as before)
    @FXML LineChart<String, Number> monthSalesChart;
    @FXML LineChart<String, Number> monthRevenueChart;
    @FXML LineChart<String, Number> monthProfitChart;
    @FXML CategoryAxis salesChartDayAxis;
    @FXML NumberAxis   salesChartValueAxis;
    @FXML CategoryAxis revenueChartDayAxis;
    @FXML NumberAxis   revenueChartValueAxis;
    @FXML CategoryAxis profitChartDayAxis;
    @FXML NumberAxis   profitChartValueAxis;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // recent sales
        saleIdColumn.setCellValueFactory(new PropertyValueFactory("id"));
        saleCustomerColumn.setCellValueFactory(new PropertyValueFactory("customer"));
        saleEmployeeColumn.setCellValueFactory(new PropertyValueFactory("employee"));
        saleDateColumn.setCellValueFactory(new PropertyValueFactory("date"));
        saleTotalColumn.setCellValueFactory(new PropertyValueFactory("formattedTotal"));

        // top products 
        topProductColumn.setCellValueFactory(new PropertyValueFactory("product"));
        topSoldColumn.setCellValueFactory(new PropertyValueFactory("soldQty"));
        topRevenueColumn.setCellValueFactory(new PropertyValueFactory("formattedRevenue"));

        //  pending payments
        paymentSaleColumn.setCellValueFactory(new PropertyValueFactory("saleId"));
        paymentAmountColumn.setCellValueFactory(new PropertyValueFactory("formattedAmount"));
        paymentMethodColumn.setCellValueFactory(new PropertyValueFactory("method"));
        paymentDateColumn.setCellValueFactory(new PropertyValueFactory("date"));

        loadCounts();
        loadRecentSales();
        loadTopProducts();
        loadPendingPayments();
        loadMonthlyCharts();
    }

    // load all count labels and alerts
    void loadCounts() {
        try {
            productsCountLabel.setText(String.valueOf(runCount("select count(*) from Product")));
            customersCountLabel.setText(String.valueOf(runCount("select count(*) from Customer")));
            suppliersCountLabel.setText(String.valueOf(runCount("select count(*) from Supplier")));
            salesCountLabel.setText(String.valueOf(runCount("select count(*) from Sale")));
            purchasesCountLabel.setText(String.valueOf(runCount("select count(*) from Purchase")));

            int empCount = runCount("select count(*) from Employee");
            employeesLabel.setText("Employees: " + empCount);

            int returnCount = runCount("select count(*) from Return_Record");
            returnsLabel.setText("Returns: " + returnCount);

            int totalStock = runCount("select sum(Quantity) from Storage_Details");
            totalStockLabel.setText("Total Stock: " + totalStock);

            int lowStock = runCount("select count(*) from Storage_Details where Quantity <= 3");
            lowStockLabel.setText("Low Stock Products: " + lowStock);

            // net revenue
            double gross = runDouble("select sum(Unit_Price * Quantity - Discount) from Sale_Details");
            double returned = runDouble("select sum(r.Quantity * (sd.Unit_Price - sd.Discount / sd.Quantity)) " +
                "from Return_Record r, Sale_Details sd " +
                "where r.Sale_ID = sd.Sale_ID and r.Product_ID = sd.Product_ID and r.Return_Status = 'Approved'");
            revenueLabel.setText("$" + String.format("%.2f", gross - returned));

            // count sales with unpaid remaining
            int pendingCount = 0;
            Connection mycon = DBconnection.getConnection();
            Statement loopStmt = mycon.createStatement();
            Statement calcStmt = mycon.createStatement();
            ResultSet saleRs = loopStmt.executeQuery("select Sale_ID from Sale");
            while (saleRs.next()) {
                int saleId = saleRs.getInt("Sale_ID");
                double total = getSaleTotal(calcStmt, saleId);
                double paid  = getPaidAmount(calcStmt, saleId);
                if (total - paid > 0.001) { pendingCount++; }
            }
            saleRs.close();
            calcStmt.close();
            loopStmt.close();
            mycon.close();

            pendingPaymentsLabel.setText("Pending Payments: " + pendingCount);

            // alert labels show
            alertOneLabel.setText("- Pending payments: " + pendingCount);
            alertTwoLabel.setText("- Low stock products: " + lowStock);
            alertThreeLabel.setText("- Total items in warehouse: " + totalStock);

        } catch (Exception e) {
            productsCountLabel.setText("Error");
            System.out.println("loadCounts error: " + e.getMessage());
        }
    }

    // load last 10 sales 
    void loadRecentSales() {
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmtMain = mycon.createStatement();
        Statement stmtCalc = mycon.createStatement();
        String sql = "select s.Sale_ID, c.First_Name, c.Last_Name, e.First_Name as EFirst, e.Last_Name as ELast, s.Sale_Date " +
                     "from Sale s, Customer c, Employee e " +
                     "where s.Customer_ID = c.Customer_ID and s.Employee_ID = e.Employee_ID " +
                     "order by s.Sale_Date desc, s.Sale_ID desc";
        ResultSet rs = stmtMain.executeQuery(sql);
        ObservableList<DashboardSale> list = FXCollections.observableArrayList();
        int count = 0;
        while (rs.next() && count < 10) {
            int saleId = rs.getInt("Sale_ID");
            String customer = rs.getString("First_Name") + " " + rs.getString("Last_Name");
            String employee = rs.getString("EFirst") + " " + rs.getString("ELast");
            double total = getSaleTotal(stmtCalc, saleId);
            list.add(new DashboardSale(saleId, customer, employee, rs.getString("Sale_Date"), total));
            count++;
        }
        recentSalesTable.setItems(list);
        rs.close();
        stmtCalc.close();
        stmtMain.close();
        mycon.close();
    } catch (Exception e) {
        System.out.println("loadRecentSales error: " + e.getMessage());
    }
}

void loadTopProducts() {
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmtMain = mycon.createStatement();
        Statement stmtCalc = mycon.createStatement();
        String sql = "select p.Product_ID, p.Product_Name, sum(sd.Quantity) as SoldQty " +
                     "from Sale_Details sd, Product p " +
                     "where sd.Product_ID = p.Product_ID " +
                     "group by p.Product_ID, p.Product_Name " +
                     "order by SoldQty desc";
        ResultSet rs = stmtMain.executeQuery(sql);
        ObservableList<TopProduct> list = FXCollections.observableArrayList();
        int count = 0;
        while (rs.next() && count < 10) {
            int productId = rs.getInt("Product_ID");
            int soldQty = rs.getInt("SoldQty");
            ResultSet revRs = stmtCalc.executeQuery("select sum(Unit_Price * Quantity - Discount) as Rev from Sale_Details where Product_ID = " + productId);
            revRs.next();
            double revenue = revRs.getDouble("Rev");
            if (revRs.wasNull()) { revenue = 0; }
            revRs.close();
            ResultSet retRs = stmtCalc.executeQuery("select sum(r.Quantity * (sd.Unit_Price - sd.Discount / sd.Quantity)) as RetVal from Return_Record r, Sale_Details sd where r.Sale_ID = sd.Sale_ID and r.Product_ID = sd.Product_ID and r.Return_Status = 'Approved' and r.Product_ID = " + productId);
            retRs.next();
            double retVal = retRs.getDouble("RetVal");
            if (retRs.wasNull()) { retVal = 0; }
            retRs.close();
            ResultSet retQtyRs = stmtCalc.executeQuery("select sum(Quantity) as RetQty from Return_Record where Return_Status = 'Approved' and Product_ID = " + productId);
            retQtyRs.next();
            int retQty = retQtyRs.getInt("RetQty");
            if (retQtyRs.wasNull()) { retQty = 0; }
            retQtyRs.close();
            list.add(new TopProduct(rs.getString("Product_Name"), soldQty - retQty, revenue - retVal));
            count++;
        }
        topProductsTable.setItems(list);
        rs.close();
        stmtCalc.close();
        stmtMain.close();
        mycon.close();
    } catch (Exception e) {
        System.out.println("loadTopProducts error: " + e.getMessage());
    }
}
// load up to 10 pending payments
   void loadPendingPayments() {
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmtMain = mycon.createStatement();
        Statement stmtCalc = mycon.createStatement();
        Statement stmtLast = mycon.createStatement();
        ResultSet rs = stmtMain.executeQuery("select Sale_ID from Sale order by Sale_ID desc");
        ObservableList<PendingPayment> list = FXCollections.observableArrayList();
        while (rs.next()) {
            int saleId = rs.getInt("Sale_ID");
            double total = getSaleTotal(stmtCalc, saleId);
            double paid = getPaidAmount(stmtCalc, saleId);
            double remaining = total - paid;
            if (remaining > 0.001) {
                String method = "No payment";
                String date = "-";
                ResultSet lastRs = stmtLast.executeQuery("select Payment_Method, Payment_Date from Payment where Sale_ID = " + saleId + " order by Payment_Date desc, Payment_ID desc");
                if (lastRs.next()) {
                    method = lastRs.getString("Payment_Method");
                    date = lastRs.getString("Payment_Date");
                }
                lastRs.close();
                list.add(new PendingPayment(saleId, remaining, method, date));
                if (list.size() == 10) { break; }
            }
        }
        paymentsTable.setItems(list);
        rs.close();
        stmtLast.close();
        stmtCalc.close();
        stmtMain.close();
        mycon.close();
    } catch (Exception e) {
        System.out.println("loadPendingPayments error: " + e.getMessage());
    }
}
    // load line charts for last 6 months 
    void loadMonthlyCharts() {
        monthSalesChart.getData().clear();
        monthRevenueChart.getData().clear();
        monthProfitChart.getData().clear();

        XYChart.Series<String, Number> salesSeries   = new XYChart.Series<>(); salesSeries.setName("Sales");
        XYChart.Series<String, Number> revenueSeries = new XYChart.Series<>(); revenueSeries.setName("Revenue");
        XYChart.Series<String, Number> profitSeries  = new XYChart.Series<>(); profitSeries.setName("Profit");

        LocalDate today = LocalDate.now();
        String[] monthNames = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

        int totalSales = 0;
        double totalRev = 0;
        double totalProfit = 0;

        try {
            Connection mycon     = DBconnection.getConnection();
            Statement stmtCount  = mycon.createStatement();
            Statement stmtDetail = mycon.createStatement();
            Statement stmtCalc   = mycon.createStatement();

            // loop last 6 months oldest to newest so chart reads left to right
            for (int offset = 5; offset >= 0; offset--) {
                LocalDate monthDate = today.minusMonths(offset);
                int m    = monthDate.getMonthValue();
                int y    = monthDate.getYear();
                String label = monthNames[m - 1] + " " + y;

                // count sales this month
                ResultSet countRs = stmtCount.executeQuery("select count(*) as SaleCount from Sale " +
                    "where month(Sale_Date) = " + m + " and year(Sale_Date) = " + y);
                countRs.next();
                int saleCount = countRs.getInt("SaleCount");
                countRs.close();

                // get all detail rows for this month
                ResultSet detailRs = stmtDetail.executeQuery(
                    "select sd.Sale_ID, sd.Product_ID, sd.Quantity, sd.Unit_Price, sd.Discount " +
                    "from Sale s, Sale_Details sd " +
                    "where s.Sale_ID = sd.Sale_ID " +
                    "and month(s.Sale_Date) = " + m + " and year(s.Sale_Date) = " + y);

                double monthRev    = 0;
                double monthProfit = 0;

                while (detailRs.next()) {
                    int saleId    = detailRs.getInt("Sale_ID");
                    int productId = detailRs.getInt("Product_ID");
                    int qty       = detailRs.getInt("Quantity");
                    double price  = detailRs.getDouble("Unit_Price");
                    double disc   = detailRs.getDouble("Discount");

                    ResultSet retRs = stmtCalc.executeQuery("select sum(Quantity) as RetQty from Return_Record " +
                        "where Return_Status = 'Approved' " +
                        "and Sale_ID = " + saleId + " and Product_ID = " + productId);
                    retRs.next();
                    int retQty = retRs.getInt("RetQty");
                    if (retRs.wasNull()) { retQty = 0; }
                    retRs.close();

                    int netQty      = qty - retQty;
                    double netPrice = price - disc / qty;
                    monthRev += netPrice * netQty;

                    ResultSet supRs = stmtCalc.executeQuery("select avg(Supply_Price) as AvgSupply from Supplier_Product " +
                        "where Product_ID = " + productId);
                    supRs.next();
                    double supplyPrice = supRs.getDouble("AvgSupply");
                    if (supRs.wasNull()) { supplyPrice = 0; }
                    supRs.close();

                    if (supplyPrice > 0) { monthProfit += (netPrice - supplyPrice) * netQty; }
                }
                detailRs.close();

                salesSeries.getData().add(new XYChart.Data<>(label, saleCount));
                revenueSeries.getData().add(new XYChart.Data<>(label, monthRev));
                profitSeries.getData().add(new XYChart.Data<>(label, monthProfit));

                // summary labels show current month only (offset == 0)
                if (offset == 0) {
                    totalSales  = saleCount;
                    totalRev = monthRev;
                    totalProfit = monthProfit;
                }
            }

            stmtCalc.close();
            stmtDetail.close();
            stmtCount.close();
            mycon.close();
        } catch (Exception e) {
            System.out.println("loadMonthlyCharts error: " + e.getMessage());
        }

        // summary labels show this month's numbers with $ sign
        monthSalesLabel.setText(String.valueOf(totalSales));
        monthRevenueLabel.setText("$" + String.format("%.2f", totalRev));
        monthProfitLabel.setText("$" + String.format("%.2f", totalProfit));

        if (totalProfit < 0) {
            monthProfitLabel.setStyle("-fx-font-size:24px; -fx-font-weight:bold; -fx-text-fill:#cc3300;");
        } else {
            monthProfitLabel.setStyle("-fx-font-size:24px; -fx-font-weight:bold; -fx-text-fill:#2e7d32;");
        }

        monthSalesChart.getData().add(salesSeries);
        monthRevenueChart.getData().add(revenueSeries);
        monthProfitChart.getData().add(profitSeries);
    }

    // get sale total after subtracting approved returns
    double getSaleTotal(Statement stmt, int saleId) throws Exception {
        ResultSet saleRs = stmt.executeQuery("select sum(Unit_Price * Quantity - Discount) as Total " +
            "from Sale_Details where Sale_ID = " + saleId);
        saleRs.next();
        double total = saleRs.getDouble("Total");
        if (saleRs.wasNull()) { total = 0; }
        saleRs.close();

        ResultSet retRs = stmt.executeQuery("select sum(r.Quantity * (sd.Unit_Price - sd.Discount / sd.Quantity)) as RetTotal " +
                                "from Return_Record r, Sale_Details sd " +
                                "where r.Sale_ID = sd.Sale_ID and r.Product_ID = sd.Product_ID " +
                                "and r.Return_Status = 'Approved' and r.Sale_ID = " + saleId);
        retRs.next();
        double retTotal = retRs.getDouble("RetTotal");
        if (retRs.wasNull()) { retTotal = 0; }
        retRs.close();

        return total - retTotal;
    }

    // get total paid for a sale
    double getPaidAmount(Statement stmt, int saleId) throws Exception {
        ResultSet rs = stmt.executeQuery(
            "select sum(Amount) as Paid from Payment where Sale_ID = " + saleId
        );
        double paid = 0;
        if (rs.next()) { paid = rs.getDouble("Paid"); if (rs.wasNull()) { paid = 0; } }
        rs.close();
        return paid;
    }

    // run a query that returns one integer
    private int runCount(String sql) {
        int value = 0;
        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) { value = rs.getInt(1); if (rs.wasNull()) { value = 0; } }
            rs.close(); stmt.close(); mycon.close();
        } catch (Exception e) { System.out.println("runCount failed: " + sql); }
        return value;
    }

    // run a query that returns one double
    private double runDouble(String sql) {
        double value = 0;
        try {
            Connection mycon = DBconnection.getConnection();
            Statement stmt = mycon.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) { value = rs.getDouble(1); if (rs.wasNull()) { value = 0; } }
            rs.close(); stmt.close(); mycon.close();
        } catch (Exception e) { System.out.println("runDouble failed: " + sql); }
        return value;
    }

    @FXML void notReady() { alertOneLabel.setText("This section is not ready yet"); }

    @FXML void openProducts(ActionEvent event) {
        try { Parent root = FXMLLoader.load(getClass().getResource("/fxml/product.fxml"));
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root,1400,840)); stage.setMaximized(true);
        } catch (Exception e) { alertOneLabel.setText("Cannot open products page"); }
    }
    @FXML void openCustomers(ActionEvent event) {
        try { Parent root = FXMLLoader.load(getClass().getResource("/fxml/customer.fxml"));
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root,1400,840)); stage.setMaximized(true);
        } catch (Exception e) { alertOneLabel.setText("Cannot open customers page"); }
    }
    @FXML void openSuppliers(ActionEvent event) {
        try { Parent root = FXMLLoader.load(getClass().getResource("/fxml/supplier.fxml"));
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root,1400,840)); stage.setMaximized(true);
        } catch (Exception e) { alertOneLabel.setText("Cannot open suppliers page"); }
    }
    @FXML void openEmployees(ActionEvent event) {
        try { Parent root = FXMLLoader.load(getClass().getResource("/fxml/employee.fxml"));
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root,1400,840)); stage.setMaximized(true);
        } catch (Exception e) { alertOneLabel.setText("Cannot open employees page"); }
    }
    @FXML void openSales(ActionEvent event) {
        try { Parent root = FXMLLoader.load(getClass().getResource("/fxml/sales.fxml"));
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root,1400,840)); stage.setMaximized(true);
        } catch (Exception e) { alertOneLabel.setText("Cannot open sales page"); }
    }
    @FXML void openPurchases(ActionEvent event) {
        try { Parent root = FXMLLoader.load(getClass().getResource("/fxml/purchases.fxml"));
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root,1400,840)); stage.setMaximized(true);
        } catch (Exception e) { alertOneLabel.setText("Cannot open purchases page"); }
    }
    @FXML void openInventory(ActionEvent event) {
        try { Parent root = FXMLLoader.load(getClass().getResource("/fxml/inventory.fxml"));
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root,1400,840)); stage.setMaximized(true);
        } catch (Exception e) { alertOneLabel.setText("Cannot open inventory page"); }
    }
    @FXML
void openReports(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/report.fxml"));
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root,1400,840));
        stage.setMaximized(true);
    } catch (Exception e) {
        alertOneLabel.setText("Cannot open reports page");
    }
}
}