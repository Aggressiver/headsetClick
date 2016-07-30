package com.csu.wk.headset;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.csu.wk.headset.utils.HeadSetUtil;

public class MainActivity extends AppCompatActivity implements HeadSetUtil.onHeadSetClickListener {
    HeadSetUtil mHeadSetUtil;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHeadSetUtil = HeadSetUtil.getInstance();


    }

    @Override
    protected void onResume() {
        super.onResume();
        mContext = this.getApplicationContext();
        mHeadSetUtil.setHeadSetClickListener(this);
        mHeadSetUtil.open(mContext);
    }

    @Override
    public void oneClick() {
        Toast.makeText(this.getApplicationContext(), "单击一次", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doubleClick() {
        Toast.makeText(this.getApplicationContext(), "单击二次", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void threeClick() {
        Toast.makeText(this.getApplicationContext(), "单击三次", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHeadSetUtil.close(mContext);
    }
}
