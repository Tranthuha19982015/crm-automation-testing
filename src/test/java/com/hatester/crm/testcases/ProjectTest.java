package com.hatester.crm.testcases;

import com.hatester.commons.BaseTest;
import com.hatester.crm.models.CustomerDTO;
import com.hatester.crm.models.ProjectDTO;
import com.hatester.crm.pages.DashboardPage;
import com.hatester.crm.pages.LoginPage;
import com.hatester.crm.pages.ProjectPage;
import com.hatester.crm.testcontext.TestContext;
import com.hatester.dataproviders.DataProviderFactory;
import com.hatester.helpers.RuntimeDataHelper;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

@Epic("CRM Admin")
@Feature("Project feature")
public class ProjectTest extends BaseTest {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private ProjectPage projectPage;

    @Test(dataProvider = "addProjectData", dataProviderClass = DataProviderFactory.class)
    public void testAddProject(ProjectDTO projectDTO) {
        TestContext.setProject(projectDTO);

        loginPage = new LoginPage();
        dashboardPage = loginPage.login();
        projectPage = dashboardPage.goToProjectsFromMenu();

        projectPage.verifyHeaderProjectsSummaryDisplayed();
        projectPage.clickButtonAddProject();
        projectPage.verifyHeaderAddNewProjectDisplayed();

        String valueLastKey = RuntimeDataHelper.get("latestCustomer");
        String customerName = RuntimeDataHelper.get(valueLastKey);

        projectPage.fillData(TestContext.getProject(), customerName);
        projectPage.clickButtonSave();
        projectPage.goToProjectsFromMenu();
        projectPage.searchProject(TestContext.getProject().getProjectName());
        projectPage.verifyProjectIsAddedSuccessfully(TestContext.getProject().getProjectName());
    }
}
