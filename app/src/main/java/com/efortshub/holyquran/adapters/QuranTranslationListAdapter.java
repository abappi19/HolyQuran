package com.efortshub.holyquran.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.efortshub.holyquran.databinding.RowLanguageListItemBinding;
import com.efortshub.holyquran.models.QuranTranslation;

import java.util.List;

/**
 * Created by H. Bappi on  6:57 PM 10/2/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public class QuranTranslationListAdapter extends RecyclerView.Adapter{
    RowLanguageListItemBinding binding;
    private List<QuranTranslation> translationList;

    public QuranTranslationListAdapter(List<QuranTranslation> translationList) {

        this.translationList = translationList;
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

        QuranTranslation translation = translationList.get(holder.getAdapterPosition());
        binding.tvLanguageName.setText(translation.getLanguage_name());
        binding.tvCountryName.setText(translation.getName());

        binding.btnRoot.setOnClickListener(view -> {



        });


    }

    @Override
    public int getItemCount() {
        return translationList.size();
    }
}
