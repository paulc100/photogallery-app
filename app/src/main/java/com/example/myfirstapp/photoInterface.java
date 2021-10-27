package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class photoInterface {

    private photoRetriever retriever = new photoRetriever();
    private photoUpdater updater = new photoUpdater();

    public void scrollPhotos(MainActivityPresenter.MainActivityView view, Boolean forward, ArrayList<PhotoFileModel> photos, int index, String mPhotoCity)
    {retriever.scrollPhotos(view, forward, photos, index, mPhotoCity);}

    public void searchPhotos(Intent data, ArrayList<PhotoFileModel> photos, MainActivityPresenter.MainActivityView view)
    {retriever.searchPhotos(data, photos, view);}

    public void updatePhoto(String caption, ArrayList<PhotoFileModel> photos, int index, String mLastLocation)
    {updater.updatePhoto(caption, photos, index, mLastLocation);}

}
