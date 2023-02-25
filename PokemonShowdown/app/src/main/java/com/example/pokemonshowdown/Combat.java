package com.example.pokemonshowdown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class Combat extends AppCompatActivity {

    //VIEWS
    private ImageView background, playerturn, playerturnscreen, pk, pkb, pkbstatus, pkstatus;
    private TextView screentext, pk_name, pkb_name, pkb_hp, pkb_maxhp;
    //Pkb es el pokemon del jugador que tiene el turno actualmente
    private ExtendedFloatingActionButton figth, pokemonChange, atk1, atk2, atk3, atk4, aux;
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

    //2 posibles pokemon que entran nuevos
    private PokemonBattler newPokemonP1, newPokemonP2;

    //Movimientos ejecutados por cada pokemon
    private Move moveP1, moveP2;

    //contador de turnos--> 3 para animacion de combate
    int turnManager;

    //handler
    private DBHandler handler;

    //booleano para desactivar los botones y activar el fondo para la animaicion
    private boolean activatedBackGround=false;

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
        figth = (ExtendedFloatingActionButton) findViewById(R.id.fight);
        pokemonChange = (ExtendedFloatingActionButton) findViewById(R.id.changepk);
        atk1 = (ExtendedFloatingActionButton) findViewById(R.id.atk1);
        atk2 = (ExtendedFloatingActionButton) findViewById(R.id.atk2);
        atk3 = (ExtendedFloatingActionButton) findViewById(R.id.atk3);
        atk4 = (ExtendedFloatingActionButton) findViewById(R.id.atk4);
        constraintPk = (ConstraintLayout) findViewById(R.id.constraintPkData);
        constraintPkb = (ConstraintLayout) findViewById(R.id.constraintPkBData);
        textConstraint = (ConstraintLayout) findViewById(R.id.constraintTexto);
        pk_hpBar = (ProgressBar) findViewById(R.id.pk_hpBar);
        pkb_hpBar = (ProgressBar) findViewById(R.id.pkb_hpBar);
        pkbHealth = (LinearLayout) findViewById(R.id.pkbhealth);

        //leer la informacion del Bundle para inicializar los pokemon
        initPokemonsChapuzada();
        //initPokemons();

        //INICIO
        turnManager=0;
        pokemonBack=new PokemonBattler(pk1py1);
        pokemonFront=new PokemonBattler(pk1py2);
        changeTurn();

        playerturnscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerturnscreen.setVisibility(View.INVISIBLE);

                if(turnManager==3){
                    //iniciar con calculos y animacion

                    //provisional:
                    turnManager=1;
                    changeTurn();
                }else{
                    figth.setVisibility(View.VISIBLE);
                    pokemonChange.setVisibility(View.VISIBLE);
                }
            }
        });


        pokemonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //codigo al cambiar de pokemon
            }
        });

        figth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectMove();
            }
        });

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activatedBackGround){

                }else{
                    hideMoves();
                    pokemonChange.setVisibility(View.VISIBLE);
                    figth.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    //cuando este la 2 borrar esta xit
    private void initPokemonsChapuzada(){
        handler=new DBHandler(this);

        pk1py1=new PokemonBattler(handler.getPokemonById(3));
        pk2py1=new PokemonBattler(handler.getPokemonById(6));
        pk3py1=new PokemonBattler(handler.getPokemonById(9));
        pk1py2=new PokemonBattler(handler.getPokemonById(12));
        pk2py2=new PokemonBattler(handler.getPokemonById(15));
        pk3py2=new PokemonBattler(handler.getPokemonById(18));
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
                    aux=player+pok+mv;
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

        pk1py1.setStatus(1);
        pk1py2.setStatus(2);
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

    public void changeTurn(){
        turnManager++;
        if(turnManager>3)turnManager=1;

        if(turnManager == 1){
            playerturn.setImageResource(R.drawable.j1);
            textConstraint.setVisibility(View.INVISIBLE);
        }else if (turnManager == 2){
            playerturnscreen.setImageResource(R.drawable.t2p);
            playerturn.setImageResource(R.drawable.j2);
            textConstraint.setVisibility(View.INVISIBLE);
        }

        playerturnscreen.setVisibility(View.VISIBLE);
        playerturn.setVisibility(View.VISIBLE);
        textConstraint.setVisibility(View.INVISIBLE);
        pkbHealth.setVisibility(View.VISIBLE);
        figth.setVisibility(View.INVISIBLE);
        pokemonChange.setVisibility(View.INVISIBLE);

        hideMoves();
        //calculo de barras de vida
        pk_hpBar.setProgress(pokemonFront.hpPorcentage());
        pkb_hpBar.setProgress(pokemonFront.hpPorcentage());
        pkbstatus.setVisibility(View.VISIBLE);
        pkbstatus.setVisibility(View.VISIBLE);

        //cambios de sprites
        pkb.setImageResource(pokemonBack.getImgB());
        pk.setImageResource(pokemonFront.getImg());

        //cambios en los nombres
        pkb_name.setText(pokemonBack.getName());
        pk_name.setText(pokemonFront.getName());

        //cambio en la vida variable
        pkb_hp.setText(pokemonBack.getCurrentHp()+"");

        //cambio en la vida total
        pkb_maxhp.setText(pokemonBack.getHp()+"");

        switch (pokemonBack.getStatus()){
            case 0:pkbstatus.setVisibility(View.INVISIBLE);
                break;
            case 1:pkbstatus.setImageResource(R.drawable.paralizado);
                break;
            case 2:pkbstatus.setImageResource(R.drawable.quemado);
                break;
            case 3:pkbstatus.setImageResource(R.drawable.envenenado);
                break;
            case 4:pkbstatus.setImageResource(R.drawable.dormido);
                break;
            case 5:pkbstatus.setImageResource(R.drawable.congelado);
                break;
        }

        switch (pokemonFront.getStatus()){
            case 0:pkstatus.setVisibility(View.INVISIBLE);
                break;
            case 1:pkstatus.setImageResource(R.drawable.paralizado);
                break;
            case 2:pkstatus.setImageResource(R.drawable.quemado);
                break;
            case 3:pkstatus.setImageResource(R.drawable.envenenado);
                break;
            case 4:pkstatus.setImageResource(R.drawable.dormido);
                break;
            case 5:pkstatus.setImageResource(R.drawable.congelado);
                break;
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
        updateMove(0);
        switch (pokemonBack.getMoves().size()){
            case 2:
                updateMove(1);
                atk2.setVisibility(View.VISIBLE);
            case 3:
                updateMove(1);
                updateMove(2);
                atk2.setVisibility(View.VISIBLE);
                atk3.setVisibility(View.VISIBLE);
            case 4:
                updateMove(1);
                updateMove(2);
                updateMove(3);
                atk2.setVisibility(View.VISIBLE);
                atk3.setVisibility(View.VISIBLE);
                atk4.setVisibility(View.VISIBLE);
                break;
        }


    }

    private void updateMove(int pos){
        switch (pos){
            case 0:aux=atk1;
                break;
            case 1:aux=atk2;
                break;
            case 2:aux=atk3;
                break;
            case 3:aux=atk4;
                break;
        }

        String name=pokemonBack.getMoves().get(pos).getName();
        aux.setText(name);

        int type=pokemonBack.getMoves().get(pos).getType();

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
        int imgWater=R.drawable.water;

        switch (type){
            case 1:
                aux.setIcon(getResources().getDrawable(imgBug));
                aux.setBackgroundColor(Color.parseColor(colorBug));
                break;
            case 2:
                aux.setIcon(getResources().getDrawable(imgDark));
                aux.setBackgroundColor(Color.parseColor(colorDark));
                break;
            case 3:
                aux.setIcon(getResources().getDrawable(imgDragon));
                aux.setBackgroundColor(Color.parseColor(colorDragon));
                break;
            case 4:
                aux.setIcon(getResources().getDrawable(imgElectric));
                aux.setBackgroundColor(Color.parseColor(colorElectric));
                break;
            case 5:
                aux.setIcon(getResources().getDrawable(imgFairy));
                aux.setBackgroundColor(Color.parseColor(colorFairy));
                break;
            case 6:
                aux.setIcon(getResources().getDrawable(imgFight));
                aux.setBackgroundColor(Color.parseColor(colorFight));
                break;
            case 7:
                aux.setIcon(getResources().getDrawable(imgFire));
                aux.setBackgroundColor(Color.parseColor(colorFire));
                break;
            case 8:
                aux.setIcon(getResources().getDrawable(imgFlying));
                aux.setBackgroundColor(Color.parseColor(colorFlying));
                break;
            case 9:
                aux.setIcon(getResources().getDrawable(imgGhost));
                aux.setBackgroundColor(Color.parseColor(colorGhost));
                break;
            case 10:
                aux.setIcon(getResources().getDrawable(imgGrass));
                aux.setBackgroundColor(Color.parseColor(colorGrass));
                break;
            case 11:
                aux.setIcon(getResources().getDrawable(imgGround));
                aux.setBackgroundColor(Color.parseColor(colorGround));
                break;
            case 12:
                aux.setIcon(getResources().getDrawable(imgIce));
                aux.setBackgroundColor(Color.parseColor(colorIce));
                break;
            case 13:
                aux.setIcon(getResources().getDrawable(imgNormal));
                aux.setBackgroundColor(Color.parseColor(colorNormal));
                break;
            case 14:
                aux.setIcon(getResources().getDrawable(imgPoison));
                aux.setBackgroundColor(Color.parseColor(colorPoison));
                break;
            case 15:
                aux.setIcon(getResources().getDrawable(imgPsychic));
                aux.setBackgroundColor(Color.parseColor(colorPsychic));
                break;
            case 16:
                aux.setIcon(getResources().getDrawable(imgRock));
                aux.setBackgroundColor(Color.parseColor(colorRock));
                break;
            case 17:
                aux.setIcon(getResources().getDrawable(imgSteel));
                aux.setBackgroundColor(Color.parseColor(colorSteel));
                break;
            case 18:
                aux.setIcon(getResources().getDrawable(imgWater));
                aux.setBackgroundColor(Color.parseColor(colorWater));
                break;
        }

    }

    private void hideMoves(){
        atk1.setVisibility(View.INVISIBLE);
        atk2.setVisibility(View.INVISIBLE);
        atk3.setVisibility(View.INVISIBLE);
        atk4.setVisibility(View.INVISIBLE);
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

