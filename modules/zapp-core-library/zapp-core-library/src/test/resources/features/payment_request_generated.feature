Feature: Payment Request

  Scenario: PaymentRequest.N0: 
    Given the Merchant is "valid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N1: 
    And the RTP Type is "IMMEDIATE"
    And the Merchant is "valid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N2: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "ADDRESS"
    And the Merchant is "valid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N3: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "ADDRESS"
    And the Merchant is "valid"
    And the Payment Type is "BILL_PAY"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N4: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "ADDRESS"
    And the Merchant is "valid"
    And the Payment Type is "INSTANT_PAYMENT"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N5: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "ADDRESS"
    And the Merchant is "valid"
    And the Payment Type is "SMB"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N6: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "ADDRESS"
    And the Merchant is "invalid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N7: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "ADDRESS"
    And the Merchant is "invalid"
    And the Payment Type is "BILL_PAY"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N8: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "ADDRESS"
    And the Merchant is "invalid"
    And the Payment Type is "INSTANT_PAYMENT"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N9: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "ADDRESS"
    And the Merchant is "invalid"
    And the Payment Type is "SMB"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N10: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "ADDRESS"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N11: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "COLLECT_IN_STORE"
    And the Merchant is "valid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N12: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "COLLECT_IN_STORE"
    And the Merchant is "valid"
    And the Payment Type is "BILL_PAY"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N13: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "COLLECT_IN_STORE"
    And the Merchant is "valid"
    And the Payment Type is "INSTANT_PAYMENT"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N14: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "COLLECT_IN_STORE"
    And the Merchant is "valid"
    And the Payment Type is "SMB"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N15: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "COLLECT_IN_STORE"
    And the Merchant is "invalid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N16: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "COLLECT_IN_STORE"
    And the Merchant is "invalid"
    And the Payment Type is "BILL_PAY"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N17: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "COLLECT_IN_STORE"
    And the Merchant is "invalid"
    And the Payment Type is "INSTANT_PAYMENT"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N18: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "COLLECT_IN_STORE"
    And the Merchant is "invalid"
    And the Payment Type is "SMB"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N19: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "COLLECT_IN_STORE"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N20: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "DIGITAL"
    And the Merchant is "valid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N21: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "DIGITAL"
    And the Merchant is "valid"
    And the Payment Type is "BILL_PAY"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N22: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "DIGITAL"
    And the Merchant is "valid"
    And the Payment Type is "INSTANT_PAYMENT"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N23: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "DIGITAL"
    And the Merchant is "valid"
    And the Payment Type is "SMB"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N24: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "DIGITAL"
    And the Merchant is "invalid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N25: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "DIGITAL"
    And the Merchant is "invalid"
    And the Payment Type is "BILL_PAY"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N26: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "DIGITAL"
    And the Merchant is "invalid"
    And the Payment Type is "INSTANT_PAYMENT"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N27: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "DIGITAL"
    And the Merchant is "invalid"
    And the Payment Type is "SMB"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N28: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "DIGITAL"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N29: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "FACE_2_FACE"
    And the Merchant is "valid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N30: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "FACE_2_FACE"
    And the Merchant is "valid"
    And the Payment Type is "BILL_PAY"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N31: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "FACE_2_FACE"
    And the Merchant is "valid"
    And the Payment Type is "INSTANT_PAYMENT"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N32: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "FACE_2_FACE"
    And the Merchant is "valid"
    And the Payment Type is "SMB"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N33: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "FACE_2_FACE"
    And the Merchant is "invalid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N34: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "FACE_2_FACE"
    And the Merchant is "invalid"
    And the Payment Type is "BILL_PAY"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N35: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "FACE_2_FACE"
    And the Merchant is "invalid"
    And the Payment Type is "INSTANT_PAYMENT"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N36: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "FACE_2_FACE"
    And the Merchant is "invalid"
    And the Payment Type is "SMB"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N37: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "FACE_2_FACE"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N38: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "SERVICE"
    And the Merchant is "valid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N39: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "SERVICE"
    And the Merchant is "valid"
    And the Payment Type is "BILL_PAY"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N40: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "SERVICE"
    And the Merchant is "valid"
    And the Payment Type is "INSTANT_PAYMENT"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N41: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "SERVICE"
    And the Merchant is "valid"
    And the Payment Type is "SMB"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N42: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "SERVICE"
    And the Merchant is "invalid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N43: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "SERVICE"
    And the Merchant is "invalid"
    And the Payment Type is "BILL_PAY"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N44: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "SERVICE"
    And the Merchant is "invalid"
    And the Payment Type is "INSTANT_PAYMENT"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N45: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "SERVICE"
    And the Merchant is "invalid"
    And the Payment Type is "SMB"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N46: 
    And the RTP Type is "IMMEDIATE"
    And the Delivery Type is "SERVICE"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N47: 
    And the RTP Type is "DEFERRED"
    And the Merchant is "valid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N48: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "ADDRESS"
    And the Merchant is "valid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N49: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "ADDRESS"
    And the Merchant is "valid"
    And the Payment Type is "BILL_PAY"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N50: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "ADDRESS"
    And the Merchant is "valid"
    And the Payment Type is "INSTANT_PAYMENT"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N51: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "ADDRESS"
    And the Merchant is "valid"
    And the Payment Type is "SMB"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N52: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "ADDRESS"
    And the Merchant is "invalid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N53: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "ADDRESS"
    And the Merchant is "invalid"
    And the Payment Type is "BILL_PAY"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N54: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "ADDRESS"
    And the Merchant is "invalid"
    And the Payment Type is "INSTANT_PAYMENT"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N55: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "ADDRESS"
    And the Merchant is "invalid"
    And the Payment Type is "SMB"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N56: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "ADDRESS"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N57: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "COLLECT_IN_STORE"
    And the Merchant is "valid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N58: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "COLLECT_IN_STORE"
    And the Merchant is "valid"
    And the Payment Type is "BILL_PAY"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N59: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "COLLECT_IN_STORE"
    And the Merchant is "valid"
    And the Payment Type is "INSTANT_PAYMENT"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N60: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "COLLECT_IN_STORE"
    And the Merchant is "valid"
    And the Payment Type is "SMB"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N61: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "COLLECT_IN_STORE"
    And the Merchant is "invalid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N62: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "COLLECT_IN_STORE"
    And the Merchant is "invalid"
    And the Payment Type is "BILL_PAY"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N63: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "COLLECT_IN_STORE"
    And the Merchant is "invalid"
    And the Payment Type is "INSTANT_PAYMENT"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N64: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "COLLECT_IN_STORE"
    And the Merchant is "invalid"
    And the Payment Type is "SMB"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N65: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "COLLECT_IN_STORE"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N66: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "DIGITAL"
    And the Merchant is "valid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N67: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "DIGITAL"
    And the Merchant is "valid"
    And the Payment Type is "BILL_PAY"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N68: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "DIGITAL"
    And the Merchant is "valid"
    And the Payment Type is "INSTANT_PAYMENT"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N69: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "DIGITAL"
    And the Merchant is "valid"
    And the Payment Type is "SMB"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N70: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "DIGITAL"
    And the Merchant is "invalid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N71: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "DIGITAL"
    And the Merchant is "invalid"
    And the Payment Type is "BILL_PAY"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N72: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "DIGITAL"
    And the Merchant is "invalid"
    And the Payment Type is "INSTANT_PAYMENT"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N73: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "DIGITAL"
    And the Merchant is "invalid"
    And the Payment Type is "SMB"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N74: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "DIGITAL"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N75: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "FACE_2_FACE"
    And the Merchant is "valid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N76: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "FACE_2_FACE"
    And the Merchant is "valid"
    And the Payment Type is "BILL_PAY"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N77: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "FACE_2_FACE"
    And the Merchant is "valid"
    And the Payment Type is "INSTANT_PAYMENT"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N78: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "FACE_2_FACE"
    And the Merchant is "valid"
    And the Payment Type is "SMB"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N79: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "FACE_2_FACE"
    And the Merchant is "invalid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N80: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "FACE_2_FACE"
    And the Merchant is "invalid"
    And the Payment Type is "BILL_PAY"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N81: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "FACE_2_FACE"
    And the Merchant is "invalid"
    And the Payment Type is "INSTANT_PAYMENT"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N82: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "FACE_2_FACE"
    And the Merchant is "invalid"
    And the Payment Type is "SMB"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N83: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "FACE_2_FACE"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N84: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "SERVICE"
    And the Merchant is "valid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N85: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "SERVICE"
    And the Merchant is "valid"
    And the Payment Type is "BILL_PAY"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N86: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "SERVICE"
    And the Merchant is "valid"
    And the Payment Type is "INSTANT_PAYMENT"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N87: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "SERVICE"
    And the Merchant is "valid"
    And the Payment Type is "SMB"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N88: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "SERVICE"
    And the Merchant is "invalid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N89: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "SERVICE"
    And the Merchant is "invalid"
    And the Payment Type is "BILL_PAY"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N90: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "SERVICE"
    And the Merchant is "invalid"
    And the Payment Type is "INSTANT_PAYMENT"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N91: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "SERVICE"
    And the Merchant is "invalid"
    And the Payment Type is "SMB"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N92: 
    And the RTP Type is "DEFERRED"
    And the Delivery Type is "SERVICE"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N93: 
    And the RTP Type is "IMMEDIATE"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N94: 
    And the RTP Type is "IMMEDIATE"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N95: 
    And the RTP Type is "IMMEDIATE"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N96: 
    And the RTP Type is "IMMEDIATE"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N97: 
    And the RTP Type is "IMMEDIATE"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N98: 
    And the RTP Type is "IMMEDIATE"
    And the Deferred request to pay agreement amount is "invalid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N99: 
    And the RTP Type is "IMMEDIATE"
    And the Deferred request to pay agreement amount is "negative"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N100: 
    And the RTP Type is "IMMEDIATE"
    And the Deferred request to pay agreement amount is "zero"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N101: 
    And the RTP Type is "IMMEDIATE"
    And the Deferred request to pay agreement amount is "positive"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N102: 
    And the RTP Type is "IMMEDIATE"
    And the Deferred request to pay expiry datetime is "2015-01-01"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N103: 
    And the RTP Type is "IMMEDIATE"
    And the Authentication check required type is "NONE"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N104: 
    And the RTP Type is "IMMEDIATE"
    And the Authentication check required type is "FUNDS_CHECK"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N105: 
    And the RTP Type is "IMMEDIATE"
    And the Authentication check required type is "FUNDS_GUARANTEE"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N106: 
    And the RTP Type is "IMMEDIATE"
    And the Authentication check required type is "LATER"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N107: 
    And the RTP Type is "DEFERRED"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N108: 
    And the RTP Type is "DEFERRED"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N109: 
    And the RTP Type is "DEFERRED"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N110: 
    And the RTP Type is "DEFERRED"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N111: 
    And the RTP Type is "DEFERRED"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N112: 
    And the RTP Type is "IMMEDIATE"
    And the Payment Type is "BILL_PAY"
    And the Checkout Type is "NORMAL"
    And the Bill Details is "invalid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N113: 
    And the RTP Type is "IMMEDIATE"
    And the Payment Type is "BILL_PAY"
    And the Checkout Type is "NORMAL"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N114: 
    And the RTP Type is "IMMEDIATE"
    And the Payment Type is "BILL_PAY"
    And the Checkout Type is "QUICK"
    And the Bill Details is "valid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N115: 
    And the RTP Type is "IMMEDIATE"
    And the Payment Type is "INSTANT_PAYMENT"
    And the Checkout Type is "NORMAL"
    And the Bill Details is "valid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N116: 
    And the RTP Type is "IMMEDIATE"
    And the Payment Type is "INSTANT_PAYMENT"
    And the Checkout Type is "NORMAL"
    And the Bill Details is "invalid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N117: 
    And the RTP Type is "IMMEDIATE"
    And the Payment Type is "SMB"
    And the Checkout Type is "NORMAL"
    And the Bill Details is "valid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N118: 
    And the RTP Type is "IMMEDIATE"
    And the Payment Type is "SMB"
    And the Checkout Type is "NORMAL"
    And the Bill Details is "invalid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N119: 
    And the RTP Type is "IMMEDIATE"
    And the Payment Type is "SMB"
    And the Checkout Type is "QUICK"
    And the Bill Details is "valid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N120: 
    And the RTP Type is "DEFERRED"
    And the Payment Type is "BILL_PAY"
    And the Checkout Type is "NORMAL"
    And the Bill Details is "valid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N121: 
    And the RTP Type is "DEFERRED"
    And the Payment Type is "SMB"
    And the Checkout Type is "NORMAL"
    And the Bill Details is "valid"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N122: 
    And the Checkout Type is "QUICK"
    And the Address is "valid"
    And the Delivery Type is "ADDRESS"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N123: 
    And the Checkout Type is "QUICK"
    And the Address is "invalid"
    And the Delivery Type is "ADDRESS"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N124: 
    And the Checkout Type is "QUICK"
    And the Delivery Type is "COLLECT_IN_STORE"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N125: 
    And the Checkout Type is "QUICK"
    And the Delivery Type is "FACE_2_FACE"
    When I try to create the Payment Request
    Then Payment Request is not created

  Scenario: PaymentRequest.N126: 
    And the Checkout Type is "QUICK"
    And the Delivery Type is "SERVICE"
    When I try to create the Payment Request
    Then Payment Request is not created

