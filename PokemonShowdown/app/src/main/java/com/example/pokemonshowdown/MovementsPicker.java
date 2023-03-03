package com.example.pokemonshowdown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MovementsPicker extends AppCompatActivity {

    private List<Move> moves;

    private DBHandler handler;

    private List<Move> movesPk1py1;
    private List<Move> movesPk2py1;
    private List<Move> movesPk3py1;
    private List<Move> movesPk1py2;
    private List<Move> movesPk2py2;
    private List<Move> movesPk3py2;
    private FloatingActionButton next;
    private int cont;
    private  ListAdapter listAdapter;
    private List<Boolean> selected;
    private ArrayList<Move> mchoosed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movements_picker);

        next=findViewById(R.id.nextB);

        Bundle b=getIntent().getExtras();
        handler=new DBHandler(this);
        movesPk1py1=handler.getMovesFromPokemon(b.getInt("pk1py1"));
        movesPk2py1=handler.getMovesFromPokemon(b.getInt("pk2py1"));
        movesPk3py1=handler.getMovesFromPokemon(b.getInt("pk3py1"));
        movesPk1py2=handler.getMovesFromPokemon(b.getInt("pk1py2"));
        movesPk2py2=handler.getMovesFromPokemon(b.getInt("pk2py2"));
        movesPk3py2=handler.getMovesFromPokemon(b.getInt("pk3py2"));

        moves = handler.getMoves();

        RecyclerView recyclerView = findViewById(R.id.recyclerview_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listAdapter = new ListAdapter(movesPk1py1,MovementsPicker.this);
        recyclerView.setAdapter(listAdapter);
        selected = new ArrayList<>();
        mchoosed = new ArrayList<>();
        deselect();


        listAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int position = recyclerView.getChildAdapterPosition(view);
                if(!(getCountSelected() == 4)){
                    if(selected.get(position)){
                        selected.set(position,false);
                        mchoosed.remove(listAdapter.mData.get(position));
                        listAdapter.holder.cardDeselect();
                    }else{
                        selected.set(position,true);
                        mchoosed.remove(listAdapter.mData.get(position));
                        listAdapter.holder.cardSelect();
                    }
                }else if(selected.get(position)){
                    selected.set(position,false);
                    mchoosed.remove(listAdapter.mData.get(position));
                    listAdapter.holder.cardDeselect();
                }
                if (getCountSelected() == 3) {
                    next.setVisibility(View.VISIBLE);
                } else {
                    next.setVisibility(View.INVISIBLE);
                }
            }
        });

        cont = 1;

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (cont){
                    case 1://cambia al segundo pokemon del primer jugador
                        listAdapter = new ListAdapter(movesPk2py1,MovementsPicker.this);
                        recyclerView.setAdapter(listAdapter);
                        break;
                    case 2:
                        listAdapter = new ListAdapter(movesPk3py1,MovementsPicker.this);
                        recyclerView.setAdapter(listAdapter);
                        break;
                    case 3:
                        listAdapter = new ListAdapter(movesPk1py2,MovementsPicker.this);
                        recyclerView.setAdapter(listAdapter);
                        break;
                    case 4:
                        listAdapter = new ListAdapter(movesPk2py2,MovementsPicker.this);
                        recyclerView.setAdapter(listAdapter);
                        break;
                    case 5:
                        listAdapter = new ListAdapter(movesPk3py2,MovementsPicker.this);
                        recyclerView.setAdapter(listAdapter);
                        break;
                    case 6:
                        Intent intent = new Intent(MovementsPicker.this, Combat.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        break;

                }
                cont ++;
            }
        });

    }
    // Método auxiliar que cuenta el número de elementos seleccionados
    private int getCountSelected() {
        int toret = 0;
        for (Boolean b : selected) {
            if (b) toret++;
        }
        return toret;
    }

    public void deselect() {
        for (int i = 0; i < listAdapter.mData.size(); i++) {
            selected.add(false);
        }
    }
}