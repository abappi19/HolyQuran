package com.efortshub.holyquran.utils.download_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.efortshub.holyquran.databases.HbSqliteOpenHelper;
import com.efortshub.holyquran.utils.HbConst;

import java.util.ArrayList;
import java.util.List;

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


    public boolean addItem(Item item){
        helper.createNewTable(HbConst.KEY_TABLE_NAME_DOWNLOAD_QUE, "url");
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+HbConst.KEY_TABLE_NAME_DOWNLOAD_QUE, null);

        boolean b = false;
        while (cursor.moveToNext()){
            String turl = cursor.getString(1);
            if (turl.equals(item.getUrl())) {
                b = true;
                break;
            }
        }

        cursor.close();
        if (!b){
            ContentValues contentValues = new ContentValues();
            contentValues.put("url", item.getUrl());
            db.insert(HbConst.KEY_TABLE_NAME_DOWNLOAD_QUE, null, contentValues);
        }

        db.close();
        return !b;
    }
    public void addItems(ArrayList<Item> items){
        if (items!=null){
            if (items.size()>0){
                for (Item item: items){
                    addItem(item);
                }
            }
        }

    }
    public List<Item> enQue(int size){
        helper.createNewTable(HbConst.KEY_TABLE_NAME_DOWNLOAD_QUE, "url");
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+HbConst.KEY_TABLE_NAME_DOWNLOAD_QUE, null);
        int temp = size;

        List<Item> items = new ArrayList<>();
        while (cursor.moveToNext()){
            if (temp>0){

                String url = cursor.getString(1);
                int id = cursor.getInt(0);
                items.add(new Item(url, id));

                temp--;
            }else break;

        }
        db.close();
        return items;
    }
    public boolean deQue(Item item){
        boolean done =false;
        helper.createNewTable(HbConst.KEY_TABLE_NAME_DOWNLOAD_QUE, "url");
        SQLiteDatabase db = helper.getWritableDatabase();
        int i = db.delete(HbConst.KEY_TABLE_NAME_DOWNLOAD_QUE, "id = ?", new String[]{item.getId()+""});
        if (i!=0) done = true;
        db.close();
      return done;
    }

    public long queSize(){
        helper.createNewTable(HbConst.KEY_TABLE_NAME_DOWNLOAD_QUE, "url");
        SQLiteDatabase db = helper.getWritableDatabase();


        long l =  DatabaseUtils.queryNumEntries(db, HbConst.KEY_TABLE_NAME_DOWNLOAD_QUE);
        db.close();
        return l;

    }

    public static class Item{
        private String url;
        private int id;

        public Item(String url) {
            this.url = url;
            this.id = -1;
        }

        public Item(String url, int id) {
            this.url = url;
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public int getId() {
            return id;
        }
    }
}
