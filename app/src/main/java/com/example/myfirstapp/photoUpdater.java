package com.example.myfirstapp;

import java.io.File;
import java.util.ArrayList;

public class photoUpdater{

    public void updatePhoto(String caption, ArrayList<PhotoFileModel> photos, int index, String mLastLocation) {
        PhotoFileModel photo = photos.get(index);
        String path = photo.path;
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
            photo.path = newCaption;
        }
    }

}
