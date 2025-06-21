package com.api.task.Automation.tests;


import assertions.AuthorsAssertions;
import com.api.task.automation.constants.GeneralConstants;
import com.api.task.automation.dataModels.AuthorDm;
import com.api.task.automation.utils.Log;
import com.paypal.selion.platform.dataprovider.DataProviderFactory;
import com.paypal.selion.platform.dataprovider.DataResource;
import com.paypal.selion.platform.dataprovider.SeLionDataProvider;
import com.paypal.selion.platform.dataprovider.impl.InputStreamResource;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AuthorsTests extends BaseTest {

    @Test()
    public void getAuthorsData() {
        //Create extent test to be logged in report using test case title
        test = extent.createTest("Get all Authors data from : /api/v1/Authors");
        Log.test = test;
        Log.startTestCase("Get all Authors data from : /api/v1/Authors");
        RestAssured.baseURI = serverUrl;
        RequestSpecification request = RestAssured.given();
        request.contentType(contentType);
        Response response = request.get("Authors");
        int statusCode = response.statusCode();
        AuthorDm[] authors = response.jsonPath().getObject("", AuthorDm[].class);
        AuthorsAssertions authorsAssertions = new AuthorsAssertions();
        authorsAssertions.assertGetAuthorsData(statusCode, authors);
    }

    @Test(dataProvider = "getAuthorsDataProvider")
    public void getAuthorDataWithId(AuthorDm authorDm) {
        //Create extent test to be logged in report using test case title
        test = extent.createTest(authorDm.getTestCaseTitle());
        Log.test = test;
        Log.startTestCase(authorDm.getTestCaseTitle());
        RestAssured.baseURI = serverUrl;
        RequestSpecification request = RestAssured.given();
        request.contentType(contentType);
        Response response = request.get("Authors/" + authorDm.getToBeVerifiedAuthorId());
        int statusCode = response.statusCode();
        AuthorDm author = response.jsonPath().getObject("", AuthorDm.class);
        AuthorsAssertions authorsAssertions = new AuthorsAssertions();
        authorsAssertions.assertAuthorData(statusCode, author, authorDm);
    }

    @Test(dataProvider = "deleteAuthorDataProvider")
    public void deleteAuthorWithId(AuthorDm authorDm) {
        //Create extent test to be logged in report using test case title
        test = extent.createTest(authorDm.getTestCaseTitle());
        Log.test = test;
        Log.startTestCase(authorDm.getTestCaseTitle());
        RestAssured.baseURI = serverUrl;
        RequestSpecification request = RestAssured.given();
        request.contentType(contentType);
        Response response = request.get("Authors/" + authorDm.getToBeVerifiedAuthorId());
        int statusCode = response.statusCode();
        AuthorDm author = response.jsonPath().getObject("", AuthorDm.class);
        AuthorsAssertions authorsAssertions = new AuthorsAssertions();
        authorsAssertions.assertDeletedAuthorResponse(statusCode, authorDm.getExpectedResponseCode(),
                author.getTitle(), authorDm.getExpectedResponseTitle());
    }

    @Test(dataProvider = "postNewAuthorDataProvider")
    public void postNewAuthor(AuthorDm authorDm) {
        //Create extent test to be logged in report using test case title
        test = extent.createTest(authorDm.getTestCaseTitle());
        Log.test = test;
        Log.startTestCase(authorDm.getTestCaseTitle());
        try {
            RestAssured.baseURI = serverUrl;
            if (authorDm.getId().equals(GeneralConstants.STRING_DELIMETER)) {
                authorDm.setId(String.valueOf(newRandom));
            }
            JSONObject mainParameters = new JSONObject();
            mainParameters.put("id", authorDm.getId());
            mainParameters.put("idBook", authorDm.getIdBook());
            mainParameters.put("firstName", authorDm.getFirstName());
            mainParameters.put("lastName", authorDm.getLastName());

            RequestSpecification request = RestAssured.given();
            request.contentType(contentType);
            request.body(mainParameters.toString());
            Response response = request.post("Authors");
            int statusCode = response.statusCode();
            AuthorDm author = response.jsonPath().getObject("", AuthorDm.class);
            AuthorsAssertions authorsAssertions = new AuthorsAssertions();
            authorsAssertions.assertAuthorData(statusCode, author, authorDm);
        } catch (Exception e) {
            Log.error("Error occurred in " + new Object() {
            }
                    .getClass().getName() + "." + new Object() {
            }
                    .getClass()
                    .getEnclosingMethod()
                    .getName(), e);
        }
    }

    @Test(dataProvider = "updateAuthorDataProvider")
    public void updateBook(AuthorDm authorDm) {
        //Create extent test to be logged in report using test case title
        test = extent.createTest(authorDm.getTestCaseTitle());
        Log.test = test;
        Log.startTestCase(authorDm.getTestCaseTitle());
        try {
            RestAssured.baseURI = serverUrl;
            if (authorDm.getId().equals(GeneralConstants.STRING_DELIMETER)) {
                authorDm.setId(String.valueOf(newRandom));
            }

            JSONObject mainParameters = new JSONObject();
            mainParameters.put("id", authorDm.getId());
            mainParameters.put("idBook", authorDm.getIdBook());
            mainParameters.put("firstName", authorDm.getFirstName());
            mainParameters.put("lastName", authorDm.getLastName());

            RequestSpecification request = RestAssured.given();
            request.contentType(contentType);
            request.body(mainParameters.toString());
            Response response = request.put("Authors/" + authorDm.getToBeVerifiedAuthorId());
            int statusCode = response.statusCode();
            AuthorDm author = response.jsonPath().getObject("", AuthorDm.class);
            AuthorsAssertions authorsAssertions = new AuthorsAssertions();
            authorsAssertions.assertAuthorData(statusCode, author, authorDm);
        } catch (Exception e) {
            Log.error("Error occurred in " + new Object() {
            }
                    .getClass().getName() + "." + new Object() {
            }
                    .getClass()
                    .getEnclosingMethod()
                    .getName(), e);
        }
    }

    @DataProvider(name = "getAuthorsDataProvider")
    public Object[][] getAuthorsJsonDataProvider() throws IOException {
        DataResource resource =
                new InputStreamResource(Files.newInputStream(Paths.get(testDataConfigsProps.getProperty("getAuthors"))),
                        AuthorDm.class, "json");
        SeLionDataProvider dataProvider =
                DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

    @DataProvider(name = "deleteAuthorDataProvider")
    public Object[][] deleteAuthorJsonDataProvider() throws IOException {
        DataResource resource =
                new InputStreamResource(Files.newInputStream(Paths.get(testDataConfigsProps.getProperty("deleteAuthor"))),
                        AuthorDm.class, "json");
        SeLionDataProvider dataProvider =
                DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

    @DataProvider(name = "postNewAuthorDataProvider")
    public Object[][] postNewAuthorJsonDataProvider() throws IOException {
        DataResource resource =
                new InputStreamResource(Files.newInputStream(Paths.get(testDataConfigsProps.getProperty("postNewAuthor"))),
                        AuthorDm.class, "json");
        SeLionDataProvider dataProvider =
                DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

    @DataProvider(name = "updateAuthorDataProvider")
    public Object[][] updateAuthorJsonDataProvider() throws IOException {
        DataResource resource =
                new InputStreamResource(Files.newInputStream(Paths.get(testDataConfigsProps.getProperty("updateAuthor"))),
                        AuthorDm.class, "json");
        SeLionDataProvider dataProvider =
                DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

}
