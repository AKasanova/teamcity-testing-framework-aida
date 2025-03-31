package com.example.teamcity.ui.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.ui.pages.ProjectPage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class CreateNewBuildConfigurationPage extends CreateBasePage {
    private static final String BUILD_CONFIGURATION_SHOW_MODE = "createBuildTypeMenu";
    public SelenideElement errorMessage = $(By.xpath("//span[@id='error_url' and text()='URL must not be empty']"));


    public static CreateNewBuildConfigurationPage open(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, BUILD_CONFIGURATION_SHOW_MODE), CreateNewBuildConfigurationPage.class);
    }

    public CreateNewBuildConfigurationPage createForm(String url) {
        baseCreateForm(url);
        errorMessage.shouldBe(Condition.visible, BASE_WAITING);
        return this;
    }


    public CreatedBuildConfigurationPage setupBuildConfiguration(String buildTypeName) {
        buildTypeNameInput.val(buildTypeName);
        submitButton.click();
        return Selenide.page(CreatedBuildConfigurationPage.class);
    }



}
