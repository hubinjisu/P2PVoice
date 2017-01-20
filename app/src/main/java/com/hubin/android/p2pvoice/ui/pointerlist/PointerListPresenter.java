package com.hubin.android.p2pvoice.ui.pointerlist;

import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.hubin.android.p2pvoice.api.audio.impl.P2pVoiceServiceImpl;
import com.hubin.android.p2pvoice.api.audio.impl.UDPReceivedThread;
import com.hubin.android.p2pvoice.api.audio.impl.UDPSendThread;
import com.hubin.android.p2pvoice.api.audio.itf.IP2pVoiceService;
import com.hubin.android.p2pvoice.api.db.impl.DbService;
import com.hubin.android.p2pvoice.bean.Pointer;
import com.hubin.android.p2pvoice.bean.PointerListItem;
import com.hubin.android.p2pvoice.utils.UiConstants;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by tester on 16-7-1.
 */
public class PointerListPresenter implements PointerListContract.IPointerListPresenter
{
    private static final String TAG = "PointerListPresenter";
    private PointerListContract.IPointerListView iPointerView;
    private IP2pVoiceService p2pVoiceService;
    private boolean isPlaying = false;
    private boolean isRecording = false;

    private UDPSendThread udpSendThread;
    private UDPReceivedThread udpReceivedThread;
    private DatagramSocket datagramSocket = null;
    private DbService dbService;

    private File sendAudioFile, recAudioFile;

