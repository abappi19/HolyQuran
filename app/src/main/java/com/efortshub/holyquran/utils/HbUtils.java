package com.efortshub.holyquran.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.documentfile.provider.DocumentFile;

import com.efortshub.holyquran.R;
import com.efortshub.holyquran.databases.HbSqliteOpenHelper;
import com.efortshub.holyquran.models.ArabicFontSettings;
import com.efortshub.holyquran.models.DownloadPathDetails;
import com.efortshub.holyquran.models.QuranTranslation;
import com.efortshub.holyquran.models.TranslatedFontSettings;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
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
        return getSharedPreferences(context).getInt(HbConst.KEY_THEME, R.style.Theme_HBWhiteLight);
    }

    public static void saveTheme(Context context, int theme) {
        getSharedPreferences(context).edit().putInt(HbConst.KEY_THEME, theme).apply();

    }


    public static void saveArabicFontSettings(Context context, String fontSize, String script, String fontName, String style) {
        if (fontSize == null) fontSize = HbConst.DEFAULT_ARABIC_FONT_SIZE +"";
        if (script == null) script = HbConst.DEFAULT_ARABIC_SCRIPT;
        if (fontName == null) fontName = HbConst.DEFAULT_ARABIC_FONT;

        getSharedPreferences(context).edit().putString(HbConst.KEY_ARABIC_SCRIPT, script).apply();
        getSharedPreferences(context).edit().putString(HbConst.KEY_ARABIC_FONT, fontName).apply();
        getSharedPreferences(context).edit().putString(HbConst.KEY_ARABIC_FONT_SIZE, fontSize).apply();
        getSharedPreferences(context).edit().putString(HbConst.KEY_ARABIC_FONT_STYLE, style).apply();

    }


    private static ArabicFontSettings getSavedArabicFontSetting(Context context) {
        String script = getSharedPreferences(context).getString(HbConst.KEY_ARABIC_SCRIPT, HbConst.DEFAULT_ARABIC_SCRIPT);
        String font = getSharedPreferences(context).getString(HbConst.KEY_ARABIC_FONT, HbConst.DEFAULT_ARABIC_FONT);
        String fontSize = getSharedPreferences(context).getString(HbConst.KEY_ARABIC_FONT_SIZE, HbConst.DEFAULT_ARABIC_FONT_SIZE +"");
        String style = getSharedPreferences(context).getString(HbConst.KEY_ARABIC_FONT_STYLE, HbConst.DEFAULT_ARABIC_FONT_STYLE +"");
        return new ArabicFontSettings(fontSize, script, font, style);
    }


    public static void saveTranslatedFontSettings(Context context, String fontSize, String fontName, String style) {
        if (fontSize == null) fontSize = HbConst.DEFAULT_ARABIC_FONT_SIZE +"";
        if (fontName == null) fontName = HbConst.DEFAULT_ARABIC_FONT;
        getSharedPreferences(context).edit().putString(HbConst.KEY_TRANSLATION_FONT, fontName).apply();
        getSharedPreferences(context).edit().putString(HbConst.KEY_TRANSLATION_FONT_SIZE, fontSize).apply();
        getSharedPreferences(context).edit().putString(HbConst.KEY_TRANSLATION_FONT_STYLE, style).apply();

    }

    public static void savePrimaryQuranTranslationId(Context context, QuranTranslation translation) {
        if (translation==null){
            translation = new QuranTranslation(HbConst.DEFAULT_ARABIC_PRIMARY_TRANSLATION_LANGUAGE_ID, HbConst.DEFAULT_ARABIC_PRIMARY_TRANSLATION_NAME, HbConst.DEFAULT_ARABIC_PRIMARY_TRANSLATION_LANGUAGE_NAME);
        }

        getSharedPreferences(context).edit().putString(HbConst.KEY_QURAN_PRIMARY_TRANSLATION_LANGUAGE_ID, translation.getId().trim()).apply();
        getSharedPreferences(context).edit().putString(HbConst.KEY_QURAN_PRIMARY_TRANSLATION_LANGUAGE_NAME, translation.getLanguage_name().trim()).apply();
        getSharedPreferences(context).edit().putString(HbConst.KEY_QURAN_PRIMARY_TRANSLATION_NAME, translation.getName().trim()).apply();

    }
    public static void saveSecondaryQuranTranslationId(Context context, QuranTranslation translation) {
        if (translation==null){
            translation = new QuranTranslation(HbConst.DEFAULT_ARABIC_SECONDARY_TRANSLATION_LANGUAGE_ID, HbConst.DEFAULT_ARABIC_SECONDARY_TRANSLATION_NAME, HbConst.DEFAULT_ARABIC_SECONDARY_TRANSLATION_LANGUAGE_NAME);
        }

        getSharedPreferences(context).edit().putString(HbConst.KEY_QURAN_SECONDARY_TRANSLATION_LANGUAGE_ID, translation.getId().trim()).apply();
        getSharedPreferences(context).edit().putString(HbConst.KEY_QURAN_SECONDARY_TRANSLATION_LANGUAGE_NAME, translation.getLanguage_name().trim()).apply();
        getSharedPreferences(context).edit().putString(HbConst.KEY_QURAN_SECONDARY_TRANSLATION_NAME, translation.getName().trim()).apply();

    }

    private static TranslatedFontSettings getSavedTranslatedFontSetting(Context context) {
        String font = getSharedPreferences(context).getString(HbConst.KEY_TRANSLATION_FONT, HbConst.DEFAULT_ARABIC_FONT);
        String fontSize = getSharedPreferences(context).getString(HbConst.KEY_TRANSLATION_FONT_SIZE, HbConst.DEFAULT_ARABIC_FONT_SIZE +"");
        String style = getSharedPreferences(context).getString(HbConst.KEY_TRANSLATION_FONT_STYLE, HbConst.DEFAULT_ARABIC_FONT_STYLE +"");
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

    public static int getArabicFontStyleTypeFace(Context context) {
         String style = getSavedArabicFontSetting(context).getStyle();

         int styleTypeface = Typeface.NORMAL;

        if (style.equals("normal")) {
            styleTypeface = Typeface.NORMAL;
        } else if (style.equals("bold")) {
            styleTypeface = Typeface.BOLD;
        } else if (style.equals("italic")) {
            styleTypeface = Typeface.ITALIC;
        }else if (style.equals("bold_italic")) {
            styleTypeface = Typeface.BOLD_ITALIC;
        }

        return styleTypeface;

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

    public static QuranTranslation getQuranTranslationIdPrimary(@NonNull Context context) {

        String id =  getSharedPreferences(context).getString(HbConst.KEY_QURAN_PRIMARY_TRANSLATION_LANGUAGE_ID, HbConst.DEFAULT_ARABIC_PRIMARY_TRANSLATION_LANGUAGE_ID);
        String name =  getSharedPreferences(context).getString(HbConst.KEY_QURAN_PRIMARY_TRANSLATION_NAME, HbConst.DEFAULT_ARABIC_PRIMARY_TRANSLATION_NAME);
        String language_name =  getSharedPreferences(context).getString(HbConst.KEY_QURAN_PRIMARY_TRANSLATION_LANGUAGE_NAME, HbConst.DEFAULT_ARABIC_PRIMARY_TRANSLATION_LANGUAGE_NAME);


        return new QuranTranslation(id, name, language_name);
    }

    public static QuranTranslation getQuranTranslationIdSecondary(@NonNull Context context) {

        String id =  getSharedPreferences(context).getString(HbConst.KEY_QURAN_SECONDARY_TRANSLATION_LANGUAGE_ID, HbConst.DEFAULT_ARABIC_SECONDARY_TRANSLATION_LANGUAGE_ID);
        String name =  getSharedPreferences(context).getString(HbConst.KEY_QURAN_SECONDARY_TRANSLATION_NAME, HbConst.DEFAULT_ARABIC_SECONDARY_TRANSLATION_NAME);
        String language_name =  getSharedPreferences(context).getString(HbConst.KEY_QURAN_SECONDARY_TRANSLATION_LANGUAGE_NAME, HbConst.DEFAULT_ARABIC_SECONDARY_TRANSLATION_LANGUAGE_NAME);


        return new QuranTranslation(id, name, language_name);
    }

    public static boolean getQuranTranslationVisibilityPrimary(Context context) {
        return getSharedPreferences(context).getBoolean(HbConst.KEY_TRANSLATION_VISIBILITY_PRIMARY, HbConst.DEFAULT_QURAN_TRANSLATION_VISIBILITY_PRIMARY);
    }

    public static boolean getQuranTranslationVisibilitySecondary(Context context) {
        return getSharedPreferences(context).getBoolean(HbConst.KEY_TRANSLATION_VISIBILITY_SECONDARY, HbConst.DEFAULT_QURAN_TRANSLATION_VISIBILITY_SECONDARY);
    }
    public static void setQuranTranslationVisibilityPrimary(Context context, boolean trns) {
        getSharedPreferences(context).edit().putBoolean(HbConst.KEY_TRANSLATION_VISIBILITY_PRIMARY, trns).apply();
    }

    public static void setQuranTranslationVisibilitySecondary(Context context, boolean trns) {
        getSharedPreferences(context).edit().putBoolean(HbConst.KEY_TRANSLATION_VISIBILITY_SECONDARY, trns).apply();

    }

    public static File getSystemAllocatedDownloadDir(Context context){

        File mainDir = new File( context.getFilesDir().getAbsolutePath(), HbConst.KEY_DOWNLOAD_DIR_MAIN_PATH);
        if (!mainDir.exists()){
            if(mainDir.mkdirs()){
                return mainDir;
            }
        }





        return mainDir;
    }
    public static File getSystemAllocatedDownloadDir(Context context, String subPath){
        File mainDir = new File( context.getFilesDir().getAbsolutePath(), HbConst.KEY_DOWNLOAD_DIR_MAIN_PATH);

        if (!mainDir.exists()){
            mainDir.mkdirs();
        }
        return mainDir;
    }

    public static DocumentFile getIntentDocumentDownloadDir(Context context, Uri uri) throws Exception {

      if (uri.toString().endsWith("%2FHolyQuran")){
          throw new Exception(context.getString(R.string.txt_cant_select_path_that_contains)+" HolyQuran");
        }else if (uri.toString().contains("tree/primary%3A")){
          throw new Exception(context.getString(R.string.txt_we_dont_allow_phone_storage));
      }else {
          DocumentFile documentFile = DocumentFile.fromTreeUri(context, uri);
          DocumentFile unf = documentFile.findFile(HbConst.KEY_DOWNLOAD_DIR_MAIN_PATH);
          if (unf!=null) {
              if (!unf.exists()) {
                  DocumentFile dfm = documentFile.createDirectory(HbConst.KEY_DOWNLOAD_DIR_MAIN_PATH);
                  unf = dfm;
              }
          }else {
              DocumentFile dfm = documentFile.createDirectory(HbConst.KEY_DOWNLOAD_DIR_MAIN_PATH);
              unf = dfm;
          }
          return unf;
      }
    }
    public static DownloadPathDetails getSavedDownloadPathDetails(Context context){
       boolean isSystemAllocated =  getSharedPreferences(context).getBoolean(HbConst.KEY_IS_SYSTEM_ALLOCATED_DOWNLOAD_PATH, true);
        String uriString = getSharedPreferences(context).getString(HbConst.KEY_IS_CUSTOM_DOCUMENT_DOWNLOAD_URI, "");

        DownloadPathDetails downloadPathDetails  = new DownloadPathDetails(Uri.parse(uriString), isSystemAllocated);
        return downloadPathDetails;
    }
    public static void setSavedDownloadPathDetails(Context context, Uri uri){

        HbSqliteOpenHelper oh = HbSqliteOpenHelper.getInstance(context.getApplicationContext());

        getSharedPreferences(context).edit().putBoolean(HbConst.KEY_IS_SYSTEM_ALLOCATED_DOWNLOAD_PATH, uri==null).apply();
        if (uri!=null) {
            boolean b = oh.insertNewCustomPath(uri.toString());
            getSharedPreferences(context).edit().putString(HbConst.KEY_IS_CUSTOM_DOCUMENT_DOWNLOAD_URI, uri.toString()).apply();
        }else {
            getSharedPreferences(context).edit().putString(HbConst.KEY_IS_CUSTOM_DOCUMENT_DOWNLOAD_URI, "").apply();
        }


    }
    public static DocumentFile getSavedDocumentDownloadDir(Context context,@NonNull String uriString,@Nullable String subPath) throws Exception {
        Uri uri;
        try{

          /*  if (uriString==null){
                uri = Uri.parse(getSavedDownloadPathDetails(context));
            }else if (uriString.trim().isEmpty()) {
                uri = Uri.parse(getSavedDownloadPathDetails(context));
            }else {
                uri = Uri.parse(uriString);
            }*/
            uri = Uri.parse(uriString);

            DocumentFile documentFile = DocumentFile.fromTreeUri(context, uri);
            DocumentFile unf = documentFile;

            if (!uri.toString().endsWith("%2FHolyQuran")){
                unf = documentFile.findFile(HbConst.KEY_DOWNLOAD_DIR_MAIN_PATH);
                if (unf!=null) {
                    if (!unf.exists()) {
                        DocumentFile dfm = documentFile.createDirectory(HbConst.KEY_DOWNLOAD_DIR_MAIN_PATH);
                        unf = dfm;
                    }
                }else {
                    DocumentFile dfm = documentFile.createDirectory(HbConst.KEY_DOWNLOAD_DIR_MAIN_PATH);
                    unf = dfm;
                }
            }
            if (subPath!=null){
                DocumentFile subFile = unf.findFile(subPath);
                if (subFile!=null) {
                    if (!subFile.exists()) {
                        DocumentFile dfm = documentFile.createDirectory(HbConst.KEY_DOWNLOAD_DIR_MAIN_PATH);
                        subFile = dfm;
                    }
                }else {
                    DocumentFile dfm = documentFile.createDirectory(HbConst.KEY_DOWNLOAD_DIR_MAIN_PATH);
                    subFile = dfm;
                }
                return subFile;
            }else return unf;

        }catch (Exception e){
            throw new Exception(e.getMessage(), e.getCause());
        }
    }

    public static boolean isColorDark(int color){
        if (getContrastColor(color)==-1){
            return false;
        }else return true;
    }

    public static boolean isDarkTheme(Context context){
        Resources.Theme myTheme = new ContextThemeWrapper(context, getSavedTheme(context)).getTheme();

        TypedValue typeColor = new TypedValue();
        myTheme.resolveAttribute(R.attr.colorPrimaryVariant, typeColor, true);
        int colortheme = typeColor.data;

        return !isColorDark(colortheme);
    }

    public static int getContrastColor(int color) {
        double y = (299 * Color.red(color) + 587 * Color.green(color) + 114 * Color.blue(color)) / 1000;
        int c =  y >= 128 ? Color.BLACK : Color.WHITE;
        return c;
    }


    public static boolean getShowTajweedCheck(Context context) {
        return getSharedPreferences(context).getBoolean(HbConst.KEY_SHOW_TAJWEED, true);
    }
    public static void setShowTajweedCheck(Context context, boolean isShowTajweed) {
         getSharedPreferences(context).edit().putBoolean(HbConst.KEY_SHOW_TAJWEED, isShowTajweed).apply();
    }

    public static void setTajweedFilteredTextView(TextView tv, String text) {
        Context context = tv.getContext();

        tv.setTypeface(getArabicFont(context), getArabicFontStyleTypeFace(context));
        tv.setTextSize(getArabicFontSize(context));
        if (getShowTajweedCheck(context)) {
            tv.setText(QuranArabicUtils.getTajweed(context, text));
        }else tv.setText(text);


    }

    public static void setShouldStartDownlaod(Context context,boolean b) {
        getSharedPreferences(context).edit().putBoolean("should_st_d", b).apply();
    }
    public static boolean getShouldStartDownlaod(Context context) {
       return getSharedPreferences(context).getBoolean("should_st_d", false);
    }
}
