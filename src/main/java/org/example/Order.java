package org.example;

public interface Order {
    public default String placeOrder(Trade trade){
        return null;
    }

    public default String cancelOrder(int OrderId){
        return null;
    }

}
