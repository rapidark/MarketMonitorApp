package com.sse.monitor.bean;

/**
 * Created by Eric on 2016/10/18.
 * 大盘指数
 */
public class IndexBean {
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
}
