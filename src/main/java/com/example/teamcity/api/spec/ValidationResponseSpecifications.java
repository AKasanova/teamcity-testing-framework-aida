package com.example.teamcity.api.spec;

import com.example.teamcity.api.enums.ErrorMessage;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

public class ValidationResponseSpecifications {
    public static ResponseSpecification checkIdAlreadyExists(String id, ErrorMessage errorMessage) {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .expectBody(Matchers.containsString(errorMessage.format(id)))
                .build();
    }
    public static ResponseSpecification checkProjectWithIdAlreadyExist(String projectId) {
        return checkIdAlreadyExists(projectId, ErrorMessage.PROJECT_ID_ALREADY_EXISTS);
    }

    public static ResponseSpecification checkBuildTypeWithIdAlreadyExist(String buildTypeId) {
        return checkIdAlreadyExists(buildTypeId, ErrorMessage.BUILD_TYPE_ID_ALREADY_EXISTS);
    }

}
