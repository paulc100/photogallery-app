package com.example.myfirstapp;

import android.graphics.Bitmap;

public interface GalleryItem {
    String[] getAttributes();

    Bitmap getBitmap();
}
