package com.example.thomas.center.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.thomas.center.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by thomas on 28/03/17.
 */
public class DownloadImageTask extends AsyncTask<String,Void,Bitmap> {

    private ImageView imageToDl;

    public DownloadImageTask(ImageView imageToDl, Context context){
        this.imageToDl = imageToDl;
        Context context1 = context;
    }

    @Override
    protected void onPreExecute() {
            imageToDl.setImageResource(R.drawable.placeholder);
    }


    @Override
    protected Bitmap doInBackground(String... params) {
        String url = params[0];
        InputStream in = null;

        if(url.contains("youtube")){
            url = "http://img.youtube.com/vi/" + params[0].split("=")[1]+"/default.jpg";
        }

        try {
            in = new URL(url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(in);
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {
        this.imageToDl.setImageBitmap(bitmap);
    }

}
