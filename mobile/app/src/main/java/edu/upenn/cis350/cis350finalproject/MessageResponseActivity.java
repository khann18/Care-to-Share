package edu.upenn.cis350.cis350finalproject;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MessageResponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.claim_post);
        Bundle bundle = getIntent().getExtras();
        String claimId = bundle.getString("CLAIMID");
        String description = bundle.getString("DESCRIPTION");
        TextView header = (TextView) findViewById(R.id.header);
        String headerText = description;
        header.setText(headerText);
    }
}
