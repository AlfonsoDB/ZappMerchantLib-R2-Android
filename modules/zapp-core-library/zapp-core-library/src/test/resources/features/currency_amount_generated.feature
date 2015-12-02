Feature: Currency Amount

  Scenario: CurrencyAmount.N0
    When I try to create the Currency Amount
    Then the Currency Amount is not created

  Scenario: CurrencyAmount.P0
    Given the Value is "-25"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully

  Scenario: CurrencyAmount.P1
    Given the Value is "-25"
    And the Currency Code is "GBP"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully

  Scenario: CurrencyAmount.N1
    Given the Value is "-25"
    And the Currency Code is ""
    When I try to create the Currency Amount
    Then the Currency Amount is not created

  Scenario: CurrencyAmount.P2
    Given the Value is "-25"
    And the Currency Code is "USD"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully

  Scenario: CurrencyAmount.P3
    Given the Value is "0"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully

  Scenario: CurrencyAmount.P4
    Given the Value is "0"
    And the Currency Code is "GBP"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully

  Scenario: CurrencyAmount.N2
    Given the Value is "0"
    And the Currency Code is ""
    When I try to create the Currency Amount
    Then the Currency Amount is not created

  Scenario: CurrencyAmount.P5
    Given the Value is "0"
    And the Currency Code is "USD"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully

  Scenario: CurrencyAmount.P6
    Given the Value is "123"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully

  Scenario: CurrencyAmount.P7
    Given the Value is "123"
    And the Currency Code is "GBP"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully

  Scenario: CurrencyAmount.N3
    Given the Value is "123"
    And the Currency Code is ""
    When I try to create the Currency Amount
    Then the Currency Amount is not created

  Scenario: CurrencyAmount.P8
    Given the Value is "123"
    And the Currency Code is "USD"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully

