package com.mycompany.itech_management_system;
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
//ui elements and chart
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.VBox;
//imports for switching between pages
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class ReportController implements Initializable {

  @FXML TableView<ReportRow> reportTable;
  @FXML TableColumn<ReportRow, String> col1;
  @FXML TableColumn<ReportRow, String> col2;
  @FXML TableColumn<ReportRow, String> col3;
  @FXML TableColumn<ReportRow, String> col4;
  @FXML TableColumn<ReportRow, String> col5;
  @FXML TableColumn<ReportRow, String> col6;
  @FXML Label titleLabel;
  @FXML Label messageLabel;
  @FXML DatePicker fromDatePicker;
  @FXML DatePicker toDatePicker;
  @FXML VBox chartBox;
    //the bar chart and its x axis 
    BarChart<String, Number> categoryChart;
    CategoryAxis categoryAxis;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //link the table columns to the ReportRow fields
     col1.setCellValueFactory(new PropertyValueFactory<>("col1"));
     col2.setCellValueFactory(new PropertyValueFactory<>("col2"));
     col3.setCellValueFactory(new PropertyValueFactory<>("col3"));
     col4.setCellValueFactory(new PropertyValueFactory<>("col4"));
     col5.setCellValueFactory(new PropertyValueFactory<>("col5"));
     col6.setCellValueFactory(new PropertyValueFactory<>("col6"));
        //bar chart needs the axis objects made first befor using it
        categoryAxis = new CategoryAxis();
      NumberAxis yAxis = new NumberAxis();
       categoryAxis.setLabel("");
        yAxis.setLabel("Value");
        categoryChart = new BarChart<>(categoryAxis, yAxis);
       categoryChart.setPrefHeight(350);
       categoryChart.setPrefWidth(750);
         categoryChart.setLegendVisible(false);
        chartBox.getChildren().add(categoryChart);
        //load the first report when the page opens
        showNeverSoldProducts();
    }

    //change the column headers depending on which report is chosen
    void setColumns(String a, String b, String c, String d, String e, String f) {
         col1.setText(a);
         col2.setText(b);
        col3.setText(c);
         col4.setText(d);
       col5.setText(e);
        col6.setText(f);
    }



@FXML
void showNeverSoldProducts() {

    titleLabel.setText("Never Sold Products");
    setColumns("Product ID", "Product Name", "Category", "Brand", "Price", "");
    categoryChart.getData().clear();
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "select p.Product_ID, p.Product_Name, c.Category_Name, b.Brand_Name, p.Price "+ "from Product p "+ "join Category c on p.Category_ID = c.Category_ID "+ "join Brand b on p.Brand_ID = b.Brand_ID "+ "left join Sale_Details sd on p.Product_ID = sd.Product_ID "+ "where sd.Product_ID is null";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<ReportRow> list = FXCollections.observableArrayList();
        while (rs.next()) {
            String id = rs.getString("Product_ID");
            String name = rs.getString("Product_Name");
            String cat = rs.getString("Category_Name");
            String brand = rs.getString("Brand_Name");
            String price = rs.getString("Price");
            list.add(new ReportRow(id, name, cat, brand, price, ""));
        }
        reportTable.setItems(list);
        messageLabel.setText("Found " + list.size() + " product(s) never sold");
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        messageLabel.setText("Could not load this report");
    }
}

//bar chart showing how many products are in each category
@FXML
void showProductsPerCategoryChart() {


    titleLabel.setText("Products per Category");
    //table columns that will be shown in th table
    setColumns("Category", "Total Products", "", "", "", "");
    //clear old table data
    reportTable.setItems(FXCollections.observableArrayList());
    //clear old chart data before loading new data
    categoryChart.getData().clear();
    try {

        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "select c.Category_Name, count(p.Product_ID) as Total_Products "+ "from Category c left join Product p on c.Category_ID = p.Category_ID "+ "group by c.Category_Name "+ "order by Total_Products desc";
        ResultSet rs = stmt.executeQuery(sql);
        //data that will be shown in the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        //stores category names for x axis
        ObservableList<String> cats = FXCollections.observableArrayList();
        //stores table rows
        ObservableList<ReportRow> list = FXCollections.observableArrayList();
        //read every row returned from the query
        while (rs.next()) {
            //get category name
            String cat = rs.getString("Category_Name");
            //get total products in this category
            int total = rs.getInt("Total_Products");
            //add category name to x axis
            cats.add(cat);
            //add one bar to the chart
            series.getData().add(new XYChart.Data<>(cat, total));
            //add the same information to the table
            list.add(new ReportRow(cat, "" + total, "", "", "", ""));
        }
        //show category names on x axis
        categoryAxis.setCategories(cats);
        //hide legend because there is only one series
        categoryChart.setLegendVisible(false);
        //show chart data
        categoryChart.getData().add(series);
        //show table data
        reportTable.setItems(list);
        messageLabel.setText("Chart loaded");
        rs.close();
        stmt.close();
        mycon.close();

    } catch (Exception e) {

        //error message if something go wrong
        messageLabel.setText("Could not load the chart");
    }
}

