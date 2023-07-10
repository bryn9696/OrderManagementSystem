package org.example;

public class Trade {
    private final int id;
    private final double price;
    private final long size;
    private final Side side;

    public Trade (int id, double price, long size, Side side){
        this.id = id;
        this.price = price;
        this.size = size;
        this.side = side;
    }

    public double price() {
        return price;
    }

    public long size() {
        return size;
    }

    public Side side() {
        return side;
    }

    public int getId() {
        return id;
    }
}
