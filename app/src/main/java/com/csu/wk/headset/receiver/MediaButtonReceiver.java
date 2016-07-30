package com.csu.wk.headset.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;

import com.csu.wk.headset.utils.HeadSetUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by WangKun on 16/7/30.
 */
public class MediaButtonReceiver extends BroadcastReceiver {
    public static int count = 0;
    public MyTimerTask mTimerTask;
    HeadSetUtil.onHeadSetClickListener mHeadSetClickListener;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                mHeadSetClickListener.oneClick();
            }
            if (msg.what == 2) {
                mHeadSetClickListener.doubleClick();
            }
            if (msg.what == 3) {
                mHeadSetClickListener.threeClick();
            }
        }
    };
    private Timer mTimer;

    public MediaButtonReceiver() {
        mTimer = new Timer();
        mHeadSetClickListener = HeadSetUtil.getInstance().getHeadSetClickListener();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_MEDIA_BUTTON.equals(action)) {
            KeyEvent keyEvent = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            if (mHeadSetClickListener != null) {
                Log.i("onReceive", keyEvent.getAction() + "");
                mTimerTask = new MyTimerTask();
                if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    if (count == 0) {
                        count++;
                        mTimer.schedule(mTimerTask, 1000);
                    } else if (count == 1) {
                        count++;
                    } else if (count == 2) {
                        count = 0;
                        mTimerTask.cancel();
                        mHeadSetClickListener.threeClick();
                    }

                }
            }
        }
        abortBroadcast();

    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            if (count == 1) {
                mHandler.sendEmptyMessage(1);
            } else if (count == 2) {
                mHandler.sendEmptyMessage(2);
            }
            count = 0;
        }
    }
}
