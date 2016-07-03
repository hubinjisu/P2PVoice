package com.hubin.android.p2pvoice.mvp.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.hubin.android.p2pvoice.R;

/**
 * Created by tester on 16-7-3.
 */
public class SettingActivity extends Activity {

    private EditText ipEditText;
    private Switch recordSendSwitch;
    private Switch recordRecSwitch;
    private RadioGroup radioGroup;
    private RadioButton frequence8K, frequence16K, frequence44K;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);

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
//                if (isChecked) {
//                    isSaveSend = true;
//                } else {
//                    isSaveSend = false;
//                }
            }
        });
        recordRecSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    isSaveRec = true;
//                } else {
//                    isSaveRec = false;
//                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

//                if (checkedId == frequence8K.getId()) {
//                    frequence = 8000;
//                    dataSize = 160;
//                } else if (checkedId == frequence16K.getId()) {
//                    frequence = 16000;
//                    dataSize = 320;
//                } else if (checkedId == frequence44K.getId()) {
//                    frequence = 44100;
//                    dataSize = 640;
//                }
            }
        });
    }
}
