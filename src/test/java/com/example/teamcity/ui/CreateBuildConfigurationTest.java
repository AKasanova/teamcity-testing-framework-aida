package com.example.teamcity.ui;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.ui.admin.CreateNewBuildConfigurationPage;
import com.example.teamcity.ui.pages.ProjectPage;
import org.testng.annotations.Test;
import com.codeborne.selenide.Condition;

import static com.example.teamcity.api.enums.Endpoint.*;
import static io.qameta.allure.Allure.step;

public class CreateBuildConfigurationTest extends BaseUiTest {
    private static final String REPO_URL = "https://github.com/AKasanova/teamcity-testing-framework-aida";

    @Test(description = "User should be able create a new build configuration for a project", groups = {"Regression"})
    public void userBuildConfigurationTest() {
        // подготовка окружения
        loginAs(testData.getUser());

        // create project on API level
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        var createdProject = userCheckRequests.<Project>getRequest(PROJECTS).read(testData.getProject().getId());

        // взаимодействие с UI
        var buildType = testData.getBuildType();
        CreateNewBuildConfigurationPage.open(createdProject.getId())
                .createForm(REPO_URL)
                .setupBuildConfiguration(buildType.getName());

        // проверка состояния API
        var createdBuildType = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECTS).read(createdProject.getId());
        softy.assertNotNull(createdBuildType);


        // (корректность считывания данных и отображение данных на UI)

       var openedProject = ProjectPage.open(createdProject.getId());
       var foundBuilds = openedProject.getBuilds().stream()
               .anyMatch(build -> build.getName().text().equals(testData.getBuildType().getName()));
       softy.assertTrue(foundBuilds);
    }

    @Test(description = "User should not be able to create build configuration without REPO_URL", groups = {"Negative"})
    public void userCreatesProjectWithoutName() {
        loginAs(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        var createdProject = userCheckRequests.<Project>getRequest(PROJECTS).read(testData.getProject().getId());

        var initialBuildTypesCount = superUserCheckRequests.<BuildType>getRequest(BUILD_TYPES).read(createdProject.getId() + "/buildTypes").getCount();


      CreateNewBuildConfigurationPage.open(createdProject.getId()).createFormWithError("")
              .errorMessage.shouldHave(Condition.exactText("URL must not be empty"));

      var newBuildTypesCount = superUserCheckRequests.<BuildType>getRequest(PROJECT_BUILD_TYPES).read(createdProject.getId() + "/buildTypes").getCount();
      softy.assertEquals(newBuildTypesCount, initialBuildTypesCount);



    }
}
