package edu.upenn.cis350.cis350finalproject.data;

import android.util.Log;

import org.json.JSONObject;

import java.net.URL;

import edu.upenn.cis350.cis350finalproject.APITask;

public class DataSource {

    public static boolean isCorrectPassword(String username, String password) {
        try {
            URL url = new URL("http://10.0.2.2:3000/loginCheck?username=" + username + "&password=" + password);
            APITask task = new APITask();
            task.execute(url);
            String result = task.get();
            Log.d("RESULT", result);
            return (result.equals("true"));
        }catch (Exception e){
            return false;
        }
    }

    public static void createAccount(String firstName, String lastName, String username, String password, String email, String phoneNumber, String accountType, String location, String organization) {
        try {
            URL url = new URL("http://10.0.2.2:3000/createaccount?firstName=" + firstName +
                    "&lastName=" + lastName + "&username=" + username + "&phoneNumber=" + phoneNumber
            + "&email=" + email + "&password=" + password + "&userType=" + accountType +
                    "&organization=" + organization + "&location=" + location);
            APITask task = new APITask();
            task.execute(url);
            String result = task.get();
            Log.d("RESULT", result);
        }catch (Exception e){

        }
    }

    public static boolean isUsernameTaken(String username) {
        try {
            URL url = new URL("http://10.0.2.2:3000/usernameTaken?username=" + username);
            APITask task = new APITask();
            task.execute(url);
            String result = task.get();
            Log.d("RESULT", result);
            return (result.equals("true"));
        }catch (Exception e){
            return false;
        }
    }

    public static void updateAccount(String firstName, String lastName, String username, String password, String email, String phoneNumber, String accountType, String location, String organization) {
        try {
            Log.d("MADE IT HERE", "yuh");
            URL url = new URL("http://10.0.2.2:3000/updateaccount?firstName=" + firstName +
                    "&lastName=" + lastName + "&username=" + username + "&phoneNumber=" + phoneNumber
                    + "&email=" + email + "&password=" + password + "&userType=" + accountType
            + "&organization=" + organization + "&location=" + location);
            Log.d("THE URL", url.toString());
            APITask task = new APITask();
            task.execute(url);
            String result = task.get();
            Log.d("RESULT", result);
        }catch (Exception e){

        }
    }

    public static JSONObject getAccountInfo(String username) {
        try {
            URL url = new URL("http://10.0.2.2:3000/getUser?username=" + username);
            APITask task = new APITask();
            task.execute(url);
            String result = task.get();
            JSONObject j = new JSONObject(result);
            Log.d("RESULT", result);
            return j;

        }catch (Exception e){
            return null;
        }
    }

    public static void deleteAccount(String username) {
        try {
            URL url = new URL("http://10.0.2.2:3000/deleteaccount?username=" + username);
            APITask task = new APITask();
            task.execute(url);
            String result = task.get();
            JSONObject j = new JSONObject(result);
            Log.d("RESULT", result);

        }catch (Exception e){

        }
    }
}
