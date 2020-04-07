package edu.upenn.cis350.cis350finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class MessageBoardActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup radioGroup;
    private String donorUsername;
    private JSONArray arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_message_board);
        Bundle bundle = getIntent().getExtras();
        donorUsername = bundle.getString("USER");
        View selectButton = findViewById(R.id.select_post);
        selectButton.setVisibility(View.GONE);
        radioGroup = findViewById(R.id.radiogroup);
        arr = DataSource.getClaimsByDonor(donorUsername);


        addRadioButtons();


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
                rdbtn.setPadding(30, 40, 20, 30);
                rdbtn.setText(firstName + " from " + org + " says:\n" + description);
                rdbtn.setTag(id);
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
        startActivityForResult(i, MessageResponseActivity_ID);

    }
}