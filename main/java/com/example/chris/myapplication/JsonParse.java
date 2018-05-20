package com.example.chris.myapplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 20/05/2018.
 */

public class JsonParse {
  double current_latitude,current_longitude;
  public JsonParse(){}
  public JsonParse(double current_latitude,double current_longitude){
    this.current_latitude=current_latitude;
    this.current_longitude=current_longitude;
  }
  public List<SuggestGetSet> getParseJsonWCF(String sName)
  {
    List<SuggestGetSet> ListData = new ArrayList<SuggestGetSet>();
    try {
      String temp=sName.replace(" ", "%20");
      URL js = new URL("http://populisto.com/suggestion.php?name="+temp);
      URLConnection jc = js.openConnection();
      BufferedReader reader = new BufferedReader(new InputStreamReader(jc.getInputStream()));
      String line = reader.readLine();
      System.out.println("jsonParse: " + line);
      JSONObject jsonResponse = new JSONObject(line);
      JSONArray jsonArray = jsonResponse.getJSONArray("results");
      for(int i = 0; i < jsonArray.length(); i++){
        JSONObject r = jsonArray.getJSONObject(i);
        ListData.add(new SuggestGetSet(r.getString("cat_id"),r.getString("cat_name")));
      }
    } catch (Exception e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();

    }
    return ListData;

  }

}
