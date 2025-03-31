package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

@Getter
public class BuildConfigurationElement extends BasePageElement{
    private SelenideElement name;
    private SelenideElement button;



    public BuildConfigurationElement(SelenideElement element) {
        super(element);
        this.name = find("div[class*='Details__heading']");
        this.button = find("button");



    }
}
