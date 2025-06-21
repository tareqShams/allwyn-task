package com.api.task.Automation.tests;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import com.api.task.automation.utils.Log;
import com.api.task.automation.utils.PropertiesFilesHandler;

import io.restassured.http.ContentType;

import org.testng.ITestResult;
import org.testng.annotations.*;

import com.api.task.automation.constants.GeneralConstants;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class BaseTest {

    public static ExtentReports extent;

    //Extent report objects
    public ExtentHtmlReporter htmlReporter;
    public ExtentTest test;


    //Initialize instances of properties files to be used in all tests
    PropertiesFilesHandler propHandler = new PropertiesFilesHandler();
    Properties generalConfigsProps = propHandler.loadPropertiesFile(GeneralConstants.GENERAL_CONFIG_FILE_NAME);
    public String serverUrl = generalConfigsProps.getProperty(GeneralConstants.BASE_URL);
    public ContentType contentType = (ContentType.JSON);
    // Default config from properties file
    Properties testDataConfigsProps = propHandler.loadPropertiesFile(GeneralConstants.TEST_DATA_CONFIG_FILE_NAME);
    Random random = new Random();
    int newRandom = random.nextInt(9999);
    String dateTime = new SimpleDateFormat("hh:mm:ss").format(new Date());
    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    // get report file path
    String extentReportFilePath = generalConfigsProps.getProperty(GeneralConstants.EXTENT_REPORT_FILE_PATH);

    @BeforeTest(description = "Setting up extent report", alwaysRun = true)
    @Parameters({"browserType", "buildNumber", "userType"})
    public void setExtent() {
        try {
            Log.info("Setting up extent report before test");

            // specify location of the report
           /* htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + extentReportFilePath +
                    GeneralConstants.STRING_DELIMETER +
                    dateTime + ".html");*/
            htmlReporter = new ExtentHtmlReporter("D:\\report.html");

            htmlReporter.config().setDocumentTitle(generalConfigsProps.getProperty(GeneralConstants.EXTENT_REPORT_TITLE)); // Title of report
            htmlReporter.config().setReportName(generalConfigsProps.getProperty(GeneralConstants.EXTENT_REPORT_NAME)); // Name of the report
            htmlReporter.config().setTheme(Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);

            // Passing General information
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Url", serverUrl);
            extent.setSystemInfo("Date", date);
        } catch (Exception e) {
            Log.error("Error occurred while setting up extent reports on Exception: ", e);
        }

    }


    @AfterMethod(description = "Logging test status to log file and extent report", alwaysRun = true)
    public void logTestStatusForReport(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                Log.info("logging Testcase FAILED " + result.getName() + " in Extent Report");
                test.log(Status.FAIL, "Test Case Name FAILED is " + result.getName()); // to add name in extent report
                test.log(Status.FAIL, "EXCEPTION Thrown is " + result.getThrowable()); // to add error/exception in extent report
            } else if (result.getStatus() == ITestResult.SKIP) {
                Log.info("logging Testcase SKIPPED " + result.getName() + " in Extent Report");
                test.log(Status.SKIP, "Test Case SKIPPED is " + result.getName());
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                Log.info("logging Testcase SUCCESS " + result.getName() + " in Extent Report");
                test.log(Status.PASS, "Test Case PASSED is " + result.getName());
            }
            // log that test case has ended
            Log.endTestCase(result.getName());
        } catch (Exception e) {
            Log.warn("Error occurred while logging testcase " + result.getName() + " result to extent report", e);
        }
    }

    @Parameters({"browserType", "buildNumber", "userType"})
    @AfterTest(description = "Closing extent report after running Test", alwaysRun = true)
    public void endReport() {
        Log.info("Closing Extent report after Test");
        if (extent != null)
            extent.flush();
    }
}
