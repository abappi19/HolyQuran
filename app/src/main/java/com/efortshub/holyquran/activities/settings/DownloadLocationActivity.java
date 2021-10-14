package com.efortshub.holyquran.activities.settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.efortshub.holyquran.R;
import com.efortshub.holyquran.adapters.DownloadPathListAdapter;
import com.efortshub.holyquran.databases.HbSqliteOpenHelper;
import com.efortshub.holyquran.databinding.ActivityDownloadLocationBinding;
import com.efortshub.holyquran.databinding.DialogDownloadPathActionBinding;
import com.efortshub.holyquran.interfaces.DownloadPathItemClickListener;
import com.efortshub.holyquran.models.DownloadPathDetails;
import com.efortshub.holyquran.utils.HbConst;
import com.efortshub.holyquran.utils.HbUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DownloadLocationActivity extends AppCompatActivity {

    private static final String TAG = "hhhh";
    ActivityDownloadLocationBinding binding;



    int oldTheme = R.style.Theme_HBWhiteLight;

    @Override
    protected void onResume() {
        super.onResume();
        if (oldTheme != HbUtils.getSavedTheme(this)) {
            recreate();
        }else {



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
       // loadPermissionRequest();
        initListener();


    }
/*

    private boolean loadPermissionRequest() {
        if (ContextCompat.checkSelfPermission(DownloadLocationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(DownloadLocationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {

            binding.llSecPermissionWarning.setVisibility(View.VISIBLE);
            ActivityCompat.requestPermissions(DownloadLocationActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    HbConst.REQUEST_CODE_SELECT_STORAGE_PERMISSION);
            return false;
        } else {
            binding.llSecPermissionWarning.setVisibility(View.GONE);
            return true;
        }
    }
*/

    private void initListener() {
        binding.btnSetAnotherPath.setOnClickListener(view -> {
     /*       if (loadPermissionRequest()) {
            }
*/
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
        DownloadPathDetails dpd = HbUtils.getSavedDownloadPathDetails(DownloadLocationActivity.this);

        if (dpd.isSystemAllocated()){

            binding.includeDefaultPath.tvItemMainTitle.setText(getString(R.string.txt_system_allocated));
            File file = HbUtils.getSystemAllocatedDownloadDir(this);
            binding.includeDefaultPath.tvTranslationName.setText(getString(R.string.txt_hidden_system_path));
            File[] files = file.listFiles();
            binding.defaultPathFilesCount.setText(files.length+" Files");
            binding.includeDefaultPath.ivDownloadStatus.setImageResource(R.drawable.ic_baseline_snippet_folder_24);

        }else{
            Uri uri  = dpd.getDocumentMainPathURi();
            Log.d("hhhhbb", "onBindViewHolder: "+uri);
            String filteredPath = dpd.getDocumentMainPathURi().getPath().split("document/")[1];

            Log.d("hhbb", "onBindViewHolder: filtered path: "+filteredPath);

            String title = filteredPath.split(":" )[1].replace("/HolyQuran", "");
            DocumentFile df = DocumentFile.fromTreeUri(this, uri);
            DocumentFile[] dfiles = df.listFiles();
            binding.defaultPathFilesCount.setText(dfiles.length+" Files");
            binding.includeDefaultPath.tvItemMainTitle.setText(title);
            binding.includeDefaultPath.tvTranslationName.setText(filteredPath);
            binding.includeDefaultPath.ivDownloadStatus.setImageResource(R.drawable.ic_baseline_snippet_folder_24);

        }


       binding.includeDefaultPath.btnRoot.setBackground(ContextCompat.getDrawable(DownloadLocationActivity.this, R.drawable.bg_widget_active));

        HbSqliteOpenHelper oh = HbSqliteOpenHelper.getInstance(getApplicationContext());
        List<DownloadPathDetails> list = oh.getAllCustomPath();

        List<DownloadPathDetails> filteredList = new ArrayList<>();

        if (!dpd.isSystemAllocated()){
            filteredList.add(new DownloadPathDetails(null, true));
        }

        for (DownloadPathDetails dd: list){
                if (!dd.getDocumentMainPathURi().equals(dpd.getDocumentMainPathURi())){
                    filteredList.add(dd);
                }

        }

        DownloadPathListAdapter adapter = new DownloadPathListAdapter(filteredList, new DownloadPathItemClickListener(){
            @Override
            public void onItemClicked(DownloadPathDetails downloadPathDetails) {

                AlertDialog alertDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(DownloadLocationActivity.this);
                DialogDownloadPathActionBinding db = DialogDownloadPathActionBinding.inflate(getLayoutInflater(), null, false);
                builder.setView(db.getRoot());
                alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.transparent);

                alertDialog.show();

                db.btnSeeFiles.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri uri= downloadPathDetails.getDocumentMainPathURi();
                    if (uri==null){
                        Toast.makeText(DownloadLocationActivity.this, "We don't provide permission to see System Allocated Path.", Toast.LENGTH_SHORT).show();

                    }else {
                        intent.setDataAndType(uri, DocumentsContract.Document.MIME_TYPE_DIR);
                        startActivity(Intent.createChooser(intent, "Open Via:"));
                    }

                });
                db.btnCloseDialog.setOnClickListener(v -> alertDialog.dismiss());

                db.btnSetDownloadPath.setOnClickListener(v -> {
                    HbUtils.setSavedDownloadPathDetails(DownloadLocationActivity.this, downloadPathDetails.getDocumentMainPathURi());
                    loadDefaultPath();

                    alertDialog.dismiss();
                    Toast.makeText(DownloadLocationActivity.this, getString(R.string.txt_done), Toast.LENGTH_SHORT).show();
                });







            }

            @Override
            public void onItemDeleteRequest(DownloadPathDetails downloadPathDetails) {

            }

            @Override
            public void onItemSetCurrentPathRequest(DownloadPathDetails downloadPathDetails) {

            }
        });

        binding.rvPreviousDownloadPath.setLayoutManager(new LinearLayoutManager(DownloadLocationActivity.this, LinearLayoutManager.VERTICAL, false));
        binding.rvPreviousDownloadPath.setItemViewCacheSize(50);
        binding.rvPreviousDownloadPath.setAdapter(adapter);




    }
/*

    private void loadCustomPath() {
        DownloadPathDetails downloadPathDetails = HbUtils.getSavedDownloadPathDetails(this);

        if (!downloadPathDetails.isSystemAllocated()){

            binding.includeDefaultPath.tvLanguageName.setText(R.string.txt_current_path);
            binding.includeDefaultPath.tvTranslationName.setText(downloadPathDetails.getDocumentMainPathURi().getPath());
            binding.includeDefaultPath.ivDownloadStatus.setImageResource(R.drawable.ic_baseline_snippet_folder_24);







        }



    }
*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == HbConst.REQUEST_CODE_SELECT_DOWNLOAD_PATH) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Uri uri = data.getData();
                    Log.d(TAG, "onActivityResult: " + uri);

                    if (uri != null) {
                        try {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                getContentResolver().takePersistableUriPermission(uri,
                                        Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                            }

                            DocumentFile mainPath = HbUtils.getIntentDocumentDownloadDir(this, uri);

                            HbUtils.setSavedDownloadPathDetails(this, mainPath.getUri());

                            loadDefaultPath();





                        } catch (Exception e) {
                            e.printStackTrace();
                            new AlertDialog.Builder(DownloadLocationActivity.this)
                                    .setTitle(getString(R.string.txt_warning))
                                    .setMessage(e.getMessage())
                                    .setPositiveButton(R.string.txt_choose_another, (dialogInterface, i) -> {

                                        Intent intent;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                                            intent.putExtra("android.content.extra.SHOW_ADVANCED", true);
                                            intent.putExtra("android.content.extra.FANCY", true);
                                            intent.putExtra("android.content.extra.SHOW_FILESIZE", true);
                                            ActivityCompat.startActivityForResult(DownloadLocationActivity.this, intent, HbConst.REQUEST_CODE_SELECT_DOWNLOAD_PATH, null);
                                        }


                                    })
                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                    .create().show();
                        }
                    }

                }

            } else {
                Toast.makeText(DownloadLocationActivity.this, "Cancelled by user.", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == HbConst.REQUEST_CODE_SELECT_STORAGE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
               // binding.llSecPermissionWarning.setVisibility(View.GONE);


            } else {
                new AlertDialog.Builder(DownloadLocationActivity.this)
                        .setTitle(getString(R.string.txt_warning))
                        .setMessage(getString(R.string.txt_storage_permission_warning_desc))
                        .setPositiveButton("Enable Permission", (dialogInterface, i) -> {

                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);

                        }).create().show();

            }
        }
    }

}