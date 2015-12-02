Feature: Address

  Scenario: Address.N0
    Given the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is not created

  Scenario: Address.P0
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P1
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P2
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P3
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P4
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P5
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 6 is "line6"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P6
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P7
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P8
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P9
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P10
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 5 is "line5"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P11
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 5 is "line5"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P12
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P13
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 6 is "line6"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P14
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P15
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P16
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P17
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P18
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P19
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P20
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 4 is "line4"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P21
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 4 is "line4"
    And the Line 6 is "line6"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P22
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 4 is "line4"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P23
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 4 is "line4"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P24
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P25
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P26
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 5 is "line5"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P27
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 5 is "line5"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P28
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P29
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 6 is "line6"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P30
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P31
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P32
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P33
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P34
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P35
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P36
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P37
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 6 is "line6"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P38
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P39
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P40
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P41
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P42
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 5 is "line5"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P43
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 5 is "line5"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P44
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P45
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 6 is "line6"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P46
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P47
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P48
    Given the Line 1 is "2 Puddle Dock"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P49
    Given the Line 1 is "2 Puddle Dock"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P50
    Given the Line 1 is "2 Puddle Dock"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P51
    Given the Line 1 is "2 Puddle Dock"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P52
    Given the Line 1 is "2 Puddle Dock"
    And the Line 4 is "line4"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P53
    Given the Line 1 is "2 Puddle Dock"
    And the Line 4 is "line4"
    And the Line 6 is "line6"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P54
    Given the Line 1 is "2 Puddle Dock"
    And the Line 4 is "line4"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P55
    Given the Line 1 is "2 Puddle Dock"
    And the Line 4 is "line4"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P56
    Given the Line 1 is "2 Puddle Dock"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P57
    Given the Line 1 is "2 Puddle Dock"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P58
    Given the Line 1 is "2 Puddle Dock"
    And the Line 5 is "line5"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P59
    Given the Line 1 is "2 Puddle Dock"
    And the Line 5 is "line5"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P60
    Given the Line 1 is "2 Puddle Dock"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P61
    Given the Line 1 is "2 Puddle Dock"
    And the Line 6 is "line6"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P62
    Given the Line 1 is "2 Puddle Dock"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P63
    Given the Line 1 is "2 Puddle Dock"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P64
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P65
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P66
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P67
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P68
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P69
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 6 is "line6"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P70
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P71
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P72
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P73
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P74
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 5 is "line5"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P75
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 5 is "line5"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P76
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P77
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 6 is "line6"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P78
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P79
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P80
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P81
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P82
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P83
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P84
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 4 is "line4"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P85
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 4 is "line4"
    And the Line 6 is "line6"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P86
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 4 is "line4"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P87
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 4 is "line4"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P88
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P89
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P90
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 5 is "line5"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P91
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 5 is "line5"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P92
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P93
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 6 is "line6"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P94
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P95
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P96
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P97
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P98
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P99
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P100
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P101
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 6 is "line6"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P102
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P103
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P104
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P105
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P106
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 5 is "line5"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P107
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 5 is "line5"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P108
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P109
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Line 6 is "line6"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P110
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P111
    Given the Line 1 is "2 Puddle Dock"
    And the Line 3 is "line3"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P112
    Given the Line 1 is "2 Puddle Dock"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P113
    Given the Line 1 is "2 Puddle Dock"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P114
    Given the Line 1 is "2 Puddle Dock"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P115
    Given the Line 1 is "2 Puddle Dock"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P116
    Given the Line 1 is "2 Puddle Dock"
    And the Line 4 is "line4"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P117
    Given the Line 1 is "2 Puddle Dock"
    And the Line 4 is "line4"
    And the Line 6 is "line6"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P118
    Given the Line 1 is "2 Puddle Dock"
    And the Line 4 is "line4"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P119
    Given the Line 1 is "2 Puddle Dock"
    And the Line 4 is "line4"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P120
    Given the Line 1 is "2 Puddle Dock"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P121
    Given the Line 1 is "2 Puddle Dock"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P122
    Given the Line 1 is "2 Puddle Dock"
    And the Line 5 is "line5"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P123
    Given the Line 1 is "2 Puddle Dock"
    And the Line 5 is "line5"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P124
    Given the Line 1 is "2 Puddle Dock"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P125
    Given the Line 1 is "2 Puddle Dock"
    And the Line 6 is "line6"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P126
    Given the Line 1 is "2 Puddle Dock"
    And the Post Code is "EC4V 3DB"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.P127
    Given the Line 1 is "2 Puddle Dock"
    And the Country Code is "ABC"
    When I try to create the Address
    Then Address is created successfully

  Scenario: Address.N1
    Given the Line 1 is "2 Puddle Dock"
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    And the Country Code is ""
    When I try to create the Address
    Then Address is not created

  Scenario: Address.N2
    Given the Line 1 is ""
    And the Line 2 is "line2"
    And the Line 3 is "line3"
    And the Line 4 is "line4"
    And the Line 5 is "line5"
    And the Line 6 is "line6"
    And the Post Code is "EC4V 3DB"
    When I try to create the Address
    Then Address is not created

