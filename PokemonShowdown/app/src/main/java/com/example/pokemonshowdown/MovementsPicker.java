package com.example.pokemonshowdown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    private ThisViewHolder vh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movements_picker);
        selected = new ArrayList<>();
        mchoosed = new ArrayList<>();
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


        vh = new ThisViewHolder(next);
        RecyclerView recyclerView = findViewById(R.id.recyclerview_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listAdapter = new ListAdapter(movesPk1py1,MovementsPicker.this, vh);
        recyclerView.setAdapter(listAdapter);

        cont = 1;

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (cont){
                    case 1://cambia al segundo pokemon del primer jugador
                        listAdapter = new ListAdapter(movesPk2py1,MovementsPicker.this, vh);
                        recyclerView.setAdapter(listAdapter);
                        break;
                    case 2:
                        listAdapter = new ListAdapter(movesPk3py1,MovementsPicker.this, vh);
                        recyclerView.setAdapter(listAdapter);
                        break;
                    case 3:
                        listAdapter = new ListAdapter(movesPk1py2,MovementsPicker.this, vh);
                        recyclerView.setAdapter(listAdapter);
                        break;
                    case 4:
                        listAdapter = new ListAdapter(movesPk2py2,MovementsPicker.this, vh);
                        recyclerView.setAdapter(listAdapter);
                        break;
                    case 5:
                        listAdapter = new ListAdapter(movesPk3py2,MovementsPicker.this, vh);
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


}