package com.hubin.android.p2pvoice.bean;


import android.view.View;

/**
 * 说明：呼叫控制按钮
 *
 * @author HuBin
 * @Date 2015-3-5
 */
public class ControlBtnItem
{
    private String name;
    private int iconRes;
    private int bgRes;
    private boolean isSelected;
    private View.OnClickListener clickListener;

    public ControlBtnItem(String name, int iconRes, int bgRes)
    {
        this.name = name;
        this.iconRes = iconRes;
        this.bgRes = bgRes;
    }

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }

    public int getBgRes()
    {
        return bgRes;
    }

    public void setBgRes(int bgRes)
    {
        this.bgRes = bgRes;
    }

    public View.OnClickListener getClickListener()
    {
        return clickListener;
    }

    public void setClickListener(View.OnClickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getIconRes()
    {
        return iconRes;
    }

    public void setIconRes(int iconRes)
    {
        this.iconRes = iconRes;
    }

}
