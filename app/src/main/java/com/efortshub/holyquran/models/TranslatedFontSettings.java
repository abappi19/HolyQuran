package com.efortshub.holyquran.models;

import com.efortshub.holyquran.utils.HbConst;

import java.io.Serializable;

/**
 * Created by H. Bappi on  6:33 AM 9/30/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public class TranslatedFontSettings implements Serializable {
    private String fontSize;
    private String fontName;
    private String style;

    public TranslatedFontSettings(String fontSize, String fontName, String style) {
        this.fontSize = fontSize;
        this.fontName = fontName;
        this.style = style;
    }

    public int getFontSize() {
        try {
            return Integer.parseInt(fontSize);
        }catch (Exception e){
            e.printStackTrace();
            return HbConst.ARABIC_FONT_DEFAULT_SIZE;
        }
    }


    public String getFontName() {
        return fontName;
    }

    public String getStyle() {
        return style;
    }
}
