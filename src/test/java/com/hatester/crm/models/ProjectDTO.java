package com.hatester.crm.models;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDTO {
    private String projectName;
    private boolean checkboxCalculateProgress;
    private String progress;
    private String billingType;
    private String totalRate;
    private String ratePerHour;
    private String status;
    private boolean checkboxSendFinished;
    private String estimatedHour;
    private List<String> members;
    private String startDate;
    private String deadline;
    private String tags;
    private String description;
    private boolean checkboxSendCreatedMail;
}
