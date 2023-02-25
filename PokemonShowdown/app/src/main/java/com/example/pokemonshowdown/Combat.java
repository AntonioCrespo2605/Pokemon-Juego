package com.example.pokemonshowdown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Combat extends AppCompatActivity {

    //VIEWS
    private ImageView background, playerturn, playerturnscreen, pk, pkb;
    private TextView screentext, pk_name, pk_hp, pk_maxhp, pkb_name, pkb_hp, pkb_maxhp;
    //Pkb es el pokemon del jugador que tiene el turno actualmente
    private FloatingActionButton figth, pokemonChange, atk1, atk2, atk3, atk4;
    private ConstraintLayout constraintPk, constraintPkb, textConstraint;

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
        screentext = (TextView) findViewById(R.id.screentext);
        pk_name = (TextView) findViewById(R.id.pk_name);
        pk_hp = (TextView) findViewById(R.id.pk_actual_hp);
        pk_maxhp = (TextView) findViewById(R.id.pk_maxhp);
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







    }
}

