package edu.upenn.cis350.cis350finalproject.data;

import android.util.Log;

import java.net.URL;

import edu.upenn.cis350.cis350finalproject.AccessWebTask;
import edu.upenn.cis350.cis350finalproject.Post;
import edu.upenn.cis350.cis350finalproject.User;
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

    public static void createAccount(String firstName, String lastName, String username, String password, String email, String phoneNumber,
                                     String accountType, String location, String organization) {
        try {
            URL url = new URL("http://10.0.2.2:3000/createaccount?firstName=" + firstName +
                    "&lastName=" + lastName + "&username=" + username + "&phoneNumber=" + phoneNumber
                    + "&email=" + email + "&password=" + password + "&accountType=" + accountType
                    + "&location=" + location + "&organization=" + organization);
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


    public static void updateAccount(String firstName, String lastName, String username, String password, String email, String phoneNumber, String accountType, String location, String organization) {
        try {
            Log.d("MADE IT HERE", "yuh");
            URL url = new URL("http://10.0.2.2:3000/updateaccount?firstName=" + firstName +
                    "&lastName=" + lastName + "&username=" + username + "&phoneNumber=" + phoneNumber
                    + "&email=" + email + "&password=" + password + "&userType=" + accountType
            + "&organization=" + organization + "&location=" + location);
            Log.d("THE URL", url.toString());
            AccessWebTask task = new AccessWebTask();
            task.execute(url);
            String result = task.get();
            Log.d("RESULT", result);
        }catch (Exception e){

        
    }}
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
}