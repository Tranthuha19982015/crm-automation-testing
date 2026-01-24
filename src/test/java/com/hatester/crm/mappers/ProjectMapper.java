package com.hatester.crm.mappers;

import com.hatester.crm.models.ProjectDTO;
import com.hatester.helpers.SystemHelper;
import com.hatester.utils.DataUtils;

import java.util.Map;

public class ProjectMapper {
    public static ProjectDTO projectMapper(Map<String, String> map) {
        ProjectDTO projectDTO = new ProjectDTO();

        projectDTO.setProjectName(map.get("PROJECT_NAME") + SystemHelper.getDateTimeNow() + "_" + System.currentTimeMillis());
        projectDTO.setCheckboxCalculateProgress(Boolean.parseBoolean(map.get("CALCULATE_PROGRESS")));
        projectDTO.setProgress(map.get("PROGRESS"));
        projectDTO.setBillingType(map.get("BILLING_TYPE"));
        projectDTO.setTotalRate(map.get("TOTAL_RATE"));
        projectDTO.setRatePerHour(map.get("RATE_PER_HOUR"));
        projectDTO.setStatus(map.get("STATUS"));
        projectDTO.setCheckboxSendFinished(Boolean.parseBoolean(map.get("SEND_PROJECT_FINISHED")));
        projectDTO.setEstimatedHour(map.get("ESTIMATES_HOURS"));
        projectDTO.setMembers(DataUtils.parseList(map.get("MEMBERS")));
        projectDTO.setStartDate(map.get("START_DATE"));
        projectDTO.setDeadline(map.get("DEADLINE"));
        projectDTO.setTags(map.get("TAGS"));
        projectDTO.setDescription(map.get("DESCRIPTION"));
        projectDTO.setCheckboxSendCreatedMail(Boolean.parseBoolean(map.get("SEND_CREATE_EMAIL")));

        return projectDTO;
    }
}