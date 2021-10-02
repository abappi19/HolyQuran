package com.efortshub.holyquran.activities.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
        binding.includePrimaryTranslation.tvLanguageName.setText(quranTranslation.getLanguage_name());
        binding.includePrimaryTranslation.tvLanguageName.setAllCaps(true);
        binding.includePrimaryTranslation.tvTranslationName.setText(quranTranslation.getName());
        binding.includePrimaryTranslation.ivDownloadStatus.setImageResource(R.drawable.ic_baseline_check_box_24);

        QuranTranslation secondQuranTranslation = HbUtils.getQuranTranslationIdSecondary(this);
        binding.includeSecondaryTranslation.tvLanguageName.setText(secondQuranTranslation.getLanguage_name());
        binding.includeSecondaryTranslation.tvLanguageName.setAllCaps(true);
        binding.includeSecondaryTranslation.tvTranslationName.setText(secondQuranTranslation.getName());
        binding.includeSecondaryTranslation.ivDownloadStatus.setImageResource(R.drawable.ic_baseline_check_box_outline_blank_24);




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
                            quranTranslation -> {
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