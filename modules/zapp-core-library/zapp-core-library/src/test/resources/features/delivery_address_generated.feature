Feature: Delivery Address

  Scenario: DeliveryAddress.P0
    Given the Delivery Address Type is "GENERAL"
    And the Addressee User is "valid"
    And the Address to deliver is "valid"
    When I try to create the Delivery Address
    Then the Delivery Address is created successfully

  Scenario: DeliveryAddress.P1
    Given the Identifier is "identifier"
    And the Delivery Address Type is "GENERAL"
    And the Addressee User is "valid"
    And the Address to deliver is "valid"
    When I try to create the Delivery Address
    Then the Delivery Address is created successfully

  Scenario: DeliveryAddress.N0
    Given the Delivery Address Type is "GENERAL"
    And the Addressee User is "valid"
    And the Address to deliver is "invalid"
    When I try to create the Delivery Address
    Then the Delivery Address is not created

  Scenario: DeliveryAddress.N1
    Given the Delivery Address Type is "GENERAL"
    And the Addressee User is "invalid"
    And the Address to deliver is "valid"
    When I try to create the Delivery Address
    Then the Delivery Address is not created

  Scenario: DeliveryAddress.P2
    Given the Delivery Address Type is "GENERAL"
    And the Addressee User is "is_null"
    And the Address to deliver is "valid"
    When I try to create the Delivery Address
    Then the Delivery Address is created successfully

  Scenario: DeliveryAddress.P3
    Given the Identifier is "identifier"
    And the Delivery Address Type is "GENERAL"
    And the Addressee User is "is_null"
    And the Address to deliver is "valid"
    When I try to create the Delivery Address
    Then the Delivery Address is created successfully

  Scenario: DeliveryAddress.N2
    Given the Delivery Address Type is "GENERAL"
    And the Addressee User is "is_null"
    And the Address to deliver is "invalid"
    When I try to create the Delivery Address
    Then the Delivery Address is not created

  Scenario: DeliveryAddress.P4
    Given the Delivery Address Type is "ZAPP"
    And the Addressee User is "valid"
    And the Address to deliver is "valid"
    When I try to create the Delivery Address
    Then the Delivery Address is created successfully

  Scenario: DeliveryAddress.P5
    Given the Identifier is "identifier"
    And the Delivery Address Type is "ZAPP"
    And the Addressee User is "valid"
    And the Address to deliver is "valid"
    When I try to create the Delivery Address
    Then the Delivery Address is created successfully

  Scenario: DeliveryAddress.N3
    Given the Delivery Address Type is "ZAPP"
    And the Addressee User is "valid"
    And the Address to deliver is "invalid"
    When I try to create the Delivery Address
    Then the Delivery Address is not created

  Scenario: DeliveryAddress.N4
    Given the Delivery Address Type is "ZAPP"
    And the Addressee User is "invalid"
    And the Address to deliver is "valid"
    When I try to create the Delivery Address
    Then the Delivery Address is not created

  Scenario: DeliveryAddress.P6
    Given the Delivery Address Type is "ZAPP"
    And the Addressee User is "is_null"
    And the Address to deliver is "valid"
    When I try to create the Delivery Address
    Then the Delivery Address is created successfully

  Scenario: DeliveryAddress.P7
    Given the Identifier is "identifier"
    And the Delivery Address Type is "ZAPP"
    And the Addressee User is "is_null"
    And the Address to deliver is "valid"
    When I try to create the Delivery Address
    Then the Delivery Address is created successfully

  Scenario: DeliveryAddress.N5
    Given the Delivery Address Type is "ZAPP"
    And the Addressee User is "is_null"
    And the Address to deliver is "invalid"
    When I try to create the Delivery Address
    Then the Delivery Address is not created

  Scenario: DeliveryAddress.P8
    Given the Delivery Address Type is "CFI"
    And the Addressee User is "valid"
    And the Address to deliver is "valid"
    When I try to create the Delivery Address
    Then the Delivery Address is created successfully

  Scenario: DeliveryAddress.P9
    Given the Identifier is "identifier"
    And the Delivery Address Type is "CFI"
    And the Addressee User is "valid"
    And the Address to deliver is "valid"
    When I try to create the Delivery Address
    Then the Delivery Address is created successfully

  Scenario: DeliveryAddress.N6
    Given the Delivery Address Type is "CFI"
    And the Addressee User is "valid"
    And the Address to deliver is "invalid"
    When I try to create the Delivery Address
    Then the Delivery Address is not created

  Scenario: DeliveryAddress.N7
    Given the Delivery Address Type is "CFI"
    And the Addressee User is "invalid"
    And the Address to deliver is "valid"
    When I try to create the Delivery Address
    Then the Delivery Address is not created

  Scenario: DeliveryAddress.P10
    Given the Delivery Address Type is "CFI"
    And the Addressee User is "is_null"
    And the Address to deliver is "valid"
    When I try to create the Delivery Address
    Then the Delivery Address is created successfully

  Scenario: DeliveryAddress.P11
    Given the Identifier is "identifier"
    And the Delivery Address Type is "CFI"
    And the Addressee User is "is_null"
    And the Address to deliver is "valid"
    When I try to create the Delivery Address
    Then the Delivery Address is created successfully

  Scenario: DeliveryAddress.N8
    Given the Delivery Address Type is "CFI"
    And the Addressee User is "is_null"
    And the Address to deliver is "invalid"
    When I try to create the Delivery Address
    Then the Delivery Address is not created

  Scenario: DeliveryAddress.N9
    Given the Addressee User is "valid"
    And the Address to deliver is "valid"
    When I try to create the Delivery Address
    Then the Delivery Address is not created

