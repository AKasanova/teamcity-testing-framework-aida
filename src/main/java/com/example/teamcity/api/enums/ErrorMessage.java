package com.example.teamcity.api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    PROJECT_ID_ALREADY_EXISTS("Project ID \"%s\" is already used by another project"),
    BUILD_TYPE_ID_ALREADY_EXISTS("The build configuration / template ID \"%s\" is already used by another configuration or template");

    private final String messageFormat;
    public String format(String id) {
        return messageFormat.formatted(id);
    }
}

