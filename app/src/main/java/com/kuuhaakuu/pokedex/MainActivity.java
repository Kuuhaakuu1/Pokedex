package com.kuuhaakuu.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PokemonListCallback{
//    private PokemonAPI pokemonAPI;
private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private ArrayList<Pokemon> pokemonDataList;
    private PokemonAdapter pokemonAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_item_layout);
        // Create the adapter
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.progress_bar);

        PokemonAPI pokemonAPI = new PokemonAPI(this);
        pokemonAPI.fetchPokemonList();
    }

    @Override
    public void onPokemonListFetched(ArrayList<Pokemon> pokemonList) {
        pokemonDataList = pokemonList;
        Log.d(TAG, "Fetched " + pokemonDataList.size() + " Pokemon");
        Log.d(TAG, "Attack " + pokemonDataList.get(0).getAttack());
        Log.d(TAG, "Sprite " + pokemonDataList.get(0).getSpriteLink());
        progressBar.setVisibility(View.GONE);
        pokemonAdapter = new PokemonAdapter(pokemonDataList);
        recyclerView.setAdapter(pokemonAdapter);
    }

}
