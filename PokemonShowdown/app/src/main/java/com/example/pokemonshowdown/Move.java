package com.example.pokemonshowdown;

public class Move {

    private int id, dmg, hitProb, status, statusProb, type, restoreHPPorc;
    private boolean atkSt, priority;
    private String name;

    //CONSTRUCTOR
    public Move(int id, String name, int dmg, int hitProb, int status, int statusProb, int type, int restoreHPPorc, boolean atkSt, boolean priority) {
        this.id = id;
        this.dmg=dmg;
        this.hitProb = hitProb;
        this.status = status;
        this.statusProb = statusProb;
        this.type = type;
        this.atkSt = atkSt;
        this.name = name;
        this.restoreHPPorc=restoreHPPorc;
        this.priority=priority;
    }

    //GETTERS && SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHitProb() {
        return hitProb;
    }

    public void setHitProb(int hitProb) {
        this.hitProb = hitProb;
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
