package com.hubin.android.p2pvoice.api.voice.itf;

/**
 * Created by tester on 16-7-1.
 */
public interface IP2pVoiceService {
    boolean initRecordFile();

    boolean saveOutputVoice();

    boolean saveInputVoice();

    boolean startSendVoice();

    boolean stopSendVoice();

    boolean startReceiveVoice();

    boolean stopReceiveVoice();

    boolean startPlayReceivedVoice();

    boolean stopPlayReceivedVoice();

    String getLocalIp();
}
