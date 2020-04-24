package edu.upenn.cis350.cis350finalproject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.upenn.cis350.cis350finalproject.data.DataSource;
import java.util.ArrayList;
import java.util.List;

public class SearchUsersAdapter extends BaseAdapter implements Filterable
{

    Context context;
    public ArrayList<String> stringList = new ArrayList<String>();
    public ArrayList<JSONObject> jsonList = new ArrayList<JSONObject>();
    public ArrayList<String> masterList = new ArrayList<String>();
    public ArrayList<String> descriptionList = new ArrayList<String>();


    public SearchUsersAdapter(Context c)
    {
        JSONArray users = DataSource.getAllUsers();
        if (users != null) {
            for (int i = 0; i < users.length(); i++) {
                try {
                    JSONObject j = (JSONObject) users.get(i);
                    this.stringList.add((String) j.get("firstName") + " " + (String) j.get("lastName"));
                    this.masterList.add((String) j.get("firstName") + " " + (String) j.get("lastName"));
                    this.jsonList.add(j);
                    this.descriptionList.add((String) j.get("username"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }

        context = c;

    }

    public void addItem(String item) {

        masterList.add(item);
        stringList.add(item);
        this.notifyDataSetChanged();
    }


    @Override
    public int getCount() {

        return stringList.size();
    }

    @Override
    public Object getItem(int position) {
        Log.d("Position", "" + position);
        return jsonList.get(position);
    }

    public void notifyDataSetChanged() {

        super.notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public Filter getFilter(){
        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                constraint = constraint.toString().toLowerCase();
                FilterResults result = new FilterResults();

                if (constraint != null && constraint.toString().length() > 0) {
                    List<String> founded = new ArrayList<String>();

                    for (String item : masterList) {
                        if (item.toString().toLowerCase().contains(constraint)) {
                            founded.add(item);
                        }
                    }

                    result.values = founded;
                    result.count = founded.size();
                } else {
                    result.values = masterList;
                    result.count = masterList.size();
                }
                return result;


            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                stringList.clear();
                for (String item : (List<String>) results.values) {
                    stringList.add(item);
                }
                notifyDataSetChanged();
            }

        };
        return filter;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =  inflater.inflate(R.layout.single_row,parent,false);
        TextView description = (TextView) view.findViewById(R.id.firstLine);
        description.setAllCaps(true);
        description.setTypeface(null, Typeface.BOLD);
        TextView extraInfo = (TextView) view.findViewById(R.id.secondLine);

        if (position < stringList.size()) {
            String temp = stringList.get(position);
            description.setText(temp);
            JSONObject curr = jsonList.get(position);
            try {
                //Extra text displayed with each user
                extraInfo.setText("Location: " + curr.getString("location"));
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            description.setText("");
            extraInfo.setText("");
        }
        return view;
    }



}