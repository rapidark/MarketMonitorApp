package com.sse.monitor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.shiki.utils.ReservoirUtils;
import com.sse.monitor.R;
import com.sse.monitor.core.BaseActivity;
import com.sse.monitor.mms.MmsConstants;
import com.sse.monitor.presenter.LoginPresenter;
import com.sse.monitor.presenter.iview.LoginView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Eric on 2016/4/27.
 */
public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.et_account) EditText etUserCode;
    @BindView(R.id.et_passwd) EditText etPasswd;
    @BindView(R.id.rl_login) RelativeLayout rlProgress;
    LoginPresenter loginPresenter;

    boolean mIsBackToGesture;

    public static Intent getCallingIntent(Context context, boolean isBackToGesture) {
        Intent callingIntent = new Intent(context, LoginActivity.class);
        callingIntent.putExtra(MmsConstants.BACK_TO_GESTRUE,isBackToGesture);
        return callingIntent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        getSupportActionBar().hide();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        mIsBackToGesture = getIntent().getBooleanExtra(MmsConstants.BACK_TO_GESTRUE,false);
        this.loginPresenter = new LoginPresenter();
        this.loginPresenter.attachView(this);
        this.loginPresenter.showUser();
    }

    @OnClick(R.id.btn_login)
    void login() {
        this.loginPresenter.login();
    }

    @Override
    public String getUserCode() {
        return etUserCode.getText().toString();
    }

    @Override
    public void setUserCode(String userCode) {
        this.etUserCode.setText(userCode);
        this.etUserCode.setSelection(userCode.length());

    }

    @Override
    public String getPasswd() {
        return etPasswd.getText().toString();
    }

    @Override
    public void setPasswd(String passwd) {
        this.etPasswd.setText(passwd);
    }

    @Override
    public void showLoginProgress() {
        this.rlProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoginProgress() {
        this.rlProgress.setVisibility(View.GONE);
    }

    @Override
    public void enterMain() {
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }

    @Override
    public void enterGestrue() {
        startActivity(GestureLockActivity.getCallingIntent(LoginActivity.this,!ReservoirUtils.getInstance().contains(MmsConstants.GESTRUE)));
        finish();
    }

    @Override
    public void onBackPressed() {
        if(mIsBackToGesture){
            enterGestrue();
        }else{
            finish();
        }
        //super.onBackPressed();
    }

    @Override
    public void onFailure(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
