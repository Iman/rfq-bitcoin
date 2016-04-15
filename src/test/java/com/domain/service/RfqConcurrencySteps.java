package com.domain.service;


import com.domain.order.LiveOrderBoardMock;
import com.domain.order.Order;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class RfqConcurrencySteps {
    private final LiveOrderBoardMock liveOrderBoardMock = new LiveOrderBoardMock();
    private final List<Quote> quotes = Collections.synchronizedList(new LinkedList<>());

    @Given("^the concurrency live order list for currency '(.+)'$")
    public void addLiveOrders(String currency, List<Order> orders) {
        liveOrderBoardMock.getCurrencyToOrdersMap().put(currency, orders);
    }

    @When("^(\\d+) users get the quote for currency '(.+)' and amount (\\d+)$")
    public void quote(int userNumber, String currency, int amount) throws InterruptedException {
        RfqService service = new RfqServiceImpl(liveOrderBoardMock);
        AtomicInteger finished = new AtomicInteger();
        for (int i = 0; i < userNumber; i++) {
            new Thread(() ->
            {
                Optional<Quote> optional = service.quoteFor(currency, amount);
                if (optional.isPresent()) {
                    quotes.add(optional.get());
                }
                finished.incrementAndGet();
            }).start();
        }

        while (finished.get() < userNumber) {
            Thread.sleep(100);
        }
    }

    @Then("^(\\d+) quotes of bid (.+) and ask (.+) should be returned$")
    public void verify(int quoteNumber, double bid, double ask) {
        Assert.assertTrue(quotes.size() == quoteNumber);
        for (Quote quote : quotes) {
            Assert.assertTrue(quote.bid == bid);
            Assert.assertTrue(quote.ask == ask);
        }
    }
}
