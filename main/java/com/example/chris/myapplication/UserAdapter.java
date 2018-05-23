package com.example.chris.myapplication;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 20/05/2018.
 */

public class UserAdapter extends ArrayAdapter<User> {

  //userList is a breakdown of our jsonString
  private List<User> userList;
  private List<User> tempList;
  private List<User> suggestionList;

  public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {

    super(context, resource, objects);
    userList = objects;
    tempList = new ArrayList<>(userList);
    //when user types, get the data from tempList and filter it
    suggestionList = new ArrayList<>();
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

    if (convertView == null)
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_raw_layout, parent, false);

    TextView textView = (TextView) convertView.findViewById(R.id.simple_text);

    //make userList an object of User class
    User user = userList.get(position);

    textView.setText(user.getName());

    return convertView;
  }

  @NonNull
  @Override
  public Filter getFilter() {
    return userFilter;
  }

  Filter userFilter = new Filter() {

    @Override
    public CharSequence convertResultToString(Object resultValue) {

      //we want to convert the user value to a string
      User user = (User) resultValue;

      return user.getName();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

      FilterResults filterResults = new FilterResults();

      //contraint is the text being typed
      if (constraint != null && constraint.length() > 0) {

        //clear suggestionList if it exists already
        suggestionList.clear();
        //convert constraint to lower case
        constraint = constraint.toString().trim().toLowerCase();

        //for every user in our tempList
        for (User user : tempList) {

          //if the user name contains the text we have typed
          if (user.getName().toLowerCase().contains(constraint)) {
            //then add it to the suggestionList
            suggestionList.add(user);
          }
        }

        filterResults.count = suggestionList.size();
        filterResults.values = suggestionList;

      }
      return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

      ArrayList<User> uList = (ArrayList<User>) results.values;

      if (results != null && results.count > 0) {

        //clear the data
        clear();

        for (User u : uList) {

          add(u);
          notifyDataSetChanged();
        }
      }
    }

  };

}