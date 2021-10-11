package com.efortshub.holyquran.activities.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.efortshub.holyquran.R;
import com.efortshub.holyquran.adapters.QuranTranslationListAdapter;
import com.efortshub.holyquran.databinding.ActivityAppTranslationSettingBinding;
import com.efortshub.holyquran.interfaces.TranslationChangeListener;
import com.efortshub.holyquran.models.QuranTranslation;
import com.efortshub.holyquran.utils.HbConst;
import com.efortshub.holyquran.utils.HbUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class AppTranslationSettingActivity extends AppCompatActivity {
    ActivityAppTranslationSettingBinding binding;
    private static final String TAG = "hhhh";


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
        binding = ActivityAppTranslationSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.includeTitle.tvTitle.setText(getString(R.string.txt_app_translation));
        binding.includeTitle.btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });





        loadCurrentLanguageIds();
        loadAvailableTranslationlist();





    } //end of onCreate.....................

    private void loadCurrentLanguageIds() {
        QuranTranslation quranTranslation = HbUtils.getQuranTranslationIdPrimary(this);
        binding.includePrimaryTranslation.tvItemMainTitle.setText(quranTranslation.getLanguage_name());
        binding.includePrimaryTranslation.tvItemMainTitle.setAllCaps(true);
        binding.includePrimaryTranslation.tvTranslationName.setText(quranTranslation.getName());
        binding.includePrimaryTranslation.ivDownloadStatus.setImageResource(R.drawable.ic_baseline_check_box_24);

        QuranTranslation secondQuranTranslation = HbUtils.getQuranTranslationIdSecondary(this);
        binding.includeSecondaryTranslation.tvItemMainTitle.setText(secondQuranTranslation.getLanguage_name());
        binding.includeSecondaryTranslation.tvItemMainTitle.setAllCaps(true);
        binding.includeSecondaryTranslation.tvTranslationName.setText(secondQuranTranslation.getName());
        binding.includeSecondaryTranslation.ivDownloadStatus.setImageResource(R.drawable.ic_baseline_check_box_outline_blank_24);

        boolean primaryTrnsVisibility = HbUtils.getQuranTranslationVisibilityPrimary(this);
        boolean secondaryTrnsVisibility = HbUtils.getQuranTranslationVisibilitySecondary(this);

        if (primaryTrnsVisibility){
            binding.includePrimaryTranslation.ivDownloadStatus.setImageResource(R.drawable.ic_baseline_check_box_24);
        }else {
            binding.includePrimaryTranslation.ivDownloadStatus.setImageResource(R.drawable.ic_baseline_check_box_outline_blank_24);

        }

        if (secondaryTrnsVisibility){
            binding.includeSecondaryTranslation.ivDownloadStatus.setImageResource(R.drawable.ic_baseline_check_box_24);
        }else {
            binding.includeSecondaryTranslation.ivDownloadStatus.setImageResource(R.drawable.ic_baseline_check_box_outline_blank_24);
        }

        binding.includePrimaryTranslation.btnRoot.setOnClickListener(view -> {
            HbUtils.setQuranTranslationVisibilityPrimary(this, !primaryTrnsVisibility);
            loadCurrentLanguageIds();
        });

        binding.includeSecondaryTranslation.btnRoot.setOnClickListener(view -> {
            HbUtils.setQuranTranslationVisibilitySecondary(this, !secondaryTrnsVisibility);
            loadCurrentLanguageIds();
        });





    }

    private void loadAvailableTranslationlist() {

        JSONObject jsonObject = HbUtils.getTranslationListHbj(this);

        if (jsonObject==null){

        }else {

            List<QuranTranslation> translationList = new ArrayList<>();

            try {
                JSONArray transArray = jsonObject.getJSONArray("translations");

                if (transArray.length()>0){

                    for (int i =0; i<transArray.length(); i++){
                        JSONObject jo = (JSONObject) transArray.get(i);
                        String id, name, author_name, slug,  language_name, translated_name, translated_language;

                        id = jo.get("id")+"";
                        name = jo.get("author_name")+"";
                        if (name.equals("null")) name = jo.get("name")+"";
                        language_name = jo.get("language_name")+"";

                        QuranTranslation quranTranslation = new QuranTranslation(
                                id,
                                name,
                                language_name);

                        translationList.add(quranTranslation);


                        Log.d(TAG, "loadAvailableTranslationlist: "+id
                                +"\n"+name
                                +"\n"+language_name
                                );


                    }

                    List<QuranTranslation> downloadFilteredTranslationList = new ArrayList<>();

                    for (QuranTranslation qt: translationList){
                        if (qt.getId().equals(HbConst.DEFAULT_ARABIC_PRIMARY_TRANSLATION_LANGUAGE_ID)
                        || qt.getId().equals(HbConst.DEFAULT_ARABIC_SECONDARY_TRANSLATION_LANGUAGE_ID)){
                            qt.setDownloaded(true);
                            downloadFilteredTranslationList.add(qt);
                        }
                    }

                    for (QuranTranslation qt: translationList){
                        if (!downloadFilteredTranslationList.contains(qt)){
                            downloadFilteredTranslationList.add(qt);
                        }
                    }

                    QuranTranslationListAdapter adapter = new QuranTranslationListAdapter(downloadFilteredTranslationList,
                            (index,quranTranslation) -> {
                        if (index==1){
                            TypedValue typedValue = new TypedValue();
                            getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
                            int drawable = typedValue.resourceId;
                            binding.includePrimaryTranslation.btnRoot.setBackground(ContextCompat.getDrawable(AppTranslationSettingActivity.this, R.drawable.bg_widget_active));
                            new Handler(Looper.getMainLooper())
                                    .postDelayed(() -> binding.includePrimaryTranslation.btnRoot.setBackground(ContextCompat.getDrawable(AppTranslationSettingActivity.this, drawable)), 2000);
                        }else  if (index==2){
                            TypedValue typedValue = new TypedValue();
                            getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
                            int drawable = typedValue.resourceId;
                            binding.includeSecondaryTranslation.btnRoot.setBackground(ContextCompat.getDrawable(AppTranslationSettingActivity.this, R.drawable.bg_widget_active));
                            new Handler(Looper.getMainLooper())
                                    .postDelayed(() -> binding.includeSecondaryTranslation.btnRoot.setBackground(ContextCompat.getDrawable(AppTranslationSettingActivity.this, drawable)), 2000);
                        }
                        loadCurrentLanguageIds();
                            });


                    binding.rvTranslations.setLayoutManager(new LinearLayoutManager(AppTranslationSettingActivity.this, RecyclerView.VERTICAL, false));
                    binding.rvTranslations.setItemViewCacheSize(150);
                    binding.rvTranslations.setAdapter(adapter);


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }






    }
}