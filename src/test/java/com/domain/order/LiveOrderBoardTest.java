package com.domain.order;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.assertEquals;

public class LiveOrderBoardTest {

    private List<Order> orders;
    private LiveOrderBoardMock liveOrderBoardMock;

    @Before
    public void setUp() throws Exception {
        orders = new CopyOnWriteArrayList<Order>(Arrays.asList(
                new Order(Direction.BUY, 232.71, Currency.USD.toString(), 200),
                new Order(Direction.SELL, 232.74, Currency.USD.toString(), 100),
                new Order(Direction.SELL, 232.73, Currency.USD.toString(), 200)
        ));

        liveOrderBoardMock = new LiveOrderBoardMock();
    }

    @Test
    public void testGetCurrencyToOrdersMap() throws Exception {

        liveOrderBoardMock.getCurrencyToOrdersMap().put(Currency.USD.toString(), orders);

        assertEquals(liveOrderBoardMock.getCurrencyToOrdersMap().get(Currency.USD.toString()), orders);
    }

    @Test
    public void testOrdersFor() throws Exception {
        liveOrderBoardMock.getCurrencyToOrdersMap().put(Currency.USD.toString(), orders);

        liveOrderBoardMock.ordersFor(Currency.USD.toString());
        assertEquals(liveOrderBoardMock.ordersFor(Currency.USD.toString()), orders);
    }

    @After
    public void tearDown() throws Exception {
        orders.clear();
    }
}