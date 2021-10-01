package com.efortshub.holyquran.activities.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.ConfigurationCompat;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.efortshub.holyquran.R;
import com.efortshub.holyquran.databinding.ActivityAppLanguageSettingBinding;
import com.efortshub.holyquran.utils.HbUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class AppLanguageSettingActivity extends AppCompatActivity {
    private static final String TAG = "hhhh";

    ActivityAppLanguageSettingBinding binding;


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
        binding = ActivityAppLanguageSettingBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding.includeTitle.tvTitle.setText(getString(R.string.txt_languages_and_translation));
        binding.includeTitle.btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        setDefaultLanguage();
        loadAvailableLocaleList();



        
    }

    private void setDefaultLanguage() {

        Locale currentLocale = ConfigurationCompat.getLocales(getResources().getConfiguration()).get(0);
        String currentLanguage = currentLocale.getLanguage();
        String currentCountry = currentLocale.getCountry();
        String currentLanguageName = currentLocale.getDisplayLanguage();
        String currentCountryName = currentLocale.getDisplayLanguage();
        binding.includeLang.tvCountryName.setText(currentCountryName);
        binding.includeLang.tvLanguageName.setText(currentLanguageName);
        binding.includeLang.tvCountryLanguageCode.setText(currentLanguage+"-"+currentCountry);




    }

    private void loadAvailableLocaleList() {



        Locale[] localeList = Locale.getAvailableLocales();

        List<Locale> locales = Arrays.asList(localeList);

        for (Locale l: locales){
            Log.d(TAG, "onCreate:getCountry  "+l.getCountry());
            Log.d(TAG, "onCreate:getDisplayCountry  "+l.getDisplayCountry());
            Log.d(TAG, "onCreate:getDisplayLanguage  "+l.getDisplayLanguage());
            Log.d(TAG, "onCreate:getDisplayName  "+l.getDisplayName());
            Log.d(TAG, "onCreate: getLanguage "+l.getLanguage());


            Log.d(TAG, "onCreate: hhh\n\n\n\njjj\n\n\n\n");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}