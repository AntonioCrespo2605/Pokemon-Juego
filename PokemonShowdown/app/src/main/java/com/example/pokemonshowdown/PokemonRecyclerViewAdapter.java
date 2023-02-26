package com.example.pokemonshowdown;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.pokemonshowdown.R.drawable.*;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;


public class PokemonRecyclerViewAdapter extends RecyclerView.Adapter<PokemonRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Pokemon> mData;
    private List<Boolean> selected;
    private DBHandler handler;
    private ViewHolder p;
    private FloatingActionButton f;

    // Constructor
    public PokemonRecyclerViewAdapter(Context mContext, List<Pokemon> mData, ViewHolder p) {
        this.mContext = mContext;
        this.mData = mData;
        this.p = p;
        this.f = p.getFab();

        this.handler = new DBHandler(mContext);
        this.selected = new ArrayList<Boolean>();

        // Se llena la lista de selecciones con valores "false" para cada elemento en la lista de la base de datos de Pokemon
        deselect();
    }

    // Crea una nueva vista para cada elemento de la lista
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_pokemon, parent, false);
        return new MyViewHolder(view);
    }

    // Asigna valores a los elementos de la vista del elemento en base a los datos del objeto Pokemon en esa posición
    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.pokemon_name.setText(mData.get(position).getName());
        holder.img_pokemon.setImageResource(mData.get(position).getImg());

        // Si el elemento está seleccionado, se establece el color de fondo en azul, de lo contrario se establece en gris oscuro
        // Mantiene la carta seleccionada cuando actualizas la vista
        if (selected.get(position)) holder.ll.setBackgroundColor(ContextCompat.getColor(mContext, R.color.blue_selected));
        else holder.ll.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white_shadow));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected.get(position)) {
                    selected.set(position, false);
                   holder.ll.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white_shadow));
                } else {
                    selected.set(position, true);
                    holder.ll.setBackgroundColor(ContextCompat.getColor(mContext, R.color.blue_selected));
                }

                // Se muestra o se oculta el botón flotante dependiendo del número de elementos seleccionados
                if (getCountSelected() == 3) {
                    f.setVisibility(View.VISIBLE);
                } else {
                    f.setVisibility(View.INVISIBLE);
                }

            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ImageView imgPkm, type1, type2;
                TextView tv_hp, tv_dmg, tv_def, tv_spd, tv_name;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.layout_custom_dialog,null);

                imgPkm = dialogView.findViewById(R.id.pokemon_img);
                type1 = dialogView.findViewById(R.id.type1);
                type2 = dialogView.findViewById(R.id.type2);
                tv_name = dialogView.findViewById(R.id.txttite);
                tv_hp = dialogView.findViewById(R.id.tv_hp);
                tv_dmg = dialogView.findViewById(R.id.tv_dmg);
                tv_def = dialogView.findViewById(R.id.tv_def);
                tv_spd = dialogView.findViewById(R.id.tv_spd);

                imgPkm.setImageResource(mData.get(position).getImg());
                switch (mData.get(position).getType1()){
                    case 1:
                        type1.setImageResource(bug);

                        break;
                    case 2:
                        type1.setImageResource(dark);
                        break;
                    case 3:
                        type1.setImageResource(dragon);
                        break;
                    case 4:
                        type1.setImageResource(electric);
                        break;
                    case 5:
                        type1.setImageResource(fairy);
                        break;
                    case 6:
                        type1.setImageResource(fight);
                        break;
                    case 7:
                        type1.setImageResource(fire);
                        break;
                    case 8:
                        type1.setImageResource(flying);
                        break;
                    case 9:
                        type1.setImageResource(ghost);
                        break;
                    case 10:
                        type1.setImageResource(grass);
                        break;
                    case 11:
                        type1.setImageResource(ground);
                        break;
                    case 12:
                        type1.setImageResource(ice);
                        break;
                    case 13:
                        type1.setImageResource(normal);
                        break;
                    case 14:
                        type1.setImageResource(poison);
                        break;
                    case 15:
                        type1.setImageResource(psychic);
                        break;
                    case 16:
                        type1.setImageResource(rock);
                        break;
                    case 17:
                        type1.setImageResource(steel);
                        break;
                    case 18:
                        type1.setImageResource(water);
                        break;
                }
                switch (mData.get(position).getType2()){
                    case 1:
                        type2.setImageResource(bug);

                        break;
                    case 2:
                        type2.setImageResource(dark);
                        break;
                    case 3:
                        type2.setImageResource(dragon);
                        break;
                    case 4:
                        type2.setImageResource(electric);
                        break;
                    case 5:
                        type2.setImageResource(fairy);
                        break;
                    case 6:
                        type2.setImageResource(fight);
                        break;
                    case 7:
                        type2.setImageResource(fire);
                        break;
                    case 8:
                        type2.setImageResource(flying);
                        break;
                    case 9:
                        type2.setImageResource(ghost);
                        break;
                    case 10:
                        type2.setImageResource(grass);
                        break;
                    case 11:
                        type2.setImageResource(ground);
                        break;
                    case 12:
                        type2.setImageResource(ice);
                        break;
                    case 13:
                        type2.setImageResource(normal);
                        break;
                    case 14:
                        type2.setImageResource(poison);
                        break;
                    case 15:
                        type2.setImageResource(psychic);
                        break;
                    case 16:
                        type2.setImageResource(rock);
                        break;
                    case 17:
                        type2.setImageResource(steel);
                        break;
                    case 18:
                        type2.setImageResource(water);
                        break;
                }

                tv_name.setText(mData.get(position).getName());
                tv_hp.setText(mData.get(position).getHp()+ "");
                tv_dmg.setText(mData.get(position).getDmg()+ "");
                tv_def.setText(mData.get(position).getDef()+ "");
                tv_spd.setText(mData.get(position).getSpd()+ "");

                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();
                return true;
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

    public ArrayList<Pokemon> getSelected() {
        ArrayList<Pokemon> toret = new ArrayList<Pokemon>();

        for (int i = 0; i < selected.size(); i++) {
            if (selected.get(i)) toret.add(handler.getPokemons().get(i));
        }

        return toret;
    }

    public void deselect(){
        for (int i = 0; i < handler.getPokemons().size(); i++) {
            selected.add(false);
        }
    }

}

