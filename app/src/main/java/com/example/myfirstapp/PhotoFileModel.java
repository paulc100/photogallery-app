package com.example.myfirstapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Stream;

public class PhotoFileModel {
    public String path;

    public String[] getAttributes(){
        return path.split("_");
    }

    public Bitmap getBitmap(){
        return BitmapFactory.decodeFile(path);
    }

    public PhotoFileModel(String path){
        this.path = path;
    }


    //Get array list of photos that contains photos filtered based on timestamps, keywords and location data. This is filtered through the Stream API.
    public static ArrayList<PhotoFileModel> findPhotos(Date startTimestamp, Date endTimestamp, String keywords, String location) {

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Android/data/com.example.myfirstapp/files/Pictures");

        ArrayList<PhotoFileModel> photos = new ArrayList<PhotoFileModel>();

        File[] fList = file.listFiles();
        if (fList != null)
        {
            flist.stream.filter(file -> file.lastModified() >= startTimestamp.getTime() || startTimestamp == null).filter(file -> file.lastModified() <= endTimestamp.getTime() || endTimestamp == null).filter(file -> file.getPath().contains(keywords) || keywords = "").filter(file -> file.getPath().contains(location) || location == "")
            .foreach(file -> photos.add(new PhotoFileModel(file.getPath)));

        }
        return photos;
    }
}