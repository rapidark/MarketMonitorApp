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
public class LogisticMain {
    private static LogisticMain instance;

    private LogisticService logisticService;


    public static LogisticMain getInstance() {
        if (instance == null) instance = new LogisticMain();
        return instance;
    }


    private LogisticMain() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(7000, TimeUnit.MILLISECONDS);

        /*
         * 查看网络请求发送状况
         */
        if (MonitorApplication.getInstance().log) {
            okHttpClient.interceptors().add(new LoggingInterceptor());
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(LogisticApi.BASE_URL)
                .addCallAdapterFactory(
                        RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(
                        MonitorApplication.getInstance().gson))
                .client(okHttpClient)
                .build();
        this.logisticService = retrofit.create(LogisticService.class);
    }

    public LogisticService getLogisticService() {
        return logisticService;
    }
}
