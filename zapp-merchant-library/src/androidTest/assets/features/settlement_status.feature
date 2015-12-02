Feature: MerchantService.getSettlementStatus

Scenario: Get the settlement status successfully
    Given the merchant id is "MerchantDemo2"
    And the settlement retrieval id is "settlementRetrievalId"
    And the Merchant Delegate get settlement status endpoint returns settlement status "AUTHORISED"
    When I try to get settlement status
    Then the settlement status is retrieved successfully

Scenario: Get the settlement status with error "NETWORK_ERROR"
    Given the merchant id is "MerchantDemo2"
    And the settlement retrieval id is "settlementRetrievalId"
    And the Merchant Delegate get settlement status endpoint responds with failure "NETWORK_ERROR"
    When I try to get settlement status
    Then the settlement status fails with error "NETWORK_ERROR"

Scenario: Get the settlement status with error "INVALID_SETTLEMENT_RETRIEVAL_ID"
    Given the merchant id is "MerchantDemo2"
    When I try to get settlement status
    Then the settlement status fails with error "INVALID_SETTLEMENT_RETRIEVAL_ID"

Scenario: Get the settlement status with error "INVALID_MERCHANT_ID"
    Given the settlement retrieval id is "settlementRetrievalId"
    When I try to get settlement status
    Then the settlement status fails with error "INVALID_MERCHANT_ID"