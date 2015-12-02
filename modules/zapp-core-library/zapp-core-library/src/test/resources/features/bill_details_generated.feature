Feature: Bill Details

  Scenario: BillDetails.N0
    When I try to create the Bill Details
    Then The Bill Details is not created

  Scenario: BillDetails.N1
    Given the Reference is ""
    When I try to create the Bill Details
    Then The Bill Details is not created

  Scenario: BillDetails.P0
    Given the Reference is "ref1234abcd"
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.P1
    Given the Reference is "ref1234abcd"
    And the Account Id is ""
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.P2
    Given the Reference is "ref1234abcd"
    And the Account Id is "12345678"
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.P3
    Given the Reference is "ref1234abcd"
    And the Period To is "2015-12-31"
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.P4
    Given the Reference is "ref1234abcd"
    And the Period To is "2015-12-31"
    And the Account Id is ""
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.P5
    Given the Reference is "ref1234abcd"
    And the Period To is "2015-12-31"
    And the Account Id is "12345678"
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.P6
    Given the Reference is "ref1234abcd"
    And the Period To is "2015-01-01"
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.P7
    Given the Reference is "ref1234abcd"
    And the Period To is "2015-01-01"
    And the Account Id is ""
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.P8
    Given the Reference is "ref1234abcd"
    And the Period To is "2015-01-01"
    And the Account Id is "12345678"
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.P9
    Given the Reference is "ref1234abcd"
    And the Period From is "2015-01-01"
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.P10
    Given the Reference is "ref1234abcd"
    And the Period From is "2015-01-01"
    And the Account Id is ""
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.P11
    Given the Reference is "ref1234abcd"
    And the Period From is "2015-01-01"
    And the Account Id is "12345678"
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.P12
    Given the Reference is "ref1234abcd"
    And the Period From is "2015-01-01"
    And the Period To is "2015-12-31"
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.P13
    Given the Reference is "ref1234abcd"
    And the Period From is "2015-01-01"
    And the Period To is "2015-12-31"
    And the Account Id is ""
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.P14
    Given the Reference is "ref1234abcd"
    And the Period From is "2015-01-01"
    And the Period To is "2015-12-31"
    And the Account Id is "12345678"
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.P15
    Given the Reference is "ref1234abcd"
    And the Period From is "2015-01-01"
    And the Period To is "2015-01-01"
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.P16
    Given the Reference is "ref1234abcd"
    And the Period From is "2015-01-01"
    And the Period To is "2015-01-01"
    And the Account Id is ""
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.P17
    Given the Reference is "ref1234abcd"
    And the Period From is "2015-01-01"
    And the Period To is "2015-01-01"
    And the Account Id is "12345678"
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.P18
    Given the Reference is "ref1234abcd"
    And the Period From is "2015-12-31"
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.P19
    Given the Reference is "ref1234abcd"
    And the Period From is "2015-12-31"
    And the Account Id is ""
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.P20
    Given the Reference is "ref1234abcd"
    And the Period From is "2015-12-31"
    And the Account Id is "12345678"
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.P21
    Given the Reference is "ref1234abcd"
    And the Period From is "2015-12-31"
    And the Period To is "2015-12-31"
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.P22
    Given the Reference is "ref1234abcd"
    And the Period From is "2015-12-31"
    And the Period To is "2015-12-31"
    And the Account Id is ""
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.P23
    Given the Reference is "ref1234abcd"
    And the Period From is "2015-12-31"
    And the Period To is "2015-12-31"
    And the Account Id is "12345678"
    When I try to create the Bill Details
    Then The Bill Details is created successfully

  Scenario: BillDetails.N2
    Given the Reference is "ref1234abcd"
    And the Period From is "2015-12-31"
    And the Period To is "2015-01-01"
    When I try to create the Bill Details
    Then The Bill Details is not created

  Scenario: BillDetails.N3
    Given the Reference is "ref1234abcd"
    And the Period From is "2015-12-31"
    And the Period To is "2015-01-01"
    And the Account Id is ""
    When I try to create the Bill Details
    Then The Bill Details is not created

  Scenario: BillDetails.N4
    Given the Reference is "ref1234abcd"
    And the Period From is "2015-12-31"
    And the Period To is "2015-01-01"
    And the Account Id is "12345678"
    When I try to create the Bill Details
    Then The Bill Details is not created

