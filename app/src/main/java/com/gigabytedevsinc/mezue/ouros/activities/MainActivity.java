package com.gigabytedevsinc.mezue.ouros.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dm.material.dashboard.candybar.activities.CandyBarMainActivity;
import com.dm.material.dashboard.candybar.helpers.InAppBillingHelper;
import com.gigabytedevsinc.mezue.ouros.R;
import com.gigabytedevsinc.mezue.ouros.licenses.License;

public class MainActivity extends CandyBarMainActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMainActivity(savedInstanceState,
                new InAppBillingHelper.Property(
                        License.isLicenseCheckerEnabled(),
                        License.getRandomString(),
                        License.getLicenseKey(),
                        License.getDonationProductsId(),
                        License.getPremiumRequestProductsId(),
                        License.getPremiumRequestProductsCount()));

    }

}
