package com.mycompany.itech_management_system;

//row used for all the report tables
// each report has different columns so just use col1..col6 and set the column titles depending on which report 
public class ReportRow {

    String col1;
    String col2;
    String col3;
    String col4;
    String col5;
    String col6;

    public ReportRow(String col1, String col2, String col3, String col4, String col5, String col6) {
        this.col1 = col1;
        this.col2 = col2;
        this.col3 = col3;
        this.col4 = col4;
        this.col5 = col5;
        this.col6 = col6;
    }

    public String getCol1() {
        return col1; 
    }
    public String getCol2() {
        return col2; 
    }
    public String getCol3() { 
        return col3;
    }
    public String getCol4() { 
        return col4; 
    }
    public String getCol5() { 
        return col5; 
    }
    public String getCol6() {
        return col6; 
    }
}