Feature: Test Currency Amount logic.

  Scenario: toStringWithPence.1.GBP
    Given the Value is "1"
    And the Currency Code is "GBP"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully
    And the Display String of the Currency Amount with pence formatting is "1p"

  Scenario: toString.1.GBP
    Given the Value is "1"
    And the Currency Code is "GBP"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully
    And the Display String of the Currency Amount is "£0.01"

  Scenario: toStringWithPence.10.GBP
    Given the Value is "10"
    And the Currency Code is "GBP"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully
    And the Display String of the Currency Amount with pence formatting is "10p"

  Scenario: toString.10.GBP
    Given the Value is "10"
    And the Currency Code is "GBP"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully
    And the Display String of the Currency Amount is "£0.10"

  Scenario: toStringWithPence.99.GBP
    Given the Value is "99"
    And the Currency Code is "GBP"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully
    And the Display String of the Currency Amount with pence formatting is "99p"

  Scenario: toString.99.GBP
    Given the Value is "99"
    And the Currency Code is "GBP"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully
    And the Display String of the Currency Amount is "£0.99"

  Scenario: toString.100.GBP
    Given the Value is "100"
    And the Currency Code is "GBP"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully
    And the Display String of the Currency Amount is "£1.00"

  Scenario: toString.101.GBP
    Given the Value is "101"
    And the Currency Code is "GBP"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully
    And the Display String of the Currency Amount is "£1.01"

  Scenario: toString.110.GBP
    Given the Value is "110"
    And the Currency Code is "GBP"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully
    And the Display String of the Currency Amount is "£1.10"

  Scenario: toString.999.GBP
    Given the Value is "999"
    And the Currency Code is "GBP"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully
    And the Display String of the Currency Amount is "£9.99"

  Scenario: toString.1000.GBP
    Given the Value is "1000"
    And the Currency Code is "GBP"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully
    And the Display String of the Currency Amount is "£10.00"

  Scenario: toString -100 GBP
    Given the Value is "-100"
    And the Currency Code is "GBP"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully
    And the Display String of the Currency Amount is "-£1.00"

  Scenario: toStringWithPence -1 GBP
    Given the Value is "-1"
    And the Currency Code is "GBP"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully
    And the Display String of the Currency Amount with pence formatting is "-1p"

  Scenario: toString -1 GBP
    Given the Value is "-1"
    And the Currency Code is "GBP"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully
    And the Display String of the Currency Amount is "-£0.01"

  Scenario: toString null
    Given the Value is "1"
    And the Currency Code is "GBP"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully
    When I set the Currency Amount value to null
    Then the Display String of the Currency Amount returns a null value

  Scenario: toString throws Exception for invalid currency code
    Given the Value is "1"
    And the Currency Code is "GBP"
    When I try to create the Currency Amount
    Then the Currency Amount is created successfully
    When I set an invalid Currency Code like "XXX"
    When I want to retrieve the Display String
    Then an IllegalArgumentException is thrown

  #
  # add
  #

  Scenario: add null
    Given the current Currency Amount is "1.03 GBP"
    When I try to add a second currency amount "" to the current Currency Amount
    Then the second currency amount adding is failed

  Scenario: add to Currency Amount with null currency code
    Given the current Currency Amount is "1.03 GBP"
    And I set "null" to Currency Code
    When I try to add a second currency amount "2.30 GBP" to the current Currency Amount
    Then an IllegalArgumentException is thrown

  Scenario: add to Currency Amount with different currency code
    Given the current Currency Amount is "1.03 GBP"
    And I set "EUR" to Currency Code
    When I try to add a second currency amount "2.30 GBP" to the current Currency Amount
    Then an IllegalArgumentException is thrown

  Scenario: add 1.11 EUR to 2.22 GBP
    Given the current Currency Amount is "2.22 GBP"
    When I try to add a second currency amount "1.11;EUR" to the current Currency Amount
    Then the second currency amount adding is failed

  Scenario: add 2.30 GBP to 1.03 GBP
    Given the current Currency Amount is "1.03 GBP"
    When I try to add a second currency amount "2.30 GBP" to the current Currency Amount
    Then the second currency amount is added successfully
    And the Display String of the Currency Amount is "£3.33"

  Scenario: add -1.03 GBP to 1.03 GBP using pence formatting
    Given the current Currency Amount is "1.03 GBP"
    When I try to add a second currency amount "-1.03 GBP" to the current Currency Amount
    Then the second currency amount is added successfully
    And the Display String of the Currency Amount with pence formatting is "0p"

  Scenario: add -1.03 GBP to 1.03 GBP
    Given the current Currency Amount is "1.03 GBP"
    When I try to add a second currency amount "-1.03 GBP" to the current Currency Amount
    Then the second currency amount is added successfully
    And the Display String of the Currency Amount is "£0.00"

  #
  # subtract
  #

  Scenario: subtract null
    Given the current Currency Amount is "1.03 GBP"
    When I try to subtract a second currency amount "" from the current Currency Amount
    Then the second currency amount subtracting is failed

  Scenario: subtract from Currency Amount with null currency code
    Given the current Currency Amount is "1.03 GBP"
    And I set "null" to Currency Code
    When I try to subtract a second currency amount "2.30 GBP" from the current Currency Amount
    Then the second currency amount subtracting is failed

  Scenario: subtract from Currency Amount with different currency code
    Given the current Currency Amount is "1.03 GBP"
    And I set "EUR" to Currency Code
    When I try to subtract a second currency amount "2.30 GBP" from the current Currency Amount
    Then an IllegalArgumentException is thrown

  Scenario: subtract 1.11 EUR from 2.22 GBP
    Given the current Currency Amount is "2.22 GBP"
    When I try to subtract a second currency amount "1.11;EUR" from the current Currency Amount
    Then the second currency amount subtracting is failed

  Scenario: subtract 2.30 GBP from 1.03 GBP
    Given the current Currency Amount is "1.03 GBP"
    When I try to subtract a second currency amount "2.30 GBP" from the current Currency Amount
    Then the second currency amount is subtracted successfully
    And the Display String of the Currency Amount is "-£1.27"

  Scenario: subtract -1.03 GBP from 1.03 GBP
    Given the current Currency Amount is "1.03 GBP"
    When I try to subtract a second currency amount "-1.03 GBP" from the current Currency Amount
    Then the second currency amount is subtracted successfully
    And the Display String of the Currency Amount is "£2.06"

  #
  # multiplyByQuantity
  #

  Scenario: multiply by quantity null
    Given the current Currency Amount is "2.29 GBP"
    When I try to multiply the currency amount by quantity ""
    Then the currency amount multiplication is failed

  Scenario: multiply 0.73 GBP by 0 using pence formatting
    Given the current Currency Amount is "0.73 GBP"
    When I try to multiply the currency amount by quantity "0"
    Then the currency amount is multiplied successfully
    And the Display String of the Currency Amount with pence formatting is "0p"

  Scenario: multiply 0.73 GBP by 0
    Given the current Currency Amount is "0.73 GBP"
    When I try to multiply the currency amount by quantity "0"
    Then the currency amount is multiplied successfully
    And the Display String of the Currency Amount is "£0.00"

  Scenario: multiply 1.29 GBP by -1
    Given the current Currency Amount is "1.29 GBP"
    When I try to multiply the currency amount by quantity "-1"
    Then the currency amount is multiplied successfully
    And the Display String of the Currency Amount is "-£1.29"

  Scenario: multiply 1.49 GBP by 3
    Given the current Currency Amount is "1.49 GBP"
    When I try to multiply the currency amount by quantity "3"
    Then the currency amount is multiplied successfully
    And the Display String of the Currency Amount is "£4.47"

  Scenario: multiply 10000 GBP by 10
    Given the current Currency Amount is "10000.00 GBP"
    When I try to multiply the currency amount by quantity "10"
    Then the currency amount is multiplied successfully
    And the Display String of the Currency Amount is "£100000.00"

  Scenario: multiply -7.50 GBP by 2
    Given the current Currency Amount is "-7.00 GBP"
    When I try to multiply the currency amount by quantity "2"
    Then the currency amount is multiplied successfully
    And the Display String of the Currency Amount is "-£14.00"

  Scenario: multiply -3.21 GBP by -5
    Given the current Currency Amount is "-3.21 GBP"
    When I try to multiply the currency amount by quantity "-5"
    Then the currency amount is multiplied successfully
    And the Display String of the Currency Amount is "£16.05"
