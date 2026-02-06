package com.hatester.crm.pages;

import com.hatester.commons.BasePage;
import com.hatester.crm.models.ProjectDTO;
import com.hatester.enums.CRMEnum;
import com.hatester.keywords.WebUI;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import java.util.function.Function;

public class ProjectPage extends BasePage {
    //projects list page
    private By buttonNewProject = By.xpath("//div[@id='vueApp']//a[normalize-space()='New Project' and contains(@href,'projects/project')]");
    private By headerProjectsSummary = By.xpath("//div[@id='vueApp']//span[normalize-space()='Projects Summary']");
    private By inputSearchProjects = By.xpath("//div[@id='projects_filter']//input[@aria-controls='projects']");

    private By rowItemInList(String projectName) {
        By rowItem = By.xpath("(//table[@id='projects']//a[normalize-space()='" + projectName + "'])/ancestor::tr");
        return rowItem;
    }

    private By projectItemInList(String projectName) {
        By xpathProject = By.xpath("//table[@id='projects']//a[normalize-space()='" + projectName + "']");
        return xpathProject;
    }

    private By buttonEditProject(String projectName) {
        By xpathButtonEdit = By.xpath("(//table[@id='projects']//a[normalize-space()='" + projectName + "'])/following-sibling::div//a[normalize-space()='Edit']");
        return xpathButtonEdit;
    }

    private By buttonDeleteProject(String projectName) {
        By xpathButtonDelete = By.xpath("(//table[@id='projects']//a[normalize-space()='" + projectName + "'])/following-sibling::div//a[normalize-space()='Delete']");
        return xpathButtonDelete;
    }

    //Add/edit project page
    private By headerAddNewProject = By.xpath("//h4[normalize-space()='Add new project']");
    private By headerEditProject = By.xpath("//h4[normalize-space()='Edit Project']");

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
    private By labelEstimatedHours = By.xpath("//label[@for='estimated_hours']");
    private By inputEstimatedHours = By.xpath("//input[@id='estimated_hours']");

    // members
    private By dropdownMembers = By.xpath("//button[contains(@data-id,'project_members')]");
    private By inputSearchMembers = By.xpath("(//button[contains(@data-id,'project_members')]/following-sibling::div)//input[@aria-label='Search']");

    private By selectDropdownMembers(String member) {
        By xpathMember = By.xpath("(//button[contains(@data-id,'project_members')]/following-sibling::div)/descendant::span[text()='" + member + "']");
        return xpathMember;
    }

    private By buttonDeleteAllMembers = By.xpath("(//button[contains(@data-id,'project_members')]/following-sibling::div)/descendant::button[text()='Deselect All']");

    // input
    private By inputStartDate = By.xpath("//input[@id='start_date']");
    private By inputDeadline = By.xpath("//input[@id='deadline']");

    //tags
    private By labelTags = By.xpath("//label[@for='tags']");
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
    public void verifyProjectsPageDisplayed() {
        Assert.assertTrue(WebUI.checkElementExist(headerProjectsSummary, 5, 500),
                "Projects page is not displayed.");
    }

    public void clickNewProjectButton() {
        WebUI.clickElement(buttonNewProject);
    }

    public void verifyAddNewProjectPageDisplayed() {
        Assert.assertTrue(WebUI.checkElementExist(headerAddNewProject, 5, 500),
                "Add New Project page is not displayed.");
    }

    private void handleProjectName(ProjectDTO dto, CRMEnum type) {
        if (type == CRMEnum.EDIT && !dto.isUpdateProjectName()) {
            return;
        }

        WebUI.setTextIfChanged(inputProjectName, dto.getProjectName(), "value");
    }

    private void handleCustomer(String customer) {
        WebUI.selectSearchableDropdownIfChanged(dropdownCustomer, customer, "title", inputSearchCustomer, selectDropdownCustomer(customer));
    }

    private void handleCalculateProgress(ProjectDTO dto) {
        boolean expected = dto.isCheckboxCalculateProgress();  //mặc định = false dù excel có rỗng hoặc không có cột này
        WebUI.setCheckboxIfChanged(checkboxCalculateProgress, expected, labelCheckboxCalculateProgress);

        //set progress khi checkbox OFF (không tích)
        if (!expected) {
            String progress = dto.getProgress();
            if (StringUtils.isBlank(progress)) {
                return;
            }

            WebUI.setSliderValue(inputHiddenProgress, sliderProgress, Integer.parseInt(progress));
        }
    }

    private void handleBillingType(ProjectDTO dto) {
        String dtoBillingType = dto.getBillingType();
        WebUI.selectDropdownIfChanged(dropdownBillingType, dtoBillingType, "title", selectDropdownBillingType(dtoBillingType));
    }

    private void handleStatus(ProjectDTO dto) {
        String dtoStatus = dto.getStatus();
        WebUI.selectDropdownIfChanged(dropdownStatus, dtoStatus, "title", selectDropdownStatus(dtoStatus));
    }

    private void handleCheckboxSendFinished(ProjectDTO dto) {
        String expected = dto.getStatus();
        if (StringUtils.isBlank(expected)) {
            return;
        }

        if (!"Finished".equals(expected)) {
            return;
        }

        WebUI.setCheckboxIfChanged(checkboxSendFinished, dto.isCheckboxSendFinished(), labelCheckboxSendFinished);
    }

