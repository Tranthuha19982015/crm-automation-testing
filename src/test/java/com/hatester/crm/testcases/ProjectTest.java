package com.hatester.crm.testcases;

import com.hatester.commons.BaseTest;
import com.hatester.crm.models.CustomerDTO;
import com.hatester.crm.models.ProjectDTO;
import com.hatester.crm.pages.CustomerPage;
import com.hatester.crm.pages.DashboardPage;
import com.hatester.crm.pages.LoginPage;
import com.hatester.crm.pages.ProjectPage;
import com.hatester.dataproviders.DataProviderFactory;
import com.hatester.enums.ProjectEnum;
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

    @Test(dataProvider = "addProjectData", dataProviderClass = DataProviderFactory.class)
    public void testAddProject(CustomerDTO customerDTO, ProjectDTO projectDTO) {
        loginPage = new LoginPage();
        dashboardPage = loginPage.login();

        //Add Customer
        customerPage = dashboardPage.goToCustomersFromMenu();
        customerPage.verifyHeaderCustomersSummaryIsDisplayed();
        customerPage.clickButtonNewCustomer();
        customerPage.verifyCustomerDetailsTabIsActive();
        String customerName = customerPage.addCustomer(customerDTO);
        customerPage.goToCustomersFromMenu();

        customerPage.searchCustomer(customerDTO.getCompany());
        customerPage.verifyCustomerIsAddedSuccessfully(customerDTO.getCompany());

        //Add project
        projectPage = customerPage.goToProjectsFromMenu();
        projectPage.verifyProjectsPageDisplayed();
        projectPage.clickNewProjectButton();
        projectPage.verifyAddNewProjectPageDisplayed();

        projectPage.fillProjectFormAndSave(projectDTO, customerName, ProjectEnum.ADD);

        projectPage.goToProjectsFromMenu();
        projectPage.searchProjectByName(projectDTO.getProjectName());
        projectPage.verifyProjectDisplayedInList(projectDTO.getProjectName());
    }

    @Test(dataProvider = "editProjectData", dataProviderClass = DataProviderFactory.class)
    public void testEditProject(CustomerDTO customerDTO, ProjectDTO projectAdd, ProjectDTO projectEdit) {
        loginPage = new LoginPage();
        dashboardPage = loginPage.login();

        //Add Customer
        customerPage = dashboardPage.goToCustomersFromMenu();
        customerPage.verifyHeaderCustomersSummaryIsDisplayed();
        customerPage.clickButtonNewCustomer();
        customerPage.verifyCustomerDetailsTabIsActive();
        String customerName = customerPage.addCustomer(customerDTO);
        customerPage.goToCustomersFromMenu();

        customerPage.searchCustomer(customerDTO.getCompany());
        customerPage.verifyCustomerIsAddedSuccessfully(customerDTO.getCompany());


        //Add project
        projectPage = customerPage.goToProjectsFromMenu();
        projectPage.verifyProjectsPageDisplayed();
        projectPage.clickNewProjectButton();
        projectPage.verifyAddNewProjectPageDisplayed();

        ProjectDTO project = projectPage.fillProjectFormAndSave(projectAdd, customerName, ProjectEnum.ADD);
        String projectName = project.getProjectName();

        projectPage.goToProjectsFromMenu();
        projectPage.searchProjectByName(projectName);
        projectPage.verifyProjectDisplayedInList(projectName);


        //Edit project
        projectPage.clickEditProjectButton(projectName);
        projectPage.verifyEditProjectPageDisplayed();

        projectPage.fillProjectFormAndSave(projectEdit, customerName, ProjectEnum.EDIT);

        if (projectEdit.isUpdateProjectName()) {
            projectName = projectEdit.getProjectName();
        }

        projectPage.goToProjectsFromMenu();
        projectPage.searchProjectByName(projectName);
        projectPage.verifyProjectDisplayedInList(projectName);
    }

    @Test(dataProvider = "addProjectData", dataProviderClass = DataProviderFactory.class)
    public void testDeleteProject(CustomerDTO customerDTO, ProjectDTO projectDTO) {
        loginPage = new LoginPage();
        dashboardPage = loginPage.login();

        //Add Customer
        customerPage = dashboardPage.goToCustomersFromMenu();
        customerPage.verifyHeaderCustomersSummaryIsDisplayed();
        customerPage.clickButtonNewCustomer();
        customerPage.verifyCustomerDetailsTabIsActive();
        String customerName = customerPage.addCustomer(customerDTO);
        customerPage.goToCustomersFromMenu();

        customerPage.searchCustomer(customerName);
        customerPage.verifyCustomerIsAddedSuccessfully(customerName);

        //Add project
        projectPage = customerPage.goToProjectsFromMenu();
        projectPage.verifyProjectsPageDisplayed();
        projectPage.clickNewProjectButton();
        projectPage.verifyAddNewProjectPageDisplayed();

        ProjectDTO project = projectPage.fillProjectFormAndSave(projectDTO, customerName, ProjectEnum.ADD);
        String projectName = project.getProjectName();

        projectPage.goToProjectsFromMenu();
        projectPage.searchProjectByName(projectName);
        projectPage.verifyProjectDisplayedInList(projectName);

        //Delete project
        projectPage.clickDeleteProjectButton(projectName);
        projectPage.confirmDeleteProject(ProjectEnum.ACCEPTED);
        projectPage.searchProjectByName(projectName);
        projectPage.verifyDeleteProjectResult(projectName, ProjectEnum.ACCEPTED);
    }
}
