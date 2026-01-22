package com.hatester.commons;

import com.hatester.crm.pages.CustomerPage;
import com.hatester.keywords.WebUI;
import org.openqa.selenium.By;

public class BasePage {
    protected By menuDashboard = By.xpath("//ul[@id='side-menu']/descendant::a[normalize-space()='Dashboard']");
    protected By menuCustomers = By.xpath("//ul[@id='side-menu']/descendant::a[normalize-space()='Customers']");

    protected void clickMenuItem(By element) {
        WebUI.clickElement(element);
    }

    public CustomerPage goToCustomersFromMenu() {
        clickMenuItem(menuCustomers);
        return new CustomerPage();
    }
}