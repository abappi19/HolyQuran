package com.efortshub.holyquran.utils;

/**
 * Created by H. Bappi on  12:52 PM 9/27/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public class HbConst {

    //FETCHING NATIVE VARIABLE HERE INCLUDING API AND OFFLINE URL

    static {
        System.loadLibrary("native-lib");
    }

    public static native String getOnlineBaseJsonUrl();
    public static native String getOfflineHbjBaseUrl();
    public static native String stringFromJNI();


    public static native String getOfflineHbjImlaei();
    public static native String getOfflineHbjIndopak();
    public static native String getOfflineHbjUthmani();
    public static native String getOfflineHbjTranslations();
    public static native String getOfflineHbjTrEn();
    public static native String getOfflineHbjTrBn();



    //DEFAULT ARABIC FONT INITIALIZATION


    public static final int ARABIC_FONT_DEFAULT_SIZE = 14;
    public static final String ARABIC_FONT_DEFAULT_FONT = "othmani";
    public static final String ARABIC_FONT_DEFAULT_SCRIPT = "Imlaei";
    public static final String ARABIC_FONT_DEFAULT_STYLE = "normal";
    public static final String OFFLINE_HBJ_IMLAEI = getOfflineHbjImlaei();
    public static final String OFFLINE_HBJ_INDOPAK = getOfflineHbjIndopak();
    public static final String OFFLINE_HBJ_UTHMANI = getOfflineHbjUthmani();
    public static final String OFFLINE_HBJ_TRANSLATIONS = getOfflineHbjTranslations();
    public static final String OFFLINE_HBJ_TR_EN = getOfflineHbjTrEn();
    public static final String OFFLINE_HBJ_TR_BN= getOfflineHbjTrBn();
    public static final String ONLINE_JSON_TRANSLATION_URL = getOnlineBaseJsonUrl()+"translations/%s.json";
    public static final String ONLINE_JSON_RECITATION_URL = getOnlineBaseJsonUrl()+"recitations/%s.json";


    //DEFAULT KEY USED IN APP TO VALIDATE OF FIND CONTENT
    public static final String KEY_SHARED_PREF_KEY = "sp";
    public static final String KEY_THEME = "theme";
    public static final String KEY_ARABIC_SCRIPT = "arb_script";
    public static final String KEY_ARABIC_FONT = "arb_font";
    public static final String KEY_ARABIC_FONT_SIZE = "arb_font_size";
    public static final String KEY_ARABIC_FONT_STYLE = "arb_font_style";
    public static final String KEY_TRANSLATION_FONT = "trs_font";
    public static final String KEY_TRANSLATION_FONT_SIZE = "trs_font_size";
    public static final String KEY_TRANSLATION_FONT_STYLE = "trs_font_style";
    public static final String KEY_REQUIRED_OPEN_SETTINGS = "restart_required";







}
