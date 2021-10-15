package com.efortshub.holyquran.workers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.work.ForegroundInfo;
import androidx.work.ListenableWorker;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.efortshub.holyquran.R;

/**
 * Created by H. Bappi on  9:36 AM  10/15/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * github: https://github.com/hbappi
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public class DownloadWorker extends Worker {
    public DownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);


    }

    @NonNull
    @Override
    public Result doWork() {

       // setForegroundAsync(createForegroundInfo("Starting Download"));


        download();


        return Result.success();
    }

    private void download() {
        for (int i =0; i<100; i++){
            try {
                setForegroundAsync(createForegroundInfo("progress: "+i));
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private ForegroundInfo createForegroundInfo(String text) {
        Context context = getApplicationContext();
        PendingIntent cancelIntent = WorkManager.getInstance(context).createCancelPendingIntent(getId());

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            createChannel();
        }

        Notification notification = new NotificationCompat.Builder(context, "idid")
                .setContentTitle("sample download")
                .setContentText(text)
                .setTicker("ticker title")
                .setSmallIcon(R.drawable.ic_baseline_cloud_download_24)
                .setOngoing(true)
                // Add the cancel action to the notification which can
                // be used to cancel the worker
                .addAction(android.R.drawable.ic_delete, "cancel", cancelIntent)
                .build();



        return new ForegroundInfo(11, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationManager notification = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


        CharSequence name = "name";
        String description = "desc";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("idid", name, importance);
        channel.setDescription(description);
        notification.createNotificationChannel(channel);



    }
}
