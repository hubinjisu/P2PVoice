package com.hubin.android.p2pvoice.ui.pointerlist;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hubin.android.p2pvoice.R;
import com.hubin.android.p2pvoice.bean.ControlBtnItem;
import com.hubin.android.p2pvoice.bean.PointerListItem;
import com.hubin.android.p2pvoice.ui.pointerdetail.PointerDetailActivity;
import com.hubin.android.p2pvoice.utils.UiConstants;
import com.hubin.android.p2pvoice.utils.UiUtils;
import com.hubin.android.p2pvoice.view.TimerText;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 说明：呼叫列表适配器
 *
 * @author hubin
 * @Date 2015-4-1
 */
public class PointerListAdapter extends RecyclerView.Adapter<PointerListAdapter.CallItemViewHolder>
{
    private static final String TAG = "PointerListAdapter";
    private static final int UPDATE_LIST = 0;
    private static final int UPDATE_LIST_ITEM = 1;
    public List<PointerListItem> mListItems;
    protected LayoutInflater inflater;
    private Context context;
    private AdapterHandler mHandler;
    private int controlViewHeight;
    private PointerListPresenter presenter;

    private ControlBtnItem releaseControlBtnItem;
    private ControlBtnItem sendControlBtnItem;
    private ControlBtnItem receiveControlBtnItem;
    private ControlBtnItem playSendRecordControlBtnItem;
    private ControlBtnItem showDetailControlBtnItem;
    private ControlBtnItem speakerControlBtnItem;
    private ControlBtnItem playReceiveRecordControlBtnItem;
    private ControlBtnItem videoCloseControlBtnItem;
    private ControlBtnItem dtmfControlBtnItem;
    private ControlBtnItem outsideAudiodeviceControlBtnItem;
    private ControlBtnItem closeRingControlBtnItem;
    private ControlBtnItem forwardControlBtnItem;
    private ControlBtnItem intoConfControlBtnItem;
    private ControlBtnItem transferToConfControlBtnItem;
    private ControlBtnItem showMoreControlBtnItem;

    private ControlBtnItem closeConfControlBtnItem;
    private ControlBtnItem openConfControlBtnItem;

    public PointerListAdapter(Context mContext, PointerListPresenter presenter)
    {
        this.context = mContext;
        this.inflater = LayoutInflater.from(mContext);
        this.controlViewHeight = (int) context.getResources().getDimension(R.dimen.call_list_ctr_height);
        this.mHandler = new AdapterHandler(this);
        this.presenter = presenter;
    }

    @Override
    public CallItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View convertView = inflater.inflate(R.layout.adapter_pointer_list_item, parent, false);
        return new CallItemViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(CallItemViewHolder holder, int position)
    {
        if (mListItems == null || mListItems.size() <= position)
        {
            return;
        }
        final PointerListItem item = mListItems.get(position);
        if (item == null)
        {
            return;
        }

        try
        {
            // viewHolder.timerView.setStartTime(item.getCallModel().getStartTime());
            // 显示通话对象
            holder.nameView.setText(item.getPointer().getIp());
            holder.numberView.setText(item.getPointer().getPort() + "");

            // 初始化状态和业务控制区
            handleStatus(holder, item);
        }
        catch (Exception e)
        {
            Log.e(TAG, e.toString());
        }
    }

