package com.efortshub.holyquran.activities.settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.efortshub.holyquran.R;
import com.efortshub.holyquran.databinding.ActivityDownloadLocationBinding;
import com.efortshub.holyquran.utils.HbConst;
import com.efortshub.holyquran.utils.HbUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;

public class DownloadLocationActivity extends AppCompatActivity {

    private static final String TAG = "hhhh";
    ActivityDownloadLocationBinding binding;


    int oldTheme = R.style.Theme_HBWhiteLight;

    @Override
    protected void onResume() {
        super.onResume();
        if (oldTheme!= HbUtils.getSavedTheme(this)){
            recreate();
        }
    }
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        oldTheme = HbUtils.getSavedTheme(this);
        setTheme(oldTheme);
        super.onCreate(savedInstanceState);
        binding = ActivityDownloadLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.includeTitle.tvTitle.setText(getString(R.string.txt_download_path));
        binding.includeTitle.btnGoBack.setOnClickListener(view -> onBackPressed());

        loadDefaultPath();
        loadPermissionRequest();

        initListener();










    }

    private void loadPermissionRequest() {
        if (ContextCompat.checkSelfPermission(DownloadLocationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
        || ContextCompat.checkSelfPermission(DownloadLocationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
        || ContextCompat.checkSelfPermission(DownloadLocationActivity.this, Manifest.permission.MANAGE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            binding.llSecPermissionWarning.setVisibility(View.VISIBLE);
            ActivityCompat.requestPermissions(DownloadLocationActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE},
                    HbConst.REQUEST_CODE_SELECT_STORAGE_PERMISSION);

        }else {
            binding.llSecPermissionWarning.setVisibility(View.GONE);
        }


    }

    private void initListener() {
        binding.btnSetAnotherPath.setOnClickListener(view -> {

            Intent intent;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                intent.putExtra("android.content.extra.SHOW_ADVANCED", true);
                intent.putExtra("android.content.extra.FANCY", true);
                intent.putExtra("android.content.extra.SHOW_FILESIZE", true);
                ActivityCompat.startActivityForResult(DownloadLocationActivity.this, intent, HbConst.REQUEST_CODE_SELECT_DOWNLOAD_PATH, null);
            }






        });
    }

    private void loadDefaultPath() {
        File file = HbUtils.getDownloadDir(DownloadLocationActivity.this);
        String path = file.getAbsolutePath();

        binding.includeDefaultPath.tvLanguageName.setText(R.string.txt_current_path);
        binding.includeDefaultPath.tvTranslationName.setText(path);
        binding.includeDefaultPath.ivDownloadStatus.setImageResource(R.drawable.ic_baseline_snippet_folder_24);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==HbConst.REQUEST_CODE_SELECT_DOWNLOAD_PATH){
            if (resultCode==RESULT_OK){
                if (data!=null){

                    Uri uri = data.getData();
                    Log.d(TAG, "onActivityResult: "+uri);

                    DocumentFile documentFile =  DocumentFile.fromTreeUri(DownloadLocationActivity.this, uri);

                    DocumentFile dfm = documentFile.createDirectory(HbConst.KEY_DOWNLOAD_DIR_MAIN_PATH);


                    Log.d(TAG, "onActivityResult: "+dfm.getUri());


                    /*
                    if (bundle!=null){
                        for (String key : bundle.keySet()){
                            Log.d(TAG, "onActivityResult:  key"+key);
                            Log.d(TAG, "onActivityResult: value of key: "+bundle.get(key));
                        }

                    }else {

                    }*/
                }

            }else {
                Toast.makeText(DownloadLocationActivity.this, "Cancelled by user.", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode== HbConst.REQUEST_CODE_SELECT_STORAGE_PERMISSION){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED
            && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                binding.llSecPermissionWarning.setVisibility(View.GONE);




            }else {
                Toast.makeText(DownloadLocationActivity.this, "Parmission not granted...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}