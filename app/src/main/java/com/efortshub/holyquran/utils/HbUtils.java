package com.efortshub.holyquran.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.efortshub.holyquran.R;

/**
 * Created by H. Bappi on  12:04 PM 9/29/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public class HbUtils {

    private static SharedPreferences getSharedPreferences(Context context){
        SharedPreferences sp = context.getSharedPreferences("sp", Context.MODE_PRIVATE);

        return sp;
    }

    public static int getSavedTheme(Context context){
        return getSharedPreferences(context).getInt("theme", R.style.Theme_HBWhiteLight);
    }

    public static void saveTheme(Context context, int theme){
        getSharedPreferences(context).edit().putInt("theme", theme).apply();
    }


}
