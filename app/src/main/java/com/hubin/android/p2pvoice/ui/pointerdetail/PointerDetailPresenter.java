package com.hubin.android.p2pvoice.ui.pointerdetail;

import com.hubin.android.p2pvoice.api.db.impl.DbService;
import com.hubin.android.p2pvoice.bean.PointerListItem;

/**
 * Description:
 * <p/>
 * Author: hubin
 * Date: 2016/8/22.
 */
public class PointerDetailPresenter implements PointerDetailContract.IPointerDetailPresenter
{
    private PointerDetailContract.IPointerDetailView detailView;
    private DbService dbService;

    public PointerDetailPresenter(PointerDetailContract.IPointerDetailView detailView)
    {
        this.detailView = detailView;
        this.dbService = DbService.getInstance();
    }

    @Override
    public void addPointer(PointerListItem pointer)
    {
        dbService.addPointer(pointer.getPointer());
    }

    @Override
    public void deletePointer(PointerListItem pointer)
    {
        dbService.deletePointer(pointer.getPointer());
    }

    @Override
    public void updatePointer(PointerListItem pointer)
    {
        dbService.updatePointer(pointer.getPointer());
    }

    @Override
    public void start()
    {

    }

    @Override
    public void release()
    {

    }
}
