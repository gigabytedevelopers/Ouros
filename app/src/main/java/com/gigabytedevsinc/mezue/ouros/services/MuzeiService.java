package com.gigabytedevsinc.mezue.ouros.services;

import android.content.Intent;

import com.dm.material.dashboard.candybar.services.CandyBarMuzeiService;
import com.gigabytedevsinc.mezue.ouros.R;

public class MuzeiService extends CandyBarMuzeiService {

    private static final String SOURCE_NAME = "CandyBar:MuzeiArtSource";

    public MuzeiService() {
        super(SOURCE_NAME);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startCommand(intent, flags, startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        initMuzeiService();
    }

    @Override
    protected void onTryUpdate(int reason) {
        String wallpaperUrl = getResources().getString(R.string.wallpaper_json);
        tryUpdate(wallpaperUrl);
    }
}
