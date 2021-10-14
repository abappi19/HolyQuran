package com.efortshub.holyquran.utils.download_helper;

import java.util.ArrayList;
import java.util.List;

public class HbDownloadQue {
    List<String> queList;

    public HbDownloadQue(){
        queList = new ArrayList<>();
    }

    public void add(String url){
        queList.add(url);
    }
    public String enQue(){
        if (queList.size()>0){

            String url = queList.get(0);

            queList.remove(0);

            return url;
        }
        else return null;
    }
    public boolean isExist(String url) {
        boolean b = false;
        for (String s: queList){
            if (s.equals(url)){
                b = true;
                break;
            }
        }
        
        return b;
    }
}
