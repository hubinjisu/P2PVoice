package com.hubin.android.p2pvoice.mvp.view;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.hubin.android.p2pvoice.R;
import com.hubin.android.p2pvoice.UDPReceivedThread;
import com.hubin.android.p2pvoice.UDPSendThread;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;

public class PointerActivity extends AppCompatActivity implements IPointerView, View.OnClickListener{
    private static final String TAG = "PointerActivity";
    private Button startSendButton, stopSendButton, startReceButton, stopReceButton, stopUDPButton;
    private Button playButton, finishButton, playRecButton, finishRecButton;
    public static final int PORT = 9005;

    public static int frequence = 16000; // 采样频率
    public static final int channelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    public static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
    private RecordTask recordTask = null;

    private File sendAudioFile, recAudioFile;
    private String sendAudioFileName = "sendrecording";
    private String recAudioFileName = "recrecording";
    private UDPSendThread udpSendThread;
    private UDPReceivedThread udpReceivedThread;
    private String ip;
    private DatagramSocket datagramSocket = null;
    private boolean isPlaying = false;
    private boolean isRecording = false;
    // 本地录音文件是否保存
    private boolean isSaveSend;
    // 就收录音文件是否保存
    private boolean isSaveRec;

    private int dataSize = 160;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pointer);
        //初始化View
        initView();
        //设置监听器
        setListener();
        //保存音频文件
        setAudioSaveFile();
        //初始化Socket
        initSocket();
    }

    /**
     * 初始化View
     */
    private void initView() {
        playButton = (Button) findViewById(R.id.play_button);
        finishButton = (Button) findViewById(R.id.finish_button);
        playRecButton = (Button) findViewById(R.id.play_rec_button);
        finishRecButton = (Button) findViewById(R.id.finish_rec_button);
        startSendButton = (Button) findViewById(R.id.start_send_button);
        stopSendButton = (Button) findViewById(R.id.stop_send_button);
        startReceButton = (Button) findViewById(R.id.start_rece_button);
        stopReceButton = (Button) findViewById(R.id.stop_rece_button);
        stopUDPButton = (Button) findViewById(R.id.stop_udp_button);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Title");//设置主标题
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));//设置主标题颜色

        toolbar.inflateMenu(R.menu.tool_bar_menu);//设置右上角的填充菜单
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.action_item1) {
                    startActivity(new Intent(PointerActivity.this, SettingActivity.class));

                }
                return true;
            }
        });
    }

    @Override
    public String getPointerIp() {
        return "";
    }


    private void initSocket() {
        Log.i(TAG, "initSocket::");
        if (datagramSocket != null) {
            try {
                datagramSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            datagramSocket = new DatagramSocket(PORT);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private void setAudioSaveFile() {
        File saveFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/T30AudioRecord/");
        saveFile.delete();
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        try {
            sendAudioFile = File.createTempFile(sendAudioFileName, ".pcm", saveFile);
            recAudioFile = File.createTempFile(recAudioFileName, ".pcm", saveFile);
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }

    private void setListener() {
        playButton.setOnClickListener(this);
        finishButton.setOnClickListener(this);
        playRecButton.setOnClickListener(this);
        finishRecButton.setOnClickListener(this);

        startSendButton.setOnClickListener(this);
        stopSendButton.setOnClickListener(this);
        startReceButton.setOnClickListener(this);
        stopReceButton.setOnClickListener(this);
        stopUDPButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.start_button:
//                // 开始录制
//                Log.i(TAG, "start_button::");
//                recordTask = new RecordTask();
//                recordTask.execute();
//                startButton.setEnabled(false);
//                stopButton.setEnabled(true);
//                break;
//            case R.id.stop_button:
//                // 结束录制
//                Log.i(TAG, "stop_button::");
//                isRecording = false;
//                startButton.setEnabled(true);
//                stopButton.setEnabled(false);
//                break;
            case R.id.play_button:
                // 开始播放
                Log.i(TAG, "play_button::");
                PlayTask playTask = new PlayTask(true);
                playTask.execute();
                isPlaying = true;
                finishButton.setEnabled(true);
                playButton.setEnabled(false);
                break;
            case R.id.finish_button:
                // 停止播放
                Log.i(TAG, "finish_button::");
                isPlaying = false;
                playButton.setEnabled(true);
                finishButton.setEnabled(false);
                break;
            case R.id.play_rec_button:
                // 开始播放rece
                Log.i(TAG, "play_button::");
                PlayTask playTask1 = new PlayTask(false);
                playTask1.execute();
                isPlaying = true;
                finishRecButton.setEnabled(true);
                playRecButton.setEnabled(false);
                break;
            case R.id.finish_rec_button:
                // 停止播放rece
                Log.i(TAG, "finish_button::");
                isPlaying = false;
                playRecButton.setEnabled(true);
                finishRecButton.setEnabled(false);
                break;
            case R.id.start_send_button:
                // 开始发送数据
                Log.i(TAG, "start_send_button::");

                if (udpSendThread != null) {
                    udpSendThread.setSend(false);
                }
                udpSendThread = new UDPSendThread(datagramSocket, sendAudioFile);
                udpSendThread.setReceivedIP(ip);
                udpSendThread.setSend(true);
                udpSendThread.setSaveSend(isSaveSend);
                udpSendThread.setSendDataSize(dataSize);
                udpSendThread.startSend();

                stopSendButton.setEnabled(true);
                startSendButton.setEnabled(false);
                break;
            case R.id.stop_send_button:
                // 结束发送数据
                Log.i(TAG, "stop_send_button::");
                if (udpSendThread != null) {
                    udpSendThread.setSend(false);
//                    udpSendThread.stopSend();
                }

                stopSendButton.setEnabled(false);
                startSendButton.setEnabled(true);

                break;
            case R.id.start_rece_button:
                // 开始接收
                Log.i(TAG, "start_rece_button::");
                if (udpReceivedThread != null) {
                    udpReceivedThread.setReceived(false);
                }
                udpReceivedThread = new UDPReceivedThread(datagramSocket, recAudioFile);
                udpReceivedThread.setReceived(true);
                udpReceivedThread.setSaveReceived(isSaveRec);
                udpReceivedThread.setReceiveDataSize(dataSize);
                udpReceivedThread.connectSocket();

                stopReceButton.setEnabled(true);
                startReceButton.setEnabled(false);
                break;
            case R.id.stop_rece_button:
                // 结束接收
                Log.i(TAG, "stop_rece_button::");
                if (udpReceivedThread != null) {
                    udpReceivedThread.setReceived(false);
//                    udpReceivedThread.stopReceivedData();
                }

                stopReceButton.setEnabled(false);
                startReceButton.setEnabled(true);
                break;

            case R.id.stop_udp_button:
                Log.i(TAG, "stop_udp_button");
                if (datagramSocket != null) {
                    datagramSocket.close();
                    datagramSocket = null;
                }
                stopUDPButton.setEnabled(false);
                stopUDPButton.setText("Socket已停止！");
                break;

            default:
                break;
        }
    }

    /**
     * 说明：录制类
     *
     * @author chaimb
     * @Date 2016-4-14
     */
    class RecordTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            isRecording = true;
            try {
                // 开通输出流到指定的文件
                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(sendAudioFile)));
                // 根据定义好的几个配置，来获取合适的缓冲大小
                int bufferSize = AudioRecord.getMinBufferSize(frequence, channelConfig, audioEncoding);
                // 实例化AudioRecord
                AudioRecord record = new AudioRecord(MediaRecorder.AudioSource.MIC, frequence, channelConfig, audioEncoding, bufferSize);
                // 定义缓冲
                short[] buffer = new short[bufferSize];

                // 开始录制
                record.startRecording();

                int r = 0; // 存储录制进度
                // 定义循环，根据isRecording的值来判断是否继续录制
                while (isRecording) {
                    // 从bufferSize中读取字节，返回读取的short个数
                    // 这里老是出现buffer overflow，不知道是什么原因，试了好几个值，都没用，TODO：待解决
                    int bufferReadResult = record.read(buffer, 0, buffer.length);
                    // 循环将buffer中的音频数据写入到OutputStream中
                    for (int i = 0; i < bufferReadResult; i++) {
                        dos.writeShort(buffer[i]);
                    }
                    publishProgress(new Integer(r)); // 向UI线程报告当前进度
                    r++; // 自增进度值
                }
                // 录制结束
                record.stop();
                dos.close();
            } catch (Exception e) {
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
    class PlayTask extends AsyncTask<Void, Integer, Void> {
        File audioFile = null;

        public PlayTask(boolean isSend) {
            if (isSend) {
                audioFile = sendAudioFile;
            } else {
                audioFile = recAudioFile;
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (audioFile.exists()) {
                int bufferSize = AudioTrack.getMinBufferSize(frequence, channelConfig, audioEncoding);
                short[] buffer = new short[bufferSize / 4];
                try {
                    // 定义输入流，将音频写入到AudioTrack类中，实现播放
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(audioFile)));
                    // 实例AudioTrack
                    AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC, frequence, channelConfig, audioEncoding, bufferSize, AudioTrack.MODE_STREAM);
                    // 开始播放
                    track.play();
                    // 由于AudioTrack播放的是流，所以，我们需要一边播放一边读取
                    while (isPlaying && dis.available() > 0) {
                        int i = 0;
                        while (dis.available() > 0 && i < buffer.length) {
                            buffer[i] = dis.readShort();
                            i++;
                        }
                        // 然后将数据写入到AudioTrack中
                        track.write(buffer, 0, buffer.length);
                    }

                    // 播放结束
                    track.stop();
                    dis.close();
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
            return null;
        }
    }
}
