package com.hatester.crm.models;

import lombok.Data;

//Annotation @Data của lombox tự động sinh getter/setter/constructor/...
@Data
public class LoginDTO {
    private String email;
    private String password;
}