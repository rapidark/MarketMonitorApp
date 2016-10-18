package com.sse.monitor.mms;

import com.squareup.okhttp.OkHttpClient;
import com.sse.monitor.MonitorApplication;

import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by Eric on 2016/4/28.
 */
public class MmsMain {
    private static MmsMain instance;

    private MmsService mMmsService;


    public static MmsMain getInstance() {
        if (instance == null) instance = new MmsMain();
        return instance;
    }


    private MmsMain() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(7000, TimeUnit.MILLISECONDS);

        /*
         * 查看网络请求发送状况
         */
        if (MonitorApplication.getInstance().log) {
            okHttpClient.interceptors().add(new LoggingInterceptor());
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(MmsApi.BASE_URL)
                .addCallAdapterFactory(
                        RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(
                        MonitorApplication.getInstance().gson))
                .client(okHttpClient)
                .build();
        this.mMmsService = retrofit.create(MmsService.class);
    }

    public MmsService getMmsService() {
        return mMmsService;
    }
}
