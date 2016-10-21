package com.sse.monitor.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

import com.sse.monitor.R;
import com.sse.monitor.core.BaseActivity;
import com.sse.monitor.di.HasComponent;
import com.sse.monitor.di.components.DaggerMainComponent;
import com.sse.monitor.di.components.MainComponent;
import com.sse.monitor.mms.MmsConstants;
import com.sse.monitor.presenter.MainPresenter;
import com.sse.monitor.presenter.iview.MainView;
import com.sse.monitor.ui.adapter.MainViewPagerAdapter;
import com.sse.monitor.ui.fragment.HomeFragment;
import com.sse.monitor.ui.fragment.MarketFragment;
import com.sse.monitor.ui.fragment.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Eric on 2016/5/5.
 */
public class MainActivity extends BaseActivity implements MainView,HasComponent<MainComponent> {
    @BindView(R.id.vp_main) ViewPager vpMain;
    @BindView(R.id.rg_tabbar) RadioGroup rgTab;
    private List<Fragment> fgContent;
    private MainComponent mainComponent;

    MainPresenter mMainPresenter;

    private void initializeInjector() {
        this.mainComponent = DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initializeInjector();
    }

    @Override
    protected void initListeners() {
        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rgTab.check(R.id.rb_home);
                        break;
                    case 1:
                        rgTab.check(R.id.rb_market);
                        break;
                    case 2:
                        rgTab.check(R.id.rb_profile);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        vpMain.setCurrentItem(0, false);
                        break;
                    case R.id.rb_market:
                        vpMain.setCurrentItem(1, false);
                        break;
                    case R.id.rb_profile:
                        vpMain.setCurrentItem(2, false);
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {
        fgContent = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        MarketFragment marketFragment = new MarketFragment();
        ProfileFragment profileFragment = new ProfileFragment();
        fgContent.add(homeFragment);
        fgContent.add(marketFragment);
        fgContent.add(profileFragment);
        vpMain.setAdapter(new MainViewPagerAdapter(
                getSupportFragmentManager(), fgContent));
        vpMain.setCurrentItem(0);
        rgTab.check(R.id.rb_home);
    }

    @Override
    public MainComponent getComponent() {
        return mainComponent;
    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.mMainPresenter = new MainPresenter();
        this.mMainPresenter.attachView(this);
        String list = "s_" + MmsConstants.SH_000001
                + "," + "s_" + MmsConstants.SZ_399001
                + "," + "s_" + MmsConstants.SZ_399006
                + "," + "s_" + MmsConstants.SH_000300
                + "," + "s_" + MmsConstants.SH_000016
                + "," + "s_" + MmsConstants.SH_000905;
        //this.mMainPresenter.loadIndexData(list);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //this.mMainPresenter.stopLoadIndexData();
    }
}
