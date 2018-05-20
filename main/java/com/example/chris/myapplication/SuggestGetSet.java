package com.example.chris.myapplication;

/**
 * Created by Chris on 20/05/2018.
 */
public class SuggestGetSet {

  String cat_id,cat_name;
  public SuggestGetSet(String cat_id, String cat_name){
    this.setCat_id(cat_id);
    this.setCat_name(cat_name);
  }
  public String getCat_id() {
    //return the value of the JSON key named cat_id in php file
    return cat_id;
  }

  public void setCat_id(String id) {
    this.cat_id = id;
  }

  public String getCat_name() {
    //return the value of the JSON key named cat_name in php file
    return cat_name;
  }

  public void setCat_name(String name) {
    this.cat_name = name;
  }

}