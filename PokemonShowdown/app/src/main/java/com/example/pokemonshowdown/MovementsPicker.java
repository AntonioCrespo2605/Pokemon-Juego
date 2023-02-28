package com.example.pokemonshowdown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MovementsPicker extends AppCompatActivity {

    private DBHandler handler;
    private List<Move> moves;
    //Lista de ataques
    private List<Move> movesPk1py1;
    private List<Move> movesPk2py1;
    private List<Move> movesPk3py1;
    private List<Move> movesPk1py2;
    private List<Move> movesPk2py2;
    private List<Move> movesPk3py2;

    private ListAdapter listAdapter;
    private ThisViewHolder vh;

    private int cont;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movements_picker);

        //Bundle
        Bundle b = getIntent().getExtras();
        handler = new DBHandler(this);
        movesPk1py1 = handler.getMovesFromPokemon(b.getInt("pk1py1"));
        movesPk2py1 = handler.getMovesFromPokemon(b.getInt("pk2py1"));
        movesPk3py1 = handler.getMovesFromPokemon(b.getInt("pk3py1"));
        movesPk1py2 = handler.getMovesFromPokemon(b.getInt("pk1py2"));
        movesPk2py2 = handler.getMovesFromPokemon(b.getInt("pk2py2"));
        movesPk3py2 = handler.getMovesFromPokemon(b.getInt("pk3py2"));

        //No se para que es este
        moves = handler.getMoves();

        //VIEWS
        FloatingActionButton next = (FloatingActionButton) findViewById(R.id.nextB);
        next.setVisibility(View.INVISIBLE);
        next.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(MovementsPicker.this, R.color.redpy)));
        ImageView pantJ = (ImageView) findViewById(R.id.pantalla_jugador);
        pantJ.setVisibility(View.VISIBLE);
        pantJ.setImageResource(R.drawable.t1m);
        ImageView actual_pokemon;
        vh = new ThisViewHolder(next);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //primer pokemon del primer jugador
        listAdapter = new ListAdapter(movesPk1py1, MovementsPicker.this, vh);
        recyclerView.setAdapter(listAdapter);

        //pantalla para indicar el turno del jugador
        pantJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pantJ.setVisibility(View.INVISIBLE);
            }
        });

        cont = 1;
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (cont) {
                    case 1://cambia al segundo pokemon del primer jugador
                        movesPk1py1 = listAdapter.getSelected();
                        next.setVisibility(View.INVISIBLE);
                        listAdapter = new ListAdapter(movesPk2py1, MovementsPicker.this, vh);
                        recyclerView.setAdapter(listAdapter);
                        break;
                    case 2://cambia al tercer pokemon del primer jugador
                        movesPk2py1 = listAdapter.getSelected();
                        next.setVisibility(View.INVISIBLE);
                        listAdapter = new ListAdapter(movesPk3py1, MovementsPicker.this, vh);
                        recyclerView.setAdapter(listAdapter);
                        break;
                    case 3://cambia al primer pokemon del segundo jugador
                        movesPk3py1 = listAdapter.getSelected();
                        pantJ.setVisibility(View.VISIBLE);
                        pantJ.setImageResource(R.drawable.t2m);
                        next.setVisibility(View.INVISIBLE);
                        next.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(MovementsPicker.this, R.color.bluepy)));
                        listAdapter = new ListAdapter(movesPk1py2, MovementsPicker.this, vh);
                        recyclerView.setAdapter(listAdapter);
                        break;
                    case 4://cambia al segundo pokemon del segundo jugador
                        movesPk1py2 = listAdapter.getSelected();
                        next.setVisibility(View.INVISIBLE);
                        listAdapter = new ListAdapter(movesPk2py2, MovementsPicker.this, vh);
                        recyclerView.setAdapter(listAdapter);
                        break;
                    case 5://cambia al tercer pokemon del segundo jugador
                        movesPk2py2 = listAdapter.getSelected();
                        next.setVisibility(View.INVISIBLE);
                        listAdapter = new ListAdapter(movesPk3py2, MovementsPicker.this, vh);
                        recyclerView.setAdapter(listAdapter);
                        break;
                    case 6://cambia de activity y envia los pokemon con sus movimientos
                        movesPk1py1 = listAdapter.getSelected();
                        intent = new Intent(MovementsPicker.this, Combat.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("pk1py1", b.getInt("pk1py1"));
                        intent = getMoves(movesPk1py1, 1, 1);

                        intent.putExtra("pk2py1", b.getInt("pk2py1"));
                        intent = getMoves(movesPk2py1, 2, 1);

                        intent.putExtra("pk3py1", b.getInt("pk3py1"));
                        intent = getMoves(movesPk3py1, 3, 1);

                        intent.putExtra("pk1py2", b.getInt("pk1py2"));
                        intent = getMoves(movesPk1py2, 1, 2);

                        intent.putExtra("pk2py2", b.getInt("pk2py2"));
                        intent = getMoves(movesPk2py2, 2, 2);

                        intent.putExtra("pk3py2", b.getInt("pk3py2"));
                        getMoves(movesPk3py2, 3, 2);

                        startActivity(intent);
                        finish();
                        break;
                }
                cont++;
            }
        });

    }

    private Intent getMoves(List<Move> moves, int p, int j) {
        switch (moves.size()) {
            case 4:
                intent.putExtra("mv4pk" + p + "py" + j, moves.get(3).getId());
            case 3:
                intent.putExtra("mv3pk" + p + "py" + j, moves.get(2).getId());
            case 2:
                intent.putExtra("mv2pk" + p + "py" + j, moves.get(1).getId());
            case 1:
                intent.putExtra("mv1pk" + p + "py" + j, moves.get(0).getId());
                break;
        }
        return intent;
    }

}