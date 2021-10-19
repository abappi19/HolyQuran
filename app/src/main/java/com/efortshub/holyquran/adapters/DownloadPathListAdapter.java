package com.efortshub.holyquran.adapters;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.RecyclerView;

import com.efortshub.holyquran.R;
import com.efortshub.holyquran.databinding.RowLanguageListItemBinding;
import com.efortshub.holyquran.interfaces.DownloadPathItemClickListener;
import com.efortshub.holyquran.models.DownloadPathDetails;
import com.efortshub.holyquran.utils.HbUtils;

import java.io.File;
import java.util.List;

/**
 * Created by H. Bappi on  9:36 AM  10/15/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * github: https://github.com/hbappi
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public class DownloadPathListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<DownloadPathDetails> list;
    private final DownloadPathItemClickListener downloadPathItemClickListener;
    RowLanguageListItemBinding binding;

    public DownloadPathListAdapter(List<DownloadPathDetails> list, DownloadPathItemClickListener downloadPathItemClickListener) {
        this.list = list;
        this.downloadPathItemClickListener = downloadPathItemClickListener;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        DownloadPathDetails dd = list.get(holder.getAdapterPosition());

        if (dd.isSystemAllocated()){

            binding.tvItemMainTitle.setText(binding.tvItemMainTitle.getContext().getString(R.string.txt_system_allocated));
            File file = HbUtils.getSystemAllocatedDownloadDir(holder.itemView.getContext());

            binding.tvItemSubTitle.setText(binding.tvItemSubTitle.getContext().getString(R.string.txt_hidden_system_path));
            File[] files = file.listFiles();
            if (files != null) {
                binding.tvItemSideTextSmall.setText(files.length+" Files");
            }
        }else{

            Uri uri  = dd.getDocumentMainPathURi();
            Log.d("hhhhbb", "onBindViewHolder: "+uri);
            String filteredPath = dd.getDocumentMainPathURi().getPath().split("document/")[1];

            Log.d("hhbb", "onBindViewHolder: filtered path: "+filteredPath);

            String title = filteredPath.split(":" )[1].replace("/HolyQuran", "");

            binding.tvItemMainTitle.setText(title);

            binding.tvItemSubTitle.setText(filteredPath);
            DocumentFile df = DocumentFile.fromTreeUri(binding.tvItemSubTitle.getContext(), uri);
            DocumentFile[] dfiles = new DocumentFile[0];
            if (df != null) {
                dfiles = df.listFiles();
            }
            binding.tvItemSideTextSmall.setText(dfiles.length+" Files");



        }

        binding.btnRoot.setOnClickListener(v -> downloadPathItemClickListener.onItemClicked(dd));





    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}