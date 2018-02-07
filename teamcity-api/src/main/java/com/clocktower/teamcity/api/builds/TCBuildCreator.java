package com.clocktower.teamcity.api.builds;

import com.clocktower.teamcity.api.context.impl.dto.BuildTypeDto;
import com.clocktower.teamcity.api.context.impl.dto.ProjectDto;
import com.clocktower.teamcity.api.context.impl.dto.TemplateDto;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.StringWriter;

public class TCBuildCreator {

    private String product;
    private String version;
    private TemplateDto templateDto;

    public TCBuildCreator( String product, String version) {
      //  this.templateDto = templateDto;
        this.product = product;
        this.version = version;
    }

    //todo: make method that will load templateDto by name
    public TemplateDto CreateTemplate(String templateName, String templateId, String projectName, String projectId) {
        TemplateDto templateDto = new TemplateDto();
        templateDto.setId(templateId);
        templateDto.setName(templateName);
        templateDto.setProjectName(projectName);
        templateDto.setTemplateFlag(true);
        templateDto.setProjectId(projectId);
        return templateDto;

    }

    public BuildTypeDto  BuildProjectFromTemplate(String templateName, String templateId, String buildName, String buildId, String projectName, String projectId, boolean templateFlag) throws JAXBException {

        TemplateDto templateDto = CreateTemplate(templateName, templateId, projectName, projectId);
        BuildTypeDto buildTypeDto = new BuildTypeDto();
        buildTypeDto.setName(buildName);
        buildTypeDto.setId(buildId);
        buildTypeDto.setProjectId(templateDto.getProjectId());

        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(templateDto.getProjectId());
        projectDto.setName(templateDto.getProjectName());
        projectDto.setParentProjectId("TheProjectParent"); //todo: make option
        buildTypeDto.setProjectDto(projectDto);
        buildTypeDto.setTemplateDto(templateDto);


//todo: extract serializer from here

        return buildTypeDto;

    }

    public String SerializeObjects(BuildTypeDto buildTypeDto) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(BuildTypeDto.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        JAXBElement<BuildTypeDto> jaxbElement = new JAXBElement<BuildTypeDto>(new QName(null, "buildTypeDto"), BuildTypeDto.class, buildTypeDto);
        java.io.StringWriter sw = new StringWriter();
        marshaller.marshal(jaxbElement, sw);

        String xmlString = sw.toString();
        xmlString = xmlString.replaceAll("\"", "\\\"");
        return xmlString;
    }

    private TemplateDto LoadTemplate(String templateName) {
        return null;
    }


    public void GetBuilds() {

    }

    public void PushToQueue(String product, String version) {

    }

    public void UpdateProductVersion(String product, String version) {

    }
}
