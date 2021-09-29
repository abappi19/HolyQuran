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
    static {
        System.loadLibrary("native-lib");
    }

    public static native String getRestApiBaseUrl();
    public static native String stringFromJNI();

  //  public static final String BASE_URL  = getBaseUrl();





}
