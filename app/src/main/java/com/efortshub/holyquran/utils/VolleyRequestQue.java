package com.efortshub.holyquran.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by H. Bappi on  6:46 PM 10/7/21. Contact email: contact@efortshub.com bappi@efortshub.com
 * contact.efortshub@gmail.com Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public class VolleyRequestQue {
  private static RequestQueue requestQueue = null;

  public static RequestQueue getInstance(Context context){
    if (requestQueue==null) requestQueue =  Volley.newRequestQueue(context);
    return requestQueue;
  }

}
