package org.example;

public interface Order {
    public default String placeOrder(Trade trade){
        return null;
    }

    public default void cancelOrder(int OrderId){}

}
