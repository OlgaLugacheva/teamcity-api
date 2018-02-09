package com.peterservice.teamcity.api.builds;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.peterservice.teamcity.api.context.impl.HttpRequest;
import com.peterservice.teamcity.api.context.impl.dto.BuildType;
import com.peterservice.teamcity.api.context.impl.dto.Project;
import com.peterservice.teamcity.api.context.impl.dto.Template;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class TCBuildCreator {

    private String product;
    private String version;
    private String buildName;
    private String buildId;
    private String user;
    private String password;
    private String serviceUrl;

    public TCBuildCreator(String product, String version, String serviceUrl, String user, String password) {
        this.product = product;
        this.version = version;
        this.serviceUrl = serviceUrl;
        this.user = user;
        this.password = password;
        buildName = "Build" + "_" + this.product + "_" + this.version;
        buildId = buildName + "_ID";
    }

    public Template createTemplate(String templateName, String templateId, String projectName, String projectId) {
        Template template = new Template();
        template.setId(templateId);
        template.setName(templateName);
        template.setProjectName(projectName);
        template.setTemplateFlag(true);
        template.setProjectId(projectId);
        return template;

    }

    public BuildType createBuildFromTemplate(String templateName, String templateId, String projectName, String projectId) throws JAXBException {

        Template template = createTemplate(templateName, templateId, projectName, projectId);
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

    public String serializeBuildType(BuildType buildType) throws JAXBException {

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

    /**
     * Запушить и заапдейтить версию и название продукта
     *
     * @param templateName
     * @param templateId
     * @param projectName
     * @param projectId
     * @throws Exception
     */
    public void pushBuildToTC(String templateName, String templateId, String projectName, String projectId) throws Exception {
        BuildType buildType = null;
        try {
            buildType = this.createBuildFromTemplate(templateName, templateId, projectName, projectId);
        } catch (JAXBException e) {

            e.printStackTrace();
        }
        String propertyPath = "\t<property name=\"product_name\" value=\" ";
        String requestStringForPush = "\t<build>\r\n\t\t<buildType id=\"" + buildType.getId().toString() + "\" />\r\n\t</build>\r\n\t";
        String requestForBuildCreate = this.serializeBuildType(buildType);
        String newValueForVersion =  propertyPath + this.version + "\" />\r\n\t";
        String newValueForProduct = propertyPath + this.product + "\" />\r\n\t";
        String versionPath = "/app/rest/buildTypes/id:" + buildType.getId() + "/parameters/product_version";
        String namePath = "/app/rest/buildTypes/id:" + buildType.getId() + "/parameters/product_name";

        try (HttpRequest httpRequest = new HttpRequest(serviceUrl, user, password)) {
            httpRequest.doPost("/app/rest/buildTypes", requestForBuildCreate, false);
            httpRequest.doPut(versionPath, newValueForVersion, false);
            httpRequest.doPut(namePath, newValueForProduct, false);
            httpRequest.doPost("/app/rest/buildQueue", requestStringForPush, false);
        }

    }
}
