package com.hatester.crm.models;

import lombok.Data;

import java.util.List;

@Data
public class TaskDTO {
    private boolean checkboxPublic;
    private boolean checkboxBillable;
    private List<String> attachFile;
    private String taskName;
    private String milestone;
    private String startDate;
    private String dueDate;
    private String priority;
    private String repeatEvery;
    private String repeatIntervalValue;
    private String repeatIntervalUnit;
    private boolean checkboxInfinity;
    private String totalCycles;
    private String relatedToType;
    private String relatedToValue;
    private List<String> Assignees;
    private List<String> followers;
    private List<String> tags;
    private String description;
    private boolean updateTaskName;
}
