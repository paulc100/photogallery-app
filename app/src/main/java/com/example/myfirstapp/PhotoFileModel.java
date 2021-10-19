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

    //This can be replaced with the creational design pattern
    public static Stream<PhotoFileModel> findPhotos(Date startTimestamp, Date endTimestamp, String keywords, String location) {

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Android/data/com.example.myfirstapp/files/Pictures");

        Stream<PhotoFileModel> photos = null;

        File[] fList = file.listFiles();
        if (fList != null)
        {
            for (File f : fList)
            {
                if (((startTimestamp == null && endTimestamp == null) || (f.lastModified() >= startTimestamp.getTime() && f.lastModified() <= endTimestamp.getTime())) && (keywords == "" || f.getPath().contains(keywords)) && (location == "" || f.getPath().contains(location)))
                {
                    //Just in case a stream needs to be initialized, use this to set a value when first discovering a new file
                    if(photos.count() == 0){}
                    else{}
                    photos = Stream.concat(photos, Stream.of(new PhotoFileModel(f.getPath())));

                }
            }
        }
        return photos;
    }

    //This can be replaced with the creational design pattern
    public static ArrayList<PhotoFileModel> findArrayPhotos(Date startTimestamp, Date endTimestamp, String keywords, String location) {

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Android/data/com.example.myfirstapp/files/Pictures");

        ArrayList<PhotoFileModel> photos = new ArrayList<PhotoFileModel>();

        File[] fList = file.listFiles();
        if (fList != null)
        {
            for (File f : fList)
            {
                if (((startTimestamp == null && endTimestamp == null) || (f.lastModified() >= startTimestamp.getTime() && f.lastModified() <= endTimestamp.getTime())) && (keywords == "" || f.getPath().contains(keywords)) && (location == "" || f.getPath().contains(location)))
                {

                    photos.add(new PhotoFileModel(f.getPath()));

                }
            }
        }
        return photos;
    }
}