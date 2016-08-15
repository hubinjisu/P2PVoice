package com.hubin.android.p2pvoice.ui.pointer;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.hubin.android.p2pvoice.R;
import com.hubin.android.p2pvoice.model.ControlBtnItem;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * 说明：单呼控制按钮适配器
 *
 * @author
 * @Date 2015-3-5
 */
public class CallControlAdapter extends BaseAdapter
{
    private String TAG = CallControlAdapter.class.getSimpleName();
    public LayoutInflater mInflater;
    private ViewHolder mViewHolder;
    private List<ControlBtnItem> mDataSource;
    private Context context;

    public CallControlAdapter(Context context, List<ControlBtnItem> dataSrc)
    {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mDataSource = dataSrc;
        if (mDataSource == null)
        {
            mDataSource = new ArrayList<ControlBtnItem>();
        }
    }

    @Override
    public int getCount()
    {

        return mDataSource == null ? 0 : mDataSource.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        try
        {
            if (convertView != null)
            {
                mViewHolder = (ViewHolder) convertView.getTag();
            }
            else
            {
                convertView = mInflater.inflate(R.layout.adapter_control_btn_item, null);
                mViewHolder = new ViewHolder();
                mViewHolder.controlItem = (TextView) convertView.findViewById(R.id.call_control_item);
                convertView.setTag(mViewHolder);
            }
            if (mDataSource == null || mDataSource.size() <= position)
            {
                return convertView;
            }
            final ControlBtnItem controlBtnItem = mDataSource.get(position);
            if (controlBtnItem == null)
            {
                return convertView;
            }

            mViewHolder.controlItem.setText(controlBtnItem.getName());
            Drawable drawable = context.getResources().getDrawable(controlBtnItem.getIconRes());
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mViewHolder.controlItem.setCompoundDrawables(null, drawable, null, null);
            mViewHolder.controlItem.setBackground(context.getResources().getDrawable(controlBtnItem.getBgRes()));
            RxView.clicks(mViewHolder.controlItem)
                    .throttleFirst(500, TimeUnit.MICROSECONDS)
                    .subscribe(new Action1<Void>()
                    {
                        @Override
                        public void call(Void aVoid)
                        {
                            controlBtnItem.getClickListener().onClick(mViewHolder.controlItem);
                        }
                    });
            mViewHolder.controlItem.setSelected(controlBtnItem.isSelected());
        }
        catch (NotFoundException e)
        {
            Log.e(TAG, e.toString());
        }
        return convertView;
    }

    class ViewHolder
    {
        TextView controlItem;
    }

    public void clear()
    {
        this.mDataSource = null;
    }

}
