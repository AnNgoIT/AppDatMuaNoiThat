package ute.fit.noithatapp.Model;

import java.util.Date;

public class OrderModel {
    private int orderId;
    private int count;
    private String state;
    private Date date;
    private String address;

    public OrderModel(int orderId, int count, String state, Date date, String address) {
        this.orderId = orderId;
        this.count = count;
        this.state = state;
        this.date = date;
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
