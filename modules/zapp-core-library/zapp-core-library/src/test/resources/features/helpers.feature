@helpers
Feature: Test the uncovered methods (setters/getters/toString ...).

  Scenario: Two bank accounts are equal
    Given I have the following bank accounts:
    | name                | number     | sortCode    | balance    | availableFunds    |
    | Olivia Kensington   | 12345678   | 12-12-12    | valid      | valid             |
    | Olivia Kensington   | 12345678   | 12-12-12    | valid      | valid             |
    Then they are equal when I compare them
    And the string representation should be "BankAccount[Olivia Kensington, 12345678, 12-12-12, 3p, 3p]"
    And the hash codes should be equal

  Scenario: Two bank accounts are equal
    Given I have the following bank accounts:
    | name                | number     | sortCode    | balance    | availableFunds    |
    | Olivia Kensington   | 12345678   | 12-12-12    | valid      | valid             |
    | Olivia Kensington   | 12345678   | 12-12-12    | valid      | valid             |
    When I remove one of the required fields from the "BankAccount"
    Then they are not equal when I compare them

  Scenario: Bill Details can be created using setters
    Given I have the following bill details:
    | accountId    | reference     | periodFrom    | periodTo      |
    | 12345678     | ref1234abcd   | 2015-01-01    | 2015-01-01    |
    Then "Bill Details" is created successfully

  Scenario: CurrencyAmount can be created using setters
    Given I have the following currency amount:
    | value    | currencyCode    |
    | 123      | GBP             |
    Then "CurrencyAmount" is created successfully

  Scenario: Two delivery addresses with the same data should be equal
    Given I have two identical instances of "DeliveryAddress"
    Then they are equal when I compare them

  Scenario: Two delivery addresses with the same data should be equal
    Given I have two identical instances of "DeliveryAddress"
    When I change identifier of one of the addresses
    Then they are not equal when I compare them

  Scenario: Merchant can be created using setters
    Given I have the following merchants:
    | identifier    | name            | email                | phone       | address  | website    |
    | identifier    | merchantName    | hi@merchant.co.uk    | 12345678    | valid    | website    |
    Then "Merchant" is created successfully

  Scenario: PaymentAuth can be created using setters
    Given I have the following PaymentAuth's:
    | checkoutType    | deliveryType    | bankAccount    | user    | finalAmount    | deliveryAddress    | transactionStatus    | payconnectEnabled    |
    | QUICK           | DIGITAL         | valid          | valid   | valid          | valid              | IN_PROGRESS          | false                |
    Then "PaymentAuth" is created successfully

  Scenario: PaymentRequest can be created using setters
    Given I have the following PaymentRequest's:
    |rtpType    | paymentType    | checkoutType    | deliveryType    | messageType | user    | billDetails    | amount    | merchant    | address    | merchantCallbackUrl    | acrType   | defrdRTPExpDateTime     | defrdRTPAgrmtAmount     | defrdRTPMaxAgrdAmount     | defrdAmountDetails      |
    |DEFERRED   | SMB            | QUICK           | DIGITAL         | GENERAL     | valid   | valid          | valid     | valid       | valid      | merchantCallbackUrl    | NONE      | 1970-01-01              | valid                   | valid                     | valid                   |
    Then "PaymentRequest" is created successfully

  Scenario: Two transactions are equal if transactionId's are equal
    Given I have the following transactions:
    | brn    | transactionId | paymentRequest | paymentAuth | confirmationExpiryInterval | retrievalExpiryInterval | retrievalMethod | merchantCallbackUrl | askForPayconnect |initiatorIsAuthorizer  |settlementRetrievalId  |notificationSent |
    | ABCDEF | valid         | valid          | valid       | 300                        | 500                     | INVOCATION      | merchantCallbackUrl | false            |false                  |10                     |false            |
    | ABCDEF | valid         | valid          | valid       | 300                        | 500                     | INVOCATION      | merchantCallbackUrl | false            |false                  |10                     |false            |
    Then they are equal when I compare them
    And the string representation should be "Transaction[brn:ABCDEF, aptId:aptId]"

  Scenario: Two transactions are equal if brn codes are equal
    Given I have the following transactions:
    | brn    | transactionId | paymentRequest | paymentAuth | confirmationExpiryInterval | retrievalExpiryInterval | retrievalMethod | merchantCallbackUrl | askForPayconnect |
    | ABCDEF | valid         | valid          | valid       | 300                        | 500                     | INVOCATION      | merchantCallbackUrl | false            |
    | ABCDEF | valid         | valid          | valid       | 300                        | 500                     | INVOCATION      | merchantCallbackUrl | false            |
    When I remove the transactionId field
    Then they are equal when I compare them

  Scenario: Transaction status defaults to IN_PROGRESS
    Given I have the following transactions:
    | brn    | retrievalExpiryInterval | retrievalMethod |
    | ABCDEF | 500                     | BRN             |
    Then transaction status value returns "IN_PROGRESS"

  Scenario: Retrieve the Transaction status from Transaction
    Given I have the following transactions:
    | brn    | retrievalExpiryInterval | retrievalMethod |
    | ABCDEF | 500                     | BRN             |
    When I set the transaction status for PaymentAuth to be "DECLINED"
    Then transaction status value returns "DECLINED"

  Scenario: TransactionId can be created using setters
    Given I have the following transaction ids:
    | aptId    | aptrId    |
    | aptId1   | aptrId1   |
    Then "PaymentRequest" is created successfully

  Scenario: User can be created using setters
    Given I have the following users:
    | firstName    | lastName    | middleName    | title    | email               | phone        |
    | John         | Doe         | Thomas        | Mr       | hi@merchant.co.uk   | 7766554433   |
    Then "User" is created successfully
    And the string representation should be "User[firstName: John, email: hi@merchant.co.uk]"

  Scenario: AmountDetail can be created using setters
    Given I have the following amount detail:
    | type       | description    | price    | rate     |
    | CSHB       | desc           | 10       | 10       |
    Then "AmountDetail" is created successfully

  Scenario: All models should implement IValidatable interface
    Given the "com.zapp.core.IValidatable" interface should be implemented by the following models:
    | com.zapp.core.Address           |
    | com.zapp.core.BankAccount       |
    | com.zapp.core.BillDetails       |
    | com.zapp.core.CurrencyAmount    |
    | com.zapp.core.DeliveryAddress   |
    | com.zapp.core.Merchant          |
    | com.zapp.core.PaymentAuth       |
    | com.zapp.core.PaymentRequest    |
    | com.zapp.core.AmountDetail      |
    | com.zapp.core.Transaction       |
    | com.zapp.core.TransactionId     |
    | com.zapp.core.User              |