package com.domain.service;

import java.util.Optional;

public interface RfqService {

    Optional<Quote> quoteFor(String currency, int amount);
}
