package com.domain.service;

import com.domain.order.LiveOrderBoard;
import com.domain.order.Order;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class RfqServiceImpl implements RfqService {

    private final LiveOrderBoard liveOrderBoard;

    public RfqServiceImpl(LiveOrderBoard liveOrderBoard) {
        this.liveOrderBoard = liveOrderBoard;
    }

    @Override
    public Optional<Quote> quoteFor(String currency, int amount) {
        // Go over the orders and get the highest buy and the lowest sell of the specified amount.
        List<Order> orders = liveOrderBoard.ordersFor(currency);
        Comparator<Order> comparator = (o1, o2) ->
        {
            double difference = o1.getPrice() - o2.getPrice();
            return (difference > 0 ? 1 : (difference == 0 ? 0 : -1));
        };
        Optional<Order> bid = orders.stream()
                .filter(order -> order.getAmount() == amount)
                .filter(order -> order.isBuy()).max(comparator);
        Optional<Order> ask = orders.stream()
                .filter(order -> order.getAmount() == amount)
                .filter(order -> !order.isBuy()).min(comparator);

        // If the request cannot be fulfilled on both sides by our internal RFQ service, the response should be empty.
        if (!bid.isPresent() || !ask.isPresent()) {
            return Optional.empty();
        }
        
        Quote quote = new Quote(
                bid.get().getPrice() - IConstants.PRICE_DIFFERENCE,
                ask.get().getPrice() + IConstants.PRICE_DIFFERENCE);

        return Optional.of(quote);
    }

}
