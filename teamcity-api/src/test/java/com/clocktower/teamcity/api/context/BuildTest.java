package com.clocktower.teamcity.api.context;
import com.clocktower.teamcity.api.context.impl.dto.BuildType;
import com.clocktower.teamcity.api.context.impl.dto.Project;
import com.clocktower.teamcity.api.context.impl.dto.Template;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class BuildTest {
    @Test
    public void CreateBuildConfigByTemplate() throws JAXBException {

        DocumentBuilderFactory dbFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = dBuilder.newDocument();

        BuildType buildType = new BuildType();
        buildType.setName("MyBuildName");
        buildType.setId("MyBuildID");
        buildType.setProjectId("Etp_LugachevaOlga_InstallerTest");

        Project project = new Project();
        project.setId(buildType.getProjectId());
        project.setName("Installer_test");
        project.setParentProjectId("TheProjectParent");
        buildType.setProject(project);

        Template template = new Template();
        template.setId("Etp_LugachevaOlga_InstallerTest_RsPrepareInstallRhel7");
        template.setName("RS_Prepare_Install_RHEL7");
        template.setProjectName(project.getName());
        template.setTemplateFlag(true);
        template.setProjectId(project.getId());

        JAXBContext jaxbContext = JAXBContext.newInstance(BuildType.class);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        JAXBElement<BuildType> jaxbElement = new JAXBElement<BuildType>(new QName(null, "buildType"), BuildType.class, buildType);
        marshaller.marshal(jaxbElement, System.out);
        marshaller.marshal(jaxbElement, doc);



        try {
            HttpResponse<String> response = Unirest.post("https://teamcity.billing.ru/app/rest/buildTypes")
                    .header("Content-Type", "application/xml")
                    .header("Authorization", "Basic b2xnYS5sdWdhY2hldmE6c2dhbGJncjg=")
                    .header("Cache-Control", "no-cache")
                    .header("Postman-Token", "7ec62f65-4a91-7cea-6396-d0dba25059ac")
                    .body("<buildType id=\"MyBuildID\" name=\"MyBuildName\" projectId=\"Etp_LugachevaOlga_InstallerTest\" >\r\n    <project id=\"Etp_LugachevaOlga_InstallerTest\" name=\"Installer_test\" parentProjectId=\"TheProjectParent\" href=\"TheProjectHREFValue\" webUrl=\"TheWebURLOfTheProejct\"\r\n    />\r\n    <template id=\"Etp_LugachevaOlga_InstallerTest_RsPrepareInstallRhel7\" name=\"RS_Prepare_Install_RHEL7\" templateFlag=\"true\" projectName=\"Installer_test\" projectId=\"Etp_LugachevaOlga_InstallerTest\" href=\"TemplateHRef\" />\r\n    <vcs-root-entries>\r\n        <!--vcs-root-entry elements are not necessary-->\r\n    </vcs-root-entries>\r\n    <settings>          \r\n    </settings>\r\n    <parameters> \r\n    </parameters>\r\n    <steps>\r\n    </steps>\r\n    <features>\r\n    </features>\r\n    <triggers>\r\n    </triggers>\r\n    <snapshot-dependencies/>\r\n    <artifact-dependencies/>\r\n    <agent-requirements/>\r\n    <builds href=\"BuildConfigurationHREF\" />\r\n</buildType>")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void PushConfigToQueue() throws UnirestException {
        HttpResponse<String> response = Unirest.post("https://teamcity.billing.ru/app/rest/buildQueue")
                .header("Content-Type", "application/xml")
                .header("Authorization", "Basic b2xnYS5sdWdhY2hldmE6c2dhbGJncjg=")
                .header("Cache-Control", "no-cache")
                .header("Postman-Token", "47decd60-20d3-3d7f-48c0-14f0b3d4cea9")
                .body("\t<build>\r\n\t\t<buildType id=\"MyBuildID\" />\r\n\t</build>\r\n\t")
                .asString();
    }

}
