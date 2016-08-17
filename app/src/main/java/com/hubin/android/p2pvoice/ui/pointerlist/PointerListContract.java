package com.hubin.android.p2pvoice.ui.pointerlist;

import com.hubin.android.p2pvoice.base.BasePresenter;
import com.hubin.android.p2pvoice.base.IBaseView;
import com.hubin.android.p2pvoice.bean.PointerListItem;

import java.util.ArrayList;

/**
 * Created by tester on 16-7-17.
 */
public interface PointerListContract
{

    interface IPointerListPresenter extends BasePresenter<IPointerListView> {
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

//        void showPointerDetail(PointerListItem pointer);
    }

    interface IPointerListView extends IBaseView<IPointerListPresenter> {
        String getRemotePointerIp();

        int getRemotePointerPort();

        int getAudioBufferSize();

        int getAudioSampleRate();

        boolean isSaveReceivedAudio();

        boolean isSaveSentAudio();
    }
}
