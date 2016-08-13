package com.hubin.android.p2pvoice.ui.pointer;

import com.hubin.android.p2pvoice.base.BasePresenter;
import com.hubin.android.p2pvoice.base.IBaseView;
import com.hubin.android.p2pvoice.model.PointerListItem;

import java.util.ArrayList;

/**
 * Created by tester on 16-7-17.
 */
public interface PointerContract {

    interface IPointerPresenter extends BasePresenter<IPointerView> {
        void startPlaySavedRecievedAudio();

        void stopPlaySavedRecievedAudio();

        void startPlaySavedSentAudio();

        void stopPlaySavedSentAudio();

        void startSendAudioData();

        void stopSendAudioData();

        void startReceiveAudioData();

        void stopReceiveAudioData();

        void stopSocket();

        ArrayList<PointerListItem> getPointerList();
    }

    interface IPointerView extends IBaseView<IPointerPresenter> {
        String getRemotePointerIp();

        int getRemotePointerPort();

        int getAudioBufferSize();

        int getAudioSampleRate();

        boolean isSaveReceivedAudio();

        boolean isSaveSentAudio();
    }
}
