package com.efortshub.holyquran.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.efortshub.holyquran.R;
import com.efortshub.holyquran.models.ArabicFontSettings;
import com.efortshub.holyquran.models.TranslatedFontSettings;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by H. Bappi on  12:04 PM 9/29/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public class HbUtils {


    private static SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences sp = context.getSharedPreferences(HbConst.KEY_SHARED_PREF_KEY, Context.MODE_PRIVATE);
        return sp;
    }

    public static int getSavedTheme(Context context) {
        return getSharedPreferences(context).getInt(HbConst.KEY_THEME, R.style.Base_Theme_AppCompat_Light);
    }

    public static void saveTheme(Context context, int theme) {
        getSharedPreferences(context).edit().putInt(HbConst.KEY_THEME, theme).apply();
    }


    public static void saveArabicFontSettings(Context context, String fontSize, String script, String fontName, String style) {
        if (fontSize == null) fontSize = HbConst.ARABIC_FONT_DEFAULT_SIZE+"";
        if (script == null) script = HbConst.ARABIC_FONT_DEFAULT_SCRIPT;
        if (fontName == null) fontName = HbConst.ARABIC_FONT_DEFAULT_FONT;

        getSharedPreferences(context).edit().putString(HbConst.KEY_ARABIC_SCRIPT, script).apply();
        getSharedPreferences(context).edit().putString(HbConst.KEY_ARABIC_FONT, fontName).apply();
        getSharedPreferences(context).edit().putString(HbConst.KEY_ARABIC_FONT_SIZE, fontSize).apply();
        getSharedPreferences(context).edit().putString(HbConst.KEY_ARABIC_FONT_STYLE, style).apply();

    }


    private static ArabicFontSettings getSavedArabicFontSetting(Context context) {
        String script = getSharedPreferences(context).getString(HbConst.KEY_ARABIC_SCRIPT, HbConst.ARABIC_FONT_DEFAULT_SCRIPT);
        String font = getSharedPreferences(context).getString(HbConst.KEY_ARABIC_FONT, HbConst.ARABIC_FONT_DEFAULT_FONT);
        String fontSize = getSharedPreferences(context).getString(HbConst.KEY_ARABIC_FONT_SIZE, HbConst.ARABIC_FONT_DEFAULT_SIZE +"");
        String style = getSharedPreferences(context).getString(HbConst.KEY_ARABIC_FONT_STYLE, HbConst.ARABIC_FONT_DEFAULT_STYLE+"");
        return new ArabicFontSettings(fontSize, script, font, style);
    }


    public static void saveTranslatedFontSettings(Context context, String fontSize, String fontName, String style) {
        if (fontSize == null) fontSize = HbConst.ARABIC_FONT_DEFAULT_SIZE+"";
        if (fontName == null) fontName = HbConst.ARABIC_FONT_DEFAULT_FONT;
        getSharedPreferences(context).edit().putString(HbConst.KEY_TRANSLATION_FONT, fontName).apply();
        getSharedPreferences(context).edit().putString(HbConst.KEY_TRANSLATION_FONT_SIZE, fontSize).apply();
        getSharedPreferences(context).edit().putString(HbConst.KEY_TRANSLATION_FONT_STYLE, style).apply();

    }


    private static TranslatedFontSettings getSavedTranslatedFontSetting(Context context) {
        String font = getSharedPreferences(context).getString(HbConst.KEY_TRANSLATION_FONT, HbConst.ARABIC_FONT_DEFAULT_FONT);
        String fontSize = getSharedPreferences(context).getString(HbConst.KEY_TRANSLATION_FONT_SIZE, HbConst.ARABIC_FONT_DEFAULT_SIZE +"");
        String style = getSharedPreferences(context).getString(HbConst.KEY_TRANSLATION_FONT_STYLE, HbConst.ARABIC_FONT_DEFAULT_STYLE+"");
        return new TranslatedFontSettings(fontSize, font, style);
    }

    public static String getArabicScriptHbj(Context context) {
        String scriptName =  getSavedArabicFontSetting(context).getFontScriptName();
        InputStream fis;

        try {

            if (scriptName.equals("Imlaei")) {
                fis = context.getAssets().open(HbConst.OFFLINE_HBJ_IMLAEI);
            } else if (scriptName.equals("Indopak")) {
                fis = context.getAssets().open(HbConst.OFFLINE_HBJ_INDOPAK);
            } else if (scriptName.equals("Uthmani")) {
                fis = context.getAssets().open(HbConst.OFFLINE_HBJ_UTHMANI);
            } else
                fis = context.getAssets().open(HbConst.OFFLINE_HBJ_IMLAEI);

            InputStreamReader insr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(insr);
            boolean isFirst = true;
            String str;
            StringBuilder sb = new StringBuilder();
            while ((str = bufferedReader.readLine())!=null){
                if (isFirst)
                    isFirst = false;
                else
                    sb.append('\n');
                sb.append(str);
            }

            return sb.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }


    public static String getRecitationListHbj(Context context) {
        InputStream fis;

        try {
            fis = context.getAssets().open(HbConst.OFFLINE_HBJ_IMLAEI);

            InputStreamReader insr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(insr);
            boolean isFirst = true;
            String str;
            StringBuilder sb = new StringBuilder();
            while ((str = bufferedReader.readLine())!=null){
                if (isFirst)
                    isFirst = false;
                else
                    sb.append('\n');
                sb.append(str);
            }

            return sb.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }


    public static JSONObject getTranslationListHbj(Context context) {
        InputStream fis;

        try {
           fis = context.getAssets().open(HbConst.OFFLINE_HBJ_TRANSLATIONS);


            InputStreamReader insr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(insr);
            boolean isFirst = true;
            String str;
            StringBuilder sb = new StringBuilder();
            while ((str = bufferedReader.readLine())!=null){
                if (isFirst)
                    isFirst = false;
                else
                    sb.append('\n');
                sb.append(str);
            }

            return new JSONObject(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

    public static String getHbjScriptName(Context context) {
        return getSavedArabicFontSetting(context).getFontScriptName();

    }

    public static Typeface getArabicFont(Context context) {
        String fontName = getSavedArabicFontSetting(context).getFontName();

        int fontId = R.font.ar_othmani;

        if (fontName.equals("al qalam")) {
            fontId =  R.font.al_qalam_quran_majed;
        } else if (fontName.equals("othmani")) {
            fontId =  R.font.ar_othmani;
        } else if (fontName.equals("excelent_arabic")) {
            fontId =  R.font.excelent_arabic_web;
        } else if (fontName.equals("kitab")) {
            fontId =  R.font.kitab;
        } else if (fontName.equals("noorehidayat")) {
            fontId =  R.font.noorehidayat;
        } else if (fontName.equals("noorehira")) {
            fontId =  R.font.noorehira;
        } else if (fontName.equals("noorehuda")) {
            fontId =  R.font.noorehuda;
        }

        return ResourcesCompat.getFont(context, fontId);
    }

    public static int getArabicFontSize(Context context) {
        return getSavedArabicFontSetting(context).getFontSize();
    }


    public static String getArabicFontName(Context context) {
        return getSavedArabicFontSetting(context).getFontName();
    }

    public static String getArabicFontStyle(Context context) {
        return getSavedArabicFontSetting(context).getStyle();
    }


    public static Typeface getTranslatedFont(Context context) {
        String fontName = getSavedTranslatedFontSetting(context).getFontName();

        int fontId = R.font.ar_othmani;

        if (fontName.equals("al qalam")) {
            fontId =  R.font.al_qalam_quran_majed;
        } else if (fontName.equals("othmani")) {
            fontId =  R.font.ar_othmani;
        } else if (fontName.equals("excelent_arabic")) {
            fontId =  R.font.excelent_arabic_web;
        } else if (fontName.equals("kitab")) {
            fontId =  R.font.kitab;
        } else if (fontName.equals("noorehidayat")) {
            fontId =  R.font.noorehidayat;
        } else if (fontName.equals("noorehira")) {
            fontId =  R.font.noorehira;
        } else if (fontName.equals("noorehuda")) {
            fontId =  R.font.noorehuda;
        }

        return ResourcesCompat.getFont(context, fontId);
    }

    public static int getTranslatedFontSize(Context context) {
        return getSavedTranslatedFontSetting(context).getFontSize();
    }


    public static String getTranslatedFontName(Context context) {
        return getSavedTranslatedFontSetting(context).getFontName();
    }

    public static String getTranslatedFontStyle(Context context) {
        return getSavedTranslatedFontSetting(context).getStyle();
    }

    public static boolean RequiredOpenSettings(@NonNull Context context,  @NonNull boolean isRequired) {
        if (isRequired) {
            getSharedPreferences(context).edit().putBoolean(HbConst.KEY_REQUIRED_OPEN_SETTINGS, isRequired).apply();
            return true;
        }else{
            boolean b =  getSharedPreferences(context).getBoolean(HbConst.KEY_REQUIRED_OPEN_SETTINGS, false);
            if (b){
                getSharedPreferences(context).edit().putBoolean(HbConst.KEY_REQUIRED_OPEN_SETTINGS, false).apply();
            }

            return b;
        }

    }
}
