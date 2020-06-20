package com.gigabytedevsinc.mezue.ouros.activities;

import androidx.annotation.NonNull;

import com.gigabytedevsinc.mezue.ouros.licenses.License;

import candybar.lib.activities.CandyBarMainActivity;
import candybar.lib.activities.configurations.ActivityConfiguration;

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
 * Time: 8:00 AM
 * Desc: MainActivity
 **/
public class MainActivity extends CandyBarMainActivity {

    @NonNull
    @Override
    public ActivityConfiguration onInit() {
        return new ActivityConfiguration()
                .setLicenseCheckerEnabled(License.isLicenseCheckerEnabled())
                .setLicenseKey(License.getLicenseKey())
                .setRandomString(License.getRandomString())
                .setDonationProductsId(License.getDonationProductsId())
                .setPremiumRequestProducts(License.getPremiumRequestProductsId(), License.getPremiumRequestProductsCount());
    }
}
