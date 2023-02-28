package com.example.pokemonshowdown;

import java.util.ArrayList;

public class PokemonBattler extends  Pokemon{
    private int currentHp;
    private int status;
    private int hppercent;
    /*
    * 0-no status
    * 1-paralizado
    * 2-quemado
    * 3-envenenado
    * 4-dormido
    * 5-congelado
    * */

    //Constructor inicial
    public PokemonBattler(Pokemon pokemon){
        super(pokemon.getNumDex(), pokemon.getName(), pokemon.getType1(), pokemon.getType2(), pokemon.getHp(), pokemon.getDmg(), pokemon.getDef(), pokemon.getSpd(), pokemon.getImg(), pokemon.getImgB(),pokemon.getMoves());
        this.currentHp= pokemon.getHp();
        this.status=0;
        setMoves(new ArrayList<Move>());
    }

    //Constructor copia
    public PokemonBattler(PokemonBattler pokemon){
        super(pokemon.getNumDex(), pokemon.getName(), pokemon.getType1(), pokemon.getType2(), pokemon.getHp(), pokemon.getDmg(), pokemon.getDef(), pokemon.getSpd(), pokemon.getImg(), pokemon.getImgB(),pokemon.getMoves());
        currentHp=pokemon.getCurrentHp();
        status=pokemon.getStatus();
    }

    //GETTERS & SETTERS
    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        if(currentHp<0)currentHp=0;
        this.currentHp = currentHp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    //metodos para combate
    public boolean isAlive(){
        return(currentHp>0);
    }

    public int hpPercent(){
        double pc=(this.currentHp*100)/getHp();
        return (int)Math.round(pc);
    }
}
