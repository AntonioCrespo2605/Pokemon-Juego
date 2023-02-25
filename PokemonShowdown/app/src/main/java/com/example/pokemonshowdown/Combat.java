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

public class Combat extends AppCompatActivity {

    //VIEWS
    private ImageView background, playerturn, playerturnscreen, pk, pkb, pkbstatus, pkstatus;
    private TextView screentext, pk_name, pkb_name, pkb_hp, pkb_maxhp;
    //Pkb es el pokemon del jugador que tiene el turno actualmente
    private FloatingActionButton figth, pokemonChange, atk1, atk2, atk3, atk4;
    private ConstraintLayout constraintPk, constraintPkb, textConstraint;
    private ProgressBar pk_hpBar, pkb_hpBar;
    private LinearLayout pkbHealth;

    //ANTONIO RECUERDA LO DEL ALEATORIO DE LOS BACKGROUNDS



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

        //INICIO
        //background.setImageResource() lo que sea
        pk_hpBar.setProgress(100);
        pkb_hpBar.setProgress(100);
        pkbstatus.setVisibility(View.INVISIBLE);
        pkstatus.setVisibility(View.INVISIBLE);
        changeTurn(1);
        //despues del click
        playerturn.setVisibility(View.INVISIBLE);

    }

    public void changeTurn(int pturn){
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
        if(pturn == 1){
            //ESTO ES PROVISIONAL XQ AUN NO EXISTE LA IMAGEN CORRESPONDIENTE
            playerturnscreen.setImageResource(R.drawable.t1p);
            playerturn.setImageResource(R.drawable.j1);
            //pkb.setImageResource(); jugador actual
            //pk.setImageResource();
            //CAMBIA EL ESTADO DEL POKEMON SI ES NECESARIO
            //Cambia las progres bar y las vida maxima y actual



        }else if (pturn == 2){
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

    public void selectMove(){
        figth.setVisibility(View.INVISIBLE);
        pokemonChange.setVisibility(View.INVISIBLE);
        atk1.setVisibility(View.VISIBLE);
        atk2.setVisibility(View.VISIBLE);
        atk3.setVisibility(View.VISIBLE);
        atk3.setVisibility(View.VISIBLE);

        //tendras q poner el codigo para cambiar los ataques

    }

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

