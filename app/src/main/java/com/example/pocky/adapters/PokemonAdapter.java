package com.example.pocky.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pocky.R;
import com.example.pocky.model.pokyResponse.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    List<Pokemon> pokemonList;
    Context mContext;

    public PokemonAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
            .inflate(R.layout.pokemon_item,parent,false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
            Pokemon pokemon=pokemonList.get(position);
            holder.pokyName.setText(pokemon.getName());
        Glide.with(mContext).load(pokemon.getUrl()).into(holder.pokeImage);
    }

    @Override
    public int getItemCount() {
        if (pokemonList==null)return 0;
        return pokemonList.size();
    }
    public void changeData(ArrayList<Pokemon> pokemonList){
        this.pokemonList=pokemonList;
        notifyDataSetChanged();
    }
public Pokemon getPokemonAt(int pos){
        return pokemonList.get(pos);
}
    public class PokemonViewHolder extends RecyclerView.ViewHolder{
       private ImageView pokeImage;
       private TextView  pokyName;
        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            pokeImage=itemView.findViewById(R.id.poky_image);
            pokyName=itemView.findViewById(R.id.poky_name_tv);
        }
    }
}
