package com.efortshub.holyquran.utils.download_helper;

import android.content.Context;

import androidx.annotation.NonNull;

import com.efortshub.holyquran.interfaces.DownloadFileListener;

import java.util.ArrayList;
import java.util.List;


public class HbDownloadUtils {
    private Context context;
    private static  HbDownloadUtils hbDownloadUtils;
    private static HbDownloadQue que;
    private static List<Thread> downlodThreads;
    private HbDownloadUtils(@NonNull Context context){
        this.context = context;
    }
    public static HbDownloadUtils getInstance(@NonNull Context context){

        if (hbDownloadUtils==null){
            hbDownloadUtils = new HbDownloadUtils(context);
            downlodThreads = new ArrayList<>();
            que = new HbDownloadQue();
        }

        return hbDownloadUtils;
    }

    public void startDownload(String url, DownloadFileListener downloadFileListener){

        if (que.isExist(url)){
            downloadFileListener.onDownloadFailed(new Exception("Download Already in queue"), true);
        }else {


        }



    }


}
