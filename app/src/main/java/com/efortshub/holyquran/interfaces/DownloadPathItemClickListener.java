package com.efortshub.holyquran.interfaces;

import com.efortshub.holyquran.models.DownloadPathDetails;

/**
 * Created by H. Bappi on  9:36 AM  10/15/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * github: https://github.com/hbappi
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public interface DownloadPathItemClickListener {
    void onItemClicked(DownloadPathDetails downloadPathDetails);
    void onItemDeleteRequest(DownloadPathDetails downloadPathDetails);
    void onItemSetCurrentPathRequest(DownloadPathDetails downloadPathDetails);
}
