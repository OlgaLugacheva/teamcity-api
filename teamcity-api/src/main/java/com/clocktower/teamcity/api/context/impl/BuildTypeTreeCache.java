package com.clocktower.teamcity.api.context.impl;

import com.clocktower.teamcity.api.context.impl.dto.Project;
import com.clocktower.teamcity.api.context.impl.response.BuildType;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BuildTypeTreeCache {

    private final List<Project> projects;
    private final List<BuildType> buildTypes;

    private final Map<String, Project> projectsById;
    private final Map<String, BuildType> buildTypesById;

    public BuildTypeTreeCache(List<Project> projects, List<BuildType> buildTypes) {
        this.projects = projects;
        this.buildTypes = buildTypes;

        projectsById = projects.stream().collect(Collectors.toMap(Project::getId, Function.identity()));
        buildTypesById = buildTypes.stream().collect(Collectors.toMap(BuildType::getId, Function.identity()));
    }

    public List<Project> getProjects() {
        return projects;
    }

    public List<BuildType> getBuildTypes() {
        return buildTypes;
    }

    public Map<String, Project> getProjectsById() {
        return projectsById;
    }

    public Map<String, BuildType> getBuildTypesById() {
        return buildTypesById;
    }
}
