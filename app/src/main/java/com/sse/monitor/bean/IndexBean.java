package com.sse.monitor.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eric on 2016/10/18.
 * 大盘指数
 */
public class IndexBean implements Parcelable {
    /** 指数代码 */
    private String securityId;
    /** 指数名称 */
    private String symbol;
    /** 当前点数 */
    private double index;
    /** 当前价格 */
    private double price;
    /** 涨跌率 */
    private double rate;
    /** 成交量（手） */
    private long volume;
    /** 成交额（万元） */
    private double turnover;

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }

    public double getIndex() {
        return index;
    }

    public void setIndex(double index) {
        this.index = index;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getTurnover() {
        return turnover;
    }

    public void setTurnover(double turnover) {
        this.turnover = turnover;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.securityId);
        dest.writeString(this.symbol);
        dest.writeDouble(this.index);
        dest.writeDouble(this.price);
        dest.writeDouble(this.rate);
        dest.writeLong(this.volume);
        dest.writeDouble(this.turnover);
    }

    public IndexBean() {
    }

    protected IndexBean(Parcel in) {
        this.securityId = in.readString();
        this.symbol = in.readString();
        this.index = in.readDouble();
        this.price = in.readDouble();
        this.rate = in.readDouble();
        this.volume = in.readLong();
        this.turnover = in.readDouble();
    }

    public static final Parcelable.Creator<IndexBean> CREATOR = new Parcelable.Creator<IndexBean>() {
        @Override
        public IndexBean createFromParcel(Parcel source) {
            return new IndexBean(source);
        }

        @Override
        public IndexBean[] newArray(int size) {
            return new IndexBean[size];
        }
    };
}
