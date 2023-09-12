package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
//public class Oms implements Order {
//    public int id = 0;
//    public TradeExecution execution = new TradeExecution();
//    final ArrayList<Trade> bidList = new ArrayList<>();
//    final ArrayList<Trade> askList = new ArrayList<>();
//
//    public String placeOrder(Trade trade) {
//        id += 1;
//        addTradesToTree(trade);
//        if (trade.side().equals(Side.BUY)) {
//            return searchAskTree(trade, askList);
//        } else {
//            return searchBidTree(trade, bidList);
//        }
//    }
//
//    public String searchBidTree(Trade trade, ArrayList<Trade> bidList) {
//        var status = Status.RESTING;
//        for (Trade ask : bidList) {
//            if (ask.size() == trade.size()) {
//                status = Status.FILLED;
//            } else if (ask.size() < trade.size() && ask.size() != 0) {
//                status = Status.PARTIAL;
//            }
//        }
//        return execution.tradeExecution(trade.getId(), status);
//    }
//
//    public String searchAskTree(Trade trade, ArrayList<Trade> askList) {
//        // use binary search tree?
//        var status = Status.RESTING;
//        for (Trade bid : askList) {
//            if (bid.size() == trade.size()) {
//                status = Status.FILLED;
//            } else if (bid.size() < trade.size() && bid.size() != 0) {
//                status = Status.PARTIAL;
//            }
//        }
//        return execution.tradeExecution(trade.getId(), status);
//    }
//
//    public void addTradesToTree(Trade trade) {
//        if (trade.side().equals(Side.BUY)) {
//            bidList.add(trade);
//            bidList.sort(Comparator.comparing(t -> trade.price()));
//            System.out.println(bidList.get(0));
//        } else {
//            askList.add(trade);
//        }
//    }
//
//    public String cancelOrder(int orderId){
//        var status = Status.RESTING;
//        int tradeId = -1;
//        for (Trade bid : bidList) {
//            if (bid.getId() == orderId) {
//                tradeId = bid.getId();
//                status = Status.CANCELLED;
//            } else if (bid.getId() != orderId) {
//                status = Status.NONE;
//            }
//        }
//
//        return execution.tradeExecution(tradeId, status);
//    }
//    public static void main(String[] args) {
//        // Press Opt+Enter with your caret at the highlighted text to see how
//        // IntelliJ IDEA suggests fixing it.
//        System.out.printf("Hello and welcome!");
//
//        for (int i = 1; i <= 5; i++) {
//
//            System.out.println("i = " + i);
//        }
//    }
//}

public class Oms implements Order {
    private int id = 0;
    private TradeExecution execution = new TradeExecution();
    private TradeNode bidRoot; // Binary search tree root for bids
    private TradeNode askRoot; // Binary search tree root for asks

    public String placeOrder(Trade trade) {
        id += 1;
        trade.setStatus(Status.NONE);
        if (trade.side() == Side.BUY) {
            bidRoot = addTradeToTree(trade, bidRoot);
        } else {
            askRoot = addTradeToTree(trade, askRoot);
        }
        return determineResponse(trade);
    }

    private String determineResponse(Trade trade) {
        // Implement logic to determine the response based on the trade placement
        // You can use searchBidTree and searchAskTree methods to check for fills or rests
        if (trade.side() == Side.BUY) {
            return searchAskTree(trade); // Modify as needed
        } else {
            return searchBidTree(trade); // Modify as needed
        }
    }

    private TradeNode addTradeToTree(Trade trade, TradeNode root) {
        if (root == null) {
            return new TradeNode(trade);
        }

        if (trade.price() <= root.trade.price()) {
            root.left = addTradeToTree(trade, root.left);
        } else {
            root.right = addTradeToTree(trade, root.right);
        }

        return root; // Return the modified root
    }

    public String searchBidTree(Trade trade) {
        TradeNode result = searchTree(trade, bidRoot);
        if (result != null) {
            if (result.trade.size() == trade.size()) {
                return execution.tradeExecution(result.trade.getId(), Status.FILLED);
            } else if (result.trade.size() < trade.size() && result.trade.size() != 0) {
                return execution.tradeExecution(result.trade.getId(), Status.PARTIAL);
            }
        }
        return execution.tradeExecution(trade.getId(), Status.RESTING);
    }

    public String searchAskTree(Trade trade) {
        TradeNode result = searchTree(trade, askRoot);
        if (result != null) {
            if (result.trade.size() == trade.size()) {
                return execution.tradeExecution(result.trade.getId(), Status.FILLED);
            } else if (result.trade.size() < trade.size() && result.trade.size() != 0) {
                return execution.tradeExecution(result.trade.getId(), Status.PARTIAL);
            }
        }
        return execution.tradeExecution(trade.getId(), Status.RESTING);
    }

    private TradeNode searchTree(Trade trade, TradeNode root) {
        if (root == null) {
            return null;
        }
        if (trade.price() == root.trade.price()) {
            return root;
        }
        if (trade.price() < root.trade.price()) {
            return searchTree(trade, root.left);
        } else {
            return searchTree(trade, root.right);
        }
    }

    public String cancelOrder(Trade tradeToCancel) {
        int orderIdToCancel = tradeToCancel.getId();
        return cancelOrder(orderIdToCancel);
    }

    public String cancelOrder(int orderIdToCancel) {
        // Search for the trade with the specified order ID in the binary search tree
        TradeNode tradeNode = searchOrderById(orderIdToCancel, bidRoot);

        if (tradeNode != null) {
            // The trade with the specified order ID exists, mark it as CANCELLED
            tradeNode.trade.setStatus(Status.CANCELLED);
            return execution.tradeExecution(orderIdToCancel, Status.CANCELLED);
        } else {
            // The trade with the specified order ID does not exist, return NONE
            return execution.tradeExecution(orderIdToCancel, Status.NONE);
        }
    }

    private TradeNode searchOrderById(int orderId, TradeNode root) {
        if (root == null) {
            return null; // Order not found
        }

        if (orderId < root.trade.getId()) {
            // Search in the left subtree
            return searchOrderById(orderId, root.left);
        } else if (orderId > root.trade.getId()) {
            // Search in the right subtree
            return searchOrderById(orderId, root.right);
        } else {
            // Found the order with the specified ID
            return root;
        }
    }

    private static class TradeNode {
        Trade trade;
        TradeNode left;
        TradeNode right;

        TradeNode(Trade trade) {
            this.trade = trade;
            this.left = null;
            this.right = null;
        }
    }
}