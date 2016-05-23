package com.sse.monitor.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sse.monitor.di.HasComponent;

import butterknife.ButterKnife;

/**
 * Created by Eric on 2016/5/6.
 */
public abstract class BaseFragment extends Fragment {
    protected View self;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (this.self == null) {
            this.self = inflater.inflate(this.getLayoutId(), container, false);
        }
        if (this.self.getParent() != null) {
            ViewGroup parent = (ViewGroup) this.self.getParent();
            parent.removeView(this.self);
        }
        ButterKnife.bind(this, this.self);
        this.initViews(this.self, savedInstanceState);
        this.initData(savedInstanceState);
        this.initListeners();
        return this.self;
    }

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract int getLayoutId();

    protected abstract void initViews(View self, Bundle savedInstanceState);

    protected abstract void initListeners();

    @SuppressWarnings("unchecked")
    protected <V extends View> V findView(int id) {
        return (V) this.self.findViewById(id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}
