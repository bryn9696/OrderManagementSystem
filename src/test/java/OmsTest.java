import org.example.Oms;
import org.example.Side;
import org.example.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OmsTest {
    Oms oms;
    @BeforeEach
    void newOms() {
        this.oms = new Oms();
    }

    @Test
    @DisplayName("A crossing ASK will be accepted and return id, status")
    void askAccepted() {
        var tradeBid = new Trade(0, 99.5, 100, Side.BUY);
        var tradeAsk = new Trade(1, 99.5, 100, Side.ASK);
        var tradeBid1 = new Trade(2, 83.5, 100, Side.BUY);
        var tradeAsk1 = new Trade(3, 83.5, 100, Side.ASK);

        // Place trades using the binary search tree-based OMS
        var tradeBidOne = oms.placeOrder(tradeBid);
        var tradeAskOne = oms.placeOrder(tradeAsk);
        var tradeBidTwo = oms.placeOrder(tradeBid1);
        var tradeAskTwo = oms.placeOrder(tradeAsk1);

        // Check the expected results
        assertEquals("0, FILLED", tradeAskOne);
        assertEquals("2, FILLED", tradeAskTwo);
    }

    @Test
    @DisplayName("A crossing BID will be accepted and return id, status")
    void bidAccepted(){
        var tradeBid = new Trade(1,99.5, 100, Side.BUY);
        var tradeAsk = new Trade(0,99.5, 100, Side.ASK);
        var tradeBid1 = new Trade(2,99.5, 100, Side.BUY);
        var tradeAsk1 = new Trade(3,99.5, 100, Side.ASK);
        var tradeAskOne = oms.placeOrder(tradeAsk);
        var tradeBidOne = oms.placeOrder(tradeBid);
        var tradeAskTwo = oms.placeOrder(tradeAsk1);
        var tradeBidTwo = oms.placeOrder(tradeBid1);


        assertEquals("0, FILLED", tradeBidOne);
        assertEquals("0, FILLED", tradeBidTwo);
    }

    @Test
    @DisplayName("A non-crossing ASK will be requested and return id, status")
    void askRequested(){
        var tradeBid = new Trade(0,99.5, 0, Side.BUY);
        var tradeAsk = new Trade(1,99.5, 100, Side.ASK);
        var tradeBid1 = new Trade(2,99.5, 0, Side.BUY);
        var tradeAsk1 = new Trade(3,99.5, 100, Side.ASK);
        var tradeBidOne = oms.placeOrder(tradeBid);
        var tradeAskOne = oms.placeOrder(tradeAsk);
        var tradeBidTwo = oms.placeOrder(tradeBid1);
        var tradeAskTwo = oms.placeOrder(tradeAsk1);

        assertEquals("1, RESTING", tradeAskOne);
        assertEquals("3, RESTING", tradeAskTwo);
    }

    @Test
    @DisplayName("A non-crossing BID will be requested and return id, status")
    void bidRequested(){
        var tradeBid = new Trade(1,99.5, 100, Side.BUY);
        var tradeAsk = new Trade(0,99.5, 0, Side.ASK);
        var tradeBid1 = new Trade(3,99.5, 100, Side.BUY);
        var tradeAsk1 = new Trade(2,99.5, 0, Side.ASK);
        var tradeAskOne = oms.placeOrder(tradeAsk);
        var tradeBidOne = oms.placeOrder(tradeBid);
        var tradeAskTwo = oms.placeOrder(tradeAsk1);
        var tradeBidTwo = oms.placeOrder(tradeBid1);

        assertEquals("1, RESTING", tradeBidOne);
        assertEquals("3, RESTING", tradeBidTwo);
    }

    @Test
    @DisplayName("An ASK with a greater quantity the Bid will be Partially filled and return id, status")
    void askPartial(){
        var tradeBid = new Trade(0,99.5, 50, Side.BUY);
        var tradeAsk = new Trade(1,99.5, 100, Side.ASK);
        var tradeBid1 = new Trade(2,99.5, 50, Side.BUY);
        var tradeAsk1 = new Trade(3,99.5, 100, Side.ASK);
        var tradeBidOne = oms.placeOrder(tradeBid);
        var tradeAskOne = oms.placeOrder(tradeAsk);
        var tradeBidTwo = oms.placeOrder(tradeBid1);
        var tradeAskTwo = oms.placeOrder(tradeAsk1);

        assertEquals("0, PARTIAL", tradeAskOne);
        assertEquals("0, PARTIAL", tradeAskTwo);
    }

    @Test
    @DisplayName("A BID with a greater quantity the Ask will be Partially filled and return id, status")
    void bidPartial(){
        var tradeBid = new Trade(1,99.5, 150, Side.BUY);
        var tradeAsk = new Trade(0,99.5, 50, Side.ASK);
        var tradeBid1 = new Trade(3,99.5, 100, Side.BUY);
        var tradeAsk1 = new Trade(2,99.5, 250, Side.ASK);
        var tradeAskOne = oms.placeOrder(tradeAsk);
        var tradeBidOne = oms.placeOrder(tradeBid);
        var tradeAskTwo = oms.placeOrder(tradeAsk1);
        var tradeBidTwo = oms.placeOrder(tradeBid1);

        assertEquals("0, PARTIAL", tradeBidOne);
        assertEquals("0, PARTIAL", tradeBidTwo);
    }

    @Test
    @DisplayName("Cancelling a non existing trade will return none")
    void cancelNonExistingBid(){
        var tradeBid = new Trade(0, 99.5, 150, Side.BUY);
        var tradeBid1 = new Trade(1, 99.5, 50, Side.BUY);
        var tradeBid2 = new Trade(2, 99.5, 100, Side.BUY);
        var tradeBidOne = oms.placeOrder(tradeBid);
        var tradeBidTwo = oms.placeOrder(tradeBid1);
        var tradeBidThree = oms.placeOrder(tradeBid2);

        var cancel = oms.cancelOrder(4);

        assertEquals("4, NONE", cancel);
    }

    @Test
    @DisplayName("Cancelling an existing trade will return cancelled and return id, status")
    void cancelExistingBid(){
        var tradeBid = new Trade(0, 99.5, 150, Side.BUY);
        var tradeBid1 = new Trade(1, 99.5, 50, Side.BUY);
        var tradeBid2 = new Trade(2, 99.5, 100, Side.BUY);
        var tradeBidOne = oms.placeOrder(tradeBid);
        var tradeBidTwo = oms.placeOrder(tradeBid1);
        var tradeBidThree = oms.placeOrder(tradeBid2);

        var cancel = oms.cancelOrder(0);

        assertEquals("0, CANCELLED", cancel);
    }

}
