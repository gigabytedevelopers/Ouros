package com.gigabytedevsinc.mezue.ouros.applications;

import androidx.annotation.NonNull;

import candybar.lib.applications.CandyBarApplication;

/**
 * Project - Ouros
 * Created with Android Studio
 * Company: Gigabyte Developers
 * User: Emmanuel Nwokoma
 * Title: Founder and CEO
 * Day: Saturday, 20
 * Month: June
 * Year: 2020
 * Date: 20 Jun, 2020
 * Time: 8:30 AM
 * Desc: CandyBar
 **/
public class CandyBar extends CandyBarApplication {

    @NonNull
    @Override
    public Configuration onInit() {
        // Sample configuration
        Configuration configuration = new Configuration();

        configuration.setGenerateAppFilter(true);
        configuration.setGenerateAppMap(true);
        configuration.setGenerateThemeResources(true);

        return configuration;
    }
}
