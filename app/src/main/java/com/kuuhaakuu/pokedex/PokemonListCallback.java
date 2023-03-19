package com.kuuhaakuu.pokedex;

import java.util.ArrayList;

public interface PokemonListCallback {
    void onPokemonListFetched(ArrayList<Pokemon> pokemonList);
}

