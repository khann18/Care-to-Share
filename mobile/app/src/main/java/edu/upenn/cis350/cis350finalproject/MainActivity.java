package edu.upenn.cis350.cis350finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
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
        DataSource.getTest();
        return false;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView listView = (ListView) findViewById(R.id.listView);
        lv = listView;
        sv = (SearchView) findViewById(R.id.searchbar);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                CharSequence text = "Hello toast!";
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
    }


}