    private void handleStatus(CallItemViewHolder viewHolder, final PointerListItem item)
    {
        ArrayList<ControlBtnItem> controlBtnItems = new ArrayList<ControlBtnItem>();
//        switch (item.getStatus())
//        {
//            case UiConstants.POINTER_SESSION_STATUS_CLOSED:
//                viewHolder.timerView.setStartTime(item.getStartTime(), true);
//                controlBtnItems.add(createSendControlItem(item));
//                controlBtnItems.add(createReceiveControlItem(item));
//
//                viewHolder.statusView.setText(context.getString(R.string.export_outgoing));
//                viewHolder.callStatus.setImageResource(R.mipmap.call_status_dialing);
//                break;
//            case UiConstants.POINTER_SESSION_STATUS_OPEN:
//                viewHolder.timerView.setStartTime(item.getStartTime(), true);
//                controlBtnItems.add(createReleaseControlItem(item));
//                controlBtnItems.add(createSendControlItem(item));
//                controlBtnItems.add(createPlayReceiveRecordControlItem(item));
//                controlBtnItems.add(createPlaySendRecordControlItem(item));
//
//                viewHolder.statusView.setText(context.getString(R.string.export_incoming));
//                viewHolder.callStatus.setImageResource(R.mipmap.call_status_ringing);
//                break;
//            default:
//                break;
//        }

        viewHolder.timerView.setStartTime(item.getStartTime(), true);
        controlBtnItems.add(createReleaseControlItem(item));
        controlBtnItems.add(createSendControlItem(item));
        controlBtnItems.add(createReceiveControlItem(item));
        controlBtnItems.add(createPlayReceiveRecordControlItem(item));
        controlBtnItems.add(createPlaySendRecordControlItem(item));
        controlBtnItems.add(createShowDetailControlItem(item));

        viewHolder.statusView.setText(context.getString(R.string.export_incoming));
        viewHolder.callStatus.setImageResource(R.mipmap.call_status_ringing);

        boolean hasSecondLine = false;
        // 单行默认显示6个控制按钮，如果大于6个，增加更多按钮
        if (controlBtnItems.size() > 6)
        {
            controlBtnItems.add(5, createShowMoreControlItem(item));
            hasSecondLine = true;
        }
        viewHolder.controlView.setAdapter(new CallControlAdapter(context, controlBtnItems));

        // 二级下拉菜单
        updateDropDownList(viewHolder, item, hasSecondLine);
    }

