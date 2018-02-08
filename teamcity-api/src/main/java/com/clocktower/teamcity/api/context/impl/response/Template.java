package com.clocktower.teamcity.api.context.impl.response;

import javax.xml.bind.annotation.XmlAttribute;

public class Template {

    private String id;
    private String projectId;
    private String projectName;
    private Boolean templateFlag;
    private String name;
    private String description;

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

    public void setValuesFrom(com.clocktower.teamcity.api.context.impl.dto.Template template) {
        this.id = template.getId();
        this.name = template.getName();
        this.projectId = template.getProjectId();
        this.description = template.getDescription();
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

}
