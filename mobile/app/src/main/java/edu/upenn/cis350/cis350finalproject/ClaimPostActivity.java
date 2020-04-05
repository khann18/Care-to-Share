package edu.upenn.cis350.cis350finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.upenn.cis350.cis350finalproject.data.DataSource;

public class ClaimPostActivity extends AppCompatActivity {

private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.claim_post);
        Bundle bundle = getIntent().getExtras();
        post = (Post) bundle.getSerializable("POST");
        TextView header = (TextView) findViewById(R.id.header);
        String headerText = "Provide more information about your organization to " +
                post.getPostedBy().getFirstName() + " from " +
                post.getPostedBy().getOrganization() + ":";
        header.setText(headerText);
    }

    public void onSendButtonClick(View view) {
        EditText editText = (EditText) findViewById(R.id.enterMessage);
        String message = editText.getText().toString();
        post.setClaimMessage(message);
        post.setIsClaimed();
        DataSource.setClaimMessage(post);
        finish();
    }
}

//things Im not sure about. how to maintain objects between runs of the app
