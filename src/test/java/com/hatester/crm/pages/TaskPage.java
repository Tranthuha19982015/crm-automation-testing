package com.hatester.crm.pages;

import com.hatester.commons.BasePage;
import com.hatester.config.FrameworkConfig;
import com.hatester.crm.models.TaskDTO;
import com.hatester.enums.CRMEnum;
import com.hatester.helpers.SystemHelper;
import com.hatester.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import java.util.List;
import java.util.function.Function;

import static com.hatester.config.FrameworkConfig.getUploadFilePath;

public class TaskPage extends BasePage {
    //list task page
    private By buttonNewTask = By.xpath("//a[normalize-space()='New Task']");
    private By headerTasksSummary = By.xpath("//span[normalize-space()='Tasks Summary']");
    private By inputSearchTasks = By.xpath("//div[@id='tasks_filter']/descendant::input[@aria-controls='tasks']");

    private By itemTaskInList(String taskName) {
        By xpathTaskName = By.xpath("//table[@id='tasks']/descendant::a[normalize-space()='" + taskName + "']");
        return xpathTaskName;
    }

    private By buttonEdit(String taskName) {
        By xpathButtonEdit = By.xpath("((//table[@id='tasks']/descendant::a[normalize-space()='" + taskName + "'])/following-sibling::div)//a[normalize-space()='Edit']");
        return xpathButtonEdit;
    }

    private By buttonDelete(String taskName) {
        By xpathButtonDelete = By.xpath("((//table[@id='tasks']/descendant::a[normalize-space()='" + taskName + "'])/following-sibling::div)//a[normalize-space()='Delete']");
        return xpathButtonDelete;
    }

    //add new task page
    private By headerAddNewTask = By.xpath("//h4[@id='myModalLabel']");
    private By iconCloseDialog = By.xpath("//form[@id='task-form']/descendant::button[@aria-label='Close']");

    /// checkbox public
    private By checkboxPublic = By.xpath("//input[@id='task_is_public']");
    private By labelCheckboxPublic = By.xpath("//label[@for='task_is_public']");

    /// checkbox billable
    private By checkboxBillable = By.xpath("//input[@id='task_is_billable']");
    private By labelCheckboxBillable = By.xpath("//label[@for='task_is_billable']");

    /// attach file
    private By textLinkAttachFiles = By.xpath("//a[normalize-space()='Attach Files']");

    private By inputAttachFile(int index) {
        By inputAttach = By.xpath("//input[@name='attachments[" + index + "]' and @type='file']");
        return inputAttach;
    }

    private By iconAddMoreFiles(int index) {
        By iconAddMoreFile = By.xpath("(//input[@name='attachments[" + index + "]' and @type='file']/following-sibling::span)/button[contains(@class,'add_more_attachments')]");
        return iconAddMoreFile;
    }

    /// task name (subject)
    private By labelTaskName = By.xpath("//label[@for='name']");
    private By inputTaskName = By.xpath("//div[@app-field-wrapper='name']//input[@id='name']");

    /// milestone
    private By dropdownMilestone = By.xpath("//button[@data-id='milestone']");

    /// date
    private By inputStartDate = By.xpath("//input[@id='startdate']");
    private By inputDueDate = By.xpath("//input[@id='duedate']");

    /// priority
    private By dropdownPriority = By.xpath("//button[@data-id='priority']");

    private By selectDropdownPriority(String priority) {
        By value = By.xpath("(//button[@data-id='priority']/following-sibling::div)//span[text()='" + priority + "']");
        return value;
    }

    /// repeat every
    private By dropdownRepeatEvery = By.xpath("//button[@data-id='repeat_every']");

    private By selectDropdownRepeatEvery(String type) {
        By value = By.xpath("(//button[@data-id='repeat_every']/following-sibling::div)//span[text()='" + type + "']");
        return value;
    }

    private By inputRepeatIntervalValue = By.xpath("//input[@id='repeat_every_custom']");
    private By dropdownRepeatIntervalUnit = By.xpath("//button[@data-id='repeat_type_custom']");

    private By selectDropdownRepeatIntervalUnit(String unit) {
        By value = By.xpath("(//button[@data-id='repeat_type_custom']/following-sibling::div)/descendant::span[text()='" + unit + "']");
        return value;
    }

    private By checkboxInfinity = By.xpath("//input[@id='unlimited_cycles']");
    private By labelCheckboxInfinity = By.xpath("//label[@for='unlimited_cycles']");
    private By inputTotalCycles = By.xpath("//input[@id='cycles']");

    /// related to
    private By dropdownRelatedToType = By.xpath("//button[@data-id='rel_type']");

    private By selectDropdownRelatedToType(String type) {
        By value = By.xpath("(//button[@data-id='rel_type']/following-sibling::div)/descendant::span[text()='" + type + "']");
        return value;
    }

