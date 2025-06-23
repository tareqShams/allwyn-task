# allwyn-task
1- Project tools:
- Java Jdk 23.0.2
- Maven
- TestNg 7
- Rest-Assured
- Extent Report
- Github actions for CI/CD

2- Framework

Hybrid framework with data driven and POM design patterns

3- Project Structure

a src/main/java

1- constants : including all main variables

2- dataModels : including project datamodels/excuction variables

3- utils : contains Logger and properties file reader

b- src/main/test/

1- /java/assertions : containing validation classes methods

2- /java/../tests : containg test classes with api request creation and response handling

3- /java/listners : testNg listner handling

4- /resources : containing configuration files and test data files (to be exposed to customers)

c- scr/test-output

1- ExtentReport : contains html generated report file

d- src/pom : maven build config file

e- src/testng : TestNg suite file

4- execution:

- using maven run command : mvn clean test -DsuiteXmlFile=src/testng.xml
