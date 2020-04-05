package edu.upenn.cis350.cis350finalproject;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class AccessWebTask extends AsyncTask<URL, String, String> {
    /*This method is called in background when this object's "execute" method is invoked.
    The arguments passed to "execute" are passed to this method.*/
    protected String doInBackground(URL... urls) {
        try {
            //get the first URL from the array
            URL url = urls[0];

            //create connection and send HTTP request
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();


            //read first line of data that is returned
            Scanner in = new Scanner(url.openStream());
            String msg = in.nextLine();

            //use Android JSON library to parse JSON
            JSONObject jo = new JSONObject(msg);

            //assumes that JSON object contains a "name" field
            String result = jo.getString("result");

            // this will be passed to onPostExecute method

    protected String doInBackground(URL[] urls) {
        try {
            URL url = urls[0];
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            Scanner in = new Scanner(url.openStream());
            String msg = in.nextLine();
            JSONObject jo = new JSONObject(msg);
            String result = jo.getString("result");
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }

    /*This method is called in foreground after doInBackground finishes.
    It can access and update Views in user interface.*/
    protected void onPostExecute(String msg) {
        // not implemented but you can use this if you’d like

    }
}