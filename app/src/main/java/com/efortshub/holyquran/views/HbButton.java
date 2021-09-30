package com.efortshub.holyquran.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.efortshub.holyquran.R;

/**
 * Created by H. Bappi on  10:06 AM 9/30/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public class HbButton extends androidx.appcompat.widget.AppCompatTextView{
    public HbButton(@NonNull Context context) {
        super(context);
        setTextAppearance(context, R.style.HbButtonAppearance);
        setBackground(ContextCompat.getDrawable(context, R.drawable.bg_button_clicable));
        setPadding(18,12,18,12);
    }

    public HbButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setTextAppearance(context, R.style.HbButtonAppearance);
        setBackground(ContextCompat.getDrawable(context, R.drawable.bg_button_clicable));
        setPadding(18,12,18,12);
    }

    public HbButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTextAppearance(context, R.style.HbButtonAppearance);
        setBackground(ContextCompat.getDrawable(context, R.drawable.bg_button_clicable));
        setPadding(18,12,18,12);
    }

}
