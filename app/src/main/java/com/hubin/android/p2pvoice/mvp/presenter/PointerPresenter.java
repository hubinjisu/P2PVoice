package com.hubin.android.p2pvoice.mvp.presenter;

import com.hubin.android.p2pvoice.mvp.model.impl.P2pVoiceServiceImpl;
import com.hubin.android.p2pvoice.mvp.model.itf.IP2pVoiceService;
import com.hubin.android.p2pvoice.mvp.view.IPointerView;

/**
 * Created by tester on 16-7-1.
 */
public class PointerPresenter {
    private IPointerView iPointerView;
    private IP2pVoiceService p2pVoiceService;
    public PointerPresenter(IPointerView iPointerView) {
        this.iPointerView = iPointerView;
        this.p2pVoiceService = new P2pVoiceServiceImpl();
    }



}