    private By dropdownRelatedToValue = By.xpath("//button[@data-id='rel_id']");
    private By inputSearchRelatedToValue = By.xpath("(//button[@data-id='rel_id']/following-sibling::div)/descendant::input[@aria-label='Search']");

    private By selectDropdownRelatedToValue(String type) {
        By value = By.xpath("(//button[@data-id='rel_id']/following-sibling::div)/descendant::span[contains(normalize-space(),'" + type + "')]");
        return value;
    }

    /// assignees
    private By dropdownAssignees = By.xpath("//button[@data-id='assignees']");
    private By inputSearchAssignees = By.xpath("(//button[@data-id='assignees']/following-sibling::div)/descendant::input[@aria-label='Search']");

    private By selectDropdownAssignees(String assignee) {
        By value = By.xpath("(//button[@data-id='assignees']/following-sibling::div)/descendant::span[normalize-space()='" + assignee + "']");
        return value;
    }

    /// followers
    private By dropdownFollowers = By.xpath("//button[@data-id='followers[]']");
    private By inputSearchFollowers = By.xpath("(//button[@data-id='followers[]']/following-sibling::div)/descendant::input[@aria-label='Search']");
//    private By followersSelectedText = By.xpath("//button[@data-id='followers[]']/descendant::div");

    private By selectDropdownFollowers(String assignee) {
        By value = By.xpath("(//button[@data-id='followers[]']/following-sibling::div)/descendant::span[normalize-space()='" + assignee + "']");
        return value;
    }

    /// tags
    private By labelTags = By.xpath("//label[@for='tags']");
    private By inputTag = By.xpath("(//label[@for='tags']/following-sibling::ul)/descendant::input");
    private By iconCloseTag = By.xpath("(//input[@id='tags']/following-sibling::ul)/descendant::span[text()='×']");

    /// description
    private By inputDescription = By.xpath("//textarea[@id='description']");
    private By iframeDescription = By.xpath("//iframe[@id='description_ifr']");
    private By descriptionEditor = By.xpath("//body[@data-id='description']");

    /// button
    private By buttonCloseDialogAddNew = By.xpath("//form[@id='task-form']/descendant::button[text()='Close']");
    private By buttonSave = By.xpath("//form[@id='task-form']/descendant::button[text()='Save']");

    /// nút [x] task detail
    private By buttonCloseTaskDetail(String taskName) {
        By buttonClose = By.xpath("//h4[contains(normalize-space(),'" + taskName + "')]/preceding-sibling::button");
        return buttonClose;
    }

    //Processing methods
    public void verifyTasksPageDisplayed() {
        Assert.assertTrue(WebUI.checkElementExist(headerTasksSummary, 10, 500), "Task page is not displayed.");
    }

    public void clickNewTaskButton() {
        WebUI.clickElement(buttonNewTask);
    }

    public void verifyAddNewTaskDialogDisplayed() {
        Assert.assertTrue(WebUI.checkElementExist(headerAddNewTask, 5, 500), "Add New Task dialog is not displayed.");
        Assert.assertEquals(WebUI.getElementText(headerAddNewTask).trim(), "Add new task", "The header Add new task does not match.");
    }

    private void handleCheckboxPublic(TaskDTO dto) {
        WebUI.setCheckboxIfChanged(checkboxPublic, dto.isCheckboxPublic(), labelCheckboxPublic);
    }

    private void handleCheckboxBillable(TaskDTO dto) {
        WebUI.setCheckboxIfChanged(checkboxBillable, dto.isCheckboxBillable(), labelCheckboxBillable);
    }

    private void handleAttachFiles(TaskDTO dto, CRMEnum type) {
        Function<Integer, By> iconAddMore = index -> iconAddMoreFiles(index);
        Function<Integer, By> inputAttach = index -> inputAttachFile(index);
        String fullFilePath = SystemHelper.getCurrentDir() + getUploadFilePath();
        WebUI.uploadMultiFileBySendkeys(textLinkAttachFiles, iconAddMore, inputAttach, dto.getAttachFile(), fullFilePath);
    }

    private void handleProjectName(TaskDTO dto, CRMEnum type) {
        if (CRMEnum.EDIT == type && !dto.isUpdateTaskName()) {
            return;
        }

        WebUI.setTextIfChanged(inputTaskName, dto.getTaskName(), "value");
    }

//    private void handleMileStone(TaskDTO dto) {
//        WebUI.selectDropdownIfChanged(dropdownMilestone, dto.getMilestone(), "title", );
//    }

    private void handleStartDate(TaskDTO dto) {
        WebUI.setTextIfChanged(inputStartDate, dto.getStartDate(), "value");
        WebUI.clickElement(labelTaskName);
    }

    private void handleDueDate(TaskDTO dto) {
        WebUI.setTextIfChanged(inputDueDate, dto.getDueDate(), "value");
        WebUI.clickElement(labelTaskName);
    }

