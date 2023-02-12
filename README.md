### Description

Decision engine for calculating maximum loan amount

Java, Gradle and Spring Boot were chosen to implement this service

### Usage

1. Clone the project
2. Make sure port `8080` is not in use on your local machine and gradle is installed
3. Navigate to the project directory and run `gradle build` to build the project and generate the jar file
4. In order to run the jar file, navigate to `build/libs` and run `java -jar loanCalculator-0.0.1-SNAPSHOT.jar`

### My thought process

- Mocked customers for the sake of simplicity in the Mocker class as advised
- Started with implementing an endpoint for calculating the loan maximum sum which takes a validated request based on
  the given constraints, then the controller passes the validated request to the service in order to process it further
- The LoanCalculatorService searches for the given customer in the mocked customer by the personal code, if not found it
  will throw an exception
- If the customer is found and has a loan, it will throw an exception
- In the case that no exception was thrown, the service class would then proceed with calculating the maximum sum
  by calculating the credit score first according to the given equation `(creditModifier / loanAmount) * loanPeriod`
- After having calculated the credit score, maximum sum can be calculated as follows `(creditScore * loanAmount)`
- If max sum turned out to be less than the minimum sum then the service would try to recalculate the period so the
  output would the minimum sum, `(loanPeriod * MINIMUM_SUM) / calculated maximumSum`
- The API response would be the max sum and the loan period
- Exception handling is set in place
- Added some integration tests for different scenarios of success and failure, test coverage is at 85%

### improvements

- Tests on the unit level