package com.hatester.commons;

import com.hatester.crm.pages.CustomerPage;
import com.hatester.crm.pages.ProjectPage;
import com.hatester.crm.pages.TaskPage;
import com.hatester.keywords.WebUI;
import org.openqa.selenium.By;

public class BasePage {
    protected By menuDashboard = By.xpath("//ul[@id='side-menu']/descendant::a[normalize-space()='Dashboard']");
    protected By menuCustomers = By.xpath("//ul[@id='side-menu']/descendant::a[normalize-space()='Customers']");
    protected By menuProjects = By.xpath("//ul[@id='side-menu']/descendant::a[normalize-space()='Projects']");
    protected By menuTasks = By.xpath("//ul[@id='side-menu']/descendant::a[normalize-space()='Tasks']");

    protected void clickMenuItem(By element) {
        WebUI.clickElement(element);
    }

    public CustomerPage goToCustomersFromMenu() {
        clickMenuItem(menuCustomers);
        return new CustomerPage();
    }

    public ProjectPage goToProjectsFromMenu() {
        clickMenuItem(menuProjects);
        return new ProjectPage();
    }

    public TaskPage goToTasksFromMenu() {
        clickMenuItem(menuTasks);
        return new TaskPage();
    }
}