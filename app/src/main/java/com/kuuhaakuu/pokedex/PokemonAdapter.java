package com.kuuhaakuu.pokedex;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    private ArrayList<Pokemon> pokemonList;

    public PokemonAdapter(ArrayList<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_panel, parent, false);
        return new PokemonViewHolder(view);
    }

    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);
        holder.nameTextView.setText(pokemon.getName());
        holder.hpTextView.setText("HP: " + pokemon.getHp());
        holder.defenseTextView.setText("Defense: " + pokemon.getDefense());
        holder.attackTextView.setText("Attack: " + pokemon.getAttack());
        Picasso picasso = Picasso.get();
        Picasso.get().load(pokemon.getSpriteLink()).into(holder.spriteImageView);
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    static class PokemonViewHolder extends RecyclerView.ViewHolder {

        private ImageView spriteImageView;
        private TextView nameTextView;
        private TextView hpTextView;
        private TextView defenseTextView;
        private TextView attackTextView;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            spriteImageView = itemView.findViewById(R.id.pokemon_sprite);
            nameTextView = itemView.findViewById(R.id.pokemon_name);
            hpTextView = itemView.findViewById(R.id.pokemon_hp);
            defenseTextView = itemView.findViewById(R.id.pokemon_defense);
            attackTextView = itemView.findViewById(R.id.pokemon_attack);
        }
    }


}
