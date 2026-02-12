package com.hatester.crm.steps;

import com.hatester.crm.models.ProjectDTO;
import com.hatester.crm.pages.ProjectPage;
import com.hatester.enums.CRMEnum;

public class ProjectSteps {
    private final ProjectPage projectPage;

    public ProjectSteps(ProjectPage projectPage) {
        this.projectPage = projectPage;
    }

    public ProjectDTO addProject(ProjectDTO projectDTO, String customer) {
        projectPage.verifyProjectsPageDisplayed();
        projectPage.clickNewProjectButton();
        projectPage.verifyAddNewProjectPageDisplayed();

        ProjectDTO proDTO = projectPage.fillProjectFormAndSave(projectDTO, customer, CRMEnum.ADD);
        String projectName = proDTO.getProjectName();

        projectPage.goToProjectsFromMenu();
        projectPage.searchProjectInTable(projectName);
        projectPage.verifyProjectDisplayedInList(projectName);
        projectPage.verifyProjectDataInTable(proDTO, customer);

        return proDTO;
    }

    public void editProject(String project, ProjectDTO projectEdit, String customer) {
        projectPage.clickEditProjectButton(project);
        projectPage.verifyEditProjectPageDisplayed();

        projectPage.fillProjectFormAndSave(projectEdit, customer, CRMEnum.EDIT);
        String finalProjectName = projectEdit.isUpdateProjectName() ? projectEdit.getProjectName() : project;

        // đảm bảo DTO expected mang đúng name cuối cùng
        projectEdit.setProjectName(finalProjectName);

        projectPage.goToProjectsFromMenu();
        projectPage.searchProjectInTable(finalProjectName);
        projectPage.verifyProjectDisplayedInList(finalProjectName);
        projectPage.verifyProjectDataInTable(projectEdit, customer);
    }

    public void deleteProject(String projectName, CRMEnum delete) {
        projectPage.clickDeleteProjectButton(projectName);
        projectPage.confirmDeleteProject(delete);
        projectPage.searchProjectInTable(projectName);
        projectPage.verifyDeleteProjectResult(projectName, delete);
    }
}
