package com.efortshub.holyquran.adapters;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.efortshub.holyquran.databinding.RowLanguageListItemBinding;
import com.efortshub.holyquran.interfaces.LanguageChangeListener;

import java.util.List;
import java.util.Locale;

/**
 * Created by H. Bappi on  3:46 PM 10/1/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public class LocaleLanguageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    RowLanguageListItemBinding binding;
    private final List<Locale> locales;
    private final LanguageChangeListener languageChangeListener;

    public LocaleLanguageListAdapter(List<Locale> locales, LanguageChangeListener languageChangeListener) {

        this.locales = locales;
        this.languageChangeListener = languageChangeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowLanguageListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RecyclerView.ViewHolder(binding.getRoot()) {
            @NonNull
            @Override
            public String toString() {
                return super.toString();
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Locale locale = locales.get(holder.getAdapterPosition());

        String language = locale.getLanguage();
        String country = locale.getCountry();
        String languageName = locale.getDisplayLanguage(Locale.ENGLISH);
        String countryName = locale.getDisplayCountry();
        String lc_code = language + "-" + country;

        binding.tvItemSubTitle.setText(countryName);
        binding.tvItemMainTitle.setText(languageName);
        binding.tvItemSideTextSmall.setText(lc_code);


        binding.btnRoot.setOnClickListener(view -> {
            Locale.setDefault(locale);
            Resources resources = view.getContext().getResources();
            Configuration config = resources.getConfiguration();
            config.setLocale(locale);
            resources.updateConfiguration(config, resources.getDisplayMetrics());
            languageChangeListener.onLanguageChanged(locale);
        });




    }

    @Override
    public int getItemCount() {
        return locales.size();
    }
}
