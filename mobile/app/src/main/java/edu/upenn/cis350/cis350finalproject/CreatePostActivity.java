package edu.upenn.cis350.cis350finalproject;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import edu.upenn.cis350.cis350finalproject.MainActivity;
import edu.upenn.cis350.cis350finalproject.R;
import edu.upenn.cis350.cis350finalproject.data.DataSource;

public class CreatePostActivity extends AppCompatActivity {

    private static ArrayList<String> foods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post);
        foods = new ArrayList<String>();

        final Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = ((EditText) findViewById(R.id.description)).getText().toString();
                String contact = ((EditText) findViewById(R.id.contact)).getText().toString();
                String location = ((EditText) findViewById(R.id.location)).getText().toString();
                String numPortions = ((EditText) findViewById(R.id.numPortions)).getText().toString();

                if (description.isEmpty() || contact.isEmpty() || location.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill out all of the fields", Toast.LENGTH_SHORT).show();
                } else {

                    String marked = "user";
                    String poster = getIntent().getStringExtra("username");
                    String [] bannedKeyWords = {"damn", "fuck", "drug", "drugs", "hell"};

                    for (String b: bannedKeyWords) {
                        if (description.contains(b)){
                            marked = "admin";
                        }
                    }

                    DataSource.createPost(description, contact, location, marked, poster, numPortions, foods);

                    if (marked.equals("admin")){
                        Toast.makeText(getApplicationContext(), "Post needs admin approval", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Successfully created post", Toast.LENGTH_SHORT).show();
                    }

                    new AsyncTask<String, String, String>() {

                        protected String doInBackground(String... inputs) {
                            try {
                                Thread.sleep(2000);
                            } catch (Exception e) {

                            }
                            return null;
                        }

                        protected void onPostExecute(String input) {
//                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                            startActivity(i);
                            finish();
                        }

                    }.execute();
                }


            }
        });

        Button discard = findViewById(R.id.discard_button);
        discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(i);
                finish();
            }
        });


    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        foods.add((String)((CheckBox) view).getText());
    }


}
