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
import com.hubin.android.p2pvoice.ui.pointerdetail.PointerDetailActivity;
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
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        initData();
    }

    private void initData()
    {
        listAdapter.updateList(presenter.getPointerList());
    }

    /**
     * 初始化View
     */
    private void initView()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.pointer_toolbar);

        toolbar.setTitle("P2P");//设置主标题
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));//设置主标题颜色

        toolbar.inflateMenu(R.menu.pointer_list_tool_bar_menu);//设置右上角的填充菜单
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.action_item1)
                {
                    startActivity(new Intent(PointerListActivity.this, PointerDetailActivity.class));
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

    @Override
    protected void onDestroy()
    {
        presenter.release();
        super.onDestroy();
    }


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
