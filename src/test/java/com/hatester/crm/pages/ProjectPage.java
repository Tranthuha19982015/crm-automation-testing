package com.hatester.crm.pages;

import com.hatester.commons.BasePage;
import com.hatester.crm.models.ProjectDTO;
import com.hatester.enums.ProjectEnum;
import com.hatester.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class ProjectPage extends BasePage {
    //projects list page
    private By buttonNewProject = By.xpath("//div[@id='vueApp']//a[normalize-space()='New Project' and contains(@href,'projects/project')]");
    private By headerProjectsSummary = By.xpath("//div[@id='vueApp']//span[normalize-space()='Projects Summary']");
    private By inputSearchProjects = By.xpath("//div[@id='projects_filter']//input[@aria-controls='projects']");

    private By firstRowItemProject(String projectName) {
        By xpathProject = By.xpath("//table[@id='projects']//a[text()='" + projectName + "']");
        return xpathProject;
    }

    private By buttonEdit(String projectName) {
        By xpathButtonEdit = By.xpath("(//table[@id='projects']//a[text()='" + projectName + "'])/following-sibling::div//a[normalize-space()='Edit']");
        return xpathButtonEdit;
    }

    private By buttonDelete(String projectName) {
        By xpathButtonDelete = By.xpath("(//table[@id='projects']//a[text()='" + projectName + "'])/following-sibling::div//a[normalize-space()='Delete']");
        return xpathButtonDelete;
    }

    //Add new project page
    private By headerAddNewProject = By.xpath("//h4[normalize-space()='Add new project']");

    //project name
    private By inputProjectName = By.xpath("//input[@id='name']");

    // customer
    private By dropdownCustomer = By.xpath("//button[@data-id='clientid']");
    private By inputSearchCustomer = By.xpath("(//button[@data-id='clientid']/following-sibling::div)/descendant::input[@type='search']");

    private By selectDropdownCustomer(String customerName) {
        By xpathCustomer = By.xpath("(//button[@data-id='clientid']/following-sibling::div)/descendant::span[text()='" + customerName + "']");
        return xpathCustomer;
    }

    // checkbox Calculate progress through tasks
    private By checkboxCalculateProgress = By.xpath("//input[@id='progress_from_tasks']");
    private By labelCheckboxCalculateProgress = By.xpath("//label[@for='progress_from_tasks']");
    private By inputHiddenProgress = By.xpath("//label[contains(normalize-space(),'Progress')]/following-sibling::input[@name='progress']");
    private By sliderProgress = By.xpath("//div[contains(@class,'project_progress_slider')]/span");

    // billing type
    private By dropdownBillingType = By.xpath("//button[@data-id='billing_type']");

    private By selectDropdownBillingType(String billingType) {
        By xpathBillingType = By.xpath("(//button[@data-id='billing_type']/following-sibling::div)//span[text()='" + billingType + "']");
        return xpathBillingType;
    }

    //
    private By inputTotalRate = By.xpath("//input[@id='project_cost']");
    private By inputRatePerHour = By.xpath("//input[@id='project_rate_per_hour']");

    // status
    private By dropdownStatus = By.xpath("//button[@data-id='status']");

    private By selectDropdownStatus(String status) {
        By xpathStatus = By.xpath("(//button[@data-id='status']/following-sibling::div)//span[text()='" + status + "']");
        return xpathStatus;
    }

    // checkbox Send Project Marked as Finished email to customer contacts
    private By checkboxSendFinished = By.xpath("//input[@id='project_marked_as_finished_email_to_contacts']");
    private By labelCheckboxSendFinished = By.xpath("//label[@for='project_marked_as_finished_email_to_contacts']");

    // estimated hours
    private By inputEstimatedHours = By.xpath("//input[@id='estimated_hours']");

    // members
    private By dropdownMembers = By.xpath("//button[contains(@data-id,'project_members')]");
    private By inputSearchMembers = By.xpath("(//button[contains(@data-id,'project_members')]/following-sibling::div)//input[@aria-label='Search']");

    private By selectDropdownMembers(String member) {
        By xpathMember = By.xpath("(//button[contains(@data-id,'project_members')]/following-sibling::div)/descendant::span[text()='" + member + "']");
        return xpathMember;
    }

    // input
    private By inputStartDate = By.xpath("//input[@id='start_date']");
    private By inputDeadline = By.xpath("//input[@id='deadline']");
    private By inputTags = By.xpath("(//input[@id='tags']/following-sibling::ul)//input");
    private By iconCloseTag = By.xpath("(//input[@id='tags']/following-sibling::ul)//span[text()='×']");

    // iframe description
    private By iframeDescription = By.xpath("//iframe[@id='description_ifr']");
    private By descriptionEditor = By.xpath("//body[@id='tinymce']");

    // checkbox Send project created email
    private By checkboxSendCreatedEmail = By.xpath("//input[@id='send_created_email']");
    private By labelCheckboxSendCreatedEmail = By.xpath("//label[@for='send_created_email']");

    // button Save
    private By buttonSave = By.xpath("//button[normalize-space()='Save']");

    //Processing methods
    public void verifyHeaderProjectsSummaryDisplayed() {
        Assert.assertTrue(WebUI.checkElementExist(headerProjectsSummary, 5, 500),
                "The Projects Summary header is not displayed.");
    }

    public void clickButtonAddProject() {
        WebUI.clickElement(buttonNewProject);
    }

    public void verifyHeaderAddNewProjectDisplayed() {
        Assert.assertTrue(WebUI.checkElementExist(headerAddNewProject, 5, 500),
                "The Add New Project header is not displayed.");
    }

    public void selectMembers(List<String> members) {
        for (String member : members) {
            WebUI.clickElement(selectDropdownMembers(member));
        }
    }

    public void closeTags() {
        List<WebElement> tags = WebUI.getWebElements(iconCloseTag);
        for (WebElement tag : tags) {
            tag.click();
        }
    }

    public void clearData() {
        String attributeBillingType = WebUI.getElementAttribute(dropdownBillingType, "title");
        if (attributeBillingType.equals("Fixed Rate")) {
            WebUI.clearElementText(inputTotalRate);
        } else if (attributeBillingType.equals("Project Hours")) {
            WebUI.clearElementText(inputRatePerHour);
        }

        WebUI.clearElementText(inputEstimatedHours);
        WebUI.clearElementText(inputStartDate);
        WebUI.clearElementText(inputDeadline);
        closeTags();
        WebUI.clearElementText(descriptionEditor);
    }

    public void fillData(ProjectDTO projectDTO, String customer, ProjectEnum type) {

        if (type.equals(ProjectEnum.ADD)) {
            WebUI.setText(inputProjectName, projectDTO.getProjectName());
        }

        if (type.equals(ProjectEnum.EDIT)) {
            clearData();
        }

        WebUI.clickElement(dropdownCustomer);
        WebUI.setText(inputSearchCustomer, customer);
        WebUI.clickElement(selectDropdownCustomer(customer));

        if (!WebUI.isCheckboxSelected(checkboxCalculateProgress) && projectDTO.isCheckboxCalculateProgress()) {
            WebUI.clickElement(labelCheckboxCalculateProgress);
        } else if (WebUI.isCheckboxSelected(checkboxCalculateProgress) && !projectDTO.isCheckboxCalculateProgress()) {
            WebUI.clickElement(labelCheckboxCalculateProgress);
        }

        if (!WebUI.isCheckboxSelected(checkboxCalculateProgress)) {
            WebUI.setSliderValue(inputHiddenProgress, sliderProgress, Integer.parseInt(projectDTO.getProgress()));
        }

        WebUI.clickElement(dropdownBillingType);
        WebUI.clickElement(selectDropdownBillingType(projectDTO.getBillingType()));

        WebUI.clickElement(dropdownStatus);
        WebUI.clickElement(selectDropdownStatus(projectDTO.getStatus()));

        if (projectDTO.getStatus().equals("Finished") && projectDTO.isCheckboxSendFinished() && !WebUI.isCheckboxSelected(checkboxSendFinished)) {
            WebUI.clickElement(labelCheckboxSendFinished);
        }

        if (projectDTO.getBillingType().equals("Fixed Rate")) {
            WebUI.setText(inputTotalRate, projectDTO.getTotalRate());
        } else if (projectDTO.getBillingType().equals("Project Hours")) {
            WebUI.setText(inputRatePerHour, projectDTO.getRatePerHour());
        }

        WebUI.setText(inputEstimatedHours, projectDTO.getEstimatedHour());

        WebUI.clickElement(dropdownMembers);
        selectMembers(projectDTO.getMembers());
        WebUI.clickElement(dropdownMembers);

        WebUI.clearElementText(inputStartDate);
        WebUI.setText(inputStartDate, projectDTO.getStartDate());
        WebUI.setText(inputDeadline, projectDTO.getDeadline());

        WebUI.clickElement(inputTags);
        WebUI.setTextAndKey(inputTags, projectDTO.getTags(), Keys.ENTER);

        WebUI.switchToFrame(iframeDescription);
        WebUI.setText(descriptionEditor, projectDTO.getDescription());
        WebUI.switchToDefaultContent();

        if (!WebUI.isCheckboxSelected(checkboxSendCreatedEmail) && !projectDTO.isCheckboxSendCreatedMail()) {
            WebUI.clickElement(labelCheckboxSendCreatedEmail);
        }
    }

    public void clickButtonSave() {
        WebUI.clickElement(buttonSave);
    }

    public String addProject(ProjectDTO projectDTO, String customer, ProjectEnum type) {
        fillData(projectDTO, customer, type);
        clickButtonSave();

        return projectDTO.getProjectName();
    }

    public void searchProject(String projectName) {
        WebUI.clickElement(inputSearchProjects);
        WebUI.setText(inputSearchProjects, projectName);
    }

    public void verifyProjectIsAddedSuccessfully(String projectName) {
        Assert.assertTrue(WebUI.checkElementExist(firstRowItemProject(projectName), 10, 500),
                "Project is not added successfully.");
    }
}