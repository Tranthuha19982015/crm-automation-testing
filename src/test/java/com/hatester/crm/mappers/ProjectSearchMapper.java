package com.hatester.crm.mappers;

import com.hatester.crm.models.ProjectSearchDTO;

import java.util.Map;

public class ProjectSearchMapper {
    public static ProjectSearchDTO projectSearchMapper(Map<String, String> map) {
        ProjectSearchDTO searchDTO = new ProjectSearchDTO();

        searchDTO.setKeyword(map.get("KEYWORD"));
        searchDTO.setProject(map.get("PROJECT_NAME"));
        searchDTO.setCustomer(map.get("CUSTOMER"));
        searchDTO.setTag(map.get("TAG"));

        return searchDTO;
    }
}
