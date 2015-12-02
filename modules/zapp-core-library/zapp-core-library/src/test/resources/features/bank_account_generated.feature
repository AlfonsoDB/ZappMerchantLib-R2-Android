Feature: Bank Account

  Scenario: BankAccount.N0
    Given the Balance is "valid"
    And the Available Funds are "valid"
    When I try to create the Bank Account
    Then The Bank Account is not created

  Scenario: BankAccount.N1
    Given the Number is ""
    And the Balance is "valid"
    And the Available Funds are "valid"
    When I try to create the Bank Account
    Then The Bank Account is not created

  Scenario: BankAccount.N2
    Given the Number is "12345678"
    And the Balance is "valid"
    And the Available Funds are "valid"
    When I try to create the Bank Account
    Then The Bank Account is not created

  Scenario: BankAccount.N3
    Given the Number is "12345678"
    And the Name is ""
    And the Balance is "valid"
    And the Available Funds are "valid"
    When I try to create the Bank Account
    Then The Bank Account is not created

  Scenario: BankAccount.N4
    Given the Number is "12345678"
    And the Name is "Olivia Kensington"
    And the Balance is "valid"
    And the Available Funds are "valid"
    When I try to create the Bank Account
    Then The Bank Account is not created

  Scenario: BankAccount.N5
    Given the Number is "12345678"
    And the Name is "Olivia Kensington"
    And the Sort Code is ""
    And the Balance is "valid"
    And the Available Funds are "valid"
    When I try to create the Bank Account
    Then The Bank Account is not created

  Scenario: BankAccount.P0
    Given the Number is "12345678"
    And the Name is "Olivia Kensington"
    And the Sort Code is "12-12-12"
    And the Balance is "valid"
    And the Available Funds are "valid"
    When I try to create the Bank Account
    Then Bank Account is created successfully

  Scenario: BankAccount.N6
    Given the Number is "12345678"
    And the Name is "Olivia Kensington"
    And the Sort Code is "12-12-12"
    And the Balance is "valid"
    And the Available Funds are "invalid"
    When I try to create the Bank Account
    Then The Bank Account is not created

  Scenario: BankAccount.N7
    Given the Number is "12345678"
    And the Name is "Olivia Kensington"
    And the Sort Code is "12-12-12"
    And the Balance is "valid"
    When I try to create the Bank Account
    Then The Bank Account is not created

  Scenario: BankAccount.N8
    Given the Number is "12345678"
    And the Name is "Olivia Kensington"
    And the Sort Code is "12-12-12"
    And the Balance is "invalid"
    And the Available Funds are "valid"
    When I try to create the Bank Account
    Then The Bank Account is not created

  Scenario: BankAccount.N9
    Given the Number is "12345678"
    And the Name is "Olivia Kensington"
    And the Sort Code is "12-12-12"
    And the Available Funds are "valid"
    When I try to create the Bank Account
    Then The Bank Account is not created

