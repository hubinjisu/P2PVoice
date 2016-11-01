package com.hubin.android.p2pvoice.api.audio.impl;

import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;


import com.hubin.android.p2pvoice.utils.BytesTransUtil;
import com.hubin.android.p2pvoice.utils.UiConstants;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 说明：
 *
 * @author chaimb
 * @Date 2016-4-14
 */
public class UDPReceivedThread implements Runnable
{
    private static final String TAG = UDPReceivedThread.class.getName();
    // 接收的线程
    private Thread receivedThread;
    // 接收数据Socket
    private DatagramSocket receivedUdp = null;
    // 接收方的Packet
    private DatagramPacket receivedPacket = null;

    // 接收的数据
    private byte receivedData[] = null;
    private boolean isReceived = true;
    private boolean isSaveReceived = false;
    private int bufferSize = -1;
    private File recAudioFile;
    private int receiveDataSize;
    private int sampleRate;
    private int remotePort;

    public UDPReceivedThread(DatagramSocket datagramSocket, File recAudioFile, int remotePort, int sampleRate)
    {
        this.receivedUdp = datagramSocket;
        this.recAudioFile = recAudioFile;
        this.sampleRate = sampleRate;
        this.remotePort = remotePort;
    }

    public void setReceiveDataSize(int receiveDataSize)
    {
        this.receiveDataSize = receiveDataSize;
    }

    /**
     * 方法说明 :开始接收
     *
     * @author chaimb
     * @Date 2016-4-14
     */
    public void startReceived()
    {
        if (receivedThread == null)
        {
            receivedThread = new Thread(this);
            receivedThread.start();
        }
    }

    @Override
    public void run()
    {
        // 接收线程
        Log.i(TAG, "Thread::receivedData::");
        receivedData();
    }

    private void receivedData()
    {
        Log.i(TAG, "receivedData::");
        try
        {
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(recAudioFile)));
            bufferSize = AudioTrack.getMinBufferSize(sampleRate, UiConstants.AUDIO_CHANNEL, UiConstants.AUDIO_FORMAT);
            // 实例AudioTrack
            AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate, UiConstants.AUDIO_CHANNEL, UiConstants.AUDIO_FORMAT,
                    bufferSize, AudioTrack.MODE_STREAM);
            short[] bufferR = new short[receiveDataSize];
            receivedData = new byte[receiveDataSize * 2];
            receivedPacket = new DatagramPacket(receivedData, receivedData.length);
            // 开始播放
            track.play();
            while (isReceived)
            {
                receivedPacket.setData(receivedData);
                receivedUdp.receive(receivedPacket);
                receivedData = receivedPacket.getData();
//                Log.e(TAG, "r:" + receivedData.length);
//                Log.e(TAG, "r:" + Arrays.toString(buffer));

                bufferR = BytesTransUtil.getInstance().Bytes2Shorts(receivedPacket.getData(), bufferR);
                track.write(bufferR, 0, bufferR.length);
                if (isSaveReceived)
                {
                    for (int i = 0; i < bufferR.length; i++)
                    {
                        dos.writeShort(bufferR[i]);
                    }
                }
            }
            // 播放结束
            track.stop();
            dos.close();
        }
        catch (Exception e)
        {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 方法说明 :停止接收数据
     *
     * @author chaimb
     * @Date 2016-4-14
     */
    public void stopReceivedData()
    {
        isReceived = false;
        if (receivedPacket != null)
        {
            receivedPacket = null;
        }
    }

    public boolean isReceived()
    {
        return isReceived;
    }

    public void setReceived(boolean isReceived)
    {
        this.isReceived = isReceived;
    }

    public boolean isSaveReceived()
    {
        return isSaveReceived;
    }

    public void setSaveReceived(boolean isSaveReceived)
    {
        this.isSaveReceived = isSaveReceived;
    }
}
