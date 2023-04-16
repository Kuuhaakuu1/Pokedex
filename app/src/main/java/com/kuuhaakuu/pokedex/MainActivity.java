package com.kuuhaakuu.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PokemonListCallback{
//    private PokemonAPI pokemonAPI;
private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private ArrayList<Pokemon> pokemonDataList = new ArrayList<Pokemon>();
    private PokemonAdapter pokemonAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create the adapter
        //pokemonAdapter = new PokemonAdapter(pokemonDataList);
        //recyclerView = findViewById(R.id.recycler_view);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //progressBar = findViewById(R.id.progress_bar);

        PokemonAPI pokemonAPI = new PokemonAPI(this);
        pokemonAPI.fetchPokemonList();
    }

    @Override
    public void onPokemonListFetched(ArrayList<Pokemon> pokemonList) {
        pokemonDataList = pokemonList;
        Log.d(TAG, "Fetched " + pokemonDataList.size() + " Pokemon");
        Log.d(TAG, "Attack " + pokemonDataList.get(0).getAttack());
        Log.d(TAG, "Sprite " + pokemonDataList.get(0).getSpriteLink());
        ImageView spriteView = findViewById(R.id.pokemon_sprite);
        TextView nameView = findViewById(R.id.pokemon_name);
        TextView hpView = findViewById(R.id.pokemon_hp);
        TextView defenseView = findViewById(R.id.pokemon_defense);
        TextView attackView = findViewById(R.id.pokemon_attack);

// Load the sprite using a library such as Picasso or Glide
        nameView.setText(pokemonDataList.get(0).getName());
        hpView.setText(String.valueOf(pokemonDataList.get(0).getHp()));
        defenseView.setText(String.valueOf(pokemonDataList.get(0).getDefense()));
        attackView.setText(String.valueOf(pokemonDataList.get(0).getAttack()));
        Picasso.get().load(pokemonDataList.get(0).getSpriteLink()).into(spriteView);
        View panel = LayoutInflater.from(this).inflate(R.layout.pokemon_panel, null);
        ViewGroup parent = findViewById(R.id.parent_layout); // The parent layout to add the panel to
        parent.addView(panel);
        //progressBar.setVisibility(View.GONE);
        // pokemonAdapter = new PokemonAdapter(pokemonDataList);
        //recyclerView.setAdapter(pokemonAdapter);
        //pokemonAdapter.onBindViewHolder(pokemonAdapter.onCreateViewHolder(this.recyclerView,1),0);
        //pokemonAdapter.notifyDataSetChanged();
    }

}
