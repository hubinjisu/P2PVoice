package com.hubin.android.p2pvoice.ui.pointerdetail;

import com.hubin.android.p2pvoice.base.BasePresenter;
import com.hubin.android.p2pvoice.base.IBaseView;
import com.hubin.android.p2pvoice.bean.Pointer;

/**
 * Created by tester on 16-7-17.
 */
public interface PointerDetailContract
{

    interface IPointerDetailPresenter extends BasePresenter<IPointerDetailView> {
        long addPointer(Pointer pointer);
        void deletePointer(Pointer pointer);
        void updatePointer(Pointer pointer);
    }

    interface IPointerDetailView extends IBaseView<IPointerDetailPresenter> {
        void showSaveResult(boolean result);
        void showDeleteResult(boolean result);
        void showUpdateResult(boolean result);
    }
}
