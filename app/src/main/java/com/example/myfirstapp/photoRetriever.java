package com.example.myfirstapp;

import android.content.Intent;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class photoRetriever{

    public void scrollPhotos(MainActivityPresenter.MainActivityView view, Boolean forward, ArrayList<PhotoFileModel> photos, int index, String mPhotoCity) {

        if (photos.size() > 0) {
            mPhotoCity = null;
            if(forward){
                if (index < (photos.size() - 1)) {
                    index++;
                }
            }else{
                if (index > 0) {
                    index--;
                }
            }
            PhotoFileModel photo = photos.get(index);
            view.displayPhoto(photo.getBitmap(), photo.getAttributes());
        }
    }

    public void searchPhotos(Intent data, ArrayList<PhotoFileModel> photos, MainActivityPresenter.MainActivityView view){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTimestamp, endTimestamp;

        try {
            String from = (String) data.getStringExtra("STARTTIMESTAMP");
            String to = (String) data.getStringExtra("ENDTIMESTAMP");
            startTimestamp = format.parse(from);
            endTimestamp = format.parse(to);
        } catch (Exception ex) {
            startTimestamp = null;
            endTimestamp = null;
        }

        String keywords = (String) data.getStringExtra("KEYWORDS");
        String location = (String) data.getStringExtra("LOCATION");

        //index = 0;

        try
        {
            photos = GalleryItemFactory.findPhotos(startTimestamp, endTimestamp, keywords, location);
            PhotoFileModel photo = photos.get(0);
            view.displayPhoto(photo.getBitmap(), photo.getAttributes());
        }
        catch(Exception e)
        {

            view.displayPhoto(null, null);

        }


    }


}
