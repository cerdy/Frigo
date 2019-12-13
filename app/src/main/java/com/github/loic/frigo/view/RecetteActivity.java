package com.github.loic.frigo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.github.loic.frigo.controler.ListRecetteAdapter;
import com.github.loic.frigo.model.Recette;
import com.github.loic.frigo.R;

import java.util.ArrayList;

public class RecetteActivity extends AppCompatActivity {
    private ArrayList<Recette> recettes;
    private RecyclerView recyclerView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recette);

        this.recettes = getIntent().getParcelableArrayListExtra("recette");
        this.recyclerView = findViewById(R.id.activity_recette_recyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        this.recyclerView.setAdapter(new ListRecetteAdapter(this.recettes,this));
    }
    public RecyclerView getRecyclerView() {
        return this.recyclerView;
    }
}
