package com.hubin.android.p2pvoice.utils.animator;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @see RecyclerView#setItemAnimator(RecyclerView.ItemAnimator)
 */
public class SlideInOutRightItemAnimator extends BaseItemAnimator
{
    public static final int ITEM_ANIMATOR_TYPE_SCALE_IN_OUT = 0;
    public static final int ITEM_ANIMATOR_TYPE_SLIDE_IN_OUT_TOP = 1;
    public static final int ITEM_ANIMATOR_TYPE_SLIDE_IN_OUT_RIGHT = 2;
    public static final int ITEM_ANIMATOR_TYPE_SLIDE_IN_OUT_LEFT = 3;
    public static final int ITEM_ANIMATOR_TYPE_SLIDE_IN_OUT_BOTTOM = 4;
    public static final int ITEM_ANIMATOR_TYPE_SCALE_IN_OUT_RIGHT = 5;

    private int animatorType;

    public SlideInOutRightItemAnimator(RecyclerView recyclerView, int animatorType)
    {
        super(recyclerView);
        this.animatorType = animatorType;
    }

    protected void animateRemoveImpl(final RecyclerView.ViewHolder holder)
    {
        final View view = holder.itemView;
        final ViewPropertyAnimatorCompat animation = ViewCompat.animate(view);
        mRemoveAnimations.add(holder);
        animation.setDuration(getRemoveDuration())
                .alpha(0)
                .translationX(+mRecyclerView.getLayoutManager().getWidth())
                .setListener(new VpaListenerAdapter()
                {
                    @Override
                    public void onAnimationStart(View view)
                    {
                        dispatchRemoveStarting(holder);
                    }

                    @Override
                    public void onAnimationEnd(View view)
                    {
                        animation.setListener(null);
                        ViewCompat.setAlpha(view, 1);
                        ViewCompat.setTranslationX(view, +mRecyclerView.getLayoutManager().getWidth());
                        dispatchRemoveFinished(holder);
                        mRemoveAnimations.remove(holder);
                        dispatchFinishedWhenDone();
                    }
                }).start();
    }

    @Override
    protected void prepareAnimateAdd(RecyclerView.ViewHolder holder)
    {
        switch (animatorType)
        {
            case ITEM_ANIMATOR_TYPE_SCALE_IN_OUT:
                break;
            case ITEM_ANIMATOR_TYPE_SCALE_IN_OUT_RIGHT:
                break;
            case ITEM_ANIMATOR_TYPE_SLIDE_IN_OUT_BOTTOM:
                break;
            case ITEM_ANIMATOR_TYPE_SLIDE_IN_OUT_LEFT:
                break;
            case ITEM_ANIMATOR_TYPE_SLIDE_IN_OUT_RIGHT:
                ViewCompat.setTranslationX(holder.itemView, +mRecyclerView.getLayoutManager().getWidth());
                break;
            case ITEM_ANIMATOR_TYPE_SLIDE_IN_OUT_TOP:
                break;
        }
    }

    protected void animateAddImpl(final RecyclerView.ViewHolder holder)
    {
        final View view = holder.itemView;
        final ViewPropertyAnimatorCompat animation = ViewCompat.animate(view);
        mAddAnimations.add(holder);
        animation.translationX(0)
                .alpha(1)
                .setDuration(getAddDuration())
                .setListener(new VpaListenerAdapter()
                {
                    @Override
                    public void onAnimationStart(View view)
                    {
                        dispatchAddStarting(holder);
                    }

                    @Override
                    public void onAnimationCancel(View view)
                    {
                        ViewCompat.setTranslationX(view, 0);
                        ViewCompat.setAlpha(view, 1);
                    }

                    @Override
                    public void onAnimationEnd(View view)
                    {
                        animation.setListener(null);
                        dispatchAddFinished(holder);
                        ViewCompat.setTranslationX(view, 0);
                        ViewCompat.setAlpha(view, 1);
                        mAddAnimations.remove(holder);
                        dispatchFinishedWhenDone();
                    }
                }).start();
    }

}

