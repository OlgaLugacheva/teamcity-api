package com.clocktower.teamcity.api;

import com.clocktower.teamcity.api.builds.TCBuildCreator;
import com.clocktower.teamcity.api.context.impl.dto.BuildTypeDto;
import com.clocktower.teamcity.api.context.impl.dto.TemplateDto;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.xml.bind.*;
import java.io.*;

import static java.lang.System.*;

public class Main {
    public static void main(String[] args) throws JAXBException, IOException {

        TCBuildCreator tcBuildCreator = new TCBuildCreator("fdhsfgh", "1.2");

        BuildTypeDto buildTypeDto = tcBuildCreator.BuildProjectFromTemplate("RS_Prepare_Install_RHEL7", "Etp_LugachevaOlga_InstallerTest_RsPrepareInstallRhel7",
                "MyBuildName_1", "MyBuildName_1_id", "Installer_test", "Etp_LugachevaOlga_InstallerTest", true);
        String requestString = tcBuildCreator.SerializeObjects(buildTypeDto);
        try {
            HttpResponse<String> response = Unirest.post("https://teamcity.billing.ru/app/rest/buildTypes")
                    .header("Content-Type", "application/xml")
                    .header("Authorization", "Basic b2xnYS5sdWdhY2hldmE6c2dhbGJncjg=")
                    .header("Cache-Control", "no-cache")
                    .header("Postman-Token", "7ec62f65-4a91-7cea-6396-d0dba25059ac")
                    .body(requestString)
                    .asString();

            out.println(response.getBody().toString());

            out.println(response.getStatusText().toString());
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        try {
            HttpResponse<String> response = Unirest.get("https://teamcity.billing.ru/app/rest/buildTypes/Etp_LugachevaOlga_InstallerTest_RsPrepareInstallRhel7/id")
                    .header("Content-Type", "application/xml")
                    .header("Authorization", "Basic b2xnYS5sdWdhY2hldmE6c2dhbGJncjg=")
                    .header("Cache-Control", "no-cache")
                    .header("Postman-Token", "7ec62f65-4a91-7cea-6396-d0dba25059ac")
                    .asString();

            String responseString = response.getBody().toString();

            out.println(response.getBody());

            out.println(response.getStatusText().toString());
        } catch (UnirestException e) {
            e.printStackTrace();
        }

    }
}
