package com.clocktower.teamcity.api.context;

import com.clocktower.teamcity.api.context.impl.RestService;
import com.clocktower.teamcity.api.context.impl.response.BuildTypeDto;
import com.clocktower.teamcity.api.context.impl.response.GetBuildTypesResponseDto;
import com.clocktower.teamcity.api.context.impl.response.GetProjectsResponseDto;
import com.clocktower.teamcity.api.context.impl.response.ProjectDto;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ContextTest {

    @Test
    public void loadTest() {
        RestService restService = Mockito.mock(RestService.class);

        ProjectDto rootProjectDto = new ProjectDto();
        rootProjectDto.setId("_Root");
        rootProjectDto.setName("Root project");
        rootProjectDto.setDescription("Root project description");

        ProjectDto projectDto1 = new ProjectDto();
        projectDto1.setId("project1");
        projectDto1.setName("Project 1");
        projectDto1.setDescription("Project 1 description");
        projectDto1.setParentProjectId("_Root");

        ProjectDto projectDto2 = new ProjectDto();
        projectDto2.setId("project2");
        projectDto2.setName("Project 2");
        projectDto2.setParentProjectId("_Root");

        GetProjectsResponseDto getProjectsResponseDto = new GetProjectsResponseDto();
        getProjectsResponseDto.setProject(Arrays.asList(rootProjectDto, projectDto1, projectDto2));

        BuildTypeDto buildTypeDto1 = new BuildTypeDto();
        buildTypeDto1.setId("buildType1");
        buildTypeDto1.setName("Build Type 1");
        buildTypeDto1.setDescription("Build Type 1 description");
        buildTypeDto1.setProjectId("project1");

        BuildTypeDto buildTypeDto2 = new BuildTypeDto();
        buildTypeDto2.setId("buildType2");
        buildTypeDto2.setName("Build Type 2");
        buildTypeDto2.setProjectId("project1");

        GetBuildTypesResponseDto getBuildTypesResponseDto = new GetBuildTypesResponseDto();
        getBuildTypesResponseDto.setBuildType(Arrays.asList(buildTypeDto1, buildTypeDto2));

        when(restService.sendGetRequest("/projects", GetProjectsResponseDto.class)).thenReturn(getProjectsResponseDto);
        when(restService.sendGetRequest("/buildTypes", GetBuildTypesResponseDto.class)).thenReturn(getBuildTypesResponseDto);

        Context context = new Context(restService);
        context.load();

        Project rootProject = context.getRootProject();
        assertEquals("_Root", rootProject.getId());
        assertEquals("Root project", rootProject.getName());
        assertEquals("Root project description", rootProject.getDescription());
        assertEquals(null, rootProject.getParentProjectId());
        List<Project> childProjects = rootProject.getChildProjects();
        assertNotNull(childProjects);
        assertEquals(2, childProjects.size());
        assertNotNull(rootProject.getChildBuildTypes());
        assertEquals(0, rootProject.getChildBuildTypes().size());

        Project project1 = childProjects.get(0);
        assertEquals("project1", project1.getId());
        assertEquals("Project 1", project1.getName());
        assertEquals("Project 1 description", project1.getDescription());
        assertEquals("_Root", project1.getParentProjectId());
        assertNotNull(project1.getChildProjects());
        assertEquals(0, project1.getChildProjects().size());
        List<BuildType> childBuildTypes = project1.getChildBuildTypes();
        assertNotNull(childBuildTypes);
        assertEquals(2, childBuildTypes.size());

        BuildType buildType1 = childBuildTypes.get(0);
        assertEquals("buildType1", buildType1.getId());
        assertEquals("Build Type 1", buildType1.getName());
        assertEquals("Build Type 1 description", buildType1.getDescription());
        assertEquals("project1", buildType1.getParentProjectId());

        BuildType buildType2 = childBuildTypes.get(1);
        assertEquals("buildType2", buildType2.getId());
        assertEquals("Build Type 2", buildType2.getName());
        assertEquals(null, buildType2.getDescription());
        assertEquals("project1", buildType2.getParentProjectId());

        Project project2 = childProjects.get(1);
        assertEquals("project2", project2.getId());
        assertEquals("Project 2", project2.getName());
        assertEquals(null, project2.getDescription());
        assertEquals("_Root", project2.getParentProjectId());
        assertNotNull(project2.getChildProjects());
        assertEquals(0, project2.getChildProjects().size());
        assertNotNull(project2.getChildBuildTypes());
        assertEquals(0, project2.getChildBuildTypes().size());
    }
}