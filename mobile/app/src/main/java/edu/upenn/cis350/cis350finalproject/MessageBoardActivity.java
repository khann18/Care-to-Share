package edu.upenn.cis350.cis350finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class MessageBoardActivity extends AppCompatActivity implements View.OnClickListener {

    Button loadBtn;
    String TAG = "TestActivity";
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_message_board);

        loadBtn = findViewById(R.id.load_more);
        radioGroup = findViewById(R.id.radiogroup);
        addRadioButtons(10);

        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = 10;
                addRadioButtons(number);
            }
        });
    }

    public void addRadioButtons(int number) {
        radioGroup.setOrientation(LinearLayout.VERTICAL);
        //
        for (int i = 1; i <= number; i++) {
            RadioButton rdbtn = new RadioButton(this);
            rdbtn.setId(View.generateViewId());
            rdbtn.setText("Radio " + rdbtn.getId());
            rdbtn.setOnClickListener(this);
            radioGroup.addView(rdbtn);
        }
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, " Name " + ((RadioButton) v).getText() + " Id is " + v.getId());
    }
}