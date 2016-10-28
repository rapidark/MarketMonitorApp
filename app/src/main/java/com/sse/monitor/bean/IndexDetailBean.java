package com.sse.monitor.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eric on 2016/10/25.
 */
public class IndexDetailBean implements Parcelable {
    /** 代码 */
    private String securityId;
    /** 名称 */
    private String symbol;
    /** 今日开盘价 */
    private float openPrice;
    /** 昨日收盘价 */
    private float closePrice;
    /** 振幅 */
    private float amplitude;
    /** 当前点数 */
    private float index;
    /** 涨跌 */
    private float price;
    /** 涨跌百分比 */
    private float rate;
    /** 最高成交价 */
    private float maxPrice;
    /** 最低成交价 */
    private float minPrice;
    /** 成交额 */
    private double turnover;
    /** 成交量 */
    private double volume;

    public float getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(float amplitude) {
        this.amplitude = amplitude;
    }

    public float getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(float closePrice) {
        this.closePrice = closePrice;
    }

    public float getIndex() {
        return index;
    }

    public void setIndex(float index) {
        this.index = index;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public float getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(float openPrice) {
        this.openPrice = openPrice;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
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
        dest.writeString(this.securityId);
        dest.writeString(this.symbol);
        dest.writeFloat(this.openPrice);
        dest.writeFloat(this.closePrice);
        dest.writeFloat(this.amplitude);
        dest.writeFloat(this.index);
        dest.writeFloat(this.price);
        dest.writeFloat(this.rate);
        dest.writeFloat(this.maxPrice);
        dest.writeFloat(this.minPrice);
        dest.writeDouble(this.turnover);
        dest.writeDouble(this.volume);
    }

    public IndexDetailBean() {
    }

    protected IndexDetailBean(Parcel in) {
        this.securityId = in.readString();
        this.symbol = in.readString();
        this.openPrice = in.readFloat();
        this.closePrice = in.readFloat();
        this.amplitude = in.readFloat();
        this.index = in.readFloat();
        this.price = in.readFloat();
        this.rate = in.readFloat();
        this.maxPrice = in.readFloat();
        this.minPrice = in.readFloat();
        this.turnover = in.readDouble();
        this.volume = in.readDouble();
    }

    public static final Parcelable.Creator<IndexDetailBean> CREATOR = new Parcelable.Creator<IndexDetailBean>() {
        @Override
        public IndexDetailBean createFromParcel(Parcel source) {
            return new IndexDetailBean(source);
        }

        @Override
        public IndexDetailBean[] newArray(int size) {
            return new IndexDetailBean[size];
        }
    };
}
