package com.sse.monitor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anupcowkur.reservoir.Reservoir;
import com.shiki.gesturelock.GestureLockPreviewGroup;
import com.shiki.gesturelock.GestureLockViewGroup;
import com.shiki.utils.ReservoirUtils;
import com.sse.monitor.R;
import com.sse.monitor.core.BaseActivity;
import com.sse.monitor.mms.MmsConstants;
import com.sse.monitor.presenter.iview.GestureLockView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class GestureLockActivity extends BaseActivity implements GestureLockView, GestureLockViewGroup.OnGestureLockViewListener {
    private static final String TAG = "GestureLockActivity";

    @Bind(R.id.gesture_lock_title)
    TextView mGesturelockTitle;
    @Bind(R.id.gesture_lock_glp)
    GestureLockPreviewGroup mGesturelockGlp;
    @Bind(R.id.gesture_lock_prompt)
    TextView mGesturelockPrompt;
    @Bind(R.id.gesture_lock_gl)
    GestureLockViewGroup mGesturelockGl;
    /*@Bind(R.id.gesture_lock_forget)
    TextView mGesturelockForget;*/
    @Bind(R.id.gesture_lock_change)
    TextView mGesturelockChange;

    int mCurrentStatus;
    int mLeftChanges;
    String mFirstGesture;
    int mTooShort = 4;

    public static Intent getCallingIntent(Context context, boolean isSetGesture) {
        Intent callingIntent = new Intent(context, GestureLockActivity.class);
        if(isSetGesture){
            callingIntent.putExtra(MmsConstants.GestureLock.STATUS,MmsConstants.GestureLock.SET_GESTURE);
        }else{
            callingIntent.putExtra(MmsConstants.GestureLock.STATUS,MmsConstants.GestureLock.LOGIN);
        }
        return callingIntent;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_gesture_lock;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        getSupportActionBar().hide();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mGesturelockGl.setOnGestureLockViewListener(this);
    }

    @Override
    protected void initData() {
        mCurrentStatus = getIntent().getIntExtra(MmsConstants.GestureLock.STATUS, MmsConstants.GestureLock.SET_GESTURE);
        switch (mCurrentStatus) {
            case MmsConstants.GestureLock.LOGIN:
                showForgetAndChange();
                mGesturelockGl.setUnMatchExceedBoundary(5);
                mGesturelockTitle.setText(getResources().getText(R.string.gesture_lock_login));
                mGesturelockPrompt.setText(getResources().getText(R.string.gesture_lock_login));
                break;
            default:
                hideForgetAndChange();
                mGesturelockTitle.setText(getResources().getText(R.string.gesture_lock_prompt));
                mGesturelockPrompt.setText(getResources().getText(R.string.gesture_lock_prompt));
                mGesturelockGl.setUnMatchExceedBoundary(0);
                break;

        }
    }

    @Override
    public void onBlockSelected(int cId) {
    }

    @Override
    public void onLeftChances(int leftChances) {
        mLeftChanges = leftChances;

    }

    @Override
    public void onTotalSelected(List<Integer> chose) {
        String gesture = "";
        for (Integer i : chose) {
            gesture += i;
        }
        Log.d(TAG, "gesture:" + gesture);
        switch (mCurrentStatus) {
            case MmsConstants.GestureLock.SET_GESTURE:
            case MmsConstants.GestureLock.SET_GESTURE_AGAIN_ERROR:
            case MmsConstants.GestureLock.SET_GESTURE_TOO_SHORT:
                if(gesture.length() < mTooShort){
                    mCurrentStatus = MmsConstants.GestureLock.SET_GESTURE_TOO_SHORT;
                }else{
                    mCurrentStatus = MmsConstants.GestureLock.SET_GESTURE_AGAIN;
                    // 显示预览
                    gestureLockPreviewChange(chose);
                    // 记录手势值,将来要拆分到P中
                    mFirstGesture = gesture;
                }
                // 更新提示
                updatePrompt(mCurrentStatus);
                break;
            case MmsConstants.GestureLock.SET_GESTURE_AGAIN:
                if (mFirstGesture.equals(gesture)) {
                    ReservoirUtils.getInstance().refresh(MmsConstants.GESTRUE, gesture);
                    Toast toast = Toast.makeText(GestureLockActivity.this,getResources().getText(R.string.gesture_lock_set_done),Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                    enterMain();
                } else {
                    mCurrentStatus = MmsConstants.GestureLock.SET_GESTURE_AGAIN_ERROR;
                    updatePrompt(mCurrentStatus);
                    gestureLockPreviewReset();
                }
                break;
            case MmsConstants.GestureLock.LOGIN:
            case MmsConstants.GestureLock.LOGIN_ERROR:
                String str;
                try {
                    str = Reservoir.get(MmsConstants.GESTRUE, String.class);
                    if (gesture.equals(str)) {
                        enterMain();
                    } else {
                        if(mLeftChanges <= 0){
                            //清空手势，进入登录页面
                            Reservoir.delete(MmsConstants.GESTRUE);
                            //ReservoirUtils.getInstance().delete(MmsConstants.GESTRUE);
                            enterLogin(false);
                        }else{
                            mCurrentStatus = MmsConstants.GestureLock.LOGIN_ERROR;
                            updatePrompt(mCurrentStatus);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }

    }

    @Override
    public void showForgetAndChange() {
        //mGesturelockForget.setVisibility(View.VISIBLE);
        mGesturelockChange.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideForgetAndChange() {
        //mGesturelockForget.setVisibility(View.GONE);
        mGesturelockChange.setVisibility(View.GONE);
    }

    @Override
    public void gestureLockPreviewReset() {
        mGesturelockGlp.reset();
    }

    @Override
    public void gestureLockPreviewChange(List<Integer> chose) {
        mGesturelockGlp.changeItemMode(chose);
    }

    @Override
    public void updatePrompt(int status) {
        switch (status) {
            case MmsConstants.GestureLock.SET_GESTURE:
                mGesturelockPrompt.setText(getResources().getText(R.string.gesture_lock_prompt));
                mGesturelockPrompt.setTextColor(getResources().getColor(R.color.black));
                break;
            case MmsConstants.GestureLock.SET_GESTURE_TOO_SHORT:
                mGesturelockPrompt.setText(String.format(getResources().getString(R.string.gesture_lock_too_short), mTooShort));
                mGesturelockPrompt.setTextColor(getResources().getColor(R.color.red));
                mGesturelockGl.changeItemMode();
                break;
            case MmsConstants.GestureLock.SET_GESTURE_AGAIN:
                mGesturelockPrompt.setText(getResources().getText(R.string.gesture_lock_again));
                mGesturelockPrompt.setTextColor(getResources().getColor(R.color.black));
                break;
            case MmsConstants.GestureLock.SET_GESTURE_AGAIN_ERROR:
                mGesturelockPrompt.setText(getResources().getText(R.string.gesture_lock_again_error));
                mGesturelockPrompt.setTextColor(getResources().getColor(R.color.red));
                mGesturelockGl.changeItemMode();
                break;
            case MmsConstants.GestureLock.LOGIN_ERROR:
                mGesturelockPrompt.setText(String.format(getResources().getString(R.string.gesture_lock_login_error), mLeftChanges));
                mGesturelockPrompt.setTextColor(getResources().getColor(R.color.red));
                mGesturelockGl.changeItemMode();
                break;
            case MmsConstants.GestureLock.LOGIN:
                mGesturelockPrompt.setText(getResources().getText(R.string.gesture_lock_login));
                mGesturelockPrompt.setTextColor(getResources().getColor(R.color.black));
                break;
        }
    }

    @Override
    public void enterMain() {
        startActivity(new Intent(GestureLockActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void enterLogin(boolean isBackToGesture) {
        startActivity(LoginActivity.getCallingIntent(GestureLockActivity.this,isBackToGesture));
        finish();
    }

    @Override
    public void onFailure(String msg) {

    }

    @OnClick({R.id.gesture_lock_change})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gesture_lock_change:
                enterLogin(true);
                break;
        }
    }
}
