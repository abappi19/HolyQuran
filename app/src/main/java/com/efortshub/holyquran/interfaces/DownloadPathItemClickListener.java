package com.efortshub.holyquran.interfaces;

import com.efortshub.holyquran.models.DownloadPathDetails;

public interface DownloadPathItemClickListener {
    void onItemClicked(DownloadPathDetails downloadPathDetails);
    void onItemDeleteRequest(DownloadPathDetails downloadPathDetails);
    void onItemSetCurrentPathRequest(DownloadPathDetails downloadPathDetails);
}
