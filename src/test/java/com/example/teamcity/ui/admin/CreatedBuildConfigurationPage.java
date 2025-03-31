package com.example.teamcity.ui.admin;

import com.codeborne.selenide.Selenide;

public class CreatedBuildConfigurationPage extends CreateBasePage{
    private static final String BUILD_CONFIGURATION_CREATED = "/admin/discoverRunners.html?init=1&id=buildType:%s_%s";

    public static CreatedBuildConfigurationPage open(String projectId, String buildTypeName) {
        return Selenide.open(BUILD_CONFIGURATION_CREATED.formatted(projectId, buildTypeName), CreatedBuildConfigurationPage.class);
    }
}
