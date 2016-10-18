package com.hubin.android.p2pvoice.api.db.impl;

import com.hubin.android.p2pvoice.UiApplication;
import com.hubin.android.p2pvoice.api.db.itf.IDbService;
import com.hubin.android.p2pvoice.bean.dao.DaoSession;
import com.hubin.android.p2pvoice.bean.dao.Pointer;
import com.hubin.android.p2pvoice.bean.dao.PointerDao;
import com.hubin.android.p2pvoice.utils.UiConstants;

import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

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
    private DaoSession daoSession;

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
        daoSession = UiApplication.getDaoMaster().newSession();
        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
        initData();
    }

    // 获取 NoteDao 对象
    private PointerDao getPointerDao()
    {
        return daoSession.getPointerDao();
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
        Query query = getPointerDao().queryBuilder()
                .orderAsc(PointerDao.Properties.CreateDate)
                .build();
        return query.list();
    }

    @Override
    public Pointer getPointer(String ip)
    {
        // Query 类代表了一个可以被重复执行的查询
        Query query = getPointerDao().queryBuilder()
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
            return getPointerDao().insert(pointer);
        }
        return -1;
    }

    @Override
    public boolean deletePointer(Pointer pointer)
    {
        if (pointer != null)
        {
            getPointerDao().delete(pointer);
            return true;

        }
        return false;
    }

    @Override
    public boolean updatePointer(Pointer pointer)
    {
        if (pointer != null)
        {
            getPointerDao().update(pointer);
            return true;
        }
        return false;
    }
}
