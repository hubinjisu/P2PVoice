package com.hubin.android.p2pvoice.bean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Entity mapped to table POINTER.
 */
@Entity(nameInDb = "Pointer")
public class Pointer implements Parcelable
{
    @Id(autoincrement = true)@Unique
    private Long id;
    private String name;
    @Property(nameInDb = "createDate")
    private long createDate;
    /** Not-null value. */
    @Property(nameInDb = "ip")@NotNull
    private String ip;
    @Property(nameInDb = "port")
    private int port;
    @Property(nameInDb = "audioSampleRate")
    private int audioSampleRate;
    @Property(nameInDb = "isRecordSend")
    private boolean isRecordSend;
    @Property(nameInDb = "isRecordReceive")
    private boolean isRecordReceive;
    @Generated(hash = 2138903251)
    public Pointer(Long id, String name, long createDate, @NotNull String ip, int port,
            int audioSampleRate, boolean isRecordSend, boolean isRecordReceive) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.ip = ip;
        this.port = port;
        this.audioSampleRate = audioSampleRate;
        this.isRecordSend = isRecordSend;
        this.isRecordReceive = isRecordReceive;
    }
    @Generated(hash = 1295672034)
    public Pointer() {
    }

    protected Pointer(Parcel in)
    {
        name = in.readString();
        createDate = in.readLong();
        ip = in.readString();
        port = in.readInt();
        audioSampleRate = in.readInt();
        isRecordSend = in.readByte() != 0;
        isRecordReceive = in.readByte() != 0;
    }

    public static final Creator<Pointer> CREATOR = new Creator<Pointer>()
    {
        @Override
        public Pointer createFromParcel(Parcel in)
        {
            return new Pointer(in);
        }

        @Override
        public Pointer[] newArray(int size)
        {
            return new Pointer[size];
        }
    };

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getCreateDate() {
        return this.createDate;
    }
    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
    public String getIp() {
        return this.ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public int getPort() {
        return this.port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public int getAudioSampleRate() {
        return this.audioSampleRate;
    }
    public void setAudioSampleRate(int audioSampleRate) {
        this.audioSampleRate = audioSampleRate;
    }
    public boolean getIsRecordSend() {
        return this.isRecordSend;
    }
    public void setIsRecordSend(boolean isRecordSend) {
        this.isRecordSend = isRecordSend;
    }
    public boolean getIsRecordReceive() {
        return this.isRecordReceive;
    }
    public void setIsRecordReceive(boolean isRecordReceive) {
        this.isRecordReceive = isRecordReceive;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(name);
        dest.writeLong(createDate);
        dest.writeString(ip);
        dest.writeInt(port);
        dest.writeInt(audioSampleRate);
        dest.writeByte((byte) (isRecordSend ? 1 : 0));
        dest.writeByte((byte) (isRecordReceive ? 1 : 0));
    }
}
