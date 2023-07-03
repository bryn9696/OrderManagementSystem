package org.example;

import java.util.ArrayList;
import java.util.Arrays;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Oms implements Order {
    public int id = 0;
    public TradeExecution execution = new TradeExecution();
    final ArrayList<Trade> bidList = new ArrayList<>();
    final ArrayList<Trade> askList = new ArrayList<>();

    public String placeOrder(Trade trade) {
        id += 1;
        System.out.println(trade.size() +"-"+ trade.price()+"-"+trade.side());
        addTradesToTree(trade);
        if (trade.side().equals(Side.BUY)) {
            return searchAskTree(trade, askList);
        } else {
            return searchBidTree(trade, bidList);
        }
    }

    public String searchBidTree(Trade trade, ArrayList<Trade> bidList) {
        var status = Status.RESTING;
        for (Trade ask : bidList) {
            if (ask.size() == trade.size()) {
                status = Status.FILLED;
            } else if (ask.size() < trade.size() && ask.size() != 0) {
                status = Status.PARTIAL;
            }
        }
        return execution.tradeExecution(id, status);
    }

    public String searchAskTree(Trade trade, ArrayList<Trade> askList) {
        // use binary search tree?
        var status = Status.RESTING;
        for (Trade bid : askList) {
            if (bid.size() == trade.size()) {
                status = Status.FILLED;
            } else if (bid.size() < trade.size() && bid.size() != 0) {
                status = Status.PARTIAL;
            }
        }
        return execution.tradeExecution(id, status);
    }

    public void addTradesToTree(Trade trade) {

        if (trade.side().equals(Side.BUY)) {
            bidList.add(trade);
        } else {
            askList.add(trade);
        }
    }

    public void cancelOrder(int orderId){

    }
    public static void main(String[] args) {
        // Press Opt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        // Press Ctrl+R or click the green arrow button in the gutter to run the code.
        for (int i = 1; i <= 5; i++) {

            // Press Ctrl+D to start debugging your code. We have set one breakpoint
            // for you, but you can always add more by pressing Cmd+F8.
            System.out.println("i = " + i);
        }
    }
}