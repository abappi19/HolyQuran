package com.efortshub.holyquran.interfaces;

public interface DownloadFileListener {
    void onDownloadStarted();
    void onDownloadFinished();
    void onDownloadProgress();
    void onDownloadFailed(Exception e, boolean isDownloadAlreadyAdded);

}
