Feature: Merchant

  Scenario: Merchant.N0
    Given the Merchant Address is "valid"
    When I try to create the Merchant
    Then the Merchant is not created

  Scenario: Merchant.N1
    Given the Merchant Identifier is ""
    And the Merchant Address is "valid"
    When I try to create the Merchant
    Then the Merchant is not created

  Scenario: Merchant.P0
    Given the Merchant Identifier is "identifier"
    And the Merchant Address is "valid"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P1
    Given the Merchant Identifier is "identifier"
    And the Merchant Address is "valid"
    And the Merchant Website is "website"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P2
    Given the Merchant Identifier is "identifier"
    And the Merchant Address is "valid"
    And the Merchant Name is "merchantName"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P3
    Given the Merchant Identifier is "identifier"
    And the Merchant Address is "valid"
    And the Merchant Website is "website"
    And the Merchant Name is "merchantName"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P4
    Given the Merchant Identifier is "identifier"
    And the Merchant Phone is "12345678"
    And the Merchant Address is "valid"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P5
    Given the Merchant Identifier is "identifier"
    And the Merchant Phone is "12345678"
    And the Merchant Address is "valid"
    And the Merchant Website is "website"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P6
    Given the Merchant Identifier is "identifier"
    And the Merchant Phone is "12345678"
    And the Merchant Address is "valid"
    And the Merchant Name is "merchantName"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P7
    Given the Merchant Identifier is "identifier"
    And the Merchant Phone is "12345678"
    And the Merchant Address is "valid"
    And the Merchant Website is "website"
    And the Merchant Name is "merchantName"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.N2
    Given the Merchant Identifier is "identifier"
    And the Merchant Address is "invalid"
    When I try to create the Merchant
    Then the Merchant is not created

  Scenario: Merchant.P8
    Given the Merchant Identifier is "identifier"
    And the Merchant Address is "is_null"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P9
    Given the Merchant Identifier is "identifier"
    And the Merchant Address is "is_null"
    And the Merchant Website is "website"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P10
    Given the Merchant Identifier is "identifier"
    And the Merchant Address is "is_null"
    And the Merchant Name is "merchantName"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P11
    Given the Merchant Identifier is "identifier"
    And the Merchant Address is "is_null"
    And the Merchant Website is "website"
    And the Merchant Name is "merchantName"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P12
    Given the Merchant Identifier is "identifier"
    And the Merchant Phone is "12345678"
    And the Merchant Address is "is_null"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P13
    Given the Merchant Identifier is "identifier"
    And the Merchant Phone is "12345678"
    And the Merchant Address is "is_null"
    And the Merchant Website is "website"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P14
    Given the Merchant Identifier is "identifier"
    And the Merchant Phone is "12345678"
    And the Merchant Address is "is_null"
    And the Merchant Name is "merchantName"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P15
    Given the Merchant Identifier is "identifier"
    And the Merchant Phone is "12345678"
    And the Merchant Address is "is_null"
    And the Merchant Website is "website"
    And the Merchant Name is "merchantName"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.N3
    Given the Merchant Identifier is "identifier"
    And the Merchant Email is ""
    And the Merchant Address is "valid"
    When I try to create the Merchant
    Then the Merchant is not created

  Scenario: Merchant.N4
    Given the Merchant Identifier is "identifier"
    And the Merchant Email is "@a.com"
    And the Merchant Address is "valid"
    When I try to create the Merchant
    Then the Merchant is not created

  Scenario: Merchant.P16
    Given the Merchant Identifier is "identifier"
    And the Merchant Email is "hi@merchant.co.uk"
    And the Merchant Address is "valid"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P17
    Given the Merchant Identifier is "identifier"
    And the Merchant Email is "hi@merchant.co.uk"
    And the Merchant Address is "valid"
    And the Merchant Website is "website"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P18
    Given the Merchant Identifier is "identifier"
    And the Merchant Email is "hi@merchant.co.uk"
    And the Merchant Address is "valid"
    And the Merchant Name is "merchantName"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P19
    Given the Merchant Identifier is "identifier"
    And the Merchant Email is "hi@merchant.co.uk"
    And the Merchant Address is "valid"
    And the Merchant Website is "website"
    And the Merchant Name is "merchantName"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P20
    Given the Merchant Identifier is "identifier"
    And the Merchant Email is "hi@merchant.co.uk"
    And the Merchant Phone is "12345678"
    And the Merchant Address is "valid"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P21
    Given the Merchant Identifier is "identifier"
    And the Merchant Email is "hi@merchant.co.uk"
    And the Merchant Phone is "12345678"
    And the Merchant Address is "valid"
    And the Merchant Website is "website"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P22
    Given the Merchant Identifier is "identifier"
    And the Merchant Email is "hi@merchant.co.uk"
    And the Merchant Phone is "12345678"
    And the Merchant Address is "valid"
    And the Merchant Name is "merchantName"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P23
    Given the Merchant Identifier is "identifier"
    And the Merchant Email is "hi@merchant.co.uk"
    And the Merchant Phone is "12345678"
    And the Merchant Address is "valid"
    And the Merchant Website is "website"
    And the Merchant Name is "merchantName"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.N5
    Given the Merchant Identifier is "identifier"
    And the Merchant Email is "hi@merchant.co.uk"
    And the Merchant Address is "invalid"
    When I try to create the Merchant
    Then the Merchant is not created

  Scenario: Merchant.P24
    Given the Merchant Identifier is "identifier"
    And the Merchant Email is "hi@merchant.co.uk"
    And the Merchant Address is "is_null"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P25
    Given the Merchant Identifier is "identifier"
    And the Merchant Email is "hi@merchant.co.uk"
    And the Merchant Address is "is_null"
    And the Merchant Website is "website"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P26
    Given the Merchant Identifier is "identifier"
    And the Merchant Email is "hi@merchant.co.uk"
    And the Merchant Address is "is_null"
    And the Merchant Name is "merchantName"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P27
    Given the Merchant Identifier is "identifier"
    And the Merchant Email is "hi@merchant.co.uk"
    And the Merchant Address is "is_null"
    And the Merchant Website is "website"
    And the Merchant Name is "merchantName"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P28
    Given the Merchant Identifier is "identifier"
    And the Merchant Email is "hi@merchant.co.uk"
    And the Merchant Phone is "12345678"
    And the Merchant Address is "is_null"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P29
    Given the Merchant Identifier is "identifier"
    And the Merchant Email is "hi@merchant.co.uk"
    And the Merchant Phone is "12345678"
    And the Merchant Address is "is_null"
    And the Merchant Website is "website"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P30
    Given the Merchant Identifier is "identifier"
    And the Merchant Email is "hi@merchant.co.uk"
    And the Merchant Phone is "12345678"
    And the Merchant Address is "is_null"
    And the Merchant Name is "merchantName"
    When I try to create the Merchant
    Then the Merchant is created successfully

  Scenario: Merchant.P31
    Given the Merchant Identifier is "identifier"
    And the Merchant Email is "hi@merchant.co.uk"
    And the Merchant Phone is "12345678"
    And the Merchant Address is "is_null"
    And the Merchant Website is "website"
    And the Merchant Name is "merchantName"
    When I try to create the Merchant
    Then the Merchant is created successfully

