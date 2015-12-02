package com.zapp.library.merchant.ui;

import android.view.animation.Animation;

/**
 * Empty implementation of animation listener used to keep code cleaner as child classes can implement the callback methods which are in use.
 * @author msagi
 */
public abstract class AbstractAnimationListener implements Animation.AnimationListener {

    @Override
    public void onAnimationStart(final Animation animation) {
    }

    @Override
    public void onAnimationEnd(final Animation animation) {
    }

    @Override
    public void onAnimationRepeat(final Animation animation) {
    }
}
