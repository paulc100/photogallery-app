package com.example.myfirstapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

public class PhotoFileModel implements GalleryItem{
    public String path;

    public String[] getAttributes(){
        return path.split("_");
    }

    public Bitmap getBitmap(){
        return BitmapFactory.decodeFile(path);
    }

    public PhotoFileModel(){
    }
}