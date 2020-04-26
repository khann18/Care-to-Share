package edu.upenn.cis350.cis350finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import org.json.JSONObject;
import java.util.Date;
import edu.upenn.cis350.cis350finalproject.data.DataSource;


import edu.upenn.cis350.cis350finalproject.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView sv;
    ListView lv;
    CustomAdapter ca;
    String username;

    @SuppressLint("NewApi")
    private void setupSearchView()
    {
        sv.setIconifiedByDefault(false);
        sv.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        sv.setSubmitButtonEnabled(true);
        sv.setQueryHint("Search Here");
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            ca.getFilter().filter("");
            lv.clearTextFilter();
        } else {
            ca.getFilter().filter(newText.toString());
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }



    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //get username passed to intent
        username = getIntent().getStringExtra("username");

        lv =  (ListView) findViewById(R.id.listView);
        sv = (SearchView) findViewById(R.id.searchbar);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Position", "" + position);
                String item = parent.getItemAtPosition(position).toString();
                JSONObject jItem = (JSONObject) ca.getItem(position);

                String location;
                String contact;

                try {
                    String postId = jItem.getString("_id");
                    String description = jItem.getString("description");

                    Intent i = new Intent(MainActivity.this, ClaimPostActivity.class);
                    i.putExtra("postid", postId);
                    i.putExtra("description", description);
                    i.putExtra("username", username);
                    startActivity(i);
//                    MainActivity.this.startActivity(myIntent);

                } catch (Exception e) {
                    e.printStackTrace();
                }

//                Context context = getApplicationContext();
//                CharSequence text = item;
//                int duration = Toast.LENGTH_SHORT;

//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();
            }
        });

        CustomAdapter cAdapter = new CustomAdapter(this);
        ca = cAdapter;
        lv.setAdapter(ca);

        lv.setTextFilterEnabled(true);
        setupSearchView();

        ImageButton editProfile = findViewById(R.id.edit_profile_button);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EditProfileActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        ImageButton viewDonorResponses = findViewById(R.id.view_donor_responses);
        viewDonorResponses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ViewDonorResponsesActivity.class);
                i.putExtra("username", username);
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
    }

}