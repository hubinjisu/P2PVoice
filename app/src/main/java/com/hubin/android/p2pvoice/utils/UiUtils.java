package com.hubin.android.p2pvoice.utils;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by hubin on 2016/8/6.
 */
public class UiUtils
{
    private static final String TAG = "UiUtils";
    /**
     * 展开伸缩动画
     */
    public static Animation expand(final View v, final int lowerHeight, final int higherHeight, final boolean expand, long duration)
    {
        try
        {
            if (expand)
            {
                v.getLayoutParams().height = lowerHeight;
            }
            else
            {
                v.getLayoutParams().height = higherHeight;
            }
            v.setVisibility(View.VISIBLE);
            Animation a = new Animation()
            {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t)
                {
                    int newHeight = 0;
                    if (expand)
                    {
                        newHeight = (int) (lowerHeight + (higherHeight - lowerHeight) * interpolatedTime);
                    }
                    else
                    {
                        newHeight = (int) (higherHeight - (higherHeight - lowerHeight) * interpolatedTime);
                    }
                    v.getLayoutParams().height = newHeight;
                    v.requestLayout();
                    if (interpolatedTime == 1 && !expand && lowerHeight == 0)
                    {
                        v.setVisibility(View.GONE);
                    }
                }

                @Override
                public boolean willChangeBounds()
                {
                    return true;
                }
            };
            a.setDuration(duration);
            a.setInterpolator(new AccelerateInterpolator());
            return a;
        }
        catch (Exception e)
        {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public static Animation rotate3D(final float fromDegrees, final float toDegrees, final float centerX, final float centerY, final float depthZ, final
    boolean reverse, long duration)
    {
        try
        {
            final Camera camera = new Camera();
            Animation a = new Animation()
            {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t)
                {
                    Matrix matrix = t.getMatrix();
                    float degrees = fromDegrees + ((toDegrees - fromDegrees) * interpolatedTime);
                    camera.save();
                    if (reverse)
                    {
                        camera.translate(0.0f, 0.0f, depthZ * interpolatedTime);
                    }
                    else
                    {
                        camera.translate(0.0f, 0.0f, depthZ * (1.0f - interpolatedTime));
                    }
                    camera.rotateX(degrees);
                    camera.getMatrix(matrix);
                    camera.restore();

                    matrix.preTranslate(-centerX, -centerY);
                    matrix.postTranslate(centerX, centerY);
                }

                @Override
                public boolean willChangeBounds()
                {
                    return true;
                }
            };
            a.setDuration(duration);
            return a;
        }
        catch (Exception e)
        {
            Log.e(TAG, e.toString());
            return null;
        }
    }

}
