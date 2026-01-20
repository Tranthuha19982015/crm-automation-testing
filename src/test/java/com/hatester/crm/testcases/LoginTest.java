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

    @Test(dataProvider = "loginSuccessData", dataProviderClass = DataProviderFactory.class)
    public void testLoginSuccess(LoginDTO loginDTO) {
        loginPage = new LoginPage();
        loginPage.login(loginDTO);
        loginPage.verifyLoginSuccess();
    }

    @Test(dataProvider = "invalidLoginEmailData", dataProviderClass = DataProviderFactory.class)
    public void testLoginFailedWithEmail(LoginDTO loginDTO) {
        loginPage = new LoginPage();
        loginPage.login(loginDTO);
        loginPage.verifyLoginFailedWithInvalidEmailOrPassword();
    }

    @Test(dataProvider = "invalidLoginPasswordData", dataProviderClass = DataProviderFactory.class)
    public void testLoginFailedWithPassword(LoginDTO loginDTO) {
        loginPage = new LoginPage();
        loginPage.login(loginDTO);
        loginPage.verifyLoginFailedWithInvalidEmailOrPassword();
    }
}
