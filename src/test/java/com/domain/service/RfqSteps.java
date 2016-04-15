package com.domain.service;

import com.domain.order.LiveOrderBoardMock;
import com.domain.order.Order;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.util.List;
import java.util.Optional;

public class RfqSteps {
    private final LiveOrderBoardMock liveOrderBoardMock = new LiveOrderBoardMock();
    private Quote quote;

    @Given("^the live order list for currency '(.+)'$")
    public void addLiveOrders(String currency, List<Order> orders) {
        liveOrderBoardMock.getCurrencyToOrdersMap().put(currency, orders);
    }

    @When("^the user get the quote for currency '(.+)' and amount (\\d+)$")
    public void quote(String currency, int amount) {
        RfqService service = new RfqServiceImpl(liveOrderBoardMock);
        Optional<Quote> optional = service.quoteFor(currency, amount);
        quote = (optional.isPresent() ? optional.get() : null);
    }

    @Then("^a quote of bid (.+) and ask (.+) should be returned$")
    public void verify(double bid, double ask) {
        Assert.assertTrue(quote.bid == bid);
        Assert.assertTrue(quote.ask == ask);
    }

    @Then("^an empty quote will be returned$")
    public void verify() {
        Assert.assertTrue(quote == null);
    }
}
