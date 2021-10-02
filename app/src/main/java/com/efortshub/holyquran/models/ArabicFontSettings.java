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
public class ArabicFontSettings implements Serializable {
    private String fontSize;
    private String fontScriptName;
    private String fontName;
    private String style;

    public ArabicFontSettings(String fontSize, String fontScriptName, String fontName, String style) {
        this.fontSize = fontSize;
        this.fontScriptName = fontScriptName;
        this.fontName = fontName;
        this.style = style;
    }

    public int getFontSize() {
        try {
            return Integer.parseInt(fontSize);
        }catch (Exception e){
            e.printStackTrace();
            return HbConst.DEFAULT_ARABIC_FONT_SIZE;
        }
    }

    public String getFontScriptName() {
        return fontScriptName;
    }

    public String getFontName() {
        return fontName;
    }

    public String getStyle() {
        return style;
    }
}
