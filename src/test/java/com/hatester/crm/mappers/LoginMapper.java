package com.hatester.crm.mappers;

import com.hatester.crm.models.LoginDTO;

import java.util.Map;

public class LoginMapper {
    public static LoginDTO loginMapper(Map<String, String> map) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(map.get("EMAIL"));
        loginDTO.setPassword(map.get("PASSWORD"));
        return loginDTO;
    }
}