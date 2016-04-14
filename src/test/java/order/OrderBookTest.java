package order;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.assertEquals;

public class OrderBookTest {

    private List<Order> orders;
    private OrderBook orderBook;

    @Before
    public void setUp() throws Exception {
        orders = new CopyOnWriteArrayList<Order>(Arrays.asList(
                new Order(Direction.BUY, 232.71, Currency.USD.toString(), 200),
                new Order(Direction.SELL, 232.74, Currency.USD.toString(), 100)
        ));

        orderBook = new OrderBook();
    }

    @Test
    public void testGetCurrencyToOrdersMap() throws Exception {

        orderBook.getCurrencyToOrdersMap().put(Currency.USD.toString(), orders);

        assertEquals(orderBook.getCurrencyToOrdersMap().get(Currency.USD.toString()), orders);
    }

    @Test
    public void testOrdersFor() throws Exception {
        orderBook.getCurrencyToOrdersMap().put(Currency.USD.toString(), orders);

        orderBook.ordersFor(Currency.USD.toString());
        assertEquals(orderBook.ordersFor(Currency.USD.toString()), orders);
    }

    @After
    public void tearDown() throws Exception {
        orders.clear();
    }
}