package edu.upenn.cis350.cis350finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import edu.upenn.cis350.cis350finalproject.data.DataSource;


import edu.upenn.cis350.cis350finalproject.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

//    private RecyclerView recyclerView;
//    private RAdapter mAdapter;
//    private RecyclerView.LayoutManager layoutManager;

    MapView mapView;
    GoogleMap map;
    SearchView sv;
    ListView lv;
    CustomAdapter ca;

    public void checkPermission(String permission, int requestCode)
    {

        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                MainActivity.this,
                permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat
                    .requestPermissions(
                            MainActivity.this,
                            new String[] { permission },
                            requestCode);
        }
        else {
            Toast
                    .makeText(MainActivity.this,
                            "Permission already granted",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

    

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
        // DataSource.getTest();
        return false;
    }



    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1337);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

        //get username passed to intent
        final String username = getIntent().getStringExtra("username");



        ListView listView = (ListView) findViewById(R.id.listView);
        lv = listView;
        sv = (SearchView) findViewById(R.id.searchbar);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                Context context = getApplicationContext();
                CharSequence text = item;
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                ca.addItem("NewPost");
                // When clicked perform some action...
            }
        });

        CustomAdapter cAdapter = new CustomAdapter(this);
        ca = cAdapter;
        listView.setAdapter(ca);

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

        ImageButton createPost = findViewById(R.id.create_post);
        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreatePostActivity.class);
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


    public static final int CLAIMPOSTACTIVITY_ID = 1;

    public void onClaimPostButtonClick(View view) {
        Intent i = new Intent(this, ClaimPostActivity.class);

//          Intent i = new Intent(this, MessageBoardActivity.class);



//        DO STUFF HERE TO PUT THIS IN DB AS DUMMY
        User me = new User("Paula", "Hann", "Phoenix",
                "Donor", "yikes", "on bikes", "11111111",
                "hann@seas.upenn.edu", "Food4Us");
        Post p = new Post("Yes I am giving away tasty foods.", "Philadelphia",
                new Date(), me, "khann22@seas.upenn.edu", false, "");
        i.putExtra("POST", p);

        DataSource.createFullUser(me);
        DataSource.createPost(p);
        startActivityForResult(i, CLAIMPOSTACTIVITY_ID);

    }
}
