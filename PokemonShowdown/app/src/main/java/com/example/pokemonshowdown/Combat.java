package com.example.pokemonshowdown;

import static android.graphics.Color.valueOf;
import static com.example.pokemonshowdown.R.drawable.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class Combat extends AppCompatActivity {

    //VIEWS
    private ImageView background, playerturn, playerturnscreen, pk, pkb, pkbstatus, pkstatus, pk1, pk2, pk3;
    private TextView screentext, pk_name, pkb_name, pkb_hp, pkb_maxhp;
    //Pkb es el pokemon del jugador que tiene el turno actualmente
    private ExtendedFloatingActionButton figth, pokemonChange, atk1, atk2, atk3, atk4, aux;
    private ConstraintLayout constraintPk, constraintPkb, textConstraint;
    private ProgressBar pk_hpBar, pkb_hpBar;
    private LinearLayout pkbHealth, pokemon_team;
    private CardView cv1, cv2, cv3;

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

    //booleano para desactivar los botones y activar el fondo para la animacion
    private boolean activatedBackGround = false;

    //booleanos para saber si mostrar la pantalla de jugador despues de seleccionar un pokemon en caso de muerte previa
    boolean p1Died, p2Died;

    //array con las strings para generar el dialogo clickable
    private ArrayList<String> firstPart, secondPart;

    //
    private ArrayList<String> toret;

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
        pokemon_team = (LinearLayout) findViewById(R.id.pokemon_team);
        pk1 = (ImageView) findViewById(R.id.pk1);
        pk2 = (ImageView) findViewById(R.id.pk2);
        pk3 = (ImageView) findViewById(R.id.pk3);
        cv1 = (CardView) findViewById(R.id.cv1);
        cv2 = (CardView) findViewById(R.id.cv2);
        cv3 = (CardView) findViewById(R.id.cv3);

        //leer la informacion del Bundle para inicializar los pokemon
        initPokemonsChapuzada();
        //initPokemons();

        //INICIO
        turnManager = 0;
        pokemonBack = new PokemonBattler(pk1py2);
        pokemonFront = new PokemonBattler(pk1py1);
        p1Died = false;
        p2Died = false;
        changeTurn();

        playerturnscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerturnscreen.setVisibility(View.INVISIBLE);

                if (turnManager == 3) {
                    //iniciar con calculos y animacion

                    //provisional:
                    changeTurn();
                } else {
                    figth.setVisibility(View.VISIBLE);
                    pokemonChange.setVisibility(View.VISIBLE);
                }
            }
        });


        pokemonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPokemon();
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
                if (activatedBackGround) {

                } else {
                    hideMoves();
                    pokemonChange.setVisibility(View.VISIBLE);
                    figth.setVisibility(View.VISIBLE);
                    pokemon_team.setVisibility(View.INVISIBLE);
                }
            }
        });


        //3 botones de cambiar pokemon
        pk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (turnManager == 1) {
                    if (pokemonBack.getNumDex() != pk1py1.getNumDex() && pk1py1.isAlive()) {
                        p1Atack = false;
                        newPokemonP1 = new PokemonBattler(pk1py1);
                        changeTurn();
                    }
                } else if (turnManager == 2) {
                    if (pokemonBack.getNumDex() != pk1py2.getNumDex() && pk1py2.isAlive()) {
                        p2Atack = false;
                        newPokemonP2 = new PokemonBattler(pk1py2);
                        changeTurn();
                    }
                } else {

                }
            }
        });

        pk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (turnManager == 1) {
                    if (pokemonBack.getNumDex() != pk2py1.getNumDex() && pk2py1.isAlive()) {
                        p1Atack = false;
                        newPokemonP1 = new PokemonBattler(pk2py1);
                        changeTurn();
                    }
                } else if (turnManager == 2) {
                    if (pokemonBack.getNumDex() != pk2py2.getNumDex() && pk2py2.isAlive()) {
                        p2Atack = false;
                        newPokemonP2 = new PokemonBattler(pk2py2);
                        changeTurn();
                    }
                } else {

                }
            }
        });

        pk3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (turnManager == 1) {
                    if (pokemonBack.getNumDex() != pk3py1.getNumDex() && pk3py1.isAlive()) {
                        p1Atack = false;
                        newPokemonP1 = new PokemonBattler(pk3py1);
                        changeTurn();
                    }
                } else if (turnManager == 2) {
                    if (pokemonBack.getNumDex() != pk3py2.getNumDex() && pk3py2.isAlive()) {
                        p2Atack = false;
                        newPokemonP2 = new PokemonBattler(pk3py2);
                        changeTurn();
                    }
                } else {

                }
            }
        });

        //4 botones de ataques
        atk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (turnManager == 1) {
                    moveP1 = new Move(pokemonBack.getMoves().get(0));
                    p1Atack = true;
                    changeTurn();
                } else if (turnManager == 2) {
                    moveP2 = new Move(pokemonBack.getMoves().get(0));
                }
            }
        });

        atk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (turnManager == 1) {
                    moveP1 = new Move(pokemonBack.getMoves().get(1));
                    p1Atack = true;
                    changeTurn();
                } else if (turnManager == 2) {
                    moveP2 = new Move(pokemonBack.getMoves().get(1));
                    p2Atack = true;
                    changeTurn();
                }
            }
        });

        atk3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (turnManager == 1) {
                    moveP1 = new Move(pokemonBack.getMoves().get(2));
                    p1Atack = true;
                    changeTurn();
                } else if (turnManager == 2) {
                    moveP2 = new Move(pokemonBack.getMoves().get(2));
                    p2Atack = true;
                    changeTurn();
                }
            }
        });

        atk4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (turnManager == 1) {
                    moveP1 = new Move(pokemonBack.getMoves().get(3));
                    p1Atack = true;
                    changeTurn();
                } else if (turnManager == 2) {
                    moveP2 = new Move(pokemonBack.getMoves().get(3));
                    p2Atack = true;
                    changeTurn();
                }
            }
        });

    }

    //cuando este la 2 borrar esta xit
    private void initPokemonsChapuzada() {
        handler = new DBHandler(this);

        pk1py1 = new PokemonBattler(handler.getPokemonById(3));
        pk2py1 = new PokemonBattler(handler.getPokemonById(6));
        pk3py1 = new PokemonBattler(handler.getPokemonById(9));
        pk1py2 = new PokemonBattler(handler.getPokemonById(89));
        pk2py2 = new PokemonBattler(handler.getPokemonById(15));
        pk3py2 = new PokemonBattler(handler.getPokemonById(18));
        //ArrayList auxiliar para rellenar los 4 movimientos
        ArrayList<Move> movesAdd;
        int aux;

        ArrayList<PokemonBattler> battlers = new ArrayList<PokemonBattler>();
        battlers.add(pk1py1);
        battlers.add(pk2py1);
        battlers.add(pk3py1);
        battlers.add(pk1py2);
        battlers.add(pk2py2);
        battlers.add(pk3py2);

        //recorrer jugadores, con sus respectivos pokemon, con sus respectivos ataques
        for (int player = 1; player <= 2; player++) {
            for (int pok = 1; pok <= 3; pok++) {
                movesAdd = new ArrayList<Move>();
                for (int mv = 1; mv <= 4; mv++) {
                    aux = player + pok + mv;
                    if (aux > 0) movesAdd.add(handler.getMoveById(aux));
                }
                int pos = -1;
                if (player == 2) pos = 2;
                pos += pok;
                battlers.get(pos).setMoves(movesAdd);
            }
        }

        pk1py1 = battlers.get(0);
        pk2py1 = battlers.get(1);
        pk3py1 = battlers.get(2);
        pk1py2 = battlers.get(3);
        pk2py2 = battlers.get(4);
        pk3py2 = battlers.get(5);

        pk1py1.setStatus(1);
        pk1py2.setStatus(2);


        pk1py1.setCurrentHp(20);
        pk2py1.setCurrentHp(0);
        pk1py2.setCurrentHp(10);
    }

    private void initPokemons() {
        Bundle b = getIntent().getExtras();
        handler = new DBHandler(this);

        //inicializar battlers
        pk1py1 = new PokemonBattler(handler.getPokemonById(b.getInt("pk1py1")));
        pk2py1 = new PokemonBattler(handler.getPokemonById(b.getInt("pk2py1")));
        pk3py1 = new PokemonBattler(handler.getPokemonById(b.getInt("pk3py1")));
        pk1py2 = new PokemonBattler(handler.getPokemonById(b.getInt("pk1py2")));
        pk2py2 = new PokemonBattler(handler.getPokemonById(b.getInt("pk2py2")));
        pk3py2 = new PokemonBattler(handler.getPokemonById(b.getInt("pk3py2")));

        //ArrayList auxiliar para rellenar los 4 movimientos
        ArrayList<Move> movesAdd;
        int aux;

        ArrayList<PokemonBattler> battlers = new ArrayList<PokemonBattler>();
        battlers.add(pk1py1);
        battlers.add(pk2py1);
        battlers.add(pk3py1);
        battlers.add(pk1py2);
        battlers.add(pk2py2);
        battlers.add(pk3py2);

        //recorrer jugadores, con sus respectivos pokemon, con sus respectivos ataques
        for (int player = 1; player <= 2; player++) {
            for (int pok = 1; pok <= 3; pok++) {
                movesAdd = new ArrayList<Move>();
                for (int mv = 1; mv <= 4; mv++) {
                    aux = b.getInt("mv" + mv + "pk" + pok + "py" + player);
                    if (aux > 0) movesAdd.add(handler.getMoveById(aux));
                }
                int pos = -1;
                if (player == 2) pos = 2;
                pos += pok;
                battlers.get(pos).setMoves(movesAdd);
            }
        }

        pk1py1 = battlers.get(0);
        pk2py1 = battlers.get(1);
        pk3py1 = battlers.get(2);
        pk1py2 = battlers.get(3);
        pk2py2 = battlers.get(4);
        pk3py2 = battlers.get(5);
    }


    //cosas de aitana
    public void changeTurn() {
        turnManager++;
        if (turnManager > 3) turnManager = 1;
        reverseBattlers();

        if (turnManager == 1) {
            playerturnscreen.setImageResource(R.drawable.t1p);
            playerturn.setImageResource(R.drawable.j1);
            textConstraint.setVisibility(View.INVISIBLE);
            if (!p1Died) playerturnscreen.setVisibility(View.VISIBLE);
        } else if (turnManager == 2) {
            playerturnscreen.setImageResource(R.drawable.t2p);
            playerturn.setImageResource(R.drawable.j2);
            textConstraint.setVisibility(View.INVISIBLE);
            if (!p2Died) playerturnscreen.setVisibility(View.VISIBLE);
        }

        playerturn.setVisibility(View.VISIBLE);
        textConstraint.setVisibility(View.INVISIBLE);
        pkbHealth.setVisibility(View.VISIBLE);
        figth.setVisibility(View.INVISIBLE);
        pokemonChange.setVisibility(View.INVISIBLE);
        pokemon_team.setVisibility(View.INVISIBLE);

        hideMoves();

        //calculo de barras de vida
        pk_hpBar.setProgress(pokemonFront.hpPercent());
        pkb_hpBar.setProgress(pokemonBack.hpPercent());
        pkstatus.setVisibility(View.VISIBLE);
        pkbstatus.setVisibility(View.VISIBLE);

        //meter este codigo en funciones
        Drawable progressDrawable = pk_hpBar.getProgressDrawable().mutate();
        if (pokemonFront.hpPercent() < 11) {
            progressDrawable.setColorFilter(ContextCompat.getColor(this, R.color.redhp), android.graphics.PorterDuff.Mode.SRC_IN);
            pk_hpBar.setProgressDrawable(progressDrawable);
        } else if (pokemonFront.hpPercent() < 26) {
            progressDrawable.setColorFilter(ContextCompat.getColor(this, R.color.orangehp), android.graphics.PorterDuff.Mode.SRC_IN);
            pk_hpBar.setProgressDrawable(progressDrawable);
        } else if (pokemonFront.hpPercent() < 51) {
            progressDrawable.setColorFilter(ContextCompat.getColor(this, R.color.yellowhp), android.graphics.PorterDuff.Mode.SRC_IN);
            pk_hpBar.setProgressDrawable(progressDrawable);
        } else {
            progressDrawable.setColorFilter(ContextCompat.getColor(this, R.color.greenhp), android.graphics.PorterDuff.Mode.SRC_IN);
            pk_hpBar.setProgressDrawable(progressDrawable);
        }

        if (pokemonBack.hpPercent() < 11) {
            progressDrawable.setColorFilter(ContextCompat.getColor(this, R.color.redhp), android.graphics.PorterDuff.Mode.SRC_IN);
            pkb_hpBar.setProgressDrawable(progressDrawable);
        } else if (pokemonBack.hpPercent() < 26) {
            progressDrawable.setColorFilter(ContextCompat.getColor(this, R.color.orangehp), android.graphics.PorterDuff.Mode.SRC_IN);
            pkb_hpBar.setProgressDrawable(progressDrawable);
        } else if (pokemonBack.hpPercent() < 51) {
            progressDrawable.setColorFilter(ContextCompat.getColor(this, R.color.yellowhp), android.graphics.PorterDuff.Mode.SRC_IN);
            pkb_hpBar.setProgressDrawable(progressDrawable);
        } else {
            progressDrawable.setColorFilter(ContextCompat.getColor(this, R.color.greenhp), android.graphics.PorterDuff.Mode.SRC_IN);
            pkb_hpBar.setProgressDrawable(progressDrawable);
        }

        //cambios de sprites
        pkb.setImageResource(pokemonBack.getImgB());
        pk.setImageResource(pokemonFront.getImg());

        //cambios en los nombres
        pkb_name.setText(pokemonBack.getName());
        pk_name.setText(pokemonFront.getName());

        //cambio en la vida variable
        pkb_hp.setText(pokemonBack.getCurrentHp() + "");

        //cambio en la vida total
        pkb_maxhp.setText(pokemonBack.getHp() + "");

        //convertir los switchs en funcion
        getPokemonStatus(pokemonBack, pkbstatus);
        getPokemonStatus(pokemonFront, pkstatus);

        if (turnManager == 3) {
            showBattle();
        }

    }

    private void getPokemonStatus(PokemonBattler pokemon, ImageView status){
        switch (pokemon.getStatus()) {
            case 0:
                status.setVisibility(View.INVISIBLE);
                break;
            case 1:
                status.setImageResource(R.drawable.paralizado);
                break;
            case 2:
                status.setImageResource(R.drawable.quemado);
                break;
            case 3:
                status.setImageResource(R.drawable.envenenado);
                break;
            case 4:
                status.setImageResource(R.drawable.dormido);
                break;
            case 5:
                status.setImageResource(R.drawable.congelado);
                break;
        }
    }

    //se activa el modo combate
    private void showBattle() {
        activatedBackGround = true;
        generateFirstDialog(pokemonBackFirst());
    }

    //
    private void generateFirstDialog(boolean p1first) {
        if (p1first) firstPart = generateDialogPlayer(1);
        else firstPart = generateDialogPlayer(2);
    }

    private ArrayList<String> generateDialogPlayer(int player) {
        toret = new ArrayList<String>();
        if (checkStatus(player)) {
            checkMove(player);
        }
        return toret;
    }

    private boolean checkStatus(int player) {
        Random r = new Random();

        PokemonBattler focus;
        if (player == 1) focus = new PokemonBattler(pokemonBack);
        else focus = new PokemonBattler(pokemonFront);

        if ((player==1&&!p1Atack)||(player==2&&!p2Atack)) {
            toret.add("/backToBall" + player);//comando
            toret.add("/newPokemonChange" + player);//comando
            return false;
        }
        if (!focus.isAlive()) return false;

        //si está dormido
        if (focus.getStatus() == 4) {
            //si despierta
            if (r.nextInt(5) == 0) {
                toret.add("/wakeUp" + player);//comando
            } else {
                toret.add(focus.getName() + " está completamente dormido");
                return false;
            }
        } else {
            //si está paralizado
            if (focus.getStatus() == 1) {
                toret.add(focus.getName() + " está paralizado!\nQuizás no pueda moverse");
                //si falla
                if (r.nextInt(4) == 0) {
                    toret.add(focus.getName() + " está paralizado\nNo puede moverse!");
                    return false;
                }
            //si está congelado
            } else if(focus.getStatus()==5){
                //si se descongela
                if(r.nextInt(10)==0){
                    toret.add("/defrost"+player);//comando
                }else{
                    toret.add(focus.getName() + " está congelado\nNo puede moverse!");
                    return false;
                }
            }
        }

        Move movefocus;
        if (player == 1) movefocus =new Move(moveP1) ;
        else movefocus = new Move(moveP2);

        toret.add(focus.getName() + " ha usado " + movefocus.getName());

        return true;
    }

    private void checkMove(int player) {
        Random r = new Random();

        PokemonBattler focus;
        if (player == 1) focus = new PokemonBattler(pokemonBack);
        else focus = new PokemonBattler(pokemonFront);

        PokemonBattler victim;
        if (player == 1)victim = new PokemonBattler(pokemonFront);
        else victim = new PokemonBattler(pokemonBack);

        Move movefocus;
        if (player == 1) movefocus = new Move(moveP1);
        else movefocus = new Move(moveP2);

        r=new Random();
        //si falla
        if(1+r.nextInt(101)>movefocus.getAccuracy()){
            toret.add("Pero ha fallado");
            return;
        }

        //efectividad
        double m=getMultiplayerEffectivity(movefocus.getType(), victim.getType1(), victim.getType2());
        //si no hace efecto por tipos
        if(m==0){
            toret.add("No tiene ningún efecto");
            return;
        }

        //si es un movimiento de estado
        if(!movefocus.isAtkSt()){
            if(victim.getStatus()!=0){
                toret.add(focus.getName()+" ha fallado el ataque");
            }else{
                toret.add("/newStatus"+player);//comando
            }
            return;
        }

        toret.add("/atak"+player);

        //
        if(m==2||m==4)toret.add("Es supereficaz!");
        else if(m==0.5||m==0.25)toret.add("No es muy efectivo...");


    }

    private static double getDamage(Move move, PokemonBattler focus, PokemonBattler victim){
        double e=getMultiplayerEffectivity(move.getType(), victim.getType1(), victim.getType2());
        double b;
        if(focus.getType1()==move.getType()||focus.getType2()==move.getType())b=1.5;
        else b=1;
        int a=focus.getDmg();



        return 0;
    }


    //decide que pokemon ataca primero, devuelve true si es el pokemon que esta de espaldas
    private boolean pokemonBackFirst() {
        double speed1 = pokemonBack.getSpd();
        double speed2 = pokemonFront.getSpd();

        //si está paralizado se baja la velocidad a la mitad
        if (pokemonBack.getStatus() == 1) speed1 = speed1 / 2;
        if (pokemonFront.getStatus() == 1) speed2 = speed2 / 2;

        Random r = new Random();
        if (!p1Atack) return true;
        if (!p2Atack) return false;
        if (moveP1.isPriority() && !moveP2.isPriority()) return true;
        if (moveP2.isPriority()) return false;
        if (speed1 > speed2) return true;
        if (speed1 < speed2) return false;
        return r.nextBoolean();
    }

    public void reverseBattlers() {
        PokemonBattler aux = new PokemonBattler(pokemonBack);
        pokemonBack = new PokemonBattler(pokemonFront);
        pokemonFront = new PokemonBattler(aux);
    }

    //esto salta cuando le das a "luchar"
    public void selectMove() {
        figth.setVisibility(View.INVISIBLE);
        pokemonChange.setVisibility(View.INVISIBLE);
        hideMoves();
        atk1.setVisibility(View.VISIBLE);
        updateMove(0);
        switch (pokemonBack.getMoves().size()) {
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

    private void updateMove(int pos) {
        switch (pos) {
            case 0:
                aux = atk1;
                break;
            case 1:
                aux = atk2;
                break;
            case 2:
                aux = atk3;
                break;
            case 3:
                aux = atk4;
                break;
        }

        String name = pokemonBack.getMoves().get(pos).getName();
        aux.setText(name);

        int type = pokemonBack.getMoves().get(pos).getType();

        //opacidad del icono
        aux.getIcon().setAlpha(190);

        switch (type) {
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
                aux.setIcon(ResourcesCompat.getDrawable(getResources(), fighting, null));
                aux.setBackgroundColor(ContextCompat.getColor(this, R.color.fighting));
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

    private void hideMoves() {
        atk1.setVisibility(View.INVISIBLE);
        atk2.setVisibility(View.INVISIBLE);
        atk3.setVisibility(View.INVISIBLE);
        atk4.setVisibility(View.INVISIBLE);
    }

    public void selectPokemon() {
        pokemon_team.setVisibility(View.VISIBLE);
        figth.setVisibility(View.INVISIBLE);
        pokemonChange.setVisibility(View.INVISIBLE);

        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

        if (turnManager == 1) {
            pk1.setImageResource(pk1py1.getImg());
            pk2.setImageResource(pk2py1.getImg());
            pk3.setImageResource(pk3py1.getImg());

            if (pk1py1.isAlive()) {
                pk1.setColorFilter(null);
            } else {
                pk1.setColorFilter(filter);
            }

            if (pk2py1.isAlive()) {
                pk2.setColorFilter(null);
            } else {
                pk2.setColorFilter(filter);
            }

            if (pk3py1.isAlive()) {
                pk3.setColorFilter(null);
            } else {
                pk3.setColorFilter(filter);
            }

            if (pokemonBack.getNumDex() == pk1py1.getNumDex()) {
                cv1.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_selected));
                cv2.setBackgroundColor(ContextCompat.getColor(this, R.color.greyp));
                cv3.setBackgroundColor(ContextCompat.getColor(this, R.color.greyp));
            } else if (pokemonBack.getNumDex() == pk2py1.getNumDex()) {
                cv1.setBackgroundColor(ContextCompat.getColor(this, R.color.greyp));
                cv2.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_selected));
                cv3.setBackgroundColor(ContextCompat.getColor(this, R.color.greyp));
            } else {
                cv1.setBackgroundColor(ContextCompat.getColor(this, R.color.greyp));
                cv2.setBackgroundColor(ContextCompat.getColor(this, R.color.greyp));
                cv3.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_selected));
            }
        } else if (turnManager == 2) {
            pk1.setImageResource(pk1py2.getImg());
            pk2.setImageResource(pk2py2.getImg());
            pk3.setImageResource(pk3py2.getImg());

            if (pk1py2.isAlive()) {
                pk1.setColorFilter(null);
            } else {
                pk1.setColorFilter(filter);
            }

            if (pk2py2.isAlive()) {
                pk2.setColorFilter(null);
            } else {
                pk2.setColorFilter(filter);
            }

            if (pk3py2.isAlive()) {
                pk3.setColorFilter(null);
            } else {
                pk3.setColorFilter(filter);
            }

            if (pokemonBack.getNumDex() == pk1py2.getNumDex()) {
                cv1.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_selected));
                cv2.setBackgroundColor(ContextCompat.getColor(this, R.color.greyp));
                cv3.setBackgroundColor(ContextCompat.getColor(this, R.color.greyp));
            } else if (pokemonBack.getNumDex() == pk2py2.getNumDex()) {
                cv1.setBackgroundColor(ContextCompat.getColor(this, R.color.greyp));
                cv2.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_selected));
                cv3.setBackgroundColor(ContextCompat.getColor(this, R.color.greyp));
            } else {
                cv1.setBackgroundColor(ContextCompat.getColor(this, R.color.greyp));
                cv2.setBackgroundColor(ContextCompat.getColor(this, R.color.greyp));
                cv3.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_selected));
            }
        }
    }

    private static double getMultiplayerEffectivity(int moveType, int t1, int t2) {
        double toret = 1;

        switch (moveType) {
            //tipo bicho;
            case 1:
                if (t1 == 10 || t1 == 15 || t1 == 2) toret = 2;
                else if (t1 == 17 || t1 == 9 || t1 == 5 || t1 == 7 || t1 == 6 || t1 == 14 || t1 == 8) toret = 0.5;
                else toret = 1;
                break;
            //tipo siniestro
            case 2:
                if (t1 == 9 || t1 == 15) toret = 2;
                else if (t1 == 5 || t1 == 6 || t1 == 2) toret = 0.5;
                else toret = 1;
                break;
            //tipo dragon
            case 3:
                if (t1 == 3) toret = 2;
                else if (t1 == 17) toret = 0.5;
                else if (t1 == 5) toret = 0;
                else toret = 1;
                break;
            //tipo electrico
            case 4:
                if (t1 == 8 || t1 == 18) toret = 2;
                else if (t1 == 3 || t1 == 4 || t1 == 10) toret = 0.5;
                else if (t1 == 11) toret = 0;
                else toret = 1;
                break;
            //fairy
            case 5:
                if (t1 == 17 || t1 == 7 || t1 == 14) toret = 0.5;
                else if (t1 == 3 || t1 == 6 || t1 == 2) toret = 2;
                else toret = 1;
                break;
            //lucha
            case 6:
                if (t1 == 17 || t1 == 12 || t1 == 13 || t1 == 16 || t1 == 2) toret = 2;
                else if (t1 == 1 || t1 == 5 || t1 == 15 || t1 == 14 || t1 == 8) toret = 0.5;
                else if (t1 == 9) toret = 0;
                else toret = 1;
                break;
            //fuego
            case 7:
                if (t1 == 17 || t1 == 1 || t1 == 12 || t1 == 10) toret = 2;
                else if (t1 == 18 || t1 == 3 || t1 == 7 || t1 == 16) toret = 0.5;
                else toret = 1;
                break;
            //volador
            case 8:
                if (t1 == 1 || t1 == 6 || t1 == 10) toret = 2;
                if (t1 == 17 || t1 == 4 || t1 == 16) toret = 0.5;
                else toret = 1;
                break;
            //fantasma
            case 9:
                if (t1 == 9 || t1 == 15) toret = 2;
                else if (t1 == 2) toret = 0.5;
                else if (t1 == 13) toret = 0;
                else toret = 1;
                break;
            //planta
            case 10:
                if (t1 == 18 || t1 == 16 || t1 == 11) toret = 2;
                else if (t1 == 17 || t1 == 1 || t1 == 3 || t1 == 7 || t1 == 10 || t1 == 14 || t1 == 8)
                    toret = 0.5;
                else toret = 1;
                break;
            //tierra
            case 11:
                if (t1 == 17 || t1 == 4 || t1 == 7 || t1 == 16 || t1 == 14) toret = 2;
                else if (t1 == 1 || t1 == 10) toret = 0.5;
                else toret = 1;
                break;
            //hielo
            case 12:
                if (t1 == 3 || t1 == 10 || t1 == 11 || t1 == 8) toret = 2;
                else if (t1 == 17 || t1 == 18 || t1 == 7 || t1 == 12) toret = 0.5;
                else toret = 1;
                break;
            //normal
            case 13:
                if (t1 == 17 || t1 == 16) toret = 0.5;
                else if (t1 == 9) toret = 0;
                else toret = 1;
                break;
            //veneno
            case 14:
                if (t1 == 10 || t1 == 5) toret = 2;
                else if (t1 == 9 || t1 == 16 || t1 == 11 || t1 == 14) toret = 0.5;
                else if (t1 == 17) toret = 0;
                else toret = 1;
                break;
            //psiquico
            case 15:
                if (t1 == 17 || t1 == 15) toret = 0.5;
                else if (t1 == 6 || t1 == 14) toret = 2;
                else if (t1 == 2) toret = 0;
                else toret = 1;
                break;
            //roca
            case 16:
                if (t1 == 1 || t1 == 7 || t1 == 12 || t1 == 8) toret = 2;
                else if (t1 == 17 || t1 == 6 || t1 == 11) toret = 0.5;
                else toret = 1;
                break;
            //acero
            case 17:
                if (t1 == 17 || t1 == 18 || t1 == 4 || t1 == 7) toret = 0.5;
                else if (t1 == 5 || t1 == 12 || t1 == 16) toret = 2;
                else toret = 1;
                break;
            //agua
            case 18:
                if (t1 == 18 || t1 == 3 || t1 == 10) toret = 0.5;
                else if (t1 == 7 || t1 == 16 || t1 == 11) toret = 2;
                else toret = 1;
                break;
        }


        //tipo2
        if (t2 != 0) {
            switch (moveType) {
                //tipo bicho;
                case 1:
                    if (t2 == 10 || t2 == 15 || t2 == 2) toret *= 2;
                    else if (t2 == 17 || t2 == 9 || t2 == 5 || t2 == 7 || t2 == 6 || t2 == 14 || t2 == 8)
                        toret *= 0.5;
                    else toret *= 1;
                    break;
                //tipo siniestro
                case 2:
                    if (t2 == 9 || t2 == 15) toret *= 2;
                    else if (t2 == 5 || t2 == 6 || t2 == 2) toret *= 0.5;
                    else toret *= 1;
                    break;
                //tipo dragon
                case 3:
                    if (t2 == 3) toret *= 2;
                    else if (t2 == 17) toret *= 0.5;
                    else if (t2 == 5) toret *= 0;
                    else toret *= 1;
                    break;
                //tipo electrico
                case 4:
                    if (t2 == 8 || t2 == 18) toret *= 2;
                    else if (t2 == 3 || t2 == 4 || t2 == 10) toret *= 0.5;
                    else if (t2 == 11) toret *= 0;
                    else toret *= 1;
                    break;
                //fairy
                case 5:
                    if (t2 == 17 || t2 == 7 || t2 == 14) toret *= 0.5;
                    else if (t2 == 3 || t2 == 6 || t2 == 2) toret *= 2;
                    else toret *= 1;
                    break;

                //lucha
                case 6:
                    if (t2 == 17 || t2 == 12 || t2 == 13 || t2 == 16 || t2 == 2) toret *= 2;
                    else if (t2 == 1 || t2 == 5 || t2 == 15 || t2 == 14 || t2 == 8) toret *= 0.5;
                    else if (t2 == 9) toret *= 0;
                    else toret *= 1;
                    break;

                //fuego
                case 7:
                    if (t2 == 17 || t2 == 1 || t2 == 12 || t2 == 10) toret *= 2;
                    else if (t2 == 18 || t2 == 3 || t2 == 7 || t2 == 16) toret *= 0.5;
                    else toret *= 1;
                    break;
                //volador
                case 8:
                    if (t2 == 1 || t2 == 6 || t2 == 10) toret *= 2;
                    if (t2 == 17 || t2 == 4 || t2 == 16) toret *= 0.5;
                    else toret *= 1;
                    break;
                //fantasma
                case 9:
                    if (t2 == 9 || t2 == 15) toret *= 2;
                    else if (t2 == 2) toret *= 0.5;
                    else if (t2 == 13) toret *= 0;
                    else toret *= 1;
                    break;

                //planta
                case 10:
                    if (t2 == 18 || t2 == 16 || t2 == 11) toret *= 2;
                    else if (t2 == 17 || t2 == 1 || t2 == 3 || t2 == 7 || t2 == 10 || t2 == 14 || t2 == 8)
                        toret *= 0.5;
                    else toret *= 1;
                    break;

                //tierra
                case 11:
                    if (t2 == 17 || t2 == 4 || t2 == 7 || t2 == 16 || t2 == 14) toret *= 2;
                    else if (t2 == 1 || t2 == 10) toret *= 0.5;
                    else toret *= 1;
                    break;
                //hielo
                case 12:
                    if (t2 == 3 || t2 == 10 || t2 == 11 || t2 == 8) toret *= 2;
                    else if (t2 == 17 || t2 == 18 || t2 == 7 || t2 == 12) toret *= 0.5;
                    else toret *= 1;
                    break;
                //normal
                case 13:
                    if (t2 == 17 || t2 == 16) toret *= 0.5;
                    else if (t2 == 9) toret *= 0;
                    else toret *= 1;
                    break;
                //veneno
                case 14:
                    if (t2 == 10 || t2 == 5) toret *= 2;
                    else if (t2 == 9 || t2 == 16 || t2 == 11 || t2 == 14) toret *= 0.5;
                    else if (t2 == 17) toret *= 0;
                    else toret *= 1;
                    break;
                //psiquico
                case 15:
                    if (t2 == 17 || t2 == 15) toret *= 0.5;
                    else if (t2 == 6 || t2 == 14) toret *= 2;
                    else if (t2 == 2) toret *= 0;
                    else toret *= 1;
                    break;

                //roca
                case 16:
                    if (t2 == 1 || t2 == 7 || t2 == 12 || t2 == 8) toret *= 2;
                    else if (t2 == 17 || t2 == 6 || t2 == 11) toret *= 0.5;
                    else toret *= 1;
                    break;
                //acero
                case 17:
                    if (t2 == 17 || t2 == 18 || t2 == 4 || t2 == 7) toret *= 0.5;
                    else if (t2 == 5 || t2 == 12 || t2 == 16) toret *= 2;
                    else toret *= 1;
                    break;
                //agua
                case 18:
                    if (t2 == 18 || t2 == 3 || t2 == 10) toret *= 0.5;
                    else if (t2 == 7 || t2 == 16 || t2 == 11) toret *= 2;
                    else toret *= 1;
                    break;
            }
        }
        return toret;
    }

}