//products with the highest total quantity sold
@FXML
void showBestSellingProducts() {

    titleLabel.setText("Best Selling Products");
    setColumns("Product ID", "Product Name", "Total Sold", "", "", "");
    categoryChart.getData().clear();
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "select p.Product_ID, p.Product_Name, sum(sd.Quantity) as Total_Sold "+ "from Product p, Sale_Details sd "+ "where p.Product_ID = sd.Product_ID "+ "group by p.Product_ID, p.Product_Name "+ "order by Total_Sold desc";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<ReportRow> list = FXCollections.observableArrayList();
        while (rs.next()) {
            String id = rs.getString("Product_ID");
            String name = rs.getString("Product_Name");
            String sold = rs.getString("Total_Sold");
            list.add(new ReportRow(id, name, sold, "", "", ""));
        }
        reportTable.setItems(list);
        messageLabel.setText("Showing " + list.size() + " product(s)");
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        messageLabel.setText("Error loading report");
    }
}

//customers who never made a sale (with ouy walk-in customer id=1)
@FXML
void showNeverBoughtCustomers() {

    titleLabel.setText("Never Bought Customers");
    setColumns("Customer ID", "First Name", "Last Name", "", "", "");
    categoryChart.getData().clear();
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "select c.Customer_ID, c.First_Name, c.Last_Name "+ "from Customer c left join Sale s on c.Customer_ID = s.Customer_ID "+ "where s.Sale_ID is null "+ "and c.Customer_ID <> 1";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<ReportRow> list = FXCollections.observableArrayList();
        while (rs.next()) {
            String id = rs.getString("Customer_ID");
            String first = rs.getString("First_Name");
            String last = rs.getString("Last_Name");
            list.add(new ReportRow(id, first, last, "", "", ""));
        }
        reportTable.setItems(list);
        messageLabel.setText("Found " + list.size() + " customer(s)");
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        messageLabel.setText("Something went wrong");
    }
}

//sales count and total amount for each customer in a date range
@FXML
void showCustomerSalesPeriod() {

    titleLabel.setText("Customer Sales Summary");
    setColumns("Customer ID", "First Name", "Last Name", "Sales Count", "Total Sales", "");
    categoryChart.getData().clear();
    if (fromDatePicker.getValue() == null || toDatePicker.getValue() == null) {
        messageLabel.setText("Please choose both dates");
        return;
    }
    String fromDate = fromDatePicker.getValue().toString();
    String toDate = toDatePicker.getValue().toString();
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        //becuase sum just ignores the null rows by itself
        String sql = "select c.Customer_ID, c.First_Name, c.Last_Name, "+ "(select count(*) from Sale s2 where s2.Customer_ID = c.Customer_ID "+ "and s2.Sale_Date between '" + fromDate + "' and '" + toDate + "') as Sales_Count, "+ "sum(sd.Unit_Price * sd.Quantity) - sum(sd.Discount) as Total_Sales "+ "from Customer c, Sale s, Sale_Details sd "+ "where c.Customer_ID = s.Customer_ID "+ "and s.Sale_ID = sd.Sale_ID "+ "and s.Sale_Date between '" + fromDate + "' and '" + toDate + "' "+ "and c.Customer_ID <> 1 "+ "group by c.Customer_ID, c.First_Name, c.Last_Name "+ "order by Total_Sales desc";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<ReportRow> list = FXCollections.observableArrayList();
        while (rs.next()) {
            String id = rs.getString("Customer_ID");
            String first = rs.getString("First_Name");
            String last = rs.getString("Last_Name");
            String count = rs.getString("Sales_Count");
            String total = rs.getString("Total_Sales");
            list.add(new ReportRow(id, first, last, count, total, ""));
        }
        reportTable.setItems(list);
        messageLabel.setText("Showing " + list.size() + " customer(s)");
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        messageLabel.setText("Error loading report");
    }
}