    public PointerListPresenter(PointerListContract.IPointerListView iPointerView)
    {
        this.iPointerView = iPointerView;
        this.p2pVoiceService = new P2pVoiceServiceImpl();
        this.dbService = DbService.getInstance();
        Observable.create(new Observable.OnSubscribe<Object>()
        {
            @Override
            public void call(Subscriber<? super Object> subscriber)
            {
                //保存音频文件
                initAudioSaveFile();
                //初始化Socket
                initSocket();
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    private void initAudioSaveFile()
    {
        Log.i(TAG, "initAudioSaveFile: ");
        File saveFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/T30AudioRecord");
        try
        {
//            saveFile.delete();
            if (!saveFile.exists())
            {
                Log.i(TAG, "initAudioSaveFile: mkdirs");
                saveFile.mkdirs();
            }
            sendAudioFile = new File(saveFile, UiConstants.FILE_NAME_SEND_AUDIO + ".pcm");
            recAudioFile = new File(saveFile, UiConstants.FILE_NAME_RECEIVE_AUDIO + ".pcm");
//            sendAudioFile = File.createTempFile(UiConstants.FILE_NAME_SEND_AUDIO, ".pcm", saveFile);
//            recAudioFile = File.createTempFile(UiConstants.FILE_NAME_RECEIVE_AUDIO, ".pcm", saveFile);
        }
        catch (Exception e)
        {
            Log.e(TAG, "initAudioSaveFile:" + e.toString());
        }

        Log.i(TAG, "initAudioSaveFile: create files");
    }

    private void initSocket()
    {
        Log.i(TAG, "initSocket::");
        if (datagramSocket != null)
        {
            try
            {
                datagramSocket.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        try
        {
            datagramSocket = new DatagramSocket(UiConstants.DEFAULT_AUDIO_PORT);
        }
        catch (Exception e)
        {
            Log.e(TAG, e.toString());
        }
    }

    public ArrayList<PointerListItem> getPointerList()
    {
        ArrayList<PointerListItem> pointerList = new ArrayList<PointerListItem>();
        List<Pointer> pointers = dbService.getAllPointers();
        PointerListItem pointerItem;
//        pointerItem.getPointer().setIp(UiConstants.DEFAULT_REMOTE_POINTER_IP);
//        pointerItem.getPointer().setPort(UiConstants.DEFAULT_AUDIO_PORT);
//        pointerList.add(pointerItem);
        for (Pointer pointer : pointers)
        {
            pointerItem = new PointerListItem();
            pointerItem.setPointer(pointer);
            pointerList.add(pointerItem);
        }
        return pointerList;
    }

    public void startPlaySavedRecievedAudio()
    {
        Log.d(TAG, "startPlaySavedRecievedAudio: ");
        PlayTask playTask = new PlayTask(true);
        playTask.execute();
        isPlaying = true;
    }

    public void stopPlaySavedRecievedAudio()
    {
        Log.d(TAG, "stopPlaySavedRecievedAudio: ");
        isPlaying = false;
    }

    public void startPlaySavedSentAudio()
    {
        Log.d(TAG, "startPlaySavedSentAudio: ");
        PlayTask playTask1 = new PlayTask(false);
        playTask1.execute();
        isPlaying = true;
    }

    public void stopPlaySavedSentAudio()
    {
        Log.d(TAG, "stopPlaySavedSentAudio: ");
        isPlaying = false;
    }

    public void startSendAudioData()
    {
        if (udpSendThread != null)
        {
            udpSendThread.setSend(false);
        }
        udpSendThread = new UDPSendThread(datagramSocket, sendAudioFile, iPointerView.getRemotePointerPort(), iPointerView.getAudioSampleRate());
        udpSendThread.setReceivedIP(iPointerView.getRemotePointerIp());
        udpSendThread.setSend(true);
        udpSendThread.setSaveSend(iPointerView.isSaveSentAudio());
        udpSendThread.setSendDataSize(iPointerView.getAudioBufferSize());
        udpSendThread.startSend();
    }

    public void stopSendAudioData()
    {
        if (udpSendThread != null)
        {
            udpSendThread.stopSend();
        }
    }

    public void startReceiveAudioData()
    {
        if (udpReceivedThread != null)
        {
            udpReceivedThread.setReceived(false);
        }
        udpReceivedThread = new UDPReceivedThread(datagramSocket, recAudioFile, iPointerView.getRemotePointerPort(), iPointerView.getAudioSampleRate());
        udpReceivedThread.setReceived(true);
        udpReceivedThread.setSaveReceived(iPointerView.isSaveReceivedAudio());
        udpReceivedThread.setReceiveDataSize(iPointerView.getAudioBufferSize());
        udpReceivedThread.startReceived();
    }

    public void stopReceiveAudioData()
    {
        if (udpReceivedThread != null)
        {
            udpReceivedThread.stopReceivedData();
        }
    }

    public void stopSocket()
    {
        if (datagramSocket != null)
        {
            datagramSocket.close();
            datagramSocket = null;
        }
    }

    @Override
    public void start()
    {

    }

    @Override
    public void release()
    {
        stopPlaySavedRecievedAudio();
        stopPlaySavedSentAudio();
        stopReceiveAudioData();
        stopSendAudioData();
        stopSocket();
    }

    /**
     * 说明：录制类
     *
     * @author chaimb
     * @Date 2016-4-14
     */
    class RecordTask extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            Log.d(TAG, "RecordTask: ");
            isRecording = true;
            try
            {
                // 开通输出流到指定的文件
                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(sendAudioFile)));
                // 根据定义好的几个配置，来获取合适的缓冲大小
                int bufferSize = AudioRecord.getMinBufferSize(iPointerView.getAudioSampleRate(), UiConstants.AUDIO_CHANNEL, UiConstants.AUDIO_FORMAT);
                // 实例化AudioRecord
                AudioRecord record = new AudioRecord(MediaRecorder.AudioSource.MIC, iPointerView.getAudioSampleRate(), UiConstants.AUDIO_CHANNEL, UiConstants
                        .AUDIO_FORMAT, bufferSize);
                // 定义缓冲
                short[] buffer = new short[bufferSize];

                // 开始录制
                record.startRecording();

                int r = 0; // 存储录制进度
                // 定义循环，根据isRecording的值来判断是否继续录制
                while (isRecording)
                {
                    // 从bufferSize中读取字节，返回读取的short个数
                    // 这里老是出现buffer overflow，不知道是什么原因，试了好几个值，都没用，TODO：待解决
                    int bufferReadResult = record.read(buffer, 0, buffer.length);
                    // 循环将buffer中的音频数据写入到OutputStream中
                    for (int i = 0; i < bufferReadResult; i++)
                    {
                        dos.writeShort(buffer[i]);
                    }
                    publishProgress(new Integer(r)); // 向UI线程报告当前进度
                    r++; // 自增进度值
                }
                // 录制结束
                record.stop();
                dos.close();
            }
            catch (Exception e)
            {
                // TODO: handle exception
            }
            return null;
        }

    }


    /**
     * 说明：录制类
     *
     * @author chaimb
     * @Date 2016-4-14
     */
    class PlayTask extends AsyncTask<Void, Integer, Void>
    {
        File audioFile = null;

        public PlayTask(boolean isSend)
        {
            if (isSend)
            {
                audioFile = sendAudioFile;
            }
            else
            {
                audioFile = recAudioFile;
            }
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            if (audioFile != null && audioFile.exists())
            {
                Log.d(TAG, "PlayTask: ");
                int bufferSize = AudioTrack.getMinBufferSize(iPointerView.getAudioSampleRate(), UiConstants.AUDIO_CHANNEL, UiConstants.AUDIO_FORMAT);
                short[] buffer = new short[bufferSize / 4];
                try
                {
                    // 定义输入流，将音频写入到AudioTrack类中，实现播放
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(audioFile)));
                    // 实例AudioTrack
                    AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC, iPointerView.getAudioSampleRate(), UiConstants.AUDIO_CHANNEL, UiConstants
                            .AUDIO_FORMAT, bufferSize, AudioTrack.MODE_STREAM);
                    // 开始播放
                    track.play();
                    // 由于AudioTrack播放的是流，所以，我们需要一边播放一边读取
                    while (isPlaying && dis.available() > 0)
                    {
                        int i = 0;
                        while (dis.available() > 0 && i < buffer.length)
                        {
                            buffer[i] = dis.readShort();
                            i++;
                        }
                        // 然后将数据写入到AudioTrack中
                        track.write(buffer, 0, buffer.length);
                    }

                    // 播放结束
                    track.stop();
                    dis.close();
                }
                catch (Exception e)
                {
                    Log.e(TAG, e.toString());
                }
            }
            return null;
        }
    }

}
