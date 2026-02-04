package com.hatester.crm.testcases;

import com.hatester.commons.BaseTest;
import com.hatester.crm.models.CustomerDTO;
import com.hatester.crm.models.ProjectDTO;
import com.hatester.crm.models.TaskDTO;
import com.hatester.crm.pages.*;
import com.hatester.dataproviders.DataProviderFactory;
import com.hatester.enums.CRMEnum;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

@Epic("CRM Admin")
@Feature("Task feature")
public class TaskTest extends BaseTest {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private CustomerPage customerPage;
    private ProjectPage projectPage;
    private TaskPage taskPage;

    @Test(dataProvider = "taskBaseData", dataProviderClass = DataProviderFactory.class)
    public void testAddTask(CustomerDTO cusDTO, ProjectDTO proDTO, TaskDTO taskDTO) {
        loginPage = new LoginPage();
        dashboardPage = loginPage.login();

        //add customer
        customerPage = dashboardPage.goToCustomersFromMenu();
        customerPage.verifyHeaderCustomersSummaryIsDisplayed();
        customerPage.clickButtonNewCustomer();
        customerPage.verifyCustomerDetailsTabIsActive();
        String customerName = customerPage.addCustomer(cusDTO);
        customerPage.goToCustomersFromMenu();
        customerPage.searchCustomer(customerName);
        customerPage.verifyCustomerIsAddedSuccessfully(customerName);

        //add project
        projectPage = customerPage.goToProjectsFromMenu();
        projectPage.verifyProjectsPageDisplayed();
        projectPage.clickNewProjectButton();
        projectPage.verifyAddNewProjectPageDisplayed();

        ProjectDTO projectDTO = projectPage.fillProjectFormAndSave(proDTO, customerName, CRMEnum.ADD);
        String projectName = projectDTO.getProjectName();

        projectPage.goToProjectsFromMenu();
        projectPage.searchProjectByName(projectName);
        projectPage.verifyProjectDisplayedInList(projectName);

        //add task
        taskPage = projectPage.goToTasksFromMenu();
        taskPage.verifyTasksPageDisplayed();
        taskPage.clickNewTaskButton();
        taskPage.verifyAddNewTaskDialogDisplayed();

        TaskDTO taskDTO1 = taskPage.fillTaskFormAndSave(taskDTO, projectName, CRMEnum.ADD);
        String taskName = taskDTO1.getTaskName();

        taskPage.clickbuttonCloseTaskDetail(taskName);
        taskPage.searchTaskByName(taskName);
        taskPage.verifyTaskDisplayedInList(taskName);
    }
}
