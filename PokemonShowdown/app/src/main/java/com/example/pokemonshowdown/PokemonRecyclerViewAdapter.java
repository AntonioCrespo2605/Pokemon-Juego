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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class PokemonRecyclerViewAdapter extends RecyclerView.Adapter<PokemonRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Pokemon> mData;
    private List<Boolean> selected;
    private DBHandler handler;
    private FloatingActionButton f;

    // Constructor
    public PokemonRecyclerViewAdapter(Context mContext, List<Pokemon> mData, FloatingActionButton f) {
        this.mContext = mContext;
        this.mData = mData;
        this.f = f;

        this.handler = new DBHandler(mContext);
        this.selected = new ArrayList<Boolean>();

        // Se llena la lista de selecciones con valores "false" para cada elemento en la lista de la base de datos de Pokemon
        for (int i = 0; i < handler.getPokemons().size(); i++) {
            selected.add(false);
        }
    }

    // Crea una nueva vista para cada elemento de la lista
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext); // Se crea una instancia del objeto LayoutInflater
        view = mInflater.inflate(R.layout.cardview_item_pokemon, parent, false); // Se infla el diseño de la vista del elemento
        return new MyViewHolder(view); // Se devuelve una instancia de MyViewHolder para esa vista
    }

    // Asigna valores a los elementos de la vista del elemento en base a los datos del objeto Pokemon en esa posición
    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.pokemon_name.setText(mData.get(position).getName());
        holder.img_pokemon.setImageResource(mData.get(position).getImg());

        // Si el elemento está seleccionado, se establece el color de fondo en azul, de lo contrario se establece en gris oscuro
        if (selected.get(position)) holder.ll.setBackgroundColor(Color.BLUE);
        else holder.ll.setBackgroundColor(Color.parseColor("#2d2d2d"));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected.get(position)) {
                    selected.set(position, false);
                    holder.ll.setBackgroundColor(Color.parseColor("#2d2d2d"));
                } else {
                    selected.set(position, true);
                    holder.ll.setBackgroundColor(Color.BLUE);
                }

                // Se muestra o se oculta el botón flotante dependiendo del número de elementos seleccionados
                if (getCountSelected() == 3) {
                    f.setVisibility(View.VISIBLE);
                } else {
                    f.setVisibility(View.INVISIBLE);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // Clase interna que representa el ViewHolder para cada elemento del RecyclerView
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
            ll = (LinearLayout) itemView.findViewById(R.id.llP);

        }
    }

    // Método auxiliar que cuenta el número de elementos seleccionados
    private int getCountSelected() {
        int toret = 0;
        for (Boolean b : selected) {
            if (b) toret++;
        }
        return toret;
    }

    public ArrayList<Pokemon> getSelected(){
        ArrayList<Pokemon>toret=new ArrayList<Pokemon>();

        for(int i=0;i<selected.size();i++){
            if(selected.get(i))toret.add(handler.getPokemons().get(i));
        }

        return toret;
    }
}

