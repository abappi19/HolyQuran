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
    private String author_name;
    private String slug;
    private String language_name;
    private String translated_name;
    private String translated_language_name;

    public QuranTranslation(String id, String name, String author_name, String slug, String language_name, String translated_name, String translated_language_name) {

        this.id = id;
        this.name = name;
        this.author_name = author_name;
        this.slug = slug;
        this.language_name = language_name;
        this.translated_name = translated_name;
        this.translated_language_name = translated_language_name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public String getSlug() {
        return slug;
    }

    public String getLanguage_name() {
        return language_name;
    }

    public String getTranslated_name() {
        return translated_name;
    }

    public String getTranslated_language_name() {
        return translated_language_name;
    }
}
