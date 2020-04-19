package edu.upenn.cis350.cis350finalproject;

import android.util.Log;

import org.json.JSONArray;

import java.net.URL;

//import org.json.simple.parser.JSONParser;

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

    public static void createAccount(String firstName, String lastName) {
        try {
            URL url = new URL("http://10.0.2.2:3000/createaccount?firstName=" + firstName + "&lastName=" + lastName);
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

    public static void setClaimMessage(Post p) {
        try {
            URL url = new URL("http://10.0.2.2:3000/setclaimmessage?description=" +
                    p.getDescription() + "&message=" + p.getClaimMessage());
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String result = task.get();
            Log.d("RESULT", result);
        }catch (Exception e){
            System.out.println("yikessssss");
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

    public static JSONArray getAllPosts() {
        Log.d("RESULT", "Commence Get Posts");
        String res = null;
        try {
            URL url = new URL("http://10.0.2.2:3000/getPost");
            APITask task = new APITask();
            task.execute(url);
            res = task.get();
//            JSONParser parser_obj = new JSONParser();
//            JSONArray array_obj = (JSONArray) parser_obj.parse(res);
            return new JSONArray(res);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}