package edu.upenn.cis350.cis350finalproject;

import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class MessageBoardActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loadBtn;
    private String TAG = "TestActivity";
    private RadioGroup radioGroup;
    private String donorUsername;
    private JSONArray arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_message_board);
        Bundle bundle = getIntent().getExtras();
        donorUsername = bundle.getString("USER");
        loadBtn = findViewById(R.id.load_more);
        radioGroup = findViewById(R.id.radiogroup);
        arr = DataSource.getPosts();
        addRadioButtons(10);


        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = 5;
                addRadioButtons(number);
            }
        });
    }

    public void addRadioButtons(int number) {
        radioGroup.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < arr.length() && i < number; i++) {
            try {
                JSONObject json = (JSONObject) arr.get(i);
                String description = json.getString("description");
                String id = json.getString("_id");
                RadioButton rdbtn = new RadioButton(this);
//                rdbtn.setId(id);
                rdbtn.setText("Radio " + rdbtn.getId());
                rdbtn.setOnClickListener(this);
                radioGroup.addView(rdbtn);
            } catch (Exception e) {
                break;
            }

        }
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, " Name " + ((RadioButton) v).getText() + " Id is " + v.getId());
    }

    public void onSelectButtonClick(View v) {
        Button selected = findViewById(radioGroup.getCheckedRadioButtonId());
        String message = (String) selected.getText();
    }
}