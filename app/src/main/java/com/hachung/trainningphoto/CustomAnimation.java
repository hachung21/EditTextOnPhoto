package com.hachung.trainningphoto;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.animation.TranslateAnimation;

public class CustomAnimation {

    //    public void slideUp(View view) {
//        view.setVisibility(View.VISIBLE);
//        TranslateAnimation animate = new TranslateAnimation(
//                0,                 // fromXDelta
//                0,                 // toXDelta
//                view.getHeight(),  // fromYDelta
//                0);                // toYDelta
//        animate.setDuration(500);
//        animate.setFillAfter(true);
//        view.startAnimation(animate);
//    }
//
//    // slide the view from its current position to below itself
//    public void slideDown(View view) {
//        TranslateAnimation animate = new TranslateAnimation(
//                0,                 // fromXDelta
//                0,                 // toXDelta
//                0,                 // fromYDelta
//                view.getHeight()); // toYDelta
//        animate.setDuration(500);
//        animate.setFillAfter(true);
//        view.startAnimation(animate);
//
//    }
    public void scrolledDown(View view) {
        view.animate()
                .translationY(view.getHeight())
                .alpha(0.0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                    }
                });
    }

    public void scrolledUp(View view) {

        view.animate()
                .translationY(0)
                .alpha(1.0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setVisibility(View.VISIBLE);
                    }
                });
    }
}
