package com.efortshub.holyquran.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.efortshub.holyquran.R;
import com.efortshub.holyquran.adapters.ThemeListAdapter;
import com.efortshub.holyquran.databinding.ActivitySettingsBinding;
import com.efortshub.holyquran.interfaces.ThemeChangeListener;
import com.efortshub.holyquran.utils.HbUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding binding;


    int oldTheme = R.style.Theme_HBWhiteLight;
    @Override
    protected void onResume() {
        super.onResume();
        if (oldTheme!=HbUtils.getSavedTheme(this)){
            recreate();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        oldTheme = HbUtils.getSavedTheme(this);
        setTheme(oldTheme);
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.inclueTitle.tvTitle.setText("Settings");
        binding.inclueTitle.btnGoBack.setOnClickListener(view -> onBackPressed());


        loadThemeList();

        loadFonts();









    }

    private void loadFonts() {

        String fontName = HbUtils.getArabicFontName(this);
        String scriptUrl = HbUtils.getHbjScriptUrl(this);
        String scriptName = HbUtils.getHbjScriptName(this);
        int fontSize = HbUtils.getArabicFontSize(this);
        String style = HbUtils.getArabicFontStyle(this);
        int fontSizeProgress = fontSize-9;


        binding.tvPreviewArabic.setTextSize(fontSize);
        binding.seekBarArabic.setProgress(fontSizeProgress);
        binding.btnFontSizeText.setText(String.valueOf(fontSize));

        binding.tvPreviewArabic.setTypeface(HbUtils.getArabicFont(this));


        //style chip

        if (style.equals("normal")) {
            binding.chipArabicNormal.setChecked(true);
        } else if (style.equals("bold")) {
            binding.chipArabicBold.setChecked(true);
        } else if (style.equals("italic")) {
            binding.chipArabicItalic.setChecked(true);
        }


        //script chip

        if (scriptName.equals("Imlaei")) {
            binding.chipImlaei.setChecked(true);
        } else if (scriptName.equals("Indpak")) {
            binding.chipIndopak.setChecked(true);
        } else if (scriptName.equals("Uthmani")) {
            binding.chipUthmani.setChecked(true);
        } else if (scriptName.trim().equals("UthmaniSimple")) {
            binding.chipUthmaniSimple.setChecked(true);
        }

        //font chip

        if (fontName.equals("al qalam")) {
            binding.chipAlQalam.setChecked(true);
        } else if (fontName.equals("othmani")) {
            binding.chipOthmani.setChecked(true);
        } else if (fontName.equals("excelent_arabic")) {
            binding.chipExcelentArabic.setChecked(true);
        } else if (fontName.equals("kitab")) {
            binding.chipKitab.setChecked(true);
        } else if (fontName.equals("noorehidayat")) {
            binding.chipNoorehidayat.setChecked(true);
        } else if (fontName.equals("noorehira")) {
            binding.chipNoorehira.setChecked(true);
        } else if (fontName.equals("noorehuda")) {
            binding.chipNoorehuda.setChecked(true);
        }



    }

    private void loadThemeList() {
        int HBOrangeDark = R.style.Theme_HBOrangeDark;
        int HBOrangeLight = R.style. Theme_HBOrangeLight;
        int HBBlueDark = R.style.Theme_HBBlueDark;
        int HBBlueLight = R.style. Theme_HBBlueLight;
        int HBGreyDark = R.style.Theme_HBGreyDark;
        int HBGreyLight = R.style. Theme_HBGreyLight;
        int HBWhiteDark = R.style.Theme_HBWhiteDark;
        int HBWhiteLight = R.style. Theme_HBWhiteLight;
        int HBGreenDark = R.style.Theme_HBGreenDark;
        int HBGreenLight = R.style. Theme_HBGreenLight;
        int HBTwoBlackBgGreenLightVar = R.style.Theme_HBTwoBlackBgGreenLightVar;
        int HBTwoGreyBgGreenLightVar = R.style. Theme_HBTwoGreyBgGreenLightVar;
        int HBTwoBlackBgOrangeLIghtVar = R.style.Theme_HBTwoBlackBgOrangeLIghtVar;
        int HBTwoGreyBgOrangeLIghtVar = R.style. Theme_HBTwoGreyBgOrangeLIghtVar;
        int HBTwoWhiteBgGreenDarkVAr = R.style.Theme_HBTwoWhiteBgGreenDarkVAr;
        int HBMidBlueLight = R.style. Theme_HBMidBlueLight;
        int HBMidBlueDark = R.style.Theme_HBMidBlueDark;
        int HBLowBlueLight = R.style. Theme_HBLowBlueLight;
        int HBLowBlueDark = R.style.Theme_HBLowBlueDark;
        int HBLowBlueLightWhite = R.style. Theme_HBLowBlueLightWhite;
        int HBLowBlueDarkBlack = R.style.Theme_HBLowBlueDarkBlack;
        int HBLowBlueLightGrey = R.style. Theme_HBLowBlueLightGrey;
        int HBLowBlueDarkGrey = R.style.Theme_HBLowBlueDarkGrey;
        int HBMidOrangeLight = R.style. Theme_HBMidOrangeLight;
        int HBMidOrangeDark = R.style.Theme_HBMidOrangeDark;
        int HBLowOrangeLight = R.style. Theme_HBOrangeLight;
        int HBLowOrangeDark = R.style.Theme_HBOrangeDark;
        int HBLowOrangeLightWhite = R.style.Theme_HBLowOrangeLightWhite;
        int HBLowOrangeDarkBlack = R.style.Theme_HBLowOrangeDarkBlack;
        int HBLowOrangeLightGrey = R.style.Theme_HBLowOrangeLightGrey;
        int HBLowOrangeDarkGrey = R.style.Theme_HBLowOrangeDarkGrey;



        List<Integer> themeList = new ArrayList<>();

        themeList.add(HBWhiteLight);
        themeList.add(HBGreyDark);
        themeList.add(HBTwoBlackBgGreenLightVar);
        themeList.add(HBGreenDark);
        themeList.add(HBMidBlueDark);
        themeList.add(HBOrangeDark);
        themeList.add(HBLowBlueDarkGrey);
        themeList.add(HBLowOrangeLight);
        themeList.add(HBTwoWhiteBgGreenDarkVAr);
        themeList.add(HBGreyLight);
        themeList.add(HBLowOrangeDarkBlack);
        themeList.add(HBTwoGreyBgGreenLightVar);
        themeList.add(HBMidBlueLight);
        themeList.add(HBLowBlueDarkBlack);
        themeList.add(HBWhiteDark);
        themeList.add(HBGreenLight);
        themeList.add(HBLowBlueLightWhite);
        themeList.add(HBLowBlueLight);
        themeList.add(HBLowBlueDark);
        themeList.add(HBLowBlueLightGrey);
        themeList.add(HBLowOrangeLightWhite);
        themeList.add(HBOrangeLight);
        themeList.add(HBBlueDark);
        themeList.add(HBMidOrangeDark);
        themeList.add(HBBlueLight);
        themeList.add(HBTwoGreyBgOrangeLIghtVar);
        themeList.add(HBTwoBlackBgOrangeLIghtVar);
        themeList.add(HBLowOrangeDark);
        themeList.add(HBMidOrangeLight);
        themeList.add(HBLowOrangeLightGrey);
        themeList.add(HBLowOrangeDarkGrey);

        ThemeListAdapter adapter = new ThemeListAdapter(themeList, getTheme(), new ThemeChangeListener() {
            @Override
            public void onThemeSelected(int themeId, Resources.Theme theme) {
                HbUtils.saveTheme(SettingsActivity.this, themeId);
                recreate();
            }

            @Override
            public void scrollToSelectedPosition(int position) {
                    binding.rvTheme.post(() -> binding.rvTheme.smoothScrollToPosition(position));
            }
        });

        binding.rvTheme.setLayoutManager(new LinearLayoutManager(SettingsActivity.this, RecyclerView.HORIZONTAL, false));
        binding.rvTheme.setItemViewCacheSize(100);
        binding.rvTheme.setAdapter(adapter);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}