package com.example.pocky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pocky.adapters.PokemonAdapter;
import com.example.pocky.model.pokyResponse.Pokemon;
import com.example.pocky.viewmodel.PokemonViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavActivity extends AppCompatActivity {
    private PokemonViewModel viewModel;
    private RecyclerView pokyRecyclerView;
    private PokemonAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button toHomePtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        pokyRecyclerView=findViewById(R.id.fav_recycler_v);
        toHomePtn =findViewById(R.id.to_home_button);
        toHomePtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FavActivity.this,MainActivity.class));
            }
        });
        adapter=new PokemonAdapter(this);
        layoutManager=new LinearLayoutManager(this);
        pokyRecyclerView.setAdapter(adapter);
        pokyRecyclerView.setLayoutManager(layoutManager);
        setUpSwipe();
        viewModel=new ViewModelProvider(this).get(PokemonViewModel.class);
        viewModel.getAllPokemon();
        viewModel.getFavList().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                ArrayList<Pokemon> list=new ArrayList<>();
                list.addAll(pokemons);
                adapter.changeData(list);
                adapter.notifyDataSetChanged();

            }
        });
    }
    public void setUpSwipe(){
        ItemTouchHelper.SimpleCallback callback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPokemonPosition=viewHolder.getAdapterPosition();
                Pokemon pokemon=adapter.getPokemonAt(swipedPokemonPosition);
                viewModel.deletePokemon(pokemon.getName());
                adapter.notifyDataSetChanged();
                Toast.makeText(FavActivity.this, "pokemon deleted from db", Toast.LENGTH_SHORT).show();
            }
        };
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(pokyRecyclerView);
    }
}