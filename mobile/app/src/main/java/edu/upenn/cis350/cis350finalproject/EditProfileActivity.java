package edu.upenn.cis350.cis350finalproject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import javax.xml.datatype.Duration;

import edu.upenn.cis350.cis350finalproject.data.DataSource;
import edu.upenn.cis350.cis350finalproject.ui.login.LoginActivity;

public class EditProfileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final String username = getIntent().getStringExtra("username");
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_edit_profile);

        Spinner spin = (Spinner) findViewById(R.id.input_account_type);
        final String[] account_type = { "Obtainer", "Donor"};
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,account_type);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        JSONObject j = DataSource.getAccountInfo(username);

        if (j != null) {
            try {
                ((EditText) findViewById(R.id.firstName)).setText(j.get("firstName").toString());
                ((EditText) findViewById(R.id.lastName)).setText(j.get("lastName").toString());
                ((EditText) findViewById(R.id.input_username)).setText(j.get("username").toString());
                ((EditText) findViewById(R.id.input_username)).setInputType(InputType.TYPE_NULL);
                ((EditText) findViewById(R.id.input_password)).setText(j.get("password").toString());
                ((EditText) findViewById(R.id.input_phone_number)).setText(j.get("phoneNumber").toString());
                ((EditText) findViewById(R.id.input_email)).setText(j.get("email").toString());
                ((EditText) findViewById(R.id.input_organization)).setText(j.get("organization").toString());
                ((EditText) findViewById(R.id.input_location)).setText(j.get("location").toString());
                String profPic = j.get("profilePic").toString();
                Log.d("profPIC", profPic);
                setProfilePicture(profPic);
                Spinner accountTypeSpinner = (Spinner) findViewById(R.id.input_account_type);
                ((EditText) findViewById(R.id.input_profile_pic)).setText(j.get("profilePic").toString());

                String accountType = j.get("userType").toString();
                if (accountType.equals("Obtainer")) {
                    accountTypeSpinner.setSelection(0);
                } else {
                    accountTypeSpinner.setSelection(1);
                }

            } catch (JSONException e) {
                Log.d("RESULT", "COULDN'T LOAD USER DATA");
            }
        }





        //click listener for delete account button
        Button delete = findViewById(R.id.delete_account_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataSource.deleteAccount(username);

                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.putExtra("toast", "Account successfully deleted");
                startActivity(i);


            }
        });
        //click listener for searching profiles
        ImageButton search = (ImageButton) findViewById(R.id.search_profiles);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SearchProfilesActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });




        //click listener for save changes button
        Button b = findViewById(R.id.save_changes_button);
        b.setOnClickListener(new View.OnClickListener() {
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


                //make sure all fields have been filled in
                if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()
                        || location.isEmpty() || organization.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill out all of the fields", Toast.LENGTH_SHORT).show();
                }

                else {
                    DataSource.updateAccount(firstName, lastName, username, password, email, phoneNumber, accountType, location, organization, profilePic);

                    Intent i = null;
                    if (accountType.equals("Obtainer")) {
                        i = new Intent(getApplicationContext(), MainActivity.class);
                    } else {
                        i = new Intent(getApplicationContext(), MessageBoardActivity.class);
                    }

                    //pass username to the home activity
                    i.putExtra("username", username);
                    startActivity(i);
                }
            }
        });

    }

    void setProfilePicture(String imageURL) {
        ImageView profPic = (ImageView) findViewById(R.id.profilePic);
        LoadImageTask task = new LoadImageTask();
        try {
            URL url = new URL(imageURL);
            task.execute(url);
            Object result = task.get();
            if (result != null) {
                profPic.setImageDrawable((Drawable) result);
            } else {
                profPic.setImageResource(android.R.drawable.ic_menu_camera);
            }
        } catch (Exception e) {
            Log.d("No Real URL", "No Real URL");
            profPic.setImageResource(android.R.drawable.ic_menu_camera);
        }

    }
}