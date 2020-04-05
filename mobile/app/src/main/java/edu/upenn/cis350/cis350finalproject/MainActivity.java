package edu.upenn.cis350.cis350finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import java.util.ArrayList;

import edu.upenn.cis350.cis350finalproject.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity  {
//    private RecyclerView recyclerView;
//    private RAdapter mAdapter;
//    private RecyclerView.LayoutManager layoutManager;

    MapView mapView;
    GoogleMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // recyclerView = (RecyclerView) findViewById(R.id.recycler);
        setContentView(R.layout.activity_main);

        //get username passed to intent
        final String username = getIntent().getStringExtra("username");

        // data to populate the RecyclerView with
        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");
        animalNames.add("Sheep");
        animalNames.add("Goat");

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new CustomAdapter(this));
        // set up the RecyclerView
//        RecyclerView recyclerView = findViewById(R.id.recycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mAdapter = new RAdapter(this, animalNames);
//        mAdapter.setClickListener(this);
//        recyclerView.setAdapter(mAdapter);

        ImageButton editProfile = findViewById(R.id.edit_profile_button);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EditProfileActivity.class);
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

//    @Override
//    public void onItemClick(View view, int position) {
//        Toast.makeText(this, "You clicked " + mAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
//    }

}
