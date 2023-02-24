package com.example.pokemonshowdown;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class PokemonRecyclerViewAdapter extends RecyclerView.Adapter<PokemonRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Pokemon> mData;
    private List<Boolean>selected;
    private DBHandler handler;

    public PokemonRecyclerViewAdapter(Context mContext, List<Pokemon> mData) {
        this.mContext = mContext;
        this.mData = mData;

        this.handler=new DBHandler(mContext);
        this.selected=new ArrayList<Boolean>();

        for(int i=0;i<handler.getPokemons().size();i++){
            selected.add(false);
        }
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
        if(selected.get(position))holder.ll.setBackgroundColor(Color.BLUE);
        else holder.ll.setBackgroundColor(Color.parseColor("#2d2d2d"));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected.get(position)){
                    selected.set(position, false);
                    holder.ll.setBackgroundColor(Color.parseColor("#2d2d2d"));
                }else{
                    selected.set(position, true);
                    holder.ll.setBackgroundColor(Color.BLUE);
                }
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
        LinearLayout ll;
        public MyViewHolder(View itemView) {
            super(itemView);
            pokemon_name = (TextView) itemView.findViewById(R.id.pokemon_name);
            img_pokemon = (ImageView) itemView.findViewById(R.id.pokemon_img);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
            ll = itemView.findViewById(R.id.llP);
        }
    }
}

