package com.sse.monitor.bean;

/**
 * Created by Eric on 2016/5/16.
 */
public class OrderBean {
    private String ordCode;
    private String deliveryCode;
    private String logisticCode;
    private String deliveryAddr;
    private String recipient;
    private String exprItem;
    private String appointment;
    private String deliveryFlag;
    private String ordTel;
    private String ordRemark;
    private Boolean isSelected;

    public String getOrdCode() {
        return ordCode;
    }
    public void setOrdCode(String ordCode) {
        this.ordCode = ordCode;
    }
    public String getDeliveryCode() {
        return deliveryCode;
    }
    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }
    public String getLogisticCode() {
        return logisticCode;
    }
    public void setLogisticCode(String logisticCode) {
        this.logisticCode = logisticCode;
    }
    public String getDeliveryAddr() {
        return deliveryAddr;
    }
    public void setDeliveryAddr(String deliveryAddr) {
        this.deliveryAddr = deliveryAddr;
    }
    public String getRecipient() {
        return recipient;
    }
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    public String getExprItem() {
        return exprItem;
    }
    public void setExprItem(String exprItem) {
        this.exprItem = exprItem;
    }
    public String getAppointment() {
        return appointment;
    }
    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }
    public String getDeliveryFlag() {
        return deliveryFlag;
    }
    public void setDeliveryFlag(String deliveryFlag) {
        this.deliveryFlag = deliveryFlag;
    }
    public String getOrdTel() {
        return ordTel;
    }
    public void setOrdTel(String ordTel) {
        this.ordTel = ordTel;
    }
    public String getOrdRemark() {
        return ordRemark;
    }
    public void setOrdRemark(String ordRemark) {
        this.ordRemark = ordRemark;
    }
    public Boolean getIsSelected() {
        return isSelected;
    }
    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }
}
