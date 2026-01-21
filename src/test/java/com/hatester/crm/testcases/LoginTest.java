package com.hatester.crm.testcases;

import com.hatester.commons.BaseTest;
import com.hatester.crm.models.LoginDTO;
import com.hatester.crm.pages.DashboardPage;
import com.hatester.crm.pages.LoginPage;
import com.hatester.dataproviders.DataProviderFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

@Epic("CRM Admin")
@Feature("Login feature")
public class LoginTest extends BaseTest {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    @Test(priority = 1, dataProvider = "loginSuccessData", dataProviderClass = DataProviderFactory.class)
    public void testLoginSuccess(LoginDTO loginDTO) {
        loginPage = new LoginPage();
        loginPage.login(loginDTO);
        loginPage.verifyLoginSuccess();
    }

    @Test(priority = 2, dataProvider = "invalidLoginEmailData", dataProviderClass = DataProviderFactory.class)
    public void testLoginFailedWithEmail(LoginDTO loginDTO) {
        loginPage = new LoginPage();
        loginPage.login(loginDTO);
        loginPage.verifyLoginFailedWithInvalidEmailOrPassword();
    }

    @Test(priority = 3, dataProvider = "invalidLoginPasswordData", dataProviderClass = DataProviderFactory.class)
    public void testLoginFailedWithPassword(LoginDTO loginDTO) {
        loginPage = new LoginPage();
        loginPage.login(loginDTO);
        loginPage.verifyLoginFailedWithInvalidEmailOrPassword();
    }
}
