package com.efortshub.holyquran.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.efortshub.holyquran.databinding.RowLanguageListItemBinding;
import com.efortshub.holyquran.databinding.RowQuranTranslationListItemBinding;
import com.efortshub.holyquran.interfaces.TranslationChangeListener;
import com.efortshub.holyquran.models.QuranTranslation;
import com.efortshub.holyquran.utils.HbUtils;

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
    RowQuranTranslationListItemBinding binding;

    private List<QuranTranslation> translationList;
    private TranslationChangeListener translationChangeListener;

    public QuranTranslationListAdapter(List<QuranTranslation> translationList, TranslationChangeListener translationChangeListener) {

        this.translationList = translationList;
        this.translationChangeListener = translationChangeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowQuranTranslationListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

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
        binding.tvLanguageName.setAllCaps(true);
        binding.tvTranslationName.setText(translation.getName());

        binding.btnRoot.setOnClickListener(view -> {
            HbUtils.saveQuranTranslationId(view.getContext(), translation);
            translationChangeListener.onTranslationChanged(translation);

        });


    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return translationList.size();
    }
}
