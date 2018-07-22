package com.myfit.brownies.myfit;

/**
 * Created by essamyousry on 11/4/17.
 */

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Muhammad on 10/31/2017.
 */
public class Animations {

    final int DURATION = 1500;

    public void animateTextView(int initialValue, int finalValue, final TextView textview){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(initialValue, finalValue);
        valueAnimator.setDuration(DURATION);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                textview.setText(valueAnimator.getAnimatedValue().toString());
                if(textview.getText().toString() == "3000")  textview.setTextColor(Color.rgb(51, 204, 51));

            }
        });
        valueAnimator.start();
    }

    public void animateTextViewColor(int finalColor, final TextView textview) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(finalColor);
        valueAnimator.setDuration(DURATION);
        valueAnimator.setEvaluator(new ArgbEvaluator());

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                textview.setTextColor((Integer)valueAnimator.getAnimatedValue());

            }
        });
        valueAnimator.start();
    }
    public void animateProgressBar(int initialValue, int finalValue, final ProgressBar progressbar)
    {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(initialValue, finalValue);
        valueAnimator.setDuration(DURATION);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                progressbar.setProgress((int)valueAnimator.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }

}