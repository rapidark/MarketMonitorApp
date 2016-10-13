package com.sse.monitor.ui.activity;

import android.content.Intent;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shiki.utils.ReservoirUtils;
import com.sse.monitor.R;
import com.sse.monitor.core.BaseActivity;
import com.sse.monitor.mms.MmsConstants;
import com.sse.monitor.presenter.AppPresenter;
import com.sse.monitor.presenter.iview.AppView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Eric on 2016/5/3.
 */
public class SplashActivity extends BaseActivity implements AppView {
    @Bind(R.id.rl_root)
    RelativeLayout rlRoot;
    AppPresenter appPresenter;
    @Bind(R.id.splash_iv_text)
    ImageView mSplashIvText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        getSupportActionBar().hide();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        AlphaAnimation anim = new AlphaAnimation(0.2f, 1);
        anim.setDuration(2000);
        rlRoot.startAnimation(anim);
        final ClipDrawable splashIvTextCd = (ClipDrawable)mSplashIvText.getDrawable();
        Observable.interval(40, TimeUnit.MILLISECONDS).take(50).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                splashIvTextCd.setLevel(splashIvTextCd.getLevel() +200);
            }
        });
    }

    @Override
    protected void initData() {
        this.appPresenter = new AppPresenter();
        this.appPresenter.attachView(this);
        //appPresenter.checkUpdate();
       Observable.timer(2, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                if(ReservoirUtils.getInstance().contains(MmsConstants.ACCOUNT)){
                    enterGesture(false);
                }else{
                    enterLogin();
                }
            }
        });


    }

    @Override
    public void enterLogin() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void enterGesture(boolean isSetGesture) {
        startActivity(GestureLockActivity.getCallingIntent(SplashActivity.this, isSetGesture));
        finish();
    }


    @Override
    public void onFailure(String msg) {

    }
}
