package com.tutorialscache.android_json_parsing;


import android.util.Log;

public class MoviesModel {
    //variables
    int id;
    String name,image;

    public double getRatting() {
        return ratting;
    }

    public void setRatting(double ratting) {
        this.ratting = ratting;
    }

    double ratting;

    //constructor
    public MoviesModel(int id,String name,String image, double ratting){
        this.setId(id);
        this.setName(name);
        this.setImage(image);
        this.setRatting(ratting);
    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    @Override
    public String toString() {
        Log.d("response MODEL: ","ID: "+this.getId()+" Movie Name: "+this.getName()+" Ratting: "+this.getRatting()+" ");
        return super.toString();

    }
}
