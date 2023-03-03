package com.example.pokemonshowdown;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements View.OnClickListener {
    public List<Move> mData;
    private LayoutInflater mInflater;
    private Context contexto;
    private View.OnClickListener listener;
    public ViewHolder holder;




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
        this.holder =  new ListAdapter.ViewHolder(view);
        return holder;
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
        private CardView cv;
        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.cd);
            type = itemView.findViewById(R.id.mov_type);
            name = itemView.findViewById(R.id.mov_name);
            mov_dmg = itemView.findViewById(R.id.mov_dmg);
            mov_accuracy = itemView.findViewById(R.id.mov_accuracy);
            cv = itemView.findViewById(R.id.cv);

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

        public  void cardSelect(){
            cv.setBackgroundColor(ContextCompat.getColor(contexto, R.color.blue_selected));
        }
        public  void cardDeselect(){
            cv.setBackgroundColor(ContextCompat.getColor(contexto, R.color.whitep));
        }


    }
}