    /**
     * 挂断按钮
     */
    private ControlBtnItem createSendControlItem(final PointerListItem listItem)
    {
        if (sendControlBtnItem == null)
        {
            sendControlBtnItem = new ControlBtnItem(context.getString(R.string.send), R.drawable.call_hangup_btn_select_bg, R.drawable
                    .call_control_btn_select_bg);
        }
        sendControlBtnItem.setIsSelected(listItem.isSending());

        sendControlBtnItem.setClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    if (listItem.isSending())
                    {
                        presenter.stopSendAudioData();
                    }
                    else
                    {
                        presenter.startSendAudioData();
                        listItem.setStatus(UiConstants.POINTER_SESSION_STATUS_OPEN);
                    }
                    listItem.setSelected(!listItem.isSending());
                    listItem.setSending(!listItem.isSending());
                    notifyItemChanged(mListItems.indexOf(listItem));
                }
                catch (Exception e)
                {
                    Log.e(TAG, e.toString());
                }
            }
        });
        return sendControlBtnItem;
    }

    /**
     * 挂断按钮
     */
    private ControlBtnItem createReleaseControlItem(final PointerListItem listItem)
    {
        if (releaseControlBtnItem == null)
        {
            releaseControlBtnItem = new ControlBtnItem(context.getString(R.string.call_hangup), R.drawable.call_hangup_btn_select_bg, R.drawable
                    .call_control_btn_click_bg);
        }
        releaseControlBtnItem.setClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    presenter.stopSendAudioData();
                    presenter.stopPlaySavedSentAudio();
                    presenter.stopReceiveAudioData();
                    presenter.stopPlaySavedRecievedAudio();
                    presenter.stopSocket();
                    listItem.setStatus(UiConstants.POINTER_SESSION_STATUS_CLOSED);
                    listItem.setReceiving(false);
                    listItem.setSending(false);
                    listItem.setPlayingReceive(false);
                    listItem.setPlayingSend(false);
                    notifyItemChanged(mListItems.indexOf(listItem));
                    Log.d(TAG, "click:releaseControlBtnItem Call::sessionId:");
                }
                catch (Exception e)
                {
                    Log.e(TAG, e.toString());
                }
            }
        });
        return releaseControlBtnItem;
    }

    /**
     * 接听按钮
     */
    private ControlBtnItem createReceiveControlItem(final PointerListItem listItem)
    {
        if (receiveControlBtnItem == null)
        {
            receiveControlBtnItem = new ControlBtnItem(context.getString(R.string.receive), R.mipmap.call_answer, R.drawable.call_control_btn_select_bg);
        }
        receiveControlBtnItem.setIsSelected(listItem.isReceiving());
        receiveControlBtnItem.setClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    if (listItem.isReceiving())
                    {
                        presenter.stopReceiveAudioData();
                    }
                    else
                    {
                        presenter.startReceiveAudioData();
                        listItem.setStatus(UiConstants.POINTER_SESSION_STATUS_OPEN);
                    }
                    listItem.setSelected(!listItem.isReceiving());
                    listItem.setReceiving(!listItem.isReceiving());
                    notifyItemChanged(mListItems.indexOf(listItem));
                }
                catch (Exception e)
                {
                    Log.e(TAG, e.toString());
                }
            }
        });
        return receiveControlBtnItem;
    }

    /**
     * 静音按钮
     */
    private ControlBtnItem createPlayReceiveRecordControlItem(final PointerListItem listItem)
    {
        if (playReceiveRecordControlBtnItem == null)
        {
            playReceiveRecordControlBtnItem = new ControlBtnItem(context.getString(R.string.play_receive), R.drawable.call_mute_btn_select_bg, R.drawable
                    .call_control_btn_select_bg);
        }
        playReceiveRecordControlBtnItem.setIsSelected(listItem.isPlayingReceive());
        playReceiveRecordControlBtnItem.setClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    if (listItem.isPlayingReceive())
                    {
                        presenter.stopPlaySavedRecievedAudio();
                    }
                    else
                    {
                        presenter.startPlaySavedRecievedAudio();
                    }
                    listItem.setPlayingReceive(!listItem.isPlayingReceive());
                    notifyItemChanged(mListItems.indexOf(listItem));
                }
                catch (Exception e)
                {
                    Log.e(TAG, e.toString());
                }
            }
        });
        return playReceiveRecordControlBtnItem;
    }


    /**
     * 保持按钮
     */
    private ControlBtnItem createPlaySendRecordControlItem(final PointerListItem listItem)
    {
        if (playSendRecordControlBtnItem == null)
        {
            playSendRecordControlBtnItem = new ControlBtnItem(context.getString(R.string.play_send), R.drawable.call_hold_btn_select_bg, R
                    .drawable.call_control_btn_select_bg);
        }
        playSendRecordControlBtnItem.setIsSelected(listItem.isPlayingSend());

        playSendRecordControlBtnItem.setClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    if (listItem.isPlayingSend())
                    {
                        presenter.stopPlaySavedSentAudio();
                    }
                    else
                    {
                        presenter.startPlaySavedSentAudio();
                    }
                    listItem.setSelected(!listItem.isPlayingSend());
                    listItem.setPlayingReceive(!listItem.isPlayingSend());
                    notifyItemChanged(mListItems.indexOf(listItem));
                }
                catch (Exception e)
                {
                    Log.e(TAG, e.toString());
                }
            }
        });
        return playSendRecordControlBtnItem;
    }

    /**
     * 显示详情
     */
    private ControlBtnItem createShowDetailControlItem(final PointerListItem listItem)
    {
        if (showDetailControlBtnItem == null)
        {
            showDetailControlBtnItem = new ControlBtnItem(context.getString(R.string.detail), R.drawable.call_hold_btn_select_bg, R
                    .drawable.call_control_btn_click_bg);
        }
        showDetailControlBtnItem.setClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    Intent intent = new Intent(context, PointerDetailActivity.class);
                    intent.putExtra("pointer_bean", listItem.getPointer());
                    context.startActivity(intent);
                }
                catch (Exception e)
                {
                    Log.e(TAG, e.toString());
                }
            }
        });
        return showDetailControlBtnItem;
    }

    /**
     * 更多按钮
     */
    private ControlBtnItem createShowMoreControlItem(final PointerListItem listItem)
    {
        if (showMoreControlBtnItem == null)
        {
            showMoreControlBtnItem = new ControlBtnItem(context.getString(R.string.more), R.drawable.call_more_btn_select_bg, R.drawable
                    .call_control_btn_select_bg);
        }
        showMoreControlBtnItem.setIsSelected(listItem.isDropDown());
        showMoreControlBtnItem.setClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    boolean enable = !v.isSelected();
                    Log.d(TAG, "click:more to enable: " + enable);
                    v.setSelected(enable);
                    if (enable)
                    {
                        listItem.setIsActionShow(true);
                        // 隐藏其他已下拉的菜单
                        for (PointerListItem callItem : mListItems)
                        {
                            if (callItem.isDropDown())
                            {
                                listItem.setIsActionHide(false);
                                callItem.setIsDropDown(false);
                                updateListItem(callItem);
                                break;
                            }
                        }
                    }
                    else
                    {
                        listItem.setIsActionHide(true);
                    }
                    listItem.setIsDropDown(enable);
                    updateListItem(listItem);
                }
                catch (Exception e)
                {
                    Log.e(TAG, e.toString());
                }
            }
        });
        return showMoreControlBtnItem;
    }

    private void updateDropDownList(CallItemViewHolder viewHolder, PointerListItem item, boolean hasSecondLine)
    {
        if (!hasSecondLine)
        {
            if (item.isDropDown())
            {
                item.setIsDropDown(false);
                viewHolder.controlView.startAnimation(UiUtils.expand(viewHolder.controlView, controlViewHeight, controlViewHeight * 2, false, 50));
            }
            else
            {
                viewHolder.controlView.getLayoutParams().height = controlViewHeight;
                viewHolder.controlView.requestLayout();
            }
        }
        else
        {
            if (item.isDropDown())
            {
                if (item.isActionShow())
                {
                    item.setIsActionShow(false);
                    viewHolder.controlView.startAnimation(UiUtils.expand(viewHolder.controlView, controlViewHeight, controlViewHeight * 2, true, 200));
                }
                else
                {
                    viewHolder.controlView.getLayoutParams().height = controlViewHeight * 2;
                    viewHolder.controlView.requestLayout();
                }
            }
            else
            {
                if (item.isActionHide())
                {
                    item.setIsActionHide(false);
                    viewHolder.controlView.startAnimation(UiUtils.expand(viewHolder.controlView, controlViewHeight, controlViewHeight * 2, false, 200));
                }
                else
                {
                    viewHolder.controlView.getLayoutParams().height = controlViewHeight;
                    viewHolder.controlView.requestLayout();
                }
            }
        }
    }

    /**
     * 方法说明 : 更新列表单条记录
     *
     * @param callListItem
     * @return void
     * @author hubin
     * @Date 2015-4-14
     */
    private void updateSingleRow(PointerListItem callListItem)
    {
        if (callListItem == null)
        {
            return;
        }
        int position = mListItems.indexOf(callListItem);
        Log.d(TAG, "updateSingleRow:: PointerListItem:" + callListItem.getName() + " status:" + callListItem.getStatus() + " position:" + position);
        mListItems.set(position, callListItem);
        notifyItemChanged(position);
    }


    /**
     * 方法说明 : 更新列表单条记录
     *
     * @param callListItem
     * @return void
     * @author hubin
     * @Date 2015-4-14
     */
    public void updateListItem(PointerListItem callListItem)
    {
        Log.d(TAG, "updateListItem: ");
        if (mHandler != null && callListItem != null)
        {
            mHandler.removeMessages(UPDATE_LIST_ITEM, callListItem);
            Message msg = mHandler.obtainMessage();
            msg.what = UPDATE_LIST_ITEM;
            msg.obj = callListItem;
            mHandler.sendMessageDelayed(msg, 100);
        }
    }


    /**
     * 方法说明 : 更新列表所有记录
     *
     * @return void
     * @author hubin
     * @Date 2015-4-14
     */
    public void updateList(ArrayList<PointerListItem> callListItems)
    {
        Log.d(TAG, "updateList::");
        if (mHandler != null)
        {
            mHandler.removeMessages(UPDATE_LIST);
            Message msg = mHandler.obtainMessage();
            msg.what = UPDATE_LIST;
            msg.obj = callListItems;
            mHandler.sendMessageDelayed(msg, 100);
        }
    }

    /**
     * 方法说明 : 更新列表所有记录
     *
     * @return void
     * @author hubin
     * @Date 2015-4-14
     */
    public void initListItems(ArrayList<PointerListItem> callListItems)
    {
        Log.d(TAG, "initListItems: ");
        mListItems = callListItems;
        notifyDataSetChanged();
    }

    public void addItem(PointerListItem callListItem)
    {
        if (callListItem == null)
        {
            return;
        }
        Log.d(TAG, "addItem:: PointerListItem:" + callListItem.getName() + " status:" + callListItem.getStatus());
        mListItems.add(0, callListItem);
        notifyItemInserted(0);
        notifyItemRangeChanged(0, getItemCount());
    }

    @Override
    public int getItemCount()
    {
        if (mListItems != null && mListItems.size() > 0)
        {
            return mListItems.size();
        }
        else
        {
            return 0;
        }
    }

    public void removeItem(PointerListItem callListItem)
    {
        if (callListItem == null)
        {
            return;
        }
        Log.d(TAG, "removeItem:: PointerListItem:" + callListItem.getName() + " status:" + callListItem.getStatus());
        int position = mListItems.indexOf(callListItem);
        mListItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount() - position - 1);
    }

    public void clear()
    {
        mListItems = null;
    }

    class CallItemViewHolder extends RecyclerView.ViewHolder
    {
        ImageView iconView;
        ImageView callStatus;
        TextView nameView;
        TextView numberView;
        TextView statusView;
        TimerText timerView;
        GridView controlView;
//        int index;

        public CallItemViewHolder(View itemView)
        {
            super(itemView);
            iconView = (ImageView) itemView.findViewById(R.id.caller_icon);
            callStatus = (ImageView) itemView.findViewById(R.id.status_call);
            nameView = (TextView) itemView.findViewById(R.id.caller_name);
            numberView = (TextView) itemView.findViewById(R.id.caller_number);
            statusView = (TextView) itemView.findViewById(R.id.call_status);
            timerView = (TimerText) itemView.findViewById(R.id.call_list_timer);
            controlView = (GridView) itemView.findViewById(R.id.call_control);
        }
    }


    private static class AdapterHandler extends Handler
    {
        private WeakReference<PointerListAdapter> mOwner;

        public AdapterHandler(PointerListAdapter owner)
        {
            mOwner = new WeakReference<PointerListAdapter>(owner);
        }

        public void handleMessage(Message msg)
        {
            try
            {
                if (mOwner.get() != null)
                {
                    switch (msg.what)
                    {
                        case UPDATE_LIST_ITEM:
                            // 更新列表单条记录状态
                            mOwner.get().updateSingleRow((PointerListItem) msg.obj);
                            break;
                        case UPDATE_LIST:
                            // 更新列表全部记录状态
                            mOwner.get().initListItems((ArrayList<PointerListItem>) msg.obj);
                            break;

                        default:
                            break;
                    }
                }
            }
            catch (Exception e)
            {
                Log.e(TAG, e.toString());
            }
        }
    }
}
