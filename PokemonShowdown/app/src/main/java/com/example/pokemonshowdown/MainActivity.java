package com.example.pokemonshowdown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Pokemon> pokemonList;
    private DBHandler handler;
    private PokemonRecyclerViewAdapter myAdapter;

    //VIEWS
    private LinearLayout ll;
    private FloatingActionButton next;
    private ImageView pantJ;

    private boolean changeActivity = false;

    private ArrayList<Pokemon>pPlyr1;
    private ArrayList<Pokemon>pPlyr2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //VIEWS
        ll = findViewById(R.id.linearLayoutP);
        next = findViewById(R.id.nextB);
        pantJ = findViewById(R.id.pantalla_jugador);
        //INVISIBLE
        ll.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);
        pantJ.setVisibility(View.VISIBLE);


        //pantalla para indicar el turno del jugador
        pantJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pantJ.setVisibility(View.INVISIBLE);
                ll.setVisibility(View.VISIBLE);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillPokemons();
                if (changeActivity) {
                    Intent intent = new Intent(MainActivity.this, MovementsPicker.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                } else {
                    //CAMBIO DE TURNO
                    changeActivity = true;
                    //pantJ.setImageResource();
                    ll.setVisibility(View.INVISIBLE);
                    next.setVisibility(View.INVISIBLE);
                    pantJ.setVisibility(View.VISIBLE);
                }
            }
        });

        initMoves();
        initPokemons();

        handler = new DBHandler(this);
        pokemonList = handler.getPokemons();
        RecyclerView rv = findViewById(R.id.recyclerview_id);
        ViewHolder p = new ViewHolder(next);
        myAdapter = new PokemonRecyclerViewAdapter(this, pokemonList, p);
        rv.setLayoutManager(new GridLayoutManager(this, 3));
        rv.setAdapter(myAdapter);

    }

    private void initMoves() {
        handler = new DBHandler(this);

        // water type
        handler.addNewMove(new Move(1, "Burbuja", 40, 100, -1, 0, 18, 0, false, false));
        handler.addNewMove(new Move(2, "Cascada", 80, 100, -1, 0, 18, 0, false, false));
        handler.addNewMove(new Move(3, "Hidrobomba", 110, 80, -1, 0, 18, 0, false, false));
        handler.addNewMove(new Move(4, "Martillazo", 100, 90, -1, 0, 18, 0, false, false));
        handler.addNewMove(new Move(5, "Pistola agua", 40, 100, -1, 0, 18, 0, false, false));
        handler.addNewMove(new Move(6, "Rayo burbuja", 65, 100, -1, 0, 18, 0, false, false));
        handler.addNewMove(new Move(7, "Surf", 90, 100, -1, 0, 18, 0, false, false));
        handler.addNewMove(new Move(8, "Tenaza", 122, 85, -1, 0, 18, 0, false, false));
        // bug type
        handler.addNewMove(new Move(9, "Chupavidas", 80, 100, -1, 0, 1, 40, false, false));
        handler.addNewMove(new Move(10, "Doble ataque", 50, 100, 3, 20, 1, 0, false, false));
        handler.addNewMove(new Move(11, "Pin misil", 87, 95, -1, 0, 1, 0, false, false));
        // dragon type
        handler.addNewMove(new Move(12, "Furia dragón", 40, 100, -1, 0, 3, 0, false, false));
        // electric type
        handler.addNewMove(new Move(13, "Impactrueno", 40, 100, 1, 10, 4, 0, false, false));
        handler.addNewMove(new Move(14, "Puño trueno", 75, 100, 1, 10, 4, 0, false, false));
        handler.addNewMove(new Move(15, "Rayo", 90, 100, 1, 10, 4, 0, false, false));
        handler.addNewMove(new Move(16, "Onda trueno", 0, 90, 1, 100, 4, 0, true, false));
        // ghost type
        handler.addNewMove(new Move(17, "Lengüetazo", 30, 100, 1, 30, 9, 0, false, false));
        handler.addNewMove(new Move(18, "Tinieblas", 50, 100, -1, 0, 9, 0, false, false));
        // fire type
        handler.addNewMove(new Move(19, "Ascuas", 40, 100, 2, 10, 7, 0, false, false));
        handler.addNewMove(new Move(20, "Giro fuego", 122, 85, -1, 0, 7, 0, false, false));
        handler.addNewMove(new Move(21, "Lanzallamas", 90, 100, 2, 10, 7, 0, false, false));
        handler.addNewMove(new Move(22, "Llamarada", 110, 85, 2, 30, 7, 0, false, false));
        // ice type
        handler.addNewMove(new Move(23, "Puño hielo", 75, 100, 5, 10, 12, 0, false, false));
        handler.addNewMove(new Move(24, "Rayo aurora", 65, 100, -1, 0, 12, 0, false, false));
        handler.addNewMove(new Move(25, "Rayo hielo", 90, 100, 5, 10, 12, 0, false, false));
        handler.addNewMove(new Move(26, "Ventisca", 110, 70, 5, 10, 12, 0, false, false));
        // fight type
        handler.addNewMove(new Move(27, "Doble patada", 60, 100, -1, 0, 6, 0, false, false));
        handler.addNewMove(new Move(28, "Golpe kárate", 50, 100, -1, 0, 6, 0, false, false));
        handler.addNewMove(new Move(29, "Patada baja", 60, 100, -1, 0, 6, 0, false, false));
        handler.addNewMove(new Move(30, "Patada giro", 60, 85, -1, 0, 6, 0, false, false));
        handler.addNewMove(new Move(31, "Sísmico", 50, 100, -1, 0, 6, 0, false, false));
        handler.addNewMove(new Move(32, "Sumisión", 80, 100, -1, 0, 6, -25, false, false));
        // normal type
        handler.addNewMove(new Move(33, "Agarre", 55, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(34, "Arañazo", 40, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(35, "Atadura", 52, 85, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(36, "Ataque furia", 69, 85, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(37, "Ataque rápido", 40, 100, -1, 0, 13, 0, false, true));
        handler.addNewMove(new Move(38, "Atizar", 80, 75, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(39, "Autodestruccion", 200, 100, -1, 0, 13, -100, false, false));
        handler.addNewMove(new Move(40, "Beso amoroso", 0, 75, 4, 100, 13, 0, true, false));
        handler.addNewMove(new Move(41, "Bomba huevo", 100, 75, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(42, "Bomba sónica", 20, 90, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(43, "Bombardeo", 69, 85, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(44, "Cabezazo", 130, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(45, "Canto", 0, 55, 4, 100, 13, 0, false, false));
        handler.addNewMove(new Move(46, "Clavo cañon", 69, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(47, "Constricción", 45, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(48, "Cornada", 65, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(49, "Corte", 50, 95, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(50, "Cuchillada", 70, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(51, "Derribo", 90, 85, -1, 0, 13, -25, false, false));
        handler.addNewMove(new Move(52, "Día de pago", 40, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(53, "Doble bofetón", 55, 85, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(54, "Doble filo", 120, 100, -1, 0, 13, -25, false, false));
        handler.addNewMove(new Move(55, "Explosión", 250, 100, -1, 0, 13, -100, false, false));
        handler.addNewMove(new Move(56, "Fuerza", 80, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(57, "Furia", 40, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(58, "Golpe cabeza", 70, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(59, "Golpe cuerpo", 85, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(60, "Golpe furia", 60, 80, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(61, "Guillotina", 4000, 30, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(62, "Hipercolmillo", 80, 90, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(63, "Hiperrayo", 150, 90, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(64, "Megapatada", 120, 75, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(65, "Megapuño", 80, 85, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(66, "Meteoros", 60, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(67, "Ovocuracion", 0, 100, -1, 0, 13, 50, true, false));
        handler.addNewMove(new Move(68, "Perforador", 4000, 30, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(69, "Pisotón", 65, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(70, "Placaje", 41, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(71, "Puño cometa", 90, 85, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(72, "Puño mareo", 70, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(73, "Recuperacion", 0, 75, -1, 0, 13, 50, true, false));
        handler.addNewMove(new Move(74, "Restriccion", 10, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(75, "Supersónico", 100, 55, -1, 0, 13, 0, false, false));
        handler.addNewMove(new Move(76, "Triataque", 80, 100, -1, 0, 13, 0, false, false));
        // grass type
        handler.addNewMove(new Move(77, "Absorber", 20, 100, -1, 0, 10, 50, false, false));
        handler.addNewMove(new Move(78, "Danza pétalo", 120, 100, -1, 0, 10, 0, false, false));
        handler.addNewMove(new Move(79, "Espora", 0, 100, 4, 100, 10, 0, false, false));
        handler.addNewMove(new Move(80, "Hoja afilada", 55, 95, -1, 0, 10, 0, false, false));
        handler.addNewMove(new Move(81, "Látigo cepa", 45, 100, -1, 0, 10, 0, false, false));
        handler.addNewMove(new Move(82, "Megaagotar", 40, 100, -1, 0, 10, 0, false, false));
        handler.addNewMove(new Move(83, "Paralizador", 0, 100, 1, 100, 10, 0, true, false));
        handler.addNewMove(new Move(84, "Rayo solar", 120, 100, -1, 0, 10, 0, false, false));
        handler.addNewMove(new Move(85, "Somnífero", 0, 75, 4, 100, 10, 0, true, false));
        // psychic type
        handler.addNewMove(new Move(86, "Confusión", 50, 100, -1, 0, 15, 0, false, false));
        handler.addNewMove(new Move(87, "Hipnosis", 0, 70, 4, 100, 15, 0, true, false));
        handler.addNewMove(new Move(88, "Psicoonda", 75, 100, -1, 0, 15, 0, false, false));
        handler.addNewMove(new Move(89, "Psicorrayo", 65, 100, -1, 0, 15, 0, false, false));
        handler.addNewMove(new Move(90, "Psíquico", 90, 100, -1, 0, 15, 0, false, false));
        // rock type
        handler.addNewMove(new Move(91, "Avalancha", 75, 90, -1, 0, 16, 0, false, false));
        handler.addNewMove(new Move(92, "Lanzarrocas", 50, 90, -1, 0, 16, 0, false, false));
        // dark type
        handler.addNewMove(new Move(93, "Mordisco", 60, 100, -1, 0, 2, 0, false, false));
        // gruond type
        handler.addNewMove(new Move(94, "Fisura", 65, 30, -1, 0, 11, 0, false, false));
        handler.addNewMove(new Move(95, "Hueso palo", 65, 85, -1, 0, 11, 0, false, false));
        handler.addNewMove(new Move(96, "Huesomerang", 100, 90, -1, 0, 11, 0, false, false));
        handler.addNewMove(new Move(97, "Terremoto", 100, 100, -1, 0, 11, 0, false, false));
        // poison type
        handler.addNewMove(new Move(98, "Ácido", 40, 100, -1, 0, 9, 0, false, false));
        handler.addNewMove(new Move(99, "Gas venenoso", 0, 90, 3, 100, 9, 0, true, false));
        handler.addNewMove(new Move(100, "Picotazo veneno", 15, 100, 3, 20, 9, 0, false, false));
        handler.addNewMove(new Move(101, "Polución", 30, 70, 3, 40, 9, 0, false, false));
        handler.addNewMove(new Move(102, "Polvo veneno", 0, 75, 3, 100, 9, 0, false, false));
        handler.addNewMove(new Move(103, "Residuos", 65, 100, 3, 30, 9, 0, false, false));
        handler.addNewMove(new Move(104, "Tóxico", 0, 90, 3, 100, 9, 0, true, false));
        // flying type
        handler.addNewMove(new Move(105, "Ataque aéreo", 140, 90, -1, 0, 8, 0, false, false));
        handler.addNewMove(new Move(106, "Ataque ala", 60, 100, -1, 0, 8, 0, false, false));
        handler.addNewMove(new Move(107, "Pico taladro", 80, 100, -1, 0, 8, 0, false, false));
        handler.addNewMove(new Move(108, "Picotazo", 35, 100, -1, 0, 8, 0, false, false));
        handler.addNewMove(new Move(109, "Tornado", 40, 100, -1, 0, 8, 0, false, false));
        handler.addNewMove(new Move(110, "Vuelo", 90, 95, -1, 0, 8, 0, false, false));
    }

    private void initPokemons() {
        Pokemon p = new Pokemon(3, "Venusaur", 10, 14, 80, 100, 100, 80, R.drawable.p003, R.drawable.p003b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(6, "Charizard", 7, 8, 78, 109, 85, 100, R.drawable.p006, R.drawable.p006b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(9, "Blastoise", 18, 0, 79, 85, 105, 78, R.drawable.p009, R.drawable.p009b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(12, "Butterfree", 1, 8, 60, 90, 80, 70, R.drawable.p012, R.drawable.p012b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(15, "Beedrill", 1, 14, 65, 150, 80, 145, R.drawable.p015, R.drawable.p015b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(18, "Pidgeot", 13, 8, 83, 80, 75, 101, R.drawable.p018, R.drawable.p018b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(20, "Raticate", 13, 0, 55, 81, 70, 97, R.drawable.p020, R.drawable.p020b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(22, "Fearow", 13, 8, 65, 90, 65, 100, R.drawable.p022, R.drawable.p022b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(24, "Arbok", 14, 0, 60, 95, 79, 80, R.drawable.p024, R.drawable.p024b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(25, "Pikachu", 4, 0, 35, 55, 50, 90, R.drawable.p025, R.drawable.p025b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(26, "Raichu", 4, 0, 60, 90, 80, 110, R.drawable.p026, R.drawable.p026b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(28, "Sandslash", 11, 0, 75, 100, 110, 65, R.drawable.p028, R.drawable.p028b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(31, "Nidoqueen", 14, 11, 90, 92, 87, 76, R.drawable.p031, R.drawable.p031b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(34, "Nidoking", 14, 11, 81, 102, 77, 85, R.drawable.p034, R.drawable.p034b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(36, "Clefable", 5, 0, 95, 95, 90, 60, R.drawable.p036, R.drawable.p036b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(38, "Ninetales", 7, 0, 73, 81, 100, 100, R.drawable.p038, R.drawable.p038b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(40, "Wigglytuff", 13, 5, 140, 85, 50, 45, R.drawable.p040, R.drawable.p040b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(42, "Golbat", 14, 8, 75, 80, 75, 90, R.drawable.p042, R.drawable.p042b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(45, "Vileplume", 10, 14, 75, 110, 90, 50, R.drawable.p045, R.drawable.p045b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(47, "Parasect", 1, 10, 60, 95, 80, 30, R.drawable.p047, R.drawable.p047b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(49, "Venomoth", 1, 14, 70, 90, 75, 90, R.drawable.p049, R.drawable.p049b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(51, "Dugtrio", 11, 0, 35, 100, 70, 120, R.drawable.p051, R.drawable.p051b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(52, "Meowth", 13, 0, 40, 45, 40, 90, R.drawable.p052, R.drawable.p052b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(53, "Persian", 13, 0, 65, 70, 65, 115, R.drawable.p053, R.drawable.p053b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(54, "Psyduck", 18, 0, 50, 69, 50, 100, R.drawable.p054, R.drawable.p054b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(55, "Golduck", 18, 0, 80, 95, 80, 85, R.drawable.p055, R.drawable.p055b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(57, "Primeape", 6, 0, 65, 105, 70, 95, R.drawable.p057, R.drawable.p057b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(59, "Arcanine", 7, 0, 90, 110, 85, 95, R.drawable.p059, R.drawable.p059b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(62, "Poliwrath", 18, 6, 90, 95, 95, 70, R.drawable.p062, R.drawable.p062b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(65, "Alakazam", 15, 0, 55, 135, 95, 120, R.drawable.p065, R.drawable.p065b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(68, "Machamp", 6, 0, 90, 130, 85, 55, R.drawable.p068, R.drawable.p068b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(71, "Victreebel", 10, 14, 80, 105, 70, 70, R.drawable.p071, R.drawable.p071b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(73, "Tentacruel", 18, 14, 80, 80, 120, 100, R.drawable.p073, R.drawable.p073b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(76, "Golem", 16, 11, 80, 120, 130, 45, R.drawable.p076, R.drawable.p076b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(78, "Rapidash", 7, 0, 65, 100, 80, 105, R.drawable.p078, R.drawable.p078b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(80, "Slowbro", 18, 15, 95, 100, 110, 30, R.drawable.p080, R.drawable.p080b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(80, "Slowbro", 18, 15, 95, 100, 110, 30, R.drawable.p080, R.drawable.p080b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(83, "Farfetch´d", 13, 8, 52, 90, 62, 60, R.drawable.p083, R.drawable.p083b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(85, "Dodrio", 13, 8, 60, 110, 70, 110, R.drawable.p085, R.drawable.p085b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(87, "Dewgong", 18, 12, 90, 70, 95, 70, R.drawable.p087, R.drawable.p087b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(89, "Muk", 14, 0, 105, 105, 100, 50, R.drawable.p089, R.drawable.p089b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(91, "Cloyster", 18, 12, 50, 95, 180, 70, R.drawable.p091, R.drawable.p091b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(94, "Gengar", 9, 14, 80, 130, 95, 110, R.drawable.p094, R.drawable.p094b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(95, "Onix", 16, 11, 35, 45, 160, 70, R.drawable.p095, R.drawable.p095b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(97, "Hypno", 15, 0, 85, 73, 115, 67, R.drawable.p097, R.drawable.p097b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(99, "Kingler", 18, 0, 55, 130, 115, 75, R.drawable.p099, R.drawable.p099b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(101, "Electrode", 4, 0, 60, 80, 80, 150, R.drawable.p101, R.drawable.p101b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(103, "Exeggutor", 10, 15, 95, 125, 85, 55, R.drawable.p103, R.drawable.p103b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(105, "Marowak", 11, 0, 60, 80, 110, 46, R.drawable.p105, R.drawable.p105b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(106, "Hitmonlee", 6, 0, 60, 120, 110, 87, R.drawable.p106, R.drawable.p106b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(107, "Hitmonchan", 6, 0, 50, 105, 110, 76, R.drawable.p107, R.drawable.p107b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(108, "Lickitung", 13, 0, 90, 60, 76, 30, R.drawable.p108, R.drawable.p108b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(110, "Weezing", 14, 0, 65, 90, 120, 60, R.drawable.p110, R.drawable.p110b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(112, "Rhydon", 11, 16, 105, 130, 120, 40, R.drawable.p112, R.drawable.p112b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(113, "Chansey", 13, 0, 250, 35, 105, 50, R.drawable.p113, R.drawable.p113b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(114, "Tangela", 10, 0, 65, 100, 115, 60, R.drawable.p114, R.drawable.p114b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(115, "Kangaskhan", 13, 0, 105, 95, 80, 90, R.drawable.p115, R.drawable.p115b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(117, "Seadra", 18, 0, 55, 95, 95, 85, R.drawable.p117, R.drawable.p117b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(119, "Seaking", 18, 0, 80, 92, 80, 68, R.drawable.p119, R.drawable.p119b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(121, "Starmie", 18, 15, 60, 100, 85, 115, R.drawable.p121, R.drawable.p121b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(122, "Mr.Mime", 15, 5, 40, 100, 120, 90, R.drawable.p122, R.drawable.p122b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(123, "Scyther", 1, 8, 70, 110, 80, 105, R.drawable.p123, R.drawable.p123b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(124, "Jynx", 12, 15, 65, 115, 95, 95, R.drawable.p124, R.drawable.p124b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(125, "Electabuzz", 4, 0, 65, 95, 85, 105, R.drawable.p125, R.drawable.p125b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(126, "Magmar", 7, 0, 65, 100, 85, 93, R.drawable.p126, R.drawable.p126b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(127, "Pinsir", 1, 0, 125, 100, 70, 85, R.drawable.p127, R.drawable.p127b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(128, "Tauros", 13, 0, 75, 100, 95, 110, R.drawable.p128, R.drawable.p128b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(129, "Magikarp", 18, 0, 60, 15, 55, 80, R.drawable.p129, R.drawable.p129b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(130, "Gyarados", 18, 8, 95, 125, 100, 81, R.drawable.p130, R.drawable.p130b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(131, "Lapras", 18, 12, 130, 85, 95, 60, R.drawable.p131, R.drawable.p131b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(133, "Eevee", 13, 0, 55, 55, 65, 55, R.drawable.p133, R.drawable.p133b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(134, "Vaporeon", 18, 0, 130, 110, 95, 65, R.drawable.p134, R.drawable.p134b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(135, "Jolteon", 4, 0, 65, 110, 95, 130, R.drawable.p135, R.drawable.p135b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(136, "Flareon", 7, 0, 65, 130, 110, 65, R.drawable.p136, R.drawable.p136b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(137, "Porygon", 13, 0, 65, 85, 75, 30, R.drawable.p137, R.drawable.p137b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(139, "Omastar", 16, 18, 70, 115, 125, 55, R.drawable.p139, R.drawable.p139b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(141, "Kabutops", 16, 18, 60, 115, 105, 80, R.drawable.p141, R.drawable.p141b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(142, "Aerodactyl", 16, 8, 80, 105, 75, 130, R.drawable.p142, R.drawable.p142b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(143, "Snorlax", 13, 0, 160, 110, 110, 30, R.drawable.p143, R.drawable.p143b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(144, "Articuno", 12, 8, 90, 95, 125, 85, R.drawable.p144, R.drawable.p144b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(145, "Zapdos", 4, 8, 90, 125, 90, 100, R.drawable.p145, R.drawable.p145b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(146, "Moltres", 7, 8, 90, 125, 90, 90, R.drawable.p146, R.drawable.p146b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(149, "Dragonite", 3, 8, 91, 134, 100, 80, R.drawable.p149, R.drawable.p149b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(150, "Mewtwo", 15, 0, 106, 154, 90, 130, R.drawable.p150, R.drawable.p150b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);

        p = new Pokemon(151, "Mew", 15, 0, 100, 100, 100, 100, R.drawable.p151, R.drawable.p151b);
        p.addMovesById(new int[]{}, handler.getMoves());
        handler.addNewPokemon(p);
    }

    private void fillPokemons(){
        if(changeActivity){
            pPlyr1=myAdapter.getSelected();
        }else{
            pPlyr2=myAdapter.getSelected();
        }
    }


}
