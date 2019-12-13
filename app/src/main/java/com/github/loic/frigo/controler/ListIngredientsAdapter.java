package com.github.loic.frigo.controler;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.loic.frigo.R;

import java.util.ArrayList;
import java.util.Collections;

public class ListIngredientsAdapter extends RecyclerView.Adapter<ListIngredientsAdapter.MyViewHolder> {
    private ArrayList<String> arrayList;
    private Integer color;
    private boolean clickable;
    public ListIngredientsAdapter(boolean clickable){
        this.color = Color.BLACK;
        this.arrayList = new ArrayList<>();
        this.clickable = clickable;
    }
    public ListIngredientsAdapter(ArrayList<String> arrayList,boolean clickable){
        this.color = Color.BLACK;
        this.arrayList = arrayList;
        this.clickable = clickable;
    }
    public void setColor(Integer color) {
        this.color = color;
    }
    // Add new Element of ingredient
    public void addIngredient(String ingredient){
        if(!this.arrayList.contains(ingredient)){
            this.arrayList.add(ingredient);
        }
        Collections.sort(this.arrayList);
    }
    public void deleteAll() {
        this.arrayList = new ArrayList<>();
        notifyDataSetChanged();
    }
    public void changeIngredient(ArrayList<String> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }
    public ArrayList<String> getArrayList(){
        return this.arrayList;
    }
    // Function need by Adapter
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_home_activity,parent,false));
    }
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.display(this.arrayList.get(position));
    }
    public int getItemCount() {
        return this.arrayList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private MyViewHolder(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.recyclerView_homeActivity_textView);
            if(clickable){
                itemView.setOnClickListener(v -> {
                    arrayList.remove(getAdapterPosition());
                    notifyDataSetChanged();
                });
            }
        }
        private void display(String element){
            this.textView.setTextColor(color);
            textView.setText(element);
        }
    }
}
