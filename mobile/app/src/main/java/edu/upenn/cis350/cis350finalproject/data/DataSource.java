package edu.upenn.cis350.cis350finalproject.data;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.net.URL;

import edu.upenn.cis350.cis350finalproject.APITask;
import edu.upenn.cis350.cis350finalproject.AccessWebTask;
import edu.upenn.cis350.cis350finalproject.Post;
import edu.upenn.cis350.cis350finalproject.User;

import org.json.JSONArray;
import org.json.JSONObject;


public class DataSource {

    public static boolean isCorrectPassword(String username, String password) {
        try {
            URL url = new URL("http://10.0.2.2:3000/loginCheck?username=" + username + "&password=" + password);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String result = task.get();
            Log.d("RESULT", result);
            return (result.equals("true"));
        }catch (Exception e){
            return false;
        }
    }


    public static void createPost(String description, String contact, String location, String marked, String poster) {
        try {
            URL url = new URL("http://10.0.2.2:3000/post?description=" + description +
                    "&location=" + location + "&poster=" + poster + "&contact=" + contact + "&marked=" + marked);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String result = task.get();
            Log.d("RESULT", result);
        }catch (Exception e){

        }
    }

    public static JSONObject getAccountInfo(String username) {
        try {
            URL url = new URL("http://10.0.2.2:3000/getUser?username=" + username);
            AccessWebTask task = new AccessWebTask();
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
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String result = task.get();
            JSONObject j = new JSONObject(result);
            Log.d("RESULT", result);

        }catch (Exception e){

        }
    }

    public static JSONObject findPostById(String postId) {
        try {
            URL url = new URL("http://10.0.2.2:3000/findPostById?postId=" + postId);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String result = task.get();
            JSONObject j = new JSONObject(result);
            Log.d("RESULT", result);
            return j;

        }catch (Exception e){
            return null;
        }
    }

    public static void createClaim(String obtainerUsername, String donorUsername, String postId, String claimMessage) {
        try {
            URL url = new URL("http://10.0.2.2:3000/createClaim?obtainerUsername=" + obtainerUsername +
                    "&donorUsername=" + donorUsername + "&postId=" + postId + "&claimMessage=" + claimMessage);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String result = task.get();
            Log.d("RESULT", result);
        }catch (Exception e){

        }
    }

    public static JSONObject getPosts() {
        try {
            URL url = new URL("http://10.0.2.2:3000/getPost");
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String result = task.get();
            JSONObject j = new JSONObject(result);
            Log.d("RESULT", result);
            return j;

        }catch (Exception e){
            return null;
        }
    }

    public static JSONObject getClaimById(String id) {
        try {
            URL url = new URL("http://10.0.2.2:3000/getClaimById?claimId=" + id);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String result = task.get();
            JSONObject j = new JSONObject(result);
            Log.d("RESULT", result);
            return j;

        }catch (Exception e){
            return null;
        }
    }

    public static void updateClaimStatus(String claimId, String status) {
        try {
            URL url = new URL("http://10.0.2.2:3000/updateClaimStatus?claimId=" +
                    claimId + "&claimStatus=" + status);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String result = task.get();
            Log.d("RESULT", result);
        }catch (Exception e) {
            System.out.println("yikessssss");
        }
    }

    public static void updateClaimsForAcceptedPost(String postId) {
        try {
            URL url = new URL("http://10.0.2.2:3000/updateClaimsForAcceptedPost?postId=" +
                    postId);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String result = task.get();
            Log.d("RESULT", result);
        }catch (Exception e) {
            System.out.println("yikessssss");
        }
    }

    public static JSONArray getAllPosts() {
        Log.d("RESULT", "Commence Get Posts");
        String res = null;
        try {
            URL url = new URL("http://10.0.2.2:3000/getPost");
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            res = task.get();
            int t = res.indexOf('[');
            int t1 = res.indexOf(']');
            res = res.substring(t, t1) + "]";

            Log.d("RESULT", res);
            JSONArray j = new JSONArray(res);
            return j;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONArray getClosePosts(LatLng coords) {
        Log.d("RESULT", "Commence Get Posts");
        String res = null;
        try {
            URL url = new URL("http://10.0.2.2:3000/getCPost?" + "lat" + coords.latitude + "&" + "lng" + coords.longitude);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            res = task.get();
            int t = res.indexOf('[');
            int t1 = res.indexOf(']');
            res = res.substring(t, t1) + "]";

            Log.d("RESULT", res);
            JSONArray j = new JSONArray(res);
            return j;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONArray getClaimsByObtainer(String obtainerUsername) {
        try {
            URL url = new URL("http://10.0.2.2:3000/getClaimsByObtainer?obtainerUsername=" + obtainerUsername);
            APITask task = new APITask();
            task.execute(url);
            String result = task.get();
            JSONArray j = new JSONArray(result);
            Log.d("RESULT", result);
            return j;

        }catch (Exception e){
            return null;
        }
    }



    public static void createAccount(String firstName, String lastName, String username, String password, String email, String phoneNumber,
                                     String userType, String location, String organization, String profilePic) {
        try {
            URL url = new URL("http://10.0.2.2:3000/createaccount?firstName=" + firstName +
                    "&lastName=" + lastName + "&username=" + username + "&phoneNumber=" + phoneNumber
                    + "&email=" + email + "&password=" + password + "&userType=" + userType
                    + "&location=" + location + "&organization=" + organization + "&profilePic=" + profilePic);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String result = task.get();
            Log.d("RESULT", result);
        }catch (Exception e){

        }
    }

    public static boolean isUsernameTaken(String username) {
        try {
            URL url = new URL("http://10.0.2.2:3000/usernameTaken?username=" + username);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String result = task.get();
            Log.d("RESULT", result);
            return (result.equals("true"));
        }catch (Exception e){
            return false;
        }
    }


    public static void updateAccount(String firstName, String lastName, String username, String password, String email, String phoneNumber, String accountType, String location, String organization, String profilePic) {
        try {
            Log.d("MADE IT HERE", "yuh");
            URL url = new URL("http://10.0.2.2:3000/updateaccount?firstName=" + firstName +
                    "&lastName=" + lastName + "&username=" + username + "&phoneNumber=" + phoneNumber
                    + "&email=" + email + "&password=" + password + "&userType=" + accountType
                    + "&organization=" + organization + "&location=" + location + "&profilePic=" + profilePic);
            Log.d("THE URL", url.toString());
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String result = task.get();
            Log.d("RESULT", result);
        }catch (Exception e){

        }
    }
}