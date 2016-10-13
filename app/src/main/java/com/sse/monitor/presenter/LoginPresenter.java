package com.sse.monitor.presenter;

import com.anupcowkur.reservoir.Reservoir;
import com.shiki.utils.ReservoirUtils;
import com.shiki.utils.coder.MD5Coder;
import com.sse.monitor.bean.UserBean;
import com.sse.monitor.core.mvp.BasePresenter;
import com.sse.monitor.mms.MmsConstants;
import com.sse.monitor.model.impl.LoginModel;
import com.sse.monitor.presenter.iview.LoginView;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Eric on 2016/4/29.
 */
public class LoginPresenter extends BasePresenter<LoginView> {
    private LoginModel loginModel;

    public LoginPresenter() {
        this.loginModel = LoginModel.getInstance();
    }

    public void showUser() {
        this.mCompositeSubscription.add(Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        try {
                            if (Reservoir.contains(MmsConstants.ACCOUNT)) {
                                String account = String.valueOf(Reservoir.get(MmsConstants.ACCOUNT, String.class));
                                subscriber.onNext(account);
                            }
                            subscriber.onCompleted();
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LoginPresenter.this.mCompositeSubscription.remove(this);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(String account) {
                        LoginPresenter.this.getMvpView().setUserCode(account);
                    }
                }));
    }

    public void login() {
        final String usercode = this.getMvpView().getUserCode();
        final String passwd = this.getMvpView().getPasswd();
        //final String passwd = MD5Coder.getMD5Code(this.getMvpView().getPasswd());
        if(usercode.equals("admin")&&passwd.equals("123456")){
            ReservoirUtils.getInstance().refresh(MmsConstants.ACCOUNT,usercode);
            if(ReservoirUtils.getInstance().contains(MmsConstants.GESTRUE)){
                LoginPresenter.this.getMvpView().enterMain();
            }else{
                LoginPresenter.this.getMvpView().enterGestrue();
            }

        }else{
            LoginPresenter.this.getMvpView().hideLoginProgress();
            LoginPresenter.this.getMvpView().onFailure("用户名密码错误");
        }

        //final Boolean isRemember = this.getMvpView().getRemember();
        /*this.mCompositeSubscription.add(loginModel.login(usercode, passwd)
                .flatMap(new Func1<ResultBean<UserBean>, Observable<String>>() {
                    @Override
                    public Observable<String> call(final ResultBean<UserBean> resultBean) {
                        if (resultBean.getStatusCode().equals(LogisticApi.FAILURE_DATA)) {
                            return Observable.just(resultBean.getStatusMessage());
                        } else {
                            return Observable.create(new Observable.OnSubscribe<String>() {
                                @Override
                                public void call(Subscriber<? super String> subscriber) {
                                    try {
                                        //resultBean.getResultData().setIsRemember(isRemember);
                                        ReservoirUtils.getInstance().refresh("userInfo", resultBean.getResultData());
                                        subscriber.onNext(null);
                                        subscriber.onCompleted();
                                    } catch (Exception e) {
                                        subscriber.onError(e);
                                    }
                                }
                            });
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        LoginPresenter.this.getMvpView().showLoginProgress();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LoginPresenter.this.mCompositeSubscription.remove(this);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LoginPresenter.this.getMvpView().hideLoginProgress();
                        LoginPresenter.this.getMvpView().onFailure(e.toString());
                    }

                    @Override
                    public void onNext(String resultError) {
                        if (resultError != null) {
                            LoginPresenter.this.getMvpView().hideLoginProgress();
                            LoginPresenter.this.getMvpView().onFailure(resultError);
                        } else {
                            LoginPresenter.this.getMvpView().enterMain();
                        }
                    }
                }));*/
    }
}
