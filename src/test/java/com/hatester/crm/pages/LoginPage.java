package com.hatester.crm.pages;

import com.hatester.commons.BasePage;
import com.hatester.crm.models.LoginDTO;
import com.hatester.keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;

import static com.hatester.config.FrameworkConfig.*;
import static com.hatester.constants.MessageConstant.INVALID_EMAIL_OR_PASSWORD;

public class LoginPage extends BasePage {
    private By headerLogin = By.xpath("//h1[normalize-space()='Login']");
    private By inputEmail = By.xpath("//input[@id='email']");
    private By inputPassword = By.xpath("//input[@id='password']");
    private By buttonLogin = By.xpath("//button[normalize-space()='Login']");

    private By errorInvalidEmailOrPassword = By.xpath("//div[@id='alerts']");

    public void openCRMLoginPage() {
        WebUI.openURL(getURL());
    }

    public void verifyHeaderLoginDisplayed() {
        Assert.assertTrue(WebUI.checkElementExist(headerLogin, 5, 500),
                "Login header is not displayed.");
    }

    public void enterEmail(String email) {
        WebUI.setText(inputEmail, email);
    }

    public void enterPassword(String password) {
        WebUI.setText(inputPassword, password);
    }

    public void clickLoginButton() {
        WebUI.clickElement(buttonLogin);
    }

    public void login(LoginDTO loginDTO) {
        openCRMLoginPage();
        verifyHeaderLoginDisplayed();
        enterEmail(loginDTO.getEmail());
        enterPassword(loginDTO.getPassword());
        clickLoginButton();
    }

    public DashboardPage login() {
        openCRMLoginPage();
        verifyHeaderLoginDisplayed();
        enterEmail(getEmail());
        enterPassword(getPassword());
        clickLoginButton();
        verifyLoginSuccess();
        return new DashboardPage();
    }

    public void verifyLoginSuccess() {
        WebUI.waitForPageLoaded();
        Assert.assertTrue(WebUI.checkElementExist(menuDashboard, 5, 500),
                "Dashboard menu is not displayed.");
        String currentURL = WebUI.getCurrentURL();
        Assert.assertEquals(currentURL, getAdminDashboardUrl(), "Dashboard page URL is incorrect.");
    }

    public void verifyLoginFailedWithInvalidEmailOrPassword() {
        Assert.assertTrue(WebUI.checkElementExist(errorInvalidEmailOrPassword, 5, 500),
                "Invalid email or password error message is not displayed.");

        String actualMessage = WebUI.getElementText(errorInvalidEmailOrPassword).trim();
        Assert.assertEquals(actualMessage, INVALID_EMAIL_OR_PASSWORD,
                "Invalid email or password error message does not match.");
    }
}