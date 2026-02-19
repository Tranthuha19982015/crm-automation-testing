package com.hatester.crm.testcases;

import com.hatester.commons.BaseTest;
import com.hatester.crm.models.CustomerDTO;
import com.hatester.crm.models.ProjectDTO;
import com.hatester.crm.models.ProjectSearchDTO;
import com.hatester.crm.pages.CustomerPage;
import com.hatester.crm.pages.DashboardPage;
import com.hatester.crm.pages.LoginPage;
import com.hatester.crm.pages.ProjectPage;
import com.hatester.crm.steps.CustomerSteps;
import com.hatester.crm.steps.ProjectSteps;
import com.hatester.dataproviders.DataProviderFactory;
import com.hatester.enums.CRMEnum;
import com.hatester.enums.ProjectTableColumn;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

@Epic("CRM Admin")
@Feature("Project feature")
public class ProjectTest extends BaseTest {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private CustomerPage customerPage;
    private ProjectPage projectPage;

    @Test(dataProvider = "projectBaseData", dataProviderClass = DataProviderFactory.class)
    public void testAddProject(CustomerDTO customerDTO, ProjectDTO projectDTO) {
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
    }

    @Test(dataProvider = "editProjectData", dataProviderClass = DataProviderFactory.class)
    public void testEditProject(CustomerDTO customerDTO, ProjectDTO projectAdd, ProjectDTO projectEdit) {
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

        ProjectDTO proDTO = projectSteps.addProject(projectAdd, customerName);
        String projectName = proDTO.getProjectName();

        // ===== EDIT PROJECT =====
        projectSteps.editProject(projectName, projectEdit, customerName);
    }

    @Test(dataProvider = "projectBaseData", dataProviderClass = DataProviderFactory.class)
    public void testDeleteProject(CustomerDTO customerDTO, ProjectDTO projectDTO) {
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

        // ===== DELETE PROJECT =====
        projectSteps.deleteProject(projectName, CRMEnum.ACCEPTED);
    }

    @Test(dataProvider = "searchProject", dataProviderClass = DataProviderFactory.class)
    public void testProjectTableSearchFiltersByMultipleColumns(ProjectSearchDTO proDTO) {
        loginPage = new LoginPage();
        dashboardPage = loginPage.login();
        projectPage = dashboardPage.goToProjectsFromMenu();

        //search by keyword
        String keyword = proDTO.getKeyword();
        projectPage.searchProjectInTable(keyword);
        projectPage.verifyAllProjectRowsContainSearchValue(keyword);

        //search by project name
        String projectName = proDTO.getProject();
        projectPage.searchProjectInTable(projectName);
        projectPage.verifyProjectColumnsInTableContains(ProjectTableColumn.PROJECT_NAME, projectName);

        //search by customer
        String customer = proDTO.getCustomer();
        projectPage.searchProjectInTable(customer);
        projectPage.verifyProjectColumnsInTableContains(ProjectTableColumn.CUSTOMER, customer);

        //search by tag
        String tag = proDTO.getTag();
        projectPage.searchProjectInTable(tag);
        projectPage.verifyProjectColumnsInTableContains(ProjectTableColumn.TAGS, tag);
    }
}
