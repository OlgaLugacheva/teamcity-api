package com.clocktower.teamcity.api.builds;

import com.clocktower.teamcity.api.context.BuildType;
import com.clocktower.teamcity.api.context.Project;
import com.clocktower.teamcity.api.context.Template;

public class TCBuildCreator {

    private String product;
    private String version;
    private Template template;

    public TCBuildCreator(Template template, String product, String version) {
     this.template = template;
     this.product = product;
     this.version = version;
    }

    public void BuildProjectFromTemplate(String templateName, String projectName, String buildName)
    {
        BuildType buildType = new BuildType();
        buildType.setName(buildName);
        buildType.setId();
        buildType.setProjectId("Etp_LugachevaOlga_InstallerTest");
    }

    public void GetBuilds()
    {

    }

    public void PushToQueue(String product, String version )
    {

    }

    public void UpdateProductVersion(String product, String version)
    {
        //

    }
}
