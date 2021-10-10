package com.efortshub.holyquran.models;

import android.net.Uri;

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
