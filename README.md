# Pre-Interview Exercise

## Overview
Implementation of a simple the RFQ (request-for-quote) service engine in Java which will allow the business to enter the bitcoin market.

## Requirement
The API looks like this and cannot be changed.
```
public interface RfqService {

    Optional<Quote> quoteFor(String currency, int amount);
}

public class Quote {
    public final double bid;
    public final double ask;

    public Quote(double bid, double ask) {
        this.bid = bid;
        this.ask = ask;
    }
}
```

```
public interface LiveOrderBoard {
    List<Order> ordersFor(String currency);
}
```

### Example response for USD:

|  Direction | Price  | Payment   Currency |  Amount of Bitcoins |
|---|---|---|---|
|Buy   | 232.71 | USD |  200 |
|Sell   | 232.74 | USD |  100 |
|Sell   | 232.73 | USD |  200 |
|Buy   | 232.71 | USD |  500 |
|Buy   | 232.70 | USD |  100 |
|Sell   | 232.75 | USD |  200 |
|Buy   | 232.69 | USD |  500 |
|Sell   | 232.76 | USD |  300 |
|Buy   | 232.70 | USD |  200 |

The response from our service should be a single bid/ask quote. If the request cannot be fulfilled on both sides by our internal RFQ service, the response should be empty.
   
- Bid = highest buy order – X
- Ask = lowest sell order + X

The difference between the client order and our quote (i.e. the X amount) is how we make money. X is 0.02 irrespective of the currency.

### Worked examples
RFQ for 200 bitcoins paying in USD should return:

- Bid (we buy) = 232.69
- Ask (we sell) = 232.75

RFQ for 500 bitcoins cannot be fulfilled because there’s no matching Sell order.

## Prerequisites
- Java 8
- maven 3

## Build
```mvn clean install```

## Test
```mvn clean test```

## BDD Test (Cucumber)
```mvn clean verify -Dit.test=RfqServiceRunner -Dcucumber.options=" --format html:report/cucumber-html-report-myReport"```
