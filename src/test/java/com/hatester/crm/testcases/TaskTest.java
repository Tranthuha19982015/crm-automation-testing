package com.hatester.crm.testcases;

import com.hatester.commons.BaseTest;
import com.hatester.crm.models.CustomerDTO;
import com.hatester.crm.models.ProjectDTO;
import com.hatester.crm.models.TaskDTO;
import com.hatester.crm.pages.*;
import com.hatester.crm.steps.CustomerSteps;
import com.hatester.crm.steps.ProjectSteps;
import com.hatester.crm.steps.TaskSteps;
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
    public void testAddTask(CustomerDTO customerDTO, ProjectDTO projectDTO, TaskDTO taskDTO) {
        loginPage = new LoginPage();
        dashboardPage = loginPage.login();

        // ===== ADD CUSTOMER =====
        customerPage = dashboardPage.goToCustomersFromMenu();
        CustomerSteps customerSteps = new CustomerSteps(customerPage);
        CustomerDTO cusDTO = customerSteps.addCustomer(customerDTO);
        String customerName = cusDTO.getCompany();

        // ===== ADD PROJECT =====
        projectPage = customerPage.goToProjectsFromMenu();
        ProjectSteps projectSteps = new ProjectSteps(projectPage);
        ProjectDTO proDTO = projectSteps.addProject(projectDTO, customerName);
        String projectName = proDTO.getProjectName();

        // ===== ADD TASK =====
        taskPage = projectPage.goToTasksFromMenu();
        TaskSteps taskSteps = new TaskSteps(taskPage);
        taskSteps.addTask(taskDTO, projectName);
    }
}
