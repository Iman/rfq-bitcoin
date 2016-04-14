package order;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class OrderTest {

    private Order order;

    @Before
    public void setUp() throws Exception {
        this.order = new Order(Direction.BUY, 10, Currency.USD, 100);
    }

    @Test
    public void testIsBuy() throws Exception {
        assertEquals(order.isBuy(), true);
    }

    @Test
    public void testIsBuyAsFalse() throws Exception {
        Order sellOrder = new Order(Direction.SELL, 5, Currency.USD, 15);
        assertEquals(sellOrder.isBuy(), false);
    }


    @Test
    public void testGetPrice() throws Exception {
        assertEquals(order.getPrice(), 10.0d, 0.10);
    }

    @Test
    public void testGetCurrency() throws Exception {
        assertEquals(order.getCurrency(), Currency.USD);
    }

    @Test
    public void testGetAmountOfUnit() throws Exception {
        assertEquals(order.getAmountOfUnit(), 100);
    }
}