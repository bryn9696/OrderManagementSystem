package org.example;

public class Trade {
    private final double price;
    private final long size;
    private final Side side;

    public Trade (double price, long size, Side side){
        this.price = price;
        this. size =size;
        this.side = side;
    }

}