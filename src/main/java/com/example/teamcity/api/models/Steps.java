package com.example.teamcity.api.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Steps {
    @Builder.Default
    private Integer count = 0;
    private List<Step> step;

}
