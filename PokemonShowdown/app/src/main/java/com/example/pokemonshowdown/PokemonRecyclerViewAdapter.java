package com.example.pokemonshowdown;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class PokemonRecyclerViewAdapter extends RecyclerView.Adapter<PokemonRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Pokemon> mData;


    public PokemonRecyclerViewAdapter(Context mContext, List<Pokemon> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_pokemon, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.pokemon_name.setText(mData.get(position).getName());
        holder.img_pokemon.setImageResource(mData.get(position).getImg());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView pokemon_name;
        ImageView img_pokemon;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            pokemon_name = (TextView) itemView.findViewById(R.id.pokemon_name);
            img_pokemon = (ImageView) itemView.findViewById(R.id.pokemon_img);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);

        }
    }
}