    private void handleBillingRate(ProjectDTO dto) {
        String billingType = dto.getBillingType();
        if (StringUtils.isBlank(billingType)) {
            return;
        }

        if ("Fixed Rate".equals(billingType)) {
            WebUI.setTextIfChanged(inputTotalRate, dto.getTotalRate(), "value");
            return;
        }

        if ("Project Hours".equals(billingType)) {
            WebUI.setTextIfChanged(inputRatePerHour, dto.getRatePerHour(), "value");
        }
    }

    private void handleEstimatedHours(ProjectDTO dto) {
        WebUI.setTextIfChanged(inputEstimatedHours, dto.getEstimatedHour(), "value");
    }

    private void handleMembers(ProjectDTO dto) {
        Function<String, By> selectMember = member -> selectDropdownMembers(member);
        WebUI.selectMultiDropdownToggleIfChanged(dropdownMembers, dto.getMembers(), "title", selectMember);
    }

    private void handleStartDate(ProjectDTO dto) {
        WebUI.setTextIfChanged(inputStartDate, dto.getStartDate(), "value");
        WebUI.clickElement(labelEstimatedHours);
    }

    private void handleDeadline(ProjectDTO dto) {
        WebUI.setTextIfChanged(inputDeadline, dto.getDeadline(), "value");
        WebUI.clickElement(labelEstimatedHours);
    }

    private void handleTags(ProjectDTO dto) {
        WebUI.setMultiChipInput(inputTags, dto.getTags(), iconCloseTag, labelTags);
    }

    private void handleDescription(ProjectDTO dto) {
        WebUI.setTextInIframeIfChanged(iframeDescription, descriptionEditor, dto.getDescription());
    }

    private void handleCheckboxSendCreatedEmail(ProjectDTO dto) {
        WebUI.setCheckboxIfChanged(checkboxSendCreatedEmail, dto.isCheckboxSendCreatedMail(), labelCheckboxSendCreatedEmail);
    }

    public void fillProjectForm(ProjectDTO dto, String customer, CRMEnum type) {
        handleProjectName(dto, type);
        handleCustomer(customer);
        handleCalculateProgress(dto);
        handleBillingType(dto);
        handleStatus(dto);
        handleCheckboxSendFinished(dto);
        handleBillingRate(dto);
        handleEstimatedHours(dto);
        handleMembers(dto);
        WebUI.scrollToElementAtBottom(buttonSave);
        handleStartDate(dto);
        handleDeadline(dto);
        handleTags(dto);
        handleDescription(dto);
        handleCheckboxSendCreatedEmail(dto);
    }

    public void clickSaveProjectButton() {
        WebUI.clickElement(buttonSave);
    }

    public ProjectDTO fillProjectFormAndSave(ProjectDTO projectDTO, String customer, CRMEnum type) {
        fillProjectForm(projectDTO, customer, type);
        clickSaveProjectButton();

        return projectDTO;
    }

    public void searchProjectByName(String projectName) {
        WebUI.waitForPageLoaded();
        WebUI.clearElementText(inputSearchProjects);
        WebUI.setTextAndKey(inputSearchProjects, projectName, Keys.ENTER);
        WebUI.sleep(1);
    }

    public void verifyProjectDisplayedInList(String projectName) {
        Assert.assertTrue(WebUI.checkElementExist(projectItemInList(projectName), 10, 500),
                "Project is not displayed in the project list.");
    }

    public void clickEditProjectButton(String projectName) {
//        WebUI.waitForElementVisible(projectItemInList(projectName));
//        WebUI.moveToElement(rowItemInList(projectName));
//        WebUI.sleep(1);
//        WebUI.clickElement(buttonEditProject(projectName));

        WebUI.clickJS(buttonEditProject(projectName));
    }

    public void verifyEditProjectPageDisplayed() {
        Assert.assertTrue(WebUI.checkElementExist(headerEditProject, 5, 500),
                "Edit Project page is not displayed.");
    }

    public void clickDeleteProjectButton(String projectName) {
//        WebUI.waitForElementVisible(projectItemInList(projectName));
//        WebUI.moveToElement(rowItemInList(projectName));
//        WebUI.sleep(1);
//        WebUI.clickElement(buttonDeleteProject(projectName));

        WebUI.clickJS(buttonDeleteProject(projectName));
    }

    public void confirmDeleteProject(CRMEnum type) {
        if (type == CRMEnum.ACCEPTED) {
            WebUI.acceptAlert();
            return;
        } else if (type == CRMEnum.DISMISSED) {
            WebUI.dismissAlert();
        }
    }

    public void verifyDeleteProjectResult(String projectName, CRMEnum type) {
        if (type == CRMEnum.ACCEPTED) {
            Assert.assertFalse(WebUI.checkElementExist(projectItemInList(projectName), 10, 500),
                    "Project is still displayed after deletion was accepted.");
            return;
        }
        if (type == CRMEnum.DISMISSED) {
            Assert.assertTrue(WebUI.checkElementExist(projectItemInList(projectName), 10, 500),
                    "Project is not displayed after deletion was dismissed.");
        }
    }
}