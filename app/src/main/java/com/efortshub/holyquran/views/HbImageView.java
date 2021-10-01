package com.efortshub.holyquran.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * Created by H. Bappi on  2:28 PM 10/1/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public class HbImageView extends AppCompatImageView {
    public HbImageView(@NonNull Context context) {
        super(context);
        setBackground(null);
    }

    public HbImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackground(null);
    }

    public HbImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackground(null);
    }
}
