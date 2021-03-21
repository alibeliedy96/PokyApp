package com.example.pocky.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pocky.model.pokyResponse.Pokemon;
import com.example.pocky.model.pokyResponse.PokemonResponse;
import com.example.pocky.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PokemonViewModel extends ViewModel {
    private static final String TAG = "PokemonViewModel";
    private Repository repository;
    private MutableLiveData<ArrayList<Pokemon>> pokyList = new MutableLiveData<>();
    private LiveData<List<Pokemon>> FavList = null;


    @ViewModelInject
    public PokemonViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<Pokemon>> getFavList() {
        return FavList;
    }

    public MutableLiveData<ArrayList<Pokemon>> getPokyList() {
        return pokyList;
    }

    public void getPokemons() {
        repository.getPoky().subscribeOn(Schedulers.io())
                .map(new Function<PokemonResponse, ArrayList<Pokemon>>() {
                    @Override
                    public ArrayList<Pokemon> apply(PokemonResponse pokemonResponse) throws Throwable {
                        ArrayList<Pokemon> list = pokemonResponse.getResults();
                        for (Pokemon pokemon : list) {
                            String url = pokemon.getUrl();
                            String[] pokemonIndex = url.split("/");
                            pokemon.setUrl("https://pokeres.bastionbot.org/images/pokemon/" + pokemonIndex[pokemonIndex.length - 1] + ".png");


                        }
                        return list;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> pokyList.setValue(result)
                        , error -> Log.e(TAG, error.getMessage()));

    }

    public void insertPokemon(Pokemon pokemon) {
        repository.insertPokemon(pokemon);
    }

    public void deletePokemon(String pokemonName) {
        repository.deletePokemon(pokemonName);
    }

    public void getAllPokemon() {
        FavList = repository.getFavPokemon();
    }

}
