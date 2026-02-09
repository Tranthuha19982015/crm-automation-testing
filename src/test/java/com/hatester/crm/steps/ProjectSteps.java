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
        projectPage.searchProjectByName(projectName);
        projectPage.verifyProjectDisplayedInList(projectName);

        return proDTO;
    }

    public void editProject(String project, ProjectDTO projectEdit, String customer) {
        projectPage.clickEditProjectButton(project);
        projectPage.verifyEditProjectPageDisplayed();

        ProjectDTO proDTO = projectPage.fillProjectFormAndSave(projectEdit, customer, CRMEnum.EDIT);

        if (projectEdit.isUpdateProjectName()) {
            project = projectEdit.getProjectName();
        }

        projectPage.goToProjectsFromMenu();
        projectPage.searchProjectByName(project);
        projectPage.verifyProjectDisplayedInList(project);
    }

    public void deleteProject(String projectName, CRMEnum delete) {
        projectPage.clickDeleteProjectButton(projectName);
        projectPage.confirmDeleteProject(delete);
        projectPage.searchProjectByName(projectName);
        projectPage.verifyDeleteProjectResult(projectName, delete);
    }
}
