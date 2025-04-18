package com.example.teamcity.api;

import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.Steps;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.example.teamcity.api.enums.Endpoint.*;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;
import static com.example.teamcity.api.spec.ValidationResponseSpecifications.checkBuildTypeWithIdAlreadyExist;
import static io.qameta.allure.Allure.step;

@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseApiTest {
    @Test(description = "User should be able to create build type", groups = {"Positive", "CRUD"})
    public void userCreatesBuildTypeTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        var createdBuildType = userCheckRequests.<BuildType>getRequest(BUILD_TYPES).read(testData.getBuildType().getId());

        BuildType expectedBuildType = testData.getBuildType();
        expectedBuildType.setSteps(new Steps());  // Empty Steps object

        // Now compare with the actual createdBuildType
        softy.assertEquals(createdBuildType, expectedBuildType, "Build type name is not correct");
    }
    @Test(description = "User should not be able to create 2 build types with the same id", groups = {"Negative", "CRUD"})
    public void userCreatesTwoBuildTypeTestWithTheSameIdTest(){
        var buildTypeWithSameId = generate(Arrays.asList(testData.getProject()), BuildType.class, testData.getBuildType().getId());

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());
        new UncheckedBase(Specifications.authSpec(testData.getUser()), BUILD_TYPES)
                .create(buildTypeWithSameId)
                .then().spec(checkBuildTypeWithIdAlreadyExist(testData.getBuildType().getId()));
    }
    @Test(description = "Project admin should be able to create build type for their project", groups = {"Positive", "Roles"})
    public void ProjectAdminCreatesBuildTypeTest(){
        step("Create user");
        step("Create project");
        step("Grant user PROJECT_ADMIN in project");
        step("Check buildType was created successfully");
    }

    @Test(description = "Project admin should not be able to create build type for not their project", groups = {"Negative", "Roles"})
    public void ProjectAdminCreatesBuildTypeForAnotherUserProjectTest(){
        step("Create user1");
        step("Create project1");
        step("Grant user PROJECT_ADMIN in project1");

        step("Create user2");
        step("Create project2");
        step("Grant user PROJECT_ADMIN in project2");

        step("Create buildType1 for project1 by user2");
        step("Check buildType was not created with forbidden code");


    }



}
