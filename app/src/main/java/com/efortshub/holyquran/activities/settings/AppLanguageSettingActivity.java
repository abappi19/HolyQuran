package com.efortshub.holyquran.activities.settings;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.os.ConfigurationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.efortshub.holyquran.R;
import com.efortshub.holyquran.activities.SplashActivity;
import com.efortshub.holyquran.adapters.LocaleLanguageListAdapter;
import com.efortshub.holyquran.databinding.ActivityAppLanguageSettingBinding;
import com.efortshub.holyquran.interfaces.LanguageChangeListener;
import com.efortshub.holyquran.utils.HbUtils;

import java.util.ArrayList;
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
        }else {
            loadAvailableLocaleList(binding.searchBar.getQuery().toString());
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        oldTheme = HbUtils.getSavedTheme(this);
        setTheme(oldTheme);
        binding = ActivityAppLanguageSettingBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding.includeTitle.tvTitle.setText(getString(R.string.txt_app_language));
        binding.includeTitle.btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        setDefaultLanguage(null);
        loadAvailableLocaleList("");
        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadAvailableLocaleList(newText);
                return true;
            }
        });



        
    }

    private void setDefaultLanguage(@Nullable Locale locale) {

        Locale currentLocale = ConfigurationCompat.getLocales(getResources().getConfiguration()).get(0);

        binding.tvRestartRequest.setVisibility(View.GONE);
        binding.btnRestart.setVisibility(View.GONE);

        if (locale!=null) {
            currentLocale = locale;
            binding.tvRestartRequest.setVisibility(View.VISIBLE);
            binding.btnRestart.setVisibility(View.VISIBLE);
            binding.btnRestart.setOnClickListener(view -> {
                HbUtils.RequiredOpenSettings(view.getContext(), true);
                Context context = view.getContext();
                Intent intent = new Intent(context, SplashActivity.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                finishAffinity();
            });

        }

        String currentLanguage = currentLocale.getLanguage();
        String currentCountry = currentLocale.getCountry();
        String currentLanguageName = currentLocale.getDisplayLanguage();
        String currentCountryName = currentLocale.getDisplayCountry();
        binding.includeLang.tvCountryName.setText(currentCountryName);
        binding.includeLang.tvLanguageName.setText(currentLanguageName);
        binding.includeLang.tvCountryLanguageCode.setText(currentLanguage+"-"+currentCountry);




    }

    private void loadAvailableLocaleList(String query) {
        Locale[] localeList = Locale.getAvailableLocales();
        List<Locale> locales = Arrays.asList(localeList);

        List<Locale> filteredLocales = new ArrayList<>();

        if (query.trim().isEmpty()){
            filteredLocales = locales;
        }else {
            for (Locale locale : locales) {
                if (locale.getDisplayLanguage().toLowerCase(Locale.ROOT).trim().startsWith(query.toLowerCase(Locale.ROOT).trim())
                        || locale.getDisplayCountry().toLowerCase(Locale.ROOT).trim().startsWith(query.toLowerCase(Locale.ROOT).trim())
                        || locale.getCountry().toLowerCase(Locale.ROOT).trim().startsWith(query.toLowerCase(Locale.ROOT).trim())
                        || locale.getLanguage().toLowerCase(Locale.ROOT).trim().startsWith(query.toLowerCase(Locale.ROOT).trim())
                ) {
                    filteredLocales.add(locale);
                }


            }
        }

        LocaleLanguageListAdapter adapter = new LocaleLanguageListAdapter(filteredLocales, new LanguageChangeListener(){
            @Override
            public void onLanguageChanged(Locale locale) {
                setDefaultLanguage(locale);
            }
        });
        binding.rvLocales.setLayoutManager(new LinearLayoutManager(AppLanguageSettingActivity.this, RecyclerView.VERTICAL, false));
        binding.rvLocales.setItemViewCacheSize(150);
        binding.rvLocales.setAdapter(adapter);






    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}