package com.example.chris.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.chris.myapplication.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

  public static final String KEY_PHONENUMBER_USER = "phonenumberofuser";

  private AutoCompleteTextView userName;

  private static final String jsonString = "http://www.populisto.com/suggestion.php";

  private ArrayList<User> userList;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    userName = (AutoCompleteTextView) findViewById(R.id.user_name);

    userList = new ArrayList<>();

    StringRequest request = new StringRequest(Request.Method.GET, jsonString,
        new Response.Listener<String>() {

          @Override
          public void onResponse(String response) {

            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

            try {

              //extract the individual details in jsonString
              userList = extractUser(response);

            } catch (Exception e) {
              e.printStackTrace();
            }

            Toast.makeText(getApplicationContext(), userList.toString(), Toast.LENGTH_LONG).show();

            //context, layout, list of users
            UserAdapter userAdapter = new UserAdapter(getApplicationContext(), R.layout.user_raw_layout, userList);

            userName.setAdapter(userAdapter);
            userName.setThreshold(1);

          }


        }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {

      }
    })

    {
      @Override
      //post info to php
      protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        //phoneNoofUser is the value we get from Android, the user's phonenumber.
        //the key,KEY_PHONENUMBER_USER, is "phonenumberofuser". When we see "phonenumberofuser" in our php,
        //put in phoneNoofUser
        params.put(KEY_PHONENUMBER_USER, "+353872934480");
        return params;

      }
    };


    AppController.getInstance().addToRequestQueue(request);
  }

  //extract the individual details in jsonString
  private ArrayList<User> extractUser(String response) {

    ArrayList<User> list = new ArrayList<>();

    try {
      JSONObject rootJO = new JSONObject(response);

      //break down the array in the JSON object
      JSONArray userJA = rootJO.getJSONArray("results");

      for (int i = 0; i < userJA.length(); i++) {

        JSONObject jo = userJA.getJSONObject(i);

        int id = jo.getInt("cat_id");
        String name = jo.getString("cat_name");

        //make a new user object
        User user = new User(id, name);

        //add the object to the list array
        list.add(user);

        Log.e("Parse JSON", "id: " + id + " name: " + name);
      }

      Toast.makeText(getApplicationContext(), userList.toString(), Toast.LENGTH_LONG).show();


    } catch (JSONException e) {
      e.printStackTrace();
    }

    return list;
  }


}
