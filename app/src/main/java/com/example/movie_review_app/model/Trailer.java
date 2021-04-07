package com.example.movie_review_app.model;

import com.google.gson.annotations.SerializedName;

public class Trailer {

    @SerializedName("key") //it should be key
    private String Key;
    @SerializedName("name")
    private String name;

    public Trailer(String key, String name){
        this.Key = key;
        this.name = name;
    }

    public String getKey(){
        return Key;
    }
    public void setKey(String Key){
        this.Key = Key;
    }
    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
