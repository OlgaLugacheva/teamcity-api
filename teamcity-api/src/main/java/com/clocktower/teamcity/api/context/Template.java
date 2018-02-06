package com.clocktower.teamcity.api.context;

import com.clocktower.teamcity.api.context.impl.response.ProjectDto;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

public class Template {

    private String id;
    private String projectId;
    private String projectName;
    private Boolean templateFlag;

    public String getProjectName() {
        return projectName;
    }
    @XmlAttribute
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Boolean getTemplateFlag() {
        return templateFlag;
    }
    @XmlAttribute
    public void setTemplateFlag(Boolean templateFlag) {
        this.templateFlag = templateFlag;
    }

    private String name;
    // private String description;

    private List<String> childProjectIds;
    private List<String> childBuildTypeIds;


    public void setValuesFrom(ProjectDto projectDto) {
        this.id = projectDto.getId();
        this.name = projectDto.getName();
        this.projectId = projectDto.getParentProjectId();
        //   this.description = projectDto.getDescription();
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

    @XmlAttribute
    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @XmlAttribute
    public void setName(String name) {
        this.name = name;
    }


    void setChildProjectIds(List<String> childProjectIds) {
        this.childProjectIds = childProjectIds;
    }

    void setChildBuildTypeIds(List<String> childBuildTypeIds) {
        this.childBuildTypeIds = childBuildTypeIds;
    }
}
