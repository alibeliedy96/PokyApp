package com.example.pocky.repository;

import androidx.lifecycle.LiveData;

import com.example.pocky.db.PokemonDao;
import com.example.pocky.model.pokyResponse.Pokemon;
import com.example.pocky.model.pokyResponse.PokemonResponse;
import com.example.pocky.network.PokemonApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class Repository {
    private PokemonApiService pokemonApiService;
    private PokemonDao pokemonDao;

    @Inject
    public Repository(PokemonApiService pokemonApiService,PokemonDao pokemonDao) {
        this.pokemonApiService = pokemonApiService;
        this.pokemonDao=pokemonDao;
    }
    public Observable<PokemonResponse> getPoky(){
        return pokemonApiService.getPokemons();
    }

    public void insertPokemon(Pokemon pokemon){
        pokemonDao.insertPokemon(pokemon);
    }
    public void deletePokemon(String pokemonName){
        pokemonDao.deletePokemon(pokemonName);
    }
    public LiveData<List<Pokemon>> getFavPokemon(){
        return pokemonDao.getAllPokemons();
    }
}
