package edu.upenn.cis350.cis350finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.upenn.cis350.cis350finalproject.ui.login.LoginActivity;

public class MessageBoardActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup radioGroup;
    private String donorUsername;
    private JSONArray arr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_message_board);
        Bundle bundle = getIntent().getExtras();
        donorUsername = bundle.getString("username");
        View selectButton = findViewById(R.id.select_post);
        selectButton.setVisibility(View.GONE);
        TextView noMessageText = (TextView) findViewById(R.id.noMessages);

        ImageButton editProfile = findViewById(R.id.edit_profile_button);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EditProfileActivity.class);
                i.putExtra("username", donorUsername);
                startActivityForResult(i,1);
            }
        });


        ImageButton createPost = findViewById(R.id.create_post);
        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreatePostActivity.class);
                i.putExtra("username", donorUsername);
                startActivity(i);
            }
        });

        Button logout = findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        radioGroup = findViewById(R.id.radiogroup);
        arr = DataSource.getClaimsByDonor(donorUsername);

        if (arr.length() > 0) {
            noMessageText.setVisibility(View.GONE);

            addRadioButtons();
        } else {
            noMessageText.setVisibility(View.VISIBLE);
        }


    }

    public void addRadioButtons() {
        radioGroup.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < arr.length(); i++) {
            try {
                JSONObject json = (JSONObject) arr.get(i);


                String description = json.getString("claimMessage");
                String id = json.getString("_id");
                String obtainerUsername = json.getString("obtainerUsername");
                JSONObject user = DataSource.getAccountInfo(obtainerUsername);
                String firstName = user.getString("firstName");
                String org = user.getString("organization");

                RadioButton rdbtn = new RadioButton(this);
                rdbtn.setId(View.generateViewId());
                rdbtn.setPadding(30, 15, 20, 30);
                rdbtn.setFontFeatureSettings("");
                rdbtn.setText(firstName + " from " + org + " says: \n" + description);
                rdbtn.setTag(id);
                rdbtn.setTextSize(20);
                rdbtn.setTypeface(Typeface.SERIF);


                View v = new View(this);
                v.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, 1));
                v.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                radioGroup.addView(v);

                rdbtn.setOnClickListener(this);

                radioGroup.addView(rdbtn);

            } catch (Exception e) {
                System.out.println("oops");
            }

        }

    }

    public static final int MessageResponseActivity_ID = 1;


    @Override
    public void onClick(View v) {
        View selectButton = findViewById(R.id.select_post);
        selectButton.setVisibility(View.VISIBLE);
    }

    public void onSelectButtonClick(View v) {
        Button selected = findViewById(radioGroup.getCheckedRadioButtonId());
        String message = (String) selected.getText();
        String claimId = selected.getTag().toString();
        Intent i = new Intent(this, MessageResponseActivity.class);

        i.putExtra("CLAIMID", claimId);
        i.putExtra("DESCRIPTION", message);
        i.putExtra("username", donorUsername);
        startActivityForResult(i, MessageResponseActivity_ID);

    }

}