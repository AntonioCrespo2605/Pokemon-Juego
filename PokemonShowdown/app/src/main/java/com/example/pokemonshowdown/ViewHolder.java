package com.example.pokemonshowdown;

import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewHolder {
    private FloatingActionButton fab;
    ImageView pk1;
    ImageView pk2;
    ImageView pk3;

    public ViewHolder(FloatingActionButton fab, ImageView pk1, ImageView pk2, ImageView pk3) {
        this.fab = fab;
        this.pk1 = pk1;
        this.pk2 = pk2;
        this.pk3 = pk3;

    }

    public FloatingActionButton getFab() {
        return fab;
    }

    public void setFab(FloatingActionButton fab) {
        this.fab = fab;
    }
}

