package com.clocktower.teamcity.api.context;

import com.clocktower.teamcity.api.context.impl.response.BuildTypeDto;

public class BuildType {
    private final Context context;

    private String id;
    private String parentProjectId;
    private String name;
    private String description;

    public BuildType(Context context) {
        this.context = context;
    }

    public void setValuesFrom(BuildTypeDto buildTypeDto) {
        this.id = buildTypeDto.getId();
        this.name = buildTypeDto.getName();
        this.parentProjectId = buildTypeDto.getProjectId();
        this.description = buildTypeDto.getDescription();
    }

    public String getId() {
        return id;
    }

    public String getParentProjectId() {
        return parentProjectId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
