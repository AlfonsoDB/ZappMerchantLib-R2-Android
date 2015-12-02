Feature: MerchantService.initiatePayment

  Scenario: Initiate payment with NETWORK_ERROR
    Given the Request to Pay Type is "IMMEDIATE"
    Given the Payment Type is "INSTANT_PAYMENT"
    And the Checkout Type is "NORMAL"
    And the Delivery Type is "SERVICE"
    And the Currency Amount is "10 GBP"
    And the Merchant Delegate initiate payment endpoint responds with failure "NETWORK_ERROR"
    When I try to initiate the payment with these details
    Then the payment initiation is failed

  Scenario: Initiate payment with CONSUMER_CUSTOM_ERROR
    Given the Request to Pay Type is "IMMEDIATE"
    Given the Payment Type is "INSTANT_PAYMENT"
    And the Checkout Type is "NORMAL"
    And the Delivery Type is "SERVICE"
    And the Currency Amount is "10 GBP"
    And the Merchant Delegate initiate payment endpoint responds with failure "CONSUMER_CUSTOM_ERROR"
    When I try to initiate the payment with these details
    Then the payment initiation is failed

  Scenario: Initiate payment with INVALID_SERVER_RESPONSE
    Given the Request to Pay Type is "IMMEDIATE"
    Given the Payment Type is "INSTANT_PAYMENT"
    And the Checkout Type is "NORMAL"
    And the Delivery Type is "SERVICE"
    And the Currency Amount is "10 GBP"
    And the Merchant Delegate initiate payment endpoint responds with failure "INVALID_SERVER_RESPONSE"
    When I try to initiate the payment with these details
    Then the payment initiation is failed

  Scenario: Initiate payment with INVALID_BRN
    Given the Request to Pay Type is "IMMEDIATE"
    Given the Payment Type is "INSTANT_PAYMENT"
    And the Checkout Type is "NORMAL"
    And the Delivery Type is "SERVICE"
    And the Currency Amount is "10 GBP"
    And the Merchant Delegate initiate payment endpoint responds with failure "INVALID_BRN"
    When I try to initiate the payment with these details
    Then the payment initiation is failed

  Scenario: Initiate payment
    Given the Request to Pay Type is "IMMEDIATE"
    Given the Payment Type is "INSTANT_PAYMENT"
    And the Checkout Type is "NORMAL"
    And the Delivery Type is "SERVICE"
    And the Currency Amount is "10 GBP"
    When I try to initiate the payment with these details
    Then the payment is initiated successfully

  Scenario: Initiate payment
    Given the Request to Pay Type is "IMMEDIATE"
    Given the Payment Type is "SMB"
    And the Checkout Type is "NORMAL"
    And the Delivery Type is "SERVICE"
    And the Currency Amount is "200 GBP"
    And the Merchant Delegate initiate payment endpoint responds with Zapp Code "zappCode" only transaction
    When I try to initiate the payment with these details
    Then the payment is initiated successfully

  Scenario: Initiate payment
    Given the Request to Pay Type is "IMMEDIATE"
    Given the Payment Type is "BILL_PAY"
    And the Checkout Type is "NORMAL"
    And the Delivery Type is "SERVICE"
    And the Currency Amount is "100 GBP"
    And the Merchant Delegate initiate payment endpoint responds with Zapp Code "zappCode" and Transaction Id "aptId" transaction
    When I try to initiate the payment with these details
    Then the payment is initiated successfully
