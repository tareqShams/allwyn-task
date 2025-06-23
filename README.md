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
- src/main/java
-- constants : including all main variables
-- dataModels : including project datamodels/excuction variables
-- utils : contains Logger and properties file reader
-src/main/test/
--/java/assertions : containing validation classes methods
--/java/../tests : containg test classes with api request creation and response handling
--/java/listners : testNg listner handling
--/resources : containing configuration files and test data files (to be exposed to customers)
-scr/test-output
--ExtentReport : contains html generated report file
-src/pom : maven build config file
-src/testng : TestNg suite file

execution:
- using maven run command : mvn clean test -DsuiteXmlFile=src/testng.xml