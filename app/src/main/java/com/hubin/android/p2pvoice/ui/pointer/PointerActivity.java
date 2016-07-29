package com.hubin.android.p2pvoice.ui.pointer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.hubin.android.p2pvoice.R;
import com.hubin.android.p2pvoice.ui.setting.SettingActivity;
import com.hubin.android.p2pvoice.utils.UiConstants;

public class PointerActivity extends AppCompatActivity implements PointerContract.IPointerView, View.OnClickListener{
    private static final String TAG = "PointerActivity";
    private Button startSendButton, stopSendButton, startReceButton, stopReceButton, stopUDPButton;
    private Button playButton, finishButton, playRecButton, finishRecButton;

    private SharedPreferences preferences;
    private PointerPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pointer);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        presenter = new PointerPresenter(this);
        //初始化View
        initView();
        //设置监听器
        setListener();
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
    public String getRemotePointerIp() {
        return preferences.getString(UiConstants.REMOTE_POINTER_IP, "");
    }

    @Override
    public int getRemotePointerPort() {
        return preferences.getInt(UiConstants.REMOTE_POINTER_PORT, UiConstants.DEFAULT_AUDIO_PORT);
    }

    @Override
    public int getAudioBufferSize() {
        return preferences.getInt(UiConstants.AUDIO_BUFFER_SIZE, UiConstants.DEFAULT_AUDIO_BUFFER_SIZE);
    }

    @Override
    public int getAudioSampleRate() {
        return preferences.getInt(UiConstants.AUDIO_SAMPLE_RATE, UiConstants.DEFAULT_AUDIO_SAMPLE_RATE);
    }

    @Override
    public boolean isSaveReceivedAudio() {
        return preferences.getBoolean(UiConstants.IS_SAVE_RECEIVED_AUDIO, false);
    }

    @Override
    public boolean isSaveSentAudio() {
        return preferences.getBoolean(UiConstants.IS_SAVE_SEND_AUDIO, false);
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
                presenter.startPlaySavedSentAudio();
                finishButton.setEnabled(true);
                playButton.setEnabled(false);
                break;
            case R.id.finish_button:
                // 停止播放
                Log.i(TAG, "finish_button::");
                presenter.stopPlaySavedSentAudio();
                playButton.setEnabled(true);
                finishButton.setEnabled(false);
                break;
            case R.id.play_rec_button:
                // 开始播放rece
                Log.i(TAG, "play_button::");
                presenter.startPlaySavedRecievedAudio();
                finishRecButton.setEnabled(true);
                playRecButton.setEnabled(false);
                break;
            case R.id.finish_rec_button:
                // 停止播放rece
                Log.i(TAG, "finish_button::");
                presenter.stopPlaySavedRecievedAudio();
                playRecButton.setEnabled(true);
                finishRecButton.setEnabled(false);
                break;
            case R.id.start_send_button:
                // 开始发送数据
                Log.i(TAG, "start_send_button::");

                presenter.startSendAudioData();

                stopSendButton.setEnabled(true);
                startSendButton.setEnabled(false);
                break;
            case R.id.stop_send_button:
                // 结束发送数据
                Log.i(TAG, "stop_send_button::");
                presenter.stopSendAudioData();
                stopSendButton.setEnabled(false);
                startSendButton.setEnabled(true);

                break;
            case R.id.start_rece_button:
                // 开始接收
                Log.i(TAG, "start_rece_button::");
                presenter.startReceiveAudioData();

                stopReceButton.setEnabled(true);
                startReceButton.setEnabled(false);
                break;
            case R.id.stop_rece_button:
                // 结束接收
                Log.i(TAG, "stop_rece_button::");
                presenter.stopReceiveAudioData();
                stopReceButton.setEnabled(false);
                startReceButton.setEnabled(true);
                break;

            case R.id.stop_udp_button:
                Log.i(TAG, "stop_udp_button");
                presenter.stopSocket();
                stopUDPButton.setEnabled(false);
                stopUDPButton.setText("Socket已停止！");
                break;

            default:
                break;
        }
    }

}
