package com.efortshub.holyquran.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.os.ConfigurationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.View;
import android.widget.SeekBar;

import com.efortshub.holyquran.R;
import com.efortshub.holyquran.activities.settings.AppLanguageSettingActivity;
import com.efortshub.holyquran.activities.settings.AppTranslationSettingActivity;
import com.efortshub.holyquran.adapters.ThemeListAdapter;
import com.efortshub.holyquran.databinding.ActivitySettingsBinding;
import com.efortshub.holyquran.interfaces.ThemeChangeListener;
import com.efortshub.holyquran.utils.HbUtils;
import com.google.android.material.chip.ChipGroup;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
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

        loadDropDownListener();

        loadThemeList();
        loadFonts();
        loadLanguagesAndTranslations();



    }

    private void loadDropDownListener() {

        binding.btnArrowFontArabic.setOnClickListener(view -> {
            updateShowHideMenu(binding.btnArrowFontArabic, binding.llSecFontArabicPreview, binding.ivArrowFontArabic);

        });

        binding.btnArrowFontTranslation.setOnClickListener(view -> {
            updateShowHideMenu(binding.btnArrowFontTranslation, binding.llSecFontTranslation, binding.ivArrowFontTranslation);

        });



    }

    private void updateShowHideMenu(ConstraintLayout btnClicable, LinearLayoutCompat llSection, AppCompatImageView ivArrow ) {
        if (llSection.getVisibility()==View.VISIBLE){
            llSection.setVisibility(View.GONE);
            ivArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            TypedValue typedValue = new TypedValue();
            getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
            int drawable = typedValue.resourceId;

            btnClicable.setBackground(ContextCompat.getDrawable(SettingsActivity.this, drawable));
        }else {
            llSection.setVisibility(View.VISIBLE);
            ivArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
           btnClicable.setBackground(ContextCompat.getDrawable(SettingsActivity.this, R.drawable.bg_widget_active));
        }
    }

    private void loadLanguagesAndTranslations() {
        Locale locale = ConfigurationCompat.getLocales(getResources().getConfiguration()).get(0);
        String language = locale.getDisplayLanguage();
        binding.tvLanguageSelectedLang.setText(language);


        binding.btnSelectAppLanguage.setOnClickListener(view -> startActivity(new Intent(SettingsActivity.this, AppLanguageSettingActivity.class)));

        binding.btnSelectTranslationLanguage.setOnClickListener(view -> startActivity(new Intent(SettingsActivity.this, AppTranslationSettingActivity.class)));




    }

    private void loadFonts() {

        //get arabic font details

        String fontName = HbUtils.getArabicFontName(this);
        String scriptName = HbUtils.getHbjScriptName(this);
        int fontSize = HbUtils.getArabicFontSize(this);
        String style = HbUtils.getArabicFontStyle(this);
        int fontSizeProgress = fontSize-9;
        Typeface fontTypeface = HbUtils.getArabicFont(this);
        int styleTypeface = Typeface.NORMAL;


        //style chip
        if (style.equals("normal")) {
            binding.chipArabicNormal.setChecked(true);
            styleTypeface = Typeface.NORMAL;
        } else if (style.equals("bold")) {
            binding.chipArabicBold.setChecked(true);
            styleTypeface = Typeface.BOLD;
        } else if (style.equals("italic")) {
            binding.chipArabicItalic.setChecked(true);
            styleTypeface = Typeface.ITALIC;
        }else if (style.equals("bold_italic")) {
            binding.chipArabicBoldItalic.setChecked(true);
            styleTypeface = Typeface.BOLD_ITALIC;
        }


        //script chip

        if (scriptName.equals("Imlaei")) {
            binding.chipImlaei.setChecked(true);
            binding.tvPreviewArabic.setText(R.string.txt_arabic_font_test_imlaei);
        } else if (scriptName.equals("Indopak")) {
            binding.chipIndopak.setChecked(true);
            binding.tvPreviewArabic.setText(R.string.txt_arabic_font_test_indopak);
        } else if (scriptName.equals("Uthmani")) {
            binding.chipUthmani.setChecked(true);
            binding.tvPreviewArabic.setText(R.string.txt_arabic_font_test_uthmani);
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





        binding.tvPreviewArabic.setTextSize(fontSize);
        binding.seekBarArabic.setProgress(fontSizeProgress);
        binding.btnFontSizeText.setText(String.valueOf(fontSize));

        binding.tvPreviewArabic.setTypeface(fontTypeface,styleTypeface);


        //initialize all listener

        binding.seekBarArabic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) { }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int fontSize = binding.seekBarArabic.getProgress()+10;
                HbUtils.saveArabicFontSettings(SettingsActivity.this, fontSize+"",scriptName, fontName, style );
                loadFonts();
            }
        });


        binding.chipGroupScript.setOnCheckedChangeListener((group, checkedId) -> {
            if (binding.chipImlaei.isChecked()){
                HbUtils.saveArabicFontSettings(SettingsActivity.this, fontSize+"",getString(R.string.txt_imlaei), fontName, style );
            }else    if (binding.chipIndopak.isChecked()){
                HbUtils.saveArabicFontSettings(SettingsActivity.this, fontSize+"",getString(R.string.txt_indopak), fontName, style );
            }else    if (binding.chipUthmani.isChecked()){
                HbUtils.saveArabicFontSettings(SettingsActivity.this, fontSize+"",getString(R.string.txt_uthmani), fontName, style );
            }
            loadFonts();

        });



        binding.chipGroupFontArabic.setOnCheckedChangeListener((group, checkedId) -> {
            if (binding.chipAlQalam.isChecked()){
                HbUtils.saveArabicFontSettings(SettingsActivity.this, fontSize+"",scriptName,getString(R.string.txt_al_qalam), style );
            }else    if (binding.chipOthmani.isChecked()){
                HbUtils.saveArabicFontSettings(SettingsActivity.this, fontSize+"",scriptName,getString(R.string.txt_ar_othmani), style );
            }else    if (binding.chipExcelentArabic.isChecked()){
                HbUtils.saveArabicFontSettings(SettingsActivity.this, fontSize+"",scriptName,getString(R.string.txt_excelent_arabic), style );
            }else    if (binding.chipKitab.isChecked()){
                HbUtils.saveArabicFontSettings(SettingsActivity.this, fontSize+"",scriptName,getString(R.string.txt_kitab), style );
            }else    if (binding.chipNoorehidayat.isChecked()){
                HbUtils.saveArabicFontSettings(SettingsActivity.this, fontSize+"",scriptName,getString(R.string.txt_noorehidayat), style );
            }else    if (binding.chipNoorehira.isChecked()){
                HbUtils.saveArabicFontSettings(SettingsActivity.this, fontSize+"",scriptName,getString(R.string.txt_noorehira), style );
            }else    if (binding.chipNoorehuda.isChecked()){
                HbUtils.saveArabicFontSettings(SettingsActivity.this, fontSize+"",scriptName,getString(R.string.txt_noorehuda), style );
            }
            loadFonts();

        });


        binding.chipGroupFontStyle.setOnCheckedChangeListener((group, checkedId) -> {
            if (binding.chipArabicNormal.isChecked()){
                HbUtils.saveArabicFontSettings(SettingsActivity.this, fontSize+"",scriptName,fontName, getString(R.string.txt_normal) );
            }else    if (binding.chipArabicItalic.isChecked()){
                HbUtils.saveArabicFontSettings(SettingsActivity.this, fontSize+"",scriptName,fontName, getString(R.string.txt_italic) );
            }else    if (binding.chipArabicBold.isChecked()){
                HbUtils.saveArabicFontSettings(SettingsActivity.this, fontSize+"",scriptName,fontName, getString(R.string.txt_bold) );
            }else    if (binding.chipArabicBoldItalic.isChecked()){
                HbUtils.saveArabicFontSettings(SettingsActivity.this, fontSize+"",scriptName,fontName, getString(R.string.txt_bold_italic) );
            }

            loadFonts();

        });










        //get translated font details

        String translated_fontName = HbUtils.getTranslatedFontName(this);
        int translated_fontSize = HbUtils.getTranslatedFontSize(this);
        String translated_style = HbUtils.getTranslatedFontStyle(this);
        int translated_fontSizeProgress = translated_fontSize-9;
        Typeface translated_fontTypeface = HbUtils.getTranslatedFont(this);
        int translated_styleTypeface = Typeface.NORMAL;


        //style chip
        if (translated_style.equals("normal")) {
            binding.chipTranslationNormal.setChecked(true);
            translated_styleTypeface = Typeface.NORMAL;
        } else if (translated_style.equals("bold")) {
            binding.chipTranslationBold.setChecked(true);
            translated_styleTypeface = Typeface.BOLD;
        } else if (translated_style.equals("italic")) {
            binding.chipTranslationItalic.setChecked(true);
            translated_styleTypeface = Typeface.ITALIC;
        }else if (translated_style.equals("bold_italic")) {
            binding.chipTranslationBoldItalic.setChecked(true);
            translated_styleTypeface = Typeface.BOLD_ITALIC;
        }

        //font chip

        if (translated_fontName.equals("al qalam")) {
            binding.chipTranslationAlQalam.setChecked(true);
        } else if (translated_fontName.equals("othmani")) {
            binding.chipTranslationOthmani.setChecked(true);
        } else if (translated_fontName.equals("excelent_arabic")) {
            binding.chipTranslationExcelentArabic.setChecked(true);
        } else if (translated_fontName.equals("kitab")) {
            binding.chipTranslationKitab.setChecked(true);
        } else if (translated_fontName.equals("noorehidayat")) {
            binding.chipTranslationNoorehidayat.setChecked(true);
        } else if (translated_fontName.equals("noorehira")) {
            binding.chipTranslationNoorehira.setChecked(true);
        } else if (translated_fontName.equals("noorehuda")) {
            binding.chipTranslationNoorehuda.setChecked(true);
        }




        binding.chipGroupTranslationFont.setOnCheckedChangeListener((group, checkedId) -> {
            if (binding.chipTranslationAlQalam.isChecked()){
                HbUtils.saveTranslatedFontSettings(SettingsActivity.this, translated_fontSize+"",getString(R.string.txt_al_qalam), translated_style );
            }else    if (binding.chipTranslationOthmani.isChecked()){
                HbUtils.saveTranslatedFontSettings(SettingsActivity.this, translated_fontSize+"",getString(R.string.txt_ar_othmani), translated_style );
            }else    if (binding.chipTranslationExcelentArabic.isChecked()){
                HbUtils.saveTranslatedFontSettings(SettingsActivity.this, translated_fontSize+"",getString(R.string.txt_excelent_arabic), translated_style );
            }else    if (binding.chipTranslationKitab.isChecked()){
                HbUtils.saveTranslatedFontSettings(SettingsActivity.this, translated_fontSize+"",getString(R.string.txt_kitab), translated_style );
            }else    if (binding.chipTranslationNoorehidayat.isChecked()){
                HbUtils.saveTranslatedFontSettings(SettingsActivity.this, translated_fontSize+"",getString(R.string.txt_noorehidayat), translated_style );
            }else    if (binding.chipTranslationNoorehira.isChecked()){
                HbUtils.saveTranslatedFontSettings(SettingsActivity.this, translated_fontSize+"",getString(R.string.txt_noorehira), translated_style );
            }else    if (binding.chipTranslationNoorehuda.isChecked()){
                HbUtils.saveTranslatedFontSettings(SettingsActivity.this, translated_fontSize+"",getString(R.string.txt_noorehuda), translated_style );
            }
            loadFonts();

        });


        binding.chipGroupTranslationFontStyle.setOnCheckedChangeListener((group, checkedId) -> {
            if (binding.chipTranslationNormal.isChecked()){
                HbUtils.saveTranslatedFontSettings(SettingsActivity.this, translated_fontSize+"",translated_fontName, getString(R.string.txt_normal) );
            }else    if (binding.chipTranslationItalic.isChecked()){
                HbUtils.saveTranslatedFontSettings(SettingsActivity.this, translated_fontSize+"",translated_fontName, getString(R.string.txt_italic) );
            }else    if (binding.chipTranslationBold.isChecked()){
                HbUtils.saveTranslatedFontSettings(SettingsActivity.this, translated_fontSize+"",translated_fontName, getString(R.string.txt_bold) );
            }else    if (binding.chipTranslationBoldItalic.isChecked()){
                HbUtils.saveTranslatedFontSettings(SettingsActivity.this, translated_fontSize+"",translated_fontName, getString(R.string.txt_bold_italic) );
            }

            loadFonts();

        });


        binding.tvPreviewTranslation.setTextSize(translated_fontSize);
        binding.seekBarTranslation.setProgress(translated_fontSizeProgress);
        binding.btnFontSizeTranslation.setText(String.valueOf(translated_fontSize));
        binding.tvPreviewTranslation.setTypeface(translated_fontTypeface,translated_styleTypeface);

        binding.seekBarTranslation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) { }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int translated_fontSize = binding.seekBarTranslation.getProgress()+10;
                HbUtils.saveTranslatedFontSettings(SettingsActivity.this, translated_fontSize+"", translated_fontName, translated_style );
                loadFonts();
            }
        });




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
        int Theme_HBAquaBlueDark = R.style.Theme_HBAquaBlueDark;
        int Theme_HBAquaBlueLight = R.style.Theme_HBAquaBlueLight;



        List<Integer> themeList = new ArrayList<>();

        themeList.add(HBWhiteLight);
        themeList.add(HBGreyDark);
        themeList.add(HBTwoBlackBgGreenLightVar);
        themeList.add(HBGreenDark);
        themeList.add(Theme_HBAquaBlueDark);
        themeList.add(Theme_HBAquaBlueLight);
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


        binding.rvTheme.setVisibility(View.VISIBLE);
        binding.rvTheme.setLayoutManager(new LinearLayoutManager(SettingsActivity.this, RecyclerView.HORIZONTAL, false));
        binding.rvTheme.setItemViewCacheSize(100);
        binding.rvTheme.setAdapter(adapter);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}