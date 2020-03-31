package edu.upenn.cis350.cis350finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import java.util.ArrayList;

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
    }

//    @Override
//    public void onItemClick(View view, int position) {
//        Toast.makeText(this, "You clicked " + mAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
//    }

    public static final int CLAIMPOSTACTIVITY_ID = 1;

    public void onLaunchButtonClick(View view) {
        Intent i = new Intent(this, ClaimPostActivity.class);
//        DO STUFF HERE TO PUT THIS IN DB AS DUMMY
//        User me = new User("Katherine", "Hann", "Phoenix",
//                "Donor", "khann22", "123", "6023205772",
//                "khann22@seas.upenn.edu", "Food4U");
//        Post p = new Post("Yes I am giving away tasty foods.", "Philadelphia", new Date(), me, "khann22@seas.upenn.edu");
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("POST", p);
//
//        try {
//            URL url = new URL("http://10.0.2.2:3000/createaccount?username=" + "khann22");
//            AsyncTask<URL, String, String> task = new AccessWebTask();
//            task.execute(url);
//            String name = task.get();
//        } catch (Exception e) {
//            //do nothing???
//        }
        startActivityForResult(i, CLAIMPOSTACTIVITY_ID);

    }
}
