package edu.upenn.cis350.cis350finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import org.json.JSONObject;

public class SearchProfilesActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    SearchView sv;
    ListView lv;
    SearchUsersAdapter ca;
    String username;

    @SuppressLint("NewApi")
    private void setupSearchView()
    {
        sv.setIconifiedByDefault(false);
        sv.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        sv.setSubmitButtonEnabled(true);
        sv.setQueryHint("Search for users...");
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_profiles);

        lv =  (ListView) findViewById(R.id.list_view);
        sv = (SearchView) findViewById(R.id.search_bar);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                JSONObject jItem = (JSONObject) ca.getItem(position);


                try {
                    String firstName = jItem.getString("firstName");
                    String lastName = jItem.getString("lastName");
                    String username = jItem.getString("username");
                    String phoneNumber = jItem.getString("phoneNumber");
                    String email = jItem.getString("email");
                    String organization = jItem.getString("organization");
                    String location = jItem.getString("location");
                    String userType = jItem.getString("userType");
                    String profilePic = "";
                    if (jItem.has("profilePic")) {
                        profilePic = jItem.getString("profilePic");
                    }
                    String accountType = jItem.getString("userType");

                    Intent myIntent = new Intent(SearchProfilesActivity.this, ViewProfileActivity.class);
                    //These are the parameters passed to ViewProfileActivity when a user is clicked
                    myIntent.putExtra("username", username);
                    myIntent.putExtra("firstName", firstName);
                    myIntent.putExtra("lastName", lastName);
                    myIntent.putExtra("phoneNumber", phoneNumber);
                    myIntent.putExtra("email", email);
                    myIntent.putExtra("organization", organization);
                    myIntent.putExtra("location", location);
                    myIntent.putExtra("profilePic", profilePic);
                    myIntent.putExtra("userType", userType);
                    SearchProfilesActivity.this.startActivity(myIntent);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        SearchUsersAdapter cAdapter = new SearchUsersAdapter(this);
        ca = cAdapter;
        lv.setAdapter(ca);

        lv.setTextFilterEnabled(true);
        setupSearchView();



    }
}