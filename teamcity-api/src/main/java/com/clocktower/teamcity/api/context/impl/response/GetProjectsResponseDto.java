package com.clocktower.teamcity.api.context.impl.response;

import com.clocktower.teamcity.api.context.impl.dto.Project;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetProjectsResponseDto {
    private List<Project> project;

    public List<Project> getProject() {
        return project;
    }

    public void setProject(List<Project> project) {
        this.project = project;
    }
}
