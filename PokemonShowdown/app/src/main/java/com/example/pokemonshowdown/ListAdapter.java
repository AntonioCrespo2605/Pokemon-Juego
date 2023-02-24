package com.example.pokemonshowdown;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements View.OnClickListener {
    private List<Move> mData;
    private LayoutInflater mInflater;
    private Context contexto;
    private View.OnClickListener listener;


    public ListAdapter(List<Move> itemlList, Context contexto) {
        this.mInflater = LayoutInflater.from(contexto);
        this.contexto = contexto;
        this.mData = itemlList;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        View view = mInflater.inflate(R.layout.list_element, null);
        view.setOnClickListener(this);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int pos) {
        holder.bindData(mData.get(pos));

    }

    public void setItems(List<Move> items) {
        mData = items;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage, type;
        TextView name, mov_dmg, mov_accuracy;

        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.cd);
            name = itemView.findViewById(R.id.mov_name);
            mov_dmg = itemView.findViewById(R.id.mov_dmg);
            mov_accuracy = itemView.findViewById(R.id.mov_accuracy);

        }

        void bindData(final Move item) {
            name.setText(item.getName());
            mov_dmg.setText(item.getDmg());
            mov_accuracy.setText(item.getHitProb());
            type.setImageResource(item.getType());

        }


    }
}

