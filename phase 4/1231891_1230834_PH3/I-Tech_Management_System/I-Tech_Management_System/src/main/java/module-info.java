module com.mycompany.itech_management_system  {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.mycompany.itech_management_system to javafx.fxml;
    exports com.mycompany.itech_management_system;
}