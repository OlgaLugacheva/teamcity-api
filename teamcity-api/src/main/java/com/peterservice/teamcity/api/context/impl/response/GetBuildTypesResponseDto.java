package com.peterservice.teamcity.api.context.impl.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetBuildTypesResponseDto {
    private List<BuildType> buildType;

    public List<BuildType> getBuildType() {
        return buildType;
    }

    public void setBuildType(List<BuildType> buildType) {
        this.buildType = buildType;
    }
}
