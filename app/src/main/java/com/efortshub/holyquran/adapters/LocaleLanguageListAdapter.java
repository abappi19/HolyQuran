package com.efortshub.holyquran.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.efortshub.holyquran.databinding.RowLanguageListItemBinding;

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
public class LocaleLanguageListAdapter extends RecyclerView.Adapter {

    RowLanguageListItemBinding binding;
    private List<Locale> locales;

    public LocaleLanguageListAdapter(List<Locale> locales) {

        this.locales = locales;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowLanguageListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RecyclerView.ViewHolder(binding.getRoot()) {
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
        String languageName = locale.getDisplayLanguage();
        String countryName = locale.getDisplayCountry();

        binding.tvCountryName.setText(countryName);
        binding.tvLanguageName.setText(languageName);
        binding.tvCountryLanguageCode.setText(language+"-"+country);




    }

    @Override
    public int getItemCount() {
        return locales.size();
    }
}
