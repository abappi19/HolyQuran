package com.efortshub.holyquran.adapters;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.efortshub.holyquran.R;
import com.efortshub.holyquran.databinding.RowLanguageListItemBinding;
import com.efortshub.holyquran.interfaces.DownloadPathItemClickListener;
import com.efortshub.holyquran.models.DownloadPathDetails;

import java.util.List;

public class DownloadPathListAdapter extends RecyclerView.Adapter {

    private List<DownloadPathDetails> list;
    private DownloadPathItemClickListener downloadPathItemClickListener;
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
            @Override
            public String toString() {
                return super.toString();
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        DownloadPathDetails dd = list.get(holder.getAdapterPosition());
        Uri uri  = dd.getDocumentMainPathURi();
        Log.d("hhhhbb", "onBindViewHolder: "+uri);
        String filteredPath = dd.getDocumentMainPathURi().getPath().split("document/")[1];

        Log.d("hhbb", "onBindViewHolder: filtered path: "+filteredPath);

        String title = filteredPath.split(":" )[1].replace("/HolyQuran", "");

        binding.tvItemMainTitle.setText(title);

        binding.tvItemSubTitle.setText(filteredPath);

        binding.btnRoot.setOnClickListener(v -> {
            downloadPathItemClickListener.onItemClicked(dd);
        });





    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}