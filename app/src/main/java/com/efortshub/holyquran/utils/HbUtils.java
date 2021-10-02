package com.efortshub.holyquran.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.efortshub.holyquran.R;
import com.efortshub.holyquran.models.ArabicFontSettings;
import com.efortshub.holyquran.models.TranslatedFontSettings;

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
        SharedPreferences sp = context.getSharedPreferences("sp", Context.MODE_PRIVATE);
        return sp;
    }

    public static int getSavedTheme(Context context) {
        return getSharedPreferences(context).getInt("theme", R.style.Base_Theme_AppCompat_Light);
    }

    public static void saveTheme(Context context, int theme) {
        getSharedPreferences(context).edit().putInt("theme", theme).apply();
    }


    public static void saveArabicFontSettings(Context context, String fontSize, String script, String fontName, String style) {
        if (fontSize == null) fontSize = "14";
        if (script == null) script = "Imlaei";
        if (fontName == null) fontName = "othmani";
        getSharedPreferences(context).edit().putString("arb_script", script).apply();
        getSharedPreferences(context).edit().putString("arb_font", fontName).apply();
        getSharedPreferences(context).edit().putString("arb_font_size", fontSize).apply();
        getSharedPreferences(context).edit().putString("arb_font_style", style).apply();

    }


    private static ArabicFontSettings getSavedArabicFontSetting(Context context) {
        String script = getSharedPreferences(context).getString("arb_script", HbConst.ARABIC_FONT_DEFAULT_SCRIPT);
        String font = getSharedPreferences(context).getString("arb_font", HbConst.ARABIC_FONT_DEFAULT_FONT);
        String fontSize = getSharedPreferences(context).getString("arb_font_size", HbConst.ARABIC_FONT_DEFAULT_SIZE +"");
        String style = getSharedPreferences(context).getString("arb_font_style", HbConst.ARABIC_FONT_DEFAULT_STYLE+"");
        return new ArabicFontSettings(fontSize, script, font, style);
    }


    public static void saveTranslatedFontSettings(Context context, String fontSize, String fontName, String style) {
        if (fontSize == null) fontSize = "14";
        if (fontName == null) fontName = "othmani";
        getSharedPreferences(context).edit().putString("trs_font", fontName).apply();
        getSharedPreferences(context).edit().putString("trs_font_size", fontSize).apply();
        getSharedPreferences(context).edit().putString("trs_font_style", style).apply();

    }


    private static TranslatedFontSettings getSavedTranslatedFontSetting(Context context) {
        String font = getSharedPreferences(context).getString("trs_font", HbConst.ARABIC_FONT_DEFAULT_FONT);
        String fontSize = getSharedPreferences(context).getString("trs_font_size", HbConst.ARABIC_FONT_DEFAULT_SIZE +"");
        String style = getSharedPreferences(context).getString("trs_font_style", HbConst.ARABIC_FONT_DEFAULT_STYLE+"");
        return new TranslatedFontSettings(fontSize, font, style);
    }

    public static String getHbjScriptUrl(Context context) {
        String scriptName =  getSavedArabicFontSetting(context).getFontScriptName();
        String baseHbjUrl = HbConst.getOfflineHbjBaseUrl();

        if (scriptName.equals("Imlaei")) {
            return baseHbjUrl + "im.hbj";
        } else if (scriptName.equals("Indopak")) {
            return baseHbjUrl + "in.hbj";
        } else if (scriptName.equals("Uthmani")) {
            return baseHbjUrl + "ut.hbj";
        } else return baseHbjUrl + "im.hbj";

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
            getSharedPreferences(context).edit().putBoolean("restart_required", isRequired).apply();
            return true;
        }else{
            boolean b =  getSharedPreferences(context).getBoolean("restart_required", false);
            if (b){
                getSharedPreferences(context).edit().putBoolean("restart_required", false).apply();
            }

            return b;
        }

    }
}
