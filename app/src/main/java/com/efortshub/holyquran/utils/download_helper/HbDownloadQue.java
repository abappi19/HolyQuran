package com.efortshub.holyquran.utils.download_helper;

import android.content.Context;

import com.efortshub.holyquran.databases.HbSqliteOpenHelper;
import com.efortshub.holyquran.utils.HbConst;

import java.util.ArrayList;

public class HbDownloadQue  {
    private static HbDownloadQue que;
    private HbSqliteOpenHelper helper;

    private HbDownloadQue(Context context) {
        this.helper = HbSqliteOpenHelper.getInstance(context);
    }

    public static HbDownloadQue getInstance(Context context){
        if (que==null) que = new HbDownloadQue(context);
        return que;
    }


    boolean enQue(String url){
        helper.createNewTable(HbConst.KEY_TABLE_NAME_DOWNLOAD_QUE, "url");




    }
    private String deQue(){


    }
    boolean isExist(String url) {


    }
}
