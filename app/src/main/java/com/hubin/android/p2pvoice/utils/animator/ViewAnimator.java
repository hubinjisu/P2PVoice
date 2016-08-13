package com.hubin.android.p2pvoice.utils.animator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

public class ViewAnimator {
    private static final String SAVEDINSTANCESTATE_FIRSTANIMATEDPOSITION = "savedinstancestate_firstanimatedposition";
    private static final String SAVEDINSTANCESTATE_LASTANIMATEDPOSITION = "savedinstancestate_lastanimatedposition";
    private static final String SAVEDINSTANCESTATE_SHOULDANIMATE = "savedinstancestate_shouldanimate";
    private static final int INITIAL_DELAY_MILLIS = 150;
    private static final int DEFAULT_ANIMATION_DELAY_MILLIS = 100;
    private static final int DEFAULT_ANIMATION_DURATION_MILLIS = 300;
    private final RecyclerView mRecyclerView;
    private final SparseArray<Animator> mAnimators = new SparseArray();
    private int mInitialDelayMillis = 150;
    private int mAnimationDelayMillis = 100;
    private int mAnimationDurationMillis = 300;
    private long mAnimationStartMillis;
    private int mFirstAnimatedPosition;
    private int mLastAnimatedPosition;
    private boolean mShouldAnimate = true;

    public ViewAnimator(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        this.mAnimationStartMillis = -1L;
        this.mFirstAnimatedPosition = -1;
        this.mLastAnimatedPosition = -1;
    }

    public void reset() {
        for(int i = 0; i < this.mAnimators.size(); ++i) {
            this.mAnimators.get(this.mAnimators.keyAt(i)).cancel();
        }

        this.mAnimators.clear();
        this.mFirstAnimatedPosition = -1;
        this.mLastAnimatedPosition = -1;
        this.mAnimationStartMillis = -1L;
        this.mShouldAnimate = true;
    }

    public void setShouldAnimateFromPosition(int position) {
        this.enableAnimations();
        this.mFirstAnimatedPosition = position - 1;
        this.mLastAnimatedPosition = position - 1;
    }

    public void setShouldAnimateNotVisible() {
        this.enableAnimations();
        this.mFirstAnimatedPosition = ((LinearLayoutManager)this.mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
        this.mLastAnimatedPosition = ((LinearLayoutManager)this.mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
    }

    void setLastAnimatedPosition(int lastAnimatedPosition) {
        this.mLastAnimatedPosition = lastAnimatedPosition;
    }

    public void setInitialDelayMillis(int delayMillis) {
        this.mInitialDelayMillis = delayMillis;
    }

    public void setAnimationDelayMillis(int delayMillis) {
        this.mAnimationDelayMillis = delayMillis;
    }

    public void setAnimationDurationMillis(int durationMillis) {
        this.mAnimationDurationMillis = durationMillis;
    }

    public void enableAnimations() {
        this.mShouldAnimate = true;
    }

    public void disableAnimations() {
        this.mShouldAnimate = false;
    }

    public void cancelExistingAnimation(View view) {
        int hashCode = view.hashCode();
        Animator animator = this.mAnimators.get(hashCode);
        if(animator != null) {
            animator.end();
            this.mAnimators.remove(hashCode);
        }

    }

    public void animateViewIfNecessary(int position, View view, Animator[] animators) {
        if(this.mShouldAnimate && position > this.mLastAnimatedPosition) {
            if(this.mFirstAnimatedPosition == -1) {
                this.mFirstAnimatedPosition = position;
            }

            this.animateView(position, view, animators);
            this.mLastAnimatedPosition = position;
        }

    }

    private void animateView(int position, View view, Animator[] animators) {
        if(this.mAnimationStartMillis == -1L) {
            this.mAnimationStartMillis = SystemClock.uptimeMillis();
        }

        ViewCompat.setAlpha(view, 0.0F);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animators);
        set.setStartDelay((long)this.calculateAnimationDelay(position));
        set.setDuration((long)this.mAnimationDurationMillis);
        set.start();
        this.mAnimators.put(view.hashCode(), set);
    }

    private int calculateAnimationDelay(int position) {
        int lastVisiblePosition = ((LinearLayoutManager)this.mRecyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
        int firstVisiblePosition = ((LinearLayoutManager)this.mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        if(this.mLastAnimatedPosition > lastVisiblePosition) {
            lastVisiblePosition = this.mLastAnimatedPosition;
        }

        int numberOfItemsOnScreen = lastVisiblePosition - firstVisiblePosition;
        int numberOfAnimatedItems = position - 1 - this.mFirstAnimatedPosition;
        int delay;
        int delaySinceStart;
        if(numberOfItemsOnScreen + 1 < numberOfAnimatedItems) {
            delay = this.mAnimationDelayMillis;
            if(this.mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
                delaySinceStart = ((GridLayoutManager)this.mRecyclerView.getLayoutManager()).getSpanCount();
                delay += this.mAnimationDelayMillis * (position % delaySinceStart);
                Log.d("GAB", "Delay[" + position + "]=*" + lastVisiblePosition + "|" + firstVisiblePosition + "|");
            }
        } else {
            delaySinceStart = (position - this.mFirstAnimatedPosition) * this.mAnimationDelayMillis;
            delay = Math.max(0, (int)(-SystemClock.uptimeMillis() + this.mAnimationStartMillis + (long)this.mInitialDelayMillis + (long)delaySinceStart));
        }

        Log.d("GAB", "Delay[" + position + "]=" + delay + "|" + lastVisiblePosition + "|" + firstVisiblePosition + "|");
        return delay;
    }

    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putInt("savedinstancestate_firstanimatedposition", this.mFirstAnimatedPosition);
        bundle.putInt("savedinstancestate_lastanimatedposition", this.mLastAnimatedPosition);
        bundle.putBoolean("savedinstancestate_shouldanimate", this.mShouldAnimate);
        return bundle;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if(parcelable instanceof Bundle) {
            Bundle bundle = (Bundle)parcelable;
            this.mFirstAnimatedPosition = bundle.getInt("savedinstancestate_firstanimatedposition");
            this.mLastAnimatedPosition = bundle.getInt("savedinstancestate_lastanimatedposition");
            this.mShouldAnimate = bundle.getBoolean("savedinstancestate_shouldanimate");
        }

    }
}
