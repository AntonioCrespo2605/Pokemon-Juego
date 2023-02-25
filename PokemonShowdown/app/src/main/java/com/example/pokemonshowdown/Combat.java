package com.example.pokemonshowdown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
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
        turnManager=1;
        pokemonBack=new PokemonBattler(pk1py1);
        pokemonFront=new PokemonBattler(pk1py2);
        changeTurn();






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
        int aux;

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
                    aux=b.getInt("mv"+mv+"pk"+pok+"py"+player);
                    if(aux>0)movesAdd.add(handler.getMoveById(aux));
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
        textConstraint.setVisibility(View.INVISIBLE);
        pkbHealth.setVisibility(View.VISIBLE);
        hideMoves();
        if(turnManager == 1){
            //calculo de barras de vida
            pk_hpBar.setProgress(pokemonFront.hpPorcentage());
            pkb_hpBar.setProgress(pokemonFront.hpPorcentage());
            pkbstatus.setVisibility(View.INVISIBLE);
            pkstatus.setVisibility(View.INVISIBLE);

            //los pokemon mostrados al principio serán los pk1 de cada player


            //el primero en elegir será el jugador 1
            turnManager=1;
            playerturn.setImageResource(R.drawable.j1);

            //ocultamos datos que no se deberían ver de primeras
            textConstraint.setVisibility(View.INVISIBLE);


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
        hideMoves();
        atk1.setVisibility(View.VISIBLE);
        switch (pokemonBack.getMoves().size()){
            case 2:
                atk2.setVisibility(View.VISIBLE);
            case 3:
                atk3.setVisibility(View.VISIBLE);
            case 4:
                atk4.setVisibility(View.VISIBLE);
                break;
        }


    }

    private void updateMove(int pos){
        String colorBug="#1a4c27";
        String colorDark="#030708";
        String colorDragon="#458894";
        String colorElectric="#e2e22f";
        String colorFairy="#971848";
        String colorFight="#9c4020";
        String colorFire="#ab1e20";
        String colorFlying="#4a677d";
        String colorGhost="#32336b";
        String colorGrass="#117838";
        String colorGround="#a6712a";
        String colorIce="#84d4eb";
        String colorNormal="#7a515d";
        String colorPoison="#5d2b8c";
        String colorPsychic="#a52a6b";
        String colorRock="#48180a";
        String colorSteel="#5f756e";
        String colorWater="#1552e1";

        /*
        int imgBug=R.drawable.bug;
        int imgDark=R.drawable.dark;
        int imgDragon=R.drawable.dragon;
        int imgElectric=R.drawable.electric;
        int imgFairy=R.drawable.fairy;
        int imgFight=R.drawable.fight;
        int imgFire=R.drawable.fire;
        int imgFlying=R.drawable.flying;
        int imgGhost=R.drawable.ghost;
        int imgGrass=R.drawable.grass;
        int imgGround=R.drawable.ground;
        int imgIce=R.drawable.ice;
        int imgNormal=R.drawable.normal;
        int imgPoison=R.drawable.poison;
        int imgPsychic=R.drawable.psychic;
        int imgRock=R.drawable.rock;
        int imgSteel=R.drawable.steel;
        int imgWater=R.drawable.water;*/

        int typeMove=pokemonBack.getMoves().get(pos).getType();



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

