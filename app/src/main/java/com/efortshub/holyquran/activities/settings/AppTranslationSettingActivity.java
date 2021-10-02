package com.efortshub.holyquran.activities.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.efortshub.holyquran.R;
import com.efortshub.holyquran.adapters.QuranTranslationListAdapter;
import com.efortshub.holyquran.databinding.ActivityAppTranslationSettingBinding;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppTranslationSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        loadAvailableTranslationlist();





    } //end of onCreate.....................

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
                        name = jo.get("name")+"";
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

                    QuranTranslationListAdapter adapter = new QuranTranslationListAdapter(translationList)





                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }






    }
}