package com.sse.monitor.mms;

/**
 * Created by Eric on 2016/10/11.
 */
public class MmsConstants {
    public static final String ACCOUNT = "Account";
    public static final String GESTRUE = "Gesture";

    public static final String BACK_TO_GESTRUE = "BackToGesture";

    public class GestureLock{
        public static final int SET_GESTURE = 0;
        public static final int SET_GESTURE_TOO_SHORT = 1;
        public static final int SET_GESTURE_AGAIN = 2;
        public static final int SET_GESTURE_AGAIN_ERROR = 3;
        public static final int LOGIN = 4;
        public static final int LOGIN_ERROR = 5;
        public static final String STATUS = "GestureLockStatus";
    }
}
