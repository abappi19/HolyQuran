package com.efortshub.holyquran.activities.settings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.efortshub.holyquran.R;
import com.efortshub.holyquran.databinding.ActivityDownloadManagerBinding;
import com.efortshub.holyquran.interfaces.DownloadFileListener;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDownloadManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listener = this;

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
    protected void onResume() {
        super.onResume();
        listener = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        listener = null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        listener = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

        binding.tvItemTitle.setText(downloadingItem.getTitle());
        binding.tvSubtitle.setText(downloadingItem.getSubtitle());

        binding.pbProgress.setProgress(progress);

    }

    @Override
    public void onDownloadFailed(Exception e, boolean isDownloadAlreadyAdded) {

    }
}