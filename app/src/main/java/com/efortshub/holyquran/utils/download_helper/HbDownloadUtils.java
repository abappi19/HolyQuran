package com.efortshub.holyquran.utils.download_helper;

import android.content.Context;
import android.util.Log;
import android.util.Patterns;
import android.webkit.URLUtil;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.BackoffPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.efortshub.holyquran.interfaces.DownloadFileListener;
import com.efortshub.holyquran.workers.DownloadWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * Created by H. Bappi on  9:36 AM  10/15/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * github: https://github.com/hbappi
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public class HbDownloadUtils {
    private Context context;
    private static final String TAG = "hhbbh";
    private static  HbDownloadUtils hbDownloadUtils;
    public static HbDownloadQue que;
    private static List<Thread> downlodThreads;

    private HbDownloadUtils(Context context){
        this.context = context;

    }
    public static HbDownloadUtils getInstance(Context context){
        if (hbDownloadUtils==null){
            hbDownloadUtils = new HbDownloadUtils(context);
            downlodThreads = new ArrayList<>();
            que = HbDownloadQue.getInstance(context);
        }

        return hbDownloadUtils;
    }

    public void startDownload(@NonNull String url, String title, String subtitle, @Nullable DownloadFileListener downloadFileListener){

        if (url==null) url = "";
        if (title==null) title = "";
        if (subtitle==null) subtitle = "";
        if (Patterns.WEB_URL.matcher(url).matches()){

            boolean isAdded = que.addItem(new HbDownloadQue.Item(url, title, subtitle));
            if (!isAdded){
                Log.d(TAG, "startDownload: que is already exists... "+url);
                if (downloadFileListener != null) {
                    downloadFileListener.onDownloadStarted(null);
                }

            }else {
                Log.d(TAG, "startDownload: que items: ");
            }


        }else if (downloadFileListener!=null) downloadFileListener.onDownloadFailed(new Exception("Not a valid URL"), false);


        OneTimeWorkRequest downloadRequest = new OneTimeWorkRequest.Builder(DownloadWorker.class)
                .addTag("download_worker")
                .build();

        WorkManager.getInstance(context).enqueueUniqueWork("download_worker", ExistingWorkPolicy.APPEND_OR_REPLACE, downloadRequest);


    }


}
