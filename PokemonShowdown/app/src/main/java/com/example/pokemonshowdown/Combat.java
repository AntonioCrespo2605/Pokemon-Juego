package com.example.pokemonshowdown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Combat extends AppCompatActivity {

    //VIEWS
    private ImageView background, playerturn, playerturnscreen, pk, pkb, pkbstatus, pkstatus;
    private TextView screentext, pk_name, pkb_name, pkb_hp, pkb_maxhp;
    //Pkb es el pokemon del jugador que tiene el turno actualmente
    private FloatingActionButton figth, pokemonChange, atk1, atk2, atk3, atk4;
    private ConstraintLayout constraintPk, constraintPkb, textConstraint;
    private ProgressBar pk_hpBar, pkb_hpBar;
    private LinearLayout pkbHealth;

    //6 pokemon de batalla
    private PokemonBattler pk1py1, pk2py1, pk3py1, pk1py2, pk2py2, pk3py2;

    //2 Pokemon (back y normal)
    private PokemonBattler pokemonFront, pokemonBack;

    //booleanos para saber si el jugador atacó o cambió de pokemon
    private boolean p1Atack;
    private boolean p2Atack;

    //Movimientos ejecutados por cada pokemon
    private Move moveP1, moveP2;

    //contador de turnos--> 3 para animacion de combate
    int turnManager;

    private DBHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat);

        //VIEWS
        background = (ImageView) findViewById(R.id.background);
        playerturn = (ImageView) findViewById(R.id.playerturn);
        playerturnscreen = (ImageView) findViewById(R.id.playerturnscreen);
        pk = (ImageView) findViewById(R.id.pk);
        pkb = (ImageView) findViewById(R.id.pkB);
        pkstatus = (ImageView) findViewById(R.id.pkstatus);
        pkbstatus = (ImageView) findViewById(R.id.pkbstatus);
        screentext = (TextView) findViewById(R.id.screentext);
        pk_name = (TextView) findViewById(R.id.pk_name);
        pkb_name = (TextView) findViewById(R.id.pkb_name);
        pkb_hp = (TextView) findViewById(R.id.pkb_actual_hp);
        pkb_maxhp = (TextView) findViewById(R.id.pkb_maxhp);
        figth = (FloatingActionButton) findViewById(R.id.fight);
        pokemonChange = (FloatingActionButton) findViewById(R.id.changepk);
        atk1 = (FloatingActionButton) findViewById(R.id.atk1);
        atk2 = (FloatingActionButton) findViewById(R.id.atk2);
        atk3 = (FloatingActionButton) findViewById(R.id.atk3);
        atk4 = (FloatingActionButton) findViewById(R.id.atk4);
        constraintPk = (ConstraintLayout) findViewById(R.id.constraintPkData);
        constraintPkb = (ConstraintLayout) findViewById(R.id.constraintPkBData);
        textConstraint = (ConstraintLayout) findViewById(R.id.constraintTexto);
        pk_hpBar = (ProgressBar) findViewById(R.id.pk_hpBar);
        pkb_hpBar = (ProgressBar) findViewById(R.id.pkb_hpBar);
        pkbHealth = (LinearLayout) findViewById(R.id.pkbhealth);

        //leer la informacion del Bundle para inicializar los pokemon
        initPokemons();

        //INICIO
        firstScreen();






    }

    private void firstScreen(){
        //las barras de vida del principio empiezan al 100% y ningún pokemon parte con estado
        pk_hpBar.setProgress(100);
        pkb_hpBar.setProgress(100);
        pkbstatus.setVisibility(View.INVISIBLE);
        pkstatus.setVisibility(View.INVISIBLE);

        //los pokemon mostrados al principio serán los pk1 de cada player
        pokemonBack=new PokemonBattler(pk1py1);
        pokemonFront=new PokemonBattler(pk1py2);

        //el primero en elegir será el jugador 1
        turnManager=1;
        playerturn.setImageResource(R.drawable.j1);

        //ocultamos datos que no se deberían ver de primeras
        textConstraint.setVisibility(View.INVISIBLE);

    }

    private void initPokemons(){
        Bundle b=getIntent().getExtras();
        handler=new DBHandler(this);

        //inicializar battlers
        pk1py1=new PokemonBattler(handler.getPokemonById(b.getInt("pk1py1")));
        pk2py1=new PokemonBattler(handler.getPokemonById(b.getInt("pk2py1")));
        pk3py1=new PokemonBattler(handler.getPokemonById(b.getInt("pk3py1")));
        pk1py2=new PokemonBattler(handler.getPokemonById(b.getInt("pk1py2")));
        pk2py2=new PokemonBattler(handler.getPokemonById(b.getInt("pk2py2")));
        pk3py2=new PokemonBattler(handler.getPokemonById(b.getInt("pk3py2")));

        //ArrayList auxiliar para rellenar los 4 movimientos
        ArrayList<Move> movesAdd;

        ArrayList<PokemonBattler>battlers=new ArrayList<PokemonBattler>();
        battlers.add(pk1py1);
        battlers.add(pk2py1);
        battlers.add(pk3py1);
        battlers.add(pk1py2);
        battlers.add(pk2py2);
        battlers.add(pk3py2);

        //recorrer jugadores, con sus respectivos pokemon, con sus respectivos ataques
        for(int player=1;player<=2;player++){
            for(int pok=1; pok<=3;pok++){
                movesAdd=new ArrayList<Move>();
                for(int mv=1;mv<=4;mv++){
                    movesAdd.add(handler.getMoveById(b.getInt("mv"+mv+"pk"+pok+"py"+player)));
                }
                int pos=-1;
                if(player==2)pos=2;
                pos+=pok;
                battlers.get(pos).setMoves(movesAdd);
            }
        }

        pk1py1=battlers.get(0);
        pk2py1=battlers.get(1);
        pk3py1=battlers.get(2);
        pk1py2=battlers.get(3);
        pk2py2=battlers.get(4);
        pk3py2=battlers.get(5);
    }


    //cosas de aitana

    //
    public void changeTurn(){
        playerturnscreen.setVisibility(View.VISIBLE);
        playerturn.setVisibility(View.VISIBLE);
        figth.setVisibility(View.INVISIBLE);
        textConstraint.setVisibility(View.INVISIBLE);
        pokemonChange.setVisibility(View.INVISIBLE);
        pkbHealth.setVisibility(View.VISIBLE);
        atk1.setVisibility(View.INVISIBLE);
        atk2.setVisibility(View.INVISIBLE);
        atk3.setVisibility(View.INVISIBLE);
        atk4.setVisibility(View.INVISIBLE);
        if(turnManager == 1){
            //ESTO ES PROVISIONAL XQ AUN NO EXISTE LA IMAGEN CORRESPONDIENTE
            playerturnscreen.setImageResource(R.drawable.t1p);
            playerturn.setImageResource(R.drawable.j1);
            //pkb.setImageResource(); jugador actual
            //pk.setImageResource();
            //CAMBIA EL ESTADO DEL POKEMON SI ES NECESARIO
            //Cambia las progres bar y las vida maxima y actual



        }else if (turnManager == 2){
            //ESTO ES PROVISIONAL XQ AUN NO EXISTE LA IMAGEN CORRESPONDIENTE
            playerturnscreen.setImageResource(R.drawable.t2p);
            playerturn.setImageResource(R.drawable.j2);
            //pkb.setImageResource(); //jugador actual
            //pk.setImageResource();
            //CAMBIA EL ESTADO DEL POKEMON SI ES NECESARIO
            //Cambia las progres bar y las vida maxima y actual
        }else{
            //ESTO ES PROVISIONAL XQ AUN NO EXISTE LA IMAGEN CORRESPONDIENTE
            playerturnscreen.setImageResource(R.drawable.background);
            //pkb.setImageResource(); //jugador uno
            //pk.setImageResource();
            //CAMBIA EL ESTADO DEL POKEMON SI ES NECESARIO

        }
    }

    public void selectAction(){
       constraintPk.setVisibility(View.VISIBLE);
       constraintPkb.setVisibility(View.VISIBLE);
       figth.setVisibility(View.VISIBLE);
       pokemonChange.setVisibility(View.VISIBLE);


    }

    //esto salta cuando le das a "luchar"
    public void selectMove(){
        figth.setVisibility(View.INVISIBLE);
        pokemonChange.setVisibility(View.INVISIBLE);
        atk1.setVisibility(View.VISIBLE);
        atk2.setVisibility(View.VISIBLE);
        atk3.setVisibility(View.VISIBLE);
        atk3.setVisibility(View.VISIBLE);

        //tendras q poner el codigo para cambiar los ataques
    }

    private void hideMoves(){
        atk1.setVisibility(View.INVISIBLE);
        atk2.setVisibility(View.INVISIBLE);
        atk3.setVisibility(View.INVISIBLE);
        atk3.setVisibility(View.INVISIBLE);
    }

    //
    public void showText(){
        textConstraint.setVisibility(View.VISIBLE);
        constraintPkb.setVisibility(View.INVISIBLE);
        constraintPk.setVisibility(View.INVISIBLE);
        screentext.setText(""); //loquesea
    }

    public void recievesAttack(){
        //entiendo q recibira el daño y el pokemon q recibe el daño
        textConstraint.setVisibility(View.INVISIBLE);
        constraintPkb.setVisibility(View.VISIBLE);
        constraintPk.setVisibility(View.VISIBLE);
        pkbHealth.setVisibility(View.INVISIBLE);

        //si lo recibe el pokemon del jugador uno
        pkb_hpBar.setProgress(90);//el porcentaje que sea

        //si lo recibe el contrario
        pk_hpBar.setProgress(90);//el porcentaje que sea

    }




}

