package com.github.loic.frigo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.github.loic.frigo.model.Recette;
import com.github.loic.frigo.R;
import com.github.loic.frigo.controler.ListIngredientsAdapter;
import com.github.loic.frigo.model.GetApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {
    private FloatingActionButton addProduct,goToRecetteActivity,deleteAll;
    private CustomPopUp customPopUp;
    private RecyclerView recyclerView;
    private ArrayList<Recette> recettes;
    private ListIngredientsAdapter listIngredientsAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.recettes = null;
        this.customPopUp = new CustomPopUp(this);
        this.addProduct = findViewById(R.id.activityHome_FloatingActionButton_AddElement);
        this.deleteAll = findViewById(R.id.activityHome_FloatingActionButton_delete);
        this.goToRecetteActivity = findViewById(R.id.activityHome_FloatingActionButton_Check);
        this.addProduct.setOnClickListener(v -> {
            this.customPopUp.build();
            if(this.recettes==null){
                this.getDataArrayList();
            }
        });
        this.deleteAll.setOnClickListener(v -> this.listIngredientsAdapter.deleteAll());
        this.goToRecetteActivity.setOnClickListener(v -> this.recetteActivity(this.recettes,this.listIngredientsAdapter.getArrayList()));
        this.recyclerView = findViewById(R.id.activityHome_recyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        this.listIngredientsAdapter = new ListIngredientsAdapter(true);
        this.recyclerView.setAdapter(this.listIngredientsAdapter);
    }
    private void getDataArrayList(){
        if((this.recettes = this.getFromSharedPreferences())==null)
            this.getApi();
        else
            setAdapterCustomPopUpRecyclerView(this.recettes);
    }
    private void getApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ns202518.ovh.net/mehdi/api/frigo/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetApi api = retrofit.create(GetApi.class);
        Call<ArrayList<Recette>> call = api.get();
        call.enqueue(new Callback<ArrayList<Recette>>() {
            public void onResponse(Call<ArrayList<Recette>> call, Response<ArrayList<Recette>> response) {
                if(response.isSuccessful()){
                    SetRecettes(response.body());
                    setAdapterCustomPopUpRecyclerView(response.body());
                    setSaveData(response.body());
                } else {
                }
            }
            public void onFailure(Call<ArrayList<Recette>> call, Throwable t) {
            }
        });
    }
    private void SetRecettes(ArrayList<Recette> recettes){
        this.recettes = recettes;
    }
    private void setAdapterCustomPopUpRecyclerView(ArrayList<Recette> recettes) {
        // Cela ne pose pas de problème si la liste recettes est vide ( Pas de if pour voir si la liste est vide) car cette méthode n'est pas appellée car l'api n'a pas de réponse et ce trouve donc dans onFailure

        // 1 er algo possible pour n'avoir que des ingrédients unique
        HashMap<String,Integer> ingredients_hashMap = new HashMap<>();
        for(Recette recette:recettes)
            for(String ingredient:recette.getIngredients())
                ingredients_hashMap.put(ingredient,0);

        ArrayList<String> ingredients = new ArrayList<>();
        ingredients_hashMap.forEach((k,v) -> ingredients.add(k));
        Collections.sort(ingredients);
        this.customPopUp.setAdapteur(ingredients,this);

        Log.i("TESTE",ingredients.toString());
    }
    public void setSaveData(ArrayList<Recette> recettes){
        SharedPreferences sharedPreferences = getSharedPreferences("recette",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("arrayList<Recette> from api", new Gson().toJson(recettes));
        editor.apply();
    }
    private ArrayList<Recette> getFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("recette",MODE_PRIVATE);
        return new Gson().fromJson(sharedPreferences.getString("arrayList<Recette> from api",null),new TypeToken<ArrayList<Recette>>() {}.getType());
    }
    public void addIngredient(String ingredient){
        this.listIngredientsAdapter.addIngredient(ingredient);
        this.listIngredientsAdapter.notifyDataSetChanged();
        this.customPopUp.stop();
    }
    public void addEveryIngredient(){
        // 2 eme algo possible pour n'avoir que des ingrédients unique
        ArrayList<String> ingredients = new ArrayList<>();
        if(this.recettes!=null){
            for(Recette recette:this.recettes){
                for(String ingredient:recette.getIngredients()){
                    if(!ingredients.contains(ingredient)){
                        ingredients.add(ingredient);
                    }
                }
            }
        }
        // Ajouter dans la recyclerView tout les ingredients
        // Vue que l'on ajoute des elements déja trié on ne va pas plus les triée car sinon l'algo de trie va etre long (Si les elements du tableau du bubble sort sont déja triée alors c'est a ce moment que l'algo prend le plus de temps)
        for(String ingredient:ingredients)
            this.listIngredientsAdapter.addIngredient(ingredient);
        this.listIngredientsAdapter.notifyDataSetChanged();
        this.customPopUp.stop();
    }
    public void recetteActivity(ArrayList<Recette> recettes, ArrayList<String> ingrédients){
        ArrayList<Recette> recettePossible = new ArrayList<>();
        if(recettes != null) {
            Collections.addAll(recettePossible,recettes.toArray(new Recette[recettes.size()]));
            for(Recette recette: recettes)
                for(String ingredient: recette.getIngredients())
                    if(!ingrédients.contains(ingredient))
                        recettePossible.remove(recette);
        }
        Intent intent = new Intent(this, RecetteActivity.class);
        intent.putExtra("recette",recettePossible);
        startActivity(intent);
    }
}
