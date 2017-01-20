package com.hubin.android.p2pvoice;

import android.app.Application;
import android.app.KeyguardManager;
import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;

import com.hubin.android.p2pvoice.api.db.impl.DBHelper;
import com.hubin.android.p2pvoice.bean.dao.DaoMaster;

/**
 * replace the Application
 * put the global shared variables into it.
 */
public class UiApplication extends Application
{
    public static volatile Handler applicationHandler = null;
    private static String TAG = "UiApplication";
    private static UiApplication mInstance;
    private static AudioManager audioManager;
    private static KeyguardManager keyguardManager;
    private static PowerManager powerManager;

    private static DaoMaster daoMaster;

    public UiApplication()
    {
        mInstance = this;
    }

    public static UiApplication getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new UiApplication();
        }
        return mInstance;
    }

    public static AudioManager getAudioManager()
    {
        if (audioManager == null)
        {
            audioManager = (AudioManager) UiApplication.getInstance().getSystemService(Context.AUDIO_SERVICE);
        }
        return audioManager;
    }

    public static PowerManager getPowerManager()
    {
        if (powerManager == null)
        {
            powerManager = (PowerManager) UiApplication.getInstance().getSystemService(POWER_SERVICE);
        }
        return powerManager;
    }

    public static KeyguardManager getKeyguardManager()
    {
        if (keyguardManager == null)
        {
            keyguardManager = (KeyguardManager) UiApplication.getInstance().getSystemService(Context.KEYGUARD_SERVICE);
        }
        return keyguardManager;
    }

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        // 初始化日志
        Log.i(TAG, "init UiApplication");
//        LeakCanary.install(this);
        applicationHandler = new Handler(this.getMainLooper());
        // DaoMaster对象的方法放到 Application 层，避免多次创建生成 Session 对象
        getDaoMaster();
    }

    public static DaoMaster getDaoMaster() {
        if (daoMaster == null)
        {
            // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
            daoMaster = new DaoMaster(DBHelper.getInstance(getInstance()).getWritableDb());
        }
        return daoMaster;
    }

}
