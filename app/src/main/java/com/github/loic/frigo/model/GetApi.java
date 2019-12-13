package com.github.loic.frigo.model;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
public interface GetApi {
    @GET("recettes.json")
    Call<ArrayList<Recette>> get();
}
