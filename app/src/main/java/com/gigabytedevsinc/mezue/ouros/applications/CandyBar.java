package com.gigabytedevsinc.mezue.ouros.applications;

import com.dm.material.dashboard.candybar.applications.CandyBarApplication;

public class CandyBar extends CandyBarApplication {

    @Override
    public void onCreate() {
        //Sample configuration
        Configuration configuration = new Configuration();

        configuration.setGenerateAppFilter(true);
        configuration.setGenerateAppMap(true);
        configuration.setGenerateThemeResources(true);
        configuration.setDashboardThemingEnabled(true);
        
        initApplication(configuration);
    }
}
