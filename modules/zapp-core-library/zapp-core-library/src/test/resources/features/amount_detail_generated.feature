Feature: Amount Detail

  Scenario: AmountDetail.N0: 
    Given the Price is "is_null"
    And the Rate is "is_null"
    When I try to create the Amount Detail
    Then Amount Detail is not created

  Scenario: AmountDetail.N1: 
    And the Amount Type is "CSHB"
    And the Price is "is_null"
    And the Rate is "is_null"
    When I try to create the Amount Detail
    Then Amount Detail is not created

  Scenario: AmountDetail.N2: 
    And the Amount Type is "CSHB"
    And the Price is "negative"
    And the Rate is "is_null"
    When I try to create the Amount Detail
    Then Amount Detail is not created

  Scenario: AmountDetail.P0: 
    And the Amount Type is "CSHB"
    And the Price is "zero"
    And the Rate is "is_null"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.N3: 
    And the Amount Type is "CSHB"
    And the Price is "zero"
    And the Rate is "negative"
    When I try to create the Amount Detail
    Then Amount Detail is not created

  Scenario: AmountDetail.P1: 
    And the Amount Type is "CSHB"
    And the Price is "zero"
    And the Rate is "zero"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.P2: 
    And the Amount Type is "CSHB"
    And the Price is "zero"
    And the Rate is "positive"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.P3: 
    And the Amount Type is "CSHB"
    And the Price is "positive"
    And the Rate is "is_null"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.N4: 
    And the Amount Type is "CSHB"
    And the Price is "positive"
    And the Rate is "negative"
    When I try to create the Amount Detail
    Then Amount Detail is not created

  Scenario: AmountDetail.P4: 
    And the Amount Type is "CSHB"
    And the Price is "positive"
    And the Rate is "zero"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.P5: 
    And the Amount Type is "CSHB"
    And the Price is "positive"
    And the Rate is "positive"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.N5: 
    And the Amount Type is "DELC"
    And the Price is "is_null"
    And the Rate is "is_null"
    When I try to create the Amount Detail
    Then Amount Detail is not created

  Scenario: AmountDetail.N6: 
    And the Amount Type is "DELC"
    And the Price is "negative"
    And the Rate is "is_null"
    When I try to create the Amount Detail
    Then Amount Detail is not created

  Scenario: AmountDetail.P6: 
    And the Amount Type is "DELC"
    And the Price is "zero"
    And the Rate is "is_null"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.N7: 
    And the Amount Type is "DELC"
    And the Price is "zero"
    And the Rate is "negative"
    When I try to create the Amount Detail
    Then Amount Detail is not created

  Scenario: AmountDetail.P7: 
    And the Amount Type is "DELC"
    And the Price is "zero"
    And the Rate is "zero"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.P8: 
    And the Amount Type is "DELC"
    And the Price is "zero"
    And the Rate is "positive"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.P9: 
    And the Amount Type is "DELC"
    And the Price is "positive"
    And the Rate is "is_null"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.N8: 
    And the Amount Type is "DELC"
    And the Price is "positive"
    And the Rate is "negative"
    When I try to create the Amount Detail
    Then Amount Detail is not created

  Scenario: AmountDetail.P10: 
    And the Amount Type is "DELC"
    And the Price is "positive"
    And the Rate is "zero"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.P11: 
    And the Amount Type is "DELC"
    And the Price is "positive"
    And the Rate is "positive"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.N9: 
    And the Amount Type is "FEES"
    And the Price is "is_null"
    And the Rate is "is_null"
    When I try to create the Amount Detail
    Then Amount Detail is not created

  Scenario: AmountDetail.N10: 
    And the Amount Type is "FEES"
    And the Price is "negative"
    And the Rate is "is_null"
    When I try to create the Amount Detail
    Then Amount Detail is not created

  Scenario: AmountDetail.P12: 
    And the Amount Type is "FEES"
    And the Price is "zero"
    And the Rate is "is_null"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.N11: 
    And the Amount Type is "FEES"
    And the Price is "zero"
    And the Rate is "negative"
    When I try to create the Amount Detail
    Then Amount Detail is not created

  Scenario: AmountDetail.P13: 
    And the Amount Type is "FEES"
    And the Price is "zero"
    And the Rate is "zero"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.P14: 
    And the Amount Type is "FEES"
    And the Price is "zero"
    And the Rate is "positive"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.P15: 
    And the Amount Type is "FEES"
    And the Price is "positive"
    And the Rate is "is_null"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.N12: 
    And the Amount Type is "FEES"
    And the Price is "positive"
    And the Rate is "negative"
    When I try to create the Amount Detail
    Then Amount Detail is not created

  Scenario: AmountDetail.P16: 
    And the Amount Type is "FEES"
    And the Price is "positive"
    And the Rate is "zero"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.P17: 
    And the Amount Type is "FEES"
    And the Price is "positive"
    And the Rate is "positive"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.N13: 
    And the Amount Type is "RBTS"
    And the Price is "is_null"
    And the Rate is "is_null"
    When I try to create the Amount Detail
    Then Amount Detail is not created

  Scenario: AmountDetail.N14: 
    And the Amount Type is "RBTS"
    And the Price is "negative"
    And the Rate is "is_null"
    When I try to create the Amount Detail
    Then Amount Detail is not created

  Scenario: AmountDetail.P18: 
    And the Amount Type is "RBTS"
    And the Price is "zero"
    And the Rate is "is_null"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.N15: 
    And the Amount Type is "RBTS"
    And the Price is "zero"
    And the Rate is "negative"
    When I try to create the Amount Detail
    Then Amount Detail is not created

  Scenario: AmountDetail.P19: 
    And the Amount Type is "RBTS"
    And the Price is "zero"
    And the Rate is "zero"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.P20: 
    And the Amount Type is "RBTS"
    And the Price is "zero"
    And the Rate is "positive"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.P21: 
    And the Amount Type is "RBTS"
    And the Price is "positive"
    And the Rate is "is_null"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.N16: 
    And the Amount Type is "RBTS"
    And the Price is "positive"
    And the Rate is "negative"
    When I try to create the Amount Detail
    Then Amount Detail is not created

  Scenario: AmountDetail.P22: 
    And the Amount Type is "RBTS"
    And the Price is "positive"
    And the Rate is "zero"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.P23: 
    And the Amount Type is "RBTS"
    And the Price is "positive"
    And the Rate is "positive"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.N17: 
    And the Amount Type is "VATX"
    And the Price is "is_null"
    And the Rate is "is_null"
    When I try to create the Amount Detail
    Then Amount Detail is not created

  Scenario: AmountDetail.N18: 
    And the Amount Type is "VATX"
    And the Price is "negative"
    And the Rate is "is_null"
    When I try to create the Amount Detail
    Then Amount Detail is not created

  Scenario: AmountDetail.P24: 
    And the Amount Type is "VATX"
    And the Price is "zero"
    And the Rate is "is_null"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.N19: 
    And the Amount Type is "VATX"
    And the Price is "zero"
    And the Rate is "negative"
    When I try to create the Amount Detail
    Then Amount Detail is not created

  Scenario: AmountDetail.P25: 
    And the Amount Type is "VATX"
    And the Price is "zero"
    And the Rate is "zero"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.P26: 
    And the Amount Type is "VATX"
    And the Price is "zero"
    And the Rate is "positive"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.P27: 
    And the Amount Type is "VATX"
    And the Price is "positive"
    And the Rate is "is_null"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.N20: 
    And the Amount Type is "VATX"
    And the Price is "positive"
    And the Rate is "negative"
    When I try to create the Amount Detail
    Then Amount Detail is not created

  Scenario: AmountDetail.P28: 
    And the Amount Type is "VATX"
    And the Price is "positive"
    And the Rate is "zero"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

  Scenario: AmountDetail.P29: 
    And the Amount Type is "VATX"
    And the Price is "positive"
    And the Rate is "positive"
    When I try to create the Amount Detail
    Then Amount Detail is created successfully

