package com.peterservice.teamcity.api.context.impl.dto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
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

    public void setDescription(String description) {
        this.description = description;
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
