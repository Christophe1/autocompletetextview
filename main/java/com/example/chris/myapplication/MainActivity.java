package com.example.chris.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

  private AutoCompleteTextView userName;

  private String jsonString = "{\n" +
      "\t\"results\": [{\n" +
      "\t\t\t\"id\": \"1\",\n" +
      "\t\t\t\"name\": \"stocktaker\"\n" +
      "\t\t}, {\n" +
      "\t\t\t\"id\": \"2\",\n" +
      "\t\t\t\"name\": \"stoolmaker\"\n" +
      "\t\t}, {\n" +
      "\t\t\t\"id\": \"3\",\n" +
      "\t\t\t\"name\": \"statesman\"\n" +
      "\t\t}, {\n" +
      "\t\t\t\"id\": \"4\",\n" +
      "\t\t\t\"name\": \"abacus maker\"\n" +
      "\t\t},\n" +
      "\t\t{\n" +
      "\t\t\t\"id\": \"5\",\n" +
      "\t\t\t\"name\": \"artist\"\n" +
      "\t\t}, {\n" +
      "\t\t\t\"id\": \"6\",\n" +
      "\t\t\t\"name\": \"airplane repairman\"\n" +
      "\t\t}, {\n" +
      "\t\t\t\"id\": \"7\",\n" +
      "\t\t\t\"name\": \"architect\"\n" +
      "\t\t}\n" +
      "\t]\n" +
      "}";

  private ArrayList<User> userList;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initView();

    //extract the individual details in jsonString
    userList = extractUser(jsonString);

    //context, layout, list of users
    UserAdapter userAdapter = new UserAdapter(this, R.layout.user_raw_layout, userList);

    userName.setAdapter(userAdapter);
    userName.setThreshold(1);
    //userName.setHint("Country");

  }

  private void initView() {

    userName = (AutoCompleteTextView) findViewById(R.id.user_name);

    userList = new ArrayList<>();



  }

  //extract the individual details in jsonString
  private ArrayList<User> extractUser(String jsonString) {

    ArrayList<User> list = new ArrayList<>();

    try {
      JSONObject rootJO = new JSONObject(jsonString);

      //break down the array in the JSON object
      JSONArray userJA = rootJO.getJSONArray("results");
      Toast.makeText(MainActivity.this, userJA.toString(), Toast.LENGTH_LONG).show();


      for (int i = 0; i < userJA.length(); i++) {

        JSONObject jo = userJA.getJSONObject(i);

        int id = jo.getInt("id");
        String name = jo.getString("name");

        //make a new user object
        User user = new User(id, name);

        //add the object to the list array
        list.add(user);
      }

    } catch (JSONException e) {
      e.printStackTrace();
    }

    return list;
  }


}





















