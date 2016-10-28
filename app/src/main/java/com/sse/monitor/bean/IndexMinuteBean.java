package com.sse.monitor.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eric on 2016/10/25.
 */
public class IndexMinuteBean implements Parcelable {
    /** 时间 */
    private String time;
    /** 当前点数 */
    private float index;
    /** 成交量 */
    private double volume;

    public float getIndex() {
        return index;
    }

    public void setIndex(float index) {
        this.index = index;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.time);
        dest.writeFloat(this.index);
        dest.writeDouble(this.volume);
    }

    public IndexMinuteBean() {
    }

    protected IndexMinuteBean(Parcel in) {
        this.time = in.readString();
        this.index = in.readFloat();
        this.volume = in.readDouble();
    }

    public static final Creator<IndexMinuteBean> CREATOR = new Creator<IndexMinuteBean>() {
        @Override
        public IndexMinuteBean createFromParcel(Parcel source) {
            return new IndexMinuteBean(source);
        }

        @Override
        public IndexMinuteBean[] newArray(int size) {
            return new IndexMinuteBean[size];
        }
    };
}
