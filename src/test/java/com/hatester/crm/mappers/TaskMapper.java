package com.hatester.crm.mappers;

import com.hatester.crm.models.TaskDTO;
import com.hatester.utils.DataUtil;
import com.hatester.utils.TestDataUtil;

import java.util.Map;

public class TaskMapper {
    public static TaskDTO taskMapper(Map<String, String> map) {
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setCheckboxPublic(Boolean.parseBoolean(map.get("PUBLIC")));
        taskDTO.setCheckboxBillable(Boolean.parseBoolean(map.get("BILLABLE")));
        taskDTO.setTaskName(TestDataUtil.generateUnique(map.get("TASK_NAME")));
        taskDTO.setMilestone(map.get("MILESTONE"));
        taskDTO.setStartDate(map.get("START_DATE"));
        taskDTO.setDueDate(map.get("DUE_DATE"));
        taskDTO.setPriority(map.get("PRIORITY"));
        taskDTO.setRepeatEvery(map.get("REPEAT_EVERY"));
        taskDTO.setRepeatIntervalValue(map.get("REPEAT_INTERVAL_VALUE"));
        taskDTO.setRepeatIntervalUnit(map.get("REPEAT_INTERVAL_UNIT"));
        taskDTO.setCheckboxInfinity(Boolean.parseBoolean(map.get("INFINITY")));
        taskDTO.setTotalCycles(map.get("TOTAL_CYCLES"));
        taskDTO.setRelatedToType(map.get("RELATED_TO_TYPE"));
        taskDTO.setAssignees(DataUtil.parseList(map.get("ASSIGNEES")));
        taskDTO.setFollowers(DataUtil.parseList(map.get("FOLLOWERS")));
        taskDTO.setTags(DataUtil.parseList(map.get("TAGS")));
        taskDTO.setDescription(map.get("DESCRIPTION"));

        return taskDTO;
    }
}
