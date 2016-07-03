package com.hubin.android.p2pvoice.mvp.view;

/**
 * Created by tester on 16-7-1.
 */
public interface IPointerView {

    String getRemotePointerIp();
    int getRemotePointerPort();

    int getAudioBufferSize();

    int getAudioSampleRate();

    boolean isSaveReceivedAudio();

    boolean isSaveSentAudio();


}
