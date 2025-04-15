package com.example.teamcity.ui.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.ui.pages.ProjectPage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class CreateNewBuildConfigurationPage extends CreateBasePage {
    private static final String BUILD_CONFIGURATION_SHOW_MODE = "createBuildTypeMenu";

    public static CreateNewBuildConfigurationPage open(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, BUILD_CONFIGURATION_SHOW_MODE), CreateNewBuildConfigurationPage.class);
    }

    public CreateNewBuildConfigurationPage createForm(String url) {
        baseCreateForm(url);
        connectionSuccessfulMessage.should(Condition.appear, BASE_WAITING);
        return this;
    }

    public CreateNewBuildConfigurationPage createFormWithError(String url, String errorText) {
        baseCreateForm(url);
        $(Selectors.byText(errorText)).shouldBe(Condition.visible);
        return this;
    }


    public CreatedBuildConfigurationPage setupBuildConfiguration(String buildTypeName) {
        buildTypeNameInput.val(buildTypeName);
        submitButton.click();
        submitButton.shouldNotBe(Condition.interactable);
        return Selenide.page(CreatedBuildConfigurationPage.class);
    }



}