//top customers based on total amount they bought 
@FXML
void showTopCustomersReport() {

    titleLabel.setText("Top Customers by Purchases");
    setColumns("Customer ID", "First Name", "Last Name", "Total Bought", "", "");
    categoryChart.getData().clear();
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        //sum(price*qty) - sum(discount) gives total bought even if discount is null for some rows
        String sql = "select c.Customer_ID, c.First_Name, c.Last_Name, sum(sd.Unit_Price * sd.Quantity) - sum(sd.Discount) as total_bought "+ "from Customer c, Sale s, Sale_Details sd "+ "where c.Customer_ID = s.Customer_ID and s.Sale_ID = sd.Sale_ID and c.Customer_ID <> 1 "+ "group by c.Customer_ID, c.First_Name, c.Last_Name "+ "order by total_bought desc";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<ReportRow> list = FXCollections.observableArrayList();
        while (rs.next()) {
            String id = rs.getString("Customer_ID");
            String first = rs.getString("First_Name");
            String last = rs.getString("Last_Name");
            String total = rs.getString("total_bought");
            list.add(new ReportRow(id, first, last, total, "", ""));
        }
        reportTable.setItems(list);
        messageLabel.setText("Showing " + list.size() + " customer(s)");
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        messageLabel.setText("Could not load this report");
    }
}

//the product thats been supplied the most overall
@FXML
void showMostSuppliedProduct() {

    titleLabel.setText("Most Supplied Product");
    setColumns("Product ID", "Product Name", "Total Supplied", "", "", "");
    categoryChart.getData().clear();
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "select p.Product_ID, p.Product_Name, sum(pd.Quantity) as Total_Supplied "+ "from Product p, Purchase_Details pd "+ "where p.Product_ID = pd.Product_ID "+ "group by p.Product_ID, p.Product_Name "+ "order by Total_Supplied desc";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<ReportRow> list = FXCollections.observableArrayList();
        while (rs.next()) {
            String id = rs.getString("Product_ID");
            String name = rs.getString("Product_Name");
            String supplied = rs.getString("Total_Supplied");
            list.add(new ReportRow(id, name, supplied, "", "", ""));
        }
        reportTable.setItems(list);
        messageLabel.setText("Showing " + list.size() + " product(s)");
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        messageLabel.setText("Could not get the data");
    }
}

//top suppliers based on the total value they supplied 
@FXML
void showTopSuppliersReport() {

    titleLabel.setText("Top Suppliers by Supply Value");
    setColumns("Supplier ID", "Contact Name", "Company Name", "Total Supplied", "", "");
    categoryChart.getData().clear();
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "select s.Supplier_ID, s.Contact_Name, s.Company_Name, sum(pd.Unit_Price * pd.Quantity) as total_supplied "+ "from Supplier s, Purchase p, Purchase_Details pd "+ "where s.Supplier_ID = p.Supplier_ID and p.Purchase_ID = pd.Purchase_ID "+ "group by s.Supplier_ID, s.Contact_Name, s.Company_Name "+ "order by total_supplied desc";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<ReportRow> list = FXCollections.observableArrayList();
        while (rs.next()) {
            String id = rs.getString("Supplier_ID");
            String contact = rs.getString("Contact_Name");
            String company = rs.getString("Company_Name");
            String total = rs.getString("total_supplied");
            list.add(new ReportRow(id, contact, company, total, "", ""));
        }
        reportTable.setItems(list);
        messageLabel.setText("Showing " + list.size() + " supplier(s)");
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        messageLabel.setText("Could not load this report");
    }
}

