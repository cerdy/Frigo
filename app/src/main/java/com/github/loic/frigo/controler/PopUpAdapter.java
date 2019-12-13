package com.github.loic.frigo.controler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.loic.frigo.R;

import java.util.ArrayList;

public class PopUpAdapter extends RecyclerView.Adapter<PopUpAdapter.MyViewHolder>{
    ArrayList<String> elements;
    private OnMyItemClickListener listener;
    // Functions
    public PopUpAdapter(ArrayList<String> elements) {
        this.elements = elements;
    }
    public void setOnMyItemClickListener(OnMyItemClickListener listener) {
        this.listener = listener;
    }
    public String getElement(Integer key){
        return this.elements.get(key);
    }
    // Function needed
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_pop_up,parent,false));
    }
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.display(this.elements.get(position));
    }
    public int getItemCount() {
        return this.elements.size();
    }
    // Classes
    public interface OnMyItemClickListener {
        void click(String element);
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private MyViewHolder(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.recyclerView_popUp_textView);
            itemView.setOnClickListener(v -> listener.click(getElement(getAdapterPosition())));
        }
        private void display(String element){
            this.textView.setText(element);
        }
    }
}
