package com.gigabytedevsinc.mezue.ouros.activities;

import androidx.annotation.NonNull;

import com.gigabytedevsinc.mezue.ouros.R;

import candybar.lib.activities.CandyBarSplashActivity;
import candybar.lib.activities.configurations.SplashScreenConfiguration;

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
 * Time: 7:57 AM
 * Desc: SplashActivity
 **/
public class SplashActivity extends CandyBarSplashActivity {

    @NonNull
    @Override
    public SplashScreenConfiguration onInit() {
        return new SplashScreenConfiguration(MainActivity.class)
                .setBottomText(getString(R.string.splash_screen_title));
    }
}
