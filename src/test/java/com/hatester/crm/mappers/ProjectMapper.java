package com.hatester.crm.mappers;

import com.hatester.crm.models.ProjectDTO;
import com.hatester.helpers.SystemHelper;
import com.hatester.utils.DataUtil;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class ProjectMapper {
    public static ProjectDTO projectMapper(Map<String, String> map) {
        ProjectDTO projectDTO = new ProjectDTO();

        projectDTO.setProjectName(map.get("PROJECT_NAME") + " " + SystemHelper.getDateTimeNow() + "_" + RandomStringUtils.randomAlphanumeric(6));
        projectDTO.setCheckboxCalculateProgress(Boolean.parseBoolean(map.get("CALCULATE_PROGRESS")));
        projectDTO.setProgress(map.get("PROGRESS"));
        projectDTO.setBillingType(map.get("BILLING_TYPE"));
        projectDTO.setTotalRate(map.get("TOTAL_RATE"));
        projectDTO.setRatePerHour(map.get("RATE_PER_HOUR"));
        projectDTO.setStatus(map.get("STATUS"));
        projectDTO.setCheckboxSendFinished(Boolean.parseBoolean(map.get("SEND_PROJECT_FINISHED")));
        projectDTO.setEstimatedHour(map.get("ESTIMATES_HOURS"));
        projectDTO.setMembers(DataUtil.parseList(map.get("MEMBERS")));
        projectDTO.setStartDate(map.get("START_DATE"));
        projectDTO.setDeadline(map.get("DEADLINE"));
        projectDTO.setTags(DataUtil.parseList(map.get("TAGS")));
        projectDTO.setDescription(map.get("DESCRIPTION"));
        projectDTO.setCheckboxSendCreatedMail(Boolean.parseBoolean(map.get("SEND_CREATE_EMAIL")));
        projectDTO.setUpdateProjectName(Boolean.parseBoolean(map.get("UPDATE_PROJECT_NAME")));

        return projectDTO;
    }
}