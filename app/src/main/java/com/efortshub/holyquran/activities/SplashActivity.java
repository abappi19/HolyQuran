package com.efortshub.holyquran.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.efortshub.holyquran.R;
import com.efortshub.holyquran.activities.settings.SettingsActivity;
import com.efortshub.holyquran.databinding.ActivitySplashBinding;
import com.efortshub.holyquran.utils.HbUtils;
import com.efortshub.holyquran.workers.DownloadWorker;

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
            WorkRequest workRequest = new OneTimeWorkRequest.Builder(DownloadWorker.class)
                    .build();
            WorkManager.getInstance(v.getContext()).enqueue(workRequest);

        });





    }

}