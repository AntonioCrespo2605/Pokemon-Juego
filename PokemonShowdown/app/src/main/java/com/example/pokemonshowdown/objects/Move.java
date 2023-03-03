package com.example.pokemonshowdown.objects;

public class Move {

    private int id, dmg, accuracy, status, statusProb, type, restoreHPPorc;
    private boolean atkSt, priority;
    private String name;

    //CONSTRUCTOR
    public Move(int id, String name, int dmg, int accuracy, int status, int statusProb, int type, int restoreHPPorc, boolean atkSt, boolean priority) {
        this.id = id;
        this.dmg = dmg;
        this.accuracy = accuracy;
        this.status = status;
        this.statusProb = statusProb;
        this.type = type;
        this.atkSt = atkSt;
        this.name = name;
        this.restoreHPPorc = restoreHPPorc;
        this.priority = priority;
    }

    //constructor pocho

    public Move(String name, int dmg, int hitProb){
        this.name = name;
        this.dmg = dmg;
        this.accuracy = hitProb;

    }

    public Move(Move move){
        this.id = move.getId();
        this.dmg = move.getDmg();
        this.accuracy = move.getAccuracy();
        this.status = move.getStatus();
        this.statusProb = move.getStatusProb();
        this.type = move.getType();
        this.atkSt = move.isAtkSt();
        this.name = move.getName();
        this.restoreHPPorc = move.getRestoreHPPorc();
        this.priority = move.isPriority();
    }

    //GETTERS && SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatusProb() {
        return statusProb;
    }

    public void setStatusProb(int statusProb) {
        this.statusProb = statusProb;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isAtkSt() {
        return atkSt;
    }

    public void setAtkSt(boolean atkSt) {
        this.atkSt = atkSt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public int getRestoreHPPorc() {
        return restoreHPPorc;
    }

    public void setRestoreHPProb(int restoreHPProb) {
        this.restoreHPPorc = restoreHPProb;
    }

    public void setRestoreHPPorc(int restoreHPPorc) {
        this.restoreHPPorc = restoreHPPorc;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }


}
