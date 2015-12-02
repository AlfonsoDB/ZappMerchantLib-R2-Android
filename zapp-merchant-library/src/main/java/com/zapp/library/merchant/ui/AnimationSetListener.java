package com.zapp.library.merchant.ui;

import android.support.annotation.NonNull;
import android.view.animation.Animation;

/**
 * Animation set listener to provide single callback for animation set.
 *
 * @author msagi
 */
public class AnimationSetListener extends AbstractAnimationListener {

    /**
     * The number of animations currently running.
     */
    private int mAnimationsRunning = 0;

    /**
     * The wrapped animation listener callback.
     */
    private final Animation.AnimationListener mAnimationListener;

    /**
     * Create new instance.
     *
     * @param animationListener The animation listener callback to forward animation callbacks to.
     */
    public AnimationSetListener(final @NonNull Animation.AnimationListener animationListener) {
        mAnimationListener = animationListener;
    }

    @Override
    public void onAnimationStart(final Animation animation) {
        if (mAnimationsRunning == 0) {
            //forwarding the first animation start event to wrapped callback interface
            mAnimationListener.onAnimationStart(animation);
        }
        mAnimationsRunning++;
    }

    @Override
    public void onAnimationEnd(final Animation animation) {
        mAnimationsRunning--;
        if (mAnimationsRunning == 0) {
            //forwarding the last animation stop event to wrapped callback interface
            mAnimationListener.onAnimationEnd(animation);
        }
    }
}
