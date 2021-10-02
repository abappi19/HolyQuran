package com.efortshub.holyquran.models;

/**
 * Created by H. Bappi on  5:23 PM 10/2/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public class QuranTranslation {
    private String id;
    private String name;
    private String language_name;
    private boolean downloaded = false;

    public QuranTranslation(String id, String name,String language_name) {

        this.id = id;
        this.name = name;
        this.language_name = language_name;
    }

    public QuranTranslation(String id, String name,String language_name, boolean downloaded) {

        this.id = id;
        this.name = name;
        this.language_name = language_name;
        this.downloaded = downloaded;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLanguage_name() {
        return language_name;
    }


    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }

    public boolean isDownloaded() {
        return downloaded;
    }
}
