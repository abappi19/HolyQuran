package com.efortshub.holyquran.adapters;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.efortshub.holyquran.R;
import com.efortshub.holyquran.activities.settings.DownloadLocationActivity;
import com.efortshub.holyquran.databinding.DialogDownloadConfirmationBinding;
import com.efortshub.holyquran.databinding.RowQuranTranslationListItemBinding;
import com.efortshub.holyquran.interfaces.TranslationChangeListener;
import com.efortshub.holyquran.models.DownloadPathDetails;
import com.efortshub.holyquran.models.QuranTranslation;
import com.efortshub.holyquran.utils.HbUtils;
import com.efortshub.holyquran.utils.download_helper.HbDownloadUtils;

import java.util.List;

/**
 * Created by H. Bappi on  6:57 PM 10/2/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public class QuranTranslationListAdapter extends Adapter<RecyclerView.ViewHolder> {
    RowQuranTranslationListItemBinding binding;

    private final List<QuranTranslation> translationList;
    private final TranslationChangeListener translationChangeListener;

    public QuranTranslationListAdapter(List<QuranTranslation> translationList, TranslationChangeListener translationChangeListener) {

        this.translationList = translationList;
        this.translationChangeListener = translationChangeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowQuranTranslationListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

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

        QuranTranslation translation = translationList.get(holder.getAdapterPosition());
        binding.tvItemMainTitle.setText(translation.getLanguage_name());
        binding.tvItemMainTitle.setAllCaps(true);
        binding.tvTranslationName.setText(translation.getName());

        if (translation.isDownloaded()){
            binding.ivDownloadStatus.setImageResource(R.drawable.ic_baseline_done_all_24);

            binding.btnRoot.setOnClickListener(view -> {
                AlertDialog alertDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(view.getContext().getResources().getString(R.string.txt_app_translation));
                builder.setMessage(R.string.txt_select_translation_slot_desc);
                builder.setPositiveButton(R.string.txt_secondary, (dialogInterface, i) -> {
                    HbUtils.saveSecondaryQuranTranslationId(view.getContext(), translation);
                    translationChangeListener.onTranslationChanged(2,translation);

                });
                builder.setNegativeButton(R.string.txt_primary, (dialogInterface, i) -> {
                    HbUtils.savePrimaryQuranTranslationId(view.getContext(), translation);
                    translationChangeListener.onTranslationChanged(1,translation);

                });

                alertDialog = builder.create();

                alertDialog.show();

            });

        }else {
            binding.btnRoot.setOnClickListener(view -> {

                AlertDialog alertDialog;

                DialogDownloadConfirmationBinding db = DialogDownloadConfirmationBinding.inflate(LayoutInflater.from(view.getContext())
                , null, false);

                alertDialog = new AlertDialog.Builder(view.getContext())
                        .setView(db.getRoot())
                        .create();


                db.tvTranslationName.setText(translation.getName());
                db.tvItemMainTitle.setText(translation.getLanguage_name());



                DownloadPathDetails dd = HbUtils.getSavedDownloadPathDetails(view.getContext());

                if (dd.isSystemAllocated()){

                    db.tvDownloadPath.setText(view.getContext().getString(R.string.txt_hidden_system_path));

                }else{

                    Uri uri  = dd.getDocumentMainPathURi();
                    Log.d("hhhhbb", "onBindViewHolder: "+uri);
                    String filteredPath = dd.getDocumentMainPathURi().getPath().split("document/")[1];

                    Log.d("hhbb", "onBindViewHolder: filtered path: "+filteredPath);

                    db.tvDownloadPath.setText(filteredPath);


                }

                db.btnChangeDownloadPath.setOnClickListener(v->{
                    v.getContext().startActivity(new Intent(v.getContext(), DownloadLocationActivity.class));
                    if (alertDialog!=null)alertDialog.dismiss();

                });

                db.btnDownloadTrans.setOnClickListener(v -> {
                    Log.d("hhbbh", "onBindViewHolder: download starting...");
                    HbDownloadUtils.getInstance()
                            .startDownload("a", null);


                });

                db.btnCancelDownload.setOnClickListener(v -> {

                    //todo: test///////
                    Log.d("hhbbh", "onBindViewHolder: que from outside: ");

                    for (String s: HbDownloadUtils.que){
                        Log.d("hhbbh", "onBindViewHolder: ques outside : "+s);
                    }


                    if (alertDialog != null) {
                        alertDialog.dismiss();

                    }
                });
              alertDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.transparent));
              alertDialog.show();











                /*
                String fileStr = view.getContext().getExternalFilesDir(view.getContext().getFilesDir().getAbsolutePath()).getAbsolutePath()+"/hbj/"+translation.getId()+".json";

                File file = new File(fileStr);
                try {
                    if (!file.exists()){
                        Log.d(TAG, "onBindViewHolder: file dees not  exist");
                        file.createNewFile();
                        FileOutputStream fo = new FileOutputStream(file);
                        fo.flush();
                        fo.close();
                    }
                    FileOutputStream fo = new FileOutputStream(file);

                    fo.flush();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fo);
                    outputStreamWriter.write("hi, how are you");
                    outputStreamWriter.close();
                    fo.close();




                    FileOutputStream fout = new FileOutputStream(file);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fout);

                    outputStreamWriter.write("hi, how are you");
                    outputStreamWriter.close();

                    Log.d(TAG, "onBindViewHolder: success");


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "onBindViewHolder: "+e.getMessage());
                }*/

            });
        }




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
