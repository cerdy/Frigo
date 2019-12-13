package com.github.loic.frigo.controler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.loic.frigo.model.Recette;
import com.github.loic.frigo.view.RecetteActivity;
import com.github.loic.frigo.view.FragmentListIngredient;
import com.github.loic.frigo.R;

import java.util.ArrayList;

public class ListRecetteAdapter extends RecyclerView.Adapter<ListRecetteAdapter.MyViewHolder> {
    private ArrayList<Recette> recettes;
    private RecetteActivity recetteActivity;
    public ListRecetteAdapter(ArrayList<Recette> recettes,RecetteActivity recetteActivity){
        this.recetteActivity = recetteActivity;
        this.recettes = recettes;
    }
    public void setPositionVisible(Integer position){
        this.recetteActivity.getRecyclerView().getLayoutManager().scrollToPosition(position);
    }
    // Function need by Adapter
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_recette_activity,parent,false));
    }
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.display(this.recettes.get(position).getTitle(),this.recettes.get(position).getDescription(),this.recettes.get(position).getIngredients());
    }
    public int getItemCount() {
        return this.recettes.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title,description;
        private FragmentManager fragmentManager;
        private ArrayList<String> ingredient;
        private MyViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.recyclerView_recetteActivity_title);
            this.description = itemView.findViewById(R.id.recyclerView_recetteActivity_recette);
            itemView.setOnClickListener(v -> {
                setPositionVisible(getAdapterPosition());
                FragmentListIngredient fragment = new FragmentListIngredient(ingredient);
                this.fragmentManager.beginTransaction().replace(R.id.recyclerView_recetteActivity_fragment,fragment).commit();
            });
            this.fragmentManager = ((FragmentActivity)itemView.getContext()).getSupportFragmentManager();
        }
        private void display(String title,String description,ArrayList<String> ingredient){
            this.title.setText(title);
            this.description.setText(description);
            this.ingredient = ingredient;
            FragmentListIngredient fragment = new FragmentListIngredient(this.ingredient);
            this.fragmentManager.beginTransaction().replace(R.id.recyclerView_recetteActivity_fragment,fragment).commit();
            Log.i("TESTE","title:"+title+"\nIngredient:"+ingredient.toString()+"\nSize:"+ingredient.size());
        }
    }
}