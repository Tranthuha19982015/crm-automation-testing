package com.hatester.crm.testcases;

import com.hatester.commons.BaseTest;
import com.hatester.crm.models.CustomerDTO;
import com.hatester.crm.pages.CustomerPage;
import com.hatester.crm.pages.DashboardPage;
import com.hatester.crm.pages.LoginPage;
import com.hatester.crm.testcontext.TestContext;
import com.hatester.dataproviders.DataProviderFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

@Epic("CRM Admin")
@Feature("Customers feature")
public class CustomerTest extends BaseTest {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private CustomerPage customerPage;

    @Test(dataProvider = "addCustomerData", dataProviderClass = DataProviderFactory.class)
    public void testAddCustomer(CustomerDTO customerDTO) {
        TestContext.setCustomer(customerDTO);

        loginPage = new LoginPage();
        dashboardPage = loginPage.login();
        customerPage = dashboardPage.goToCustomersFromMenu();

        customerPage.verifyHeaderCustomersSummaryIsDisplayed();
        customerPage.clickButtonNewCustomer();
        customerPage.verifyCustomerDetailsTabIsActive();
        customerPage.fillData(TestContext.getCustomer());
        customerPage.clickButtonSave();
        customerPage.goToCustomersFromMenu();
        customerPage.searchCustomer(TestContext.getCustomer().getCompany());
        customerPage.verifyCustomerIsAddedSuccessfully(TestContext.getCustomer().getCompany());
    }
}