package edu.upenn.cis350.cis350finalproject;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import edu.upenn.cis350.cis350finalproject.data.DataSource;
import edu.upenn.cis350.cis350finalproject.ui.login.LoginActivity;

public class ViewProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        final String username = getIntent().getStringExtra("username");
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_view_profile);

        ((TextView) findViewById(R.id.profile_view_title)).setText(getIntent().getStringExtra("username"));

        ((TextView) findViewById(R.id.firstName)).setText(getIntent().getStringExtra("firstName") + " " + getIntent().getStringExtra("lastName"));
        ((TextView) findViewById(R.id.input_phone_number)).setText("Phone number: " +  getIntent().getStringExtra("phoneNumber"));
        ((TextView) findViewById(R.id.input_email)).setText("Email: " + getIntent().getStringExtra("email"));
        ((TextView) findViewById(R.id.input_organization)).setText("Organization: " + getIntent().getStringExtra("organization"));
        ((TextView) findViewById(R.id.input_location)).setText("Location: " + getIntent().getStringExtra("location"));
        ((TextView) findViewById(R.id.account_type)).setText(getIntent().getStringExtra("userType"));
        String profPic = getIntent().getStringExtra("profilePic");
        setProfilePicture(profPic);

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
