package com.learn.ranjitsihota.popularmovies;

public class Favorite {
    int _id;
    public Favorite(){

    }
    // constructor
    public Favorite(int id){
        this._id = id;

    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

}
