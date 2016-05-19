package com.sse.monitor.logistic;

/**
 * Created by Maik on 2016/4/19.
 */
public class LogisticApi {
    public static final String BASE_URL = "http://10.0.3.2:8080/wlms/api/";
    public static final String LOGISTIC_DATA_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public static final String SUCCESS_DATA = "00001";
    public static final String FAILURE_DATA = "00000";

    public static final int INTENT_REQUEST_MESSAGE_CODE = 10001;
    public static final String INTENT_EXTRA_PARAM_MESSAGE = "org.shiki.INTENT_PARAM_MESSAGE_ID";
}
