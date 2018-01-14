package com.clocktower.teamcity.api.context.impl.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetProjectsResponseDto {
    private List<ProjectDto> project;

    public List<ProjectDto> getProject() {
        return project;
    }

    public void setProject(List<ProjectDto> project) {
        this.project = project;
    }
}
