package com.domain.service;

import com.domain.order.Currency;
import com.domain.order.Direction;
import com.domain.order.LiveOrderBoardMock;
import com.domain.order.Order;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class RfqServiceImplTest {
    private LiveOrderBoardMock liveOrderBoard;

    @Before
    public void before() {

        liveOrderBoard = new LiveOrderBoardMock();
        Map<String, List<Order>> currencyToOrders = liveOrderBoard.getCurrencyToOrdersMap();
        List<Order> orders = new ArrayList<>();
        currencyToOrders.put(Currency.USD.toString(), orders);
        orders.add(new Order(Direction.BUY, 232.71, Currency.USD.toString(), 200));
        orders.add(new Order(Direction.SELL, 232.74, Currency.USD.toString(), 100));
        orders.add(new Order(Direction.SELL, 232.73, Currency.USD.toString(), 200));
        orders.add(new Order(Direction.BUY, 232.71, Currency.USD.toString(), 500));
        orders.add(new Order(Direction.BUY, 232.70, Currency.USD.toString(), 100));
        orders.add(new Order(Direction.SELL, 232.75, Currency.USD.toString(), 200));
        orders.add(new Order(Direction.BUY, 232.69, Currency.USD.toString(), 500));
        orders.add(new Order(Direction.SELL, 232.76, Currency.USD.toString(), 300));
        orders.add(new Order(Direction.BUY, 232.70, Currency.USD.toString(), 200));
    }

    @Test
    public void testBothBuyAndSellAvailable() {
        RfqService rfqService = new RfqServiceImpl(liveOrderBoard);
        Quote quote = rfqService.quoteFor(Currency.USD.toString(), 200).get();
        Assert.assertTrue(quote.bid == 232.69);
        Assert.assertTrue(quote.ask == 232.75);
    }

    /**
     * RFQ for 500 bitcoins cannot be fulfilled because there’s no matching Sell order.
     */
    @Test
    public void testOnlyBidAvailable() {
        RfqService rfqService = new RfqServiceImpl(liveOrderBoard);
        Optional<Quote> quote = rfqService.quoteFor(Currency.USD.toString(), 500);
        Assert.assertTrue(!quote.isPresent());
    }

    @Test
    public void testOnlyAskAvailable() {
        RfqService rfqService = new RfqServiceImpl(liveOrderBoard);
        Optional<Quote> quote = rfqService.quoteFor(Currency.USD.toString(), 300);
        Assert.assertTrue(!quote.isPresent());
    }

    @Test
    public void testNotAvailable() {
        RfqService rfqService = new RfqServiceImpl(liveOrderBoard);
        Optional<Quote> quote = rfqService.quoteFor(Currency.USD.toString(), 123);
        Assert.assertTrue(!quote.isPresent());
    }

    @Test
    public void testMultiThreading() throws InterruptedException {
        RfqService rfqService = new RfqServiceImpl(liveOrderBoard);

        AtomicInteger completed = new AtomicInteger();
        AtomicBoolean failed = new AtomicBoolean();
        for (int i = 0; i < 100; i++) {
            new Thread(() ->
            {
                Quote quote = rfqService.quoteFor(Currency.USD.toString(), 200).get();
                if (quote.bid == 232.68 && quote.ask == 232.75) {
                    failed.set(true);
                }
                completed.incrementAndGet();
            }).start();
        }

        // Wait for the threads to complete.
        while (completed.get() < 100) {
            Thread.sleep(1000);
        }

        Assert.assertTrue(!failed.get());
    }

}