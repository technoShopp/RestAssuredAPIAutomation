## Project:
REST API Automation using REST Assured 

## Description:
To automate the GET response of the 16 days Weather forecast REST API 

## Technology Stack

*1. Rest Assured*

*2. Cucumber*

*3. Java*

*4. Logging*

*5. Maven*

*6. Cucumber -html -report*


## Installation and execution

### Following are the installations need to run the test automation suite###
**JDK**
**Maven**


*To run the script from a command line in a Terminal window you can use the below command.*
```
mvn test verify -Dcucumber.options="--tags @auto"
```

## Reporting and Logging
**Reporting Path**
```
./target/report/cucumber-html-reports/report-feature_4277810500.html
```
**Logging Path**
```
./src/test/java/Logs/APILog
```

## Assumptions

```
1.The GET end point URL "https://api.weatherbit.io/v2.0/forecast/daily?postal_code=<postal_code>" gets one postal code at a time and hence the validation has been performed to test one postal code at a time. 
2.The key and the GET End point URLS are maintained in the global.properties file
3.The objective is to check the conducive days that the surfer can visit beached based on the three conditions : days, temp and uv
4.The days should be only thrursday and Friday - First filter
5.The temperature should be between 20 and 30(end points included) and assumed temp is the "temp" key from  the response JSON  - Second filter 
6.The uv should be less than or equal to 3 - Third filter
7.The days values in the feature file examples should be strictly with a single space
8.The tear up and tear down information will also appear on logs
9.Validated the response of Bondi, Shelly and Manly beaches of Sydney and arrived at the days that are condcive based on the three filters mentioned above.
```

## Documentation

*https://github.com/technoShopp/RestAssuredAPIAutomation/tree/master/Execution%20Instructions*
