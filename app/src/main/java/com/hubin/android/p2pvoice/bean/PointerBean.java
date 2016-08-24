package com.hubin.android.p2pvoice.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.hubin.android.p2pvoice.utils.UiConstants;

/**
 * Description:
 * <p/>
 * Author: hubin
 * Date: 2016/8/22.
 */
public class PointerBean implements Parcelable
{
    private String ip;
    private int port = UiConstants.DEFAULT_AUDIO_PORT;
    private int audioSampleRate;
    private boolean isRecordSend;
    private boolean isRecordReceive;

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

    public int getAudioSampleRate()
    {
        return audioSampleRate;
    }

    public void setAudioSampleRate(int audioSampleRate)
    {
        this.audioSampleRate = audioSampleRate;
    }

    public boolean isRecordSend()
    {
        return isRecordSend;
    }

    public void setRecordSend(boolean recordSend)
    {
        isRecordSend = recordSend;
    }

    public boolean isRecordReceive()
    {
        return isRecordReceive;
    }

    public void setRecordReceive(boolean recordReceive)
    {
        isRecordReceive = recordReceive;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.ip);
        dest.writeInt(this.port);
        dest.writeInt(this.audioSampleRate);
        dest.writeByte(this.isRecordSend ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isRecordReceive ? (byte) 1 : (byte) 0);
    }

    public PointerBean()
    {
    }

    protected PointerBean(Parcel in)
    {
        this.ip = in.readString();
        this.port = in.readInt();
        this.audioSampleRate = in.readInt();
        this.isRecordSend = in.readByte() != 0;
        this.isRecordReceive = in.readByte() != 0;
    }

    public static final Parcelable.Creator<PointerBean> CREATOR = new Parcelable.Creator<PointerBean>()
    {
        @Override
        public PointerBean createFromParcel(Parcel source)
        {
            return new PointerBean(source);
        }

        @Override
        public PointerBean[] newArray(int size)
        {
            return new PointerBean[size];
        }
    };
}
