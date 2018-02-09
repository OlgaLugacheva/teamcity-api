package com.peterservice.teamcity.api;

import com.peterservice.teamcity.api.builds.TCBuildCreator;

public class Main {
    public static void main(String[] args) throws Exception {
        TCBuildCreator tcBuildCreator = new TCBuildCreator("any_product", "555", "teamcity.billing.ru", "olga.lugacheva", "sgalbgr8");
        tcBuildCreator.pushBuildToTC("RS_Prepare_Install_RHEL7", "Etp_LugachevaOlga_InstallerTest_RsPrepareInstallRhel7", "Installer_test", "Etp_LugachevaOlga_InstallerTest");
    }
}
