package com.hubin.android.p2pvoice.ui.pointerdetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.hubin.android.p2pvoice.R;
import com.hubin.android.p2pvoice.bean.dao.Pointer;
import com.hubin.android.p2pvoice.utils.UiConstants;

/**
 * Description:
 * <p/>
 * Author: hubin
 * Date: 2016/8/22.
 */
public class PointerDetailActivity extends AppCompatActivity implements PointerDetailContract.IPointerDetailView
{
    private static final String TAG = "PointerDetailActivity";

    private EditText ipEditText;
    private EditText portEditText;
    private Switch recordSendSwitch;
    private Switch recordRecSwitch;
    private Spinner sampleRateSpinner;
    //    private RadioGroup radioGroup;
//    private SharedPreferences preferences;
//    private RadioButton frequence8K, frequence16K, frequence44K;
    private Toolbar mToolbar;
    private PointerDetailPresenter presenter;

    private Pointer pointerBean;
    private boolean isEditable;
    private boolean isAddPage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_pointer_detail);
        presenter = new PointerDetailPresenter(this);
//        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        ipEditText = (EditText) findViewById(R.id.ip_edittext);
        portEditText = (EditText) findViewById(R.id.port_edittext);
        recordSendSwitch = (Switch) findViewById(R.id.switch_send_record);
        recordRecSwitch = (Switch) findViewById(R.id.switch_received_record);
        sampleRateSpinner = (Spinner) findViewById(R.id.sample_rate_spinner);
        sampleRateSpinner.setAdapter(new ArrayAdapter<String>(this, R.layout.simple_spinner_item, getResources().getStringArray(R.array.sample_rate)));
//        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
//        frequence8K = (RadioButton) findViewById(R.id.f8k_radiobutton);
//        frequence16K = (RadioButton) findViewById(R.id.f16k_radiobutton);
//        frequence44K = (RadioButton) findViewById(R.id.f44k_radiobutton);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Pointer");//设置主标题
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));//设置主标题颜色
        //取代原本的actionbar
//        setSupportActionBar(mToolbar);

        //设置NavigationIcon的点击事件,需要放在setSupportActionBar之后设置才会生效,
        //因为setSupportActionBar里面也会setNavigationOnClickListener
        mToolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        mToolbar.inflateMenu(R.menu.pointer_detail_tool_bar_menu);//设置右上角的填充菜单
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                int menuItemId = item.getItemId();
                switch (menuItemId)
                {
                    case R.id.action_edit:
//                        item.setTitle("保存");
                        Toast.makeText(PointerDetailActivity.this, "start to edit", Toast.LENGTH_SHORT).show();
                        setEditable(true);
                        break;
                    case R.id.action_delete:
                        if (!UiConstants.DEFAULT_REMOTE_POINTER_IP.equals(pointerBean.getIp()))
                        {
                            Toast.makeText(PointerDetailActivity.this, "success to delete", Toast.LENGTH_SHORT).show();
                            presenter.deletePointer(pointerBean);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(PointerDetailActivity.this, "can not delete local pointer", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.action_confirm:
                        if (isAddPage)
                        {
                            resetPointerData();
                            presenter.addPointer(pointerBean);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(PointerDetailActivity.this, "success to save", Toast.LENGTH_SHORT).show();
                            resetPointerData();
                            presenter.updatePointer(pointerBean);
                            setEditable(false);
                        }
                        break;
                    case R.id.action_cancel:
                        if (isAddPage)
                        {
                            finish();
                        }
                        else
                        {
                            Toast.makeText(PointerDetailActivity.this, "fail to save", Toast.LENGTH_SHORT).show();
                            setEditable(false);
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void resetPointerData()
    {
        pointerBean.setIp(ipEditText.getText().toString());
        pointerBean.setIsRecordSend(recordSendSwitch.isChecked());
        pointerBean.setIsRecordReceive(recordRecSwitch.isChecked());
        switch (sampleRateSpinner.getSelectedItemPosition())
        {
            case 0:
                pointerBean.setAudioSampleRate(8000);
                break;
            case 1:
                pointerBean.setAudioSampleRate(16000);
                break;
            case 2:
                pointerBean.setAudioSampleRate(44100);
                break;
        }
    }

    private void setEditable(boolean editable)
    {
        isEditable = editable;
        ipEditText.setEnabled(editable);
        portEditText.setEnabled(editable);
        recordSendSwitch.setEnabled(editable);
        recordRecSwitch.setEnabled(editable);
        sampleRateSpinner.setEnabled(editable);
        mToolbar.getMenu().findItem(R.id.action_edit).setVisible(!isEditable);
        mToolbar.getMenu().findItem(R.id.action_delete).setVisible(!isEditable);
        mToolbar.getMenu().findItem(R.id.action_confirm).setVisible(isEditable);
        mToolbar.getMenu().findItem(R.id.action_cancel).setVisible(isEditable);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        pointerBean = getIntent().getParcelableExtra("pointer_bean");
        if (pointerBean != null)
        {
            Log.d(TAG, "pointerBean != null");
            ipEditText.setText(pointerBean.getIp() + "");
            portEditText.setText(pointerBean.getPort() + "");
            recordSendSwitch.setChecked(pointerBean.getIsRecordSend());
            recordRecSwitch.setChecked(pointerBean.getIsRecordReceive());
            switch (pointerBean.getAudioSampleRate())
            {
                case 44100:
                    sampleRateSpinner.setSelection(2);
                    break;
                case 16000:
                    sampleRateSpinner.setSelection(1);
                    break;
                case 8000:
                    sampleRateSpinner.setSelection(0);
                    break;
                default:
                    break;
            }
            setEditable(false);
            isAddPage = false;
        }
        else
        {
            pointerBean = new Pointer();
            setEditable(true);
            isAddPage = true;
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        Log.d(TAG, "onOptionsItemSelected: item.getItemId(): " + item.getItemId());
//        if (item.getItemId() == android.R.id.home)
//        {
//            onBackPressed();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        pointerBean = null;
        if (TextUtils.isEmpty(ipEditText.getText().toString()))
        {
            return;
        }

//        if (radioGroup.getCheckedRadioButtonId() == frequence8K.getId())
//        {
//            preferences.edit().putInt(UiConstants.AUDIO_SAMPLE_RATE, 8000);
//            preferences.edit().putInt(UiConstants.AUDIO_BUFFER_SIZE, 160);
//        }
//        else if (radioGroup.getCheckedRadioButtonId() == frequence16K.getId())
//        {
//            preferences.edit().putInt(UiConstants.AUDIO_SAMPLE_RATE, 16000);
//            preferences.edit().putInt(UiConstants.AUDIO_BUFFER_SIZE, 320);
//        }
//        else if (radioGroup.getCheckedRadioButtonId() == frequence44K.getId())
//        {
//            preferences.edit().putInt(UiConstants.AUDIO_SAMPLE_RATE, 44100);
//            preferences.edit().putInt(UiConstants.AUDIO_BUFFER_SIZE, 640);
//        }
//
//        preferences.edit().putBoolean(UiConstants.IS_SAVE_SEND_AUDIO, recordSendSwitch.isChecked());
//        preferences.edit().putBoolean(UiConstants.IS_SAVE_RECEIVED_AUDIO, recordRecSwitch.isChecked());
//        preferences.edit().putString(UiConstants.REMOTE_POINTER_IP, ipEditText.getText().toString());
//        preferences.edit().commit();
    }


    @Override
    public void showSaveResult(boolean result)
    {

    }

    @Override
    public void showDeleteResult(boolean result)
    {

    }

    @Override
    public void showUpdateResult(boolean result)
    {

    }
}
