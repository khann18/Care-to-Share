package edu.upenn.cis350.cis350finalproject.ui.login;

import android.content.Intent;
import android.os.Bundle;

//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import edu.upenn.cis350.cis350finalproject.MainActivity;
import edu.upenn.cis350.cis350finalproject.R;
import edu.upenn.cis350.cis350finalproject.data.DataSource;

public class CreateAccountActivity extends AppCompatActivity {


    String[] account_type = { "Obtainer", "Donor"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_create_account);

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
                String organization = ((EditText) findViewById(R.id.input_organization)).getText().toString();
                String location = ((EditText) findViewById(R.id.input_location)).getText().toString();
                String profilePic = ((EditText) findViewById(R.id.input_profile_pic)).getText().toString();

                Spinner accountTypeSpinner = (Spinner) findViewById(R.id.input_account_type);
                String accountType = accountTypeSpinner.getSelectedItem().toString();
                Log.d("account type", accountType);


                //make sure all fields have been filled in
                if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()
                || location.isEmpty() || organization.isEmpty() || accountType.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill out all of the fields", Toast.LENGTH_SHORT).show();
                }

                else if (DataSource.isUsernameTaken(username)) {
                    Toast.makeText(getApplicationContext(), "Username is already taken", Toast.LENGTH_SHORT).show();
                }

                else {
                    DataSource.createAccount(firstName, lastName, username, password, email, phoneNumber, accountType, location, organization, profilePic);

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    //pass username to the home activity
                    i.putExtra("username", username);
                    startActivity(i);
                }


            }
        });

        //set up spinner for account type
        Spinner spin = (Spinner) findViewById(R.id.input_account_type);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,account_type);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        //set up click listener for going back to login page
        TextView backToLogin = findViewById(R.id.link_login);
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });

    }

}
