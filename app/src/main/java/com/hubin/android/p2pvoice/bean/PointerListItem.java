package com.hubin.android.p2pvoice.bean;

import com.hubin.android.p2pvoice.utils.UiConstants;

/**
 * 说明：呼叫列表单元
 *
 * @author hubin
 * @Date 2015-4-2
 */
public class PointerListItem extends BaseListItem
{
    private static final long serialVersionUID = 1L;

    private int status = UiConstants.POINTER_SESSION_STATUS_CLOSED;
    private String mediaType;

    private boolean isDropDown;
    //    private int type;
    // 单呼转会议
//    private boolean isTransferToConf = false;
    private boolean isReceiving;
    private boolean isSending;
    private boolean isPlayingSend;
    private boolean isPlayingReceive;
    private boolean isActionShow;
    private boolean isActionHide;
    private long startTime;
    private long endTime;
    private PointerBean pointer;

    public PointerListItem()
    {
        pointer = new PointerBean();
    }

    public boolean isActionShow()
    {
        return isActionShow;
    }

    public void setIsActionShow(boolean isActionShow)
    {
        this.isActionShow = isActionShow;
    }

    public boolean isActionHide()
    {
        return isActionHide;
    }

    public void setIsActionHide(boolean isActionHide)
    {
        this.isActionHide = isActionHide;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public boolean isPlayingSend()
    {
        return isPlayingSend;
    }

    public void setPlayingSend(boolean isPlayingSend)
    {
        this.isPlayingSend = isPlayingSend;
    }

    public boolean isPlayingReceive()
    {
        return isPlayingReceive;
    }

    public void setPlayingReceive(boolean isPlayingReceive)
    {
        this.isPlayingReceive = isPlayingReceive;
    }

    public boolean isReceiving()
    {
        return isReceiving;
    }

    public void setReceiving(boolean isReceiving)
    {
        this.isReceiving = isReceiving;
    }

    public boolean isSending()
    {
        return isSending;
    }

    public void setSending(boolean isSending)
    {
        this.isSending = isSending;
    }

    public String getMediaType()
    {
        return mediaType;
    }

    public void setMediaType(String mediaType)
    {
        this.mediaType = mediaType;
    }

    public boolean isDropDown()
    {
        return isDropDown;
    }

    public void setIsDropDown(boolean isDropDown)
    {
        this.isDropDown = isDropDown;
    }

    public long getStartTime()
    {
        return startTime;
    }

    public void setStartTime(long startTime)
    {
        this.startTime = startTime;
    }

    public long getEndTime()
    {
        return endTime;
    }

    public void setEndTime(long endTime)
    {
        this.endTime = endTime;
    }

    public PointerBean getPointer()
    {
        return pointer;
    }

    public void setPointer(PointerBean pointer)
    {
        this.pointer = pointer;
    }
}
