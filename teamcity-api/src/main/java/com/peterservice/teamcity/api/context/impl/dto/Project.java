package com.peterservice.teamcity.api.context.impl.dto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="project")
public class Project {

    private String id;
    private String parentProjectId;
    private String name;
    private String description;


    public String getId() {
        return id;
    }

    public String getParentProjectId() {
        return parentProjectId;
    }

    public String getDescription() {
        return description;
    }

    @XmlAttribute
    public void setId(String id) {
        this.id = id;
    }
    @XmlAttribute
    public void setParentProjectId(String parentProjectId) {
        this.parentProjectId = parentProjectId;
    }

    @XmlAttribute
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
