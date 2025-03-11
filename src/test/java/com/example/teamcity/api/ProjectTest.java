package com.example.teamcity.api;

import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.example.teamcity.api.enums.Endpoint.PROJECTS;
import static com.example.teamcity.api.enums.Endpoint.USERS;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;
import static io.qameta.allure.Allure.step;

@Test(groups = {"Regression"})
public class ProjectTest extends BaseApiTest {
    @Test(description = "User should be able to create a project", groups = {"Positive", "CRUD"})
    public void userCreatesProjectTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        var createdProject = userCheckRequests.<Project>getRequest(PROJECTS).read(testData.getProject().getId());

        softy.assertEquals(testData.getProject().getName(), createdProject.getName(), "Project name is not correct");
    }
    @Test(description = "User should not be able to create 2 projects with the same id", groups = {"Negative", "CRUD"})
    public void userCreatesTwoProjectsWithTheSameIdTest(){
        var ProjectWithSameId = generate(Arrays.asList(testData.getProject()), Project.class, testData.getProject().getId());

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.getRequest(PROJECTS).create(testData.getProject());
        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(ProjectWithSameId)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project ID \"%s\" is already used by another project".formatted(testData.getProject().getId())));

    }
    @Test(description = "User should be able to copy a project", groups = {"Positive", "CRUD"})
    public void userCanCopyExistingProjectTest() {
        step("Create user");
        step("Create project1");
        step("Grant user PROJECT_ADMIN in project");
        // Need to set up SourceProject class with locator field in models to be able to pass it in Project -I'm not sure
        step("Create a copy of project1");
        step("Check that copied project was successfully created and sourceLocator is correct");
    }
    @Test(description = "User should be able to copy a project", groups = {"Positive", "CRUD"})
    public void userCanCreateProjectWithSingleValidSymbolNameTest() {
        step("Create user");
        step("Create project with a name contained 1 valid symbol");
        step("Check project was successfully created");
    }

    @Test(description = "User should be able to copy a project", groups = {"Positive", "CRUD"})
    public void userCanCreateProjectWithMaxValidSymbolNameTest() {
        step("Create user");
        step("Create project with a name contained max number of valid symbols");
        step("Check project was successfully created");
    }

    @Test(description = "User can not create project with an empty name", groups = {"Negative", "CRUD"})
    public void userCreatesProjectWithEmptyNameTest(){
        step("Create user");
        step("Create project with an empty name");
        step("Check project was not created with forbidden code");
    }

    @Test(description = "User can not create project with an empty name", groups = {"Negative", "CRUD"})
    public void userCreatesProjectWithNameWithInvalidSymbolsTest() {
        step("Create user");
        step("Create project with name contained invalid symbols");
        step("Check project was not created with forbidden code");
    }

    @Test(description = "User can not create project with an empty id", groups = {"Negative", "CRUD"})
    public void userCreatesProjectWithEmptyIdTest() {
        step("Create user");
        step("Create project with an empty id");
        step("Check project was not created with forbidden code");
    }

    @Test(description = "User can not create project with an empty id", groups = {"Negative", "CRUD"})
    public void userCreatesProjectWithEmptyIdAndNameTest() {
        step("Create user");
        step("Create project with an empty id and empty name");
        step("Check project was not created with forbidden code");
    }

    @Test(description = "User can not create project with a space before name", groups = {"Negative", "CRUD"})
    public void userCreatesProjectWithSpaceBeforeNameTest() {
        step("Create user");
        step("Create project with a name started with space");
        step("Check project was not created with forbidden code");
    }

    @Test(description = "Project developer should not be able to create project", groups = {"Negative", "Roles"})
    public void ProjectDeveloperCreatesProjectTest(){
        step("Create user");
        step("Create project");
        step("Grant user PROJECT_DEVELOPER in project");
        step("Create a new project");
        step("Check project was not created with forbidden code");
    }

    @Test(description = "Project Viewer should not be able to create project", groups = {"Negative", "Roles"})
    public void ProjectViewerCreatesProjectTest(){
        step("Create user");
        step("Create project");
        step("Grant user PROJECT_VIEWER in project");
        step("Create a new project");
        step("Check project was not created with forbidden code");
    }


}

