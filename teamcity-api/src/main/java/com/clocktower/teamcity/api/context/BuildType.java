package com.clocktower.teamcity.api.context;

import com.clocktower.teamcity.api.context.impl.response.BuildTypeDto;

public class BuildType {
//    private final Context context;

    private String id;
    private String projectId;
    private String name;
    private Project project;
    private Template template;
//    private String description;

    public BuildType() {
//        this.context = context;


    }

    public void setValuesFrom(BuildTypeDto buildTypeDto) {
        this.id = buildTypeDto.getId();
        this.name = buildTypeDto.getName();
        this.projectId = buildTypeDto.getProjectId();
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
    public void setName(String name) {
        this.name = name;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public void setId(String id) {

        this.id = id;
    }
}
