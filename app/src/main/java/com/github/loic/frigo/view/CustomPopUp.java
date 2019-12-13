package com.github.loic.frigo.view;

import android.app.Dialog;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.loic.frigo.R;
import com.github.loic.frigo.controler.PopUpAdapter;

import java.util.ArrayList;

public class CustomPopUp extends Dialog {
    private RecyclerView recyclerView;
    public CustomPopUp(HomeActivity activity){
        super(activity,R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.ingredient_pop_up);
        this.recyclerView = findViewById(R.id.popUp_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        this.recyclerView.setLayoutManager(layoutManager);
        Button button = findViewById(R.id.popUp_button);
        button.setOnClickListener(v -> activity.addEveryIngredient());
    }
    public void setAdapteur(ArrayList<String> ingredients,HomeActivity homeActivity){
        PopUpAdapter adapteur = new PopUpAdapter(ingredients);
        adapteur.setOnMyItemClickListener(ingredient -> homeActivity.addIngredient(ingredient));
        this.recyclerView.setAdapter(adapteur);
    }
    public void build(){
        show();
    }
    public void stop() { hide(); }
}
