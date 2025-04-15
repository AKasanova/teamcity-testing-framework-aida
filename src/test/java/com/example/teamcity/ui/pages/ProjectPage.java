package com.example.teamcity.ui.pages;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.BuildConfigurationElement;
import com.example.teamcity.ui.elements.ProjectElement;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProjectPage extends BasePage {
    private static final String PROJECT_URL = "/project/%s";

    public SelenideElement title = $("span[class*='ProjectPageHeader']");
    private SelenideElement header = $(".MainPanel__router--gF > div");
    public ElementsCollection buildTypeNameElements = $$("div[class*='BuildTypes__item']");

    public static ProjectPage open(String projectId) {
        return Selenide.open(PROJECT_URL.formatted(projectId), ProjectPage.class);
    }

    public ProjectPage() {
        header.shouldBe(Condition.visible, BASE_WAITING);
    }

    public List<BuildConfigurationElement> getBuilds() {
        return generatePageElements(buildTypeNameElements, BuildConfigurationElement::new);
    }
}