//how many products each supplier provides 
@FXML
void showProductsPerSupplierReport() {

    titleLabel.setText("Products per Supplier");
    setColumns("Contact Name", "Company Name", "Total Products", "", "", "");
    categoryChart.getData().clear();
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "select s.Contact_Name, s.Company_Name, count(sp.Product_ID) as total_products "+ "from Supplier s, Supplier_Product sp "+ "where s.Supplier_ID = sp.Supplier_ID "+ "group by s.Supplier_ID, s.Contact_Name, s.Company_Name "+ "order by total_products desc";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<ReportRow> list = FXCollections.observableArrayList();
        while (rs.next()) {
            String contact = rs.getString("Contact_Name");
            String company = rs.getString("Company_Name");
            String total = rs.getString("total_products");
            list.add(new ReportRow(contact, company, total, "", "", ""));
        }
        reportTable.setItems(list);
        messageLabel.setText("Showing " + list.size() + " supplier(s)");
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        messageLabel.setText("Could not load this report");
    }
}

//employees ranked by how many sales they recorded
@FXML
void showTopEmployeesSales() {

    titleLabel.setText("Top Employees by Sales Count");
    setColumns("Employee ID", "First Name", "Last Name", "Sales Count", "", "");
    categoryChart.getData().clear();
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "select e.Employee_ID, e.First_Name, e.Last_Name, count(s.Sale_ID) as Sales_Count "+ "from Employee e, Sale s "+ "where e.Employee_ID = s.Employee_ID "+ "group by e.Employee_ID, e.First_Name, e.Last_Name "+ "order by Sales_Count desc";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<ReportRow> list = FXCollections.observableArrayList();
        while (rs.next()) {
            String id = rs.getString("Employee_ID");
            String first = rs.getString("First_Name");
            String last = rs.getString("Last_Name");
            String count = rs.getString("Sales_Count");
            list.add(new ReportRow(id, first, last, count, "", ""));
        }
        reportTable.setItems(list);
        messageLabel.setText("Showing " + list.size() + " employee(s)");
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        messageLabel.setText("Error loading report");
    }
}

//highest salary per position
@FXML
void showSalaryPerPosition() {

    titleLabel.setText("Salary per Position");
    //show only position and salary columns
    setColumns("Position", "Salary", "", "", "", "");
    //clear old chart data
    categoryChart.getData().clear();

    try {

        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement(); 
        //get the highest salary for each position 
        String sql = "select Position, max(Salary) as max_salary "+ "from Employee "+ "group by Position "+ "order by max_salary desc";
        ResultSet rs = stmt.executeQuery(sql);
        //data for the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        //store position names for x axis
        ObservableList<String> cats = FXCollections.observableArrayList();
        //store table rows
        ObservableList<ReportRow> list = FXCollections.observableArrayList();
        //read all rows returned from the query
        while (rs.next()) {
            //get position name
            String pos = rs.getString("Position");
            //get highest salary in this position
            double salary = rs.getDouble("max_salary");
            //add position name to x axis
            cats.add(pos);
            //add one bar to the chart
            series.getData().add(new XYChart.Data<>(pos, salary));
            //add the same data to the table
            list.add(new ReportRow(pos, "" + salary, "", "", "", ""));
        }
        //show position names on x axis
        categoryAxis.setCategories(cats);
        //hide legend because there is only one series
        categoryChart.setLegendVisible(false);
        //show chart data
        categoryChart.getData().add(series);
        //show table data
        reportTable.setItems(list);
        messageLabel.setText("Chart loaded");
        rs.close();
        stmt.close();
        mycon.close();

    } catch (Exception e) {

        //error message if somthing fails
        messageLabel.setText("Could not load the chart");
    }
}
//total value of everything currently stored
@FXML
void showTotalInventoryValue() {

    titleLabel.setText("Total Inventory Value");
    setColumns("Product ID", "Product Name", "Total Stock", "Price", "Inventory Value", "");
    categoryChart.getData().clear();
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "select p.Product_ID, p.Product_Name, sum(sd.Quantity) as Total_Stock, "+ "p.Price, (sum(sd.Quantity) * p.Price) as Inventory_Value "+ "from Product p, Storage_Details sd "+ "where p.Product_ID = sd.Product_ID "+ "group by p.Product_ID, p.Product_Name, p.Price "+ "order by Inventory_Value desc";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<ReportRow> list = FXCollections.observableArrayList();
        while (rs.next()) {
            String id = rs.getString("Product_ID");
            String name = rs.getString("Product_Name");
            String stock = rs.getString("Total_Stock");
            String price = rs.getString("Price");
            String value = rs.getString("Inventory_Value");
            list.add(new ReportRow(id, name, stock, price, value, ""));
        }
        reportTable.setItems(list);
        messageLabel.setText("Showing " + list.size() + " product(s)");
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        messageLabel.setText("Could not get the data");
    }
}

