package com.efortshub.holyquran.interfaces;

import com.efortshub.holyquran.utils.download_helper.HbDownloadQue;

/**
 * Created by H. Bappi on  8:18 AM PM 10/15/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * github: https://github.com/hbappi
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public interface DownloadFileListener {

    void onDownloadStarted(HbDownloadQue.Item downloadingItem);
    void onDownloadFinished(HbDownloadQue.Item downloadingItem);
    void onDownloadProgress(HbDownloadQue.Item downloadingItem, int progress);
    void onDownloadFailed(Exception e, boolean isDownloadAlreadyAdded);

}
