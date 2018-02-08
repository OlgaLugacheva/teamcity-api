package com.clocktower.teamcity.api.context;

import com.clocktower.teamcity.api.builds.TCBuildCreator;
import com.clocktower.teamcity.api.context.impl.SafHttpRequest;
import com.clocktower.teamcity.api.context.impl.dto.BuildType;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import static java.lang.System.exit;
import static java.lang.System.out;
import static org.junit.Assert.assertEquals;

public class TCBuildCreatorTest {
    @Test
    public void testAuthorization() throws Exception {
        String login = "user";
        String password = "password";
        URI tcURL = null;
        try {
            tcURL = new URI("https://teamcity.billing.ru");
        } catch (URISyntaxException e) {
            exit(1);
        }
        String xmlString = "<buildType id=\"MyBuildID\" name=\"MyBuildName\" projectId=\"Etp_LugachevaOlga_InstallerTest\" >\r\n    <projectDto id=\"Etp_LugachevaOlga_InstallerTest\" name=\"Installer_test\" parentProjectId=\"TheProjectParent\" href=\"TheProjectHREFValue\" webUrl=\"TheWebURLOfTheProejct\"\r\n    />\r\n    <templateDto id=\"Etp_LugachevaOlga_InstallerTest_RsPrepareInstallRhel7\" name=\"RS_Prepare_Install_RHEL7\" templateFlag=\"true\" projectName=\"Installer_test\" projectId=\"Etp_LugachevaOlga_InstallerTest\" href=\"TemplateHRef\" />\r\n    <vcs-root-entries>\r\n        <!--vcs-root-entry elements are not necessary-->\r\n    </vcs-root-entries>\r\n    <settings>          \r\n    </settings>\r\n    <parameters> \r\n    </parameters>\r\n    <steps>\r\n    </steps>\r\n    <features>\r\n    </features>\r\n    <triggers>\r\n    </triggers>\r\n    <snapshot-dependencies/>\r\n    <artifact-dependencies/>\r\n    <agent-requirements/>\r\n    <builds href=\"BuildConfigurationHREF\" />\r\n</buildType>";
        SafHttpRequest httpRequest = new SafHttpRequest(tcURL, login, password, "/httpAuth/app/rest");
        httpRequest.createPostRequest("/buildTypes", null, xmlString);
    }

    @Test
    public void CreateBuilds() throws JAXBException {
        TCBuildCreator tcBuildCreator = new TCBuildCreator("product", "1");
        BuildType buildType = null;
        try {
            buildType = tcBuildCreator.BuildProjectFromTemplate("RS_Prepare_Install_RHEL7", "Etp_LugachevaOlga_InstallerTest_RsPrepareInstallRhel7",
                    "Installer_test", "Etp_LugachevaOlga_InstallerTest");
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        String requestStringForPush = "\t<build>\r\n\t\t<buildType id=\" "+ buildType.getId()+"\" />\r\n\t</build>\r\n\t";
        String requestString = tcBuildCreator.SerializeBuildType(buildType);

        try {
            HttpResponse<String> response = Unirest.post("https://teamcity.billing.ru/app/rest/buildTypes")
                    .header("Content-Type", "application/xml")
                    .header("Cache-Control", "no-cache")
                    .header("Postman-Token", "7ec62f65-4a91-7cea-6396-d0dba25059ac")
                    .body(requestString)
                    .asString();

            out.println(response.getBody().toString());
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        try {
            HttpResponse<String> response = Unirest.post("https://teamcity.billing.ru/app/rest/buildQueue")
                    .header("Content-Type", "application/xml")
                    .header("Cache-Control", "no-cache")
                    .body(requestStringForPush)
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        try {
            HttpResponse<String> response = Unirest.get("https://teamcity.billing.ru/app/rest/buildTypes/Etp_LugachevaOlga_InstallerTest_RsPrepareInstallRhel7/id")
                    .header("Content-Type", "application/xml")
                    .header("Cache-Control", "no-cache")
                    .header("Postman-Token", "7ec62f65-4a91-7cea-6396-d0dba25059ac")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }


}
