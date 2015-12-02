Feature: MerchantService.notifyMerchantPayment

Scenario: Notify payment
    Given the Request to Pay Type is "IMMEDIATE"
    Given the Payment Type is "BILL_PAY"
    And the Checkout Type is "NORMAL"
    And the Delivery Type is "SERVICE"
    And the Currency Amount is "100 GBP"
    And the Merchant Delegate initiate payment endpoint responds with Zapp Code "zappCode" and Transaction Id "aptId" transaction
    When I try to initiate the payment with these details
    Then the payment is initiated successfully
    And I try to notify payment
    Then the payment notification is successful

  Scenario: Notify payment with error "PAYMENT_NOT_CONFIRMED"
    Given the Request to Pay Type is "IMMEDIATE"
    Given the Payment Type is "BILL_PAY"
    And the Checkout Type is "NORMAL"
    And the Delivery Type is "SERVICE"
    And the Currency Amount is "100 GBP"
    And the Merchant Delegate initiate payment endpoint responds with Zapp Code "zappCode" and Transaction Id "aptId" transaction
    When I try to initiate the payment with these details
    Then the payment is initiated successfully
    And the Merchant Delegate notify payment endpoint responds with failure "PAYMENT_NOT_CONFIRMED"
    And I try to notify payment
    Then the payment notification is failed

  Scenario: Notify payment with error "NETWORK_ERROR"
    Given the Request to Pay Type is "IMMEDIATE"
    Given the Payment Type is "BILL_PAY"
    And the Checkout Type is "NORMAL"
    And the Delivery Type is "SERVICE"
    And the Currency Amount is "100 GBP"
    And the Merchant Delegate initiate payment endpoint responds with Zapp Code "zappCode" and Transaction Id "aptId" transaction
    When I try to initiate the payment with these details
    Then the payment is initiated successfully
    And the Merchant Delegate notify payment endpoint responds with failure "NETWORK_ERROR"
    And I try to notify payment
    Then the payment notification is failed