package com.hubin.android.p2pvoice.api.impl;

import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;


import com.hubin.android.p2pvoice.utils.BytesTransUtil;
import com.hubin.android.p2pvoice.utils.UiConstants;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 说明：
 *
 * @author  chaimb
 *
 * @Date 2016-4-14
 */
public class UDPSendThread implements Runnable
{
    private static final String TAG = UDPSendThread.class.getName();

    // 发送的线程
    private Thread sendThread;
    // 发送数据Socket
    private DatagramSocket sendUdp = null;

    // 发送方的Packet
    private DatagramPacket sendPacket = null;

    private String receivedIP = "";

    // 发送的数据
    private byte sendData[] = null;
    private boolean isSend = true;
    private boolean isSaveSend = true;

    private int bufferSize = -1;

    private int sendDataSize = -1;
    private File audioFile;
    private int sampleRate;
    private int remotePort;

    public UDPSendThread(DatagramSocket datagramSocket, File audioFile, int remotePort, int sampleRate)
    {
        this.sendUdp = datagramSocket;
        this.audioFile = audioFile;
        this.sampleRate = sampleRate;
        this.remotePort = remotePort;
        // 根据定义好的几个配置，来获取合适的缓冲大小
        bufferSize = AudioRecord.getMinBufferSize(sampleRate, UiConstants.AUDIO_CHANNEL, UiConstants.AUDIO_FORMAT);
        sendData = new byte[bufferSize];
    }


    public void setSendDataSize(int dataSize)
    {
        this.sendDataSize = dataSize;
    }
    /**
     * 方法说明 :开始发送
     * @author chaimb
     * @Date 2016-4-14
     */
    public void startSend()
    {
        if (sendThread == null)
        {
            sendThread = new Thread(this);
            sendThread.start();
        }
    }

    /**
     * 方法说明 :停止发送
     * @author chaimb
     * @Date 2016-4-14
     */
    public void stopSend()
    {
        if (sendPacket != null)
        {
            sendPacket = null;
        }
    }

    @Override
    public void run()
    {
        if (Thread.currentThread() == sendThread)
        {// 发送线程
            sendData();
        }
    }

    /**
     * 方法说明 :
     * @author chaimb
     * @Date 2016-4-17
     */
    public void sendData()
    {
        Log.i(TAG, "sendData::");
        try
        {
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(audioFile)));
            // 实例化AudioRecord
            AudioRecord record = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, UiConstants.AUDIO_CHANNEL, UiConstants.AUDIO_FORMAT,
                    bufferSize);
            // 定义缓冲
            byte[] bufferSend = new byte[sendDataSize * 2];
            InetAddress receivedAddress = InetAddress.getByName(receivedIP);
            sendPacket = new DatagramPacket(bufferSend, bufferSend.length, receivedAddress, remotePort);
            short[] buffer = new short[sendDataSize];
            // 开始录制
            record.startRecording();
            // 定义循环，根据isRecording的值来判断是否继续录制
            while (isSend)
            {
                // 从bufferSize中读取字节，返回读取的short个数
                record.read(buffer, 0, buffer.length);
//                Log.e(TAG, "s:" + buffer.length);
//                Log.e(TAG, "s:" + Arrays.toString(buffer));

                // 循环将buffer中的音频数据写入到OutputStream中
                if (isSaveSend)
                {
                    for (int i = 0; i < buffer.length; i++)
                    {
                        dos.writeShort(buffer[i]);
                    }
                }

                bufferSend = BytesTransUtil.getInstance().Shorts2Bytes(buffer, bufferSend);
                sendPacket.setData(bufferSend);
                sendUdp.send(sendPacket);
            }
            // 录制结束
            record.stop();
            dos.close();
        }
        catch (Exception e)
        {
            Log.e(TAG, e.toString());
        }
    }

    public String getReceivedIP()
    {
        return receivedIP;
    }

    public void setReceivedIP(String receivedIP)
    {
        this.receivedIP = receivedIP;
    }

    public boolean isSend()
    {
        return isSend;
    }

    public void setSend(boolean isSend)
    {
        this.isSend = isSend;
    }

    public byte[] getSendData()
    {
        return sendData;
    }

    public void setSendData(byte sendData[])
    {
        this.sendData = sendData;
    }

    public boolean isSaveSend()
    {
        return isSaveSend;
    }

    public void setSaveSend(boolean isSaveSend)
    {
        this.isSaveSend = isSaveSend;
    }

}
