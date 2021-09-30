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


    //FETCHING NATIVE VARIABLE HERE INCLUDING API AND OFFLINE URL

    static {
        System.loadLibrary("native-lib");
    }

    public static native String getRestApiBaseUrl();
    public static native String getBaseHbjUrl();
    public static native String stringFromJNI();






}
