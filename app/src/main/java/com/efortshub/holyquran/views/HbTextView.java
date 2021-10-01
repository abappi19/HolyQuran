package com.efortshub.holyquran.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by H. Bappi on  10:33 AM 10/1/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public class HbTextView extends androidx.appcompat.widget.AppCompatTextView {
    public HbTextView(@NonNull Context context) {
        super(context);
        setBackground(null);
    }

    public HbTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackground(null);
    }

    public HbTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackground(null);
    }
}
