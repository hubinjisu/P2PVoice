package com.hubin.android.p2pvoice.api.db.itf;

import com.hubin.android.p2pvoice.bean.Pointer;

import java.util.List;

/**
 * Description:
 * <p/>
 * Author: hubin
 * Date: 2016/8/26.
 */
public interface IDbService
{
    List<Pointer> getAllPointers();
    Pointer getPointer(String ip);
    long addPointer(Pointer pointer);
    boolean deletePointer(Pointer pointer);
    boolean updatePointer(Pointer pointer);
}
