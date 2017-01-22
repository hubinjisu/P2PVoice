package com.hubin.android.p2pvoice.api.db.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hubin.android.p2pvoice.bean.dao.DaoMaster;

/**
 * 说明：提供数据库创建更新等操作服务
 *
 * @author  hubin
 *
 * @Date 2015-3-17
 */
public class DBHelper extends DaoMaster.OpenHelper
{
    private static final String TAG = DBHelper.class.getSimpleName();
    public static final String DB_NAME = "p2p_voice.db"; // 数据库名称
    private static DBHelper mDBHelper;

    public synchronized static DBHelper getInstance(Context context)
    {
        if (mDBHelper == null)
        {
            mDBHelper = new DBHelper(context);
        }
        return mDBHelper;
    }
    
    public DBHelper(Context context)
    {
        super(context, DB_NAME, null);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
//        if(newVersion == 2)
//        {
//            db.execSQL("ALTER TABLE " + DBConstantValues.TB_NAME_DATA_TYPE +" ADD " + DBConstantValues.DB_DataType.TYPE_ICON + " STRING");
//            db.execSQL("ALTER TABLE " + DBConstantValues.TB_NAME_DATA_TYPE +" ADD " + DBConstantValues.DB_DataType.TYPE_VIDEO + " STRING");
//        }
//        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME_USER);
//        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME_CUSTOM_CONTACT);
//        DBHelper devOpenHelper = getInstance();
//        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
//        DaoSession daoSession = daoMaster.newSession();
//        AttendantDao userDao = daoSession.getAttendantDao();

    }

}
