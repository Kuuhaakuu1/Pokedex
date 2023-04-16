package com.kuuhaakuu.pokedex;

import android.os.AsyncTask;
import android.util.Log;

import com.kuuhaakuu.pokedex.Pokemon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PokemonAPI {

    private static final String TAG = PokemonAPI.class.getSimpleName();
    private static final String POKEMON_LIST_URL = "https://pokeapi.co/api/v2/pokemon?offset=0&limit=100";
    private static final String POKEMON_URL_TEMPLATE = "https://pokeapi.co/api/v2/pokemon/%s/";

    private ArrayList<String> pokemonList;
    private ArrayList<Pokemon> pokemonDataList = new ArrayList<Pokemon>();
    private PokemonListCallback callback;

    public PokemonAPI(PokemonListCallback callback) {
        this.callback = callback;
    }

    public void fetchPokemonList() {
        new FetchPokemonListTask().execute();
    }

    public ArrayList<Pokemon> getPokemonDataList() {
        return pokemonDataList;
    }

    private class FetchPokemonListTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
        Log.d(TAG,"Hy ?");
            try {
                URL url = new URL(POKEMON_LIST_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    return stringBuilder.toString();
                } else {
                    Log.e(TAG, "Error fetching Pokemon list response: " + responseCode);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error fetching Pokemon list", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    pokemonList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject pokemonObject = jsonArray.getJSONObject(i);
                        String pokemonName = pokemonObject.getString("name");
                        pokemonList.add(pokemonName);
                    }
                    Log.d(TAG, "Fetched " + pokemonList.size() + " Pokemon");

                    // Request data for each
                    // Pokemon in the list
                    new FetchPokemonDataTask().execute();
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing Pokemon list response", e);
                }
            }

        }
    }

    private class FetchPokemonDataTask extends AsyncTask<Void, Void, Pokemon> {

        @Override
        protected Pokemon doInBackground(Void... voids) {
            try {
                for (String pokemonName : pokemonList) {
                    String pokemonUrl = String.format(POKEMON_URL_TEMPLATE, pokemonName);
                    URL url = new URL(pokemonUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line);
                        }
                        bufferedReader.close();
                        inputStream.close();
                        JSONObject pokemonObject = new JSONObject(stringBuilder.toString());
                        String spriteLink = pokemonObject.getJSONObject("sprites").getString("front_default");
                        int hp = pokemonObject.getJSONArray("stats").getJSONObject(0).getInt("base_stat");
                        int defense = pokemonObject.getJSONArray("stats").getJSONObject(1).getInt("base_stat");
                        int attack = pokemonObject.getJSONArray("stats").getJSONObject(2).getInt("base_stat");
                        String name = pokemonObject.getString("name");
                        Pokemon pokemon = new Pokemon(spriteLink, hp, defense, attack, name);
                        pokemonDataList.add(pokemon);
                    } else {
                        Log.e(TAG, "Error fetching Pokemon data response: " + responseCode);
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Error fetching Pokemon data", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Pokemon result) {
                if (pokemonDataList != null) {
                    callback.onPokemonListFetched(pokemonDataList);
                }
        }
    }
}
