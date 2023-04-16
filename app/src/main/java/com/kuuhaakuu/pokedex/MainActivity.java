package com.kuuhaakuu.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
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
        pokemonAdapter = new PokemonAdapter(pokemonDataList);
        recyclerView = findViewById(R.id.parent_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        ViewGroup parent = null;
// Load the sprite using a library such as Picasso or Glide
        for(Pokemon pokemon : pokemonDataList){
            nameView.setText("Pokemon : " + pokemon.getName().substring(0, 1).toUpperCase() + pokemon.getName().substring(1));
            hpView.setText("Hp : " + String.valueOf(pokemon.getHp()));
            defenseView.setText("Defense : " + String.valueOf(pokemon.getDefense()));
            attackView.setText("Attack : " + String.valueOf(pokemon.getAttack()));
            Picasso.get().load(pokemon.getSpriteLink()).into(spriteView);

            View panel = LayoutInflater.from(this).inflate(R.layout.pokemon_panel, parent, false);
            parent = findViewById(R.id.parent_layout); // The parent layout to add the panel to
            parent.addView(panel);
        }



    }

}
