package com.hatester.enums;

public enum ProjectTableColumn {
    ID(1, "#"),
    PROJECT_NAME(2, "Project Name"),  // tương ứng với: new ProjectTableColumn(2, "Project Name");
    CUSTOMER(3, "Customer"),
    TAGS(4, "Tags"),
    START_DATE(5, "Start Date"),
    DEADLINE(6, "Deadline"),
    MEMBERS(7, "Members"),
    STATUS(8, "Status");

    private int index;
    private String columnName;

    ProjectTableColumn(int index, String columnName) {
        this.index = index;
        this.columnName = columnName;
    }

    public int getIndex() {
        return index;
    }

    public String getColumnName() {
        return columnName;
    }
}
