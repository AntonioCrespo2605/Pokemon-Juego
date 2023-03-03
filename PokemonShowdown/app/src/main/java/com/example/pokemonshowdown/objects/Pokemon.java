package com.example.pokemonshowdown.objects;

import com.example.pokemonshowdown.objects.Move;

import java.util.ArrayList;

public class Pokemon {
    private int numDex, type1, type2, hp, dmg, def, spd, img, imgB;
    private String name;
    private ArrayList<Move> moves;

    //CONSTRUCTORS
    public Pokemon(int numDex, String name, int type1, int type2, int hp, int dmg, int def, int spd, int img, int imgB, ArrayList<Move> moves) {
        this.numDex = numDex;
        this.type1 = type1;
        this.type2 = type2;
        this.hp = hp;
        this.dmg = dmg;
        this.def = def;
        this.spd = spd;
        this.img = img;
        this.imgB = imgB;
        this.name = name;
        this.moves = moves;
    }

    public Pokemon(int numDex, String name, int type1, int type2, int hp, int dmg, int def, int spd, int img, int imgB) {
        this.numDex = numDex;
        this.type1 = type1;
        this.type2 = type2;
        this.hp = hp;
        this.dmg = dmg;
        this.def = def;
        this.spd = spd;
        this.img = img;
        this.imgB = imgB;
        this.name = name;
    }

    public void addMovesById(int[] msid, ArrayList<Move> ms) {
        ArrayList<Move> add = new ArrayList<>();

        for(int i=0;i<msid.length;i++){
            add.add(getMoveByIdFrom(msid[i], ms));
        }

        this.setMoves(add);
    }

    private Move getMoveByIdFrom(int id, ArrayList<Move> ms){
        for(Move m:ms){
            if (m.getId()==id)return m;
        }
        return ms.get(0);
    }

    //GETTERS && SETTERS
    public int getNumDex() {
        return numDex;
    }

    public void setNumDex(int numDex) {
        this.numDex = numDex;
    }

    public int getType1() {
        return type1;
    }

    public void setType1(int type1) {
        this.type1 = type1;
    }

    public int getType2() {
        return type2;
    }

    public void setType2(int type2) {
        this.type2 = type2;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getSpd() {
        return spd;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getImgB() {
        return imgB;
    }

    public void setImgB(int imgB) {
        this.imgB = imgB;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<Move> moves) {
        this.moves = moves;
    }

    @Override
    public String toString() {
        return "Pokemon{" +

                ", name='" + name + '\n' ;
    }
}
