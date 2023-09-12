package org.example;

public class Trade {
    private final int id;
    private final double price;
    private final long size;
    private final Side side;
    private Status status;

    public Trade (int id, double price, long size, Side side){
        this.id = id;
        this.price = price;
        this.size = size;
        this.side = side;
//        this.status = status;
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

    public Status getStatus() {
        return status;
    }

    public Status setStatus(Status status) { return this.status = status; }
}
