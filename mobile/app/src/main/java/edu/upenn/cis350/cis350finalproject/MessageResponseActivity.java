package edu.upenn.cis350.cis350finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import edu.upenn.cis350.cis350finalproject.data.DataSource;

import org.json.JSONObject;

public class MessageResponseActivity extends AppCompatActivity {

    private String claimId;
    private String donorUsername;
    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_response);
        Bundle bundle = getIntent().getExtras();
        claimId = bundle.getString("CLAIMID");
        donorUsername = bundle.getString("username");

        String description = bundle.getString("DESCRIPTION");
        TextView header = (TextView) findViewById(R.id.header);
        int index = description.indexOf(":") + 1;
        description = description.substring(0, index) + "\n" + description.substring(index);
        String headerText = description;
        header.setText(headerText);


        TextView addInfo = (TextView) findViewById(R.id.additionalInfo);
        String info = "";
        JSONObject claim = DataSource.getClaimById(claimId);
        String obtainerUsername = "";
        String location;
        String phoneNumber;
        String email;
        try {
            obtainerUsername = claim.getString("obtainerUsername");
            JSONObject obtainer = DataSource.getAccountInfo(obtainerUsername);
            location = obtainer.getString("location");
            phoneNumber = obtainer.getString("phoneNumber");
            email = obtainer.getString("email");
            postId = claim.getString("postId");

            info = "Additional Information: \n \nOrganization location: " + location + "\n" +
                    obtainer.getString("firstName") + "'s email: " + email + "\n" +
                    obtainer.getString("firstName") + "'s phone number: " + phoneNumber;
        } catch (Exception e) {

        }
        addInfo.setText(info);
    }

    private final int MessageBoardActivity_ID = 1;

    //Gotta tell person that their claim was rejected - add field in claims db
    //make sure to show if not responded to
    public void onRejectButtonClick(View view) {
        DataSource.updateClaimStatus(claimId, "rejected");
        Intent i = new Intent(this, MessageBoardActivity.class);

        i.putExtra("username", donorUsername);
        startActivityForResult(i, MessageBoardActivity_ID);
    }

    public void onAcceptButtonClick(View view) {
        DataSource.updateClaimsForAcceptedPost(postId);
        DataSource.setPostIsClaimed(postId);

        DataSource.updateClaimStatus(claimId, "accepted");
        Intent i = new Intent(this, MessageBoardActivity.class);

        i.putExtra("username", donorUsername);
        startActivityForResult(i, MessageBoardActivity_ID);
    }
}
