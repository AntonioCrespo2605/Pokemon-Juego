package com.example.pokemonshowdown.uicontrollers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;

import com.example.pokemonshowdown.R;

public class ProgressBarAnimation extends Animation {
    private ProgressBar progressBar;
    private float from;
    private float  to;
    private Context context;

    public ProgressBarAnimation(ProgressBar progressBar, float from, float to, Context context) {
        super();
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
        this.context=context;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);

        Drawable progressDrawable = progressBar.getProgressDrawable().mutate();
        int perc=(int)value;
        if (perc < 11) {
            progressDrawable.setColorFilter(ContextCompat.getColor(context, R.color.redhp), android.graphics.PorterDuff.Mode.SRC_IN);
            progressBar.setProgressDrawable(progressDrawable);
        } else if (perc < 26) {
            progressDrawable.setColorFilter(ContextCompat.getColor(context, R.color.orangehp), android.graphics.PorterDuff.Mode.SRC_IN);
            progressBar.setProgressDrawable(progressDrawable);
        } else if (perc < 51) {
            progressDrawable.setColorFilter(ContextCompat.getColor(context, R.color.yellowhp), android.graphics.PorterDuff.Mode.SRC_IN);
            progressBar.setProgressDrawable(progressDrawable);
        } else {
            progressDrawable.setColorFilter(ContextCompat.getColor(context, R.color.greenhp), android.graphics.PorterDuff.Mode.SRC_IN);
            progressBar.setProgressDrawable(progressDrawable);
        }
    }
}
