package com.zapp.library.merchant.ui.fragment;

import com.zapp.library.merchant.R;
import com.zapp.library.merchant.ui.AnimationSetListener;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

/**
 * The fragment adds animation feature to base Zapp Popup Fragment.
 * @author msagi
 * @see PBBAPopupFragment
 */
public abstract class PBBAAnimatedPopupFragment extends PBBAPopupFragment {

    /**
     * The animation set to run with the fragment.
     */
    private AnimationSet mAnimationSet;

    /**
     * The popup frame view element of the PBBA Popup.
     */
    private View mPbbaPopupFrame;

    /**
     * The content wrapper view element of the PBBA Popup.
     */
    private View mPbbaPopupContent;

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPbbaPopupFrame = view.findViewById(R.id.pbba_popup_container);
        mPbbaPopupContent = view.findViewById(R.id.pbba_popup_content);
        animateIn();
    }

    /**
     * Execute starting animation of the fragment.
     */
    public void animateIn() {
        final Activity activity = getActivity();
        final Animation frameInAnimation = AnimationUtils.loadAnimation(activity, R.anim.pbba_popup_in);
        final Animation contentInAnimation = AnimationUtils.loadAnimation(activity, R.anim.pbba_popup_content_in);
        mPbbaPopupFrame.setAnimation(frameInAnimation);
        mPbbaPopupContent.setAnimation(contentInAnimation);
        mAnimationSet = new AnimationSet(/* shareInterpolator */ true);
        mAnimationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimationSet.addAnimation(frameInAnimation);
        mAnimationSet.addAnimation(contentInAnimation);
        mAnimationSet.setRepeatCount(0);
        mAnimationSet.start();
    }

    public void animateOut(@NonNull final Animation.AnimationListener animationListener) {
        final Activity activity = getActivity();
        final AnimationSetListener animationSetListener = new AnimationSetListener(animationListener);
        final Animation contentOutAnimation = AnimationUtils.loadAnimation(activity, R.anim.pbba_popup_content_out);
        contentOutAnimation.setAnimationListener(animationSetListener);
        final Animation frameOutAnimation = AnimationUtils.loadAnimation(activity, R.anim.pbba_popup_out);
        frameOutAnimation.setAnimationListener(animationSetListener);
        mPbbaPopupContent.clearAnimation();
        mPbbaPopupContent.setAnimation(contentOutAnimation);
        mPbbaPopupFrame.clearAnimation();
        mPbbaPopupFrame.setAnimation(frameOutAnimation);
        mAnimationSet = new AnimationSet(/* shareInterpolator */ true);
        mAnimationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimationSet.addAnimation(contentOutAnimation);
        mAnimationSet.addAnimation(frameOutAnimation);
        mAnimationSet.setRepeatCount(0);
        //since two animations are running at the same time and Y axis 'squeeze' takes longer, we set the content invisible so 'squeeze' animation will not blink
        //it when it kicks in
        mPbbaPopupContent.setVisibility(View.INVISIBLE);
        mAnimationSet.start();
    }
}
