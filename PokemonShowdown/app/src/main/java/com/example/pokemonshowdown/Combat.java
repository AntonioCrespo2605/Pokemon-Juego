package com.example.pokemonshowdown;

import static com.example.pokemonshowdown.R.drawable.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
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
        pk1py2=new PokemonBattler(handler.getPokemonById(94));
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

        switch (type){
            case 1:
                aux.setIcon(ResourcesCompat.getDrawable(getResources(), bug, null));
                aux.setBackgroundColor(ContextCompat.getColor(this, R.color.bug));
                break;
            case 2:
                aux.setIcon(ResourcesCompat.getDrawable(getResources(), dark, null));
                aux.setBackgroundColor(ContextCompat.getColor(this, R.color.dark));
                break;
            case 3:
                aux.setIcon(ResourcesCompat.getDrawable(getResources(), dragon, null));
                aux.setBackgroundColor(ContextCompat.getColor(this, R.color.dragon));
                break;
            case 4:
                aux.setIcon(ResourcesCompat.getDrawable(getResources(), electric, null));
                aux.setBackgroundColor(ContextCompat.getColor(this, R.color.electric));
                break;
            case 5:
                aux.setIcon(ResourcesCompat.getDrawable(getResources(), fairy, null));
                aux.setBackgroundColor(ContextCompat.getColor(this, R.color.fairy));
                break;
            case 6:
                aux.setIcon(ResourcesCompat.getDrawable(getResources(), fight, null));
                aux.setBackgroundColor(ContextCompat.getColor(this, R.color.fight));
                break;
            case 7:
                aux.setIcon(ResourcesCompat.getDrawable(getResources(), fire, null));
                aux.setBackgroundColor(ContextCompat.getColor(this, R.color.fire));
                break;
            case 8:
                aux.setIcon(ResourcesCompat.getDrawable(getResources(), flying, null));
                aux.setBackgroundColor(ContextCompat.getColor(this, R.color.flying));
                break;
            case 9:
                aux.setIcon(ResourcesCompat.getDrawable(getResources(), ghost, null));
                aux.setBackgroundColor(ContextCompat.getColor(this, R.color.ghost));
                break;
            case 10:
                aux.setIcon(ResourcesCompat.getDrawable(getResources(), grass, null));
                aux.setBackgroundColor(ContextCompat.getColor(this, R.color.grass));
                break;
            case 11:
                aux.setIcon(ResourcesCompat.getDrawable(getResources(), ground, null));
                aux.setBackgroundColor(ContextCompat.getColor(this, R.color.ground));
                break;
            case 12:
                aux.setIcon(ResourcesCompat.getDrawable(getResources(), ice, null));
                aux.setBackgroundColor(ContextCompat.getColor(this, R.color.ice));
                break;
            case 13:
                aux.setIcon(ResourcesCompat.getDrawable(getResources(), normal, null));
                aux.setBackgroundColor(ContextCompat.getColor(this, R.color.normal));
                break;
            case 14:
                aux.setIcon(ResourcesCompat.getDrawable(getResources(), poison, null));
                aux.setBackgroundColor(ContextCompat.getColor(this, R.color.poison));
                break;
            case 15:
                aux.setIcon(ResourcesCompat.getDrawable(getResources(), psychic, null));
                aux.setBackgroundColor(ContextCompat.getColor(this, R.color.psychic));
                break;
            case 16:
                aux.setIcon(ResourcesCompat.getDrawable(getResources(), rock, null));
                aux.setBackgroundColor(ContextCompat.getColor(this, R.color.rock));
                break;
            case 17:
                aux.setIcon(ResourcesCompat.getDrawable(getResources(), steel, null));
                aux.setBackgroundColor(ContextCompat.getColor(this, R.color.steel));
                break;
            case 18:
                aux.setIcon(ResourcesCompat.getDrawable(getResources(), water, null));
                aux.setBackgroundColor(ContextCompat.getColor(this, R.color.water));
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

