package ute.fit.noithatapp.Model;

public class OrderModel {
    private int orderId;
    private int count;
    private int state;

    public OrderModel(int orderId, int count, int state) {
        this.orderId = orderId;
        this.count = count;
        this.state = state;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
