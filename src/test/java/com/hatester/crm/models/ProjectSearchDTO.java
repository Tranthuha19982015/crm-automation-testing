package com.hatester.crm.models;

import lombok.Data;

@Data
public class ProjectSearchDTO {
    private String keyword;
    private String project;
    private String customer;
    private String tag;
}
