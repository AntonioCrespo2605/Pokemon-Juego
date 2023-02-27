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

    public ImageView getPk1() {
        return pk1;
    }

    public void setPk1(ImageView pk1) {
        this.pk1 = pk1;
    }

    public ImageView getPk2() {
        return pk2;
    }

    public void setPk2(ImageView pk2) {
        this.pk2 = pk2;
    }

    public ImageView getPk3() {
        return pk3;
    }

    public void setPk3(ImageView pk3) {
        this.pk3 = pk3;
    }
}

