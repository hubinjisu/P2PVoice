package com.hubin.android.p2pvoice.ui.pointerdetail;

import com.hubin.android.p2pvoice.base.BasePresenter;
import com.hubin.android.p2pvoice.base.IBaseView;
import com.hubin.android.p2pvoice.bean.PointerListItem;

/**
 * Created by tester on 16-7-17.
 */
public interface PointerDetailContract
{

    interface IPointerDetailPresenter extends BasePresenter<IPointerDetailView> {
        void showPointerDetail(PointerListItem pointer);
    }

    interface IPointerDetailView extends IBaseView<IPointerDetailPresenter> {
    }
}