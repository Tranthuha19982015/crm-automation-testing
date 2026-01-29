package com.hatester.crm.mappers;

import com.hatester.crm.models.CustomerDTO;
import com.hatester.helpers.SystemHelper;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Map;

public class CustomerMapper {
    public static CustomerDTO customerMapper(Map<String, String> map) {
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setCompany(map.get("COMPANY") + " " + SystemHelper.getDateTimeSimple() + "_" + RandomStringUtils.randomAlphanumeric(6));
        customerDTO.setVatNumber(map.get("VAT_NUMBER"));
        customerDTO.setPhone(map.get("PHONE"));
        customerDTO.setWebsite(map.get("WEBSITE"));
        customerDTO.setGroups(map.get("GROUPS"));
        customerDTO.setCurrency(map.get("CURRENCY"));
        customerDTO.setDefaultLanguage(map.get("LANGUAGE"));
        customerDTO.setAddress(map.get("ADDRESS"));
        customerDTO.setCity(map.get("CITY"));
        customerDTO.setState(map.get("STATE"));
        customerDTO.setZipCode(map.get("ZIP_CODE"));
        customerDTO.setCountry(map.get("COUNTRY"));

        return customerDTO;
    }
}