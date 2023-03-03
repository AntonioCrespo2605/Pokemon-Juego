package com.example.pokemonshowdown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.pokemonshowdown.objects.DBHandler;
import com.example.pokemonshowdown.objects.Move;
import com.example.pokemonshowdown.objects.Pokemon;
import com.example.pokemonshowdown.uicontrollers.PokemonRecyclerViewAdapter;
import com.example.pokemonshowdown.uicontrollers.ThisViewHolder;
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
    private ImageView pantJ, player;
    private ImageView pk1;
    private ImageView pk2;
    private ImageView pk3;

    private boolean changeActivity = false;

    private ArrayList<Pokemon> pPlyr1;
    private ArrayList<Pokemon> pPlyr2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doBindService();
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        startService(music);


        //VIEWS
        ll = findViewById(R.id.linearLayoutP);
        next = findViewById(R.id.nextB);
        pantJ = findViewById(R.id.pantalla_jugador);
        player = findViewById(R.id.player);
        pk1 = findViewById(R.id.pokemon);
        pk2 = findViewById(R.id.pk2);
        pk3 = findViewById(R.id.pk3);
        //INVISIBLE
        next.setVisibility(View.INVISIBLE);
        pantJ.setVisibility(View.VISIBLE);
        pk1.setVisibility(View.INVISIBLE);
        pk2.setVisibility(View.INVISIBLE);
        pk3.setVisibility(View.INVISIBLE);

        next.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.redpy)));

        //pantalla para indicar el turno del jugador
        pantJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pantJ.setVisibility(View.INVISIBLE);
            }
        });

        //boton para continuar a la siguiente activity o cambiar el turno
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillPokemons();
                if (changeActivity) {
                    Intent intent = new Intent(MainActivity.this, MovementsPicker.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("pk1py1", pPlyr1.get(0).getNumDex());
                    intent.putExtra("pk2py1", pPlyr1.get(1).getNumDex());
                    intent.putExtra("pk3py1", pPlyr1.get(2).getNumDex());
                    intent.putExtra("pk1py2", pPlyr2.get(0).getNumDex());
                    intent.putExtra("pk2py2", pPlyr2.get(1).getNumDex());
                    intent.putExtra("pk3py2", pPlyr2.get(2).getNumDex());
                    startActivity(intent);
                    finish();
                } else {
                    //CAMBIO DE TURNO
                    changeActivity = true;
                    pantJ.setVisibility(View.VISIBLE);
                    pantJ.setImageResource(R.drawable.t2p);
                    next.setVisibility(View.INVISIBLE);
                    next.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.bluepy)));
                    player.setImageResource(R.drawable.j2);
                    pk1.setVisibility(View.INVISIBLE);
                    pk2.setVisibility(View.INVISIBLE);
                    pk3.setVisibility(View.INVISIBLE);
                }
            }
        });

        handler = new DBHandler(this);
        pokemonList = handler.getPokemons();
        RecyclerView rv = findViewById(R.id.recyclerview_id);
        ThisViewHolder p = new ThisViewHolder(next, pk1, pk2, pk3);
        myAdapter = new PokemonRecyclerViewAdapter(this, pokemonList, p);
        rv.setLayoutManager(new GridLayoutManager(this, 3));
        rv.setAdapter(myAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (mServ.isPlaying()) {
                mServ.pauseMusic();
            }
        }catch (Exception e){
            System.err.println("Error" + e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (!mServ.isPlaying()) {
                mServ.resumeMusic();
            }
        }catch (Exception e){
            System.err.println("Error" + e);
        }
    }

    private void fillPokemons() {
        if (changeActivity) {
            pPlyr2 = myAdapter.getSelected();
        } else {
            pPlyr1 = myAdapter.getSelected();
            RecyclerView rv = findViewById(R.id.recyclerview_id);
            ThisViewHolder p = new ThisViewHolder(next, pk1, pk2, pk3);
            myAdapter = new PokemonRecyclerViewAdapter(this, pokemonList, p);
            rv.setLayoutManager(new GridLayoutManager(this, 3));
            rv.setAdapter(myAdapter);
        }
    }

    //servicio de musica
    private boolean mIsBound = false;
    private MusicService mServ;
    private ServiceConnection Scon = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder) binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService() {
        bindService(new Intent(this, MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        if (mIsBound) {
            unbindService(Scon);
            mIsBound = false;
        }
    }
}