Feature: To implement a test to automate the Weather forecast for the Top beaches
@auto
Scenario Outline: As a choosey surfer wanting to visit "<beach>" for "<ScenarioType>" - "<ScenarioDescription>"
Given I like to surf beach "<beach>" in Sydney with the "<postalCode>" and "<country>"
When I hit the GET request
Then I like to surf on "<noOfDays>" and those days should be "<days>"
And I check for "<factor2>" to be "<tempRange>" for "<beach>"
And I check for "<factor3>" to be "<uvIndex>" for "<beach>"

Examples:
|beach    |postalCode|country|noOfDays|days                    | tempRange| uvIndex|factor2    | factor3|ScenarioType     |  ScenarioDescription                       |
#POSITIVE SCENARIOS
|Manly    |2095      |AU     |2       |thursday friday         | 20and30  |3       |temperature|uv      |POSITIVE SCENARIO|  ALL CRIRETIA MATCHED - data1              |
|Shelly   |2261      |AU     |2       |thursday friday         | 20and30  |3       |temperature|uv      |POSITIVE SCENARIO|  ALL CRIRETIA MATCHED - data2              |
|Bondi    |2026      |AU     |2       |thursday friday         | 20and30  |3       |temperature|uv      |POSITIVE SCENARIO|  ALL CRIRETIA MATCHED - data3              |
# NEGATIVE SCENARIO - WHEN THE DAYS ARE NOT THURSDAY OR FRIDAY
|Bondi    |2026      |AU     |2       |tuesday friday          | 20and30  |3       |temperature|uv      |NEGATIVE SCENARIO|  GIVEN DATES ARE NOT THURSDAY AND FRIDAY   |
# NEGATIVE SCENARIO - WHEN THE NUMBER OF DAYS IS NOT EQUAL TO 2
|Bondi    |2026      |AU     |3       |tuesday friday          | 20and30  |3       |temperature|uv      |NEGATIVE SCENARIO|  GIVEN DAYS NOT EQUAL TO 2                 |





