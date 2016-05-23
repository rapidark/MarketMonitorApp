package com.sse.monitor.logistic;

import com.squareup.okhttp.ResponseBody;
import com.sse.monitor.bean.MessageBean;
import com.sse.monitor.bean.OrderBean;
import com.sse.monitor.bean.ResultBean;
import com.sse.monitor.bean.UserBean;
import com.sse.monitor.bean.VersionBean;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Eric on 2016/4/25.
 */
public interface LogisticService {

    @GET("app/getVersion")
    Observable<ResultBean<VersionBean>> getVersion();

    @GET("/wlms/apk/GalleryFinal-master.zip")
    Observable<ResponseBody> downloadAPK();

    @GET("user/oauth/{usercode}/{password}")
    Observable<ResultBean<UserBean>> login(@Path("usercode") String usercode, @Path("password") String password);

    @GET("message/{expressId}")
    Observable<ResultBean<List<MessageBean>>> getMessageList(@Path("expressId") String expressId);

    @GET("message/{expressId}/{messageId}")
    Observable<ResultBean<MessageBean>> getMessageDetail(@Path("expressId") String expressId, @Path("messageId") String messageId);

    @GET("dispatch/receipt/{expressId}")
    Observable<ResultBean<List<OrderBean>>> receiptOrderList(@Path("expressId") String expressId);
}
