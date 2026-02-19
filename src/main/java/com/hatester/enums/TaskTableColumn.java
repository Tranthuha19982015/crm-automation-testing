package com.hatester.enums;

public enum TaskTableColumn {
    ID(2, "#"),
    NAME(3, "Name"),
    STATUS(4, "Status"),
    START_DATE(5, "Start Date"),
    DUE_DATE(6, "Deadline"),
    ASSIGNED_TO(7, "Assigned to"),
    TAGS(8, "Tags"),
    PRIORITY(9, "Priority");

    private int index;
    private String columnName;

    TaskTableColumn(int index, String columnName) {
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
