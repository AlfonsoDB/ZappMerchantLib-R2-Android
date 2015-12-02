Feature: TransactionId

  Scenario: Transaction.P0
    Given the APT Id of the transaction id is "aptId"
    And the APTR Id of the transaction id is "aptrId"
    When I try to create the Transaction Id
    Then the Transaction Id is created successfully

  Scenario: Transaction.N0
    Given the APT Id of the transaction id is "aptId"
    When I try to create the Transaction Id
    Then the Transaction Id is not created

  Scenario: Transaction.N1
    Given the APTR Id of the transaction id is "aptrId"
    When I try to create the Transaction Id
    Then the Transaction Id is not created

  Scenario: Transaction.N2
    When I try to create the Transaction Id
    Then the Transaction Id is not created
