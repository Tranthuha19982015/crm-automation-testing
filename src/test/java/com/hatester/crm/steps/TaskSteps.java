package com.hatester.crm.steps;

import com.hatester.crm.models.TaskDTO;
import com.hatester.crm.pages.TaskPage;
import com.hatester.enums.CRMEnum;

public class TaskSteps {
    private final TaskPage taskPage;

    public TaskSteps(TaskPage taskPage) {
        this.taskPage = taskPage;
    }

    public TaskDTO addTask(TaskDTO taskDTO, String projectName) {
        taskPage.verifyTasksPageDisplayed();
        taskPage.clickNewTaskButton();
        taskPage.verifyAddNewTaskDialogDisplayed();

        TaskDTO taskDTO1 = taskPage.fillTaskFormAndSave(taskDTO, projectName, CRMEnum.ADD);
        String taskName = taskDTO1.getTaskName();

        taskPage.clickbuttonCloseTaskDetail(taskName);
        taskPage.searchTaskByName(taskName);
        taskPage.verifyTaskDisplayedInList(taskName);

        return taskDTO;
    }
}
