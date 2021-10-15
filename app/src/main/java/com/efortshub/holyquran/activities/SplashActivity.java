package com.efortshub.holyquran.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.efortshub.holyquran.R;
import com.efortshub.holyquran.databinding.ActivitySplashBinding;
import com.efortshub.holyquran.utils.HbConst;
import com.efortshub.holyquran.utils.HbUtils;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;
    int oldTheme = R.style.Theme_HBWhiteLight;

    @Override
    protected void onResume() {
        super.onResume();
        if (oldTheme!=HbUtils.getSavedTheme(this)){
            recreate();
        }else {
            if (
            HbUtils.RequiredOpenSettings(this, false)){
                startActivity(new Intent(SplashActivity.this, SettingsActivity.class));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        oldTheme = HbUtils.getSavedTheme(this);
        setTheme(oldTheme);
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnGoTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, SettingsActivity.class));

            }
        });

        binding.btnGotoDownloadManager.setOnClickListener(v -> {

        });

        




    }

}