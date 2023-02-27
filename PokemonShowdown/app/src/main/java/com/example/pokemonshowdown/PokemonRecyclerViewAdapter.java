package com.example.pokemonshowdown;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.pokemonshowdown.R.drawable.*;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class PokemonRecyclerViewAdapter extends RecyclerView.Adapter<PokemonRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Pokemon> mData;

    private List<Boolean> selected;
    private ViewHolder p;
    private FloatingActionButton f;

    // Constructor
    public PokemonRecyclerViewAdapter(Context mContext, List<Pokemon> mData, ViewHolder p) {
        this.mContext = mContext;
        this.mData = mData;
        this.p = p;
        this.f = p.getFab();
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
        if (selected.get(position))
            holder.ll.setBackgroundColor(ContextCompat.getColor(mContext, R.color.blue_selected));
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
                View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.layout_custom_dialog, null);

                imgPkm = dialogView.findViewById(R.id.pokemon_img);
                type1 = dialogView.findViewById(R.id.type1);
                type2 = dialogView.findViewById(R.id.type2);
                tv_name = dialogView.findViewById(R.id.txttite);
                tv_hp = dialogView.findViewById(R.id.tv_hp);
                tv_dmg = dialogView.findViewById(R.id.tv_dmg);
                tv_def = dialogView.findViewById(R.id.tv_def);
                tv_spd = dialogView.findViewById(R.id.tv_spd);

                imgPkm.setImageResource(mData.get(position).getImg());

                selectType(type1, mData.get(position).getType1());
                selectType(type2, mData.get(position).getType2());

                tv_name.setText(mData.get(position).getName());
                tv_hp.setText(mData.get(position).getHp() + "");
                tv_dmg.setText(mData.get(position).getDmg() + "");
                tv_def.setText(mData.get(position).getDef() + "");
                tv_spd.setText(mData.get(position).getSpd() + "");

                builder.setView(dialogView);
                builder.setCancelable(true);
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                return true;
            }
        });

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    //ONCLICK

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

//        for (int i = 0; i < selected.size(); i++) {
//            if (selected.get(i)) {
//                toret.add(mData.get(i));
//                System.out.println(mData.get(i).toString());
//            }
//
//        }

        return toret;
    }

    //borrar seleccion (para siguiente jugador)
    public void deselect() {
        for (int i = 0; i < mData.size(); i++) {
            selected.add(false);
        }
    }

    //ONLONGCLICK

    //metodo para los tipos del pokemon en el dialog
    private void selectType(ImageView img, int type) {
        switch (type) {
            case 1:
                img.setImageResource(bug);
                break;
            case 2:
                img.setImageResource(dark);
                break;
            case 3:
                img.setImageResource(dragon);
                break;
            case 4:
                img.setImageResource(electric);
                break;
            case 5:
                img.setImageResource(fairy);
                break;
            case 6:
                img.setImageResource(fighting);
                break;
            case 7:
                img.setImageResource(fire);
                break;
            case 8:
                img.setImageResource(flying);
                break;
            case 9:
                img.setImageResource(ghost);
                break;
            case 10:
                img.setImageResource(grass);
                break;
            case 11:
                img.setImageResource(ground);
                break;
            case 12:
                img.setImageResource(ice);
                break;
            case 13:
                img.setImageResource(normal);
                break;
            case 14:
                img.setImageResource(poison);
                break;
            case 15:
                img.setImageResource(psychic);
                break;
            case 16:
                img.setImageResource(rock);
                break;
            case 17:
                img.setImageResource(steel);
                break;
            case 18:
                img.setImageResource(water);
                break;
            default:
                img.setVisibility(View.GONE);
        }

    }

}

