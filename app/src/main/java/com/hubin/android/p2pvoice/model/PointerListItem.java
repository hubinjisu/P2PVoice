package com.hubin.android.p2pvoice.model;

import com.hubin.android.p2pvoice.utils.UiConstants;

import java.util.ArrayList;

/**
 * 说明：呼叫列表单元
 *
 * @author hubin
 * @Date 2015-4-2
 */
public class PointerListItem extends BaseListItem
{
    private static final long serialVersionUID = 1L;

    // 对端号码通讯录中的组层级
    private ArrayList<String> groups;
    // 对端号码通讯录中的组层级
    private String relativeVideoNumber;
    private int status = UiConstants.POINTER_SESSION_STATUS_CLOSED;
    private String mediaType;
    // 是否入会操作
    private boolean isIntoConf;

//    private int type;
    // 单呼转会议
//    private boolean isTransferToConf = false;
    private boolean isReceiving;
    private boolean isSending;
    // 用于会议背景音控制
    private boolean isConfBgmEnabled;
    // 当作为会议成员时，被主席方语音控制的状态
    private boolean isAudioClosed;
    // 当作为会议成员时，被主席方语音控制的状态
    private boolean isPlayingSend;
    // 当作为会议成员时，被主席方语音控制的状态
    private boolean isPlayingReceive;
    private boolean isDropDown;
    private boolean isOutSideAudio;
    private boolean isActionShow;
    private boolean isActionHide;
    // 闭铃开关标识
    private boolean isCloseRing;
    private long startTime;
    private long endTime;
    private String ip;
    private int port;
    private boolean isHolded;

    public String getRelativeVideoNumber()
    {
        return relativeVideoNumber;
    }

    public void setRelativeVideoNumber(String relativeVideoNumber)
    {
        this.relativeVideoNumber = relativeVideoNumber;
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

    public ArrayList<String> getGroups()
    {
        return groups;
    }

    public void setGroups(ArrayList<String> groups)
    {
        this.groups = groups;
    }

    public boolean isIntoConf()
    {
        return isIntoConf;
    }

    public void setIntoConf(boolean isIntoConf)
    {
        this.isIntoConf = isIntoConf;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public boolean isAudioClosed()
    {
        return isAudioClosed;
    }

    public void setAudioClosed(boolean isAudioEnabled)
    {
        this.isAudioClosed = isAudioEnabled;
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

    public boolean isConfBgmEnabled()
    {
        return isConfBgmEnabled;
    }

    public void setConfBgmEnabled(boolean isConfBgmEnabled)
    {
        this.isConfBgmEnabled = isConfBgmEnabled;
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

    public boolean isOutSideAudio()
    {
        return isOutSideAudio;
    }

    public void setIsOutSideAudio(boolean isOutSideAudio)
    {
        this.isOutSideAudio = isOutSideAudio;
    }

    public boolean isCloseRing()
    {
        return isCloseRing;
    }

    public void setCloseRing(boolean isCloseRing)
    {
        this.isCloseRing = isCloseRing;
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

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public boolean isHolded()
    {
        return isHolded;
    }

    public void setHolded(boolean holded)
    {
        isHolded = holded;
    }
}
