package com.efortshub.holyquran.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.work.WorkManager;

import com.efortshub.holyquran.activities.settings.DownloadManagerActivity;
import com.efortshub.holyquran.utils.HbUtils;

import java.security.spec.ECField;

public class CancelDownloadWorkerService extends Service {
    private static final String TAG = "hhbbhb";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        HbUtils.setShouldStartDownlaod(getApplicationContext(), false);

        WorkManager.getInstance(getApplicationContext())
                .cancelAllWorkByTag("download_worker");
        try {
            WorkManager.getInstance(getApplicationContext())
                    .cancelUniqueWork("download_worker");
        }catch (Exception e){
            e.printStackTrace();
        }



        Log.d(TAG, "onStartCommand: we are cancelling download worker");
        if (DownloadManagerActivity.listener!=null){
            DownloadManagerActivity.listener.onDownloadFinished(null);
        }

     /*   if (intent.getAction().equals("stop")){
        }
        */
        stopSelf();

        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
}