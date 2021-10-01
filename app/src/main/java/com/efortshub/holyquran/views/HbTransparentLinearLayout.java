package com.efortshub.holyquran.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

/**
 * Created by H. Bappi on  2:30 PM 10/1/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public class HbTransparentLinearLayout extends LinearLayoutCompat {
    public HbTransparentLinearLayout(Context context) {
        super(context);
        setBackground(null);
    }

    public HbTransparentLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackground(null);
    }

    public HbTransparentLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackground(null);
    }

}
