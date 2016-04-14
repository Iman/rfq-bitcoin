Feature: RFQ
  To enable the user to ask for a quote by RFQ service

  Scenario: Get a quote by RFQ service.
    Given the live order list for currency 'USD'
      | direction | price  | currency | amount |
      | BUY       | 232.71 | USD      | 200    |
      | SELL      | 232.74 | USD      | 100    |
      | SELL      | 232.73 | USD      | 200    |
      | BUY       | 232.71 | USD      | 500    |
      | BUY       | 232.70 | USD      | 100    |
      | SELL      | 232.75 | USD      | 200    |
      | BUY       | 232.69 | USD      | 500    |
      | SELL      | 232.76 | USD      | 300    |
      | BUY       | 232.70 | USD      | 200    |
    When the user get the quote for currency 'USD' and amount 200
    Then a quote of bid 232.69 and ask 232.75 should be returned

  Scenario: Get an unavailable quote by RFQ service.
    Given the live order list for currency 'USD'
      | direction | price  | currency | amount |
      | BUY       | 232.71 | USD      | 200    |
      | SELL      | 232.74 | USD      | 100    |
      | SELL      | 232.73 | USD      | 200    |
      | BUY       | 232.71 | USD      | 500    |
      | BUY       | 232.70 | USD      | 100    |
      | SELL      | 232.75 | USD      | 200    |
      | BUY       | 232.69 | USD      | 500    |
      | SELL      | 232.76 | USD      | 300    |
      | BUY       | 232.70 | USD      | 200    |
    When the user get the quote for currency 'USD' and amount 500
    Then an empty quote will be returned