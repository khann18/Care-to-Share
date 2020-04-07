package edu.upenn.cis350.cis350finalproject;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class APITask extends AsyncTask<URL, String, String> {
    protected String doInBackground(URL[] urls) {
        try {
            URL url = urls[0];
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            Scanner in = new Scanner(url.openStream());
            String msg = in.nextLine();
            JSONArray jo = new JSONArray(msg);
            return jo.toString();
        } catch (Exception e) {
            return null;
        }


    }
}
