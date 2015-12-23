package com.example.makarov.photonews;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by makarov on 23.12.15.
 */
public class ImageTarget implements Target {

    private int mNameSaveImg;

    public ImageTarget(int index) {
        mNameSaveImg = index;
    }

    @Override
    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                File myFile = new File(Environment.getExternalStorageDirectory().getPath(),
                        "Instagram/" + mNameSaveImg + ".jpg");
                try {
                    myFile.createNewFile();

                    FileOutputStream ostream = new FileOutputStream(myFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 75, ostream);

                    ostream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        if (placeHolderDrawable != null) {
        }
    }
}
