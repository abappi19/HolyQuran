package com.efortshub.holyquran.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.efortshub.holyquran.models.DownloadPathDetails;

import java.util.ArrayList;
import java.util.HashMap;

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
    static final int version = 5;
    static final String tableListTable = "table_list_table";

    private HbSqliteOpenHelper(@Nullable Context context) {
        super(context, databaseName, null, version);
    }

    public static HbSqliteOpenHelper getInstance(Context context){
        if (hbSqliteOpenHelper==null) hbSqliteOpenHelper = new HbSqliteOpenHelper(context);
        return hbSqliteOpenHelper;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+tableListTable+" (id integer primary key, table_name text unique);");

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        deleteAllTables(sqLiteDatabase);
        sqLiteDatabase.execSQL("drop table if exists "+tableListTable+";");
        onCreate(sqLiteDatabase);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        onUpgrade(db, oldVersion, newVersion);
    }


    /**
     **
     ** H. Bappi -  https://github.com/hbappi
     **
     **/




    private boolean createNewTable(String tableName, String... params){
        SQLiteDatabase db  = this.getWritableDatabase();

        String paramsHere="";
        for (String s: params){
            paramsHere = paramsHere.concat(", "+s+" text");
        }
        try {
            db.execSQL("create table if not exists " + tableName + " (id integer primary key" + paramsHere + ");");
            ContentValues contentValues = new ContentValues();
            contentValues.put("table_name", tableName);
            db.insert(tableListTable, null, contentValues);
            return true;
        }catch (SQLiteException e){
            e.printStackTrace();
            return false;
        }
    }

    private void deleteAllTables(SQLiteDatabase sqLiteDatabase) {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+tableListTable, null);

        while (cursor.moveToNext()){
            String tableName = cursor.getString(1);
            sqLiteDatabase.execSQL("drop table if exists "+tableName+";");
        }
        cursor.close();

    }

    public boolean insertNewCustomPath(String path){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean b = createNewTable("customLocation", "path");

        boolean result = false;

        try (Cursor cursor = db.rawQuery("select * from customLocation", null)) {
            while (cursor.moveToNext()) {
                String locationPath = cursor.getString(1);
               if (locationPath.equals(path)){
                   result = true;
                   break;
               }
            }

        }

        if (!result) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("path", path);
            db.insert("customLocation", null, contentValues);
            result = true;
        }

        return result;
    }

    public boolean updateCustomPath(int id, String path){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean b = createNewTable("customLocation", "path");
        ContentValues contentValues = new ContentValues();
        contentValues.put("path", path);
        db.update("customLocation", contentValues, "id=?", new String[]{String.valueOf(id)});
        return true;
    }

    public boolean deleteCustomPath(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean b = createNewTable("customLocation", "path");
        db.delete("customLocation", "id = ?", new String[]{String.valueOf(id)});
        return true;
    }

    public ArrayList<DownloadPathDetails> getAllCustomPath(){
        ArrayList<DownloadPathDetails> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        boolean b = createNewTable("customLocation", "path");
        Cursor cursor = db.rawQuery("select * from customLocation where path not null", null);

        while (cursor.moveToNext()){
           int id = cursor.getInt(0);
            String url =  cursor.getString(1);
            DownloadPathDetails downloadPathDetails = new DownloadPathDetails(Uri.parse(url), false, id);
         list.add(downloadPathDetails);
        }

        return list;
    }
}
