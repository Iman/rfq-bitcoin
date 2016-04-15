package com.domain.service;

import org.junit.Assert;
import org.junit.Test;

public class QuoteTest {

    @Test
    public void testConstructor() {
        double bid = 8123.45;
        double ask = 4956.78;
        Quote quote = new Quote(bid, ask);

        Assert.assertTrue(quote.bid == bid);
        Assert.assertTrue(quote.ask == ask);
    }
}
