package com.api.task.Automation.tests;


import assertions.BooksAssertions;

import com.api.task.automation.constants.GeneralConstants;
import com.api.task.automation.dataModels.BookDm;
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

public class BooksTests extends BaseTest {

    @Test()
    public void getBooksData() {
        //Create extent test to be logged in report using test case title
        test = extent.createTest("Get all books data from : /api/v1/Books");
        Log.test = test;
        Log.startTestCase("Get all books data from : /api/v1/Books");
        RestAssured.baseURI = serverUrl;
        RequestSpecification request = RestAssured.given();
        request.contentType(contentType);
        Response response = request.get("Books");
        int statusCode = response.statusCode();
        BookDm[] books = response.jsonPath().getObject("", BookDm[].class);
        BooksAssertions booksAssertions = new BooksAssertions();
        booksAssertions.assertGetBooksData(statusCode, books);
    }
    @Test(dataProvider = "getBooksDataProvider")
    public void getBookDataWithId(BookDm bookDm) {
        //Create extent test to be logged in report using test case title
        test = extent.createTest(bookDm.getTestCaseTitle());
        Log.test = test;
        Log.startTestCase(bookDm.getTestCaseTitle());
        RestAssured.baseURI = serverUrl;
        RequestSpecification request = RestAssured.given();
        request.contentType(contentType);
        Response response = request.get("Books/" + bookDm.getToBeVerifiedBookId());
        int statusCode = response.statusCode();
        BookDm books = response.jsonPath().getObject("", BookDm.class);
        BooksAssertions booksAssertions = new BooksAssertions();
        booksAssertions.assertBookData(statusCode, books,bookDm);
    }

    @Test(dataProvider = "deleteBookDataProvider")
    public void deleteBookWithId(BookDm bookDm) {
        //Create extent test to be logged in report using test case title
        test = extent.createTest(bookDm.getTestCaseTitle());
        Log.test = test;
        Log.startTestCase(bookDm.getTestCaseTitle());
        RestAssured.baseURI = serverUrl;
        RequestSpecification request = RestAssured.given();
        request.contentType(contentType);
        Response response = request.get("Books/" + bookDm.getToBeVerifiedBookId());
        int statusCode = response.statusCode();
        BookDm books = response.jsonPath().getObject("", BookDm.class);
        BooksAssertions booksAssertions = new BooksAssertions();
        booksAssertions.assertDeletedBookResponse(statusCode,bookDm.getExpectedResponseCode(),
                books.getTitle(),bookDm.getExpectedResponseTitle());
    }

    @Test(dataProvider = "postNewBookDataProvider")
    public void postNewBook(BookDm bookDm) {
        //Create extent test to be logged in report using test case title
        test = extent.createTest(bookDm.getTestCaseTitle());
        Log.test = test;
        Log.startTestCase(bookDm.getTestCaseTitle());
        try {
            RestAssured.baseURI = serverUrl;
            if (bookDm.getId().equals(GeneralConstants.STRING_DELIMETER)) {
                bookDm.setId(String.valueOf(newRandom));
            }
            if (bookDm.getTitle().equals(GeneralConstants.STRING_DELIMETER)) {
                bookDm.setTitle("Book" + newRandom);
            }
            JSONObject mainParameters = new JSONObject();
            mainParameters.put("id", bookDm.getId());
            mainParameters.put("title", bookDm.getTitle());
            mainParameters.put("description", bookDm.getDescription());
            mainParameters.put("pageCount", bookDm.getPageCount());
            mainParameters.put("excerpt", bookDm.getExcerpt());
            mainParameters.put("publishDate", bookDm.getPublishDate());

            RequestSpecification request = RestAssured.given();
            request.contentType(contentType);
            request.body(mainParameters.toString());
            Response response = request.post("Books");
            int statusCode = response.statusCode();
            BookDm book = response.jsonPath().getObject("", BookDm.class);
            BooksAssertions booksAssertions = new BooksAssertions();
            booksAssertions.assertBookData(statusCode, book, bookDm);
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

    @Test(dataProvider = "updateBookDataProvider")
    public void updateBook(BookDm bookDm) {
        //Create extent test to be logged in report using test case title
        test = extent.createTest(bookDm.getTestCaseTitle());
        Log.test = test;
        Log.startTestCase(bookDm.getTestCaseTitle());
        try {
            RestAssured.baseURI = serverUrl;
            if (bookDm.getId().equals(GeneralConstants.STRING_DELIMETER)) {
                bookDm.setId(String.valueOf(newRandom));
            }
            if (bookDm.getTitle().equals(GeneralConstants.STRING_DELIMETER)) {
                bookDm.setTitle("Book" + newRandom);
            }
            JSONObject mainParameters = new JSONObject();
            mainParameters.put("id", bookDm.getId());
            mainParameters.put("title", bookDm.getTitle());
            mainParameters.put("description", bookDm.getDescription());
            mainParameters.put("pageCount", bookDm.getPageCount());
            mainParameters.put("excerpt", bookDm.getExcerpt());
            mainParameters.put("publishDate", bookDm.getPublishDate());

            RequestSpecification request = RestAssured.given();
            request.contentType(contentType);
            request.body(mainParameters.toString());
            Response response = request.put("Books/" + bookDm.getToBeVerifiedBookId());
            int statusCode = response.statusCode();
            BookDm book = response.jsonPath().getObject("", BookDm.class);
            BooksAssertions booksAssertions = new BooksAssertions();
            booksAssertions.assertBookData(statusCode, book, bookDm);
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

    @DataProvider(name = "getBooksDataProvider")
    public Object[][] getBooksJsonDataProvider() throws IOException {
        DataResource resource =
                new InputStreamResource(Files.newInputStream(Paths.get(testDataConfigsProps.getProperty("getBooks"))),
                        BookDm.class, "json");
        SeLionDataProvider dataProvider =
                DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

    @DataProvider(name = "deleteBookDataProvider")
    public Object[][] deleteBookJsonDataProvider() throws IOException {
        DataResource resource =
                new InputStreamResource(Files.newInputStream(Paths.get(testDataConfigsProps.getProperty("deleteBook"))),
                        BookDm.class, "json");
        SeLionDataProvider dataProvider =
                DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

    @DataProvider(name = "postNewBookDataProvider")
    public Object[][] postNewBookJsonDataProvider() throws IOException {
        DataResource resource =
                new InputStreamResource(Files.newInputStream(Paths.get(testDataConfigsProps.getProperty("postNewBook"))),
                        BookDm.class, "json");
        SeLionDataProvider dataProvider =
                DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

    @DataProvider(name = "updateBookDataProvider")
    public Object[][] updateBookJsonDataProvider() throws IOException {
        DataResource resource =
                new InputStreamResource(Files.newInputStream(Paths.get(testDataConfigsProps.getProperty("updateBook"))),
                        BookDm.class, "json");
        SeLionDataProvider dataProvider =
                DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

}
