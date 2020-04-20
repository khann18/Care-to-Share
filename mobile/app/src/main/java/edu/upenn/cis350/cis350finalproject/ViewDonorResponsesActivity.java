//package edu.upenn.cis350.cis350finalproject;
//
//import android.content.Intent;
//import android.graphics.Typeface;
//import android.os.Bundle;
//import android.util.Log;
//import android.util.LogPrinter;
//import android.view.Gravity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
//import edu.upenn.cis350.cis350finalproject.ui.login.LoginActivity;
//
//public class ViewDonorResponsesActivity extends AppCompatActivity implements View.OnClickListener {
//    private ListView list;
//    private String donorUsername;
//    private JSONArray arr;
//    private ArrayList<String> arrList = new ArrayList<>();
//    private ArrayAdapter<String> adapter;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.view_donor_responses);
//        Bundle bundle = getIntent().getExtras();
//        donorUsername = bundle.getString("username");
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrList);
//
//        TextView noMessageText = (TextView) findViewById(R.id.noMessages);
//
//
//        list = findViewById(R.id.list);
//        list.setAdapter(adapter);
//
//        arr = DataSource.getClaimsByDonor(donorUsername);
//
//        if (arr.length() > 0) {
//            noMessageText.setVisibility(View.GONE);
//
//            addRadioButtons();
//        } else {
//            noMessageText.setVisibility(View.VISIBLE);
//        }
//
//
//    }
//
//    public void addRadioButtons() {
////        radioGroup.setOrientation(LinearLayout.VERTICAL);
//        for (int i = 0; i < arr.length(); i++) {
//            String description = "";
//            String id = "";
//            String firstName = "";
//            String org = "";
//            try {
//                JSONObject json = (JSONObject) arr.get(i);
//
//                description = json.getString("claimMessage");
//                id = json.getString("_id");
//                String donorUsername = json.getString("donorUsername");
//                JSONObject user = DataSource.getAccountInfo(donorUsername);
//                firstName = user.getString("firstName");
//                org = user.getString("organization");
//
//            } catch (Exception e) {
//                TextView noMessageText = (TextView) findViewById(R.id.noMessages);
//                noMessageText.setVisibility(View.VISIBLE);
//            }
//            String text = firstName + " from " + org + " says: \n" + description;
//            arrList.add(text);
//            adapter.notifyDataSetChanged();
//
////            TextView add = new TextView(this);
////            add.setId(View.generateViewId());
////            add.setPadding(30, 15, 20, 30);
////            add.setFontFeatureSettings("");
////
////            add.setText(text);
////            add.setTag(id);
////            add.setTextSize(20);
////            add.setClickable(true);
//
////            //Dividing lines
////            View v = new View(this);
////            v.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, 1));
////            v.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
////            list.addView(v);
//
////            add.setOnClickListener(this);
//
////            list.addView(add);
//
//        }
//
//    }
//
//    public static final int MessageResponseActivity_ID = 1;
//
//
//    @Override
//    public void onClick(View v) {
//        TextView curr = (TextView) v;
//        String message = (String) curr.getText();
//        String claimId = curr.getTag().toString();
//        Intent i = new Intent(this, MessageResponseActivity.class);
//
//        i.putExtra("CLAIMID", claimId);
//        i.putExtra("DESCRIPTION", message);
//        i.putExtra("username", donorUsername);
//        startActivityForResult(i, MessageResponseActivity_ID);
//    }
//
//
//    abstract class MyAdapter extends BaseAdapter {
//        // ...
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            // ...
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = (Integer) view.getTag();
//                    // Access the row position here to get the correct data item
//                    String item = (String) getItem(position);
//                    String message = item;
//                    String claimId = "id";
//                    Intent i = new Intent(this, MessageResponseActivity.class);
//
//                    i.putExtra("CLAIMID", claimId);
//                    i.putExtra("DESCRIPTION", message);
//                    i.putExtra("username", donorUsername);
//                    startActivityForResult(i, MessageResponseActivity_ID);
//                }
//            });
//            // ... other view population as needed...
//            // Return the completed view
//            return convertView;
//        }
//    }
//
//}


package edu.upenn.cis350.cis350finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.upenn.cis350.cis350finalproject.ui.login.LoginActivity;

public class ViewDonorResponsesActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup radioGroup;
    private String obtainerUsername;
    private JSONArray arr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_donor_responses);
        Bundle bundle = getIntent().getExtras();
        obtainerUsername = bundle.getString("username");
        TextView noMessageText = (TextView) findViewById(R.id.noMessages);

        radioGroup = findViewById(R.id.radiogroup);
        arr = DataSource.getClaimsByObtainer(obtainerUsername);

        if (arr.length() > 0) {
            noMessageText.setVisibility(View.GONE);

            addRadioButtons();
        } else {
            noMessageText.setVisibility(View.VISIBLE);
        }


    }

    public void addRadioButtons() {
        radioGroup.setOrientation(LinearLayout.VERTICAL);
        Log.d("ARRAY LENGTH", Integer.toString(arr.length()));
        for (int i = 0; i < arr.length(); i++) {
            try {
                JSONObject json = (JSONObject) arr.get(i);



                String description = json.getString("claimMessage");
                String status = json.getString("claimStatus");
                if (status.equals("none")) {
                    status = "Waiting for response";
                }
                String id = json.getString("_id");
                String donorUsername = json.getString("donorUsername");
                JSONObject user = DataSource.getAccountInfo(donorUsername);
                String org = user.getString("organization");

                Log.d("MADE HERE", user.toString());

                RadioButton rdbtn = new RadioButton(this);
                rdbtn.setId(View.generateViewId());
                rdbtn.setPadding(30, 15, 20, 30);
                rdbtn.setFontFeatureSettings("");
                rdbtn.setText("Organization: " + org + "\nStatus: " + status);
                rdbtn.setTag(id);
                rdbtn.setTextSize(20);


                View v = new View(this);
                v.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, 1));
                v.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                radioGroup.addView(v);

                rdbtn.setOnClickListener(this);

                radioGroup.addView(rdbtn);

            } catch (Exception e) {
                System.out.println("oops");
            }

        }

    }

    public static final int ViewAcceptOrRejectActivity_ID = 1;


    @Override
    public void onClick(View v) {
        Button selected = findViewById(radioGroup.getCheckedRadioButtonId());
        String message = (String) selected.getText();
        String claimId = selected.getTag().toString();
        Intent i = new Intent(this, ViewAcceptOrRejectActivity.class);

        i.putExtra("CLAIMID", claimId);
        i.putExtra("DESCRIPTION", message);
        i.putExtra("username", obtainerUsername);
        startActivityForResult(i, ViewAcceptOrRejectActivity_ID);
    }



}