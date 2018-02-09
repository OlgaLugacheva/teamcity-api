package com.peterservice.teamcity.api.context.impl.response;

public class BuildType {
    private String id;
    private String projectId;
    private String name;
    private String description;


    public void setValuesFrom(com.peterservice.teamcity.api.context.impl.dto.BuildType buildType) {
        this.id = buildType.getId();
        this.name = buildType.getName();
        this.projectId = buildType.getProjectId();
    }

    public String getId() {
        return id;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
