package com.efortshub.holyquran.utils.download_helper;

import android.content.Context;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.efortshub.holyquran.interfaces.DownloadFileListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class HbDownloadUtils {
    private static final String TAG = "hhbbh";
    private static  HbDownloadUtils hbDownloadUtils;
    public static HbDownloadQue que;
    private static List<Thread> downlodThreads;

    private HbDownloadUtils(){

    }
    public static HbDownloadUtils getInstance(Context context){
        if (hbDownloadUtils==null){
            hbDownloadUtils = new HbDownloadUtils();
            downlodThreads = new ArrayList<>();
            que = HbDownloadQue.getInstance(context);
        }

        return hbDownloadUtils;
    }

    public void startDownload(@NonNull String url, @Nullable DownloadFileListener downloadFileListener){

        if (url==null) url = "";

        //todo: remove !not sign after testing
        if (Patterns.WEB_URL.matcher(url).matches()){

            boolean isAdded = que.addItem(new HbDownloadQue.Item(url));
            if (!isAdded){
                Log.d(TAG, "startDownload: que is already exists... "+url);
                if (downloadFileListener != null) {
                    downloadFileListener.onDownloadStarted();
                }

            }else {
                Log.d(TAG, "startDownload: que items: ");




            }


        }else if (downloadFileListener!=null) downloadFileListener.onDownloadFailed(new Exception("Not a valid URL"), false);


    }


}
