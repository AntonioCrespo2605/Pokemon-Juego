package com.example.pokemonshowdown;


import static com.example.pokemonshowdown.R.drawable.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokemonshowdown.objects.DBHandler;
import com.example.pokemonshowdown.objects.Move;
import com.example.pokemonshowdown.objects.PokemonBattler;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class Battle extends AppCompatActivity {

    //VIEWS
    private ImageView background, playerturn, playerturnscreen, pk, pkb, pkbstatus, pkstatus, pk1, pk2, pk3;
    private TextView pk_name, pkb_name, pkb_hp, pkb_maxhp;
    private Typewriter screentext;
    //Pkb es el pokemon del jugador que tiene el turno actualmente
    private ExtendedFloatingActionButton figth, pokemonChange, atk1, atk2, atk3, atk4, aux;
    private ConstraintLayout constraintPk, textConstraint;
    LinearLayout constraintPkb;
    private ProgressBar pk_hpBar;
    private ProgressBar pkb_hpBar;
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
    private boolean modeCombat = false;
    private boolean deadMode = false;

    //booleanos para saber si mostrar la pantalla de jugador despues de seleccionar un pokemon en caso de muerte previa
    boolean p1Died, p2Died;

    //array con las strings para generar el dialogo clickable
    private ArrayList<String> firstPart, secondPart, thirdPart;

    //auxiliar
    private ArrayList<String> toret;

    //variable para almacenar el daño de las comprobaciones para reutilizar en cálculos(al tener factor random puede generar distintos resultados si no se almacena)
    private int damage;

    //contador de clics en el fondo para controlar los dialogos
    private int clicCounter;

    //booleano para controlar quien va primero
    private boolean player1first;

    //Mediaplayer


    private Handler h;

    private static final int[] BACKGROUNDS = {fondo_agua, fondo_bosque, fondo_bosque_noche, fondo_circulo_azul, fondo_circulo_morado, fondo_ciudad_dia, fondo_ciudad_noche,
            fondo_cueva_submarina, fondo_futbol, fondo_futurista, fondo_helado, fondo_luna, fondo_mansion, fondo_montanha_nieve, fondo_monte, fondo_nieve, fondo_otonho,
            fondo_piscina, fondo_pixel_desierto_manhana, fondo_pixel_desierto_mediodia, fondo_pixel_desierto_noche, fondo_pixel_disco, fondo_pixel_nieve_manhana,
            fondo_pixel_nieve_mediodia, fondo_pixel_nieve_noche, fondo_pixel_paseo, fondo_pixel_paseo_madera, fondo_pixel_prado_atardecer, fondo_pixel_prado_manhana,
            fondo_pixel_prado_mediodia, fondo_pixel_prado_noche, fondo_playa_atardecer, fondo_playa_dia, fondo_pradera_dia, fondo_pradera_mediodia, fondo_pradera_noche,
            fondo_pueblo_arena, fondo_rocoso, fondo_taller, fondo_templo, fondo_tunel, fondo_volcan};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        //VIEWS
        background = (ImageView) findViewById(R.id.background);
        playerturn = (ImageView) findViewById(R.id.playerturn);
        playerturnscreen = (ImageView) findViewById(R.id.playerturnscreen);
        pk = (ImageView) findViewById(R.id.pk);
        pkb = (ImageView) findViewById(R.id.pkB);
        pkstatus = (ImageView) findViewById(R.id.pkstatus);
        pkbstatus = (ImageView) findViewById(R.id.pkbstatus);
        screentext = findViewById(R.id.screentext);
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
        constraintPkb = (LinearLayout) findViewById(R.id.constraintPkBData);
        textConstraint = (ConstraintLayout) findViewById(R.id.constraintTexto);
        pkbHealth = (LinearLayout) findViewById(R.id.pkbhealth);
        pokemon_team = (LinearLayout) findViewById(R.id.pokemon_team);
        pk1 = (ImageView) findViewById(R.id.pk1);
        pk2 = (ImageView) findViewById(R.id.pk2);
        pk3 = (ImageView) findViewById(R.id.pk3);
        cv1 = (CardView) findViewById(R.id.cv1);
        cv2 = (CardView) findViewById(R.id.cv2);
        cv3 = (CardView) findViewById(R.id.cv3);
        pkb_hpBar = (ProgressBar) findViewById(R.id.pkb_hpBar);
        pk_hpBar = (ProgressBar) findViewById(R.id.pk_hpBar);

        randomBackground();

        h = new Handler();

        //leer la informacion del Bundle para inicializar los pokemon
        initPokemons();

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
                if (deadMode) {
                    showDeads();
                } else if (turnManager != 3) {
                    figth.setVisibility(View.VISIBLE);
                    pokemonChange.setVisibility(View.VISIBLE);
                } else {
                    activatedBackGround = false;
                    h.postDelayed(new Runnable() {
                        public void run() {
                            constraintPk.setVisibility(View.INVISIBLE);
                            constraintPkb.setVisibility(View.INVISIBLE);
                            activatedBackGround = true;
                            textConstraint.setVisibility(View.VISIBLE);
                            screentext.setText("Hacer click en el fondo para ver el combate");
                            clicCounter = 0;
                        }
                    }, 1000);
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
                boolean textanimation;
                if (modeCombat && activatedBackGround) {
                    backgroundClicker();
                } else if (!modeCombat) {
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
                if (deadMode) {
                    if (p1Died) {
                        if (pk1py1.isAlive()) {
                            pokemonBack = new PokemonBattler(pk1py1);
                            p1Died = false;
                            checkIfDied();
                        }
                    } else {
                        if (pk1py2.isAlive()) {
                            pokemonFront = new PokemonBattler(pk1py2);
                            p2Died = false;
                            checkIfDied();
                        }
                    }

                } else if (turnManager == 1) {
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
                }
            }
        });

        pk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deadMode) {
                    if (p1Died) {
                        if (pk2py1.isAlive()) {
                            pokemonBack = new PokemonBattler(pk2py1);
                            p1Died = false;
                            checkIfDied();
                        }
                    } else {
                        if (pk2py2.isAlive()) {
                            pokemonFront = new PokemonBattler(pk2py2);
                            p2Died = false;
                            checkIfDied();
                        }
                    }

                } else if (turnManager == 1) {
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
                }
            }
        });

        pk3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deadMode) {
                    if (p1Died) {
                        if (pk3py1.isAlive()) {
                            pokemonBack = new PokemonBattler(pk3py1);
                            p1Died = false;
                            checkIfDied();
                        }
                    } else {
                        if (pk3py2.isAlive()) {
                            pokemonFront = new PokemonBattler(pk3py2);
                            p2Died = false;
                            checkIfDied();
                        }
                    }

                } else if (turnManager == 1) {
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
                    p2Atack = true;
                    changeTurn();
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

    private void randomBackground() {
        MediaPlayer bosque = MediaPlayer.create(Battle.this, R.raw.eterna_forest);
        MediaPlayer city = MediaPlayer.create(Battle.this, R.raw.anistar_city);
        MediaPlayer beach = MediaPlayer.create(Battle.this, R.raw.on_the_beach);
        MediaPlayer route = MediaPlayer.create(Battle.this, R.raw.route_209);
        MediaPlayer ghibli = MediaPlayer.create(Battle.this, R.raw.one_summer_day);
        MediaPlayer ruins = MediaPlayer.create(Battle.this, R.raw.underground_ruins_bw);
        MediaPlayer snow = MediaPlayer.create(Battle.this, R.raw.route_216);
        Random r = new Random();
        int random = r.nextInt(BACKGROUNDS.length);
        background.setImageResource(BACKGROUNDS[random]);
        switch (random) {

            case 1:
            case 2:
            case 25:
                bosque.start();
                break;

            case 5:
            case 6:
            case 40:
                city.start();
                break;

            case 0:
            case 7:
            case 26:
            case 31:
            case 32:
                beach.start();
                break;

            case 33:
            case 34:
            case 35:
            case 36:
                route.start();
                break;
            case 27:
            case 28:
            case 29:
            case 30:
                ghibli.start();
                break;
            case 39:
                ruins.start();
                break;
            case 11:
            case 13:
            case 15:
            case 22:
            case 23:
            case 24:
                snow.start();
                break;






        }


//        , 4fondo_circulo_azul, 5fondo_circulo_morado,  9fondo_futbol, 10fondo_futurista, 12fondo_luna, 13fondo_mansion,
//                 15fondo_monte, 17fondo_otonho,
//                18fondo_piscina,
//                19fondo_pixel_desierto_manhana, 20fondo_pixel_desierto_mediodia, 21fondo_pixel_desierto_noche,
//                22fondo_pixel_disco,
//                38fondo_rocoso, 39fondo_taller, 42fondo_volcan
    }

    private void showDeads() {
        cv1.setBackgroundColor(ContextCompat.getColor(this, R.color.greyp));
        cv2.setBackgroundColor(ContextCompat.getColor(this, R.color.greyp));
        cv3.setBackgroundColor(ContextCompat.getColor(this, R.color.greyp));
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        pokemon_team.setVisibility(View.VISIBLE);
        if (p1Died) {
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
        } else {
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
        }
    }

    private void checkFinal() {
        if (!player1Continue() && !player2Continue()) {
            Toast.makeText(this, "Empate", Toast.LENGTH_SHORT).show();
        } else if (!player1Continue()) {
            Toast.makeText(this, "Gana el jugador 2", Toast.LENGTH_SHORT).show();
        } else if (!player2Continue()) {
            Toast.makeText(this, "Gana el jugador 1", Toast.LENGTH_SHORT).show();
        } else {
            activatedBackGround = false;
            clicCounter = 0;
            checkIfDied();
        }
    }

    private void checkIfDied() {
        if (!p1Died && !p2Died) {
            pk.setVisibility(View.VISIBLE);
            pkb.setVisibility(View.VISIBLE);
            deadMode = false;
            modeCombat = false;
            activatedBackGround = false;
            playerturnscreen.setImageResource(t1);
            reverseBattlers();
            constraintPk.setVisibility(View.VISIBLE);
            constraintPkb.setVisibility(View.VISIBLE);
            pkbHealth.setVisibility(View.VISIBLE);
            changeTurn();
        } else {
            screentext.setText("Elije un nuevo pokemon");

            if (p1Died) playerturnscreen.setImageResource(t1);
            else playerturnscreen.setImageResource(t2);

            deadMode = true;
            playerturnscreen.setVisibility(View.VISIBLE);
        }
    }

    private void backgroundClicker() {
        if (clicCounter < firstPart.size()) {
            if (firstPart.get(clicCounter).charAt(0) == '/')
                executeCommand(firstPart.get(clicCounter));
            else screentext.setText(firstPart.get(clicCounter));
        } else {
            if (clicCounter == firstPart.size())
                generateSecondDialog(player1first);
            if (clicCounter < firstPart.size() + secondPart.size()) {
                if (secondPart.get(clicCounter - firstPart.size()).charAt(0) == '/')
                    executeCommand(secondPart.get(clicCounter - firstPart.size()));
                else screentext.setText(secondPart.get(clicCounter - firstPart.size()));
            }else if (clicCounter == firstPart.size() + secondPart.size()) {
                generateThirdDialog();
                if(thirdPart.size()!=0){
                    executeCommand(thirdPart.get(clicCounter-(firstPart.size()+secondPart.size())));
                }
            } else if (clicCounter < firstPart.size() + secondPart.size() + thirdPart.size()) {
                executeCommand(thirdPart.get(clicCounter-(firstPart.size()+secondPart.size())));
            } else {
                checkFinal();
            }
        }
        clicCounter++;
    }

    private void executeCommand(String command) {
        switch (command) {
            case "/backToBall1":
                commandBackToBall(1);
                break;
            case "/backToBall2":
                commandBackToBall(2);
                break;
            case "/newPokemonChange1":
                commandNewPokemonChange(1);
                break;
            case "/newPokemonChange2":
                commandNewPokemonChange(2);
                break;
            case "/wakeUp1":
                commandWakeUp(1);
                break;
            case "/wakeUp2":
                commandWakeUp(2);
                break;
            case "/defrost1":
                commandDefrost(1);
                break;
            case "/defrost2":
                commandDefrost(2);
                break;
            case "/newStatus1":
                commandNewStatus(1);
                break;
            case "/newStatus2":
                commandNewStatus(2);
                break;
            case "/attack1":
                commandAttack(1);
                break;
            case "/attack2":
                commandAttack(2);
                break;
            case "/kill1":
                commandKill(1);
                break;
            case "/kill2":
                commandKill(2);
                break;
            case "/newStatusSecond1":
                commandNewStatus(1);
                break;
            case "/newStatusSecond2":
                commandNewStatus(2);
                break;
            case "/healing1":
                commandHealing(1);
                break;
            case "/healing2":
                commandHealing(2);
                break;
            case "/recoil1":
                commandRecoil(1);
                break;
            case "/recoil2":
                commandRecoil(2);
                break;
            case "/killself1":
                commandKill(2);
                break;
            case "/killself2":
                commandKill(1);
                break;
            case "/burn1":commandBurn(1);
                break;
            case "/burn2":commandBurn(2);
                break;
            case "/poison1":commandPoison(1);
                break;
            case "/poison2":commandPoison(2);
                break;
        }
    }

    /*****************************COMMANDS*************************************/
    private void commandBackToBall(int player) {
        activatedBackGround = false;
        screentext.setText("");

        constraintPkb.setVisibility(View.INVISIBLE);
        constraintPk.setVisibility(View.INVISIBLE);

        if (player == 1) pkb.setVisibility(View.INVISIBLE);
        else pk.setVisibility(View.INVISIBLE);

        h.postDelayed(new Runnable() {
            public void run() {
                if (player == 1)
                    screentext.setText(pokemonBack.getName() + " ha vuelto a la pokeball");
                else screentext.setText(pokemonFront.getName() + " ha vuelto a la pokeball");
                activatedBackGround = true;
            }
        }, 500);
    }

    private void commandNewPokemonChange(int player) {
        activatedBackGround = false;
        if (player == 1) {
            pkb.setVisibility(View.VISIBLE);
            pokemonBack = new PokemonBattler(newPokemonP1);
        } else {
            pk.setVisibility(View.VISIBLE);
            pokemonFront = new PokemonBattler(newPokemonP2);
        }

        screentext.setText("");
        pk_name.setText(pokemonFront.getName());
        pkb_name.setText(pokemonBack.getName());
        pk_hpBar.setProgress(pokemonFront.hpPercent());
        pkb_hpBar.setProgress(pokemonBack.hpPercent());
        pk.setImageResource(pokemonFront.getImg());
        pkb.setImageResource(pokemonBack.getImgB());

        h.postDelayed(new Runnable() {
            public void run() {
                if (player == 1)
                    screentext.setText("Jugador 1 ha sacado a" + pokemonBack.getName());
                else screentext.setText("Jugador 2 ha sacado a " + pokemonFront.getName());
                activatedBackGround = true;
            }
        }, 500);
    }

    private void commandWakeUp(int player) {
        if (player == 1) {
            screentext.setText(pokemonBack.getName() + " ha despertado");
            pokemonBack.setStatus(0);
            if (pokemonBack.getNumDex() == pk1py1.getNumDex())
                pk1py1 = new PokemonBattler(pokemonBack);
            else if (pokemonBack.getNumDex() == pk2py1.getNumDex())
                pk2py1 = new PokemonBattler(pokemonBack);
            else if (pokemonBack.getNumDex() == pk3py1.getNumDex())
                pk3py1 = new PokemonBattler(pokemonBack);
        } else {
            screentext.setText(pokemonFront.getName() + " ha despertado");
            pokemonFront.setStatus(0);
            if (pokemonFront.getNumDex() == pk1py2.getNumDex())
                pk1py2 = new PokemonBattler(pokemonFront);
            else if (pokemonFront.getNumDex() == pk2py2.getNumDex())
                pk2py2 = new PokemonBattler(pokemonFront);
            else if (pokemonFront.getNumDex() == pk3py2.getNumDex())
                pk3py2 = new PokemonBattler(pokemonFront);
        }
    }

    private void commandDefrost(int player) {
        if (player == 1) {
            screentext.setText(pokemonBack.getName() + " se ha descongelado");
            pokemonBack.setStatus(0);
            if (pokemonBack.getNumDex() == pk1py1.getNumDex())
                pk1py1 = new PokemonBattler(pokemonBack);
            else if (pokemonBack.getNumDex() == pk2py1.getNumDex())
                pk2py1 = new PokemonBattler(pokemonBack);
            else if (pokemonBack.getNumDex() == pk3py1.getNumDex())
                pk3py1 = new PokemonBattler(pokemonBack);
        } else {
            screentext.setText(pokemonFront.getName() + " se ha descongelado");
            pokemonFront.setStatus(0);
            if (pokemonFront.getNumDex() == pk1py2.getNumDex())
                pk1py2 = new PokemonBattler(pokemonFront);
            else if (pokemonFront.getNumDex() == pk2py2.getNumDex())
                pk2py2 = new PokemonBattler(pokemonFront);
            else if (pokemonFront.getNumDex() == pk3py2.getNumDex())
                pk3py2 = new PokemonBattler(pokemonFront);
        }
    }

    private void commandNewStatus(int player) {
        if (player == 1) {
            pkstatus.setVisibility(View.VISIBLE);
            switch (moveP1.getStatus()) {
                case 1:
                    screentext.setText(pokemonFront.getName() + " ha sido paralizado");
                    pkstatus.setImageResource(paralizado);
                    break;
                case 2:
                    screentext.setText(pokemonFront.getName() + " ha sido quemado");
                    pkstatus.setImageResource(quemado);
                    break;
                case 3:
                    screentext.setText(pokemonFront.getName() + " ha sido envenenado puta");
                    pkstatus.setImageResource(envenenado);
                    break;
                case 4:
                    screentext.setText(pokemonFront.getName() + " se ha dormido");
                    pkstatus.setImageResource(dormido);
                    break;
                case 5:
                    screentext.setText(pokemonFront.getName() + " ha sido congelado");
                    pkstatus.setImageResource(congelado);
                    break;
            }
            pokemonFront.setStatus(moveP1.getStatus());
            if (pokemonFront.getNumDex() == pk1py2.getNumDex())
                pk1py2 = new PokemonBattler(pokemonFront);
            else if (pokemonFront.getNumDex() == pk2py2.getNumDex())
                pk2py2 = new PokemonBattler(pokemonFront);
            else if (pokemonFront.getNumDex() == pk3py2.getNumDex())
                pk3py2 = new PokemonBattler(pokemonFront);
        } else {
            pkbstatus.setVisibility(View.VISIBLE);
            switch (moveP2.getStatus()) {
                case 1:
                    screentext.setText(pokemonBack.getName() + " ha sido paralizado");
                    pkbstatus.setImageResource(paralizado);
                    break;
                case 2:
                    screentext.setText(pokemonBack.getName() + " ha sido quemado");
                    pkbstatus.setImageResource(quemado);
                    break;
                case 3:
                    screentext.setText(pokemonBack.getName() + " ha sido envenenado");
                    pkbstatus.setImageResource(envenenado);
                    break;
                case 4:
                    screentext.setText(pokemonBack.getName() + " se ha dormido");
                    pkbstatus.setImageResource(dormido);
                    break;
                case 5:
                    screentext.setText(pokemonBack.getName() + " ha sido congelado");
                    pkbstatus.setImageResource(congelado);
                    break;
            }
            pokemonBack.setStatus(moveP2.getStatus());
            if (pokemonBack.getNumDex() == pk1py1.getNumDex())
                pk1py1 = new PokemonBattler(pokemonBack);
            else if (pokemonBack.getNumDex() == pk2py1.getNumDex())
                pk2py1 = new PokemonBattler(pokemonBack);
            else if (pokemonBack.getNumDex() == pk3py1.getNumDex())
                pk3py1 = new PokemonBattler(pokemonBack);
        }
    }

    private void commandAttack(int player) {
        activatedBackGround = false;
        constraintPk.setVisibility(View.VISIBLE);
        constraintPkb.setVisibility(View.VISIBLE);
        textConstraint.setVisibility(View.INVISIBLE);
        if (player == 1) pokemonFront.setCurrentHp(pokemonFront.getCurrentHp() - damage);
        else pokemonBack.setCurrentHp(pokemonBack.getCurrentHp() - damage);

        if (player == 1) {
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateBars();
                    if (pokemonFront.getNumDex() == pk1py2.getNumDex())
                        pk1py2 = new PokemonBattler(pokemonFront);
                    else if (pokemonFront.getNumDex() == pk2py2.getNumDex())
                        pk2py2 = new PokemonBattler(pokemonFront);
                    else if (pokemonFront.getNumDex() == pk3py2.getNumDex())
                        pk3py2 = new PokemonBattler(pokemonFront);
                }
            }, 1000);
        } else {
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateBars();
                    if (pokemonBack.getNumDex() == pk1py1.getNumDex())
                        pk1py1 = new PokemonBattler(pokemonBack);
                    else if (pokemonBack.getNumDex() == pk2py1.getNumDex())
                        pk2py1 = new PokemonBattler(pokemonBack);
                    else if (pokemonBack.getNumDex() == pk3py1.getNumDex())
                        pk3py1 = new PokemonBattler(pokemonBack);

                }
            }, 500);
        }

        h.postDelayed(new Runnable() {
            public void run() {
                constraintPk.setVisibility(View.INVISIBLE);
                constraintPkb.setVisibility(View.INVISIBLE);
                textConstraint.setVisibility(View.VISIBLE);
                screentext.setText("...");
                activatedBackGround = true;

                //pasar al siguiente dialogo sin pasar por el backGround
                backgroundClicker();
            }
        }, 1000);
    }

    private void commandKill(int player) {
        if (player == 1) {
            pk.setVisibility(View.INVISIBLE);
            screentext.setText(pokemonFront.getName() + " ha sido debilitado");
            p2Died=true;
        } else {
            pkb.setVisibility(View.INVISIBLE);
            screentext.setText(pokemonBack.getName() + " ha sido debilitado");
            p1Died=true;
        }
    }

    //variable necesaria para este metodo
    private int hpAux;

    private void commandHealing(int player) {
        activatedBackGround = false;
        constraintPk.setVisibility(View.VISIBLE);
        constraintPkb.setVisibility(View.VISIBLE);
        textConstraint.setVisibility(View.INVISIBLE);

        h.postDelayed(new Runnable() {
            public void run() {
                if (player == 1) {
                    hpAux = pokemonBack.getCurrentHp();
                    pokemonBack.setCurrentHp(pokemonBack.getCurrentHp() + ((moveP1.getRestoreHPPorc() * pokemonBack.getCurrentHp()) / 100));
                    pkb_hpBar.setProgress(pokemonBack.hpPercent());
                    if (pokemonBack.getNumDex() == pk1py1.getNumDex())
                        pk1py1 = new PokemonBattler(pokemonBack);
                    else if (pokemonBack.getNumDex() == pk2py1.getNumDex())
                        pk2py1 = new PokemonBattler(pokemonBack);
                    else if (pokemonBack.getNumDex() == pk3py1.getNumDex())
                        pk3py1 = new PokemonBattler(pokemonBack);
                } else {
                    hpAux = pokemonFront.getCurrentHp();
                    pokemonFront.setCurrentHp(pokemonFront.getCurrentHp() + ((moveP2.getRestoreHPPorc() * pokemonFront.getCurrentHp()) / 100));
                    pk_hpBar.setProgress(pokemonFront.hpPercent());
                    if (pokemonFront.getNumDex() == pk1py2.getNumDex())
                        pk1py2 = new PokemonBattler(pokemonFront);
                    else if (pokemonFront.getNumDex() == pk2py2.getNumDex())
                        pk2py2 = new PokemonBattler(pokemonFront);
                    else if (pokemonFront.getNumDex() == pk3py2.getNumDex())
                        pk3py2 = new PokemonBattler(pokemonFront);
                }
            }
        }, 500);

        h.postDelayed(new Runnable() {
            public void run() {
                constraintPk.setVisibility(View.INVISIBLE);
                constraintPkb.setVisibility(View.INVISIBLE);
                textConstraint.setVisibility(View.VISIBLE);
                if (player == 1) {
                    screentext.setText(pokemonBack.getName() + " ha recuperado " + (pokemonBack.getCurrentHp() - hpAux) + " ps");
                } else {
                    screentext.setText(pokemonFront.getName() + " ha recuperado " + (pokemonFront.getCurrentHp() - hpAux) + " ps");
                }
                activatedBackGround = true;
            }
        }, 1000);
    }

    private void commandRecoil(int player) {
        activatedBackGround = false;
        constraintPk.setVisibility(View.VISIBLE);
        constraintPkb.setVisibility(View.VISIBLE);
        textConstraint.setVisibility(View.INVISIBLE);

        h.postDelayed(new Runnable() {
            public void run() {
                if (player == 1) {
                    hpAux = pokemonBack.getCurrentHp();
                    pokemonBack.setCurrentHp(pokemonBack.getCurrentHp() + ((moveP1.getRestoreHPPorc() * pokemonBack.getCurrentHp()) / 100));
                    pkb_hpBar.setProgress(pokemonBack.hpPercent());
                    if (pokemonBack.getNumDex() == pk1py1.getNumDex())
                        pk1py1 = new PokemonBattler(pokemonBack);
                    else if (pokemonBack.getNumDex() == pk2py1.getNumDex())
                        pk2py1 = new PokemonBattler(pokemonBack);
                    else if (pokemonBack.getNumDex() == pk3py1.getNumDex())
                        pk3py1 = new PokemonBattler(pokemonBack);
                } else {
                    hpAux = pokemonFront.getCurrentHp();
                    pokemonFront.setCurrentHp(pokemonFront.getCurrentHp() + ((moveP2.getRestoreHPPorc() * pokemonFront.getCurrentHp()) / 100));
                    pk_hpBar.setProgress(pokemonFront.hpPercent());
                    if (pokemonFront.getNumDex() == pk1py2.getNumDex())
                        pk1py2 = new PokemonBattler(pokemonFront);
                    else if (pokemonFront.getNumDex() == pk2py2.getNumDex())
                        pk2py2 = new PokemonBattler(pokemonFront);
                    else if (pokemonFront.getNumDex() == pk3py2.getNumDex())
                        pk3py2 = new PokemonBattler(pokemonFront);
                }
            }
        }, 500);

        h.postDelayed(new Runnable() {
            public void run() {
                constraintPk.setVisibility(View.INVISIBLE);
                constraintPkb.setVisibility(View.INVISIBLE);
                textConstraint.setVisibility(View.VISIBLE);
                if (player == 1) {
                    screentext.setText(pokemonBack.getName() + " ha perdido " + (hpAux - pokemonBack.getCurrentHp()) + " ps");
                } else {
                    screentext.setText(pokemonFront.getName() + " ha perdido " + (hpAux - pokemonFront.getCurrentHp()) + " ps");
                }
                activatedBackGround = true;
            }
        }, 1000);
    }

    private void commandBurn(int player){
        activatedBackGround=false;
        constraintPkb.setVisibility(View.VISIBLE);
        constraintPk.setVisibility(View.VISIBLE);
        textConstraint.setVisibility(View.INVISIBLE);

        h.postDelayed(new Runnable() {
            public void run() {
                if(player==1)pokemonBack.setCurrentHp((int)Math.round(pokemonBack.getCurrentHp()-(pokemonBack.getHp()/16)));
                else pokemonFront.setCurrentHp((int)Math.round(pokemonFront.getCurrentHp()-(pokemonFront.getHp()/16)));

                updateBars();

                if (player == 1)screentext.setText(pokemonBack.getName() + " se resiente de sus quemaduras");
                else screentext.setText(pokemonFront.getName() + " se resiente de sus quemaduras");

                if(pokemonBack.getNumDex()==pk1py1.getNumDex())pk1py1=new PokemonBattler(pokemonBack);
                else if(pokemonBack.getNumDex()==pk2py1.getNumDex())pk2py1=new PokemonBattler(pokemonBack);
                else if(pokemonBack.getNumDex()==pk3py1.getNumDex())pk3py1=new PokemonBattler(pokemonBack);

                if(pokemonFront.getNumDex()==pk1py2.getNumDex())pk1py2=new PokemonBattler(pokemonFront);
                else if(pokemonFront.getNumDex()==pk2py2.getNumDex())pk2py2=new PokemonBattler(pokemonFront);
                else if(pokemonFront.getNumDex()==pk3py2.getNumDex())pk3py2=new PokemonBattler(pokemonFront);
            }
        }, 500);

        h.postDelayed(new Runnable() {
            public void run() {
                constraintPk.setVisibility(View.INVISIBLE);
                constraintPkb.setVisibility(View.INVISIBLE);
                textConstraint.setVisibility(View.VISIBLE);
                activatedBackGround = true;
            }
        }, 1000);

    }

    private void commandPoison(int player){
        activatedBackGround=false;
        constraintPkb.setVisibility(View.VISIBLE);
        constraintPk.setVisibility(View.VISIBLE);
        textConstraint.setVisibility(View.INVISIBLE);

        h.postDelayed(new Runnable() {
            public void run() {
                if(player==1)pokemonBack.setCurrentHp((int)Math.round(pokemonBack.getCurrentHp()-(pokemonBack.getHp()/8)));
                else pokemonFront.setCurrentHp((int)Math.round(pokemonFront.getCurrentHp()-(pokemonFront.getHp()/8)));

                updateBars();

                if (player == 1)screentext.setText("el veneno resta ps a "+pokemonBack.getName());
                else screentext.setText("el veneno resta ps a "+pokemonFront.getName());

                if(pokemonBack.getNumDex()==pk1py1.getNumDex())pk1py1=new PokemonBattler(pokemonBack);
                else if(pokemonBack.getNumDex()==pk2py1.getNumDex())pk2py1=new PokemonBattler(pokemonBack);
                else if(pokemonBack.getNumDex()==pk3py1.getNumDex())pk3py1=new PokemonBattler(pokemonBack);

                if(pokemonFront.getNumDex()==pk1py2.getNumDex())pk1py2=new PokemonBattler(pokemonFront);
                else if(pokemonFront.getNumDex()==pk2py2.getNumDex())pk2py2=new PokemonBattler(pokemonFront);
                else if(pokemonFront.getNumDex()==pk3py2.getNumDex())pk3py2=new PokemonBattler(pokemonFront);
            }
        }, 500);

        h.postDelayed(new Runnable() {
            public void run() {
                constraintPk.setVisibility(View.INVISIBLE);
                constraintPkb.setVisibility(View.INVISIBLE);
                textConstraint.setVisibility(View.VISIBLE);
                activatedBackGround = true;
            }
        }, 1000);
    }
    /******************************* UI ******************************************/

    public void changeTurn() {
        turnManager++;
        if (turnManager > 3) turnManager = 1;
        reverseBattlers();

        if (turnManager == 1) {
            playerturnscreen.setImageResource(R.drawable.t1);
            playerturn.setImageResource(R.drawable.j1);
            textConstraint.setVisibility(View.INVISIBLE);
            playerturnscreen.setVisibility(View.VISIBLE);
        } else if (turnManager == 2) {
            playerturnscreen.setImageResource(R.drawable.t2);
            playerturn.setImageResource(R.drawable.j2);
            textConstraint.setVisibility(View.INVISIBLE);
            playerturnscreen.setVisibility(View.VISIBLE);
        }

        playerturn.setVisibility(View.VISIBLE);
        textConstraint.setVisibility(View.INVISIBLE);
        pkbHealth.setVisibility(View.VISIBLE);
        figth.setVisibility(View.INVISIBLE);
        pokemonChange.setVisibility(View.INVISIBLE);
        pokemon_team.setVisibility(View.INVISIBLE);

        hideMoves();

        updateBars();

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

        //ajustes en los status
        getPokemonStatus(pokemonBack, pkbstatus);
        getPokemonStatus(pokemonFront, pkstatus);


        if (turnManager == 3) {
            showBattle();
            playerturn.setVisibility(View.INVISIBLE);
        } else playerturn.setVisibility(View.VISIBLE);

    }

    //se activa el modo combate
    private void showBattle() {
        activatedBackGround = true;
        playerturnscreen.setVisibility(View.VISIBLE);
        pkbHealth.setVisibility(View.INVISIBLE);

        //AITANA CAMBIA AQUI LA PANTALLA DE COMBATE
        playerturnscreen.setImageResource(portada_vacia);

        modeCombat = true;

        clicCounter = 0;
        player1first = pokemonBackFirst();
        generateFirstDialog(player1first);
    }

    //BOTONES
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
                break;
            case 3:
                updateMove(1);
                updateMove(2);
                atk2.setVisibility(View.VISIBLE);
                atk3.setVisibility(View.VISIBLE);
                break;
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

    //cambio de pokemon
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

    private void updateBars() {
        //calculo de barras de vida
        pk_hpBar.setProgress(pokemonFront.hpPercent());
        pkb_hpBar.setProgress(pokemonBack.hpPercent());

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

        Drawable progressDrawable2 = pkb_hpBar.getProgressDrawable().mutate();
        if (pokemonBack.hpPercent() < 11) {
            progressDrawable2.setColorFilter(ContextCompat.getColor(this, R.color.redhp), android.graphics.PorterDuff.Mode.SRC_IN);
            pkb_hpBar.setProgressDrawable(progressDrawable2);
        } else if (pokemonBack.hpPercent() < 26) {
            progressDrawable2.setColorFilter(ContextCompat.getColor(this, R.color.orangehp), android.graphics.PorterDuff.Mode.SRC_IN);
            pkb_hpBar.setProgressDrawable(progressDrawable2);
        } else if (pokemonBack.hpPercent() < 51) {
            progressDrawable2.setColorFilter(ContextCompat.getColor(this, R.color.yellowhp), android.graphics.PorterDuff.Mode.SRC_IN);
            pkb_hpBar.setProgressDrawable(progressDrawable2);
        } else {
            progressDrawable2.setColorFilter(ContextCompat.getColor(this, R.color.greenhp), android.graphics.PorterDuff.Mode.SRC_IN);
            pkb_hpBar.setProgressDrawable(progressDrawable2);
        }
    }

    //estados
    private void getPokemonStatus(PokemonBattler pokemon, ImageView status) {
        status.setVisibility(View.VISIBLE);
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

    /*******************************Controller***************************************/

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

    public void reverseBattlers() {
        PokemonBattler aux = new PokemonBattler(pokemonBack);
        pokemonBack = new PokemonBattler(pokemonFront);
        pokemonFront = new PokemonBattler(aux);
    }

    //COMBATE
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

    // estado general del pokemon
    private boolean checkStatus(int player) {
        Random r = new Random();

        PokemonBattler focus;
        if (player == 1) focus = new PokemonBattler(pokemonBack);
        else focus = new PokemonBattler(pokemonFront);

        if ((player == 1 && !p1Atack) || (player == 2 && !p2Atack)) {
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
            } else if (focus.getStatus() == 5) {
                //si se descongela
                if (r.nextInt(10) == 0) {
                    toret.add("/defrost" + player);//comando
                } else {
                    toret.add(focus.getName() + " está congelado\nNo puede moverse!");
                    return false;
                }
            }
        }

        Move movefocus;
        if (player == 1) movefocus = new Move(moveP1);
        else movefocus = new Move(moveP2);

        toret.add(focus.getName() + " ha usado " + movefocus.getName());


        return true;
    }

    //ataque realizado
    private void checkMove(int player) {
        Random r = new Random();

        PokemonBattler focus;
        if (player == 1) focus = new PokemonBattler(pokemonBack);
        else focus = new PokemonBattler(pokemonFront);

        PokemonBattler victim;
        if (player == 1) victim = new PokemonBattler(pokemonFront);
        else victim = new PokemonBattler(pokemonBack);

        if (!victim.isAlive()) {
            toret.add("Pero no había objetivo");
            return;
        }

        Move movefocus;
        if (player == 1) movefocus = new Move(moveP1);
        else movefocus = new Move(moveP2);

        r = new Random();
        //si falla
        if (1 + r.nextInt(101) > movefocus.getAccuracy()) {
            toret.add("Pero ha fallado");
            return;
        }

        //efectividad
        double m = getMultiplayerEffectivity(movefocus.getType(), victim.getType1(), victim.getType2());
        //si no hace efecto por tipos o si es un movimiento de estado que ataca a un pokemon del mismo tipo
        if (m == 0 || (!movefocus.isAtkSt() && movefocus.getType() == victim.getType1()) || (!movefocus.isAtkSt() && movefocus.getType() == victim.getType2())) {
            toret.add("No tiene ningún efecto");
            return;
        }

        //si es un movimiento de estado
        if (!movefocus.isAtkSt()) {
            //if(movefocus.getId()==x)
            if (victim.getStatus() != 0) {
                toret.add(focus.getName() + " ha fallado el ataque");
            } else {
                toret.add("/newStatus" + player);//comando
            }
            return;
        }

        toret.add("/attack" + player);

        //si es supereficaz o no poco eficaz muestra el mensaje
        if (m == 2 || m == 4) toret.add("Es supereficaz!");
        else if (m == 0.5 || m == 0.25) toret.add("No es muy efectivo...");

        damage = (int) Math.round(getDamage(movefocus, focus, victim));

        if (victim.getCurrentHp() - damage <= 0) {
            toret.add("/kill" + player);
            if (player == 2) p1Died = true;
            else p2Died = true;
        } else if (r.nextInt(101) < movefocus.getStatusProb()) {
            if(victim.getType1()!=movefocus.getType()&&victim.getType2()!=movefocus.getType()||movefocus.getStatus()==4)toret.add("/newStatusSecond" + player);
            if (player == 2) p1Died = false;
            else p2Died = false;
        }

        //si recupera vida
        if (movefocus.getRestoreHPPorc() > 0) {
            toret.add("/healing" + player);
            return;
            //si la salud del atacante no sufre por retroceso
        } else if (movefocus.getRestoreHPPorc() == 0) {
            return;
        }

        toret.add("/recoil" + player);
        if (focus.getCurrentHp() + (movefocus.getRestoreHPPorc() * focus.getHp() * 0.01) <= 0) {
            toret.add("/killself" + player);
        }

    }

    private static double getDamage(Move move, PokemonBattler focus, PokemonBattler victim) {
        Random r = new Random();

        double e = getMultiplayerEffectivity(move.getType(), victim.getType1(), victim.getType2());
        double b;
        if (focus.getType1() == move.getType() || focus.getType2() == move.getType()) b = 1.5;
        else b = 1;
        int a = focus.getDmg();
        //si esta quemado
        if (focus.getStatus() == 2) a = (int) Math.round(a / 2);
        int n = 50;
        int v = 65 + r.nextInt(16);
        int p = move.getDmg();
        int d = victim.getDef();

        double p1 = 0.01 * b * e * v;
        double p2 = (0.2 * n + 1) * a * p;
        p2 = p2 / (25 * d);
        p2 = p2 + 2;

        return p1 * p2;
    }

    //DIALOGOS
    private ArrayList<String> generateDialogPlayer(int player) {
        toret = new ArrayList<String>();
        if (checkStatus(player)) {
            checkMove(player);
        }
        return toret;
    }

    //genera el primer dialogo de cada turno de combate
    private void generateFirstDialog(boolean p1first) {
        if (p1first) firstPart = generateDialogPlayer(1);
        else firstPart = generateDialogPlayer(2);
    }

    private void generateSecondDialog(boolean p1first) {
        if (p1first) secondPart = generateDialogPlayer(2);
        else secondPart = generateDialogPlayer(1);
    }

    private void generateThirdDialog() {
        thirdPart = new ArrayList<String>();
        if (pokemonBack.isAlive()) {
            if (pokemonBack.getStatus() == 2){
                thirdPart.add("/burn"+1);
                if((int)Math.round(pokemonBack.getCurrentHp()-(pokemonBack.getHp()/16))<=0){
                    thirdPart.add("/kill"+1);
                }
            }else if(pokemonBack.getStatus()==3){
                thirdPart.add("/poison"+1);
                if((int)Math.round(pokemonBack.getCurrentHp()-(pokemonBack.getHp()/8))<=0){
                    thirdPart.add("/kill"+2);
                }
            }
        }
        if (pokemonFront.isAlive()) {
            if (pokemonFront.getStatus() == 2){
                thirdPart.add("/burn"+2);
                if((int)Math.round(pokemonFront.getCurrentHp()-(pokemonFront.getHp()/16))<=0){
                    thirdPart.add("/kill"+1);
                }
            }else if(pokemonFront.getStatus()==3){
                thirdPart.add("/poison"+2);
                if((int)Math.round(pokemonFront.getCurrentHp()-(pokemonFront.getHp()/8))<=0){
                    thirdPart.add("/kill"+1);
                }
            }
        }
    }

    private static double getMultiplayerEffectivity(int moveType, int t1, int t2) {
        double toret = 1;

        switch (moveType) {
            //tipo bicho;
            case 1:
                if (t1 == 10 || t1 == 15 || t1 == 2) toret = 2;
                else if (t1 == 17 || t1 == 9 || t1 == 5 || t1 == 7 || t1 == 6 || t1 == 14 || t1 == 8)
                    toret = 0.5;
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

    //comprueba si al jugador 1 aun le quedan pokemons vivos
    private boolean player1Continue() {
        return (pk1py1.isAlive() || pk2py1.isAlive() || pk3py1.isAlive());
    }

    //comprueba si al jugador 2 aun le quedan pokemons vivos
    private boolean player2Continue() {
        return (pk1py2.isAlive() || pk2py2.isAlive() || pk3py2.isAlive());
    }


}

