package com.example.pokemonshowdown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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



    //esto es para cada vez que se le da a ok comprobar lo que tiene que hacer
    private int contConfirm=0;

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

        moves = new ArrayList<>();
        moves.add(new Move("Guillotina", 90000, 30));
        moves.add(new Move("Salpicadura", 0, 100));
        moves.add(new Move("Ataque Ala", 50, 100));
        moves.add(new Move("Rapidez", 50, 100));
        moves.add(new Move("Golpe alto", 80, 50));
        moves.add(new Move("Guillotina", 90000, 30));
        moves.add(new Move("Salpicadura", 0, 100));
        moves.add(new Move("Ataque Ala", 50, 100));
        moves.add(new Move("Rapidez", 50, 100));
        moves.add(new Move("Golpe alto", 80, 50));
        moves.add(new Move("Guillotina", 90000, 30));
        moves.add(new Move("Salpicadura", 0, 100));
        moves.add(new Move("Ataque Ala", 50, 100));
        moves.add(new Move("Rapidez", 50, 100));
        moves.add(new Move("Golpe alto", 80, 50));

        RecyclerView recyclerView = findViewById(R.id.recyclerview_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ListAdapter listAdapter = new ListAdapter(moves, this);
        //ListAdapter listAdapter = new ListAdapter(movesPk1py1, this);
        recyclerView.setAdapter(listAdapter);
        /*
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (contConfirm){
                    case 0://cambia al segundo pokemon del primer jugador
                    break;
                    case 1://cambia al tercer pokemon del primer jugador
                    break;
                    case 2://despliega la pantalla de intermedio y cambia al primer pokemon del segundo jugador
                    break;
                    case 3://cambia al segundo pokemon del segundo jugador
                    break;
                    case 4://cambia al tercer pokemon del segundo jugador
                    break;
                    case 5://cambia de activity
                    break;
                }
                contConfirm++;
            }
        });*/

        //borrar mas tarde(pruebas)
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovementsPicker.this, Combat.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

    }


}