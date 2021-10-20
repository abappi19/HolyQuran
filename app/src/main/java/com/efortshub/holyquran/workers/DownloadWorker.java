package com.efortshub.holyquran.workers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.app.NotificationCompat;
import androidx.work.ForegroundInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.efortshub.holyquran.R;
import com.efortshub.holyquran.activities.settings.DownloadManagerActivity;
import com.efortshub.holyquran.services.CancelDownloadWorkerService;
import com.efortshub.holyquran.utils.HbUtils;
import com.efortshub.holyquran.utils.download_helper.HbDownloadQue;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

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

    private static final String TAG = "hhbbhb";


    public DownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);


    }

    @NonNull
    @Override
    public Result doWork() {

        boolean isNetworkAvailable = false;

        while (!isNetworkAvailable){
                isNetworkAvailable = checkNetworkConnectivity();

            if (isNetworkAvailable){
                Log.d(TAG, "doWork: downloading");
               return download();
            }else {
                Log.d(TAG, "doWork: no internet... notify");
                setForegroundAsync(createForegroundInfo(0,0, isNetworkAvailable,0, null));
            }

        }

        return Result.success();
    }

    private boolean checkNetworkConnectivity() {

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo()!=null){
            if (cm.getActiveNetworkInfo().isConnected()){
                try{
                    URL url = new URL("https://github.com");
                    URLConnection conn = url.openConnection();
                    conn.connect();
                    Log.d(TAG, "checkNetworkConnectivity: has internet");
                    return true;
                }catch (Exception e){
                    URL url = null;
                    try {
                        url = new URL("https://google.com");
                        URLConnection conn = url.openConnection();
                        conn.connect();
                        Log.d(TAG, "checkNetworkConnectivity: has internet google");
                        return true;
                    } catch (IOException ex) {
                        return false;
                    }
                }
            }else return false;
        }

        return false;
    }

    private Result download() {

        HbDownloadQue que = HbDownloadQue.getInstance(getApplicationContext());
        int fileDownloaded = 0;


        for ( int fileRemaining =  Long.valueOf(que.queSize()).intValue(); fileRemaining>0; fileRemaining =   Long.valueOf(que.queSize()).intValue()){

            List<HbDownloadQue.Item> items = que.enQue(1);
            for (HbDownloadQue.Item item: items){
                try {
                    boolean b = false;
                    while (!b){
                        b = checkNetworkConnectivity();
                        if (b){

                            //todo: test download............
                            for (int i=0; i<100; i++){
                                Thread.sleep(100);
                                setForegroundAsync(createForegroundInfo( fileDownloaded, fileRemaining, true, i, item));
                            }
                            if (que.deQue(item))fileDownloaded++;

                        }else {
                            setForegroundAsync(createForegroundInfo(0,0, b,0, item));
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        }

        return Result.success();
    }

    private ForegroundInfo createForegroundInfo(int fileDownloaded, int fileRemaining, boolean hasNetwork, int progressCurrent, HbDownloadQue.Item item) {
        Context context = getApplicationContext();
        PendingIntent cancelIntent = PendingIntent.getService(getApplicationContext(), 123,
                new Intent(getApplicationContext(), CancelDownloadWorkerService.class)
                .setAction("stop")
                , 0);



        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            createChannel();
        }

        int  t = HbUtils.getSavedTheme(context);
        Resources.Theme theme = new ContextThemeWrapper(context, t).getTheme();
        TypedValue primaryVariantValue = new TypedValue();
        theme.resolveAttribute(R.attr.colorPrimaryVariant, primaryVariantValue, true);
        int colorPrimaryVariant = primaryVariantValue.data;

        TypedValue primaryValue = new TypedValue();
        theme.resolveAttribute(R.attr.colorPrimary, primaryValue, true);
        int colorPrimary = primaryValue.data;

        TypedValue selectableValue = new TypedValue();
        theme.resolveAttribute(android.R.attr.listSelector, selectableValue, true);
        int selectableBg = selectableValue.resourceId;


        // set big remote view
        RemoteViews remoteViewsBigContent = new RemoteViews(getApplicationContext().getPackageName(), R.layout.remote_notif_big_download);

        remoteViewsBigContent.setInt(R.id.ll_root, "setBackgroundColor", colorPrimaryVariant);
        remoteViewsBigContent.setInt(R.id.btn_details, "setBackgroundResource", selectableBg);
        remoteViewsBigContent.setInt(R.id.btn_cancel, "setBackgroundResource", selectableBg);

        remoteViewsBigContent.setTextColor(R.id.tv_app_name, colorPrimary);
        remoteViewsBigContent.setTextColor(R.id.btn_details, colorPrimary);
        remoteViewsBigContent.setTextColor(R.id.btn_cancel, colorPrimary);
        remoteViewsBigContent.setTextColor(R.id.tv_download_title, colorPrimary);
        remoteViewsBigContent.setTextColor(R.id.tv_download_progress, colorPrimary);
        remoteViewsBigContent.setTextColor(R.id.tv_remaining_progress, colorPrimary);
        remoteViewsBigContent.setTextColor(R.id.tv_title_remaining, colorPrimary);
        remoteViewsBigContent.setTextColor(R.id.tv_pb_progress, colorPrimary);

        remoteViewsBigContent.setTextViewText(R.id.tv_download_progress, " "+fileDownloaded+"");
        remoteViewsBigContent.setTextViewText(R.id.tv_remaining_progress, " "+fileRemaining+" ");

        if (hasNetwork) {
            remoteViewsBigContent.setTextViewText(R.id.tv_pb_progress, progressCurrent + "% ");
        }else {
            remoteViewsBigContent.setTextViewText(R.id.tv_pb_progress, "Waiting for network...");
        }

        if (hasNetwork){
            if (fileRemaining>0) {
                remoteViewsBigContent.setProgressBar(R.id.pb,  100,0, false);
                if (progressCurrent > 0) {
                    remoteViewsBigContent.setProgressBar(R.id.pb, 100, progressCurrent, false);
                    remoteViewsBigContent.setViewVisibility(R.id.tv_download_progress, View.VISIBLE);
                    remoteViewsBigContent.setViewVisibility(R.id.tv_download_title, View.VISIBLE);
                    remoteViewsBigContent.setViewVisibility(R.id.tv_remaining_progress, View.VISIBLE);
                    remoteViewsBigContent.setViewVisibility(R.id.tv_title_remaining, View.VISIBLE);
                }else{
                    remoteViewsBigContent.setProgressBar(R.id.pb, 100, 0, true);
                    remoteViewsBigContent.setViewVisibility(R.id.tv_download_progress, View.GONE);
                    remoteViewsBigContent.setViewVisibility(R.id.tv_download_title, View.GONE);
                    remoteViewsBigContent.setViewVisibility(R.id.tv_remaining_progress, View.GONE);
                    remoteViewsBigContent.setViewVisibility(R.id.tv_title_remaining, View.GONE);
                }
            }
        }else {
            remoteViewsBigContent.setProgressBar(R.id.pb, 100, 0, true);
            remoteViewsBigContent.setViewVisibility(R.id.tv_download_progress, View.GONE);
            remoteViewsBigContent.setViewVisibility(R.id.tv_download_title, View.GONE);
            remoteViewsBigContent.setViewVisibility(R.id.tv_remaining_progress, View.GONE);
            remoteViewsBigContent.setViewVisibility(R.id.tv_title_remaining, View.GONE);
        }



        if (item!=null){
            if (DownloadManagerActivity.listener!=null){
                DownloadManagerActivity.listener.onDownloadProgress(item, progressCurrent);
            }
        }



/*

        //set normal remote view
        RemoteViews remoteViewNormal = new RemoteViews(getApplicationContext().getPackageName(), R.layout.remote_notif_big_download);

        remoteViewNormal.setInt(R.id.ll_root, "setBackgroundColor", colorPrimaryVariant);
        remoteViewNormal.setInt(R.id.btn_details, "setBackgroundResource", selectableBg);
        remoteViewNormal.setInt(R.id.btn_cancel, "setBackgroundResource", selectableBg);
        remoteViewNormal.setTextColor(R.id.tv_app_name, colorPrimary);
        remoteViewNormal.setTextColor(R.id.btn_details, colorPrimary);
        remoteViewNormal.setTextColor(R.id.btn_cancel, colorPrimary);
        remoteViewNormal.setTextColor(R.id.tv_download_title, colorPrimary);
        remoteViewNormal.setTextColor(R.id.tv_download_progress, colorPrimary);
        remoteViewNormal.setTextViewText(R.id.tv_download_progress, fileDownloaded+"");
*/


        Intent di = new Intent(getApplicationContext(), DownloadManagerActivity.class);
        di.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent detailsIntent = PendingIntent.getActivity(getApplicationContext(), 129, di , PendingIntent.FLAG_ONE_SHOT);
        remoteViewsBigContent.setOnClickPendingIntent(R.id.btn_cancel, cancelIntent);
        remoteViewsBigContent.setOnClickPendingIntent(R.id.btn_details, detailsIntent);



        Notification notification = new NotificationCompat.Builder(context, "idid")
              //  .setCustomContentView(remoteViewNormal)
                .setCustomBigContentView(remoteViewsBigContent)
                .setTicker("ticker title")
                .setSound(null)
                .setSilent(true)
                .setSmallIcon(R.drawable.ic_baseline_cloud_download_24)
                .setOngoing(true)
                .build();


        return new ForegroundInfo(11, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationManager notification = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


        CharSequence name = "Holy Quran eFortsHub";
        String description = "Holy Quran Resource Downloading Notification Status";
        int importance = NotificationManager.IMPORTANCE_MIN;
        NotificationChannel channel = new NotificationChannel("holy_quran_efortshub", name, importance);
        channel.setDescription(description);
        channel.setSound(null,null);
        notification.createNotificationChannel(channel);

    }
}
