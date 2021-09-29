package com.efortshub.holyquran.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.efortshub.holyquran.R;
import com.efortshub.holyquran.databinding.ActivityAppCrashBinding;
import com.efortshub.holyquran.utils.HbUtils;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cat.ereza.customactivityoncrash.activity.DefaultErrorActivity;
import cat.ereza.customactivityoncrash.config.CaocConfig;

public class AppCrashActivity extends AppCompatActivity {


    private static final String TAG = "hhhh";

    ActivityAppCrashBinding binding;
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
        binding = ActivityAppCrashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.inclueTitle.tvTitle.setText("eFortsHub Crash Report");
        binding.inclueTitle.tvTitle.setPadding(12,12,12,12);
        binding.inclueTitle.btnGoBack.setVisibility(View.GONE);

        final CaocConfig config = CustomActivityOnCrash.getConfigFromIntent(getIntent());

        String error = CustomActivityOnCrash.getAllErrorDetailsFromIntent(this, getIntent());

        String stackTrace = CustomActivityOnCrash.getStackTraceFromIntent(getIntent());

        binding.tvError.setText(stackTrace);

        Log.d(TAG, "onCreate: "+error);


    }
}