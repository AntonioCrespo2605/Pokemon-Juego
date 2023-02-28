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
import static com.example.pokemonshowdown.R.drawable.txt_flying;
import static com.example.pokemonshowdown.R.drawable.txt_ground;
import static com.example.pokemonshowdown.R.drawable.txt_normal;

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
    private ArrayList<Pokemon> pchoosed;
    private ThisViewHolder p;



    // Constructor
    public PokemonRecyclerViewAdapter(Context mContext, List<Pokemon> mData, ThisViewHolder p) {
        this.mContext = mContext;
        this.mData = mData;
        this.p = p;
        this.selected = new ArrayList<>();
        this.pchoosed = new ArrayList<>();
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

        FloatingActionButton fab = p.getFab();

        // Si el elemento está seleccionado, se establece el color de fondo en azul, de lo contrario se establece en gris oscuro
        // Mantiene la carta seleccionada cuando actualizas la vista
        if (selected.get(position))
            holder.ll.setBackgroundColor(ContextCompat.getColor(mContext, R.color.blue_selected));
        else holder.ll.setBackgroundColor(ContextCompat.getColor(mContext, R.color.whitep));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(getCountSelected() == 3) ) {
                    //ya seleccionado
                    if (selected.get(position)) {
                        selected.set(position, false);
                        pchoosed.remove(mData.get(position));
                        holder.ll.setBackgroundColor(ContextCompat.getColor(mContext, R.color.whitep));

                    } else { //sin seleccionar
                        selected.set(position, true);
                        pchoosed.add(mData.get(position));
                        holder.ll.setBackgroundColor(ContextCompat.getColor(mContext, R.color.blue_selected));

                    }
                }else if(selected.get(position)){
                    selected.set(position, false);
                    pchoosed.remove(mData.get(position));
                    holder.ll.setBackgroundColor(ContextCompat.getColor(mContext, R.color.whitep));
                }
                // Se muestra o se oculta el botón flotante dependiendo del número de elementos seleccionados
                if (getCountSelected() == 3) {
                    fab.setVisibility(View.VISIBLE);
                } else {
                    fab.setVisibility(View.INVISIBLE);
                }
                updateSelected();
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ImageView imgPkm, type1, type2;
                TextView tv_hp, tv_dmg, tv_def, tv_spd, tv_name, tv_num;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.layout_custom_dialog, null);

                imgPkm = dialogView.findViewById(R.id.pokemon_img);
                type1 = dialogView.findViewById(R.id.type1);
                type2 = dialogView.findViewById(R.id.type2);
                tv_name = dialogView.findViewById(R.id.pk_name_txt);
                tv_num = dialogView.findViewById(R.id.tv_num);
                tv_hp = dialogView.findViewById(R.id.tv_hp);
                tv_dmg = dialogView.findViewById(R.id.tv_dmg);
                tv_def = dialogView.findViewById(R.id.tv_def);
                tv_spd = dialogView.findViewById(R.id.tv_spd);

                imgPkm.setImageResource(mData.get(position).getImg());

                selectType(type1, mData.get(position).getType1());
                selectType(type2, mData.get(position).getType2());

                tv_name.setText(mData.get(position).getName());

                tv_num.setText(formatNumber(mData.get(position).getNumDex()));
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

    //ONCLICK

    // Método auxiliar que cuenta el número de elementos seleccionados
    private int getCountSelected() {
        int toret = 0;
        for (Boolean b : selected) {
            if (b) toret++;
        }
        return toret;
    }

    private void updateSelected(){
        ImageView pk1, pk2, pk3;
        pk1 = p.getPk1();
        pk2 = p.getPk2();
        pk3 = p.getPk3();

        if(pchoosed.size() == 1){
            pk1.setVisibility(View.VISIBLE);
            pk1.setImageResource(pchoosed.get(0).getImg());
            pk2.setVisibility(View.INVISIBLE);
            pk3.setVisibility(View.INVISIBLE);
        }else if(pchoosed.size() == 2){
            pk1.setVisibility(View.VISIBLE);
            pk1.setImageResource(pchoosed.get(0).getImg());
            pk2.setVisibility(View.VISIBLE);
            pk2.setImageResource(pchoosed.get(1).getImg());
            pk3.setVisibility(View.INVISIBLE);
        }
        else if(pchoosed.size() == 3){
            pk1.setVisibility(View.VISIBLE);
            pk1.setImageResource(pchoosed.get(0).getImg());
            pk2.setVisibility(View.VISIBLE);
            pk2.setImageResource(pchoosed.get(1).getImg());
            pk3.setVisibility(View.VISIBLE);
            pk3.setImageResource(pchoosed.get(2).getImg());
        }else{
            pk1.setVisibility(View.INVISIBLE);
            pk2.setVisibility(View.INVISIBLE);
            pk3.setVisibility(View.INVISIBLE);
        }
    }

    public ArrayList<Pokemon> getSelected() {
       return pchoosed;
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
                img.setImageResource(txt_bug);
                break;
            case 2:
                img.setImageResource(txt_dark);
                break;
            case 3:
                img.setImageResource(txt_dragon);
                break;
            case 4:
                img.setImageResource(txt_electric);
                break;
            case 5:
                img.setImageResource(txt_fairy);
                break;
            case 6:
                img.setImageResource(txt_fighting);
                break;
            case 7:
                img.setImageResource(txt_fire);
                break;
            case 8:
                img.setImageResource(txt_flying);
                break;
            case 9:
                img.setImageResource(txt_ghost);
                break;
            case 10:
                img.setImageResource(txt_grass);
                break;
            case 11:
                img.setImageResource(txt_ground);
                break;
            case 12:
                img.setImageResource(txt_ice);
                break;
            case 13:
                img.setImageResource(txt_normal);
                break;
            case 14:
                img.setImageResource(txt_poison);
                break;
            case 15:
                img.setImageResource(txt_psychic);
                break;
            case 16:
                img.setImageResource(txt_rock);
                break;
            case 17:
                img.setImageResource(txt_steel);
                break;
            case 18:
                img.setImageResource(txt_water);
                break;
            default:
                img.setVisibility(View.GONE);
        }

    }

    public static String formatNumber(int number) {
        return String.format("#%03d", number);
    }


}

