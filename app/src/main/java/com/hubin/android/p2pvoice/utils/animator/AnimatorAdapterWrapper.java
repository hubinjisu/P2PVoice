package com.hubin.android.p2pvoice.utils.animator;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Description:
 * <p/>
 * Author: hubin
 * Date: 2016/6/28.
 */
public class AnimatorAdapterWrapper<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T>
{
    public static final int ANIMATOR_TYPE_ALPHA = 0;
    public static final int ANIMATOR_TYPE_SLIDE_IN_LEFT = 1;
    public static final int ANIMATOR_TYPE_SLIDE_IN_RIGHT = 2;
    public static final int ANIMATOR_TYPE_SWING_IN_BOTTOM = 3;
    public static final int ANIMATOR_TYPE_SLIDE_IN_BOTTOM = 4;
    public static final int ANIMATOR_TYPE_SCALE_IN = 5;
    private static final String TRANSLATION_X = "translationX";
    private static final String TRANSLATION_Y = "translationY";
    private static final String SCALE_X = "scaleX";
    private static final String SCALE_Y = "scaleY";
    private static final String ALPHA = "alpha";
    private static final float DEFAULT_SCALE_FROM = 0.6f;
    protected RecyclerView mRecyclerView;
    private int animatorType;
    private RecyclerView.Adapter<T> mAdapter;
    private ViewAnimator mViewAnimator;

    public AnimatorAdapterWrapper(RecyclerView.Adapter<T> adapter, RecyclerView recyclerView, int animatorType)
    {
        this.mAdapter = adapter;
        this.mViewAnimator = new ViewAnimator(recyclerView);
        this.mRecyclerView = recyclerView;
        this.animatorType = animatorType;
    }

    public ViewAnimator getViewAnimator()
    {
        return this.mViewAnimator;
    }

    public Parcelable onSaveInstanceState()
    {
        Bundle bundle = new Bundle();
        if (this.mViewAnimator != null)
        {
            bundle.putParcelable("savedinstancestate_viewanimator", this.mViewAnimator.onSaveInstanceState());
        }

        return bundle;
    }

    public void onRestoreInstanceState(Parcelable parcelable)
    {
        if (parcelable instanceof Bundle)
        {
            Bundle bundle = (Bundle) parcelable;
            if (this.mViewAnimator != null)
            {
                this.mViewAnimator.onRestoreInstanceState(bundle.getParcelable("savedinstancestate_viewanimator"));
            }
        }

    }

    public T onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return this.mAdapter.onCreateViewHolder(parent, viewType);
    }

    public void onBindViewHolder(T holder, int position)
    {
        this.mAdapter.onBindViewHolder(holder, position);
        this.mViewAnimator.cancelExistingAnimation(holder.itemView);
        this.animateView(holder.itemView, position);
    }

    private void animateView(View view, int position)
    {
        assert this.mViewAnimator != null;
        assert this.mRecyclerView != null;

        Animator[] animators = this.getAnimators(view);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, ALPHA, 0.0F, 1.0F);
        Animator[] concatAnimators = AnimatorUtil.concatAnimators(animators, alphaAnimator);
        this.mViewAnimator.animateViewIfNecessary(position, view, concatAnimators);
    }

    public Animator[] getAnimators(View view)
    {
        switch (animatorType)
        {
            case ANIMATOR_TYPE_ALPHA:
                return new Animator[0];
            case ANIMATOR_TYPE_SLIDE_IN_LEFT:
                return new Animator[]{ObjectAnimator.ofFloat(view, TRANSLATION_X, 0 - mRecyclerView.getLayoutManager().getWidth(), 0)};
            case ANIMATOR_TYPE_SLIDE_IN_RIGHT:
                return new Animator[]{ObjectAnimator.ofFloat(view, TRANSLATION_X, (float) this.mRecyclerView.getLayoutManager().getWidth(), 0.0F)};
            case ANIMATOR_TYPE_SWING_IN_BOTTOM:
                float mOriginalY = mRecyclerView.getLayoutManager().getDecoratedTop(view);
                float mDeltaY = mRecyclerView.getHeight() - mOriginalY;
                return new Animator[]{ObjectAnimator.ofFloat(view, TRANSLATION_Y, mDeltaY, 0)};
            case ANIMATOR_TYPE_SLIDE_IN_BOTTOM:
                return new Animator[]{ObjectAnimator.ofFloat(view, TRANSLATION_Y, mRecyclerView.getMeasuredHeight() >> 1, 0)};
            case ANIMATOR_TYPE_SCALE_IN:
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, SCALE_X, DEFAULT_SCALE_FROM, 1f);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, SCALE_Y, DEFAULT_SCALE_FROM, 1f);
                return new ObjectAnimator[]{scaleX, scaleY};
            default:
                return new Animator[0];
        }
    }

    public int getItemCount()
    {
        return this.mAdapter.getItemCount();
    }

    public int getItemViewType(int position)
    {
        return this.mAdapter.getItemViewType(position);
    }

    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer)
    {
        super.registerAdapterDataObserver(observer);
        this.mAdapter.registerAdapterDataObserver(observer);
    }

    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer)
    {
        super.unregisterAdapterDataObserver(observer);
        this.mAdapter.unregisterAdapterDataObserver(observer);
    }

    public void setHasStableIds(boolean hasStableIds)
    {
        this.mAdapter.setHasStableIds(hasStableIds);
    }

    public long getItemId(int position)
    {
        return this.mAdapter.getItemId(position);
    }

    public void onViewRecycled(T holder)
    {
        this.mAdapter.onViewRecycled(holder);
    }

    public boolean onFailedToRecycleView(T holder)
    {
        return this.mAdapter.onFailedToRecycleView(holder);
    }

    public void onViewAttachedToWindow(T holder)
    {
        this.mAdapter.onViewAttachedToWindow(holder);
    }

    public void onViewDetachedFromWindow(T holder)
    {
        this.mAdapter.onViewDetachedFromWindow(holder);
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        this.mAdapter.onAttachedToRecyclerView(recyclerView);
    }

    public void onDetachedFromRecyclerView(RecyclerView recyclerView)
    {
        this.mAdapter.onDetachedFromRecyclerView(recyclerView);
    }
}
