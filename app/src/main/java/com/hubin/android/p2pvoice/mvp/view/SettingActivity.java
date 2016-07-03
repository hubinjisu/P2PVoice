package com.hubin.android.p2pvoice.mvp.view;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
public class SettingActivity extends Activity {

    private EditText ipEditText;
    private Switch recordSendSwitch;
    private Switch recordRecSwitch;
    private RadioGroup radioGroup;
    private SharedPreferences preferences;
    private RadioButton frequence8K, frequence16K, frequence44K;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        ipEditText = (EditText) findViewById(R.id.ip_edittext);
        recordSendSwitch = (Switch) findViewById(R.id.switch_send_record);
        recordRecSwitch = (Switch) findViewById(R.id.switch_received_record);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        frequence8K = (RadioButton) findViewById(R.id.f8k_radiobutton);
        frequence16K = (RadioButton) findViewById(R.id.f16k_radiobutton);
        frequence44K = (RadioButton) findViewById(R.id.f44k_radiobutton);
    }

    private void setListener() {
        recordSendSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences.edit().putBoolean(UiConstants.IS_SAVE_SEND_AUDIO, isChecked);
                preferences.edit().commit();
            }
        });
        recordRecSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences.edit().putBoolean(UiConstants.IS_SAVE_RECEIVED_AUDIO, isChecked);
                preferences.edit().commit();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == frequence8K.getId()) {
                    preferences.edit().putInt(UiConstants.AUDIO_SAMPLE_RATE, 8000);
                    preferences.edit().putInt(UiConstants.AUDIO_BUFFER_SIZE, 160);
                    preferences.edit().commit();
                } else if (checkedId == frequence16K.getId()) {
                    preferences.edit().putInt(UiConstants.AUDIO_SAMPLE_RATE, 16000);
                    preferences.edit().putInt(UiConstants.AUDIO_BUFFER_SIZE, 320);
                    preferences.edit().commit();
                } else if (checkedId == frequence44K.getId()) {
                    preferences.edit().putInt(UiConstants.AUDIO_SAMPLE_RATE, 44100);
                    preferences.edit().putInt(UiConstants.AUDIO_BUFFER_SIZE, 640);
                    preferences.edit().commit();
                }
            }
        });
    }
}
