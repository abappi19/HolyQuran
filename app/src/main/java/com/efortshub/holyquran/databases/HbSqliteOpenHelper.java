package com.efortshub.holyquran.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.efortshub.holyquran.models.DownloadPathDetails;

import java.util.ArrayList;

/**
 * Created by H. Bappi on  4:05 PM 10/5/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public class HbSqliteOpenHelper extends SQLiteOpenHelper {

    private static HbSqliteOpenHelper hbSqliteOpenHelper;
    static final String databaseName = "hb.db";
    static final int version = 1;

    private HbSqliteOpenHelper(@Nullable Context context) {
        super(context, databaseName, null, version);
    }

    public static HbSqliteOpenHelper getInstance(Context context){
        if (hbSqliteOpenHelper==null) hbSqliteOpenHelper = new HbSqliteOpenHelper(context);
        return hbSqliteOpenHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table customLocation (id integer primary key, path text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists customLocation");
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        onUpgrade(db, oldVersion, newVersion);
    }
    public boolean insertNewCustomPath(String path){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("path", path);
        db.insert("customLocation", null, contentValues);
        return true;
    }
    public ArrayList<DownloadPathDetails> getAllCustomPath(){
        ArrayList<DownloadPathDetails> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from customLocation", null);

        while (cursor.moveToNext()){
           int id = Integer.parseInt(cursor.getString(0));
            String url =  cursor.getString(1);
            DownloadPathDetails downloadPathDetails = new DownloadPathDetails(Uri.parse(url), false, id);
         list.add(downloadPathDetails);
        }

        return list;
    }
}
