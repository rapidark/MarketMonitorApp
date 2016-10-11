package com.sse.monitor.presenter.iview;

import com.sse.monitor.core.mvp.MvpView;

import java.util.List;

/**
 * Created by Eric on 2016/10/11.
 */
public interface GestureLockView extends MvpView {
    void showForgetAndChange();
    void hideForgetAndChange();
    void gestureLockPreviewReset();
    void gestureLockPreviewChange(List<Integer> chose);
    void updatePrompt(int status);
    void enterMain();

}
