package com.efortshub.holyquran;

import android.app.Application;

import androidx.multidex.MultiDexApplication;

import com.efortshub.holyquran.activities.AppCrashActivity;
import com.efortshub.holyquran.utils.HbUtils;

import cat.ereza.customactivityoncrash.activity.DefaultErrorActivity;
import cat.ereza.customactivityoncrash.config.CaocConfig;


/**
 * Created by H. Bappi on  9:31 PM 9/27/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public class eFortsHub extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        setTheme(HbUtils.getSavedTheme(getApplicationContext()));

        CaocConfig.Builder.create()
                .trackActivities(true) //default: false
                .minTimeBetweenCrashesMs(2000) //default: 3000
                .errorActivity(AppCrashActivity.class)
                .apply();


    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


}
