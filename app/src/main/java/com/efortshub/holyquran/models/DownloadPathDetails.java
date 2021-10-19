package com.efortshub.holyquran.models;

import android.net.Uri;

/**
 * Created by H. Bappi on  9:36 AM  10/15/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * github: https://github.com/hbappi
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public class DownloadPathDetails {
    private Uri documentMainPathURi;
    private boolean isSystemAllocated;
    private  int id = -1;

    public DownloadPathDetails(Uri documentMainPathURi, boolean isSystemAllocated) {
        this.documentMainPathURi = documentMainPathURi;
        this.isSystemAllocated = isSystemAllocated;
    }

    public DownloadPathDetails(Uri documentMainPathURi, boolean isSystemAllocated, int id) {

        this.documentMainPathURi = documentMainPathURi;
        this.isSystemAllocated = isSystemAllocated;
        this.id = id;
    }

    public Uri getDocumentMainPathURi() {
        return documentMainPathURi;
    }

    public boolean isSystemAllocated() {
        return isSystemAllocated;
    }

    public int getId() {
        return id;
    }
}
