package com.example.myfirstapp;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class PhotoFileModel {
    public File file;
    public String path;

    public String[] getAttributes(){
        return path.split("_");
    }

    //This can be replaced with the creational design pattern
    public static ArrayList<String> findPhotos(Date startTimestamp, Date endTimestamp, String keywords, String location) {
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), "/Android/data/com.example.myfirstapp/files/Pictures");
        ArrayList<String> photos = new ArrayList<String>();
        File[] fList = file.listFiles();
        if (fList != null) {
            for (File f : fList) {
                if (((startTimestamp == null && endTimestamp == null) || (f.lastModified() >= startTimestamp.getTime()
                        && f.lastModified() <= endTimestamp.getTime())
                ) && (keywords == "" || f.getPath().contains(keywords))&& (location == "" || f.getPath().contains(location)))
                    photos.add(f.getPath());
            }
        }
        return photos;
    }
}
