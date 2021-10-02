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
    //DEFAULT ARABIC FONT INITIALIZATION


    public static final int ARABIC_FONT_DEFAULT_SIZE = 14;
    public static final String ARABIC_FONT_DEFAULT_FONT = "othmani";
    public static final String ARABIC_FONT_DEFAULT_SCRIPT = "Imlaei";
    public static final String ARABIC_FONT_DEFAULT_STYLE = "normal";
    public static final String OFFLINE_HBJ_IMLAEI = getOfflineHbjBaseUrl()+"im.hbj";
    public static final String OFFLINE_HBJ_INDOPAK = getOfflineHbjBaseUrl()+"in.hbj";
    public static final String OFFLINE_HBJ_UTHMANI = getOfflineHbjBaseUrl()+"ut.hbj";
    public static final String OFFLINE_HBJ_TRANSLATIONS = getOfflineHbjBaseUrl()+"tr.hbj";
    public static final String OFFLINE_HBJ_TR_EN = getOfflineHbjBaseUrl()+"tr_en.hbj";
    public static final String OFFLINE_HBJ_TR_BN= getOfflineHbjBaseUrl()+"tr_bn.hbj";
    public static final String ONLINE_JSON_TRANSLATION_URL = getOnlineBaseJsonUrl()+"translations/%s.json";
    public static final String ONLINE_JSON_RECITATION_URL = getOnlineBaseJsonUrl()+"recitations/%s.json";



    //FETCHING NATIVE VARIABLE HERE INCLUDING API AND OFFLINE URL

    static {
        System.loadLibrary("native-lib");
    }

    public static native String getOnlineBaseJsonUrl();
    public static native String getOfflineHbjBaseUrl();
    public static native String stringFromJNI();






}
