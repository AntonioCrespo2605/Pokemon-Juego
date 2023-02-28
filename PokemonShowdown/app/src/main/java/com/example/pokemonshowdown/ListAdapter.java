package com.example.pokemonshowdown;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>  {
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

        if (selected.get(position))
            holder.rv.setBackgroundColor(ContextCompat.getColor(contexto, R.color.blue_selected));
        else holder.rv.setBackgroundColor(ContextCompat.getColor(contexto, R.color.whitep));

        holder.rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(getCountSelected() ==  4)) {
                    //ya seleccionado
                    if (selected.get(position)) {
                        selected.set(position, false);
                        mchoosed.remove(mData.get(position));
                        holder.rv.setBackgroundColor(ContextCompat.getColor(contexto, R.color.whitep));

                    } else { //sin seleccionar
                        selected.set(position, true);
                        mchoosed.add(mData.get(position));
                        holder.rv.setBackgroundColor(ContextCompat.getColor(contexto, R.color.blue_selected));

                    }
                }else if(selected.get(position)){
                    selected.set(position, false);
                    mchoosed.remove(mData.get(position));
                    holder.rv.setBackgroundColor(ContextCompat.getColor(contexto, R.color.whitep));
                }
                // Se muestra o se oculta el botón flotante dependiendo del número de elementos seleccionados
                if ((getCountSelected() >= 1) && (getCountSelected() <=  4)) {
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
        ImageView iconImage, type;
        TextView name, mov_dmg, mov_accuracy;
        RelativeLayout rv;

        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.cd);
            type = itemView.findViewById(R.id.mov_type);
            name = itemView.findViewById(R.id.mov_name);
            mov_dmg = itemView.findViewById(R.id.mov_dmg);
            mov_accuracy = itemView.findViewById(R.id.mov_accuracy);
            rv = itemView.findViewById(R.id.rl);

        }

        void bindData(final Move item) {
            String dmg, accuracy;
            dmg = String.valueOf(item.getDmg());
            accuracy = String.valueOf(item.getAccuracy());
            name.setText(item.getName());
            mov_dmg.setText(dmg);
            mov_accuracy.setText(accuracy);
            //type.setImageResource(item.getType());

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

