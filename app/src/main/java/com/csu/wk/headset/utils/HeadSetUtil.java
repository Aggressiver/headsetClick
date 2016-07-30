package com.csu.wk.headset.utils;

import android.content.ComponentName;
import android.content.Context;
import android.media.AudioManager;

import com.csu.wk.headset.receiver.MediaButtonReceiver;

/**
 * Created by WangKun on 16/7/30.
 */
public class HeadSetUtil {
    public static HeadSetUtil mInstance;
    onHeadSetClickListener mHeadSetClickListener = null;

    private HeadSetUtil() {
    }

    public static HeadSetUtil getInstance() {
        if (mInstance == null) {
            synchronized (HeadSetUtil.class) {
                if (mInstance == null) {
                    mInstance = new HeadSetUtil();
                }
            }
        }
        return mInstance;
    }

    public onHeadSetClickListener getHeadSetClickListener() {
        return mHeadSetClickListener;
    }

    public void setHeadSetClickListener(onHeadSetClickListener mHeadSetClickListener) {
        this.mHeadSetClickListener = mHeadSetClickListener;
    }

    /**
     * 为Media_Button 注册监听器
     */
    public void open(Context context) {
        if (mHeadSetClickListener == null) {
            throw new IllegalStateException("please set HeadSetClickListener");
        }
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        ComponentName cn = new ComponentName(context.getPackageName(), MediaButtonReceiver.class.getName());
        am.registerMediaButtonEventReceiver(cn);
    }

    public void close(Context context) {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        ComponentName cn = new ComponentName(context.getPackageName(), MediaButtonReceiver.class.getName());
        am.unregisterMediaButtonEventReceiver(cn);
    }

    public void deleteHeadSetListener() {
        mHeadSetClickListener = null;
    }

   public  interface onHeadSetClickListener {
        abstract void oneClick();

        abstract void doubleClick();

        abstract void threeClick();
    }
}
