package com.sse.monitor.ui.activity;

import android.graphics.drawable.ClipDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.sse.monitor.R;

import java.util.Timer;
import java.util.TimerTask;

public class GestureLockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_lock);

        final ImageView iv = (ImageView) findViewById(R.id.gesturel_iv);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.test_anim);
        iv.startAnimation(animation);

        //获取图片所显示的ClipDrawble对象
        final ClipDrawable drawable = (ClipDrawable)iv.getDrawable();
        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                if(msg.what == 0x1233)
                {
                    //修改ClipDrawable的level值
                    drawable.setLevel(drawable.getLevel() +200);
                }
            }
        };
        final Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            public void run()
            {
                Message msg = new Message();
                msg.what = 0x1233;
                //发送消息,通知应用修改ClipDrawable对象的level值
                handler.sendMessage(msg);
                //取消定时器
                if(drawable.getLevel() >= 10000)
                {
                    timer.cancel();
                }
            }
        },0,50);

    }
}
