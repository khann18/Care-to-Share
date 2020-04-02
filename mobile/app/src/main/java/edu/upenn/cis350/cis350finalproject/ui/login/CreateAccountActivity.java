package edu.upenn.cis350.cis350finalproject.ui.login;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import edu.upenn.cis350.cis350finalproject.MainActivity;
import edu.upenn.cis350.cis350finalproject.R;
import edu.upenn.cis350.cis350finalproject.data.DataSource;

public class CreateAccountActivity extends AppCompatActivity {


    String[] account_type = { "Obtainer", "Donor"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        final Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = ((EditText) findViewById(R.id.firstName)).getText().toString();
                String lastName = ((EditText) findViewById(R.id.lastName)).getText().toString();
                String username = ((EditText) findViewById(R.id.input_username)).getText().toString();
                String password = ((EditText) findViewById(R.id.input_password)).getText().toString();
                String phoneNumber = ((EditText) findViewById(R.id.input_phone_number)).getText().toString();
                String email = ((EditText) findViewById(R.id.input_email)).getText().toString();
                //get all fields and first check if they're all filled in or not

                if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill out all of the fields", Toast.LENGTH_SHORT).show();
                }

                else if (DataSource.isUsernameTaken(username)) {
                    Toast.makeText(getApplicationContext(), "Username is already taken", Toast.LENGTH_SHORT).show();
                }

                else {
                    DataSource.createAccount(firstName, lastName, username, password, email, phoneNumber);

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    //pass username to the home (main) activity
                    i.putExtra("username", username);
                    startActivity(i);
                }


            }
        });

        Spinner spin = (Spinner) findViewById(R.id.input_account_type);
//        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,account_type);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }

}
