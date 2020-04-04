package edu.upenn.cis350.cis350finalproject;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class CustomAdapter extends BaseAdapter implements Filterable
{

    Context context;
    String[] rooms;
    public ArrayList<String> stringList = new ArrayList<String>();
    public ArrayList<String> masterList = new ArrayList<String>();


    public CustomAdapter(Context c)
    {
        context = c;
        Resources res = c.getResources();

        // rooms = res.getStringArray(R.array.images);
        this.stringList = new ArrayList<String>();
        // rooms = new String[]{"Post1", "Post2", "Post3", "Post15", "Post2", "Post3", "Post1", "Post2", "Post3", "Post1", "Post2", "Post3",
         //       "Post1", "Post2", "Post3", "Post1", "Post2", "Post3", "Post1", "Post2", "Post3", "Post1", "Post2", "Post3"};
        stringList.add("Post1");
        stringList.add("Post12");
        stringList.add("Post3");

        masterList.add("Post1");
        masterList.add("Post2");
        masterList.add("Post3");

        stringList.add("Post1");
        stringList.add("Post2");
        stringList.add("Post3");

        masterList.add("Post1");
        masterList.add("Post2");
        masterList.add("Post3");

        stringList.add("Post1");
        stringList.add("Post2");
        stringList.add("Post3");

        masterList.add("Post1");
        masterList.add("Post2");
        masterList.add("Post3");
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

        return stringList.get(position);
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

//
//        public Filter getFilter() {
//            return new Filter() {
//
//                @Override
//                protected FilterResults performFiltering(CharSequence constraint) {
//                    final FilterResults oReturn = new FilterResults();
//                    final ArrayList<String> results = new ArrayList<String>();
//                    if (stringList == null)
//                        stringList = new ArrayList<String>();
//                    if (constraint != null) {
//                        if (stringList != null && stringList.size() > 0) {
//                            for (final String g : stringList) {
//                                if (g.toString().toLowerCase()
//                                        .contains(constraint.toString()))
//                                    results.add(g);
//                            }
//                        }
//                        oReturn.values = results;
//                    }
//                    return oReturn;
//                }
//
//                @SuppressWarnings("unchecked")
//                @Override
//                protected void publishResults(CharSequence constraint,
//                                              FilterResults results) {
//                    stringList = (ArrayList<String>) results.values;
//                    notifyDataSetChanged();
//                }
//            };
//        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =  inflater.inflate(R.layout.single_row,parent,false);
        TextView txt = (TextView) view.findViewById(R.id.firstLine);
        // String temp = rooms[position];
        if (position < stringList.size()) {
            String temp = stringList.get(position);
            txt.setText(temp);
        } else {
            txt.setText("");
        }
        return view;
    }



}