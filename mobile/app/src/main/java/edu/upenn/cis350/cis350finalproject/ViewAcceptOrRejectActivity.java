package edu.upenn.cis350.cis350finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import edu.upenn.cis350.cis350finalproject.data.DataSource;

import org.json.JSONObject;

public class ViewAcceptOrRejectActivity extends AppCompatActivity {

    private String claimId;
    private String obtainerUsername;
    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_accept_or_reject);
        Bundle bundle = getIntent().getExtras();
        claimId = bundle.getString("CLAIMID");
        obtainerUsername = bundle.getString("username");

        JSONObject claim = DataSource.getClaimById(claimId);

        String donorUsername = "";
        String origPostDesc = "";
        String location;
        String phoneNumber;
        String email;
        String status = "";
        String donorName = "";
        String org = "";
        String text = "";
        String yourClaim = "";
        JSONObject donor = null;

        try {
            postId = claim.getString("postId");
            JSONObject post = DataSource.findPostById(postId);
            origPostDesc = post.getString("description");
            donorUsername = claim.getString("donorUsername");
            status = claim.getString("claimStatus");
            yourClaim = claim.getString("claimMessage");

            donor = DataSource.getAccountInfo(donorUsername);
            donorName = donor.getString("firstName");
            org = donor.getString("organization");
            text = "Original post by " + donorName + " from " + org + ":\n \n" + origPostDesc;

        } catch (Exception e) {
            text = "Cannot access original post.";
        }
        TextView statusInfo = (TextView) findViewById(R.id.statusInfo);

        if (status.equals("none")) {
            statusInfo.setText("Status: WAITING");
        } else if (status.equals("rejected")){
            statusInfo.setText("Status: REJECTED");
        } else if (status.equals("accepted")) {
            statusInfo.setText("Status: ACCEPTED");
        }
        statusInfo.setTextColor(getResources().getColor(android.R.color.black));
        TextView header = (TextView) findViewById(R.id.header);
        header.setText(text);

        TextView claimInfo = (TextView) findViewById(R.id.claimInfo);
        claimInfo.setText("Your claim message: \n \n" + yourClaim);




        String info = "";
        TextView addInfo = (TextView) findViewById(R.id.additionalInfo);
        if (status.equals("none")) {
            info = "Donor response needed.";
            addInfo.setText(info);
        } else if (status.equals("accepted")) {
            try {
                location = donor.getString("location");
                phoneNumber = donor.getString("phoneNumber");
                email = donor.getString("email");

                info = "Additional Information: \n \nOrganization location: " + location + "\n" +
                        donor.getString("firstName") + "'s email: " + email + "\n" +
                        donor.getString("firstName") + "'s phone number: " + phoneNumber;
            } catch (Exception e) {
                info = "Cannot retrieve donor information.";
            }
            String numPortions = "unknown";
            String tags = "none";

            try {
                JSONObject post = DataSource.findPostById(postId);
                numPortions = post.getString("numPortions");
                tags = post.getString("tags");
                tags = tags.substring(tags.lastIndexOf("[") + 1, tags.indexOf("]"));
            } catch (Exception e) {

            }

            if (tags == null || tags.isEmpty()) {
                tags = "none";
            }
            if (numPortions == null || numPortions.isEmpty()) {
                numPortions = "unknown";
            }
            info = info + "\nNumber of portions: " + numPortions + "\nFood tags: " + tags;
            addInfo.setText(info);
        } else if (status.equals("rejected")) {
            addInfo.setVisibility(View.GONE);
        }
        addInfo.setTextColor(getResources().getColor(android.R.color.black));
    }


}
