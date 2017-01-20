package com.hubin.android.p2pvoice.bean.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.hubin.android.p2pvoice.bean.Pointer;

import com.hubin.android.p2pvoice.bean.dao.PointerDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig pointerDaoConfig;

    private final PointerDao pointerDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        pointerDaoConfig = daoConfigMap.get(PointerDao.class).clone();
        pointerDaoConfig.initIdentityScope(type);

        pointerDao = new PointerDao(pointerDaoConfig, this);

        registerDao(Pointer.class, pointerDao);
    }
    
    public void clear() {
        pointerDaoConfig.clearIdentityScope();
    }

    public PointerDao getPointerDao() {
        return pointerDao;
    }

}
