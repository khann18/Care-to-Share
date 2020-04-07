package edu.upenn.cis350.cis350finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import edu.upenn.cis350.cis350finalproject.data.DataSource;

public class ClaimPostActivity extends AppCompatActivity {

    private String postId;
    private String donorUsername = "";
    private String firstName = "";
    private String organization = "";
    private String headerText = "";
    private String obtainerUsername = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.claim_post);
        Bundle bundle = getIntent().getExtras();
        postId = bundle.getString("POSTID");
        obtainerUsername = bundle.getString("USER");
        JSONObject post = DataSource.findPostById(postId);
        try {
            if (post != null) {
                donorUsername = post.getString("postedBy");
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
        TextView header = (TextView) findViewById(R.id.header);
        header.setText(headerText);
    }

    public void onSendButtonClick(View view) {
        EditText editText = (EditText) findViewById(R.id.enterMessage);
        String message = editText.getText().toString();

        DataSource.createClaim(obtainerUsername, donorUsername, postId, message);
        finish();
    }
}