package com.example.pocky.network;

import com.example.pocky.model.pokyResponse.PokemonResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface PokemonApiService {
    @GET("pokemon")
    Observable<PokemonResponse> getPokemons();
}
