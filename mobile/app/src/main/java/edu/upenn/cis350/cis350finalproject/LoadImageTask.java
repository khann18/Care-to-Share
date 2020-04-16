package edu.upenn.cis350.cis350finalproject;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class LoadImageTask extends AsyncTask<URL, String, Drawable> {
    /*This method is called in background when this object's "execute" method is invoked.
    The arguments passed to "execute" are passed to this method.*/
    protected Drawable doInBackground(URL... urls) {

        try {
            HttpURLConnection conn = (HttpURLConnection) urls[0].openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            Object img = conn.getContent();
            InputStream i = (InputStream) img;
            Drawable d = Drawable.createFromStream(i, "src name");
            return d;
        } catch (Exception e) {
            Log.d("Error getting iamge", e.toString());
            return null;
        }
    }

}

