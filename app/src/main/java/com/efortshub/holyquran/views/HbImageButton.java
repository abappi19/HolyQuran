package com.efortshub.holyquran.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.efortshub.holyquran.R;

/**
 * Created by H. Bappi on  2:28 PM 10/1/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public class HbImageButton extends AppCompatImageView {
    public HbImageButton(@NonNull Context context) {
        super(context);
        setClickable(true);
        setFocusable(true);
        setBackgroundResource(R.drawable.bg_button_clicable);
       }
    public HbImageButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
        setFocusable(true);
        setBackgroundResource(R.drawable.bg_button_clicable);
        }
    public HbImageButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
        setFocusable(true);
       setBackgroundResource(R.drawable.bg_button_clicable);
    }
}
