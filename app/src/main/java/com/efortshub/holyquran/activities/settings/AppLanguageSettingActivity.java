package com.efortshub.holyquran.activities.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.efortshub.holyquran.R;
import com.efortshub.holyquran.utils.HbUtils;

public class AppLanguageSettingActivity extends AppCompatActivity {


    int oldTheme = R.style.Theme_HBWhiteLight;
    @Override
    protected void onResume() {
        super.onResume();
        if (oldTheme!= HbUtils.getSavedTheme(this)){
            recreate();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        oldTheme = HbUtils.getSavedTheme(this);
        setTheme(oldTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_language_setting);
    }
}