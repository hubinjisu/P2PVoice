package com.hubin.android.p2pvoice.api.voice.impl;

import com.hubin.android.p2pvoice.api.voice.itf.IP2pVoiceService;

/**
 * Created by tester on 16-7-1.
 */
public class P2pVoiceServiceImpl implements IP2pVoiceService {
    @Override
    public boolean initRecordFile() {
        return false;
    }

    @Override
    public boolean saveOutputVoice() {
        return false;
    }

    @Override
    public boolean saveInputVoice() {
        return false;
    }

    @Override
    public boolean startSendVoice() {
        return false;
    }

    @Override
    public boolean stopSendVoice() {
        return false;
    }

    @Override
    public boolean startReceiveVoice() {
        return false;
    }

    @Override
    public boolean stopReceiveVoice() {
        return false;
    }

    @Override
    public boolean startPlayReceivedVoice() {
        return false;
    }

    @Override
    public boolean stopPlayReceivedVoice() {
        return false;
    }

    @Override
    public String getLocalIp() {
        return "";
    }
}
