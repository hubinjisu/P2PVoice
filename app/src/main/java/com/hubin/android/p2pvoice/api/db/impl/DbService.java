package com.hubin.android.p2pvoice.api.db.impl;

import com.hubin.android.p2pvoice.UiApplication;
import com.hubin.android.p2pvoice.api.db.itf.IDbService;
import com.hubin.android.p2pvoice.bean.dao.DaoSession;
import com.hubin.android.p2pvoice.bean.dao.Pointer;
import com.hubin.android.p2pvoice.bean.dao.PointerDao;

import java.util.ArrayList;
import java.util.List;

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
    private ArrayList<Pointer> pointers = new ArrayList<Pointer>();

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
        pointers = (ArrayList<Pointer>) getPointerDao().queryBuilder()
                .orderAsc(PointerDao.Properties.CreateDate)
                .list();
    }

    @Override
    public List<Pointer> getAllPointers()
    {
        return pointers;
    }

    @Override
    public Pointer getPointer(long id)
    {
        for (Pointer pointer : pointers)
        {
            if (pointer.getId().equals(id))
            {
                return pointer;
            }
        }
        return null;
//        // Query 类代表了一个可以被重复执行的查询
//        Query query = getPointerDao().queryBuilder()
//                .where(PointerDao.Properties.Id.eq(id))
//                .orderAsc(PointerDao.Properties.CreateDate)
//                .build();
//
//        // 查询结果以 List 返回
//        List<Pointer> notes = query.list();
//        if (notes != null && notes.size() > 0)
//        {
//            return notes.get(0);
//        }
//        else
//        {
//        }
    }

    @Override
    public boolean addPointer(Pointer pointer)
    {
        if (pointer != null)
        {
            getPointerDao().insert(pointer);
            pointers.add(pointer);
            return true;
        }
        return false;
    }

    @Override
    public boolean deletePointer(Pointer pointer)
    {
        if (pointer != null)
        {
            getPointerDao().delete(pointer);
            pointers.remove(pointer);
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
            pointers.remove(pointer);
            pointers.add(pointer);
            return true;
        }
        return false;
    }
}
