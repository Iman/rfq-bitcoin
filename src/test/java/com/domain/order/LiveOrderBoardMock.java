package com.domain.order;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LiveOrderBoardMock implements LiveOrderBoard {
    private final Map<String, List<Order>> currencyToOrdersMap = new ConcurrentHashMap<String, List<Order>>();

    public Map<String, List<Order>> getCurrencyToOrdersMap() {
        return currencyToOrdersMap;
    }

    @Override
    public synchronized List<Order> ordersFor(String currency) {
        return currencyToOrdersMap.get(currency);
    }

}

