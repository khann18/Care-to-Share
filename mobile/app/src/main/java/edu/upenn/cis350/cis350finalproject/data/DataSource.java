package edu.upenn.cis350.cis350finalproject.data;

import android.util.Log;

import java.net.URL;

import edu.upenn.cis350.cis350finalproject.AccessWebTask;
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

    //User me = new User("Katherine", "Hann", "Phoenix",
    //                "Donor", "khann22", "123", "6023205772",
    //                "khann22@seas.upenn.edu", "Food4U");
    //        Post p = new Post("Yes I am giving away tasty foods.", "Philadelphia", new Date(), me, "khann22@seas.upenn.edu");
//    public static void createFullUser(String firstName, String lastName, String location,
//                                      String userType, String username, String password,
//                                      String phoneNumber, String email, String organization) {
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
}