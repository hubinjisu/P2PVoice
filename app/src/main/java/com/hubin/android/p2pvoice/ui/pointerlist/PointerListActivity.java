package com.hubin.android.p2pvoice.ui.pointerlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.hubin.android.p2pvoice.R;
import com.hubin.android.p2pvoice.ui.setting.SettingActivity;
import com.hubin.android.p2pvoice.utils.UiConstants;

import it.gmariotti.recyclerview.adapter.SlideInRightAnimatorAdapter;
import it.gmariotti.recyclerview.itemanimator.SlideInOutRightItemAnimator;

public class PointerListActivity extends AppCompatActivity implements PointerListContract.IPointerListView
{
    private static final String TAG = "PointerListActivity";
//    private Button startSendButton, stopSendButton, startReceButton, stopReceButton, stopUDPButton;
//    private Button playButton, finishButton, playRecButton, finishRecButton;

    private SharedPreferences preferences;
    private PointerListPresenter presenter;

    private RecyclerView callListView;
    private PointerListAdapter listAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pointer);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        presenter = new PointerListPresenter(this);
        //初始化View
        initView();
        //设置监听器
        setListener();
    }

    /**
     * 初始化View
     */
    private void initView()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.pointer_toolbar);

        toolbar.setTitle("P2P");//设置主标题
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));//设置主标题颜色

        toolbar.inflateMenu(R.menu.tool_bar_menu);//设置右上角的填充菜单
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.action_item1)
                {
                    startActivity(new Intent(PointerListActivity.this, SettingActivity.class));
                }
                return true;
            }
        });

        listAdapter = new PointerListAdapter(this, presenter);
        callListView = (RecyclerView) findViewById(R.id.pointer_list);
        layoutManager = new LinearLayoutManager(this);
        callListView.setLayoutManager(layoutManager);
        callListView.addItemDecoration(new VerticalSpaceItemDecoration(3));
        SlideInRightAnimatorAdapter slideInRightAnimatorAdapter = new SlideInRightAnimatorAdapter(listAdapter, callListView);
        callListView.setAdapter(slideInRightAnimatorAdapter);
        callListView.setItemAnimator(new SlideInOutRightItemAnimator(callListView));
        listAdapter.updateList(presenter.getPointerList());
    }

    @Override
    public String getRemotePointerIp()
    {
        return preferences.getString(UiConstants.REMOTE_POINTER_IP, UiConstants.DEFAULT_REMOTE_POINTER_IP);
    }

    @Override
    public int getRemotePointerPort()
    {
        return preferences.getInt(UiConstants.REMOTE_POINTER_PORT, UiConstants.DEFAULT_AUDIO_PORT);
    }

    @Override
    public int getAudioBufferSize()
    {
        return preferences.getInt(UiConstants.AUDIO_BUFFER_SIZE, UiConstants.DEFAULT_AUDIO_BUFFER_SIZE);
    }

    @Override
    public int getAudioSampleRate()
    {
        return preferences.getInt(UiConstants.AUDIO_SAMPLE_RATE, UiConstants.DEFAULT_AUDIO_SAMPLE_RATE);
    }

    @Override
    public boolean isSaveReceivedAudio()
    {
        return preferences.getBoolean(UiConstants.IS_SAVE_RECEIVED_AUDIO, false);
    }

    @Override
    public boolean isSaveSentAudio()
    {
        return preferences.getBoolean(UiConstants.IS_SAVE_SEND_AUDIO, false);
    }


    private void setListener()
    {
//        playButton.setOnClickListener(this);
//        finishButton.setOnClickListener(this);
//        playRecButton.setOnClickListener(this);
//        finishRecButton.setOnClickListener(this);
//
//        startSendButton.setOnClickListener(this);
//        stopSendButton.setOnClickListener(this);
//        startReceButton.setOnClickListener(this);
//        stopReceButton.setOnClickListener(this);
//        stopUDPButton.setOnClickListener(this);
    }

    @Override
    protected void onDestroy()
    {
        presenter.release();
        super.onDestroy();
    }

//    @Override
//    public void onClick(View v)
//    {
//        switch (v.getId())
//        {
////            case R.id.start_button:
////                // 开始录制
////                Log.i(TAG, "start_button::");
////                recordTask = new RecordTask();
////                recordTask.execute();
////                startButton.setEnabled(false);
////                stopButton.setEnabled(true);
////                break;
////            case R.id.stop_button:
////                // 结束录制
////                Log.i(TAG, "stop_button::");
////                isRecording = false;
////                startButton.setEnabled(true);
////                stopButton.setEnabled(false);
////                break;
//            case R.id.play_button:
//                // 开始播放
//                Log.i(TAG, "play_button::");
//                presenter.startPlaySavedSentAudio();
//                finishButton.setEnabled(true);
//                playButton.setEnabled(false);
//                break;
//            case R.id.finish_button:
//                // 停止播放
//                Log.i(TAG, "finish_button::");
//                presenter.stopPlaySavedSentAudio();
//                playButton.setEnabled(true);
//                finishButton.setEnabled(false);
//                break;
//            case R.id.play_rec_button:
//                // 开始播放rece
//                Log.i(TAG, "play_button::");
//                presenter.startPlaySavedRecievedAudio();
//                finishRecButton.setEnabled(true);
//                playRecButton.setEnabled(false);
//                break;
//            case R.id.finish_rec_button:
//                // 停止播放rece
//                Log.i(TAG, "finish_button::");
//                presenter.stopPlaySavedRecievedAudio();
//                playRecButton.setEnabled(true);
//                finishRecButton.setEnabled(false);
//                break;
//            case R.id.start_send_button:
//                // 开始发送数据
//                Log.i(TAG, "start_send_button::");
//
//                presenter.startSendAudioData();
//
//                stopSendButton.setEnabled(true);
//                startSendButton.setEnabled(false);
//                break;
//            case R.id.stop_send_button:
//                // 结束发送数据
//                Log.i(TAG, "stop_send_button::");
//                presenter.stopSendAudioData();
//                stopSendButton.setEnabled(false);
//                startSendButton.setEnabled(true);
//
//                break;
//            case R.id.start_rece_button:
//                // 开始接收
//                Log.i(TAG, "start_rece_button::");
//                presenter.startReceiveAudioData();
//
//                stopReceButton.setEnabled(true);
//                startReceButton.setEnabled(false);
//                break;
//            case R.id.stop_rece_button:
//                // 结束接收
//                Log.i(TAG, "stop_rece_button::");
//                presenter.stopReceiveAudioData();
//                stopReceButton.setEnabled(false);
//                startReceButton.setEnabled(true);
//                break;
//
//            case R.id.stop_udp_button:
//                Log.i(TAG, "stop_udp_button");
//                presenter.stopSocket();
//                stopUDPButton.setEnabled(false);
//                stopUDPButton.setText("Socket已停止！");
//                break;
//
//            default:
//                break;
//        }
//    }

    class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration
    {

        private final int mVerticalSpaceHeight;

        public VerticalSpaceItemDecoration(int mVerticalSpaceHeight)
        {
            this.mVerticalSpaceHeight = mVerticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state)
        {
            outRect.bottom = mVerticalSpaceHeight;
        }
    }
}
