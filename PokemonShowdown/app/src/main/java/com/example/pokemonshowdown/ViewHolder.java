package com.example.pokemonshowdown;

import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewHolder {
    private FloatingActionButton fab;
    ImageView p1;
    ImageView p2;
    ImageView p3;

    public ViewHolder(FloatingActionButton fab) {
        this.fab = fab;

    }

    public FloatingActionButton getFab() {
        return fab;
    }

    public void setFab(FloatingActionButton fab) {
        this.fab = fab;
    }
}

