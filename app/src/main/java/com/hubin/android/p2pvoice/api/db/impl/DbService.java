package com.hubin.android.p2pvoice.api.db.impl;

import com.hubin.android.p2pvoice.UiApplication;
import com.hubin.android.p2pvoice.api.db.itf.IDbService;
import com.hubin.android.p2pvoice.bean.Pointer;
import com.hubin.android.p2pvoice.bean.dao.DaoSession;
import com.hubin.android.p2pvoice.bean.dao.PointerDao;
import com.hubin.android.p2pvoice.utils.UiConstants;

import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * Description:
 * <p/>
 * Author: hubin
 * Date: 2016/8/26.
 */
public class DbService implements IDbService
{
    private static final String TAG = "DbService";
    private static DbService instance;
    private PointerDao pointerDao;

    public static DbService getInstance()
    {
        if (instance == null)
        {
            instance = new DbService();
        }
        return instance;
    }

    private DbService()
    {
        DaoSession daoSession = UiApplication.getDaoMaster().newSession();
        pointerDao = daoSession.getPointerDao();
        initData();
    }

    private void initData()
    {
        if (getPointer(UiConstants.DEFAULT_REMOTE_POINTER_IP) == null)
        {
            Pointer localPointer = new Pointer();
            localPointer.setId(0L);
            localPointer.setIp(UiConstants.DEFAULT_REMOTE_POINTER_IP);
            localPointer.setPort(UiConstants.DEFAULT_AUDIO_PORT);
            addPointer(localPointer);
        }
    }

    @Override
    public List<Pointer> getAllPointers()
    {
        Query query = pointerDao.queryBuilder()
                .orderAsc(PointerDao.Properties.CreateDate)
                .build();
        return query.list();
    }

    @Override
    public Pointer getPointer(String ip)
    {
        // Query 类代表了一个可以被重复执行的查询
        Query query = pointerDao.queryBuilder()
                .where(PointerDao.Properties.Ip.eq(ip))
                .orderAsc(PointerDao.Properties.CreateDate)
                .build();

        // 查询结果以 List 返回
        List<Pointer> notes = query.list();
        if (notes != null && notes.size() > 0)
        {
            return notes.get(0);
        }
        else
        {
            return null;
        }
    }

    @Override
    public long addPointer(Pointer pointer)
    {
        if (pointer != null)
        {
            return pointerDao.insert(pointer);
        }
        return -1;
    }

    @Override
    public boolean deletePointer(Pointer pointer)
    {
        if (pointer != null)
        {
            pointerDao.delete(pointer);
            return true;

        }
        return false;
    }

    @Override
    public boolean updatePointer(Pointer pointer)
    {
        if (pointer != null)
        {
            pointerDao.update(pointer);
            return true;
        }
        return false;
    }
}
