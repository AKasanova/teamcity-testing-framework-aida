package com.example.teamcity.api.models;


import com.example.teamcity.api.annotations.Parameterizable;
import com.example.teamcity.api.annotations.Random;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project extends BaseModel {
   @Random
   private String name;
   @Random
   @Parameterizable
   private String id;
   private String locator;

}