//show storage sections that do not have any product inside them
@FXML
void showEmptyStorageSections() {

    titleLabel.setText("Empty Storage Sections");
    setColumns("Section ID", "Section Name", "Type", "Capacity", "", "");
    categoryChart.getData().clear();

    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        String sql = "select ss.Section_ID, ss.Section_Name, ss.Section_Type, ss.Capacity "+ "from Storage_Section ss left join Storage_Details sd on ss.Section_ID = sd.Section_ID "+ "group by ss.Section_ID, ss.Section_Name, ss.Section_Type, ss.Capacity "+ "having sum(sd.Quantity) = 0 or sum(sd.Quantity) is null";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<ReportRow> list = FXCollections.observableArrayList();
        while (rs.next()) {
            String id = rs.getString("Section_ID");
            String name = rs.getString("Section_Name");
            String type = rs.getString("Section_Type");
            String cap = rs.getString("Capacity");
            list.add(new ReportRow(id, name, type, cap, "", ""));
        }

        reportTable.setItems(list);
        messageLabel.setText("Found " + list.size() + " empty section(s)");
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        messageLabel.setText("Could not load empty sections");
    }
}
//top 10 sales by value
@FXML
void showTop10Sales() {

   
    titleLabel.setText("Top 10 Sales");
    //show only needed columns
    setColumns("Sale ID", "Sale Date", "Sale Value", "", "", "");
    //clear old chart data
    categoryChart.getData().clear();
    try {

        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        //get all sales sorted from highest value to lowest value
        String sql = "select s.Sale_ID, s.Sale_Date, sum(sd.Unit_Price * sd.Quantity) - sum(sd.Discount) as sale_value "+ "from Sale s, Sale_Details sd "+ "where s.Sale_ID = sd.Sale_ID "+ "group by s.Sale_ID, s.Sale_Date "+ "order by sale_value desc";
        ResultSet rs = stmt.executeQuery(sql);
        //data for the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        //store sale names for x axis
        ObservableList<String> cats = FXCollections.observableArrayList();
        //store table rows
        ObservableList<ReportRow> list = FXCollections.observableArrayList();
        //counter to stop after 10 sales
        int count = 0;
        //read rows and take only first 10
        while (rs.next() && count < 10) {        
            String saleId = rs.getString("Sale_ID");     
            String saleDate = rs.getString("Sale_Date");    
            double saleValue = rs.getDouble("sale_value");
            //add sale name to x axis
            cats.add("Sale " + saleId);
            //add one bar to the chart
            series.getData().add(new XYChart.Data<>("Sale " + saleId, saleValue));
            //add the same data to the table
            list.add(new ReportRow(saleId, saleDate, "" + saleValue, "", "", ""));
            
            count++;
        }

        //show sale names on x axis
        categoryAxis.setCategories(cats);
        //hide legend because there is only one series
        categoryChart.setLegendVisible(false);
        //show chart data
        categoryChart.getData().add(series);
        reportTable.setItems(list);
        messageLabel.setText("Chart loaded");
        rs.close();
        stmt.close();
        mycon.close();

    } catch (Exception e) {

        //error message if somthing fails
        messageLabel.setText("Could not load the chart");
    }
}
//profit for a chosen date range
@FXML
void showProfitByPeriod() {

    titleLabel.setText("Profit by Period");
    setColumns("Revenue", "Cost", "Profit", "", "", "");
    categoryChart.getData().clear();
    if (fromDatePicker.getValue() == null || toDatePicker.getValue() == null) {
        messageLabel.setText("Please choose both dates");
        reportTable.setItems(FXCollections.observableArrayList());
        return;
    }
    String fromDate = fromDatePicker.getValue().toString();
    String toDate = toDatePicker.getValue().toString();
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        //revenue from sale details, cost from average supply price per product
        String sql = "select sum(sd.Unit_Price * sd.Quantity) - sum(sd.Discount) as revenue, "+ "sum(sd.Quantity * (select avg(sp.Supply_Price) from Supplier_Product sp where sp.Product_ID = sd.Product_ID)) as cost "+ "from Sale s, Sale_Details sd "+ "where s.Sale_ID = sd.Sale_ID "+ "and s.Sale_Date between '" + fromDate + "' and '" + toDate + "'";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<ReportRow> list = FXCollections.observableArrayList();
        if (rs.next()) {
            double revenue = rs.getDouble("revenue");
            double cost = rs.getDouble("cost");
            double profit = revenue - cost;
            list.add(new ReportRow("" + revenue, "" + cost, "" + profit, "", "", ""));
        }
        reportTable.setItems(list);
        messageLabel.setText("Done");
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        messageLabel.setText("Could not load this report");
    }
}

