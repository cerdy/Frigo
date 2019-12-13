package com.github.loic.frigo.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.loic.frigo.R;
import com.github.loic.frigo.controler.ListIngredientsAdapter;

import java.util.ArrayList;


public class FragmentListIngredient extends Fragment {
    private RecyclerView recyclerView;
    private ListIngredientsAdapter listIngredientsAdapter;
    public FragmentListIngredient(ArrayList<String> ingredient){
        this.listIngredientsAdapter = new ListIngredientsAdapter(ingredient,false);
        listIngredientsAdapter.setColor(Color.GRAY);
    }
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_ingredient,container,false);
        this.recyclerView = view.findViewById(R.id.recyclerView_fragment_recyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        this.recyclerView.setAdapter(this.listIngredientsAdapter);
        return view;
    }
}
