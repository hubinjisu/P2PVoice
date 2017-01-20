package com.hubin.android.p2pvoice.ui.pointerdetail;

import com.hubin.android.p2pvoice.api.db.impl.DbService;
import com.hubin.android.p2pvoice.bean.Pointer;

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
    public long addPointer(Pointer pointer)
    {
        return dbService.addPointer(pointer);
    }

    @Override
    public void deletePointer(Pointer pointer)
    {
        dbService.deletePointer(pointer);
    }

    @Override
    public void updatePointer(Pointer pointer)
    {
        dbService.updatePointer(pointer);
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
