package com.efortshub.holyquran.adapters;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.efortshub.holyquran.R;
import com.efortshub.holyquran.databinding.RowThemeItemBinding;
import com.efortshub.holyquran.interfaces.ThemeChangeListener;

import java.util.List;

/**
 * Created by H. Bappi on  10:53 AM 9/29/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public class ThemeListAdapter extends RecyclerView.Adapter {
    RowThemeItemBinding binding;
    private List<Integer> themeList;
    private Resources.Theme currentTheme;
    private ThemeChangeListener themeChangeListener;

    public ThemeListAdapter(List<Integer> themeList, Resources.Theme currentTheme, ThemeChangeListener themeChangeListener) {

        this.themeList = themeList;
        this.currentTheme = currentTheme;
        this.themeChangeListener = themeChangeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowThemeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RecyclerView.ViewHolder(binding.getRoot()) {
            @Override
            public String toString() {
                return super.toString();
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int p) {
        int position = holder.getAdapterPosition();
        int theme = themeList.get(position);

        Resources.Theme myTheme = new ContextThemeWrapper(binding.getRoot().getContext(), theme).getTheme();

        TypedValue typeColorPrimary = new TypedValue();
        myTheme.resolveAttribute(R.attr.colorPrimary, typeColorPrimary, true);
        int colorPrimary = typeColorPrimary.data;

        TypedValue typeColorPrimaryVariant = new TypedValue();
        myTheme.resolveAttribute(R.attr.colorPrimaryVariant, typeColorPrimaryVariant, true);
        int colorPrimaryVariant = typeColorPrimaryVariant.data;


        try{
            binding.themeBg.setBackgroundColor(colorPrimaryVariant);
            binding.themeBtn.setBackgroundColor(colorPrimary);
            binding.themeImg.setColorFilter(colorPrimary);
            binding.themeTv.setTextColor(colorPrimary);
            binding.themeTv.setBackgroundColor(colorPrimaryVariant);
            binding.themeSwitch.setBackgroundColor(colorPrimaryVariant);
            binding.themeBtn.setTextColor(colorPrimaryVariant);
            binding.cv.setCardBackgroundColor(colorPrimaryVariant);
            binding.themeImg.setBackgroundColor(colorPrimaryVariant);

        }catch (Exception e){
            e.printStackTrace();
        }
  /*      try{
            DrawableCompat.setTintList(
                    binding.themeSwitch.getThumbDrawable(),
                    new ColorStateList(
                            new int[][]{
                                    new int[]{android.R.attr.state_checked},
                                    new int[]{android.R.attr.state_active}},
                            new int[]{
                                    colorPrimary,
                                    Color.GRAY
                            }));


        }catch (Exception e){
            e.printStackTrace();
        }*/
        binding.btnSetNow.setOnClickListener(view -> {
            themeChangeListener.onThemeSelected(theme, myTheme);
        });


    }

    @Override
    public int getItemCount() {
        return themeList.size();
    }
}
