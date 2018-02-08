package com.clocktower.teamcity.api.builds;

import com.clocktower.teamcity.api.context.impl.dto.BuildType;
import com.clocktower.teamcity.api.context.impl.dto.Project;
import com.clocktower.teamcity.api.context.impl.dto.Template;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class TCBuildCreator {

    private String product;
    private String version;
    private String buildName;
    private String buildId;
    private Template template;

    public TCBuildCreator(String product, String version) {
        this.product = product;
        this.version = version;
        buildName = "Build" + "_" + this.product + "_" + this.version;
        buildId = buildName + "_ID";
    }

    public Template CreateTemplate(String templateName, String templateId, String projectName, String projectId) {
        Template template = new Template();
        template.setId(templateId);
        template.setName(templateName);
        template.setProjectName(projectName);
        template.setTemplateFlag(true);
        template.setProjectId(projectId);
        return template;

    }

    public BuildType BuildProjectFromTemplate(String templateName, String templateId, String projectName, String projectId) throws JAXBException {

        Template template = CreateTemplate(templateName, templateId, projectName, projectId);
        BuildType buildType = new BuildType();
        buildType.setName(this.buildName);
        buildType.setId(buildId);
        buildType.setProjectId(template.getProjectId());

        Project project = new Project();
        project.setId(template.getProjectId());
        project.setName(template.getProjectName());
        project.setParentProjectId("TheProjectParent");
        buildType.setProject(project);
        buildType.setTemplate(template);
        return buildType;

    }

    public String SerializeBuildType(BuildType buildType) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(BuildType.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        java.io.StringWriter sw = new StringWriter();
        marshaller.marshal(buildType, sw);
        String xmlString = sw.toString();
        xmlString = xmlString.replaceAll("\"", "\\\"");
        return xmlString;
    }

    private Template LoadTemplate(String templateName) {
        return null;
    }

    public void GetBuilds() {

    }

    public void PushToQueue() {
    }

    public void UpdateProductVersion(String product, String version) {

    }
}
