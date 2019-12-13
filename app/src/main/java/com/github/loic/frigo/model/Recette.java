package com.github.loic.frigo.model;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Parcel;
import android.os.Parcelable;

public class Recette implements Parcelable {
    private  String key,title,source,source_type,description;
    private ArrayList<String> ingredients;
    public Recette(String key,String title,String source,String source_type,String description,ArrayList<String> ingredients){
        this.key = key;
        this.title = title;
        this.source = source;
        this.source_type = source_type;
        this.description = description;
        this.ingredients = ingredients;
    }
    public String getKey() { return key; }
    public String getTitle() { return title; }
    public String getSource() { return source; }
    public String getSource_type() { return source_type; }
    public String getDescription() { return description; }
    public ArrayList<String> getIngredients() { return ingredients; }
    // Parcelable
    protected Recette(Parcel in) { // L'ordre le lecture est important
        this.key = in.readString();
        this.title = in.readString();
        this.source = in.readString();
        this.source_type = in.readString();
        this.description = in.readString();
        this.ingredients = new ArrayList<>(Arrays.asList(in.createStringArray()));
    }
    public static final Creator<Recette> CREATOR = new Creator<Recette>() {
        public Recette createFromParcel(Parcel in) { return new Recette(in); }
        public Recette[] newArray(int size) { return new Recette[size]; }
    };
    public int describeContents() { return 0; }
    public void writeToParcel(Parcel dest, int flags) { // L'ordre de la lecture est important
        dest.writeString(this.key);
        dest.writeString(this.title);
        dest.writeString(this.source);
        dest.writeString(this.source_type);
        dest.writeString(this.description);
        dest.writeStringArray(this.ingredients.toArray(new String[this.ingredients.size()]));
    }
}
