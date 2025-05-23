package com.example.teamcity.api.spec;

import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.models.User;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.List;

public class Specifications {
    private static Specifications spec;


    private static RequestSpecBuilder reqBuilder() {
        var requestBuilder = new RequestSpecBuilder();
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
       // reqBuilder.setBaseUri("http://" + Config.getProperty("host")).build();
        requestBuilder.setContentType(ContentType.JSON);
        requestBuilder.setAccept(ContentType.JSON);
        requestBuilder.addFilters(List.of(new RequestLoggingFilter(), new ResponseLoggingFilter()));
        return requestBuilder;
    }

    public static RequestSpecification superUserSpec(){
       var requestBuilder = reqBuilder();
        requestBuilder.setBaseUri("http://%s:%s@%s".formatted( "", Config.getProperty("superUserToken"), Config.getProperty("host")));
       return requestBuilder.build();
    }

    public static RequestSpecification unauthSpec() {
       var requestBuilder = reqBuilder();
       return requestBuilder.build();
    }

   public static RequestSpecification authSpec(User user) {
        /**BasicAuthScheme basicAuthScheme = new BasicAuthScheme();
         *
         *basicAuthScheme.setUserName(user.getUser());
        basicAuthScheme.setPassword(user.getPassword());

        return reqBuilder()
                .setAuth(basicAuthScheme)
                .build();

        */
       var requestBuilder = reqBuilder();
       requestBuilder.setBaseUri("http://%s:%s@%s".formatted(user.getUsername(), user.getPassword(), Config.getProperty("host")));
       return requestBuilder.build();
   }
}