//purchase price vs sale price comparison with margin per product
@FXML
void showPurchaseVsSalePrice() {
    titleLabel.setText("Purchase vs Sale Price Comparison");
    setColumns("Product ID", "Product Name", "Supply Price", "Sale Price", "Margin", "");
    categoryChart.getData().clear();
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
        //get average supply price and average sale unit price for each product then calculate margin
        String sql = "select p.Product_ID, p.Product_Name, avg(sp.Supply_Price) as avg_supply, avg(sd.Unit_Price) as avg_sale, avg(sd.Unit_Price) - avg(sp.Supply_Price) as margin "+ "from Product p "+ "join Supplier_Product sp on p.Product_ID = sp.Product_ID "+ "join Sale_Details sd on p.Product_ID = sd.Product_ID "+ "group by p.Product_ID, p.Product_Name "+ "order by margin desc";
        ResultSet rs = stmt.executeQuery(sql);
        ObservableList<ReportRow> list = FXCollections.observableArrayList();
        while (rs.next()) {
            String id = rs.getString("Product_ID");
            String name = rs.getString("Product_Name");
            String supply = String.format("%.2f", rs.getDouble("avg_supply"));
            String sale = String.format("%.2f", rs.getDouble("avg_sale"));
            String margin = String.format("%.2f", rs.getDouble("margin"));
            list.add(new ReportRow(id, name, supply, sale, margin, ""));
        }
        reportTable.setItems(list);
        messageLabel.setText("Showing " + list.size() + " product(s)");
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        messageLabel.setText("Could not load this report");
    }
}

//all sold products that have a warranty with their warranty details
@FXML
void showWarrantyExpiry() {
    titleLabel.setText("Warranty Expiry Overview");
    setColumns("Product Name", "First Name", "Last Name", "Warranty Type", "Period", "Start Date");
    categoryChart.getData().clear();
    try {
        Connection mycon = DBconnection.getConnection();
        Statement stmt = mycon.createStatement();
String sql = "select p.Product_Name, c.First_Name, c.Last_Name, w.Warranty_Type, w.Warranty_Period, w.Start_Date "+ "from Warranty w, Sale_Details sd, Product p, Sale s, Customer c "+ "where w.Sale_ID = sd.Sale_ID "+ "and w.Product_ID = sd.Product_ID "+ "and sd.Product_ID = p.Product_ID "+ "and w.Sale_ID = s.Sale_ID "+ "and s.Customer_ID = c.Customer_ID "+ "order by w.Start_Date desc";
ResultSet rs = stmt.executeQuery(sql);
        ObservableList<ReportRow> list = FXCollections.observableArrayList();
        while (rs.next()) {
            String name = rs.getString("Product_Name");
            String first = rs.getString("First_Name");
            String last = rs.getString("Last_Name");
            String type = rs.getString("Warranty_Type");
             String period = rs.getString("Warranty_Period");
            String startDate = rs.getString("Start_Date");
            list.add(new ReportRow(name, first, last, type, period, startDate));
        }
        reportTable.setItems(list);
        messageLabel.setText("Found " + list.size() + " warranty record(s)");
        rs.close();
        stmt.close();
        mycon.close();
    } catch (Exception e) {
        messageLabel.setText("Could not load this report");
    }
}



@FXML
void notReady() {
    messageLabel.setText("This section is not ready yet");
}

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