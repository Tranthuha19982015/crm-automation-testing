package com.hatester.crm.testcases;

import com.hatester.commons.BaseTest;
import com.hatester.crm.models.CustomerDTO;
import com.hatester.crm.models.ProjectDTO;
import com.hatester.crm.pages.CustomerPage;
import com.hatester.crm.pages.DashboardPage;
import com.hatester.crm.pages.LoginPage;
import com.hatester.crm.pages.ProjectPage;
import com.hatester.crm.testcontext.TestContext;
import com.hatester.dataproviders.DataProviderFactory;
import com.hatester.enums.ProjectEnum;
import com.hatester.helpers.RuntimeDataHelper;
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
        projectPage.verifyHeaderProjectsSummaryDisplayed();
        projectPage.clickButtonAddProject();
        projectPage.verifyHeaderAddNewProjectDisplayed();

        projectPage.addProject(projectDTO, customerName, ProjectEnum.ADD);

        projectPage.goToProjectsFromMenu();
        projectPage.searchProject(projectDTO.getProjectName());
        projectPage.verifyProjectIsAddedSuccessfully(projectDTO.getProjectName());
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
        projectPage.verifyHeaderProjectsSummaryDisplayed();
        projectPage.clickButtonAddProject();
        projectPage.verifyHeaderAddNewProjectDisplayed();

        String projectName = projectPage.addProject(projectAdd, customerName, ProjectEnum.ADD);

        projectPage.goToProjectsFromMenu();
        projectPage.searchProject(projectName);
        projectPage.verifyProjectIsAddedSuccessfully(projectName);


        //Edit project
        projectPage.clickButtonEdit(projectName);
        projectPage.verifyHeaderEditProjectDisplayed();

        projectPage.addProject(projectEdit, customerName, ProjectEnum.EDIT);

        if (projectEdit.isUpdateProjectName()) {
            projectName = projectEdit.getProjectName();
        }

        projectPage.goToProjectsFromMenu();
        projectPage.searchProject(projectName);
        projectPage.verifyProjectIsAddedSuccessfully(projectName);
    }
}
