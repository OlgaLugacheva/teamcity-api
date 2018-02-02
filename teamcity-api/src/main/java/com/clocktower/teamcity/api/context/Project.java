package com.clocktower.teamcity.api.context;

import com.clocktower.teamcity.api.context.impl.response.ProjectDto;

import java.util.List;
import java.util.stream.Collectors;

public class Project {
//    private final Context context;

    private String id;
    private String parentProjectId;
    private String name;
    // private String description;

    private List<String> childProjectIds;
    private List<String> childBuildTypeIds;

    public Project() {
    }

    public void setValuesFrom(ProjectDto projectDto) {
        this.id = projectDto.getId();
        this.name = projectDto.getName();
        this.parentProjectId = projectDto.getParentProjectId();
    }

    public String getId() {
        return id;
    }

    public String getParentProjectId() {
        return parentProjectId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setParentProjectId(String parentProjectId) {
        this.parentProjectId = parentProjectId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

//    public List<Project> getChildProjects() {
//        return childProjectIds.stream().map(context::getProject).collect(Collectors.toList());
//    }
//
//    public List<BuildType> getChildBuildTypes() {
//        return childBuildTypeIds.stream().map(context::getBuildType).collect(Collectors.toList());
//    }

    void setChildProjectIds(List<String> childProjectIds) {
        this.childProjectIds = childProjectIds;
    }

    void setChildBuildTypeIds(List<String> childBuildTypeIds) {
        this.childBuildTypeIds = childBuildTypeIds;
    }
}
