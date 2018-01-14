package com.clocktower.teamcity.api.context.impl.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetBuildTypesResponseDto {
    private List<BuildTypeDto> buildType;

    public List<BuildTypeDto> getBuildType() {
        return buildType;
    }

    public void setBuildType(List<BuildTypeDto> buildType) {
        this.buildType = buildType;
    }
}
