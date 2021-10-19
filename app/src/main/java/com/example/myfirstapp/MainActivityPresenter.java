package com.example.myfirstapp;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.stream.Stream;

public class MainActivityPresenter {
    private ArrayList<String> photos = null; //Model
    private MainActivityView view;
    private int index = 0;
    private String mPhotoCity;

    String mCurrentPhotoPath;


    protected Location mLastLocation;
    private double mLatitude;
    private double mLongitude;

    private FusedLocationProviderClient fusedLocationClient;
    public MainActivityPresenter(MainActivityView view) {
        this.view = view;
        this.photos = PhotoFileModel.findPhotos(new Date(Long.MIN_VALUE), new Date(), "", "");
        if (photos.size() == 0) {
            view.displayPhoto(null, null);
        } else {
            String path = photos.get(index);
            String[] attr = path.split("_");
            view.displayPhoto(BitmapFactory.decodeFile(path), attr);


        }
        //this.stream = load all photos from device
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(view.getContext());
    }

    public void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(view.getContext(), "com.example.myfirstapp.fileprovider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            view.takePhoto(takePictureIntent);
        }
        //}
    }

    public void saveNewPhoto(){
        String[] attr = mCurrentPhotoPath.split("_");
        view.displayPhoto(BitmapFactory.decodeFile(mCurrentPhotoPath), attr);

        photos = PhotoFileModel.findPhotos(new Date(Long.MIN_VALUE), new Date(), "", "");
    }


    //Probably also related to the creational pattern
    private File createImageFile() throws IOException {
        // Create an image file name

        getLastLocation();
        getAddress(mLatitude,mLongitude);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "_caption_" + timeStamp + "_" + mPhotoCity + "_";
        File storageDir = view.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg",storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();

        return image;
    }

    public void updatePhoto(String caption) {
        String path = photos.get(index);
        String[] attr = path.split("_");
        if (attr.length >= 3) {
            String newCaption;
            if (mLastLocation != null) {
                newCaption = attr[0] + "_" + caption + "_" + attr[2] + "_" + attr[3] + "_" + attr[4] + "_";
            } else {
                newCaption = attr[0] + "_" + caption + "_" + attr[2] + "_" + attr[3] + "_" + "NoLocation" + "_";
            }
            File to = new File(newCaption);
            File from = new File(path);
            from.renameTo(to);
            photos.set(photos.indexOf(path),newCaption);
        }
    }

    public void sharePhoto() {

        if(photos.size() > 0) {

            String path = photos.get(index);
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(view.getContext(), "com.example.myfirstapp.fileprovider", new File(path));
            share.putExtra(Intent.EXTRA_STREAM, uri);
            view.sharePhoto(share);
        }
    }

    public void scrollPhotos(Boolean forward) {
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
            String path = photos.get(index);
            String[] attr = path.split("_");
            view.displayPhoto(BitmapFactory.decodeFile(path), attr);
        }
    }

    public void searchPhotos(Intent data){
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
        index = 0;
        photos = PhotoFileModel.findPhotos(startTimestamp, endTimestamp, keywords, location);
        if (photos.size() == 0) {
            view.displayPhoto(null, null);
        } else {
            String path = photos.get(index);
            String[] attr = path.split("_");
            view.displayPhoto(BitmapFactory.decodeFile(path), attr);
        }
    }

    public void getAddress(double LATITUDE, double LONGITUDE) {
        Context context = view.getContext();
        //Set Address
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {
                mPhotoCity = addresses.get(0).getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }


    @SuppressWarnings("MissingPermission")
    public void getLastLocation() {
        fusedLocationClient.getLastLocation()
                .addOnCompleteListener((AppCompatActivity) view, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                            mLatitude = mLastLocation.getLatitude();
                            mLongitude = mLastLocation.getLongitude();
                        } else {
                            //Log.w(TAG, "getLastLocation:exception", task.getException());
                            //showSnackbar(getString(R.string.no_location_detected));
                        }
                    }
                });
    }

    public interface MainActivityView{

        void displayPhoto(Bitmap image, String[] attr);
        void takePhoto(Intent photoIntent);
        void sharePhoto(Intent shareIntent);
        Context getContext();
    }
}