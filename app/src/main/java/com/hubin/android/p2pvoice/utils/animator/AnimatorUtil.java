package com.hubin.android.p2pvoice.utils.animator;

import android.animation.Animator;

public class AnimatorUtil {
    private AnimatorUtil() {
    }

    public static Animator[] concatAnimators(Animator[] animators, Animator alphaAnimator) {
        Animator[] allAnimators = new Animator[animators.length + 1];
        int i = 0;
        Animator[] arr$ = animators;
        int len$ = animators.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Animator animator = arr$[i$];
            allAnimators[i] = animator;
            ++i;
        }

        allAnimators[allAnimators.length - 1] = alphaAnimator;
        return allAnimators;
    }
}
