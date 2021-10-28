package com.example.myfirstapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class GalleryItemFactory {
    public static GalleryItem CreateGalleryItem(String path){
        PhotoFileModel photo = new PhotoFileModel();
        photo.path = path;
        return photo;
    }

    //Get array list of photos that contains photos filtered based on timestamps, keywords and location data. This is filtered through the Stream API.
    public static ArrayList<PhotoFileModel> findPhotos(Date startTimestamp, Date endTimestamp, String keywords, String location) {

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Android/data/com.example.myfirstapp/files/Pictures");

        ArrayList<PhotoFileModel> photos = new ArrayList<PhotoFileModel>();

        File[] fList = file.listFiles();
        if (fList != null)
        {
            Arrays.stream(fList).filter(f -> f.lastModified() >= startTimestamp.getTime() || startTimestamp == null)
                    .filter(f -> f.lastModified() <= endTimestamp.getTime() || endTimestamp == null)
                    .filter(f -> f.getPath().contains(keywords) || keywords == "")
                    .filter(f -> f.getPath().contains(location) || location == "")
                    .forEach(f -> photos.add((PhotoFileModel) GalleryItemFactory.CreateGalleryItem(f.getPath())));

        }
        return photos;
    }
}
