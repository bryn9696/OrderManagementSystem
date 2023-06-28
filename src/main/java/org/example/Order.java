package org.example;

public interface Order {
    public default void placeOrder(Trade trade){}

    public default void cancelOrder(int OrderId){}

}
