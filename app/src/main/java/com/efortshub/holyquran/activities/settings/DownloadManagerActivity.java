package com.efortshub.holyquran.activities.settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.efortshub.holyquran.R;
import com.efortshub.holyquran.activities.SplashActivity;
import com.efortshub.holyquran.databinding.ActivityDownloadManagerBinding;
import com.efortshub.holyquran.interfaces.DownloadFileListener;
import com.efortshub.holyquran.utils.HbUtils;
import com.efortshub.holyquran.utils.download_helper.HbDownloadQue;
import com.efortshub.holyquran.utils.download_helper.HbDownloadUtils;

/**
 * Created by H. Bappi on  9:36 AM  10/15/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * github: https://github.com/hbappi
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/

public class DownloadManagerActivity extends AppCompatActivity implements DownloadFileListener {
    ActivityDownloadManagerBinding binding;
    public static  DownloadFileListener listener;
    private boolean isActivityRunning = false;


    int oldTheme = R.style.Theme_HBWhiteLight;

    @Override
    protected void onResume() {
        super.onResume();
        if (oldTheme!= HbUtils.getSavedTheme(this)){
            recreate();
        }else {
            binding.getRoot().post(()-> isActivityRunning = true);
            listener = this;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        oldTheme = HbUtils.getSavedTheme(this);
        setTheme(oldTheme);

        super.onCreate(savedInstanceState);
        binding = ActivityDownloadManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listener = this;

        binding.getRoot().post(()-> isActivityRunning = true);

        HbDownloadUtils.getInstance(this)
                .startDownload("", null, null, null);

        binding.includeTitle.tvTitle.setText(getString(R.string.txt_download_manager));
        binding.includeTitle.btnGoBack.setOnClickListener(v -> onBackPressed());








    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        listener=this;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        listener =this;
    }


    @Override
    protected void onPause() {
        super.onPause();
        isActivityRunning = false;
        listener = null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isActivityRunning = false;
        listener = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActivityRunning = false;
        listener = null;
    }

    @Override
    public void onDownloadStarted(HbDownloadQue.Item downloadingItem) {

    }

    @Override
    public void onDownloadFinished(HbDownloadQue.Item downloadingItem) {

    }

    @Override
    public void onDownloadProgress(HbDownloadQue.Item downloadingItem, int progress) {
        if (isActivityRunning) {

            binding.tvItemTitle.setText(downloadingItem.getTitle());
            binding.tvSubtitle.setText(downloadingItem.getSubtitle());
            binding.pbProgress.setProgress(progress);


        }
    }

    @Override
    public void onDownloadFailed(Exception e, boolean isDownloadAlreadyAdded) {

    }
}