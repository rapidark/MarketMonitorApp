package com.sse.monitor.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiki.utils.ApkUtils;
import com.sse.monitor.R;
import com.sse.monitor.core.BaseActivity;
import com.sse.monitor.presenter.AppPresenter;
import com.sse.monitor.presenter.iview.AppView;

import butterknife.Bind;

/**
 * Created by Maik on 2016/5/3.
 */
public class SplashActivity extends BaseActivity implements AppView {
    @Bind(R.id.rl_root) RelativeLayout rlRoot;
    @Bind(R.id.tv_version) TextView tvVersion;
    AppPresenter appPresenter;

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
    }

    @Override
    protected void initData() {
        this.appPresenter = new AppPresenter();
        this.appPresenter.attachView(this);
        appPresenter.getMvpView().setAppVersion("版本号：" + ApkUtils.getVersionName(this));
        appPresenter.checkUpdate();
    }

    @Override
    public void setAppVersion(String versionId) {
        tvVersion.setText(versionId);
    }

    @Override
    public Integer getAppVersion() {
        return ApkUtils.getVersionCode(SplashActivity.this);
    }

    @Override
    public void showUpdateDialog(String titile, String desc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titile);
        builder.setMessage(desc);
        builder.setPositiveButton("立即更新",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        appPresenter.downloadAPK();
                    }
                });
        builder.setNegativeButton("以后再说",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enterHome();
                    }
                });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
            }
        });
        builder.show();
    }

    @Override
    public void enterHome() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void createProgressDialog() {

    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        enterHome();
    }
}
