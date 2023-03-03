package com.example.pokemonshowdown.uicontrollers;

import static com.example.pokemonshowdown.R.drawable.mt_bug;
import static com.example.pokemonshowdown.R.drawable.mt_dark;
import static com.example.pokemonshowdown.R.drawable.mt_electric;
import static com.example.pokemonshowdown.R.drawable.mt_fairy;
import static com.example.pokemonshowdown.R.drawable.mt_fighting;
import static com.example.pokemonshowdown.R.drawable.mt_fire;
import static com.example.pokemonshowdown.R.drawable.mt_flying;
import static com.example.pokemonshowdown.R.drawable.mt_ghost;
import static com.example.pokemonshowdown.R.drawable.mt_grass;
import static com.example.pokemonshowdown.R.drawable.mt_ground;
import static com.example.pokemonshowdown.R.drawable.mt_ice;
import static com.example.pokemonshowdown.R.drawable.mt_normal;
import static com.example.pokemonshowdown.R.drawable.mt_poison;
import static com.example.pokemonshowdown.R.drawable.mt_pyshic;
import static com.example.pokemonshowdown.R.drawable.mt_rock;
import static com.example.pokemonshowdown.R.drawable.mt_steel;
import static com.example.pokemonshowdown.R.drawable.mt_water;
import static com.example.pokemonshowdown.R.drawable.txt_bug;
import static com.example.pokemonshowdown.R.drawable.txt_dark;
import static com.example.pokemonshowdown.R.drawable.txt_dragon;
import static com.example.pokemonshowdown.R.drawable.txt_electric;
import static com.example.pokemonshowdown.R.drawable.txt_fairy;
import static com.example.pokemonshowdown.R.drawable.txt_fighting;
import static com.example.pokemonshowdown.R.drawable.txt_fire;
import static com.example.pokemonshowdown.R.drawable.txt_flying;
import static com.example.pokemonshowdown.R.drawable.txt_ghost;
import static com.example.pokemonshowdown.R.drawable.txt_grass;
import static com.example.pokemonshowdown.R.drawable.txt_ground;
import static com.example.pokemonshowdown.R.drawable.txt_ice;
import static com.example.pokemonshowdown.R.drawable.txt_normal;
import static com.example.pokemonshowdown.R.drawable.txt_poison;
import static com.example.pokemonshowdown.R.drawable.txt_psychic;
import static com.example.pokemonshowdown.R.drawable.txt_rock;
import static com.example.pokemonshowdown.R.drawable.txt_steel;
import static com.example.pokemonshowdown.R.drawable.txt_water;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonshowdown.objects.Move;
import com.example.pokemonshowdown.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    public List<Move> mData;
    private LayoutInflater mInflater;
    private Context contexto;

    private List<Boolean> selected;
    private ArrayList<Move> mchoosed;
    private ThisViewHolder p;


    public ListAdapter(List<Move> itemlList, Context contexto, ThisViewHolder p) {
        this.mInflater = LayoutInflater.from(contexto);
        this.contexto = contexto;
        this.mData = itemlList;
        this.p = p;
        this.selected = new ArrayList<>();
        this.mchoosed = new ArrayList<>();
        deselect();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        View view = mInflater.inflate(R.layout.list_element, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.bindData(mData.get(position));
        FloatingActionButton fab = p.getFab();
        MediaPlayer click = MediaPlayer.create(contexto, R.raw.pokemon_a_sound);

        if (selected.get(position))
            holder.rv.setBackgroundColor(ContextCompat.getColor(contexto, R.color.blue_selected));
        else holder.rv.setBackgroundColor(ContextCompat.getColor(contexto, R.color.whitep));

        holder.rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getCountSelected() == 4)) {
                    //ya seleccionado
                    if (selected.get(position)) {
                        selected.set(position, false);
                        mchoosed.remove(mData.get(position));
                        holder.rv.setBackgroundColor(ContextCompat.getColor(contexto, R.color.whitep));

                    } else { //sin seleccionar
                        selected.set(position, true);
                        mchoosed.add(mData.get(position));
                        click.start();
                        holder.rv.setBackgroundColor(ContextCompat.getColor(contexto, R.color.blue_selected));

                    }
                } else if (selected.get(position)) {
                    selected.set(position, false);
                    mchoosed.remove(mData.get(position));
                    holder.rv.setBackgroundColor(ContextCompat.getColor(contexto, R.color.whitep));
                }
                // Se muestra o se oculta el botón flotante dependiendo del número de elementos seleccionados
                if ((getCountSelected() >= 1) && (getCountSelected() <= 4)) {
                    fab.setVisibility(View.VISIBLE);
                } else {
                    fab.setVisibility(View.INVISIBLE);
                }

            }
        });

    }

    public void setItems(List<Move> items) {
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage, type, move_type;
        TextView name, mov_dmg, mov_accuracy;
        LinearLayout rv;

        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.cd);
            type = itemView.findViewById(R.id.mov_type);
            name = itemView.findViewById(R.id.mov_name);
            mov_dmg = itemView.findViewById(R.id.mov_dmg);
            mov_accuracy = itemView.findViewById(R.id.mov_accuracy);
            rv = itemView.findViewById(R.id.rl);
            move_type = itemView.findViewById(R.id.type_of_move);

        }

        void bindData(final Move item) {
            String dmg, accuracy;
            dmg = String.valueOf(item.getDmg());
            accuracy = String.valueOf(item.getAccuracy());

            if (item.getName().length() > 13) {
                String aux = item.getName().substring(0, 12) + ".";
                name.setText(aux);
            } else {
                name.setText(item.getName());
            }

            if (item.isAtkSt() && !(item.getId() == 67 || item.getId() == 73)) {
                move_type.setImageResource(R.drawable.dmg_move);
            } else {
                move_type.setImageResource(R.drawable.status_move);
            }

            if (item.getDmg() == 0) {
                mov_dmg.setText("Pot: " + "-");
            } else if(item.getDmg() > 200){
                mov_dmg.setText("Pot: " + "∞");
            }else{
                mov_dmg.setText("Pot: " + dmg);
            }

            if (item.getAccuracy() == 0) {
                mov_accuracy.setText("Prec: " + "-");
            } else {
                mov_accuracy.setText("Prec: " + accuracy);
            }

            switch (item.getType()) {
                case 1:
                    iconImage.setImageResource(mt_bug);
                    type.setImageResource(txt_bug);
                    break;
                case 2:
                    iconImage.setImageResource(mt_dark);
                    type.setImageResource(txt_dark);
                    break;
                case 3:
                    iconImage.setImageResource(mt_dark);
                    type.setImageResource(txt_dragon);
                    break;
                case 4:
                    iconImage.setImageResource(mt_electric);
                    type.setImageResource(txt_electric);
                    break;
                case 5:
                    iconImage.setImageResource(mt_fairy);
                    type.setImageResource(txt_fairy);
                    break;
                case 6:
                    iconImage.setImageResource(mt_fighting);
                    type.setImageResource(txt_fighting);
                    break;
                case 7:
                    iconImage.setImageResource(mt_fire);
                    type.setImageResource(txt_fire);
                    break;
                case 8:
                    iconImage.setImageResource(mt_flying);
                    type.setImageResource(txt_flying);
                    break;
                case 9:
                    iconImage.setImageResource(mt_ghost);
                    type.setImageResource(txt_ghost);
                    break;
                case 10:
                    iconImage.setImageResource(mt_grass);
                    type.setImageResource(txt_grass);
                    break;
                case 11:
                    iconImage.setImageResource(mt_ground);
                    type.setImageResource(txt_ground);
                    break;
                case 12:
                    iconImage.setImageResource(mt_ice);
                    type.setImageResource(txt_ice);
                    break;
                case 13:
                    iconImage.setImageResource(mt_normal);
                    type.setImageResource(txt_normal);
                    break;
                case 14:
                    iconImage.setImageResource(mt_poison);
                    type.setImageResource(txt_poison);
                    break;
                case 15:
                    iconImage.setImageResource(mt_pyshic);
                    type.setImageResource(txt_psychic);
                    break;
                case 16:
                    iconImage.setImageResource(mt_rock);
                    type.setImageResource(txt_rock);
                    break;
                case 17:
                    iconImage.setImageResource(mt_steel);
                    type.setImageResource(txt_steel);
                    break;
                case 18:
                    iconImage.setImageResource(mt_water);
                    type.setImageResource(txt_water);
                    break;
                default:
                    type.setVisibility(View.GONE);
            }

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

    //borrar seleccion (para siguiente jugador)
    public void deselect() {
        for (int i = 0; i < mData.size(); i++) {
            selected.add(false);
        }
    }

    public ArrayList<Move> getSelected() {
        return mchoosed;
    }

}

