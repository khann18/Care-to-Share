package edu.upenn.cis350.cis350finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
//    private RecyclerView recyclerView;
//    private RAdapter mAdapter;
//    private RecyclerView.LayoutManager layoutManager;

    MapView mapView;
    GoogleMap map;
    SearchView sv;
    ListView lv;
    CustomAdapter ca;


    private void setupSearchView()
    {
        sv.setIconifiedByDefault(false);
        sv.setOnQueryTextListener(this);
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

//    public boolean onQueryTextChange(String newText) {
//
//        if (TextUtils.isEmpty(newText)) {
//            ca.getFilter().filter("");
//        } else {
//            ca.getFilter().filter(newText.toString());
//        }
//        return true;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // recyclerView = (RecyclerView) findViewById(R.id.recycler);
        setContentView(R.layout.activity_main);

        // data to populate the RecyclerView with
//        ArrayList<String> animalNames = new ArrayList<>();
//        animalNames.add("Horse");
//        animalNames.add("Cow");
//        animalNames.add("Camel");
//        animalNames.add("Sheep");
//        animalNames.add("Goat");

        ListView listView = (ListView) findViewById(R.id.listView);
        lv = listView;
        sv = (SearchView) findViewById(R.id.searchbar);

        CustomAdapter cAdapter = new CustomAdapter(this);
        ca = cAdapter;
        listView.setAdapter(ca);

        lv.setTextFilterEnabled(true);
        setupSearchView();



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

}
