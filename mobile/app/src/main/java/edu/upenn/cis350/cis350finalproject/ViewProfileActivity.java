package edu.upenn.cis350.cis350finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;

import edu.upenn.cis350.cis350finalproject.data.DataSource;
import edu.upenn.cis350.cis350finalproject.ui.login.LoginActivity;

public class ViewProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String username = getIntent().getStringExtra("username");
        String userType = getIntent().getStringExtra("userType");
        setContentView(R.layout.activity_view_profile);

        TextView tv = (TextView) findViewById(R.id.top_donor);
        TextView tagView = (TextView) findViewById(R.id.tags);
        TextView numPostsView = (TextView) findViewById(R.id.num_posts);
        TextView numPortionsView = (TextView) findViewById(R.id.num_portions);


        ((TextView) findViewById(R.id.profile_view_title)).setText(username);

        ((TextView) findViewById(R.id.firstName)).setText(getIntent().getStringExtra("firstName") + " " + getIntent().getStringExtra("lastName"));
        ((TextView) findViewById(R.id.input_email)).setText("Email: " + getIntent().getStringExtra("email"));
        ((TextView) findViewById(R.id.input_organization)).setText("Organization: " + getIntent().getStringExtra("organization"));
        ((TextView) findViewById(R.id.input_location)).setText("Location: " + getIntent().getStringExtra("location"));
        ((TextView) findViewById(R.id.account_type)).setText(userType);
        String profPic = getIntent().getStringExtra("profilePic");
        setProfilePicture(profPic);

        if (userType.equals("Donor")) {
            TextView phone = (TextView) findViewById(R.id.input_phone_number);
            phone.setVisibility(View.GONE);

            JSONArray j = DataSource.getAllPosts();

            int countPortions = 0;
            int countPosts = 0;
            HashSet<String> tags = new HashSet<>();
            for (int i = 0; i < j.length(); i++) {
                try {
                    JSONObject post = (JSONObject) j.get(i);
                    if (post.getString("postedBy").equals(username)) {
                        countPosts++;
                        String portions = post.getString("numPortions");
                        if (!portions.isEmpty()) {
                            countPortions = countPortions + Integer.parseInt(portions);
                        }

                        Object t = post.get("tags");
                        String allTags = t.toString();
                        allTags = allTags.substring(allTags.lastIndexOf("[") + 1, allTags.indexOf("]"));
                        Log.d("tags!", allTags);

                        String[] currTags = allTags.split(",");

                        for (String s : currTags) {
                            if (!s.isEmpty())
                                tags.add(s);
                        }
                    }
                } catch (Exception e) {

                }

            }
            Log.d("EVERY tag!", tags.toString());

            HashMap<String, Integer> topUsers = DataSource.getStatsForProfile("getStatsForProfile");
            Log.d("top users", topUsers.toString());
            if (topUsers.containsKey(username)) {
                String text = "TOP 10 DONOR AWARD!";
                tv.setText(text);
            } else {
                tv.setVisibility(View.GONE);
            }

            if (tags.isEmpty()) {
                tagView.setVisibility(View.GONE);
            } else {
                tagView.setText("Common donations: " + tags.toString());
            }

            numPostsView.setText("Number of posts: " + countPosts);

            if (countPortions == 0) {
                numPortionsView.setVisibility(View.GONE);
            } else {
                numPortionsView.setText("Portions served: " + countPortions);
            }

        } else {
            tv.setVisibility(View.GONE);
            tagView.setVisibility(View.GONE);

            numPortionsView.setVisibility(View.GONE);
            ((TextView) findViewById(R.id.input_phone_number)).setText("Phone number: " + getIntent().getStringExtra("phoneNumber"));
            JSONArray j = DataSource.getClaimsByObtainer(username);
            int claimCount = 0;
            for (int i = 0; i < j.length(); i++) {
                try {
                    JSONObject o = (JSONObject) j.get(i);
                    String status = o.getString("claimStatus");
                    if (status.equals("accepted")) {
                        claimCount++;
                    }
                } catch (Exception e) {

                }
            }

            numPostsView.setText("Number of donations accepted: " + claimCount);
        }



    }

    void setProfilePicture(String imageURL) {
        ImageView profPic = (ImageView) findViewById(R.id.profilePic);
        LoadImageTask task = new LoadImageTask();
        try {
            URL url = new URL(imageURL);
            task.execute(url);
            Object result = task.get();
            if (result != null) {
                profPic.setImageDrawable((Drawable) result);
            } else {
                profPic.setImageResource(android.R.drawable.ic_menu_camera);
            }
        } catch (Exception e) {
            Log.d("No Real URL", "No Real URL");
            profPic.setImageResource(android.R.drawable.ic_menu_camera);
        }

    }
}