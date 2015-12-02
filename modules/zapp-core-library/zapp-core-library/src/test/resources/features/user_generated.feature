Feature: User

  Scenario: User.N0
    When I try to create the User
    Then the User is not created

  Scenario: User.N1
    Given the user's First Name is ""
    When I try to create the User
    Then the User is not created

  Scenario: User.P0
    Given the user's First Name is "John"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P1
    Given the user's First Name is "John"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P2
    Given the user's First Name is "John"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P3
    Given the user's First Name is "John"
    And the user's Last Name is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P4
    Given the user's First Name is "John"
    And the user's Last Name is ""
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P5
    Given the user's First Name is "John"
    And the user's Last Name is ""
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P6
    Given the user's First Name is "John"
    And the user's Last Name is "Doe"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P7
    Given the user's First Name is "John"
    And the user's Last Name is "Doe"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P8
    Given the user's First Name is "John"
    And the user's Last Name is "Doe"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P9
    Given the user's First Name is "John"
    And the user's Middle Name is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P10
    Given the user's First Name is "John"
    And the user's Middle Name is ""
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P11
    Given the user's First Name is "John"
    And the user's Middle Name is ""
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P12
    Given the user's First Name is "John"
    And the user's Middle Name is ""
    And the user's Last Name is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P13
    Given the user's First Name is "John"
    And the user's Middle Name is ""
    And the user's Last Name is ""
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P14
    Given the user's First Name is "John"
    And the user's Middle Name is ""
    And the user's Last Name is ""
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P15
    Given the user's First Name is "John"
    And the user's Middle Name is ""
    And the user's Last Name is "Doe"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P16
    Given the user's First Name is "John"
    And the user's Middle Name is ""
    And the user's Last Name is "Doe"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P17
    Given the user's First Name is "John"
    And the user's Middle Name is ""
    And the user's Last Name is "Doe"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P18
    Given the user's First Name is "John"
    And the user's Middle Name is "Thomas"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P19
    Given the user's First Name is "John"
    And the user's Middle Name is "Thomas"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P20
    Given the user's First Name is "John"
    And the user's Middle Name is "Thomas"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P21
    Given the user's First Name is "John"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P22
    Given the user's First Name is "John"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is ""
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P23
    Given the user's First Name is "John"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is ""
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P24
    Given the user's First Name is "John"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is "Doe"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P25
    Given the user's First Name is "John"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is "Doe"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P26
    Given the user's First Name is "John"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is "Doe"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P27
    Given the user's First Name is "John"
    And the user's Title is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P28
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P29
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P30
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Last Name is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P31
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Last Name is ""
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P32
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Last Name is ""
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P33
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Last Name is "Doe"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P34
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Last Name is "Doe"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P35
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Last Name is "Doe"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P36
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P37
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is ""
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P38
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is ""
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P39
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is ""
    And the user's Last Name is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P40
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is ""
    And the user's Last Name is ""
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P41
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is ""
    And the user's Last Name is ""
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P42
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is ""
    And the user's Last Name is "Doe"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P43
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is ""
    And the user's Last Name is "Doe"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P44
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is ""
    And the user's Last Name is "Doe"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P45
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is "Thomas"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P46
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is "Thomas"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P47
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is "Thomas"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P48
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is "Thomas"
    And the user's Last Name is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P49
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is "Thomas"
    And the user's Last Name is ""
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P50
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is "Thomas"
    And the user's Last Name is ""
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P51
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is "Thomas"
    And the user's Last Name is "Doe"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P52
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is "Thomas"
    And the user's Last Name is "Doe"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P53
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is "Thomas"
    And the user's Last Name is "Doe"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P54
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P55
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P56
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P57
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Last Name is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P58
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Last Name is ""
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P59
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Last Name is ""
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P60
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Last Name is "Doe"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P61
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Last Name is "Doe"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P62
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Last Name is "Doe"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P63
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P64
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is ""
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P65
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is ""
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P66
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is ""
    And the user's Last Name is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P67
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is ""
    And the user's Last Name is ""
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P68
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is ""
    And the user's Last Name is ""
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P69
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is ""
    And the user's Last Name is "Doe"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P70
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is ""
    And the user's Last Name is "Doe"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P71
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is ""
    And the user's Last Name is "Doe"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P72
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is "Thomas"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P73
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is "Thomas"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P74
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is "Thomas"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P75
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P76
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is ""
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P77
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is ""
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P78
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is "Doe"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P79
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is "Doe"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P80
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is "Doe"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.N2
    Given the user's First Name is "John"
    And the user's Email Address is ""
    When I try to create the User
    Then the User is not created

  Scenario: User.N3
    Given the user's First Name is "John"
    And the user's Email Address is "@a.com"
    When I try to create the User
    Then the User is not created

  Scenario: User.P81
    Given the user's First Name is "John"
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P82
    Given the user's First Name is "John"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P83
    Given the user's First Name is "John"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P84
    Given the user's First Name is "John"
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P85
    Given the user's First Name is "John"
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P86
    Given the user's First Name is "John"
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P87
    Given the user's First Name is "John"
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P88
    Given the user's First Name is "John"
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P89
    Given the user's First Name is "John"
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P90
    Given the user's First Name is "John"
    And the user's Middle Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P91
    Given the user's First Name is "John"
    And the user's Middle Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P92
    Given the user's First Name is "John"
    And the user's Middle Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P93
    Given the user's First Name is "John"
    And the user's Middle Name is ""
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P94
    Given the user's First Name is "John"
    And the user's Middle Name is ""
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P95
    Given the user's First Name is "John"
    And the user's Middle Name is ""
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P96
    Given the user's First Name is "John"
    And the user's Middle Name is ""
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P97
    Given the user's First Name is "John"
    And the user's Middle Name is ""
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P98
    Given the user's First Name is "John"
    And the user's Middle Name is ""
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P99
    Given the user's First Name is "John"
    And the user's Middle Name is "Thomas"
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P100
    Given the user's First Name is "John"
    And the user's Middle Name is "Thomas"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P101
    Given the user's First Name is "John"
    And the user's Middle Name is "Thomas"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P102
    Given the user's First Name is "John"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P103
    Given the user's First Name is "John"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P104
    Given the user's First Name is "John"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P105
    Given the user's First Name is "John"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P106
    Given the user's First Name is "John"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P107
    Given the user's First Name is "John"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P108
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P109
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P110
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P111
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P112
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P113
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P114
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P115
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P116
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P117
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P118
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P119
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P120
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is ""
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P121
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is ""
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P122
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is ""
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P123
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is ""
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P124
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is ""
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P125
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is ""
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P126
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is "Thomas"
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P127
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is "Thomas"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P128
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is "Thomas"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P129
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is "Thomas"
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P130
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is "Thomas"
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P131
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is "Thomas"
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P132
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is "Thomas"
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P133
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is "Thomas"
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P134
    Given the user's First Name is "John"
    And the user's Title is ""
    And the user's Middle Name is "Thomas"
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P135
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P136
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P137
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P138
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P139
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P140
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P141
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P142
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P143
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P144
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P145
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P146
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P147
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is ""
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P148
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is ""
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P149
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is ""
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P150
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is ""
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P151
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is ""
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P152
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is ""
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P153
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is "Thomas"
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P154
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is "Thomas"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P155
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is "Thomas"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P156
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P157
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P158
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is ""
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P159
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P160
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is ""
    When I try to create the User
    Then the User is created successfully

  Scenario: User.P161
    Given the user's First Name is "John"
    And the user's Title is "Mr"
    And the user's Middle Name is "Thomas"
    And the user's Last Name is "Doe"
    And the user's Email Address is "hi@merchant.co.uk"
    And the user's Phone Number is "7766554433"
    When I try to create the User
    Then the User is created successfully

