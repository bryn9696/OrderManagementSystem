package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Oms implements Order {
    public int id = 0;
    public TradeExecution execution = new TradeExecution();
    final ArrayList<Trade> bidList = new ArrayList<>();
    final ArrayList<Trade> askList = new ArrayList<>();

    public String placeOrder(Trade trade) {
        id += 1;
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
        return execution.tradeExecution(trade.getId(), status);
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
        return execution.tradeExecution(trade.getId(), status);
    }

    public void addTradesToTree(Trade trade) {
        if (trade.side().equals(Side.BUY)) {
            bidList.add(trade);
            bidList.sort(Comparator.comparing(t -> trade.price()));
            System.out.println(bidList.get(0));
        } else {
            askList.add(trade);
        }
    }

    public String cancelOrder(int orderId){
        var status = Status.RESTING;
        int tradeId = -1;
        for (Trade bid : bidList) {
            if (bid.getId() == orderId) {
                tradeId = bid.getId();
                status = Status.CANCELLED;
            } else if (bid.getId() != orderId) {
                status = Status.NONE;
            }
        }

        return execution.tradeExecution(tradeId, status);
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