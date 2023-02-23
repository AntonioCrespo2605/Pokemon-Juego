package com.example.pokemonshowdown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Pokemon> pokemonList ;
    private DBHandler handler;

    @SuppressLint({"ResourceType", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        pokemonList = new ArrayList<>();

        pokemonList.add(new Pokemon("Venusaur", R.drawable.p003));
        pokemonList.add(new Pokemon("Charizard", R.drawable.p006));
        pokemonList.add(new Pokemon("Blastoise", R.drawable.p009));
        pokemonList.add(new Pokemon("Venusaur", R.drawable.p003));
        pokemonList.add(new Pokemon("Charizard", R.drawable.p006));
        pokemonList.add(new Pokemon("Blastoise", R.drawable.p009));
        pokemonList.add(new Pokemon("Venusaur", R.drawable.p003));
        pokemonList.add(new Pokemon("Charizard", R.drawable.p006));
        pokemonList.add(new Pokemon("Blastoise", R.drawable.p009));
        pokemonList.add(new Pokemon("Venusaur", R.drawable.p003));
        pokemonList.add(new Pokemon("Charizard", R.drawable.p006));
        pokemonList.add(new Pokemon("Blastoise", R.drawable.p009));
        pokemonList.add(new Pokemon("Venusaur", R.drawable.p003));
        pokemonList.add(new Pokemon("Charizard", R.drawable.p006));
        pokemonList.add(new Pokemon("Blastoise", R.drawable.p009));
        pokemonList.add(new Pokemon("Venusaur", R.drawable.p003));
        pokemonList.add(new Pokemon("Charizard", R.drawable.p006));
        pokemonList.add(new Pokemon("Blastoise", R.drawable.p009));
        pokemonList.add(new Pokemon("Venusaur", R.drawable.p003));
        pokemonList.add(new Pokemon("Charizard", R.drawable.p006));
        pokemonList.add(new Pokemon("Blastoise", R.drawable.p009));*/
        initMoves();
        initPokemons();


        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, pokemonList);
        rv.setLayoutManager(new GridLayoutManager(this,3));
        rv.setAdapter(myAdapter);
        
    }

    private void initMoves(){
        handler=new DBHandler(this);

        // water type
        handler.addNewMove ( new Move(1, "Burbuja", 40, 100, -1, 0, 18, 0, false, false));
        handler.addNewMove ( new Move(2, "Cascada", 80, 100, -1, 0, 18, 0, false, false));
        handler.addNewMove ( new Move(3, "Hidrobomba", 110, 80, -1, 0, 18, 0, false, false));
        handler.addNewMove ( new Move(4, "Martillazo", 100, 90, -1, 0, 18, 0, false, false));
        handler.addNewMove ( new Move(5, "Pistola agua", 40, 100, -1, 0, 18, 0, false, false));
        handler.addNewMove ( new Move(6, "Rayo burbuja", 65, 100, -1, 0, 18, 0, false, false));
        handler.addNewMove ( new Move(7, "Surf", 90, 100, -1, 0, 18, 0, false, false));
        handler.addNewMove ( new Move(8, "Tenaza", 122, 85, -1, 0, 18, 0, false, false));
        // bug type
        handler.addNewMove ( new Move(9, "Chupavidas", 80, 100, -1, 0, 1, 40, false, false));
        handler.addNewMove ( new Move(10, "Doble ataque", 50, 100, 3, 20, 1, 0, false, false));
        handler.addNewMove ( new Move(11, "Pin misil", 87, 95, -1, 0, 1, 0, false, false));
        // dragon type
        handler.addNewMove ( new Move(12, "Furia dragón", 40, 100, -1, 0, 3, 0, false, false));
        // electric type
        handler.addNewMove ( new Move(13, "Impactrueno", 40, 100, 1, 10, 4, 0, false, false));
        handler.addNewMove ( new Move(14, "Puño trueno", 75, 100, 1, 10, 4, 0, false, false));
        handler.addNewMove ( new Move(15, "Rayo", 90, 100, 1, 10, 4, 0, false, false));
        handler.addNewMove ( new Move(16, "Onda trueno", 0, 90, 1, 100, 4, 0, true, false));
        // ghost type
        handler.addNewMove ( new Move(17, "Lengüetazo", 30, 100, 1, 30, 9, 0, false, false));
        handler.addNewMove ( new Move(18, "Tinieblas", 50, 100, -1, 0, 9, 0, false, false));
        // fire type
        handler.addNewMove ( new Move(19, "Ascuas", 40, 100, 2, 10, 7, 0, false, false));
        handler.addNewMove ( new Move(20, "Giro fuego", 122, 85, -1, 0, 7, 0, false, false));
        handler.addNewMove ( new Move(21, "Lanzallamas", 90, 100, 2, 10, 7, 0, false, false));
        handler.addNewMove ( new Move(22, "Llamarada", 110, 85, 2, 30, 7, 0, false, false));
        // ice type
        handler.addNewMove ( new Move(23, "Puño hielo", 75, 100, 5, 10, 12, 0, false, false));
        handler.addNewMove ( new Move(24, "Rayo aurora", 65, 100, -1, 0, 12, 0, false, false));
        handler.addNewMove ( new Move(25, "Rayo hielo", 90, 100, 5, 10, 12, 0, false, false));
        handler.addNewMove ( new Move(26, "Ventisca", 110, 70, 5, 10, 12, 0, false, false));
        // fight type
        handler.addNewMove ( new Move(27, "Doble patada", 60, 100, -1, 0, 6, 0, false, false));
        handler.addNewMove ( new Move(28, "Golpe kárate", 50, 100, -1, 0, 6, 0, false, false));
        handler.addNewMove ( new Move(29, "Patada baja", 60, 100, -1, 0, 6, 0, false, false));
        handler.addNewMove ( new Move(30, "Patada giro", 60, 85, -1, 0, 6, 0, false, false));
        handler.addNewMove ( new Move(31, "Sísmico", 50, 100, -1, 0, 6, 0, false, false));
        handler.addNewMove ( new Move(32, "Sumisión", 80, 100, -1, 0, 6, -25, false, false));
        // normal type
        handler.addNewMove ( new Move(33, "Agarre", 55, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(34, "Arañazo", 40, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(35, "Atadura", 52, 85, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(36, "Ataque furia", 69, 85, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(37, "Ataque rápido", 40, 100, -1, 0, 13, 0, false, true));
        handler.addNewMove ( new Move(38, "Atizar", 80, 75, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(39, "Autodestruccion", 200, 100, -1, 0, 13, -100, false, false));
        handler.addNewMove ( new Move(40, "Beso amoroso", 0, 75, 4, 100, 13, 0, true, false));
        handler.addNewMove ( new Move(41, "Bomba huevo", 100, 75, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(42, "Bomba sónica", 20, 90, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(43, "Bombardeo", 69, 85, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(44, "Cabezazo", 130, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(45, "Canto", 0, 55, 4, 100, 13, 0, false, false));
        handler.addNewMove ( new Move(46, "Clavo cañon", 69, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(47, "Constricción", 45, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(48, "Cornada", 65, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(49, "Corte", 50, 95, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(50, "Cuchillada", 70, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(51, "Derribo", 90, 85, -1, 0, 13, -25, false, false));
        handler.addNewMove ( new Move(52, "Día de pago", 40, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(53, "Doble bofetón", 55, 85, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(54, "Doble filo", 120, 100, -1, 0, 13, -25, false, false));
        handler.addNewMove ( new Move(55, "Explosión", 250, 100, -1, 0, 13, -100, false, false));
        handler.addNewMove ( new Move(56, "Fuerza", 80, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(57, "Furia", 40, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(58, "Golpe cabeza", 70, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(59, "Golpe cuerpo", 85, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(60, "Golpe furia", 60, 80, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(61, "Guillotina", 4000, 30, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(62, "Hipercolmillo", 80, 90, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(63, "Hiperrayo", 150, 90, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(64, "Megapatada", 120, 75, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(65, "Megapuño", 80, 85, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(66, "Meteoros", 60, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(67, "Ovocuracion", 0, 100, -1, 0, 13, 50, true, false));
        handler.addNewMove ( new Move(68, "Perforador", 4000, 30, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(69, "Pisotón", 65, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(70, "Placaje", 41, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(71, "Puño cometa", 90, 85, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(72, "Puño mareo", 70, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(73, "Recuperacion", 0, 75, -1, 0, 13, 50, true, false));
        handler.addNewMove ( new Move(74, "Restriccion", 10, 100, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(75, "Supersónico", 100, 55, -1, 0, 13, 0, false, false));
        handler.addNewMove ( new Move(76, "Triataque", 80, 100, -1, 0, 13, 0, false, false));
        // grass type
        handler.addNewMove ( new Move(77, "Absorber", 20, 100, -1, 0, 10, 50, false, false));
        handler.addNewMove ( new Move(78, "Danza pétalo", 120, 100, -1, 0, 10, 0, false, false));
        handler.addNewMove ( new Move(79, "Espora", 0, 100, 4, 100, 10, 0, false, false));
        handler.addNewMove ( new Move(80, "Hoja afilada", 55, 95, -1, 0, 10, 0, false, false));
        handler.addNewMove ( new Move(81, "Látigo cepa", 45, 100, -1, 0, 10, 0, false, false));
        handler.addNewMove ( new Move(82, "Megaagotar", 40, 100, -1, 0, 10, 0, false, false));
        handler.addNewMove ( new Move(83, "Paralizador", 0, 100, 1, 100, 10, 0, true, false));
        handler.addNewMove ( new Move(84, "Rayo solar", 120, 100, -1, 0, 10, 0, false, false));
        handler.addNewMove ( new Move(85, "Somnífero", 0, 75, 4, 100, 10, 0, true, false));
        // psychic type
        handler.addNewMove ( new Move(86, "Confusión", 50, 100, -1, 0, 15, 0, false, false));
        handler.addNewMove ( new Move(87, "Hipnosis", 0, 70, 4, 100, 15, 0, true, false));
        handler.addNewMove ( new Move(88, "Psicoonda", 75, 100, -1, 0, 15, 0, false, false));
        handler.addNewMove ( new Move(89, "Psicorrayo", 65, 100, -1, 0, 15, 0, false, false));
        handler.addNewMove ( new Move(90, "Psíquico", 90, 100, -1, 0, 15, 0, false, false));
        // rock type
        handler.addNewMove ( new Move(91, "Avalancha", 75, 90, -1, 0, 16, 0, false, false));
        handler.addNewMove ( new Move(92, "Lanzarrocas", 50, 90, -1, 0, 16, 0, false, false));
        // dark type
        handler.addNewMove ( new Move(93, "Mordisco", 60, 100, -1, 0, 2, 0, false, false));
        // gruond type
        handler.addNewMove ( new Move(94, "Fisura", 65, 30, -1, 0, 11, 0, false, false));
        handler.addNewMove ( new Move(95, "Hueso palo", 65, 85, -1, 0, 11, 0, false, false));
        handler.addNewMove ( new Move(96, "Huesomerang", 100, 90, -1, 0, 11, 0, false, false));
        handler.addNewMove ( new Move(97, "Terremoto", 100, 100, -1, 0, 11, 0, false, false));
        // poison type
        handler.addNewMove ( new Move(98, "Ácido", 40, 100, -1, 0, 9, 0, false, false));
        handler.addNewMove ( new Move(99, "Gas venenoso", 0, 90, 3, 100, 9, 0, true, false));
        handler.addNewMove ( new Move(100, "Picotazo veneno", 15, 100, 3, 20, 9, 0, false, false));
        handler.addNewMove ( new Move(101, "Polución", 30, 70, 3, 40, 9, 0, false, false));
        handler.addNewMove ( new Move(102, "Polvo veneno", 0, 75, 3, 100, 9, 0, false, false));
        handler.addNewMove ( new Move(103, "Residuos", 65, 100, 3, 30, 9, 0, false, false));
        handler.addNewMove ( new Move(104, "Tóxico", 0, 90, 3, 100, 9, 0, true, false));
        // flying type
        handler.addNewMove (new Move(105, "Ataque aéreo", 140, 90, -1, 0, 8, 0, false, false));
        handler.addNewMove ( new Move(106, "Ataque ala", 60, 100, -1, 0, 8, 0, false, false));
        handler.addNewMove ( new Move(107, "Pico taladro", 80, 100, -1, 0, 8, 0, false, false));
        handler.addNewMove ( new Move(108, "Picotazo", 35, 100, -1, 0, 8, 0, false, false));
        handler.addNewMove ( new Move(109, "Tornado", 40, 100, -1, 0, 8, 0, false, false));
        handler.addNewMove ( new Move(110, "Vuelo", 90, 95, -1, 0, 8, 0, false, false));
    }

    /*int numDex, String name, int type1, int type2, int hp, int dmg, int def, int spd, int img, int imgB)*/
    private void initPokemons(){
        Pokemon p=new Pokemon(3, "Venusaur", 10, 14, 30, 30, 30, 30, R.drawable.p003, R.drawable.p003b);
        p.addMovesById(new int[]{1,2,3}, handler.getMoves());
        handler.addNewPokemon(p);

        p=new Pokemon(6,"Charizard",7,8,78,109,85,100, R.drawable.p006, R.drawable.p006b);

        p.addMovesById(ms, handler.getMoves());
        handler.addNewPokemon(p);


        p=new Pokemon(9,"Blastoise",18,0,78,85,105,78, R.drawable.p009, R.drawable.p009b);
    }
    
}