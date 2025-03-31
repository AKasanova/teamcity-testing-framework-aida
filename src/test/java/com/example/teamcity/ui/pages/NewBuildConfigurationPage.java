
package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.BuildConfigurationElement;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class NewBuildConfigurationPage extends BasePage {
    private static final String BUILD_CONFIGURATION_URL = "/buildConfiguration/%s_%s";


    private final ElementsCollection buildTypeElements = $$("div[class*='BuildsByBuildType__container']");

    public SelenideElement header = $(".MainPanel__router--gF > div");

    public static NewBuildConfigurationPage open(String projectId) {
        return Selenide.open(BUILD_CONFIGURATION_URL.formatted(projectId), NewBuildConfigurationPage.class);
    }

    public NewBuildConfigurationPage() {
        header.shouldBe(Condition.visible, BASE_WAITING);
    }


  public List<BuildConfigurationElement> getBuilds() {
            return generatePageElements(buildTypeElements, BuildConfigurationElement::new);
        }
    }


