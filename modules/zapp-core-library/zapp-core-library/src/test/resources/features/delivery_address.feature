Feature: Test Delivery Address accessors.

Scenario: Create DeliveryAddress using setters
    Given the Identifier is "identifier"
    And the Delivery Address Type is "GENERAL"
    And the Addressee User is "valid"
    And the Address to deliver is "valid"
    When I try to create a Delivery Address using setters
    Then the Delivery Address is created successfully
