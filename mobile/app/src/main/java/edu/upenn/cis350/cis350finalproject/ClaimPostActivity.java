package edu.upenn.cis350.cis350finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.ArrayList;

import edu.upenn.cis350.cis350finalproject.data.DataSource;

public class ClaimPostActivity extends AppCompatActivity {

    private String postId;
    private String donorUsername = "";
    private String firstName = "";
    private String organization = "";
    private String headerText = "";
    private String obtainerUsername = "";
    private String numPortions = "unknown";
    private String tags = "none";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.claim_post);
        Bundle bundle = getIntent().getExtras();
        postId = bundle.getString("postid");
        obtainerUsername = bundle.getString("username");
        JSONObject post = DataSource.findPostById(postId);

        try {
            if (post != null) {

                donorUsername = post.getString("postedBy");
                numPortions = post.getString("numPortions");
                JSONObject donor = DataSource.getAccountInfo(donorUsername);
                firstName = donor.getString("firstName");
                organization = donor.getString("organization");

                headerText = "Provide more information about your organization to " +
                        firstName + " from " + organization + ":";
            } else {
                headerText = "Looks like this post has been removed. ";
            }


        } catch (Exception e) {
            headerText = headerText + "Cannot find the organization or user that created this post.";
        }

        try {
            if (post != null) {
                tags = post.getString("tags");
                tags = tags.substring(tags.lastIndexOf("[") + 1, tags.indexOf("]"));
            }
        } catch (Exception e) {

        }

        if (tags == null || tags.isEmpty()) {
            tags = "none";
        }
        String description = bundle.getString("description");
        TextView header1 = (TextView) findViewById(R.id.header1);
        header1.setText("Post description: \n" + description + "\n\nNumber of portions available: "
                + numPortions + "\nFood tags: " + tags);

        TextView header2 = (TextView) findViewById(R.id.header2);
        header2.setText(headerText);
    }

    public void onSendButtonClick(View view) {
        EditText editText = (EditText) findViewById(R.id.enterMessage);
        String message = editText.getText().toString();

        DataSource.createClaim(obtainerUsername, donorUsername, postId, message);
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("username", obtainerUsername);
        startActivity(i);
    }
}