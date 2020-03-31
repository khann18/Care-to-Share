package edu.upenn.cis350.cis350finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ClaimPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.claim_post);
        Bundle bundle = getIntent().getExtras();
        Post post = (Post) bundle.getSerializable("POST");
        TextView header = (TextView) findViewById(R.id.header);
        String headerText = "Provide more information about your organization to " +
                post.getPostedBy().getFirstName() + " from " +
                post.getPostedBy().getOrganization() + ":";
        header.setText(headerText);
    }

    public void onSendButtonClick(View view) {
        EditText editText = (EditText) findViewById(R.id.enterMessage);
        finish();
    }
}
