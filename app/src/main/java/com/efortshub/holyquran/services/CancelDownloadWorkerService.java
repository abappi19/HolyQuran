package com.efortshub.holyquran.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.work.WorkManager;

public class CancelDownloadWorkerService extends Service {
    private static final String TAG = "hhbbhb";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        WorkManager.getInstance(getApplicationContext())
                .cancelAllWorkByTag("download_worker");

        Log.d(TAG, "onStartCommand: we are cancelling download worker");

        if (intent.getAction().equals("stop")){
            stopSelf();
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
}