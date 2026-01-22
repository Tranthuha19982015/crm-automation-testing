package com.hatester.crm.models;

import lombok.Data;

@Data
public class CustomerDTO {
    String company;
    String vatNumber;
    String phone;
    String website;
    String groups;
    String currency;
    String defaultLanguage;
    String address;
    String city;
    String state;
    String zipCode;
    String country;
}