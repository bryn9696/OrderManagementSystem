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
    void askAccepted(){
        var tradeBid = new Trade(99.5, 100, Side.BUY);
        var tradeAsk = new Trade(99.5, 100, Side.ASK);
        var tradeBid1 = new Trade(99.5, 100, Side.BUY);
        var tradeAsk1 = new Trade(99.5, 100, Side.ASK);
        var tradeBidOne = oms.placeOrder(tradeBid);
        var tradeAskOne = oms.placeOrder(tradeAsk);
        var tradeBidTwo = oms.placeOrder(tradeBid1);
        var tradeAskTwo = oms.placeOrder(tradeAsk1);
        assertEquals("2, FILLED", tradeAskOne);
        assertEquals("4, FILLED", tradeAskTwo);
    }

    @Test
    @DisplayName("A crossing BID will be accepted and return id, status")
    void bidAccepted(){
        var tradeBid = new Trade(99.5, 100, Side.BUY);
        var tradeAsk = new Trade(99.5, 100, Side.ASK);
        var tradeBid1 = new Trade(99.5, 100, Side.BUY);
        var tradeAsk1 = new Trade(99.5, 100, Side.ASK);
        var tradeAskOne = oms.placeOrder(tradeAsk);
        var tradeBidOne = oms.placeOrder(tradeBid);
        var tradeAskTwo = oms.placeOrder(tradeAsk1);
        var tradeBidTwo = oms.placeOrder(tradeBid1);

        assertEquals("2, FILLED", tradeBidOne);
        assertEquals("4, FILLED", tradeBidTwo);
    }

    @Test
    @DisplayName("A non-crossing ASK will be requested and return id, status")
    void askRequested(){
        var tradeBid = new Trade(99.5, 0, Side.BUY);
        var tradeAsk = new Trade(99.5, 100, Side.ASK);
        var tradeBid1 = new Trade(99.5, 0, Side.BUY);
        var tradeAsk1 = new Trade(99.5, 100, Side.ASK);
        var tradeBidOne = oms.placeOrder(tradeBid);
        var tradeAskOne = oms.placeOrder(tradeAsk);
        var tradeBidTwo = oms.placeOrder(tradeBid1);
        var tradeAskTwo = oms.placeOrder(tradeAsk1);

        assertEquals("2, RESTING", tradeAskOne);
        assertEquals("4, RESTING", tradeAskTwo);}

    @Test
    @DisplayName("A non-crossing BID will be requested and return id, status")
    void bidRequested(){
        var tradeBid = new Trade(99.5, 100, Side.BUY);
        var tradeAsk = new Trade(99.5, 0, Side.ASK);
        var tradeBid1 = new Trade(99.5, 100, Side.BUY);
        var tradeAsk1 = new Trade(99.5, 0, Side.ASK);
        var tradeAskOne = oms.placeOrder(tradeAsk);
        var tradeBidOne = oms.placeOrder(tradeBid);
        var tradeAskTwo = oms.placeOrder(tradeAsk1);
        var tradeBidTwo = oms.placeOrder(tradeBid1);

        assertEquals("2, RESTING", tradeBidOne);
        assertEquals("4, RESTING", tradeBidTwo);
    }

    @Test
    @DisplayName("An ASK with a greater quantity the Bid will be Partially filled and return id, status")
    void askPartial(){}

    @Test
    @DisplayName("A BID with a greater quantity the Ask will be Partially filled and return id, status")
    void bidPartial(){}

    @Test
    @DisplayName("Cancelling a non existing trade will return none")
    void cancelNonExistingBid(){}

    @Test
    @DisplayName("Cancelling a existing trade will return cancelled and return id, status")
    void cancelExistingBid(){}

}
