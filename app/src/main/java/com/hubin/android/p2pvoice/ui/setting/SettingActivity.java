package com.hubin.android.p2pvoice.ui.setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.hubin.android.p2pvoice.R;
import com.hubin.android.p2pvoice.utils.UiConstants;

/**
 * Created by tester on 16-7-3.
 */
public class SettingActivity extends AppCompatActivity
{
    private static final String TAG = "SettingActivity";
    private EditText ipEditText;
    private Switch recordSendSwitch;
    private Switch recordRecSwitch;
    private RadioGroup radioGroup;
    private SharedPreferences preferences;
    private RadioButton frequence8K, frequence16K, frequence44K;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_setting);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        ipEditText = (EditText) findViewById(R.id.ip_edittext);
        recordSendSwitch = (Switch) findViewById(R.id.switch_send_record);
        recordRecSwitch = (Switch) findViewById(R.id.switch_received_record);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        frequence8K = (RadioButton) findViewById(R.id.f8k_radiobutton);
        frequence16K = (RadioButton) findViewById(R.id.f16k_radiobutton);
        frequence44K = (RadioButton) findViewById(R.id.f44k_radiobutton);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Setting");//设置主标题
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));//设置主标题颜色
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.d(TAG, "onOptionsItemSelected: item.getItemId(): " + item.getItemId());
        if (item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (radioGroup.getCheckedRadioButtonId() == frequence8K.getId())
        {
            preferences.edit().putInt(UiConstants.AUDIO_SAMPLE_RATE, 8000);
            preferences.edit().putInt(UiConstants.AUDIO_BUFFER_SIZE, 160);
        }
        else if (radioGroup.getCheckedRadioButtonId() == frequence16K.getId())
        {
            preferences.edit().putInt(UiConstants.AUDIO_SAMPLE_RATE, 16000);
            preferences.edit().putInt(UiConstants.AUDIO_BUFFER_SIZE, 320);
        }
        else if (radioGroup.getCheckedRadioButtonId() == frequence44K.getId())
        {
            preferences.edit().putInt(UiConstants.AUDIO_SAMPLE_RATE, 44100);
            preferences.edit().putInt(UiConstants.AUDIO_BUFFER_SIZE, 640);
        }

        preferences.edit().putBoolean(UiConstants.IS_SAVE_SEND_AUDIO, recordSendSwitch.isChecked());
        preferences.edit().putBoolean(UiConstants.IS_SAVE_RECEIVED_AUDIO, recordRecSwitch.isChecked());
        preferences.edit().putString(UiConstants.REMOTE_POINTER_IP, ipEditText.getText().toString());
        preferences.edit().commit();
    }
}
