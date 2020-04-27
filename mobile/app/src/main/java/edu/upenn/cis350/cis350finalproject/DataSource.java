package edu.upenn.cis350.cis350finalproject;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import edu.upenn.cis350.cis350finalproject.AccessWebTask;
import edu.upenn.cis350.cis350finalproject.Post;
import edu.upenn.cis350.cis350finalproject.User;

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

    public static void createPost(String description, String contact, String location, String marked, String poster, String numPortions, ArrayList<String> foods) {
        try {
            URL url = new URL("http://10.0.2.2:3000/post?description=" + description +
                    "&location=" + location + "&poster=" + poster + "&contact=" + contact + "&marked=" + marked
                    + "&numPortions=" + numPortions + "&tags=" + foods);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String result = task.get();
            Log.d("RESULT", result);
        }catch (Exception e){

        }
    }

    public static void createFullUser(User user) {
        try {
            URL url = new URL("http://10.0.2.2:3000/createaccount?firstName=" + user.getFirstName() +
                    "&lastName=" + user.getLastName() +
                    "&location=" + user.getLocation() +
                    "&userType=" + user.getUserType() +
                    "&username=" + user.getUsername() +
                    "&password=" + user.getPassword() +
                    "&phoneNumber=" + user.getPhoneNumber() +
                    "&email=" + user.getEmail() +
                    "&organization=" + user.getOrganization());
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String result = task.get();
            Log.d("RESULT", result);
        }catch (Exception e){
            System.out.println("yikessssss");
        }
    }

    public static void createPost(Post p) {
        try {
            URL url = new URL("http://10.0.2.2:3000/createpost?description=" + p.getDescription() +
                    "&location=" + p.getLocation() +
                    "&pickupTime=" + p.getPickupTime() +
                    "&postedBy=" + p.getPostedBy().getUsername() +
                    "&contactInfo=" + p.getContactInfo() +
                    "&isClaimed=" + p.getIsClaimed() +
                    "&claimMessage=" + p.getClaimMessage());
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            System.out.println("we're getting here1");
            String result = task.get();
            System.out.println("we're getting here2");
            Log.d("RESULT", result);
            System.out.println("we're getting here3");
        }catch (Exception e){
            System.out.println("yikessssss");
        }
    }

    public static void setPostIsClaimed(String postId) {
        try {
            URL url = new URL("http://10.0.2.2:3000/setPostIsClaimed?postId=" + postId);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String result = task.get();
            Log.d("RESULT", result);
        }catch (Exception e){
            Log.d("post is claimed", "yikessssss");
        }
    }

    public static void getTest() {
        try {
            URL url = new URL("http://10.0.2.2:3000/getUser");
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String result = task.get();
            Log.d("RESULT", result);
        }catch (Exception e){
            System.out.println("yikessssss");
        }
    }

    public static JSONObject findPostById(String postId) {
        try {
            URL url = new URL("http://10.0.2.2:3000/findPostById=" + postId);
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
                    "&donorUsername=" + donorUsername + "&postId=" + postId + "&claimMessage=" + claimMessage +
                    "&claimStatus=none");
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String result = task.get();
            Log.d("RESULT", result);
        }catch (Exception e){

        }
    }

    public static JSONArray getPosts() {
        try {
            URL url = new URL("http://10.0.2.2:3000/getPost");
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String result = task.get();
            JSONArray j = new JSONArray(result);
            Log.d("RESULT", result);
            return j;

        }catch (Exception e){
            return null;
        }
    }

    public static JSONArray getClaimsByDonor(String donorUsername) {
        try {
            URL url = new URL("http://10.0.2.2:3000/getClaimsByDonor?donorUsername=" + donorUsername);
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

}