    private void handlePriority(TaskDTO dto) {
        String expectedPriority = dto.getPriority();
        WebUI.selectDropdownIfChanged(dropdownPriority, expectedPriority, "title", selectDropdownPriority(expectedPriority));
    }

    private void handleRepeatEvery(TaskDTO dto) {
        String expectedRepeatEvery = dto.getRepeatEvery();
        WebUI.selectDropdownIfChanged(dropdownRepeatEvery, expectedRepeatEvery, "title", selectDropdownRepeatEvery(expectedRepeatEvery));
    }

    private void handleRepeatInterval(TaskDTO dto) {
        if ("Custom".equals(dto.getRepeatEvery())) {
            WebUI.setTextIfChanged(inputRepeatIntervalValue, dto.getRepeatIntervalValue(), "value");
            WebUI.selectDropdownIfChanged(dropdownRepeatIntervalUnit, dto.getRepeatIntervalUnit(), "title", selectDropdownRepeatIntervalUnit(dto.getRepeatIntervalUnit()));
        }
    }

    private void handleCheckboxInfinity(TaskDTO dto) {
        WebUI.setCheckboxIfChanged(checkboxInfinity, dto.isCheckboxInfinity(), labelCheckboxInfinity);
    }

    private void handleTotalCycles(TaskDTO dto) {
        if (WebUI.isCheckboxSelected(checkboxInfinity)) {
            WebUI.setTextIfChanged(inputTotalCycles, dto.getTotalCycles(), "value");
        }
    }

    private void handleRelatedToType(TaskDTO dto) {
        String expectedRelatedToType = dto.getRelatedToType();
        WebUI.selectDropdownIfChanged(dropdownRelatedToType, expectedRelatedToType, "title", selectDropdownRelatedToType(expectedRelatedToType));
    }

    private void handleRelatedToValue(String value) {
        WebUI.selectSearchableDropdownIfChanged(dropdownRelatedToValue, value, "title", inputSearchRelatedToValue, selectDropdownRelatedToValue(value));
    }

    private void handleAssignees(TaskDTO dto) {
        Function<String, By> selectAssignees = assignee -> selectDropdownAssignees(assignee);
        //Function<String, By> selectAssignees = this::selectDropdownAssignees;  (cách viết khác)
        WebUI.selectMultiDropdownToggleIfChanged(dropdownAssignees, dto.getAssignees(), "title", selectAssignees);
    }

    private void handleFollowers(TaskDTO dto) {
        Function<String, By> selectFollowers = this::selectDropdownFollowers;
        WebUI.selectMultiDropdownToggleIfChanged(dropdownFollowers, dto.getFollowers(), "title", selectFollowers);
    }

    private void handleTags(TaskDTO dto) {
        WebUI.setMultiChipInput(inputTag, dto.getTags(), iconCloseTag, labelTags);
    }

    private void handleDescription(TaskDTO dto) {
        WebUI.clickElement(inputDescription);
        WebUI.setTextInIframeIfChanged(iframeDescription, descriptionEditor, dto.getDescription());
    }

    private void fillTaskForm(TaskDTO dto, String projectName, CRMEnum type) {
        handleCheckboxPublic(dto);
        handleCheckboxBillable(dto);
        handleAttachFiles(dto, type);
        handleProjectName(dto, type);
        WebUI.scrollToElementAtBottom(buttonSave);
        handleStartDate(dto);
        handleDueDate(dto);
        handlePriority(dto);
        handleRepeatEvery(dto);
        handleRepeatInterval(dto);
        handleCheckboxInfinity(dto);
        handleTotalCycles(dto);
        handleRelatedToType(dto);
        handleRelatedToValue(projectName);
        handleAssignees(dto);
        handleFollowers(dto);
        handleTags(dto);
        handleDescription(dto);
    }

    private void clickSaveButton() {
        WebUI.clickElement(buttonSave);
    }

    public TaskDTO fillTaskFormAndSave(TaskDTO dto, String projectName, CRMEnum type) {
        fillTaskForm(dto, projectName, type);
        clickSaveButton();

        return dto;
    }

    public void clickbuttonCloseTaskDetail(String taskName) {
        WebUI.waitForPageLoaded();
        WebUI.clickElement(buttonCloseTaskDetail(taskName));
    }

    public void searchTaskByName(String taskName) {
        WebUI.waitForPageLoaded();

        WebUI.clearElementText(inputSearchTasks);
        WebUI.setTextAndKey(inputSearchTasks, taskName, Keys.ENTER);

        WebUI.sleep(1);
    }

    public void verifyTaskDisplayedInList(String taskName) {
        Assert.assertTrue(WebUI.checkElementExist(itemTaskInList(taskName), 10, 500), "Task is not displayed in the task list.");
    }
}
